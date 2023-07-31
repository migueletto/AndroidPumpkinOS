#include <stdlib.h>
#include <stdio.h>
#include <stdint.h>
#include <stdarg.h>
#include <string.h>
#include <time.h>
#include <sys/time.h>

#include <jni.h>
#include <android/bitmap.h>

#include "sys.h"
#include "script.h"
#include "pwindow.h"
#include "debug.h"
#include "xalloc.h"
#include "pitapp.h"

typedef uint16_t pixel_t;

struct texture_t {
  int width, height;
  pixel_t *buf;
};

typedef struct {
  int width, height;
  int x, y, buttons, mods;
  uint32_t format;
  int64_t shift_up;
} android_window_t;

typedef struct {
  int action, x, y, key;
} touch_event_t;

static window_provider_t window_provider;
static JNIEnv *env;
static jobject bitmap;

#define MAX_EVENTS 16
touch_event_t events[MAX_EVENTS];
int numEvents;
int idxIn;
int idxOut;

void window_bitmap(JNIEnv *_env, jobject _bitmap) {
  env = _env;
  bitmap = _bitmap;
}

static texture_t *window_create_texture(window_t *window, int width, int height) {
  texture_t *texture;

  if ((texture = xcalloc(1, sizeof(texture_t))) != NULL) {
    texture->width = width;
    texture->height = height;
    if ((texture->buf = xcalloc(width*height, sizeof(pixel_t))) == NULL) {
      xfree(texture);
      texture = NULL;
    }
  }

  return texture;
}

int window_update_texture(window_t *_window, texture_t *texture, uint8_t *raw) {
  if (texture && raw) {
    //debug(DEBUG_INFO, "MAIN", "window_update_texture");
    xmemcpy(texture->buf, raw, texture->width * texture->height * sizeof(pixel_t));
  }

  return 0;
}

int window_draw_texture(window_t *_window, texture_t *texture, int x, int y) {
  AndroidBitmapInfo bi;
  pixel_t *p;
  void *pixels;

  if (env && bitmap && texture) {
    AndroidBitmap_getInfo(env, bitmap, &bi);
    //debug(DEBUG_INFO, "PALMOS", "draw texture %dx%d on bitmap %dx%d", texture->width, texture->height, androidBitmapInfo.width, androidBitmapInfo.height);
    AndroidBitmap_lockPixels(env, bitmap, &pixels);
    p = (pixel_t *)pixels;
    p = &p[y * bi.width + x];
    xmemcpy(p, texture->buf, texture->width * texture->height * sizeof(pixel_t));
    AndroidBitmap_unlockPixels(env, bitmap);
  }

  return 0;
}

static int window_destroy_texture(window_t *_window, texture_t *texture) {
  if (texture) {
    if (texture->buf) xfree(texture->buf);
    xfree(texture);
  }

  return 0;
}

static window_t *window_create(int encoding, int *width, int *height, int xfactor, int yfactor, int rotate, int fullscreen, int software, void *data) {
  android_window_t *w;

  if ((w = xcalloc(1, sizeof(android_window_t))) != NULL) {
    w->width = *width;
    w->height = *height;
  }

  return (window_t *)w;
}

static int window_destroy(window_t *window) {
  if (window) {
    xfree(window);
  }
  return 0;
}

int window_erase(window_t *window, uint32_t bg) {
  return 0;
}

int window_render(window_t *_window) {
  //debug(DEBUG_INFO, "MAIN", "window_render");
  return 0;
}

void window_status(window_t *_window, int *x, int *y, int *buttons) {
  android_window_t *window;

  window = (android_window_t *)_window;
  *x = window->x;
  *y = window->y;
  *buttons = window->buttons;
}


static void window_add_event(touch_event_t *event) {
  if (numEvents < MAX_EVENTS) {
    numEvents++;
    xmemcpy(&events[idxIn++], event, sizeof(touch_event_t));
    if (idxIn == MAX_EVENTS)idxIn = 0;
  } else {
    debug(DEBUG_ERROR, "MAIN", "window_add_event event queue overflow");
  }
}

int window_event2(window_t *_window, int wait, int *arg1, int *arg2) {
  touch_event_t event;
  android_window_t *window;
  int r = 0;

  if (numEvents > 0) {
    xmemcpy(&event, &events[idxOut++], sizeof(touch_event_t));
    if (idxOut == MAX_EVENTS) idxOut = 0;
    numEvents--;

    switch (event.action) {
      case 0:
        *arg1 = 1;
        r = WINDOW_BUTTONDOWN;
        break;
      case 1:
        *arg1 = 1;
        r = WINDOW_BUTTONUP;
        break;
      case 2:
        window = (android_window_t *)_window;
        window->x = event.x;
        window->y = event.y;
        *arg1 = event.x;
        *arg2 = event.y;
        r = WINDOW_MOTION;
        break;
      case 3:
        *arg1 = event.key;
        r = WINDOW_KEYUP;
        break;
    }
  }

  return r;
}

static int window_draw_texture_rect(window_t *window, texture_t *texture, int tx, int ty, int w, int h, int x, int y) {
  AndroidBitmapInfo bi;
  void *pixels;
  pixel_t *p, *src;
  int i, n;

  if (env && bitmap && texture && ty < texture->height && tx < texture->width && (tx + w) <= texture->width) {
    AndroidBitmap_getInfo(env, bitmap, &bi);
    AndroidBitmap_lockPixels(env, bitmap, &pixels);
    p = (pixel_t *)pixels;
    p = &p[y * bi.width + x];
    src = &texture->buf[ty * texture->width + tx];
    n = w * sizeof(pixel_t);
    for (i = 0; i < h && (ty + i) < texture->height; i++) {
        xmemcpy(p, src, n);
        p += bi.width;
        src += texture->width;
    }
    AndroidBitmap_unlockPixels(env, bitmap);
  }

  return 0;
}

static int window_update_texture_rect(window_t *_window, texture_t *texture, uint8_t *raw, int tx, int ty, int w, int h) {
  pixel_t *p, *src;
  int i, n;

  if (texture && src && ty < texture->height && tx < texture->width && (tx + w) <= texture->width) {
    p = (pixel_t *)&texture->buf[ty * texture->width + tx];
    src = (pixel_t *)raw;
    src = &src[ty * texture->width + tx];
    n = w * sizeof(pixel_t);
    for (i = 0; i < h && (ty + i) < texture->height; i++) {
      xmemcpy(p, src, n);
      p += texture->width;
      src += texture->width;
    }
  }

  return 0;
}

void pitTouch(int action, int x, int y) {
  touch_event_t event;

  //debug(1, "XXX", "pitTouch %d %d %d", action, x, y);
  if (action == 0) {
    pitTouch(2, x, y);
  }
  event.action = action;
  event.x = x;
  event.y = y;
  window_add_event(&event);
}

void pitKey(int key) {
  touch_event_t event;

  event.action = 3;
  event.key = key;
  window_add_event(&event);
}

void window_init(int pe) {
  memset(&window_provider, 0, sizeof(window_provider_t));
  window_provider.create = window_create;
  //window_provider.draw = window_draw;
  //window_provider.draw2 = window_draw2;
  //window_provider.event = window_event;
  window_provider.destroy = window_destroy;
  window_provider.erase = window_erase;
  window_provider.render = window_render;
  //window_provider.background = window_background;
  window_provider.create_texture = window_create_texture;
  window_provider.destroy_texture = window_destroy_texture;
  window_provider.update_texture = window_update_texture;
  window_provider.draw_texture = window_draw_texture;
  window_provider.status = window_status;
  //window_provider.title = window_title;
  //window_provider.clipboard = window_clipboard;
  window_provider.event2 = window_event2;
  window_provider.draw_texture_rect = window_draw_texture_rect;
  window_provider.update_texture_rect = window_update_texture_rect;

  numEvents = 0;

  script_set_pointer(pe, WINDOW_PROVIDER, &window_provider);
}

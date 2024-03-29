#include <stdlib.h>
#include <stdio.h>
#include <stdint.h>
#include <stdarg.h>
#include <string.h>
#include <time.h>
#include <sys/time.h>

#include <jni.h>

#include <PalmOS.h>

#include "sys.h"
#include "script.h"
#include "thread.h"
#include "pwindow.h"
#include "media.h"
#include "sys.h"
#include "ptr.h"
#include "vfs.h"
#include "vfslocal.h"
#include "gps.h"
#include "mutex.h"
#include "AppRegistry.h"
#include "storage.h"
#include "pumpkin.h"
#include "secure.h"
#include "endianness.h"
#include "debug.h"
#include "xalloc.h"
#include "pitapp.h"

extern int libos_start_direct(window_provider_t *wp, secure_provider_t *secure, int width, int height, int depth, int fullscreen, int dia, int single, char *launcher);

int pitInit(void) {
  script_engine_t *engine;
  window_provider_t *wp;
  audio_provider_t *ap;
  bt_provider_t *bt;
  gps_parse_line_f gps_parse_line;
  int pe = -1;

  debug_setsyslevel(NULL, DEBUG_INFO);
  sys_init();
  debug_init(NULL);
  ptr_init();
  thread_init();
  thread_setmain();

  debug(DEBUG_INFO, "MAIN", "%s starting on %s (%s endian)", SYSTEM_NAME, SYSTEM_OS, little_endian() ? "little" : "big");
  engine = script_load_engine("libscriptlua.so");
  vfs_init();
  //vfs_local_mount("/data/user/0/com.pit.pit/", "/");
  vfs_local_mount("/data/data/com.pit.pit/", "/");

  if (script_init(engine) != -1) {
    if ((pe = script_create(engine)) != -1) {
      window_init(pe);
      wp = script_get_pointer(pe, WINDOW_PROVIDER);
      ap = script_get_pointer(pe, AUDIO_PROVIDER);
      bt = script_get_pointer(pe, BT_PROVIDER);
      gps_parse_line = script_get_pointer(pe, GPS_PARSE_LINE_PROVIDER);
      pumpkin_global_init(engine, wp, ap, bt, gps_parse_line);
      libos_start_direct(wp, NULL, 0, 0, 16, 0, 1, 0, "Launcher");
    }
  }

  return pe;
}

void pitFinish(int pe) {
  sys_set_finish(0);
  sys_usleep(1000);
  thread_wait_all();

  if (pe != -1) {
    pumpkin_global_finish();
    script_destroy(pe);
  }
  script_finish(script_get_engine(pe));
  vfs_finish();

  thread_close();
  debug(DEBUG_INFO, "MAIN", "%s stopping", SYSTEM_NAME);
  debug_close();
}

void pitRequestFinish(void) {
  debug(DEBUG_INFO, "MAIN", "pitRequestFinish");
  pumpkin_forward_event(0, MSG_KEY, WINDOW_KEY_HOME, 0, 0);
}

void pitDeploy(char *path) {
  //StoDeployFile(path);
  // XXX update Launcher
}

void pitUpdate(JNIEnv *env, jobject bitmap) {
  window_bitmap(env, bitmap);
}

void pitPause(int paused) {
  pumpkin_pause(paused);
}

void pitSetBattery(int level) {
  pumpkin_set_battery(level);
}

#ifndef PIT_TEST_H
#define PIT_TEST_H

#ifdef __cplusplus
extern "C" {
#endif

int pitInit(int width, int height);
void pitFinish(int pe);
void pitDeploy(char *path);
int pitUpdate(JNIEnv *env, jobject bitmap, int invalidate);
void pitPause(int paused);
void pitTouch(int down, int x, int y);
void pitKey(int key);

void window_init(int pe);
void window_bitmap(JNIEnv *env, jobject bitmap);

#ifdef __cplusplus
}
#endif

#endif

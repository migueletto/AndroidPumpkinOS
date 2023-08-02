#ifndef PIT_TEST_H
#define PIT_TEST_H

#ifdef __cplusplus
extern "C" {
#endif

int pitInit(void);
void pitFinish(int pe);
void pitRequestFinish(void);
void pitDeploy(char *path);
void pitUpdate(JNIEnv *env, jobject bitmap);
void pitPause(int paused);
void pitTouch(int down, int x, int y);
void pitKey(int key);
void pitSetBattery(int level);

void window_init(int pe);
void window_bitmap(JNIEnv *env, jobject bitmap);

#ifdef __cplusplus
}
#endif

#endif

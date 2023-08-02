#include <jni.h>
#include <string>

#include "pitapp.h"

extern "C" JNIEXPORT jint JNICALL Java_com_pit_pit_Pumpkin_pitInit(JNIEnv *env, jobject /* this */) {
    return pitInit();
}

extern "C" JNIEXPORT void JNICALL Java_com_pit_pit_Pumpkin_pitFinish(JNIEnv *env, jobject /* this */, int pe) {
    pitFinish(pe);
}

extern "C" JNIEXPORT void JNICALL Java_com_pit_pit_Pumpkin_pitRequestFinish(JNIEnv *env, jobject /* this */) {
    pitRequestFinish();
}

extern "C" JNIEXPORT void JNICALL Java_com_pit_pit_Pumpkin_pitDeploy(JNIEnv *env, jobject /* this */, jstring path) {
    const char *s = (char *)env->GetStringUTFChars(path, 0);
    pitDeploy((char *)s);
    env->ReleaseStringUTFChars(path, s);
}

extern "C" JNIEXPORT void JNICALL Java_com_pit_pit_Pumpkin_pitUpdate(JNIEnv *env, jobject /* this */, jobject bitmap) {
    jobject obj = env->NewGlobalRef(bitmap);
    pitUpdate(env, obj);
}

extern "C" JNIEXPORT void JNICALL Java_com_pit_pit_Pumpkin_pitTouch(JNIEnv *env, jobject /* this */, int action, int x, int y) {
    pitTouch(action, x, y);
}

extern "C" JNIEXPORT void JNICALL Java_com_pit_pit_Pumpkin_pitPause(JNIEnv *env, jobject /* this */, jboolean paused) {
    pitPause(paused);
}

extern "C" JNIEXPORT void JNICALL Java_com_pit_pit_Pumpkin_pitKey(JNIEnv *env, jobject /* this */, int key) {
    pitKey(key);
}

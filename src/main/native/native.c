#include <jni.h>
#include <stdio.h>

// Native method implementation
JNIEXPORT jint JNICALL Java_NativLib_add(JNIEnv *env, jobject obj, jint a, jint b) {
    printf("Adding %d + %d\n", a, b);
    return a + b;
}
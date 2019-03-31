//
// Created by paopao on 2018/10/6.
//
#include <jni.h>
#include <string>
//有错误，未使用，保护AppKey
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_paopao_news_engine_JNI_getAppKey(JNIEnv *env,jobject instance){
//AppKey
std::string appkey="369a11e6fb581d89d6f1eb7fa635d595";

return env->NewStringUTF(appkey.c_str());
}

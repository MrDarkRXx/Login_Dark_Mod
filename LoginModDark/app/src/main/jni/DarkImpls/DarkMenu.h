extern "C"
JNIEXPORT jobjectArray JNICALL
Java_dark_login_FloaterDark_getListFT(JNIEnv *env, jobject thiz) {
    jobjectArray ret;

    if (isTrueMod){
        const char *features[] = {
                OBFUSCATE("TG_EXAMPLE"),
                OBFUSCATE("TG_EXAMPLE 1"),
                OBFUSCATE("SB_EXAMPLE 2_0_10")
        };

        //Now you dont have to manually update the number everytime;
        int Total_Feature = (sizeof features / sizeof features[0]);
        ret = (jobjectArray)
                env->NewObjectArray(Total_Feature, env->FindClass(OBFUSCATE("java/lang/String")),
                                    env->NewStringUTF(""));

        for (int i = 0; i < Total_Feature; i++)
            env->SetObjectArrayElement(ret, i, env->NewStringUTF(features[i]));

        return (ret);
    } else {
        const char *features[] = {
                OBFUSCATE("TG_ANTI CRACK"),
                OBFUSCATE("TG_ANTI CRACK"),
                OBFUSCATE("TG_ANTI CRACK"),
                OBFUSCATE("TG_ANTI CRACK"),
                OBFUSCATE("TG_ANTI CRACK"),
                OBFUSCATE("TG_ANTI CRACK"),
                OBFUSCATE("TG_ANTI CRACK"),
                OBFUSCATE("TG_ANTI CRACK"),
                OBFUSCATE("TG_ANTI CRACK"),
                OBFUSCATE("TG_ANTI CRACK"),
                OBFUSCATE("TG_ANTI CRACK"),
                OBFUSCATE("TG_ANTI CRACK")
        };

        //Now you dont have to manually update the number everytime;
        int Total_Feature = (sizeof features / sizeof features[0]);
        ret = (jobjectArray)
                env->NewObjectArray(Total_Feature, env->FindClass(OBFUSCATE("java/lang/String")),
                                    env->NewStringUTF(""));

        for (int i = 0; i < Total_Feature; i++)
            env->SetObjectArrayElement(ret, i, env->NewStringUTF(features[i]));

        return (ret);
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_dark_login_FloaterDark_changeToggle(JNIEnv *env, jobject thiz, jint param_int) {
    switch (param_int) {
        case 1:
            break;
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_dark_login_FloaterDark_changeSeekBar(JNIEnv *env, jobject thiz, jint param_int1,
                                          jint param_int2) {
    switch (param_int2) {
        case 1:
            break;
    }
}
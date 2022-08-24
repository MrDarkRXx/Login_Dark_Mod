extern "C"
JNIEXPORT jbyteArray JNICALL
Java_dark_login_LoginDark_puk(JNIEnv *env, jobject thiz) {
    jbyte a[] = {48, -127, -97, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 1, 5, 0, 3, -127, -115, 0, 48, -127, -119, 2, -127, -127, 0, -41, 36, -11, -27, -61, 32, 124, 58, 39, -94, -13, 7, 48, -104, -109, 106, -75, -8, -128, -92, -89, -125, -49, -83, 75, 12, -26, 90, 56, 35, 52, -116, 30, 40, -69, -70, 86, 14, -80, -20, 55, -89, 104, -46, -17, -80, -119, 83, -14, 116, -66, 11, -108, 5, 76, 12, -43, -89, -49, 11, 38, -124, 71, 45, 65, -103, 10, 99, 33, 79, 21, -16, -38, -60, 24, -108, 101, -89, -18, 48, -37, -78, 59, 10, 89, 42, 51, -43, 9, -33, -68, 61, -45, 94, -49, 83, 52, 56, 105, -123, 18, 89, 3, 54, -48, -63, -61, -103, -9, 79, -36, 18, 119, 11, -35, 82, 73, -66, 12, 123, -38, 97, 121, -30, 31, -50, -106, 127, 2, 3, 1, 0, 1};
    jbyteArray ret = env->NewByteArray(162);
    env->SetByteArrayRegion (ret, 0, 162, a);
    return ret;
}

void ToastDark(JNIEnv *env, jobject thiz, const char *text) {
    jstring jstr = env->NewStringUTF(text);
    jclass toast = env->FindClass(OBFUSCATE("android/widget/Toast"));
    jmethodID methodMakeText =
            env->GetStaticMethodID(
                    toast,
                    OBFUSCATE("makeText"),
                    OBFUSCATE(
                            "(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;"));
    if (methodMakeText == NULL) {
        LOGE(OBFUSCATE("toast.makeText not Found"));
        return;
    }

    jobject toastobj = env->CallStaticObjectMethod(toast, methodMakeText,
                                                   thiz, jstr, 0);

    jmethodID methodShow = env->GetMethodID(toast, OBFUSCATE("show"), OBFUSCATE("()V"));
    if (methodShow == NULL) {
        LOGE(OBFUSCATE("toast.show not Found"));
        return;
    }
    env->CallVoidMethod(toastobj, methodShow);
}

void setDialog(jobject ctx, JNIEnv *env, const char *title, const char *msg){
    jclass Alert = env->FindClass(OBFUSCATE("android/app/AlertDialog$Builder"));
    jmethodID AlertCons = env->GetMethodID(Alert, OBFUSCATE("<init>"), OBFUSCATE("(Landroid/content/Context;)V"));

    jobject MainAlert = env->NewObject(Alert, AlertCons, ctx);

    jmethodID setTitle = env->GetMethodID(Alert, OBFUSCATE("setTitle"), OBFUSCATE("(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;"));
    env->CallObjectMethod(MainAlert, setTitle, env->NewStringUTF(title));

    jmethodID setMsg = env->GetMethodID(Alert, OBFUSCATE("setMessage"), OBFUSCATE("(Ljava/lang/CharSequence;)Landroid/app/AlertDialog$Builder;"));
    env->CallObjectMethod(MainAlert, setMsg, env->NewStringUTF(msg));

    jmethodID setCa = env->GetMethodID(Alert, OBFUSCATE("setCancelable"), OBFUSCATE("(Z)Landroid/app/AlertDialog$Builder;"));
    env->CallObjectMethod(MainAlert, setCa, false);

    jmethodID setPB = env->GetMethodID(Alert, "setPositiveButton", "(Ljava/lang/CharSequence;Landroid/content/DialogInterface$OnClickListener;)Landroid/app/AlertDialog$Builder;");
    env->CallObjectMethod(MainAlert, setPB, env->NewStringUTF("Ok"), static_cast<jobject>(NULL));

    jmethodID create = env->GetMethodID(Alert, OBFUSCATE("create"), OBFUSCATE("()Landroid/app/AlertDialog;"));
    jobject creaetob = env->CallObjectMethod(MainAlert, create);

    jclass AlertN = env->FindClass(OBFUSCATE("android/app/AlertDialog"));

    jmethodID show = env->GetMethodID(AlertN, OBFUSCATE("show"), OBFUSCATE("()V"));
    env->CallVoidMethod(creaetob, show);
}

jstring fromBase64String(JNIEnv *env, jstring str);
jstring fromBase64String(JNIEnv *env, jstring str){
    jclass base64class = env->FindClass(OBFUSCATE("android/util/Base64"));
    jmethodID base64constr = env->GetStaticMethodID(base64class, OBFUSCATE("decode"), OBFUSCATE("(Ljava/lang/String;I)[B"));

    jclass stringclass = env->FindClass(OBFUSCATE("java/lang/String"));
    jmethodID stringconstr = env->GetMethodID(stringclass, OBFUSCATE("<init>"), OBFUSCATE("([BLjava/lang/String;)V"));

    jstring mainstring = (jstring) env->NewObject(stringclass, stringconstr, env->CallStaticObjectMethod(base64class, base64constr, str, 2), env->NewStringUTF(OBFUSCATE("UTF-8")));

    return mainstring;

}

jstring toBase64(JNIEnv *env, jstring str);
jstring toBase64(JNIEnv *env, jstring str){
    jclass base64class = env->FindClass(OBFUSCATE("android/util/Base64"));
    jmethodID base64constr = env->GetStaticMethodID(base64class, OBFUSCATE("encodeToString"), OBFUSCATE("([BI)Ljava/lang/String;"));

    jclass stringclass = env->FindClass(OBFUSCATE("java/lang/String"));
    jmethodID stringconstr = env->GetMethodID(stringclass, OBFUSCATE("<init>"), OBFUSCATE("(Ljava/lang/String;)V"));
    jstring mainstring = (jstring) env->NewObject(stringclass, stringconstr, str);

    jmethodID getbyts = env->GetMethodID(stringclass, OBFUSCATE("getBytes"), OBFUSCATE("(Ljava/lang/String;)[B"));
    jobjectArray bytestrings = (jobjectArray) env->CallObjectMethod(mainstring, getbyts, env->NewStringUTF(OBFUSCATE("UTF_8")));

    jstring final = (jstring) env->CallStaticObjectMethod(base64class, base64constr ,bytestrings, 2);
    return final;

}

jstring getDeviceName(JNIEnv *env);
jstring getDeviceName(JNIEnv *env){
    jclass BuILDOS = env->FindClass(OBFUSCATE("android/os/Build"));
    jfieldID manofacturerid = env->GetStaticFieldID(BuILDOS, OBFUSCATE("MANUFACTURER"), OBFUSCATE("Ljava/lang/String;"));
    jstring MANUFACTURERSTRING = (jstring)env->GetStaticObjectField(BuILDOS, manofacturerid);

    jfieldID modelid = env->GetStaticFieldID(BuILDOS, OBFUSCATE("MODEL"), OBFUSCATE("Ljava/lang/String;"));
    jstring MODELSTRING = (jstring)env->GetStaticObjectField(BuILDOS, modelid);

    jclass stringclass = env->FindClass(OBFUSCATE("java/lang/String"));
    jmethodID stringconstr = env->GetMethodID(stringclass, OBFUSCATE("<init>"), OBFUSCATE("(Ljava/lang/String;)V"));
    jobject mainstring =  env->NewObject(stringclass, stringconstr, MODELSTRING);

    jmethodID startwhisid = env->GetMethodID(stringclass, OBFUSCATE("startsWith"), OBFUSCATE("(Ljava/lang/String;)Z"));

    jboolean jboolean1 = env->CallBooleanMethod(mainstring, startwhisid, MANUFACTURERSTRING);

    if (jboolean1){
        return MODELSTRING;
    } else {
        std::stringstream zx;
        zx << env->GetStringUTFChars(MANUFACTURERSTRING, 0);
        zx << OBFUSCATE(" ");
        zx << env->GetStringUTFChars(MODELSTRING, 0);
        std::string zax = zx.str();
        return env->NewStringUTF(zax.c_str());
    }
}

jstring getUniqueId(JNIEnv *env, jobject ctx);
jstring getUniqueId(JNIEnv *env, jobject ctx){

    jclass Secureclass = env->FindClass(OBFUSCATE("android/provider/Settings$Secure"));
    jclass ctxclass = env->FindClass(OBFUSCATE("android/content/Context"));
    jmethodID getmetodouiie = env->GetMethodID(ctxclass, OBFUSCATE("getContentResolver"), OBFUSCATE("()Landroid/content/ContentResolver;"));
    jobject getContentResolver = env->CallObjectMethod(ctx, getmetodouiie);

    jmethodID getstringid = env->GetStaticMethodID(Secureclass, OBFUSCATE("getString"), OBFUSCATE("(Landroid/content/ContentResolver;Ljava/lang/String;)Ljava/lang/String;"));

    jclass BuILDOS = env->FindClass(OBFUSCATE("android/os/Build"));
    jfieldID harid = env->GetStaticFieldID(BuILDOS, OBFUSCATE("HARDWARE"), OBFUSCATE("Ljava/lang/String;"));
    jstring HARDWARESTRING = (jstring)env->GetStaticObjectField(BuILDOS, harid);

    jfieldID androidididi = env->GetStaticFieldID(Secureclass, OBFUSCATE("ANDROID_ID"), OBFUSCATE("Ljava/lang/String;"));

    jstring android_id = (jstring) env->GetStaticObjectField(Secureclass, androidididi);

    jstring getStrigSecure = (jstring) env->CallStaticObjectMethod(Secureclass, getstringid, getContentResolver, android_id);

    std::stringstream zx;
    zx << env->GetStringUTFChars(getDeviceName(env), 0);
    zx << env->GetStringUTFChars(getStrigSecure, 0);
    zx << env->GetStringUTFChars(HARDWARESTRING, 0);
    std::string zax = zx.str();

    jclass stringclass = env->FindClass(OBFUSCATE("java/lang/String"));
    jmethodID stringconstr = env->GetMethodID(stringclass, OBFUSCATE("<init>"), OBFUSCATE("(Ljava/lang/String;)V"));
    jobject mainstring =  env->NewObject(stringclass, stringconstr, env->NewStringUTF(zax.c_str()));

    jmethodID replace = env->GetMethodID(stringclass, OBFUSCATE("replace"), OBFUSCATE("(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;"));
    env->CallObjectMethod(mainstring, replace, env->NewStringUTF(OBFUSCATE(" ")),env->NewStringUTF(OBFUSCATE("")));

    jmethodID jbytessss = env->GetMethodID(stringclass, OBFUSCATE("getBytes"), OBFUSCATE("()[B"));
    jbyteArray bytesstruing = (jbyteArray)env->CallObjectMethod(mainstring, jbytessss);

    jclass UIDDDDCLASS = env->FindClass(("java/util/UUID"));
    jmethodID nameUIDDDD = env->GetStaticMethodID(UIDDDDCLASS, OBFUSCATE("nameUUIDFromBytes"), OBFUSCATE("([B)Ljava/util/UUID;"));
    jobject uidstring = env->CallStaticObjectMethod(UIDDDDCLASS, nameUIDDDD, bytesstruing);

    jmethodID ofvalueid = env->GetStaticMethodID(stringclass, OBFUSCATE("valueOf"), OBFUSCATE("(Ljava/lang/Object;)Ljava/lang/String;"));

    jmethodID stringconstr2 = env->GetMethodID(stringclass, OBFUSCATE("<init>"), OBFUSCATE("(Ljava/lang/String;)V"));
    jobject mainstring2 =  env->NewObject(stringclass, stringconstr2, env->CallStaticObjectMethod(stringclass, ofvalueid, uidstring));

    jmethodID replaceeee = env->GetMethodID(stringclass, OBFUSCATE("replace"), OBFUSCATE("(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;"));

    return (jstring)env->CallObjectMethod(mainstring2, replaceeee, env->NewStringUTF(OBFUSCATE("-")),env->NewStringUTF(OBFUSCATE("")));
}

extern "C"
JNIEXPORT jstring JNICALL
Java_dark_login_Utils_fromBase64String(JNIEnv *env, jclass clazz, jstring s) {
    return fromBase64String(env, s);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_dark_login_LoginDark_login(JNIEnv *env, jobject thiz, jstring user, jstring pass,
                              jobject httpurlmain,  jobject ctx) {

    //puk
    jbyte a[] = {48, -127, -97, 48, 13, 6, 9, 42, -122, 72, -122, -9, 13, 1, 1, 1, 5, 0, 3, -127,
                 -115, 0, 48, -127, -119, 2, -127, -127, 0, -41, 36, -11, -27, -61, 32, 124, 58, 39,
                 -94, -13, 7, 48, -104, -109, 106, -75, -8, -128, -92, -89, -125, -49, -83, 75, 12,
                 -26, 90, 56, 35, 52, -116, 30, 40, -69, -70, 86, 14, -80, -20, 55, -89, 104, -46,
                 -17, -80, -119, 83, -14, 116, -66, 11, -108, 5, 76, 12, -43, -89, -49, 11, 38,
                 -124, 71, 45, 65, -103, 10, 99, 33, 79, 21, -16, -38, -60, 24, -108, 101, -89, -18,
                 48, -37, -78, 59, 10, 89, 42, 51, -43, 9, -33, -68, 61, -45, 94, -49, 83, 52, 56,
                 105, -123, 18, 89, 3, 54, -48, -63, -61, -103, -9, 79, -36, 18, 119, 11, -35, 82,
                 73, -66, 12, 123, -38, 97, 121, -30, 31, -50, -106, 127, 2, 3, 1, 0, 1};
    jbyteArray puk = env->NewByteArray(162);
    env->SetByteArrayRegion(puk, 0, 162, a);

    jclass Auth = env->FindClass(OBFUSCATE("dark/login/LoginDark"));

    //data
    jclass dataobjs = env->FindClass(OBFUSCATE("org/json/JSONObject"));
    jmethodID datacons = env->GetMethodID(dataobjs, OBFUSCATE("<init>"), OBFUSCATE("()V"));
    jobject datamain = env->NewObject(dataobjs, datacons);

    jmethodID put1data = env->GetMethodID(dataobjs, OBFUSCATE("put"),
                                          OBFUSCATE("(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;"));

    //USER
    env->CallObjectMethod(datamain, put1data, env->NewStringUTF(OBFUSCATE("user")), user);

    jmethodID put2data = env->GetMethodID(dataobjs, OBFUSCATE("put"),
                                          OBFUSCATE("(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;"));

    //PASS
    env->CallObjectMethod(datamain, put2data, env->NewStringUTF("pass"), pass);

    jmethodID put3data = env->GetMethodID(dataobjs, OBFUSCATE("put"),
                                          OBFUSCATE("(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;"));

    //DEVICE
    env->CallObjectMethod(datamain, put3data, env->NewStringUTF(OBFUSCATE("device")), getUniqueId(env, ctx));

    //encryp
    jmethodID encrypt = env->GetStaticMethodID(Auth, OBFUSCATE("encrypt"),
                                               OBFUSCATE("(Ljava/lang/String;[B)Ljava/lang/String;"));
    jmethodID tostringdata = env->GetMethodID(dataobjs, OBFUSCATE("toString"), OBFUSCATE("()Ljava/lang/String;"));
    jstring datastring = (jstring) env->CallObjectMethod(datamain, tostringdata);
    jstring encryptdata = (jstring) env->CallStaticObjectMethod(Auth, encrypt, datastring, puk);

    //SHADO
    jclass UtilsDark = env->FindClass(OBFUSCATE("dark/login/Utils"));
    jmethodID SHA256method = env->GetStaticMethodID(UtilsDark, OBFUSCATE("SHA256"),
                                                    OBFUSCATE("(Ljava/lang/String;)Ljava/lang/String;"));
    jstring hash = (jstring) env->CallStaticObjectMethod(UtilsDark, SHA256method, datastring);

    //token
    jclass jobjects = env->FindClass(OBFUSCATE("org/json/JSONObject"));
    jmethodID tokenconstruc = env->GetMethodID(jobjects, OBFUSCATE("<init>"), OBFUSCATE("()V"));
    jobject newobjecttoken = env->NewObject(jobjects, tokenconstruc);

    jmethodID put1 = env->GetMethodID(jobjects, OBFUSCATE("put"),
                                      OBFUSCATE("(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;"));
    env->CallObjectMethod(newobjecttoken, put1, env->NewStringUTF(OBFUSCATE("Data")), encryptdata);

    jmethodID put2 = env->GetMethodID(jobjects, OBFUSCATE("put"),
                                      OBFUSCATE("(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;"));
    env->CallObjectMethod(newobjecttoken, put2, env->NewStringUTF(OBFUSCATE("Hash")), hash);

    jmethodID tostringtoken7 = env->GetMethodID(jobjects, OBFUSCATE("toString"), OBFUSCATE("()Ljava/lang/String;"));
    jstring tostringtokenfinal = (jstring) env->CallObjectMethod(newobjecttoken, tostringtoken7);

    //url main
    jclass httpcon = env->FindClass(OBFUSCATE("java/net/HttpURLConnection"));

    jmethodID getmthodurl = env->GetMethodID(httpcon, OBFUSCATE("setRequestMethod"), OBFUSCATE("(Ljava/lang/String;)V"));
    env->CallVoidMethod(httpurlmain, getmthodurl, env->NewStringUTF(OBFUSCATE("POST")));

    jmethodID setrequespr = env->GetMethodID(httpcon, OBFUSCATE("setRequestProperty"), OBFUSCATE("(Ljava/lang/String;Ljava/lang/String;)V"));
    env->CallVoidMethod(httpurlmain, setrequespr, env->NewStringUTF(OBFUSCATE("Content-Type")), env->NewStringUTF(OBFUSCATE("application/x-www-form-urlencoded")));


    jmethodID setFixed = env->GetMethodID(httpcon, OBFUSCATE("setFixedLengthStreamingMode"), OBFUSCATE("(I)V"));

    //token
    std::stringstream zx;
    zx << OBFUSCATE("token=");
    zx << env->GetStringUTFChars(toBase64(env, tostringtokenfinal), 0);
    std::string zax = zx.str();

    jstring tokenstringchar = env->NewStringUTF(zax.c_str());

    jclass stringclass = env->FindClass(OBFUSCATE("java/lang/String"));
    jmethodID stringconstr = env->GetMethodID(stringclass, OBFUSCATE("<init>"), OBFUSCATE("(Ljava/lang/String;)V"));
    jstring mainstring = (jstring) env->NewObject(stringclass, stringconstr, tokenstringchar);

    jmethodID bytestoken = env->GetMethodID(stringclass, OBFUSCATE("length"), OBFUSCATE("()I"));
    jint getbytessss = env->CallIntMethod(mainstring, bytestoken);
    env->CallVoidMethod(httpurlmain, setFixed, getbytessss);

    //prinwi
    jclass printclass = env->FindClass(OBFUSCATE("java/io/PrintWriter"));
    jmethodID contrprin = env->GetMethodID(printclass, OBFUSCATE("<init>"), OBFUSCATE("(Ljava/io/OutputStream;)V"));

    jmethodID outhphtppp = env->GetMethodID(httpcon, OBFUSCATE("getOutputStream"), OBFUSCATE("()Ljava/io/OutputStream;"));
    jobject oupt = env->CallObjectMethod(httpurlmain, outhphtppp);
    jobject mainprint = env->NewObject(printclass, contrprin, oupt);

    jmethodID write = env->GetMethodID(printclass,OBFUSCATE("write"),OBFUSCATE("(Ljava/lang/String;)V"));
    env->CallVoidMethod(mainprint,write,env->NewStringUTF(zax.c_str()));

    jmethodID close = env->GetMethodID(printclass,OBFUSCATE("close"), OBFUSCATE("()V"));
    env->CallVoidMethod(mainprint,close);

    jmethodID impu = env->GetMethodID(httpcon, OBFUSCATE("getInputStream"), OBFUSCATE("()Ljava/io/InputStream;"));
    jobject impuuy = env->CallObjectMethod(httpurlmain, impu);

    jmethodID readstringm = env->GetStaticMethodID(UtilsDark, OBFUSCATE("readStream"), OBFUSCATE("(Ljava/io/InputStream;)Ljava/lang/String;"));
    return fromBase64String(env, (jstring)env->CallStaticObjectMethod(UtilsDark, readstringm, impuuy));
}

extern "C"
JNIEXPORT jobject JNICALL
Java_dark_login_LoginDark_NetworkConection(JNIEnv *env, jobject thiz) {
    jclass urlclass = env->FindClass(OBFUSCATE("java/net/URL"));

    jmethodID urlcontruc = env->GetMethodID(urlclass, OBFUSCATE("<init>"), OBFUSCATE("(Ljava/lang/String;)V"));

    //Change Your URL Server
    jstring urlserver = env->NewStringUTF(OBFUSCATE("https://test.com/login"));

    jobject mainurl = env->NewObject(urlclass, urlcontruc, urlserver);

    return mainurl;
}

extern "C"
JNIEXPORT jobject JNICALL
Java_dark_login_LoginDark_JS0NDark(JNIEnv *env, jobject thiz, jstring s) {
    jclass dataobjs = env->FindClass(OBFUSCATE("org/json/JSONObject"));
    jmethodID datacons = env->GetMethodID(dataobjs, OBFUSCATE("<init>"), OBFUSCATE("(Ljava/lang/String;)V"));
    jobject datamain = env->NewObject(dataobjs, datacons, s);

    jmethodID getStringJ = env->GetMethodID(dataobjs, OBFUSCATE("getString"), OBFUSCATE("(Ljava/lang/String;)Ljava/lang/String;"));
    jstring responso_success = (jstring) env->CallObjectMethod(datamain, getStringJ, env->NewStringUTF(OBFUSCATE("Status")));

    jclass stringclass = env->FindClass(OBFUSCATE("java/lang/String"));
    jmethodID stringconstr = env->GetMethodID(stringclass, OBFUSCATE("<init>"),OBFUSCATE("(Ljava/lang/String;)V"));
    jobject mainstring = env->NewObject(stringclass, stringconstr, responso_success);

    jmethodID queaklls = env->GetMethodID(stringclass, OBFUSCATE("equals"), OBFUSCATE("(Ljava/lang/Object;)Z"));

    //CHECK SUCCESS
    jstring responsestatuscheck = env->NewStringUTF(OBFUSCATE("Success"));
    jboolean jboolean1 = env->CallBooleanMethod(mainstring, queaklls, responsestatuscheck);

    if (jboolean1){
        isTrueMod = true;
    } else {
        isTrueMod = false;
    }

    return datamain;
}
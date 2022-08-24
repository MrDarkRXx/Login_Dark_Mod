#include <vector>
#include <string.h>
#include <pthread.h>
#include <cstring>
#include <jni.h>
#include <unistd.h>
#include <fstream>
#include <iostream>
#include <sstream>
#include "DarkImpls/obfuscate.h"
#include "DarkImpls/Logger.h"
bool isTrueMod;
#include "DarkImpls/DarkMenu.h"
#include "DarkImpls/UtilsDark.h"

using namespace std;
#include "DarkImpls/DarkLogin.h"


__attribute__((constructor))
void lib_main() {
  // pthread_t ptid;
  //  pthread_create(&ptid, NULL, hack_thread, NULL);
}
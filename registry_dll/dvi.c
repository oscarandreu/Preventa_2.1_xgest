
#include "registry.h"

#include <windows.h>


//	==================================================================
JNIEXPORT jdouble JNICALL Java_registry_getData(JNIEnv * env,jclass c, jstring drive)
//	==================================================================
{
     const char *root=(*env)->GetStringUTFChars(env,drive,0);
     char volumeNameBuffer[MAX_PATH];
	 DWORD lVolumeSerialNumber=0;
     DWORD lMaximumComponentLength=0;
     DWORD lFileSystemFlags=0;
     char fileSystemNameBuffer[10];
	 unsigned long out = NULL;
	 if(GetVolumeInformation(root,volumeNameBuffer,MAX_PATH,&lVolumeSerialNumber,&lMaximumComponentLength,&lFileSystemFlags,fileSystemNameBuffer,10))
     {
		 out = lVolumeSerialNumber;
     }
     return out;
}
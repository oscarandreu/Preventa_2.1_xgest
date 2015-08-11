// invok.cpp: define el	punto de entrada de	la aplicación.
//

#include "stdafx.h"
#include <stdio.h>
#include <mmsystem.h>
#include "invok.h"
#include <jni.h>
#include "Prev.h"


#define	PATH_SEPARATOR ';' /* define it	to be ':' on Solaris */
#define	USER_CLASSPATH ".;./prev.jar;./lib/jaybird-full-2.0.1.jar;;./lib/jaybird-full-2.0.1.jar;./lib/jcalendar.jar;./lib/jfreechart-1.0.1.jar;./lib/jcommon-1.0.2.jar;./lib/simpleftp.jar;./lib/mysql-connector-java-3.1.13-bin.jar"//;PC_getion_inventario.jar" /*	where Prog.class is	*/
#pragma	comment(lib, "winmm.lib")
#define	file "licencia.lic"

JNIEnv *env;
JavaVM *jvm;
jint res;
jclass cls;
jmethodID mid;
jobject	obj,objg;

// Global Variables:
static HWND            hDlg ;
HINSTANCE            hInst;                                        // current instance




int	APIENTRY _tWinMain(HINSTANCE hInstance,
					   HINSTANCE hPrevInstance,
					   LPTSTR	 lpCmdLine,
					   int		 nCmdShow)
{
	

	char *msg =	NULL;

#ifdef JNI_VERSION_1_2
	JavaVMInitArgs vm_args;
	JavaVMOption options[1];
	options[0].optionString	= "-Djava.class.path=" USER_CLASSPATH;
	vm_args.version	= 0x00010002;
	vm_args.options	= options;
	vm_args.nOptions = 1;
	vm_args.ignoreUnrecognized = JNI_TRUE;
	/* Create the Java VM */
	res	= JNI_CreateJavaVM(&jvm, (void**)&env, &vm_args);
#else
	JDK1_1InitArgs vm_args;
	char classpath[1024];
	vm_args.version	= 0x00010001;
	JNI_GetDefaultJavaVMInitArgs(&vm_args);
	/* Append USER_CLASSPATH to	the	default	system class path */
	sprintf(classpath, "%s%c%s",
		vm_args.classpath, PATH_SEPARATOR, USER_CLASSPATH);
	vm_args.classpath =	classpath;
	/* Create the Java VM */
	res	= JNI_CreateJavaVM(&jvm, &env, &vm_args);
#endif /* JNI_VERSION_1_2 */
	if (res	< 0) {
		PlaySound( "err.wav", NULL,	SND_FILENAME | SND_ASYNC);
		MessageBox(	NULL, 
			"No	se pudo	crear la maquina virtual.",
			"Error fatal.",	MB_OK| MB_ICONEXCLAMATION) ;
		return (1);
	}

	cls	= env->FindClass("system/Cargador");
	if (cls	!= NULL) {
		mid	= env->GetMethodID(cls,"<init>","()V");
		if (mid	!= NULL) {	
			FILE *fichero;
			fichero	= fopen( file, "r" );
			if(	fichero	){
				//tambien funciona mal
				obj = env->AllocObject( cls );
				objg = env->NewGlobalRef(obj);
				env->CallNonvirtualVoidMethod(objg,cls,mid);
				
				/*funciona mal
				obj = env->NewObject(cls,mid);
				objg = env->NewGlobalRef(obj);
				env->CallVoidMethod(cls, mid );
				*/
			}else{
				MessageBox(	NULL, 
					"Para registrarse ejecute la aplicación\nregistro.exe",
					"No se encuentra la licencia", MB_OK| MB_ICONEXCLAMATION) ;
				return (0);
			}

		}else{
			msg	= "No se encontro el metodo\nde inicio.";
		}
	}else{
		msg	= "No se encontro la clase de\ninicio.";
	}
	if (env->ExceptionOccurred()) {
		env->ExceptionDescribe();
	}

	if(	msg	!= NULL	){
		jvm->DestroyJavaVM(); // kill jvm 
		//PlaySound( "err.wav", NULL,	SND_FILENAME | SND_ASYNC);
		MessageBox(	NULL, 
			msg,
			"Error fatal", MB_OK| MB_ICONEXCLAMATION
			) ;		
	}


	MSG msga;
	hInst = hInstance; // Store instance handle in our global variable
	while (GetMessage (&msga, NULL, 0, 0)) {
             TranslateMessage (&msga);
             DispatchMessage (&msga);

        }

	return (0);
}


/*
clase= env->NewGlobalRef(cls);
mid	= env->GetMethodID(clase, "<init>","()V");
env->NewObject(clase, mid);
*/
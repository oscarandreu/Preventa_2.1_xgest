

#include "system_Cargador.h"
#include <windows.h>
#include <stdio.h>
#include <mmsystem.h>
#pragma comment(lib, "winmm.lib")


unsigned char rawData[419] = {
               0xAC, 0xED, 0x00, 0x05, 0x73, 0x72, 0x00, 0x14, 0x6A, 0x61, 0x76, 0x61, 0x2E, 0x73, 0x65, 0x63,   
               0x75, 0x72, 0x69, 0x74, 0x79, 0x2E, 0x4B, 0x65, 0x79, 0x52, 0x65, 0x70, 0xBD, 0xF9, 0x4F, 0xB3,   
               0x88, 0x9A, 0xA5, 0x43, 0x02, 0x00, 0x04, 0x4C, 0x00, 0x09, 0x61, 0x6C, 0x67, 0x6F, 0x72, 0x69,   
               0x74, 0x68, 0x6D, 0x74, 0x00, 0x12, 0x4C, 0x6A, 0x61, 0x76, 0x61, 0x2F, 0x6C, 0x61, 0x6E, 0x67,   
               0x2F, 0x53, 0x74, 0x72, 0x69, 0x6E, 0x67, 0x3B, 0x5B, 0x00, 0x07, 0x65, 0x6E, 0x63, 0x6F, 0x64,   
               0x65, 0x64, 0x74, 0x00, 0x02, 0x5B, 0x42, 0x4C, 0x00, 0x06, 0x66, 0x6F, 0x72, 0x6D, 0x61, 0x74,   
               0x71, 0x00, 0x7E, 0x00, 0x01, 0x4C, 0x00, 0x04, 0x74, 0x79, 0x70, 0x65, 0x74, 0x00, 0x1B, 0x4C,   
               0x6A, 0x61, 0x76, 0x61, 0x2F, 0x73, 0x65, 0x63, 0x75, 0x72, 0x69, 0x74, 0x79, 0x2F, 0x4B, 0x65,   
               0x79, 0x52, 0x65, 0x70, 0x24, 0x54, 0x79, 0x70, 0x65, 0x3B, 0x78, 0x70, 0x74, 0x00, 0x03, 0x52,   
               0x53, 0x41, 0x75, 0x72, 0x00, 0x02, 0x5B, 0x42, 0xAC, 0xF3, 0x17, 0xF8, 0x06, 0x08, 0x54, 0xE0,   
               0x02, 0x00, 0x00, 0x78, 0x70, 0x00, 0x00, 0x00, 0xA2, 0x30, 0x81, 0x9F, 0x30, 0x0D, 0x06, 0x09,   
               0x2A, 0x86, 0x48, 0x86, 0xF7, 0x0D, 0x01, 0x01, 0x01, 0x05, 0x00, 0x03, 0x81, 0x8D, 0x00, 0x30,   
               0x81, 0x89, 0x02, 0x81, 0x81, 0x00, 0x90, 0xB2, 0x8A, 0x06, 0xFD, 0x01, 0xE7, 0x1E, 0x01, 0x4A,   
               0xDB, 0x3C, 0x9D, 0x1E, 0xF9, 0x5E, 0x0C, 0xD0, 0x9E, 0x6F, 0x09, 0x0C, 0x18, 0x58, 0x07, 0x66,   
               0x80, 0x9C, 0x56, 0x37, 0x68, 0x1E, 0xB7, 0x90, 0xDA, 0xB5, 0x22, 0xCC, 0x07, 0x54, 0xFD, 0x48,   
               0xD6, 0x3B, 0x79, 0x39, 0x4C, 0xDC, 0x2D, 0xAD, 0x52, 0x2F, 0x71, 0x10, 0xE9, 0x37, 0x7E, 0x01,   
               0x56, 0x3B, 0x90, 0xCA, 0xD3, 0x38, 0xAA, 0x87, 0xD3, 0x2F, 0xB2, 0xE4, 0x21, 0x6A, 0xA4, 0x35,   
               0xE5, 0xDB, 0xDC, 0x60, 0xDC, 0x82, 0xF9, 0x03, 0x55, 0xD7, 0x23, 0x16, 0x7D, 0x03, 0xC7, 0xD8,   
               0x51, 0xBE, 0x58, 0x58, 0x52, 0xFB, 0x3A, 0x5E, 0xF7, 0xC9, 0x23, 0x24, 0xD4, 0x2A, 0xA3, 0x55,   
               0x25, 0xDB, 0x78, 0xA4, 0x36, 0xB6, 0xCD, 0xAC, 0x9A, 0x2A, 0x61, 0xC1, 0xE1, 0x36, 0x21, 0x3C,   
               0x1F, 0xC5, 0x08, 0x8D, 0x7D, 0x3D, 0x02, 0x03, 0x01, 0x00, 0x01, 0x74, 0x00, 0x05, 0x58, 0x2E,   
               0x35, 0x30, 0x39, 0x7E, 0x72, 0x00, 0x19, 0x6A, 0x61, 0x76, 0x61, 0x2E, 0x73, 0x65, 0x63, 0x75,   
               0x72, 0x69, 0x74, 0x79, 0x2E, 0x4B, 0x65, 0x79, 0x52, 0x65, 0x70, 0x24, 0x54, 0x79, 0x70, 0x65,   
               0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x12, 0x00, 0x00, 0x78, 0x72, 0x00, 0x0E, 0x6A,   
               0x61, 0x76, 0x61, 0x2E, 0x6C, 0x61, 0x6E, 0x67, 0x2E, 0x45, 0x6E, 0x75, 0x6D, 0x00, 0x00, 0x00,   
               0x00, 0x00, 0x00, 0x00, 0x00, 0x12, 0x00, 0x00, 0x78, 0x70, 0x74, 0x00, 0x06, 0x50, 0x55, 0x42,   
               0x4C, 0x49, 0x43
               };

//	==================================================================
void mensaje()
//	==================================================================
{
	HWND hWnd = FindWindow("Mobisys","");
	MessageBox( hWnd, 
				 "La licencia de su aplicación no es valida o ha caducado\npongase en contacto con su distribuidor",
				"Licencia caducada", MB_OK| MB_ICONEXCLAMATION) ;
}

//	==================================================================
void destroy( JNIEnv *env )
//	==================================================================
{
	JavaVM *jvm = NULL;
	(*env)->GetJavaVM( env, &jvm);
		(*jvm)->AttachCurrentThread(jvm, *env, NULL);
		*env = NULL;
		mensaje();
		exit(0);
}
/*Comprueba la validez de la fecha de la licencia*/
//	==================================================================
	JNIEXPORT void JNICALL Java_system_Cargador_a5(JNIEnv *env,	jclass object)
//	==================================================================
{
	/*Fecha de validez de la licencia
	private long a2;
	Fecha de alta de la licencia
	private long a6;
	*/
	jmethodID id = NULL;
	jclass cargador, calendar = NULL;
	jobject ob = NULL;
	
	jlong long2, long6, time;
	jfieldID a2, a6 = NULL;

	calendar = (*env)->FindClass(env,"java/util/Calendar");
	id = (*env)->GetStaticMethodID( env, calendar ,"getInstance","()Ljava/util/Calendar;");
	ob = (*env)->CallStaticObjectMethod(env, calendar, id );
	id = (*env)->GetMethodID( env, calendar ,"getTimeInMillis","()J");
	time = (*env)->CallLongMethod(env, ob, id );

	//referencio cargador y saco lo fieldIds de los campos a rellenar
	cargador = (*env)->GetObjectClass(env,object);
	a2 = (*env)->GetFieldID(env,cargador,"a2","J");
	a6 = (*env)->GetFieldID(env,cargador,"a6","J");

	long2 = (*env)->GetLongField(env, object, a2);
	long6 = (*env)->GetLongField(env, object, a6);
	
	if( long2 > long6 && long2 > time && long6 < time ){
		//printf("la licencia ok");
	}else{
		destroy( env );
	}
}
//	==================================================================
JNIEXPORT jstring JNICALL Java_system_Cargador_get(JNIEnv *env, jclass object, jint in)
//	==================================================================
{
	jclass cargador = NULL;
	jfieldID campo = NULL;

	cargador = (*env)->GetObjectClass(env,object);

	in = in % 6;
	switch(in){

		case 3:
			campo = (*env)->GetFieldID(env,cargador,"a3","Ljava/lang/String;");
			break;
		case 4:
			campo = (*env)->GetFieldID(env,cargador,"a4","Ljava/lang/String;");
			break;
		case 5:
			campo = (*env)->GetFieldID(env,cargador,"a5","Ljava/lang/String;");
			break;

	}
	if( campo != NULL )
		return (*env)->GetObjectField(env, object, campo);
	else 
		return NULL;
}
//	==================================================================
	JNIEXPORT void JNICALL Java_system_Cargador_a4(JNIEnv *env,	jclass object)
//	==================================================================
{
	jclass cargador,claseString = NULL;
	jmethodID id = NULL;
	jfieldID a1 = NULL;
	jdouble codigo;

	char volumeNameBuffer[MAX_PATH];
	DWORD lVolumeSerialNumber=0;
	DWORD lMaximumComponentLength=0;
	DWORD lFileSystemFlags=0;
	char fileSystemNameBuffer[10];
	unsigned long out;

	jstring s;

	//referencio cargador 
	cargador = (*env)->GetObjectClass(env,object);
	a1 = (*env)->GetFieldID(env,cargador,"a1","D");

	codigo = (*env)->GetDoubleField(env, object, a1);

	id = (*env)->GetMethodID( env, cargador ,"getPath","()Ljava/lang/String;");

	s = (*env)->CallObjectMethod(env, object, id );

	if(GetVolumeInformation( (*env)->GetStringUTFChars(env,s,0),volumeNameBuffer,MAX_PATH,&lVolumeSerialNumber,&lMaximumComponentLength,&lFileSystemFlags,fileSystemNameBuffer,10))
	{
		out = lVolumeSerialNumber;
	}

	if( out != codigo ){
		destroy( env );
		//(*jvm)->DestroyJavaVM(jvm);
	}
}
//	==================================================================
	JNIEXPORT void JNICALL Java_system_Cargador_a3(JNIEnv *env,	jclass object)
//	==================================================================
{
	jclass cargador,st,Double,Long = NULL;
	jmethodID id,id2 = NULL;
	jfieldID a1,a2,a3,a4,a5,a6 = NULL;
	jobject sto = NULL;

	jstring tmp;

	//referencio cargador y saco lo fieldIds de los campos a rellenar
	cargador = (*env)->GetObjectClass(env,object);
	id2 = (*env)->GetMethodID( env, cargador ,"getData","()Ljava/lang/String;");
	
	//referenco string tokenizer
	st = (*env)->FindClass(env,	"java/util/StringTokenizer");
	id = (*env)->GetMethodID( env, st ,"<init>","(Ljava/lang/String;Ljava/lang/String;)V");
	sto = (*env)->NewObject(env, st, id, (*env)->CallObjectMethod(env, object, id2 ), (*env)->NewStringUTF(env,"#"));
	// referencio nextoken 
	
	id = (*env)->GetMethodID( env, st ,"nextToken","()Ljava/lang/String;");

	a1 = (*env)->GetFieldID(env,cargador,"a1","D");
	a2 = (*env)->GetFieldID(env,cargador,"a2","J");
	a3 = (*env)->GetFieldID(env,cargador,"a3","Ljava/lang/String;");
	a4 = (*env)->GetFieldID(env,cargador,"a4","Ljava/lang/String;");
	a5 = (*env)->GetFieldID(env,cargador,"a5","Ljava/lang/String;");
	a6 = (*env)->GetFieldID(env,cargador,"a6","J");
	
	
	//a5
	(*env)->SetObjectField(env, object, a5, (*env)->CallObjectMethod(env, sto, id ) );
	//a1
	Double = (*env)->FindClass(env,	"java/lang/Double");
	id2 = (*env)->GetStaticMethodID( env, Double ,"parseDouble","(Ljava/lang/String;)D");
	tmp = (*env)->CallObjectMethod(env, sto, id );
	(*env)->SetDoubleField(env, object, a1, (*env)->CallStaticDoubleMethod(env, Double, id2, tmp ) );
	
	Long = (*env)->FindClass(env, "java/lang/Long");
	id2 = (*env)->GetStaticMethodID( env, Long ,"parseLong","(Ljava/lang/String;)J");
	//a6
	tmp = (*env)->CallObjectMethod(env, sto, id );
	(*env)->SetLongField(env, object, a6, (*env)->CallStaticLongMethod(env, Long, id2, tmp ) );
	//a2
	tmp = (*env)->CallObjectMethod(env, sto, id );
	(*env)->SetLongField(env, object, a2, (*env)->CallStaticLongMethod(env, Long, id2, tmp ) );
	// a3,a4
	(*env)->SetObjectField(env, object, a3, (*env)->CallObjectMethod(env, sto, id ) );
	(*env)->SetObjectField(env, object, a4, (*env)->CallObjectMethod(env, sto, id ) );
	/*
	StringTokenizer st = new StringTokenizer(out,"#");
		a5 = st.nextToken();
		a1 = st.nextToken();
		a6 = st.nextToken();
		a2 = st.nextToken();
		a3 = st.nextToken();
		a4 = st.nextToken();
	*/
}
//	==================================================================
	JNIEXPORT jobject JNICALL Java_system_Cargador_a1(JNIEnv *env,	jclass object)
//	==================================================================
{	   
		   
	jbyteArray jb,buffer =	NULL;

	jclass is =	NULL;
	jmethodID id, id2 = NULL;
	jobject baInputStream,oInputStream,oFileInputStream,pubKey,cipher = NULL;



	jb=(*env)->NewByteArray(env, 419);
	(*env)->SetByteArrayRegion(env,	jb,	0, 419,	(jbyte *)rawData);

	is = (*env)->FindClass(env,	"java/io/ByteArrayInputStream");
	id = (*env)->GetMethodID( env,is ,"<init>","([B)V");
	baInputStream = (*env)->NewObject(env, is, id, jb);
	/*
	public java.io.ObjectInputStream(java.io.InputStream)   throws java.io.IOException;
	Signature: (Ljava/io/InputStream;)V		*/
	is = (*env)->FindClass(env,	"java/io/ObjectInputStream");
	id = (*env)->GetMethodID( env,is ,"<init>","(Ljava/io/InputStream;)V");
	oInputStream = (*env)->NewObject(env, is, id, baInputStream);

	/*
	public final java.lang.Object readObject()   throws java.io.IOException, java.lang.ClassNotFoundException;
	Signature: ()Ljava/lang/Object;		*/
	id = (*env)->GetMethodID( env,is ,"readObject","()Ljava/lang/Object;");
	pubKey = (*env)->CallObjectMethod( env,oInputStream, id);

//Hasta aquí funciona perfectamente !!!!!!!

	/* public java.io.FileInputStream(java.lang.String)   throws java.io.FileNotFoundException;
	 Signature: (Ljava/lang/String;)V	*/
	is = (*env)->FindClass(env,	"java/io/FileInputStream");
	id = (*env)->GetMethodID( env,is ,"<init>","(Ljava/lang/String;)V");
	oFileInputStream = (*env)->NewObject(env, is, id, (*env)->NewStringUTF(env,"licencia.lic"));
	
	buffer=(*env)->NewByteArray(env, 128);
	
	id = (*env)->GetMethodID( env,is ,"read","()I");
	(*env)->CallVoidMethod( env, oFileInputStream, id, buffer );


	/*Cipher.getInstance("RSA/ECB/PKCS1Padding");
	Signature: (Ljava/lang/String;)Ljavax/crypto/Cipher;	*/
	is = (*env)->FindClass(env,	"javax/crypto/Cipher");
	id = (*env)->GetStaticMethodID( env,is ,"getInstance","(Ljava/lang/String;)Ljavax/crypto/Cipher;");
	cipher = (*env)->CallStaticObjectMethod( env, is, id, (*env)->NewStringUTF(env,"RSA/ECB/PKCS1Padding"));
	
	/*rsaCipher2.init(Cipher.DECRYPT_MODE, getData());	
	Signature: (ILjava/security/Key;)V	*/
	id = (*env)->GetMethodID( env,is ,"init","(ILjava/security/Key;)V");
	(*env)->CallVoidMethod( env, cipher, id, 2, pubKey );
	

	/*public final byte[] doFinal()   throws javax.crypto.IllegalBlockSizeException, javax.crypto.BadPaddingException;
	Signature: ()[B	*/
	//id = (*env)->GetMethodID( env,is ,"doFinal","()[B");
	//jb = (*env)->CallObjectMethod( env, cipher, id, buffer );

	return cipher;
}
//	==================================================================
	JNIEXPORT jbyteArray JNICALL Java_system_Cargador_a2(JNIEnv *env,	jclass object)
//	==================================================================
{
	FILE *f = NULL;
	byte buffer[128];
	jbyteArray ca = NULL;


	f = fopen("licencia.lic","r");
	if( f == NULL)
		destroy( env );
	fread (buffer,sizeof( byte ),128,f);
	fclose(f);
	ca=(*env)->NewByteArray(env, 128);
	(*env)->SetByteArrayRegion(env, ca, 0, 128, (jbyte *)buffer);
	free( buffer );
	return ca;
}


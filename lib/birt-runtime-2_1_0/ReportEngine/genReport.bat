ECHO off

IF not "%BIRT_HOME%" == "" GOTO runBirt
   ECHO "Please set BIRT_HOME first."
   GOTO end
:runBirt   

REM set the birt class path.
SET BIRTCLASSPATH=%BIRT_HOME%\ReportEngine\lib\commons-cli-1.0.jar;%BIRT_HOME%\ReportEngine\lib\commons-codec-1.3.jar;%BIRT_HOME%\ReportEngine\lib\com.ibm.icu_3.4.4.1.jar;%BIRT_HOME%\ReportEngine\lib\coreapi.jar;%BIRT_HOME%\ReportEngine\lib\dteapi.jar;%BIRT_HOME%\ReportEngine\lib\engineapi.jar;%BIRT_HOME%\ReportEngine\lib\js.jar;%BIRT_HOME%\ReportEngine\lib\modelapi.jar;%BIRT_HOME%\ReportEngine\flute.jar;%BIRT_HOME%\ReportEngine\lib\sac.jar;



REM set command
SET JAVACMD=java

set p1=%1
set p2=%2
set p3=%3
set p4=%4
set p5=%5
set p6=%6
set p7=%7
set p8=%8
set p9=%9
shift
set p10=%9
shift
set p11=%9
shift
set p12=%9
shift
set p13=%9
shift
set p14=%9
shift
set p15=%9

%JAVACMD% -cp "%BIRTCLASSPATH%" -DBIRT_HOME="%BIRT_HOME%\ReportEngine" org.eclipse.birt.report.engine.api.ReportRunner %p1% %p2% %p3% %p4% %p5% %p6% %p7% %p8% %p9% %p10% %p11% %p12% %p13% %p14% %p15%

:end
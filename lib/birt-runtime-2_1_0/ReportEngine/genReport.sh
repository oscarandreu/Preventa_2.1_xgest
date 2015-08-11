if [ "$BIRT_HOME" == "" ]; 
then 
  echo " The BIRT_HOME need be set before BirtRunner can run.";
else
  
  export BIRTCLASSPATH="$BIRT_HOME\ReportEngine\lib\commons-cli-1.0.jar:$BIRT_HOME\ReportEngine\lib\commons-codec-1.3.jar:$BIRT_HOME\ReportEngine\lib\com.ibm.icu_3.4.4.1.jar:$BIRT_HOME\ReportEngine\lib\coreapi.jar:$BIRT_HOME\ReportEngine\lib\dteapi.jar:$BIRT_HOME\ReportEngine\lib\engineapi.jar:$BIRT_HOME\ReportEngine\lib\js.jar:$BIRT_HOME\ReportEngine\lib\modelapi.jar:$BIRT_HOME\ReportEngine\flute.jar:$BIRT_HOME\ReportEngine\lib\sac.jar"

  JAVACMD='java';

  $JAVACMD -cp "$BIRTCLASSPATH" -DBIRT_HOME="$BIRT_HOME/ReportEngine" org.eclipse.birt.report.engine.api.ReportRunner $1 $2 $3 $4 $5 $6 $7 $8 $9 ${10} ${11} ${12} ${13} ${14} ${15}

fi

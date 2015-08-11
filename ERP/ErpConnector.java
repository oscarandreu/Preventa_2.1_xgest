package ERP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import system.Cargador;
import system.Configuracion;
import system.SysData;
import Gui.GuiConfiguracion;
import Gui.system.ErrorPane;

public class ErpConnector extends Exception{

	
	protected Exception excepcion;
	public boolean cancelado = false;
	protected SysData sys  = null;;
	
	// ==================================================================
	public ErpConnector(SysData sys )
	// ==================================================================
	{
		this.sys = sys;
	}
	// ==================================================================
	public void crearConexionErp( ) throws Exception 
	// ==================================================================
	{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Configuracion conf = sys.getConfiguracion();
            
//          Cargo la ruta al ERP
            String empresa = conf.getEmpresa();
            //Si la ruta no esta es la primera conexion
            if (empresa == null || "".equals(empresa)) {
//            	 indico que se importaran datos de una empresa al no exisitir fichero de configuracion
            	sys.setInicio(true);
            	//Mientras no defina una empresa
            	while(empresa == null || "".equals(empresa)){
            		getGuiConfiguracion(); //pido la configuracion
            		try{
            			
            			Connection c = getConexionErp();
            			Statement s =  c.createStatement();
            			ResultSet rs = s.executeQuery("SHOW tables"); 
            			
            			SelectEmpresa st = new SelectEmpresa( rs,conf );
                		empresa = st.getEmpresa();
                		if ( !(empresa == null && "".equals(empresa)) ) {
                			//Si se elige empresa y todo va bien se continua con la carga
                			Cargador.loader.setVisible(true);
                			SysData.setEmpresa(empresa);
                			conf.setEmpresa( empresa );
                			sys.setConfiguracion( conf );
                		}
            		}catch( Exception e ){
            			new ErrorPane("Los datos suministrados no son correctos:" +
            					"\nCompruebe que la dirección del servidor es correcta." +
            					"\nCompruebe que las credenciales suministradas son correctas.");
            			e.printStackTrace();
            		}
            	}
            }
	}
	
	/**Metodo que llama a la ventana de configuracion del servidor*/
	// ==================================================================
	protected void getGuiConfiguracion() throws Exception
	// ==================================================================
	{
		GuiConfiguracion guiConfiguracion = new GuiConfiguracion( sys, this );
		Cargador.loader.setVisible(false);
		guiConfiguracion.start();
		guiConfiguracion.join();
		if ( cancelado ) {
			throw excepcion;
		}
	}
	
	// ==================================================================
	public Connection getConexionErp() throws SQLException
	// ==================================================================
	{
		Connection conexion = null;
		Configuracion conf = sys.getConfiguracion();
		//Los datos de conexion deverian salir 
		//getConnection("jdbc:mysql://localhost/x?user=x&password=");
		conexion = DriverManager.getConnection(
				"jdbc:mysql://"+conf.getRuta()+
				"/x?user="+conf.getUser()+
				"&password="+conf.getPassword());

		return conexion;
	}
}

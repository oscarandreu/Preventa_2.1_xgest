package ERP;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import log.LogWriter;
import system.Cargador;
import system.Configuracion;
import system.SysData;
import Gui.system.Loader;
import data.Agente;


public class ActualizarDatosFromErp extends Thread {

	protected SysData sys;

	protected Connection cErp = null; // conexion que haremos con tecnopolis

	protected Connection c = null; // Conexion con nuestra bbdd

	protected Configuracion conf = null;

	protected Cargador cargador;


	/** Tiempo de espera entre acciones */

	// variable usada para diferenciar la primera vez que se ejecuta el Thread 
	protected boolean primeraVez = false;
	
	// Ventana que notifica el proceso activo de importacion de datos
	public Loader loader;
	
	// ==================================================================
	public ActualizarDatosFromErp(Cargador cargador) throws Exception
	// ==================================================================
	{
		this.sys = cargador.sys;
		this.cargador = cargador;
		conf = sys.getConfiguracion();
		cErp = sys.getConexionErp();
		c = sys.getConexionPropia();
		// Obligo a realizar una comprobacion nada mas comenzar la aplicacion
		// conf.setActualizacionArticulos( null );
		// conf.setActualizacionClientes( null );
		
		//loader = new Loader("Importando datos...");
		
		
	}
	
	

	// ==================================================================
	public void run()
	// ==================================================================
	{
		
		// Si se ha elegido empresa para importar datos al no existir fichero de configuracion
		if ( cargador.getSys().isInicio() ){
			
			cargador.getSys().setInicio(false);
			primeraVez = true;
		}
		
		if (primeraVez){
			//primera vez que se ejecuta el Thread de importar, y ademas sin fichero de configuracion
		//	loader = new Loader("Importando datos...");
		}
		while (true) {
			actualizarAgentes();
			dormir(6000);// Cada minuto
		}
	}


	// ==================================================================
	public int actualizarAgentes()
	// ==================================================================
	{
		int out = 0;
		String query = "SELECT rcod codigo, rnom nombre"
				+ " FROM FCREP" + SysData.getEmpresa();

		try {
			
			Statement s = c.createStatement();
			Statement sErp = cErp.createStatement();
			ResultSet rsErp = sErp.executeQuery(query);
			
			while (rsErp.next()) {
			
				Agente ag = new AgenteErp();
				// Compruebo si es una actualizacion o un nuevo articulo
				query = "SELECT codigo,nombre FROM agentes WHERE codigo ='"
						+ rsErp.getString("CODIGO") + "'";
				ResultSet rsPropio = s.executeQuery(query);
				if (rsPropio.next()) {
					if (!rsPropio.getString("nombre").equals(
							rsErp.getString("nombre"))) {
						// Ha habido cambios en el agente
						ag.setAgente(rsErp);
						ag.actualizarAgente(s);
					}
					// Es una actualizacion
				} else {
					// Es un nuevo agente
					ag.setAgente(rsErp);
					ag.setSerie( sErp );
					ag.insertarAgente(s);
				}
			}
			rsErp.close();
			sErp.close();
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
			out = 1;
		}

		return out;
	}


	// ==================================================================
	protected void dormir(int intervalo)
	// ==================================================================
	{
		try {
			sleep(intervalo);
		} catch (InterruptedException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}
}
package bbdd;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import log.LogWriter;

import org.firebirdsql.pool.FBWrappingDataSource;

/**
 * Clase que se encarga de realizar las conexiones con el driver Jaybird para
 * base de datos firebird
 */
public class JaybirdConnector {

	private FBWrappingDataSource dataSource;

	private FBWrappingDataSource dataSourceErp;	//dataSourceTecno

	
//==================================================================
	public JaybirdConnector() {}
// ==================================================================


	// ==================================================================
	public Connection getConexionPropia()
	// ==================================================================
	{
		Connection conexion = null;
		try {
			conexion = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
		return conexion;
	}

	// ==================================================================
	public Connection getConexionErp()
	// ==================================================================
	{
		Connection conexion = null;
		try {
			conexion = dataSourceErp.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
		return conexion;
	}

	// ==================================================================
	private FBWrappingDataSource getOrigenDatos(String host, String puerto,
			String ruta)
	// ==================================================================
	{
		FBWrappingDataSource dataSource = new org.firebirdsql.pool.FBWrappingDataSource();
		dataSource = new org.firebirdsql.pool.FBWrappingDataSource();
		dataSource.setDatabase(host + "/" + puerto + ":" + ruta);// localhost/3050:"+ruta);
		dataSource.setUserName("sysdba");
		dataSource.setPassword("masterkey");
		dataSource.setPooling(true); // this turns on pooling for this data
										// source. Max and min must be set.
		dataSource.setMinPoolSize(5); // this sets the minimum number of
										// connections to keep in the pool
		dataSource.setMaxPoolSize(30); // this sets the maximum number of
										// connections that can be open at one
										// time.
		dataSource.setLoginTimeout(1);
		dataSource.setMaxStatements(Integer.MAX_VALUE);
		dataSource.setBlockingTimeout(1);
		dataSource.setMaxPoolSize(Integer.MAX_VALUE);
		dataSource.setMaxIdleTime(Integer.MAX_VALUE);
		dataSource.setType("TYPE4");
		dataSource.setEncoding("NONE");
		return dataSource;
	}

	// ==================================================================
	public void conectarErp(String ruta)
	// ==================================================================
	{
		dataSourceErp = getOrigenDatos("127.0.0.1", "3050", ruta);
	}

	/**
	 * Clase que crea el origen de datos para la conexion con la bbdd de nuestra
	 * aplicacion
	 */
	// ==================================================================
	public void conexionPropia()
	// ==================================================================
	{
		String out = new String();
		File descriptor = new File(".");
		String rutaTmp = new String(descriptor.getAbsolutePath());
		out = rutaTmp.substring(0, rutaTmp.length() - 1);
		out = (out + "bbdd" + File.separatorChar + "database.fdb");
		dataSource = getOrigenDatos("127.0.0.1", "3050", out);
	}

}

package bbdd;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

import org.firebirdsql.pool.FBWrappingDataSource;

import system.SysData;
import ERP.ErpConnector;

/**
 * Clase que se encarga de realizar las conexiones con el driver Jaybird para
 * base de datos firebird
 */
public class BbddConnector extends ErpConnector{
	
	private FBWrappingDataSource dataSource;
	
	
	
//	==================================================================
	public BbddConnector(SysData sys )
//	==================================================================
	 {
		super(sys); 
	 }
	
// ==================================================================
	public Connection getConexionPropia() throws SQLException
// ==================================================================
	{
		return dataSource.getConnection();
	}
	

	
	// ==================================================================
	private FBWrappingDataSource getOrigenDatos(String host, String puerto,
			String ruta)
	// ==================================================================
	{
		FBWrappingDataSource dataSource1 = new org.firebirdsql.pool.FBWrappingDataSource();
		dataSource1 = new org.firebirdsql.pool.FBWrappingDataSource();
		dataSource1.setDatabase(host + "/" + puerto + ":" + ruta);// localhost/3050:"+ruta);
		dataSource1.setUserName("sysdba");
		dataSource1.setPassword("masterkey");
		dataSource1.setPooling(true); // this turns on pooling for this data
		// source. Max and min must be set.
		dataSource1.setMinPoolSize(5); // this sets the minimum number of
		// connections to keep in the pool
		dataSource1.setMaxPoolSize(30); // this sets the maximum number of
		// connections that can be open at one
		// time.
		dataSource1.setLoginTimeout(1);
		dataSource1.setMaxStatements(Integer.MAX_VALUE);
		dataSource1.setBlockingTimeout(1);
		dataSource1.setMaxPoolSize(Integer.MAX_VALUE);
		dataSource1.setMaxIdleTime(Integer.MAX_VALUE);
		dataSource1.setType("TYPE4");
		dataSource1.setEncoding("NONE");
		return dataSource1;
	}
	

	
	/**
	 * Clase que crea el origen de datos para la conexion con la bbdd de nuestra
	 * aplicacion
	 */
	// ==================================================================
	public void crearConexionPropia() throws Exception
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

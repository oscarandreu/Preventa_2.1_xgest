package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Iterator;

import log.LogWriter;
import system.SysData;

public abstract class Cobro {
	
	/* NOTA: Habra que crear en nuestra BDD local una Tabla "COBRO"
	 * */
	
	public long numeroCobro;
	
	public int codCliente;
	
	public int codCobrador;
	
	public long numeroDocumento;
	
	public String fecha;
	
	public String fechaVto;
	
	public String fechaCobro;
	
	//public int total;
	
	public String formaPago;
	
	public double cobrado;
	
	public double pendiente;
	
	public String observaciones;
	
	// ==================================================================
	public Cobro()
	// ==================================================================
	{
		
	}
	
	/**
	 * Metodo que toma el string de entrada desde la pda con el cobro y lo
	 * introduce en la bbdd
	 */
	// ==================================================================
	public boolean setCobroFromPda(String in, SysData sys, String codAgente )
	// ==================================================================
	{
		boolean out = false;
		try {
			Iterator it = SysData.tokenizar(in);
		//	numeroCobro = SysData.setLong(it.next());
			numeroDocumento = SysData.setLong(it.next());
			formaPago = (String)it.next();
			cobrado = SysData.formatearDoubleFromPda((String)it.next());
			observaciones = (String)it.next();
			fechaCobro = (String)it.next();
			codCobrador = new Integer(codAgente);
			
			
			Connection cErp = sys.getConexionErp();
			Statement sErp = cErp.createStatement();
			String query = getQueryCobroFromErp(numeroDocumento);
			ResultSet rsErp = sErp.executeQuery(query);
			
			if (rsErp.next()){
				codCliente = rsErp.getInt("codCliente");
				fecha = rsErp.getString("fecha");
				fechaVto = rsErp.getString("fechaVto");
				pendiente = rsErp.getDouble("importe") - rsErp.getDouble("importeCobrado") - cobrado;
			}
			
			out = true;
		} catch (SQLException ex){
			ex.printStackTrace();
			new LogWriter( ex.getStackTrace() );
		} catch (Exception e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
		return out;
	}
	
/**	 Intenta insertar un cobro y si no lo consigue devuelve false
 * 
 * @param sPropio
 * @return
 */
	// ==================================================================
	public boolean insertarCobro(Statement sPropio)
	// ==================================================================
	{
		boolean out = false;
		int num = 0;
		try {
			ResultSet rs = sPropio.executeQuery("SELECT MAX(numero_cobro) FROM cobro");
			if (rs.next()){
				num = rs.getInt("max");
				num++;
			}
			
			String query = "INSERT INTO COBRO ("
					+ "NUMERO_COBRO,CODIGO_CLIENTE,CODIGO_AGENTE,NUMERO_DOCUMENTO,FECHA,FECHA_VTO,FECHA_COBRO," 
					+ "FORMA_PAGO,COBRADO,PENDIENTE,OBSERVACIONES"
					+ ") VALUES (" + "'" + num + "','" + codCliente + "','" + codCobrador + "'," 
					+ "'" + numeroDocumento + "'," + "'" + fecha + "'," + "'"
					+ fechaVto + "'," + "'" + fechaCobro + "'," + "'" + formaPago + "'," + "'" + cobrado + "',"
					+ "'" + pendiente + "'," + "'" + observaciones + "'" 
					+ ")";
			System.out.println(query);
			if (sPropio.executeUpdate(query) == 1)
				out = true;
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		} catch (Exception e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
		return out;

	}

	/** Elimina el cobro de la bbdd */
	// ==================================================================
	public boolean eliminarCobro(Statement sPropio)
	// ==================================================================
	{
		boolean out = false;
		String query = "DELETE FROM COBRO WHERE numero_cobro ='"
				+ numeroCobro + "'";
		try {
			if (sPropio.executeUpdate(query) == 1)
				out = true;
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
		return out;
	}

	// ==================================================================
	public void setCobro(ResultSet rs)
	// ==================================================================
	{
		try {
			numeroCobro = rs.getLong("NUMERO_COBRO");
			codCliente = rs.getInt("CODIGO_CLIENTE");
			codCobrador = rs.getInt("CODIGO_AGENTE");
			numeroDocumento = rs.getLong("NUMERO_DOCUMENTO");
			fecha = rs.getString("FECHA");
			fechaVto = rs.getString("FECHA_VTO");
			fechaCobro = rs.getString("FECHA_COBRO");
			formaPago = rs.getString("FORMA_PAGO");
			cobrado = rs.getDouble("COBRADO");
			pendiente = rs.getDouble("PENDIENTE");
			observaciones = rs.getString("OBSERVACIONES");
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}
	
//	 ==================================================================
	public void setCobroFromLocal(ResultSet rs)
	// ==================================================================
	{
		try {
			numeroCobro = rs.getLong("NUMERO_COBRO");
			codCliente = rs.getInt("CODIGO_CLIENTE");
			codCobrador = rs.getInt("CODIGO_AGENTE");
			numeroDocumento = rs.getLong("NUMERO_DOCUMENTO");
			fecha = rs.getString("FECHA");
			fechaVto = rs.getString("FECHA_VTO");
			fechaCobro = rs.getString("FECHA_COBRO");
			formaPago = rs.getString("FORMA_PAGO");
			cobrado = rs.getDouble("COBRADO");
			pendiente = rs.getDouble("PENDIENTE");
			observaciones = rs.getString("OBSERVACIONES");
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}
	
	// ==================================================================
	public long getNumeroDocumento()
	// ==================================================================
	{
		return numeroDocumento;
	}
	
	// ==================================================================
	public int getCodigoCliente()
	// ==================================================================
	{
		return codCliente;
	}
	
//	=============================================================================================================================	
	public abstract boolean insertarCobroErp(Statement s,Statement sErp);
//	=============================================================================================================================
	
//	=============================================================================================================================	
	public abstract String getQueryCobroFromErp(long numeroDocumento);
//	=============================================================================================================================
	
	
	
	
	

}

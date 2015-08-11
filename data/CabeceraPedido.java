package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Iterator;

import log.LogWriter;
import system.SysData;

public abstract class CabeceraPedido {

	public long numeroCabecera;

	public String serie;

	public char albaran;

	public char clase;

	public int codCliente;

	public double dto1;

	public double dto2;

	public double dtopp;

	public Timestamp fecha;

	public String fechaString;

	public String observaciones;

	public int codAgente;

	public char verificado;
	
	// total con iva
	public double total_iva;
	
	

	// ==================================================================
	public CabeceraPedido() {
	}

	// ==================================================================

	/** Consigo el siguiente numero de cabecera para este comercial */
	// ==================================================================
	public int siguienteNumeroPedido(Statement s, String cod_user)
	// ==================================================================
	{
		String query = "SELECT a.NUMERO_CABECERA FROM CABECERA_PEDIDO a, AGENTES b "
				+ "WHERE  b.codigo ='" + cod_user + "' AND a.SERIE = b.SERIE AND a.anyo = '" + Calendar.YEAR+"'";
		int nextNumeroPedido = 0;
		try {
			ResultSet rs = s.executeQuery(query);
			int i = 0;
			while (rs.next()) {
				i = rs.getInt("NUMERO_CABECERA");
				if (i > nextNumeroPedido)
					nextNumeroPedido = i;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
		if( nextNumeroPedido != 0 )
			nextNumeroPedido++;
		return nextNumeroPedido;
	}

	// ==================================================================
	public boolean actualizarCabeceraPedido(Statement sPropio)
	// ==================================================================
	{
		boolean out = false;
		try {
			String query = "UPDATE CABECERA_PEDIDO SET ";
	//		query += "NUMERO_CABECERA='" + numeroCabecera + "',";
	//		query += "SERIE=" + serie + ",";
			query += "ALBARAN='" + albaran + "',";
			query += "Clase='" + clase + "',";
			query += "Codigo_cliente='" + codCliente + "',";
			query += "Dto_1='" + dto1 + "',";
			query += "Dto_2='" + dto2 + "',";
			query += "Dto_pp='" + dtopp + "',";
			query += "fecha='" + fecha + "',";
			query += "observaciones='" + observaciones + "',";
			query += "codigo_agente ='" + codAgente + "',";
			query += "total_iva='" + total_iva + "',"; 
			 
			query += "verificado ='" + verificado + "'";
			query += " WHERE NUMERO_CABECERA = '" + numeroCabecera
					+ "' AND SERIE='" + serie + "'";

			if (sPropio.executeUpdate(query) == 1)
				out = true;
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
		return out;
	}

	/**
	 * Metodo que toma el string de entrada desde la pda con la cabecera y los
	 * introduce en la bbdd
	 */
	// ==================================================================
	public boolean setCabeceraFromPda(String in )
	// ==================================================================
	{
		boolean out = false;
		try {
			Iterator it = SysData.tokenizar(in);
			numeroCabecera = SysData.setLong(it.next());
			serie = (String)it.next();
			String tmp = (String)it.next();
			clase = tmp.charAt(0);
			codCliente = SysData.setInt(it.next());
			dto1 = SysData.formatearDoubleFromPda((String)it.next());
			dto2 = SysData.formatearDoubleFromPda((String)it.next());
			dtopp = SysData.formatearDoubleFromPda((String)it.next());
			observaciones = (String)it.next();
			fechaString = (String)it.next();
			verificado = 'S';
			total_iva = SysData.formatearDoubleFromPda((String)it.next());
			System.out.println("total iva:" + total_iva);
			out = true;
		
		} catch (Exception e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
		return out;
	}

	// Intenta insertar una cabecera de pedido y si no lo consigue devuelve
	// false
	// ==================================================================
	public boolean insertarCabeceraPedido(Statement sPropio)
	// ==================================================================
	{
		boolean out = false;
		try {
			String query = "INSERT INTO CABECERA_PEDIDO ("
					+ "NUMERO_CABECERA,SERIE,ANYO,ALBARAN,Clase,Codigo_cliente,Dto_1,Dto_2,Dto_pp,FECHA,observaciones,verificado,"
					+ "TOTAL_IVA"
					+ ") VALUES (" + "'" + numeroCabecera + "'," + "'" + serie
					+ "'," + "'2006'," + "'" + albaran + "'," + "'" + clase + "'," + "'"
					+ codCliente + "'," + "'" + dto1 + "'," + "'" + dto2 + "',"
					+ "'" + dtopp + "'," + "'" + fechaString + "'," + "'"
					+ observaciones + "'," + "'" + verificado + "'," 
					+ "'" + total_iva + "'"
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

	/** Elimina la cebcera de la bbdd */
	// ==================================================================
	public boolean eliminarCabeceraPedido(Statement sPropio)
	// ==================================================================
	{
		boolean out = false;
		String query = "DELETE FROM CABECERA_PEDIDO WHERE numero_cabecera ='"
				+ numeroCabecera + "'";
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
	public void setCabeceraPedido(ResultSet rs)
	// ==================================================================
	{
		try {
			numeroCabecera = rs.getLong("NUMERO_CABECERA");
			serie = rs.getString("SERIE");
			albaran = rs.getString("ALBARAN").charAt(0);
			clase = rs.getString("CLASE").charAt(0);
			codCliente = rs.getInt("Codigo_cliente");
			dto1 = rs.getDouble("DTO_1");
			dto2 = rs.getDouble("DTO_2");
			dtopp = rs.getDouble("DTO_PP");
			fecha = rs.getTimestamp("FECHA");
			fechaString = fecha.toString();
			observaciones = rs.getString("OBSERVACIONES");
			codAgente = rs.getInt("codigo_agente ");
			total_iva = rs.getDouble("TOTAL__IVA");
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}
	
//	 ==================================================================
	public void setCabeceraPedidoFromLocal(ResultSet rs)
	// ==================================================================
	{
		try {
			numeroCabecera = rs.getLong("NUMERO_CABECERA");
			serie = rs.getString("SERIE");
			albaran = rs.getString("ALBARAN").charAt(0);
			clase = rs.getString("CLASE").charAt(0);
			codCliente = rs.getInt("Codigo_cliente");
			dto1 = rs.getDouble("DTO_1");
			dto2 = rs.getDouble("DTO_2");
			dtopp = rs.getDouble("DTO_PP");
			fecha = rs.getTimestamp("FECHA");
			observaciones = rs.getString("OBSERVACIONES");
			total_iva = rs.getDouble("TOTAL_IVA");
			fechaString = fecha.toString();
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}
	
//	=============================================================================================================================	
	public abstract boolean insertarPedidoERP(Statement s,Statement sErp,String serieArg,String anyoArg,String numPedArg,String codCliArg);
//	=============================================================================================================================
	
//	==================================================================
	public int getCodCliente()
//	==================================================================
	{
		return codCliente;
	}

//	 ==================================================================
	public long getNumeroCabecera()
//	 ==================================================================
	{
		return numeroCabecera;
	}
	
	
}

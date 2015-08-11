package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import log.LogWriter;
import system.SysData;

public class LineaPedido {

	public int numeroLinea;

	public long numeroCabecera;

	public String codArticulo;

	public String serie;

	public int tarifas;

	public double cajas;

	public double unidades;

	public double dto1;

	public double dto2;

	public double dto3;

	public String observaciones;

	// total neto sin iva
	public double total;
	
	// total con iva
	public double total_iva;
	
	// ==================================================================
	public LineaPedido() {
	}

	// ==================================================================

	/**
	 * Metodo que toma l string de entrada desde la pda con la cabecera y los
	 * introduce en la bbdd
	 */
	// ==================================================================
	public boolean setLineaFromPda(String in, String serie)
	// ==================================================================
	{
		boolean out = false;
		try {
			Iterator it = SysData.tokenizar(in);
			numeroLinea = SysData.setInt(it.next());
			numeroCabecera = SysData.setLong(it.next());
			codArticulo = (String) it.next();
			tarifas = SysData.setInt(it.next());
			cajas = formatearDouble((String)it.next());
			unidades = formatearDouble((String)it.next());
			dto1 = formatearDouble((String)it.next());
			observaciones = (String) it.next();
			total = formatearDouble((String)it.next());
			total_iva = formatearDouble((String)it.next()); 
			System.out.println("total:" + total + " total iva:" + total_iva);
			this.serie = serie;
			out = true;
		} catch (Exception e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
		return out;
	}

	// ==================================================================
	public double formatearDouble(String in) throws Exception
	// ==================================================================
	{
		return Double.parseDouble(in.replace(',', '.'));
	}

	// ==================================================================
	public void actualizarLineaPedido(Statement sPropio)
	// ==================================================================
	{
		try {
			String query = "UPDATE LINEA_PEDIDO SET ";
	//		query += "NUMERO_LINEA='" + numeroLinea + "',";
	//		query += "NUMERO_CABECERA='" + numeroCabecera + "',";
			query += "CODIGO_ARTICULO='" + codArticulo + "',";
			query += "TARIFAS='" + tarifas + "',";
			query += "CAJAS='" + cajas + "',";
			query += "UNIDADES='" + unidades + "',";
			query += "Dto_1='" + dto1 + "',";
			query += "Dto_2='" + dto2 + "',";
			query += "Dto_3='" + dto3 + "',";
			
			query += "total='" + total + "',";
			query += "total_iva='" + total_iva + "',"; 
			
			query += "observaciones='" + observaciones + "'";

			query += " WHERE NUMERO_CABECERA = '" + numeroCabecera
					+ "' AND NUMERO_LINEA='" + numeroLinea + "'";

			sPropio.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}

	// ==================================================================
	public boolean insertarLineaPedido(Statement sPropio)
	// ==================================================================
	{
		boolean out = false;
		try {
			String query = "INSERT INTO LINEA_PEDIDO ("
					+ "NUMERO_LINEA,NUMERO_CABECERA,SERIE,ANYO,CODIGO_ARTICULO,TARIFAS,CAJAS,UNIDADES,Dto_1,Dto_2,Dto_3,observaciones,"
					+ "TOTAL,TOTAL_IVA"
					+ ") VALUES (" + "'" + numeroLinea + "'," + "'"
					+ numeroCabecera + "'," + "'" + serie + "'," + "'2006'," + "'"
					+ codArticulo + "'," + "'" + tarifas + "'," + "'" + cajas
					+ "'," + "'" + unidades + "'," + "'" + dto1 + "'," + "'"
					+ dto2 + "'," + "'" + dto3 + "'," + "'" + observaciones + "',"
					+ "'" + total +"'," + "'" + total_iva + "'"
					+ ")";
			System.out.println(query);
			sPropio.executeUpdate(query);
			out = true;
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
		return out;
	}

	// ==================================================================
	public boolean setLineaPedido(ResultSet rs)
	// ==================================================================
	{
		boolean out = false;
		try {
			numeroLinea = rs.getInt("NUMERO_LINEA");
			numeroCabecera = rs.getLong("NUMERO_CABECERA");
			codArticulo = rs.getString("CODIGO_ARTICULO");
			serie = rs.getString("SERIE");
			tarifas = rs.getInt("TARIFAS");
			cajas = rs.getDouble("CAJAS");
			unidades = rs.getDouble("UNIDADES");
			dto1 = rs.getDouble("DTO_1");
			dto2 = rs.getDouble("DTO_2");
			dto3 = rs.getDouble("DTO_3");
			observaciones = rs.getString("OBSERVACIONES");
			total = rs.getDouble("TOTAL");
			total_iva = rs.getDouble("TOTAL_IVA");
			out = true;
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
		return out;
	}
}

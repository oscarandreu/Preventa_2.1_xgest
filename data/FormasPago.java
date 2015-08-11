package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import log.LogWriter;

import system.SysData;

public class FormasPago {

	public String codigo;

	public String nombre;

	public short vencimientos;

	// ==================================================================
	public FormasPago() {
	}

	// ==================================================================

	// ==================================================================
	public void actualizarFormaPago(Statement sPropio )
	// ==================================================================
	{

		try {
			String query = "UPDATE FORMAS_PAGO SET ";
			// query += "CODIGO='" + codigo + "',";
			query += "NOMBRE=" + SysData.formatear(nombre) + ",";
			query += "VENCIMIENTOS='" + vencimientos + "'";
			query += " WHERE CODIGO = '" + codigo + "'";

			System.out.println(query);
			sPropio.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}

	}

	// ==================================================================
	public void insertarFormaPago( Statement sPropio )
	// ==================================================================
	{
		try {
			String query = "INSERT INTO FORMAS_PAGO ("
					+ "CODIGO,NOMBRE,VENCIMIENTOS" + ") VALUES (";
			query += "'" + codigo + "',";
			query += SysData.formatear(nombre) + ",";
			query += "'" + vencimientos + "'";
			query += ")";
			System.out.println(query);
			sPropio.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}

	// ==================================================================
	public void setFormaPago(ResultSet rs)
	// ==================================================================
	{
		try {
			codigo = rs.getString("CODIGO");
			nombre = rs.getString("NOMBRE");
			vencimientos = rs.getShort("VENCIMIENTOS");
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}
}

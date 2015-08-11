package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import log.LogWriter;

import system.SysData;

public class Secciones {

	public String codigo;

	public String descripcion;

	// ==================================================================
	public Secciones() {
	}

	// ==================================================================

	// ==================================================================
	public void actualizarSecciones(Statement sPropio )
	// ==================================================================
	{
		try {
			String query = "UPDATE SECCIONES SET ";
			// query += "CODIGO='" + codigo + "',";
			query += "DESCRIPCION=" + SysData.formatear(descripcion);
			query += " WHERE CODIGO = '" + codigo + "'";

			sPropio.executeUpdate(query);
			System.out.println("Actualizar secciones: " + query);

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}

	}

	// ==================================================================
	public void insertarSecciones(Statement sPropio )
	// ==================================================================
	{
		try {
			String query = "INSERT INTO SECCIONES (" + "CODIGO,DESCRIPCION"
					+ ") VALUES (";
			query += "'" + codigo + "',";
			query += SysData.formatear(descripcion);
			query += ")";

			sPropio.executeUpdate(query);
			System.out.println("Insertar secciones: " + query);

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}

	// ==================================================================
	public void setSecciones(ResultSet rs)
	// ==================================================================
	{
		try {
			codigo = rs.getString("CODIGO");
			descripcion = rs.getString("DESCRIPCION");
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}

}

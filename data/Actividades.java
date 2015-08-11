package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import log.LogWriter;

import system.SysData;

public class Actividades {
	public String codigo;

	public String descripcion;

	// ==================================================================
	public Actividades() {}
	// ==================================================================

	// ==================================================================
	public void actualizarActividades( Statement sPropio )
	// ==================================================================
	{
		try {
			String query = "UPDATE Actividades SET ";
			// query += "CODIGO='" + codigo + "',";
			query += "DESCRIPCION=" + SysData.formatear(descripcion);
			query += " WHERE CODIGO = '" + codigo + "'";

			System.out.println("Actualiza Actividades: " + query);
			sPropio.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}

	}

	// ==================================================================
	public void insertarActividades(Statement sPropio)
	// ==================================================================
	{
		try {
			String query = "INSERT INTO Actividades (" + "CODIGO,DESCRIPCION"
					+ ") VALUES (";
			query += "'" + codigo + "',";
			query += SysData.formatear(descripcion);
			query += ")";

			System.out.println("Inserta Actividades: " + query);
			sPropio.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}

	// ==================================================================
	public void setActividades(ResultSet rs)
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

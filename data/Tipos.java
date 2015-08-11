package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import log.LogWriter;

import system.SysData;

public class Tipos {
	public String codigo;

	public String descripcion;

	// ==================================================================
	public Tipos() {
	}

	// ==================================================================

	// ==================================================================
	public void actualizarTipos(Statement sPropio )
	// ==================================================================
	{
		try {
			String query = "UPDATE Tipos SET ";
			// query += "CODIGO='" + codigo + "',";
			query += "DESCRIPCION=" + SysData.formatear(descripcion);
			query += " WHERE CODIGO = '" + codigo + "'";

			System.out.println("Actualiza Tipos: " + query);
			sPropio.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}

	}

	// ==================================================================
	public void insertarTipos(Statement sPropio )
	// ==================================================================
	{
		try {
			String query = "INSERT INTO Tipos (" + "CODIGO,DESCRIPCION"
					+ ") VALUES (";
			query += "'" + codigo + "',";
			query += SysData.formatear(descripcion);
			query += ")";

			System.out.println("Inserta Tipos: " + query);
			sPropio.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}

	// ==================================================================
	public void setTipos(ResultSet rs)
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

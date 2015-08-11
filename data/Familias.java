package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import log.LogWriter;

import system.SysData;

public class Familias {
	public String codigo;

	public String descripcion;

	// ==================================================================
	public Familias() {
	}

	// ==================================================================

	// ==================================================================
	public void actualizarFamilias(Statement sPropio )
	// ==================================================================
	{
		try {
			String query = "UPDATE Familias SET ";
			// query += "CODIGO='" + codigo + "',";
			query += "DESCRIPCION=" + SysData.formatear(descripcion);
			query += " WHERE CODIGO = '" + codigo + "'";

			System.out.println("Actualiza familias: " + query);
			sPropio.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}

	}

	// ==================================================================
	public void insertarFamilias( Statement sPropio )
	// ==================================================================
	{
		try {
			String query = "INSERT INTO Familias (" + "CODIGO,DESCRIPCION"
					+ ") VALUES (";
			query += "'" + codigo + "',";
			query += SysData.formatear(descripcion);
			query += ")";

			System.out.println("Inserta familias: " + query);
			sPropio.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}

	// ==================================================================
	public void setFamilias(ResultSet rs)
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

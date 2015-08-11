package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import log.LogWriter;

import system.SysData;

public class Subfamilias {

	public String codigo;

	public String descripcion;

	// ==================================================================
	public Subfamilias() {
	}

	// ==================================================================

	// ==================================================================
	public void actualizarSubfamilias(Statement sPropio )
	// ==================================================================
	{
		try {
			String query = "UPDATE Subfamilias SET ";
			// query += "CODIGO='" + codigo + "',";
			query += "DESCRIPCION=" + SysData.formatear(descripcion);
			query += " WHERE CODIGO = '" + codigo + "'";

			sPropio.executeUpdate(query);
			System.out.println("Actualiza subfamilia: " + query);

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}

	}

	// ==================================================================
	public void insertarSubfamilias(Statement sPropio )
	// ==================================================================
	{
		try {
			String query = "INSERT INTO Subfamilias (" + "CODIGO,DESCRIPCION"
					+ ") VALUES (";
			query += "'" + codigo + "',";
			query += SysData.formatear(descripcion);
			query += ")";

			sPropio.executeUpdate(query);
			System.out.println("Insertar subfamilia: " + query);

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}

	// ==================================================================
	public void setSubfamilias(ResultSet rs)
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

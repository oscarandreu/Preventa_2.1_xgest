package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import log.LogWriter;

import system.SysData;

public class Rutas {

	public String codigo;

	public String descripcion;

	// ==================================================================
	public Rutas() {}
	// ==================================================================

	// ==================================================================
	public void actualizarRutas(Statement sPropio )
	// ==================================================================
	{
		try {
			String query = "UPDATE Rutas SET ";
			// query += "CODIGO='" + codigo + "',";
			query += "DESCRIPCION=" + SysData.formatear(descripcion);
			query += " WHERE CODIGO = '" + codigo + "'";

			System.out.println("Actualiza rutas: " + query);
			sPropio.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}

	}

	// ==================================================================
	public void insertarRutas(Statement sPropio )
	// ==================================================================
	{
		try {
			String query = "INSERT INTO Rutas (" + "CODIGO,DESCRIPCION"
					+ ") VALUES (";
			query += "'" + codigo + "',";
			query += SysData.formatear(descripcion);
			query += ")";

			System.out.println("Inserta ruta: " + query);
			sPropio.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}

	// ==================================================================
	public void setRutas(ResultSet rs)
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

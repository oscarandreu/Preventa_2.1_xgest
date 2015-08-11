package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import log.LogWriter;

import system.SysData;

public class TiposIncidencias {

	public String codigo;

	public String descripcion;

	// ==================================================================
	public TiposIncidencias() {
	}

	// ==================================================================

	// ==================================================================
	public void actualizarTiposIncidencias(Statement sPropio )
	// ==================================================================
	{
		try {
			String query = "UPDATE Tipos_Incidencias SET ";
			// query += "CODIGO='" + codigo + "',";
			query += "DESCRIPCION=" + SysData.formatear(descripcion);
			query += " WHERE CODIGO = '" + codigo + "'";

			System.out.println("Actualiza tipos incidencias: " + query);
			sPropio.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}

	}

	// ==================================================================
	public void insertarTiposIncidencias(Statement sPropio )
	// ==================================================================
	{
		try {
			String query = "INSERT INTO Tipos_Incidencias ("
					+ "CODIGO,DESCRIPCION" + ") VALUES (";
			query += "'" + codigo + "',";
			query += SysData.formatear(descripcion);
			query += ")";

			System.out.println("Insertar tipos incidencias: " + query);
			sPropio.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}

	// ==================================================================
	public void setTiposIncidencias(ResultSet rs)
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

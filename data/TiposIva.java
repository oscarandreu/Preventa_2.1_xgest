package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import log.LogWriter;

import system.SysData;

public class TiposIva {
	public short codigo;

	public String descripcion;

	public float porcentajeIva;

	public float porcentajeRe;

	// ==================================================================
	public TiposIva() {
	}

	// ==================================================================

	// ==================================================================
	public void actualizarTiposIva(Statement sPropio )
	// ==================================================================
	{
		try {
			String query = "UPDATE Tipos_Iva SET ";
			// query += "CODIGO='" + codigo + "',";
			query += "DESCRIPCION=" + SysData.formatear(descripcion) + ",";
			query += "PORC_IVA=" + SysData.formatear(porcentajeIva) + ",";
			query += "PORC_RE=" + SysData.formatear(porcentajeRe);
			query += " WHERE CODIGO = '" + codigo + "'";

			System.out.println("Actualiza tipos iva: " + query);
			sPropio.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}

	}

	// ==================================================================
	public void insertarTiposIva(Statement sPropio )
	// ==================================================================
	{
		try {
			String query = "INSERT INTO Tipos_Iva ("
					+ "CODIGO,DESCRIPCION,PORC_IVA,PORC_RE" + ") VALUES (";
			query += "'" + codigo + "',";
			query += SysData.formatear(descripcion) + ",";
			query += SysData.formatear(porcentajeIva) + ",";
			query += SysData.formatear(porcentajeIva);
			query += ")";

			System.out.println("Insertar tipos iva: " + query);
			sPropio.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}

	// ==================================================================
	public void setTiposIva(ResultSet rs)
	// ==================================================================
	{
		try {
			codigo = rs.getShort("CODIGO");
			descripcion = rs.getString("DESCRIPCION");
			porcentajeIva = rs.getFloat("PORC_IVA");
			porcentajeRe = rs.getFloat("PORC_RE");
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}

}

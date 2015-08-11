package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import log.LogWriter;

public class RutasVenta {

	public String ruta;

	public int codigoCliente;

	public String cliente;

	public int vendedor;

	public int dia;

	public int linea;

	// ==================================================================
	public RutasVenta() {
	}

	// ==================================================================

	// ==================================================================
	public void actualizarRutasVenta(Statement sPropio )
	// ==================================================================
	{
		try {
			String query = "UPDATE Rutas_Venta SET ";
			query += "RUTA='" + ruta + "',";
			query += "CODCLIENTE='" + codigoCliente + "',";
			query += "CLIENTE='" + cliente + "',";
			query += "VENDEDOR='" + vendedor + "',";
			query += "DIA='" + dia + "',";
			query += "LINEA='" + linea + "'";
			query += "WHERE ruta='" + ruta + "' AND vendedor='" + vendedor
					+ "' AND dia='" + dia + "' AND linea='" + linea + "'";

			System.out.println("Actualiza rutas venta: " + query);
			sPropio.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}

	}

	// ==================================================================
	public void insertarRutasVenta(Statement sPropio )
	// ==================================================================
	{
		try {
			String query = "INSERT INTO Rutas_venta ("
					+ "RUTA,CODCLIENTE,CLIENTE,VENDEDOR,DIA,LINEA"
					+ ") VALUES (";
			query += "'" + ruta + "',";
			query += "'" + codigoCliente + "',";
			query += "'" + cliente + "',";
			query += "'" + vendedor + "',";
			query += "'" + dia + "',";
			query += "'" + linea + "'";
			query += ")";

			System.out.println("Insertar rutas venta: " + query);
			sPropio.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}

	}

	// ==================================================================
	public void setRutasVenta(ResultSet rs)
	// ==================================================================
	{
		try {
			ruta = rs.getString("RUTA");
			codigoCliente = rs.getInt("CLIENTE");
			cliente = rs.getString("NOMBRE");
			vendedor = rs.getInt("VENDEDOR");
			dia = rs.getInt("DIA");
			linea = rs.getInt("LINEA");
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}

	}

}

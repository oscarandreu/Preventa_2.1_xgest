package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;

import log.LogWriter;

public abstract class Incidencia {

	public int numero;

	public String codigo;

	public int codCliente;

	public String observaciones;

	public String fecha;

	public char verificado;
	
	public String descripcion;

	// ==================================================================
	public Incidencia() {}
	// ==================================================================

	/** Elimina la cebcera de la bbdd */
	// ==================================================================
	public boolean eliminarIncidencia(Statement sPropio)
	// ==================================================================
	{
		boolean out = false;
		String query = "DELETE FROM INCIDENCIAS WHERE numero ='" + numero + "'";
		try {
			if (sPropio.executeUpdate(query) == 1)
				out = true;
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
		return out;
	}

	/**
	 * Metodo que toma el string de entrada desde la pda con la incidencia y los
	 * introduce en la bbdd
	 */
	// ==================================================================
	public boolean setIncidenciaFromPda( String in)
	// ==================================================================
	{
		boolean out = false;
		try {
			StringTokenizer st = new StringTokenizer(in, "#");
			if (st.countTokens() == 4) {
				codCliente = Integer.parseInt(st.nextToken());
				codigo = st.nextToken();
				fecha = st.nextToken();
				observaciones = st.nextToken();
				verificado = 'S';
				out = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
		return out;
	}

	/**
	 * Consigo el siguiente numero de incidencia
	 * 
	 * @deprecated ya que ahora hay un trigger en la bbdd
	 */
	// ==================================================================
	public int siguienteNumeroIncidencia(Statement s)
	// ==================================================================
	{
		String query = "SELECT NUMERO FROM INCIDENCIAS";
		int nextNumeroIncidencia = 1;
		try {
			ResultSet rs = s.executeQuery(query);
			int i = 0;
			while (rs.next()) {
				i = rs.getInt("numero");
				if (i > nextNumeroIncidencia)
					nextNumeroIncidencia = i;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
		return nextNumeroIncidencia++;
	}

	/** Actualiza una incidencia en nuestra base de datos local a partir de los datos locales de la instanci actual */
	// ==================================================================
	public boolean actualizarIncidencia(Statement sPropio)
	// ==================================================================
	{
		boolean out = false;
		try {
			String query = "UPDATE INCIDENCIAS SET ";
		//	query += "NUMERO='" + numero + "',";
			query += "CODIGO='" + codigo + "',";
			query += "COD_CLIENTE='" + codCliente + "',";
			query += "OBSERVACIONES='" + observaciones + "',";
			query += "FECHA='" + fecha + "'";
			query += "VERIFICADO='" + verificado + "'";

			query += " WHERE NUMERO = '" + numero + "'";

			if (sPropio.executeUpdate(query) == 1)
				out = true;

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
		return out;
	}

	/** Inserta una incidencia en nuestra base de datos local, y consigue el número asignado */
	// ==================================================================
	public boolean insertarIncidencia(Statement sPropio)
	// ==================================================================
	{
		boolean out = false;
		try {
			String query = "INSERT INTO INCIDENCIAS ("
					+ "CODIGO,COD_CLIENTE,OBSERVACIONES,FECHA,VERIFICADO"
					+ ") VALUES (" +
					// "'"+numero+"',"+
					"'" + codigo + "'," + "'" + codCliente + "'," + "'"
					+ observaciones + "'," + "'" + fecha + "'," + "'"
					+ verificado + "'" + ")";
			System.out.println(query);
			if (sPropio.executeUpdate(query) == 1)
				out = true;
			ResultSet rs = sPropio.executeQuery("SELECT numero FROM INCIDENCIAS " +
					"WHERE codigo='" + codigo + "' AND cod_cliente='" + codCliente + "' " +
					"AND observaciones='" + observaciones + "' AND fecha='" + fecha + "' " +
					"AND verificado='" + verificado + "'");
			if (rs.next()){
				this.numero= rs.getInt("numero");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
		return out;
	}

	/**
	 * Metodo que devuelve la consulta a realizar para buscar una Incidencia dada en nuestra base de datos
	 */
//	 ==================================================================
	public String getQueryIncidenciaByNumero(int numero)
//	 ==================================================================
	{
		String query = "SELECT NUMERO,COD_CLIENTE,OBSERVACIONES,FECHA,VERIFICADO,CODIGO " +
				"FROM INCIDENCIAS " + 
				"WHERE NUMERO='" + numero + "'";
		return query;
	}
	
	/**
	 * Metodo que inserta una Incidencia en la base de datos del ERP
	 */
//	 ==================================================================
	public abstract boolean insertarIncidenciaERP(Statement st, int numero, String codAg) throws Exception;
//	 ==================================================================
	
	public abstract boolean setIncidencia(ResultSet rs);

	public abstract int getCodCliente();

	public abstract int getNumero();
	
	public abstract String getCodigo();

	public abstract String getDescripcion();

	public abstract String getFecha();

	public abstract String getObservaciones();
	
	public abstract String getDescripcionByCodigo(String cod,Statement st);
	
	public abstract String getSelectCount();

}

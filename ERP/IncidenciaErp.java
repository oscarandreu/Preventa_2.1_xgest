package ERP;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import log.LogWriter;
import system.SysData;

import data.Incidencia;

public class IncidenciaErp extends Incidencia {
	
	public IncidenciaErp(){
		super();
	}
	
	/** Toma los datos de la clase incidencia y los inserta en la BBDD del ERP que estemos utilizando */
//	 =====================================================================================
	public boolean insertarIncidenciaERP(Statement st, int numero, String codAg) throws Exception
//	 =====================================================================================
	{
		boolean out = false;

		String query = "INSERT INTO fcsgc" + SysData.getEmpresa() + " (" +
		"scod,stipo,scli,sfec,ssol,sobssol)" +
		" VALUES ( ";
		query += "'" + numero + "',";   // numero de la incidencia
		query += "'" + codigo + "',";	// código de la incidencia
		query += "'" + codCliente + "',";	// código del cliente
		query += SysData.formatDateToFirebird(fecha) + ",";	// fecha
		query += "'N',"; 	// se inserta como NO solucionada
		query += SysData.formatear(observaciones);	// observaciones
			query += ")";
		System.out.println(query);
		if (st.executeUpdate(query) == 1)
				out = true;
		return out;
		
	}

	/** Asigna valores a una instancia de tipo Incidencia a partir de un ResultSet */
	// ==================================================================
	public boolean setIncidencia(ResultSet rs)
	// ==================================================================
	{
		boolean out = false;
		try {
			numero = rs.getInt("NUMERO");
			codigo = rs.getString("CODIGO");
			codCliente = rs.getInt("COD_CLIENTE");
			observaciones = rs.getString("OBSERVACIONES");
			fecha = rs.getString("FECHA");
			out = true;
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
		return out;
	}

	/** Consigue la descripción de un tipo de Incidencia a partir de su codigo */
//	 ==================================================================
	public String getDescripcionByCodigo(String codigo, Statement st)
//	 ==================================================================
	{
		String descr = null;
		String query = "SELECT gtipinc" + codigo + " descripcion FROM fcgen" + SysData.getEmpresa();
		System.out.println(query);
		try{
			ResultSet rs = st.executeQuery(query);
			if (rs.next()){
				descr = rs.getString("descripcion");
			}
		}catch(SQLException ex){
			
		}
		return descr;
	}
	
	/** Devuelve la consulta a realizar para conseguir el número de incidencias en la base de datos ERP */	
//	 ==================================================================
	public String getSelectCount()
//	 ==================================================================
	{
		String query = "SELECT COUNT(*) FROM FCSGC" + SysData.getEmpresa();
		return query;
	}
	
//	 ==================================================================
	public int getCodCliente()
//	 ==================================================================
	{
		return codCliente;
	}

//	 ==================================================================
	public int getNumero()
//	 ==================================================================
	{
		return numero;
	}
	
//	 ==================================================================
	public String getCodigo()
//	 ==================================================================
	{
		return codigo;
	}

//	 ==================================================================
	public String getDescripcion()
//	 ==================================================================
	{
		return descripcion;
	}

//	 ==================================================================
	public String getFecha()
//	 ==================================================================
	{
		return fecha;
	}

//	 ==================================================================
	public String getObservaciones()
//	 ==================================================================
	{
		return observaciones;
	}
	
	
	
}

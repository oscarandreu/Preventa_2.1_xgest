package data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import log.LogWriter;

import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import system.SysData;

public abstract class Agente {

	public int codigoAgente;

	public String serieAgente;

	public String nombreAgente;

	public String passwAgente;

	public Date fechaAlta;

	public Date fechaBaja;

	// ==================================================================
	public Agente() {}
	// ==================================================================

	// ==================================================================
	public PieDataset getEstadisticaClientesAgente(SysData sys)
	// ==================================================================
	{
		Connection c = sys.getConexionPropia();
		Connection c1 = sys.getConexionPropia();
		DefaultPieDataset defaultpiedataset = new DefaultPieDataset();
		Statement s = null;
		Statement s1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		try {
			s = c.createStatement();
			s1 = c1.createStatement();
			rs = s.executeQuery("SELECT CODIGO, NOMBRE FROM AGENTES");
			int i = 0;
			while (rs.next()) {

				String nombre = rs.getString("NOMBRE");
				rs1 = s1
						.executeQuery("SELECT COUNT(*) FROM DPERSONALES WHERE AGENTE='"
								+ rs.getString("CODIGO") + "'");
				i = 0;
				if (rs1.next()) {
					i = rs1.getInt("COUNT");
				}
				defaultpiedataset.setValue(nombre, new Integer(i));
			}
			rs.close();
			rs1.close();
			s.close();
			s1.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}

		return defaultpiedataset;
	}

	// ==================================================================
	public XYDataset getEstadisticaClientesTiempo(SysData sys)
	// ==================================================================
	{
		Connection c = sys.getConexionPropia();
		Connection c1 = sys.getConexionPropia();
		Statement s = null;
		ResultSet rs = null;
		Statement s1 = null;
		ResultSet rs1 = null;

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		try {
			String agente = "";
			String codigo = "";

			int total = 0;

			Timestamp fechaLeida = null;
			Timestamp altaAgente = null;
			s = c.createStatement();
			s1 = c1.createStatement();

			TimeSeries ts = null;
			String query = "SELECT nombre ,fecha_alta, codigo FROM agentes";
			rs = s.executeQuery(query);

			while (rs.next()) {
				altaAgente = rs.getTimestamp("fecha_alta");
				agente = rs.getString("nombre");
				codigo = rs.getString("codigo");
				total = 0;

				ts = new TimeSeries(agente, Month.class);
				query = "select a.fecha_alta FROM dpersonales a WHERE a.agente = '"
						+ codigo + "' ORDER by a.fecha_alta";
				rs1 = s1.executeQuery(query);

				if (altaAgente != null) {
					ts.addOrUpdate(new Month(altaAgente.getMonth() + 1,
							altaAgente.getYear() + 1900), 0);
				}

				while (rs1.next()) {
					fechaLeida = rs1.getTimestamp("fecha_alta");
					if ( fechaLeida != null){
						if (ts.addOrUpdate(new Month(fechaLeida.getMonth() + 1,
								fechaLeida.getYear() + 1900), total) == null) {
							total = 2;
						} else {
							total++;
						}
					}
				}
				rs1.close();

				dataset.addSeries(ts);
			}
			s1.close();
			rs.close();
			s.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}

		dataset.setDomainIsPointsInTime(true);

		return dataset;
	}

	// ==================================================================
	public XYDataset getEstadisticaVentasTiempo(SysData sys)
	// ==================================================================
	{
		Connection c = sys.getConexionPropia();
		Connection c1 = sys.getConexionPropia();
		Statement s = null;
		ResultSet rs = null;
		Statement s1 = null;
		ResultSet rs1 = null;

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		try {
			String agente = "";
			String codigo = "";

			int total = 0;

			Timestamp fechaLeida = null;
			Timestamp altaAgente = null;
			s = c.createStatement();
			s1 = c1.createStatement();

			TimeSeries ts = null;
			String query = "SELECT nombre ,fecha_alta, serie FROM agentes";
			rs = s.executeQuery(query);

			while (rs.next()) {
				altaAgente = rs.getTimestamp("fecha_alta");
				agente = rs.getString("nombre");
				codigo = rs.getString("serie");
				total = 0;

				ts = new TimeSeries(agente, Month.class);
				query = "select fecha FROM cabecera_pedido a WHERE serie = '"
						+ codigo + "' ORDER by fecha";
				rs1 = s1.executeQuery(query);

				if (altaAgente != null) {
					ts.addOrUpdate(new Month(altaAgente.getMonth() + 1,
							altaAgente.getYear() + 1900), 0);
				}

				while (rs1.next()) {
					fechaLeida = rs1.getTimestamp("fecha");

					if (ts.addOrUpdate(new Month(fechaLeida.getMonth() + 1,
							fechaLeida.getYear() + 1900), total) == null) {
						total = 2;
					} else {
						total++;
					}
				}
				rs1.close();

				dataset.addSeries(ts);
			}
			s1.close();
			rs.close();
			s.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}

		dataset.setDomainIsPointsInTime(true);

		return dataset;
	}

	// ==================================================================
	public PieDataset getEstadisticaVentasAgente(SysData sys)
	// ==================================================================
	{
		Connection c = sys.getConexionPropia();
		Connection c1 = sys.getConexionPropia();
		DefaultPieDataset defaultpiedataset = new DefaultPieDataset();
		Statement s = null;
		Statement s1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		try {
			s = c.createStatement();
			s1 = c1.createStatement();
			rs = s.executeQuery("SELECT SERIE,NOMBRE FROM AGENTES");
			int i = 0;
			while (rs.next()) {

				String nombre = rs.getString("NOMBRE");
/*				rs1 = s1.executeQuery("SELECT COUNT(*) FROM CABECERA_PEDIDO WHERE SERIE='"
								+ rs.getString("SERIE") + "'");*/
				
				rs1 = s1.executeQuery("SELECT SUM(TOTAL_IVA) FROM LINEA_PEDIDO WHERE SERIE='"
						+ rs.getString("SERIE") + "'");

				
				i = 0;
				if (rs1.next()) {
					i = rs1.getInt("sum");
				}
				defaultpiedataset.setValue(nombre, new Integer(i));
				
			}
			rs.close();
			rs1.close();
			s.close();
			s1.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}

		return defaultpiedataset;
	}

	// ==================================================================
	public PieDataset getEstadisticaIncidenciasCliente(SysData sys)
	// ==================================================================
	{
		Connection c = sys.getConexionPropia();
		Connection cErp = sys.getConexionErp();
		DefaultPieDataset defaultpiedataset = new DefaultPieDataset();
		Statement sErp = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rsErp = null;
		try {
			Statement s = c.createStatement();
			sErp = cErp.createStatement();
			
			//String query = "SELECT codigo,nombre FROM AGENTES";
			rsErp = sErp.executeQuery("SELECT ccodcl CODIGO,cnom NOMBRE FROM fccli" + sys.getEmpresa());
			int i = 0;
			while (rsErp.next()) {
				
				String nombre = rsErp.getString("NOMBRE");
				String codigo = rsErp.getString("CODIGO");
				
				rs2 = s.executeQuery("SELECT cod_cliente FROM incidencias WHERE cod_cliente='" + codigo + "'");
				while(rs2.next()){
					/*String codigoCli = rs2.getString("cod_cliente");
					String query = "SELECT * FROM fccli" + SysData.getEmpresa() + "  " +
					"WHERE ccodcl='" + codigoCli + "'"; // +
					//" AND crepre='" + codigo + "'";
					System.out.println(query);
					rsErp = sErp.executeQuery(query);
					if (rsErp.next()){*/
						i++;
					//}
					
				}
				if (i>0)
					defaultpiedataset.setValue(nombre, new Integer(i));
			}
			
		//	rs.close();
			rsErp.close();
			s.close();
			sErp.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}

		return defaultpiedataset;
	}

	// ==================================================================
	public void actualizarAgente(Statement sPropio)
	// ==================================================================
	{
		try {
			String query = "UPDATE AGENTES SET ";
		//	query += "CODIGO='" + codigoAgente + "',";
			query += "SERIE='" + serieAgente + "',";
			query += "NOMBRE='" + nombreAgente + "',";
			query += "PASSW='" + passwAgente + "',";
			query += "fecha_alta=" + prepararVariable(fechaAlta) + ",";
			query += "fecha_baja=" + prepararVariable(fechaBaja);

			query += " WHERE CODIGO = '" + codigoAgente + "'";

			sPropio.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}

	// ==================================================================
	public void insertarAgente(Statement sPropio)
	// ==================================================================
	{
		try {
			String query = "INSERT INTO AGENTES ("
					+ "CODIGO,SERIE,NOMBRE,PASSW, fecha_alta, fecha_baja,empresa"
					+ ") VALUES (" + "'" + codigoAgente + "'," + "'"
					+ serieAgente + "'," + "'" + nombreAgente + "'," + "'"
					+ passwAgente + "'," + prepararVariable(fechaAlta) + ","
					+ prepararVariable(fechaBaja) + ","
					+ "'1')";
			System.out.println(query);
			sPropio.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}
	
	// ==================================================================
	public int getNumeroAgentes(Statement s)
	// ==================================================================
	{
		int i=0;
		try{
			ResultSet rs = s.executeQuery("SELECT count(*) FROM AGENTES");
			if (rs.next()){
				i = rs.getInt("count");
			}
		}catch(SQLException ex){
			ex.printStackTrace();
			new LogWriter(ex.getStackTrace());
		}
		return i;
	}


	/**Setea el agente con los datos que vienen del ERP*/
	// ==================================================================
	public abstract void setAgente(ResultSet rs);
	// ==================================================================
	
//	 ==================================================================
	public abstract void setSerie(Statement sErp );
	// ==================================================================
	
	// ==================================================================
	public String prepararVariable(Date variable)
	// ==================================================================
	{
		if (variable == null) {
			return (null);

		}
		return ("'" + variable + "'");
		
	}
}

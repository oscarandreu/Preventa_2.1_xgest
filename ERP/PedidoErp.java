package ERP;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;

import log.LogWriter;
import system.SysData;
import Gui.system.InfoPane;
import data.Articulo;
import data.CabeceraPedido;
import data.LineaPedido;

public class PedidoErp extends CabeceraPedido{
	
	public static double total_neto;

	/**
	 * Metodo que inserta un Pedido en la base de datos ERP
	 */
//	==================================================================
	public boolean insertarPedidoERP(Statement s,Statement sErp,String serieArg,String anyoArg,String numPedArg,String codCliArg)
//	==================================================================
	{
		String codigoArticulo;
		boolean out = false;
		boolean nuevo = false;
		try{
						
			//Cogemos los datos de nuestra base de datos local dada una cabecera 
			ResultSet rs = s.executeQuery("SELECT * FROM CABECERA_PEDIDO " +
					"WHERE serie='" + serieArg + "' AND anyo=" + anyoArg + " AND numero_cabecera='" + numPedArg + "'");
			if (rs.next()){
				// Establecemos el pedido con los datos obtenidos
				setCabeceraPedidoFromLocal(rs);
			}
			//Obtenemos el mayor numero de oferta en ERP
			long maxOfe = getMaxOferta(sErp);
			String fp;
			//Cogemos la forma de pago del cliente dado
			rs = sErp.executeQuery("SELECT cforpa FROM fccli" + SysData.getEmpresa() + " WHERE ccodcl='" + codCliArg + "'");
			if (rs.next()){
				fp = "'" + rs.getString("cforpa") + "'";
			}else{
				fp = "null";
			}
			
			//Comprobamos que no existe un pedido con el codigo que vamos a usar, si existe, le damos un nuevo 
			//código y actualizamos ese codigo en nuestro pedido
			if (existePedido( new Long(numPedArg).longValue() ,sErp)){
				this.numeroCabecera = PedidoErp.nextNumeroPedidoErp(sErp,null);
				System.out.println("UPDATE cabecera_pedido SET numero_cabecera='" + this.numeroCabecera + "' " +
						"WHERE numero_cabecera='" + numPedArg + "' AND serie='" + this.serie + 
						"' AND anyo=" + SysData.formatYearToFirebird(this.fecha));
				s.executeUpdate("UPDATE cabecera_pedido SET numero_cabecera='" + this.numeroCabecera + "' " +
						"WHERE numero_cabecera='" + numPedArg + "' AND serie='" + this.serie + 
						"' AND anyo=" + SysData.formatYearToFirebird(this.fecha));
				nuevo = true;
			}
			if ( existeOferta( maxOfe ,sErp) ){
				maxOfe = getMaxOferta(sErp);
			}
			
			// Insertamos la cabecera de pedido
			String query = "INSERT INTO fccoc" + SysData.getEmpresa() + " (" +
			"BOFE,BPED,BCODCL,BFECPED,BTOBRU,BESPED,BOBSE,BUSUARIO,BHORAOFE,BHORAPED,BDTO,BCODREP,BFORPA" +
			") VALUES ( ";
			query += "'" + maxOfe + "',";
			query += "'" + this.numeroCabecera + "',";
			query += "'" + this.codCliente + "',";
			query += SysData.formatDateToFirebird(this.fecha) + ",";
			query += "'" + getTotalNeto() + "',";
			//query += "'" + this.total_iva + "',";
			query += "'S',";
			query += "'" + this.observaciones + "',";
			query += "'',";
			query += SysData.formatTimeToFirebird(this.fecha) + ",";
			query += SysData.formatTimeToFirebird(this.fecha) + ",";
			query += "'" + this.dtopp + "',";
			query += "'" + this.codAgente + "',";
			query += fp;
			query += ")";
			
			System.out.println( query );
			sErp.executeUpdate( query );
			
			query = "SELECT " +
			"a.numero_linea,a.numero_cabecera,a.serie,a.anyo,a.codigo_articulo,a.tarifas,a.cajas,a.unidades," +
			"a.dto_1,a.dto_2,a.dto_3,a.observaciones,a.total,a.total_iva," +
			"a.tarifas " +
			"FROM linea_pedido a " +
			"WHERE serie ='" + this.serie + "' " +
			"AND numero_cabecera='" + this.numeroCabecera + "' " +
			"AND anyo=" + SysData.formatYearToFirebird(this.fecha) ;
			
			System.out.println(query);
			//Cogemos los datos de cada linea de pedido
			rs = s.executeQuery(query);	
			Articulo artErp = new ArticuloErp();
			// Para cada linea de pedido obtenemos los datos del articulo del la linea, y completamos los datos
			// de la linea, para finalmente insertarla
			while (rs.next()){
				codigoArticulo = rs.getString("codigo_articulo");
				query = artErp.getQueryArticuloByCodigo(codigoArticulo);
				ResultSet rs2 = sErp.executeQuery(query);
				if (rs2.next())
					artErp.setArticulo(rs2);
				
				// Insertamos las lineas de pedido
				LineaPedido linErp = new LineaPedidoErp();
				query = "INSERT INTO fcloc" + SysData.getEmpresa() + " (" +
				"LOFE,LPED,LLINEA,LFECPED," +
				"LCODAR,LCODCL,LCANTI,LPRECI,LDTO,LIMPOR,LBULTOS" + 
				") VALUES ( ";
				query += "'" + maxOfe + "',";
				query += "'" + this.numeroCabecera + "',";
				query += "'" + rs.getString("numero_linea") + "',";
				query += SysData.formatDateToFirebird(this.fecha) + ",";
				query += "'" + codigoArticulo + "',";
				query += "'" + this.codCliente + "',";
				int cajas = new Integer(rs.getString("cajas"));
				int unidBulto = (int)artErp.unidadesBulto;
				int unid= new Integer(rs.getString("unidades"));
				int tot= (cajas*unidBulto)+unid;
				query += "'" + tot + "',";
				query += "'" + artErp.pvp1 + "',";
				query += "'" + rs.getString("dto_1") + "',";
				query += "'" + rs.getString("total") + "',";
				query += "'" + unidBulto + "'";
				query += ")";
				System.out.println( query );
				sErp.executeUpdate( query );
			}
			// Marcamos ese pedido como Verificado en nuestra base de datos local
			query = "UPDATE cabecera_pedido SET verificado='V' ";
			query += "WHERE serie='" + this.serie + "' " +
					"AND anyo=" + SysData.formatYearToFirebird(this.fecha) + 
					"AND numero_cabecera='" + this.numeroCabecera + "'";
			System.out.println(query);
			s.executeUpdate(query);
			if (nuevo){
				new InfoPane("ATENCION","El numero de pedido: " + numPedArg + 
						" del agente: " + SysData.formatSerieToAgente(this.serie) + "  ya existía en la base de datos. El pedido se ha insertado con un nuevo numero: " + this.numeroCabecera);
			}
		}catch(SQLException e){
			e.printStackTrace();
			new LogWriter(e.getStackTrace());
		}
		return out;
	}

	/** Metodo que nos devuelve el numero más alto de Pedido en la base de datos ERP 
	 * @param sErp
	 * @param serie
	 * @return
	 */
//	==================================================================
	public static long nextNumeroPedidoErp( Statement sErp ,String serie)
//	==================================================================
	{
		long out = 0;
		Calendar cal = new GregorianCalendar();
		long anyo = cal.get(Calendar.YEAR) * 100000;
		long numeroBase = new Long(serie) * 1000000000 + anyo;
		String query = "SELECT MAX(bped) numero FROM fccoc" + SysData.getEmpresa() + 
		" WHERE bped>" + numeroBase + " AND bped<" + (numeroBase+70000);
		System.out.println(query);
		try{
			ResultSet rs = sErp.executeQuery( query );
			if( rs.next() ){
				out = ((Double)(rs.getDouble("numero")+1)).longValue();
				if (out == 1){
					out = numeroBase + 1;
				}
			}
		}catch( SQLException e ){
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}catch (Exception e){ e.printStackTrace();}
		return out;
	}
	
	/**
	 * Metodo que nos devuelve el numero más añtp de Oferta en la base de datos ERP
	 * @param sErp
	 * @return
	 */
//	==================================================================	
	public static long getMaxOferta(Statement sErp)
//	==================================================================
	{
		long out = 0;
		String query = "SELECT MAX(bofe) numero FROM fccoc" + SysData.getEmpresa();
		try{
			ResultSet rs = sErp.executeQuery( query );
			if( rs.next() )
				out = rs.getLong("numero")+1;
			rs.close();
		}catch( SQLException e ){
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}catch (Exception e){ e.printStackTrace();}
		
		return out;
	}
	
	/** Metodo que devuelve si ya existe un Pedido en la base de datos ERP con el numero dado
	 * 
	 * @param numero
	 * @param sErp
	 * @return
	 */
//	==================================================================
	public boolean existePedido(long numero, Statement sErp)
//	==================================================================
	{
		boolean out = false;
		try{
			String query = "SELECT bped FROM fccoc" + SysData.getEmpresa() + 
			" WHERE bped='" + numero + "'";
			System.out.println(query);
			ResultSet rsErp = sErp.executeQuery(query);
			if (rsErp.next()){
				int num = rsErp.getInt("bped");
				if(num>0) out = true;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return out;
	}

	/** Metodo que devuelve si existe ya una Oferta en la base de datos ERP con el numero dado 
	 * @param numero
	 * @param sErp
	 * @return
	 */
//	==================================================================
	public boolean existeOferta(long numero, Statement sErp)
//	==================================================================
	{
		boolean out = false;
		try{
			ResultSet rsErp = sErp.executeQuery("SELECT bofe FROM fccoc" + SysData.getEmpresa() + 
					" WHERE bofe='" + numero + "'");
			if (rsErp.next()){
				int num = rsErp.getInt("bofe");
				if(num>0) out = true;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return out;
	}

//	==================================================================
	public static void setTotalNeto(double tn)
//	==================================================================
	{
		total_neto = tn;
	}

//	==================================================================
	public static double getTotalNeto()
//	==================================================================
	{
		return total_neto;
	}

}

package ERP;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import log.LogWriter;
import system.SysData;
import Gui.system.ErrorPane;
import Gui.system.InfoPane;
import data.Cliente;

public class ClienteErp extends Cliente{
	
//	 ==================================================================	
	public ClienteErp()
//	 ==================================================================
	{
		super();
	}
	 
	/**
	 * Metodo que toma los datos de la clase cliente y los inserta en la BBDD del ERP que estemos utilizando 
	 */
//	 ================================================================================
	public boolean insertarClienteERP(Statement sErp, Statement s, String codigoOld) //throws Exception
//	 ================================================================================
	{
		boolean out = false;
		int nuevoCodigo = -1;
		int maxLocal=0;
		try{
			ResultSet rs = s.executeQuery("SELECT * FROM DPERSONALES a, DBANCARIOS b  " +
					"WHERE a.CODIGO='" + codigoOld + "' " +
			"AND a.CODIGO = b.CODIGO");
			//Instanciamos este objeto de tipo Cliente con estos datos locales
			if (rs.next())
				this.setClienteFromLocal(rs);
			
			System.out.println("comprobando el codigo...");
			if (ClienteErp.existeClienteErp(sErp,codigoOld)){
				//El codigo ya existe en el ERP, debemos asignarle otro codigo
				System.out.println("el codigo existe...");
				
				rs = s.executeQuery("SELECT MAX(codigo) FROM DPERSONALES");
				if (rs.next()){
					maxLocal = rs.getInt("MAX");
				}
				int maxErp = ClienteErp.getMaxCodigoErp(sErp);
				
				//Nos quedamos con el codigo más alto de entre los codigos de la base de datos
				//local y la base de datos ERP 
				nuevoCodigo = Math.max(maxLocal,maxErp);
				System.out.println("nuevo codigo:" + nuevoCodigo);
				
				//Le establecemos al Cliente el nuevo codigo, y realizamos un Update Cascade con los datos
				//asociados al Cliente según el codigo
				this.updateCodigo( nuevoCodigo, s);
			}	
			
			String query = "INSERT INTO fccli" + SysData.getEmpresa() + " (" +
			"CCODCL,CNOM,CRESCAR7,CDNI,CDOM,CCODPO,CPOB,CPAIS,CZONA,CACTIVIDAD,CREPRE,CTEL1,CTEL2,CFAX1,CMAIL1," +
			"CRESCAR3,CTARI,CFORPA,CDIAP1,CDIAP2,CDIAP3,CDTO,CTIPIVAFIJ,CRESNUM5,CBANCO,CENT,CSUC,CDIG,CCUE," +
			"CRESNUM2,CRICONCED,CRECARGO,COBS,CINC,XFECALTA" +
			") VALUES ( ";
			query += "'" + getCodigo() + "',";
			query += SysData.formatear(nombre) + ",";
			query += SysData.formatear(razon) + ",";
			query += SysData.formatear(nif) + ",";
			query += SysData.formatear(direccion1) + ",";
			query += SysData.formatear(codPostal) + ",";
			query += SysData.formatear(poblacion) + ",";
			query += SysData.formatear(provincia) + ",";
			query += "'" + zona + "',";		// la zona no puede ser null en xgest
			query += "'" + actividad + "',";	// la actividad no pued ser null en xgest
			query += SysData.formatear(agente) + ",";
			query += SysData.formatear(telefono1) + ",";
			query += SysData.formatear(telefono2) + ",";
			query += SysData.formatear(fax) + ",";
			query += SysData.formatear(eMail) + ",";
			query += SysData.formatear(contacto) + ",";
			query += SysData.formatear(tarifa) + ",";
			query += SysData.formatear(formaPago) + ",";
			query += SysData.formatear(diaPago1) + ",";
			query += SysData.formatear(diaPago2) + ",";
			query += SysData.formatear(diaPago3) + ",";
			query += "'" + dto1 + "',";
			query += SysData.formatear(ivaFijo) + ",";
			query += SysData.formatear(irpf) + ",";
			query += SysData.formatear(banco) + ",";
			query += SysData.formatear(entidad) + ",";
			query += SysData.formatear(sucursal) + ",";
			query += SysData.formatear(cb) + ",";
			query += SysData.formatear(cc) + ",";
			query += "'" + riesgoSolicita + "',";
			query += "'" + riesgoConcedido + "',";
			query += "'" + recEquiv + "',";
			query += SysData.formatear(observaciones) + ",";
			query += "'" + aviso + "',";		// el aviso (o incidencia) no puede ser null en xgest
			query += SysData.formatDateToFirebird(fechaAlta);
			query += ")";
			
			System.out.println(query);
			sErp.executeUpdate(query);

			//Cambiamos el estado del Cliente en la base de datos local a Verificado
			query = "UPDATE dpersonales SET potencial='V' " +
			"WHERE CODIGO='" + getCodigo() + "'";
			System.out.println( query );
			if (s.executeUpdate(query) == 1){
				out = true;
			}

			if (nuevoCodigo != -1){
				new InfoPane("ATENCION","El codigo de cliente " + codigoOld + 
						" ya existía en la base de datos. Se ha insertado el cliente con un " +
						"nuevo código: " + nuevoCodigo);
			}
		}catch(SQLException ex){
			ex.printStackTrace();
			new LogWriter(ex.getStackTrace());
			new ErrorPane("Se ha producido un error al insertar un Cliente");
		}
		
		return out;
	}
	
	/**
	 * Metodo que elimina un cliente de la base de datos ERP, dado su codigo
	 */
// ==================================================================
	public boolean eliminarClienteERP(Statement sPropio, int codigo) throws Exception
// ==================================================================
	{
		boolean out = true;
		try{
		String query = "DELETE * FROM FCCLI" + SysData.getEmpresa() + " WHERE CCODCL='" + codigo + "'";
		sPropio.executeUpdate(query);
		}catch(SQLException e){
			e.printStackTrace();
			new LogWriter(e.getStackTrace());
			out = false;
		}
		return out;
	}
	
	/**
	 * Metodo que devuelve el numero de clientes que posee un agente dado
	 */
//	 ==================================================================
	public int getNumeroClientesAgente(Statement s, String codigo)
//	 ==================================================================
	{
		int i = 0;
		
		ResultSet rs;
		try {
			rs = s.executeQuery( "SELECT COUNT(*) FROM fccli" + SysData.getEmpresa() + " WHERE crepre = '" + codigo + "'" );
			if (rs.next()) {
				i = rs.getInt("COUNT(*)");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
		
		return i;
	}
	
	/**
	 * Metodo que devuelve el numero de clientes totales en la base de datos ERP
	 * @param s
	 * @return
	 */
//	 ==================================================================
	public int getNumeroClientes(Statement s)
//	 ==================================================================
	{
		int i = 0;
		
		ResultSet rs;
		try {
			rs = s.executeQuery( "SELECT COUNT(*) FROM fccli" + SysData.getEmpresa() );
			if (rs.next()) {
				i = rs.getInt("COUNT(*)");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
		
		return i;
	}

	/**
	 * Metodo que devuelve la consulta adecuada para obtener un cliente para la PDA dado su codigo
	 */	
// ==================================================================
	public String getQueryClientesPda(String codigo)
// ==================================================================
	{
		String query = "SELECT "
			+ "c.CCODCL,c.CNOM,c.CRESCAR7,c.CDOM,c.CPOB,c.CPAIS,c.CRESCAR3,c.CMAIL1,c.CDNI,c.CTEL1,"
			+ "c.CTEL2,c.CFAX1,c.CCODPO,c.CTARI,c.CDIAP1,c.CDIAP2,c.CDIAP3,c.CDTO,c.CRESNUM2,c.CRICONCED,"
			+ "c.CRECARGO,c.CBANCO,c.CENT,c.CSUC,c.CDIG,c.CCUE,c.COBS,c.CINC,c.CREPRE,c.CFORPA,"
			+ "c.CTIPIVAFIJ,c.CZONA,c.CACTIVIDAD,c.CRESNUM5" 
			//+ "r.CRIETOT"
			+ " FROM fccli" + SysData.getEmpresa() + " c "
			//+ " left join OBT_RIESGO_CLIENTE(C.CCODCL, 'NOW', 'R') R on (1=1) "
			+ " where c.CCODCL <> 0" 
			+ " AND c.CREPRE = '"+codigo+"'";
		return query;
	}
	
	
// ==================================================================
	public String getQueryClienteByCodigo(String codigo)
// ==================================================================
	{
		String query = "SELECT "
			+ "c.CCODCL,c.CNOM,c.CRESCAR7,c.CDOM,c.CPOB,c.CPAIS,c.CRESCAR3,c.CMAIL1,c.CDNI,c.CTEL1,"
			+ "c.CTEL2,c.CFAX1,c.CCODPO,c.CTARI,c.CDIAP1,c.CDIAP2,c.CDIAP3,c.CDTO,c.CRESNUM2,c.CRICONCED,"
			+ "c.CRECARGO,c.CBANCO,c.CENT,c.CSUC,c.CDIG,c.CCUE,c.COBS,c.CINC,c.CREPRE,c.CFORPA,"
			+ "c.CTIPIVAFIJ,c.CZONA,c.CACTIVIDAD,c.CRESNUM5" 
			//+ "r.CRIETOT"
			+ " FROM fccli" + SysData.getEmpresa() + " c "
			//+ " left join OBT_RIESGO_CLIENTE(C.CCODCL, 'NOW', 'R') R on (1=1) "
			+ " where c.CCODCL <> 0" 
			+ " AND c.CCODCL = '"+codigo+"'";
		return query;
	}	

	/**
	 * Metodo que setea una instancia de tipo cliente a partir de un resultset
	 */
	// ==================================================================
	public void setClienteFromErp(ResultSet rs)
	// ==================================================================
	{
		try {
			// Ajusto la fecha pendiente de recibir para que no de problemas
			//fechaAlta = rs.getString("FECHA_ALTA");
			//fechaBaja = rs.getString("FECHA_BAJA");
			setCodigo( rs.getInt("CCODCL") );
			//codigo2 = rs.getString("CODIGO2");
			nombre = rs.getString("CNOM");
			razon = rs.getString("CRESCAR7");
			direccion1 = rs.getString("CDOM");
			//direccion2 = rs.getString("CDOMENVMAT");
			poblacion = rs.getString("CPOB");
			provincia = rs.getString("CPAIS");
			contacto = rs.getString("CRESCAR3");
			eMail = rs.getString("CMAIL1");
			//web = rs.getString("WEB");
			nif = rs.getString("CDNI");
			telefono1 = rs.getString("CTEL1");
			telefono2 = rs.getString("CTEL2");
			fax = rs.getString("CFAX1");
			//codPais = rs.getString("CPAIS");
			codPostal = rs.getString("CCODPO");

			//divisa = rs.getString("DIVISA");
			tarifa = rs.getInt("CTARI");
			diaPago1 = rs.getInt("CDIAP1");
			diaPago2 = rs.getInt("CDIAP2");
			diaPago3 = rs.getInt("CDIAP3");
			//mesNoPago = rs.getInt("MES_NO_PAGO");
			//dto1 = rs.getDouble("CDTO");
			//dto2 = rs.getDouble("DTO_2");
			dtopp = rs.getDouble("CDTO");
			//incremento = rs.getInt("INCREMENTO");
			//portes = rs.getString("PORTES");
			riesgoSolicita = rs.getDouble("CRESNUM2");
			riesgoConcedido = rs.getDouble("CRICONCED");
			//riesgoDisponible = rs.getDouble("CRIETOT");
			recEquiv = rs.getString("CRECARGO").charAt(0);
			banco = rs.getString("CBANCO");
			entidad = rs.getString("CENT");
			sucursal = rs.getString("CSUC");
			cb = rs.getString("CDIG");
			cc = rs.getString("CCUE");
			observaciones = rs.getString("COBS");
			aviso = rs.getString("CINC");

			actividad = rs.getString("CACTIVIDAD");
			agente = rs.getInt("CREPRE");
			//diaVisita = rs.getString("DIA_VISITA");
			formaPago = rs.getString("CFORPA");
			irpf = rs.getInt("CRESNUM5");
			ivaFijo = rs.getInt("CTIPIVAFIJ");
			//ruta = rs.getString("RUTA");
			//tipo = rs.getString("TIPO");

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}

	}
	
	/**
	 * Metodo que devuelve el codigo de cliente más alto en la base de datos ERP
	 * @param stErp
	 * @return
	 */
	// ==================================================================
	public static int getMaxCodigoErp(Statement stErp)
	// ==================================================================
	{
		int max = 0;
		try{
			ResultSet rsErp = stErp.executeQuery("SELECT MAX(ccodcl) FROM fccli" + SysData.getEmpresa() + "");
			if (rsErp.next())
				max = rsErp.getInt("MAX(ccodcl)");
		}catch(SQLException e){
			e.printStackTrace();
			new LogWriter(e.getStackTrace());
		}
		return max+1;
		
	}
	
	/**
	 * Metodo que devuelve si un cliente existe o no en la base de datos ERP, dado su codigo
	 * @param sErp
	 * @param codigo
	 * @return
	 */
	// ==================================================================
	public static boolean existeClienteErp(Statement sErp,String codigo) 
	// ==================================================================
	{
		boolean out = false;
		try{
			ResultSet rsErp = sErp.executeQuery("SELECT ccodcl FROM fccli" + SysData.getEmpresa() + " WHERE ccodcl='"+codigo+"'");
			if( rsErp.next() )
				out = true;
		}catch(SQLException e){
			e.printStackTrace();
			new LogWriter(e.getStackTrace());
		}
		return out;
	}

}

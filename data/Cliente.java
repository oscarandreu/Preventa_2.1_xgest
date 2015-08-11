package data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import log.LogWriter;
import system.SysData;
import ERP.ClienteErp;

public abstract class Cliente {

	private Integer codigo = null;

	public char potencial;

	public String fechaAlta;
	
	public String fechaBaja;

	public String codigo2;

	public String nombre;

	public String razon;

	public String direccion1;

	public String direccion2;

	public String poblacion;

	public String provincia;

	public String contacto;

	public String eMail;

	public String web;

	public String nif;

	public String telefono1;

	public String telefono2;

	public String fax;

	public String codPais;

	public String codPostal;

	public String ruta;

	public String zona;

	public String diaVisita;

	public String tipo;

	public String actividad;

	public int agente;

	public String formaPago;

	public int ivaFijo;

	public int irpf;

	public String divisa;

	public int tarifa;

	public int diaPago1;

	public int diaPago2;

	public int diaPago3;

	public int mesNoPago;

	public double dto1;

	public double dto2;

	public double dtopp;

	public double incremento;

	public String portes;

	public double riesgoSolicita;

	public double riesgoConcedido;

	public double riesgoDisponible;

	public char recEquiv;

	public String banco;

	public String entidad;

	public String sucursal;

	public String cb;

	public String cc;

	public String observaciones;

	public String aviso;

	// ==================================================================
	public Cliente()
	// ==================================================================
	{
	}
	
	/**
	 * Metodo que inserta un Cliente en nuestra base de datos del programa base
	 *
	 */
	// ==================================================================
	public boolean insertarCliente(Statement sPropio) throws Exception
	// ==================================================================
	{
		boolean out = false;

			String query = "INSERT INTO dpersonales ("
					+ "CODIGO,FECHA_ALTA,FECHA_BAJA,CODIGO2,NOMBRE,RAZON,DIRECCION1,DIRECCION2,POBLACION,PROVINCIA,"
					+ "CONTACTO,E_MAIL,WEB,NIF,TELEFONO1,TELEFONO2,FAX,COD_PAIS,COD_POSTAL,"
					+ "ACTIVIDAD,AGENTE,DIA_VISITA,FORMA_PAGO,IRPF,IVA_FIJO,RUTA,TIPO,ZONA,POTENCIAL"
					+ ") VALUES (";
			query += "'" + codigo + "',";
			query += SysData.formatear(fechaAlta) + ",";
			query += SysData.formatear(fechaBaja) + ",";
			query += SysData.formatear(codigo2) + ",";
			query += SysData.formatear(nombre) + ",";
			query += SysData.formatear(razon) + ",";
			query += "'" + direccion1 + "',";
			query += SysData.formatear(direccion2) + ",";
			query += SysData.formatear(poblacion) + ",";
			query += SysData.formatear(provincia) + ",";
			query += SysData.formatear(contacto) + ",";
			query += SysData.formatear(eMail) + ",";
			query += SysData.formatear(web) + ",";
			query += SysData.formatear(nif) + ",";
			query += SysData.formatear(telefono1) + ",";
			query += SysData.formatear(telefono2) + ",";
			query += SysData.formatear(fax) + ",";
			query += SysData.formatear(codPais) + ",";
			query += SysData.formatear(codPostal) + ",";
			query += SysData.formatear(actividad) + ",";
			query += "'" + agente + "',";
			query += "'" + diaVisita + "',";
			query += "'" + formaPago + "',";
			query += "'" + irpf + "',";
			query += "'" + ivaFijo + "',";
			query += SysData.formatear(ruta) + ",";
			query += SysData.formatear(tipo) + ",";
			query += SysData.formatear(zona) + ",";
			query += "'" + potencial + "'";
			query += ")";
			System.out.println(query);
			if ( sPropio.executeUpdate( query ) == 1 ) {
				query = "INSERT INTO dbancarios ("
						+ "CODIGO,DIVISA,TARIFA,DIA_PAGO1,DIA_PAGO2,DIA_PAGO3,MES_NO_PAGO,DTO_1,DTO_2,DTO_PP,"
						+ "INCREMENTO,PORTES,RIESGO_SOLICITA,RIESGO_CONCED,RIESGO_DISP,REC_EQUI,BANCO,ENTIDAD,"
						+ "SUCURSAL,CB,CC,OBSERVACIONES,AVISO" + ") VALUES (";
				query += "'" + codigo + "',";
				query += SysData.formatear(divisa) + ",";
				query += SysData.formatear(tarifa) + ",";
				query += "'" + diaPago1 + "',";
				query += "'" + diaPago2 + "',";
				query += "'" + diaPago3 + "',";
				query += "'" + mesNoPago + "',";
				query += "'" + dto1 + "',";
				query += "'" + dto2	+ "',";
				query += "'" + dtopp + "',";
				query += "'" + incremento + "',";
				query += "'" + portes + "',";
				query += "'" + riesgoSolicita + "',";
				query += "'" + riesgoConcedido + "',";
				query += "'" + riesgoDisponible	+ "',";
				query += "'" + recEquiv + "',";
				query += SysData.formatear(banco) + ",";
				query += SysData.formatear(entidad) + ",";
				query += SysData.formatear(sucursal) + ",";
				query += SysData.formatear(cb) + ",";
				query += SysData.formatear(cc) + ",";
				query += SysData.formatear(observaciones) + ",";
				query += SysData.formatear(aviso) + ")";
				System.out.println(query);
				
				try{
				if (sPropio.executeUpdate(query) == 1)
					out = true;
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
			if( out == false ) {
				// Si falla la insercion de los datos bancarios elimino los
				// datos personales
				query = "DELETE FROM dpersonales WHERE codigo='" + codigo
						+ "'";
				sPropio.executeUpdate(query);
			}

		return out;
	}

	
	/**
	 * Metodo que recibe un Cliente de la PDA, y crea una instancia de tipo 
	 * Cliente con los datos recibidos
	 *
	 */
//	 ==================================================================
	public void recibirClientePDA()
//	 ==================================================================
	{
		
	}
	
	/**
	 * Metodo que formatea los datos de envío de un Cliente a la PDA
	 *
	 */
//	 ==================================================================
	public String clienteEnvioPda()
//	 ==================================================================
	{
		String datos = new String();
		datos = codigo + "#" 
			+ fechaAlta + "#" 
			+ codigo2 + "#"
			+ nombre + "#" 
			+ razon + "#" 
			+ direccion1 + "#"
			+ direccion2 + "#"
			+ poblacion + "#"
			+ provincia + "#"
			+ contacto + "#" 
			+ eMail + "#" 
			+ web + "#" 
			+ nif + "#" 
			+ telefono1 + "#"
			+ telefono2 + "#" 
			+ fax + "#" 
			+ codPais + "#"
			+ codPostal + "#"
			+ actividad + "#" 
			+ agente + "#" 
			+ diaVisita + "#"
			+ formaPago + "#" 
			+ irpf + "#" 
			+ ivaFijo + "#"
			+ ruta + "#" 
			+ tipo + "#"
			+ zona + "#" 
			+ "N#"
			+ divisa + "#" 
			+ tarifa + "#" 
			+ diaPago1 + "#"
			+ diaPago2 + "#"
			+ diaPago3 + "#"
			+ mesNoPago + "#"
			+ SysData.formatearDoubleToPda(dto1) + "#" 
			+ SysData.formatearDoubleToPda(dto2) + "#" 
			+ SysData.formatearDoubleToPda(dtopp) + "#"
			+ SysData.formatearDoubleToPda(incremento) + "#"
			+ portes + "#"
			+ SysData.formatearDoubleToPda(riesgoSolicita) + "#"
			+ SysData.formatearDoubleToPda(riesgoConcedido) + "#"
			+ SysData.formatearDoubleToPda(riesgoDisponible) + "#"
			+ recEquiv + "#" 
			+ banco + "#" 
			+ entidad + "#"
			+ sucursal + "#" 
			+ cb + "#" 
			+ cc + "#"
			+ observaciones + "#"
			+ aviso;
		return datos;
		
	}

	/**
	 * Metodo que inserta un Cliente en la base de datos del ERP
	 */
//	 ==================================================================
	public abstract boolean insertarClienteERP(Statement sErp, Statement s,String codigo) throws Exception;
//	 ==================================================================
	
	/**
	 * Metodo que elimina un Cliente en la base de datos del ERP
	 */
//	 ==================================================================
	public abstract boolean eliminarClienteERP(Statement sPropio, int codigo) throws Exception;
//	 ==================================================================
	
	/**
	 * Metodo que calcula el número de clientes de un agente
	 * 
	 * @param s
	 * @param codigo
	 * @return
	 */
	// ==================================================================
	public abstract int getNumeroClientesAgente(Statement s, String codigo);
	// ==================================================================
	
		


/**@deprecated
 * todos los datos van ha dejar de actalizarse de esta manera. ya que van ha ser enviados directamente a la pda
 * por onsiguiente estas bases de datos tambien va ha desaparecer*/
	// ==================================================================
	public void actualizarCliente(Statement sPropio )
	// ==================================================================
	{
		try {
			String query = "UPDATE dpersonales SET ";
			//query += "CODIGO='" + codigo + "',";
			query += "FECHA_ALTA=" + SysData.formatear(fechaAlta) + ",";
			query += "FECHA_BAJA=" + SysData.formatear(fechaBaja) + ",";
			query += "POTENCIAL='" + potencial + "',";
			query += "CODIGO2='" + codigo2 + "',";
			query += "NOMBRE='" + nombre + "',";
			query += "RAZON='" + razon + "',";
			query += "DIRECCION1='" + direccion1 + "',";
			query += "DIRECCION2='" + direccion2 + "',";
			query += "POBLACION='" + poblacion + "',";
			query += "PROVINCIA='" + provincia + "',";
			query += "CONTACTO='" + contacto + "',";
			query += "E_MAIL='" + eMail + "',";
			query += "WEB='" + web + "',";
			query += "NIF='" + nif + "',";
			query += "TELEFONO1='" + telefono1 + "',";
			query += "TELEFONO2='" + telefono2 + "',";
			query += "FAX='" + fax + "',";
			query += "COD_PAIS='" + codPais + "',";
			query += "COD_POSTAL='" + codPostal + "',";
			query += "AGENTE='" + agente + "',";
			query += "DIA_VISITA='" + diaVisita + "',";
			query += "FORMA_PAGO='" + formaPago + "',";
			query += "IRPF='" + irpf + "',";
			query += "IVA_FIJO='" + ivaFijo + "',";
			query += "RUTA='" + ruta + "',";
			query += "TIPO='" + tipo + "',";
			query += "ZONA='" + zona + "'";
			query += " WHERE codigo = '" + codigo + "'";

			System.out.println(query);
			sPropio.executeUpdate(query);

			query = "UPDATE dbancarios SET ";
			//query += "CODIGO='" + codigo + "',";
			query += "DIVISA='" + divisa + "',";
			query += "TARIFA='" + tarifa + "',";
			query += "DIA_PAGO1='" + diaPago1 + "',";
			query += "DIA_PAGO2='" + diaPago2 + "',";
			query += "DIA_PAGO3='" + diaPago3 + "',";
			query += "MES_NO_PAGO='" + mesNoPago + "',";
			query += "DTO_1='" + dto1 + "',";
			query += "DTO_2='" + dto2 + "',";
			query += "DTO_PP='" + dtopp + "',";
			query += "INCREMENTO='" + incremento + "',";
			query += "PORTES='" + portes + "',";
			query += "RIESG_SOLICITA='" + riesgoSolicita + "',";
			query += "RIESGO_CONCED='" + riesgoConcedido + "',";
			query += "RIESGO_DISP='" + riesgoDisponible + "',";
			query += "REC_EQUI='" + recEquiv + "',";
			query += "BANCO='" + banco + "',";
			query += "ENTIDAD='" + entidad + "',";
			query += "SUCURSAL='" + sucursal + "',";
			query += "CB='" + cb + "',";
			query += "CC='" + cc + "',";
			query += "OBSERVACIONES='" + observaciones + "',";
			query += "AVISO='" + aviso + "'";
			query += " WHERE codigo = '" + codigo + "'";

			System.out.println(query);
			sPropio.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}


	

	// ==================================================================
	public boolean setDatosBancariosClienteFromPda(String in) throws Exception
	// ==================================================================
	{
		boolean out = false;
		
			Iterator it = SysData.tokenizar(in);
			divisa = (String) it.next();
			tarifa = SysData.setInt(it.next());
			diaPago1 = SysData.setInt(it.next());
			diaPago2 = SysData.setInt(it.next());
			diaPago3 = SysData.setInt(it.next());
			mesNoPago = SysData.setInt(it.next());
			dto1 = SysData.formatearDoubleFromPda((String)it.next());
			dto2 = SysData.formatearDoubleFromPda((String)it.next());
			dtopp = SysData.formatearDoubleFromPda((String)it.next());
			incremento = SysData.formatearDoubleFromPda((String)it.next());
			portes = (String) it.next();
			riesgoSolicita = SysData.formatearDoubleFromPda((String)it.next());
			riesgoDisponible = SysData.formatearDoubleFromPda((String)it.next());
			recEquiv = ((String) it.next()).charAt(0);
			banco = (String) it.next();
			entidad = (String) it.next();
			sucursal = (String) it.next();
			cb = (String) it.next();
			cc = (String) it.next();
			observaciones = (String) it.next();
			aviso = (String) it.next();

			out = true;

		return out;
	}

	/**
	 * Metodo que toma el string de entrada desde la pda con el cliente y los
	 * introduce en la bbdd devuelve el codigo antiguo del cliente para poder
	 * cambiar el codigo en los pedidos que tenga etc...
	 */
	// ==================================================================
	public int setDatosPersonalesClienteFromPda(Statement s, String in,	SysData sys)
	// ==================================================================
	{
		int out = 0;
		int codigoAntiguo;
		try {
			Iterator it = SysData.tokenizar(in);
			codigo = Cliente.siguienteNumeroCliente(s,sys.getConexionErp().createStatement());
			potencial = 'S';
			codigoAntiguo = SysData.setInt(it.next());
			fechaAlta = (String) it.next();
			codigo2 = (String) it.next();
			nombre = (String) it.next();
			razon = (String) it.next();
			direccion1 = (String) it.next();
			direccion2 = (String) it.next();
			poblacion = (String) it.next();
			provincia = (String) it.next();
			contacto = (String) it.next();
			eMail = (String) it.next();
			web = (String) it.next();
			nif = (String) it.next();
			telefono1 = (String) it.next();
			telefono2 = (String) it.next();
			fax = (String) it.next();
			codPais = (String) it.next();
			codPostal = (String) it.next();
			ruta = (String) it.next();
			zona = (String) it.next();
			diaVisita = (String) it.next();
			tipo = (String) it.next();
			actividad = (String) it.next();
			agente = SysData.setInt(it.next());
			formaPago = (String) it.next();
			ivaFijo = SysData.setInt(it.next());
			irpf = SysData.setInt(it.next());

			out = codigoAntiguo;

		} catch (Exception e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
		return out;
	}

	/**
	 * Metodo que busca un Cliente dado en nuestra base de datos y crea una instancia de Cliente
	 * con esos datos.
	 */
	// ==================================================================
	public void getCliente(int codigo, SysData sys)
	// ==================================================================
	{
		String query = "SELECT"
				+ " a.CODIGO, a.FECHA_ALTA, a.FECHA_BAJA, a.CODIGO2, a.NOMBRE, a.RAZON, a.DIRECCION1, a.DIRECCION2"
				+ ", a.POBLACION, a.PROVINCIA	, a.CONTACTO, a.E_MAIL, a.WEB, a.NIF"
				+ ", a.TELEFONO1, a.TELEFONO2, a.FAX, a.COD_PAIS, a.COD_POSTAL"
				+ ", a.RUTA, a.ZONA, a.DIA_VISITA, a.TIPO, a.ACTIVIDAD, a.AGENTE, a.FORMA_PAGO, a.IVA_FIJO, a.IRPF"
				+ ", c.DIVISA, c.TARIFA, c.DIA_PAGO1, c.DIA_PAGO2, c.DIA_PAGO3, c.MES_NO_PAGO"
				+ ", c.DTO_1, c.DTO_2, c.DTO_PP, c.INCREMENTO, c.PORTES, c.RIESG_SOLICITA, c.RIESGO_CONCED"
				+ ", c.RIESGO_DISP, c.REC_EQUI, c.BANCO, c.ENTIDAD, c.SUCURSAL, c.CB, c.CC, c.OBSERVACIONES, c.AVISO"
				+ " FROM dpersonales a, dbancarios c" + " WHERE a.agente = '"
				+ codigo + "' AND a.codigo = c.codigo";
		Connection c = sys.getConexionPropia();
		try {
			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				setClienteFromLocal(rs);
			}
			rs.close();
			st.close();
			c.close();

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}
	
	/**
	 * Metodo que busca un Cliente dado en nuestra base de datos y crea una instancia de Cliente
	 * con esos datos.
	 */
	// ==================================================================
	public void getClienteByCodigoLocal(int codigo, SysData sys)
	// ==================================================================
	{
		String query = "SELECT"
				+ " a.CODIGO, a.FECHA_ALTA, a.FECHA_BAJA, a.CODIGO2, a.NOMBRE, a.RAZON, a.DIRECCION1, a.DIRECCION2"
				+ ", a.POBLACION, a.PROVINCIA	, a.CONTACTO, a.E_MAIL, a.WEB, a.NIF"
				+ ", a.TELEFONO1, a.TELEFONO2, a.FAX, a.COD_PAIS, a.COD_POSTAL"
				+ ", a.RUTA, a.ZONA, a.DIA_VISITA, a.TIPO, a.ACTIVIDAD, a.AGENTE, a.FORMA_PAGO, a.IVA_FIJO, a.IRPF"
				+ ", c.DIVISA, c.TARIFA, c.DIA_PAGO1, c.DIA_PAGO2, c.DIA_PAGO3, c.MES_NO_PAGO"
				+ ", c.DTO_1, c.DTO_2, c.DTO_PP, c.INCREMENTO, c.PORTES, c.RIESGO_SOLICITA, c.RIESGO_CONCED"
				+ ", c.RIESGO_DISP, c.REC_EQUI, c.BANCO, c.ENTIDAD, c.SUCURSAL, c.CB, c.CC, c.OBSERVACIONES, c.AVISO"
				+ " FROM dpersonales a, dbancarios c" + " WHERE a.codigo = '"
				+ codigo + "' AND a.codigo = c.codigo";
		Connection c = sys.getConexionPropia();
		try {
			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery(query);
			if (rs.next()) {
				setClienteFromLocal(rs);
			}
			rs.close();
			st.close();
			c.close();

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}
	
//	 ==================================================================
	public void setClienteFromLocal(ResultSet rs)
	// ==================================================================
	{
		try {
			// Ajusto la fecha pendiente de recibir para que no de problemas
			fechaAlta = rs.getString("FECHA_ALTA");
			fechaBaja = rs.getString("FECHA_BAJA");
			setCodigo( rs.getInt("CODIGO") );
			codigo2 = rs.getString("CODIGO2");
			nombre = rs.getString("NOMBRE");
			razon = rs.getString("RAZON");
			direccion1 = rs.getString("DIRECCION1");
			direccion2 = rs.getString("DIRECCION2");
			poblacion = rs.getString("POBLACION");
			provincia = rs.getString("PROVINCIA");
			contacto = rs.getString("CONTACTO");
			eMail = rs.getString("E_MAIL");
			web = rs.getString("WEB");
			nif = rs.getString("NIF");
			telefono1 = rs.getString("TELEFONO1");
			telefono2 = rs.getString("TELEFONO2");
			fax = rs.getString("FAX");
			codPais = rs.getString("COD_PAIS");
			codPostal = rs.getString("COD_POSTAL");

			divisa = rs.getString("DIVISA");
			tarifa = rs.getInt("TARIFA");
			diaPago1 = rs.getInt("DIA_PAGO1");
			diaPago2 = rs.getInt("DIA_PAGO2");
			diaPago3 = rs.getInt("DIA_PAGO3");
			mesNoPago = rs.getInt("MES_NO_PAGO");
			dto1 = rs.getDouble("DTO_1");
			dto2 = rs.getDouble("DTO_2");
			dtopp = rs.getDouble("DTO_PP");
			incremento = rs.getInt("INCREMENTO");
			portes = rs.getString("PORTES");
			riesgoSolicita = rs.getDouble("RIESGO_SOLICITA");
			riesgoConcedido = rs.getDouble("RIESGO_CONCED");
			riesgoDisponible = rs.getDouble("RIESGO_DISP");
			recEquiv = rs.getString("REC_EQUI").charAt(0);
			banco = rs.getString("BANCO");
			entidad = rs.getString("ENTIDAD");
			sucursal = rs.getString("SUCURSAL");
			cb = rs.getString("CB");
			cc = rs.getString("CC");
			observaciones = rs.getString("OBSERVACIONES");
			aviso = rs.getString("AVISO");

			actividad = rs.getString("ACTIVIDAD");
			agente = rs.getInt("AGENTE");
			diaVisita = rs.getString("DIA_VISITA");
			formaPago = rs.getString("FORMA_PAGO");
			irpf = rs.getInt("IRPF");
			ivaFijo = rs.getInt("IVA_FIJO");
			ruta = rs.getString("RUTA");
			tipo = rs.getString("TIPO");
			zona = rs.getString("ZONA");

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}

	}
	

	/**
	 * Metodo que, a partir de un ResultSet, instancia los atributos de un
	 * Cliente
	 */
	// ==================================================================
	public abstract void setClienteFromErp(ResultSet rs);
	// ==================================================================
	
	/**
	 * Metodo que devuelve la consulta a realizar para buscar un Cliente dado
	 */
//	 ==================================================================
	public abstract String getQueryClientesPda(String codigo);
//	 ==================================================================
	
	/**
	 * Metodo que devuelve la consulta a realizar para buscar un Cliente dado un codigo
	 */
//	 ==================================================================
	public abstract String getQueryClienteByCodigo(String codigo);
//	 ==================================================================
	
/**
 * Esta funcion debera mirar en la bbdd de la aplicacion a la que se haya adaptado la version que se este utilizando*/
	// ==================================================================
	public static int siguienteNumeroCliente(Statement s,Statement sErp)
	// ==================================================================
	{
		{
			int max = 0;
			try{
				ResultSet rs = s.executeQuery("SELECT MAX(CODIGO) FROM DPERSONALES");
				if( rs.next() )
					max = rs.getInt("MAX");
			}catch(SQLException e){
				e.printStackTrace();
				new LogWriter(e.getStackTrace());
			}
			if( max == 0 ){
				max = ClienteErp.getMaxCodigoErp(sErp);
			}else{
				max++;
			}
			return max;
			
		}
	}

/** Metodo que comprueba si un cliente existe o no y si ha sido validado, para ello hace lo siguiente:
 * 1- mira en nuestra bbdd para comprobar si el cliente está y si esta validado o no
 * 2- si no esta en nuestra bbdd mira en la del erp por si esta alli si esta lo doy por validado
 * 3- devuelve un codigo de la siguiente manera:
 * 		1- cliente validado
 * 		2- cliente no validado
 * 		3- cliente inexistente*/	
//	 ==================================================================
	public static int esValido(String codigo,Statement s,Statement sErp)
//	 ==================================================================
	{
		int out = 3;
		try{
			ResultSet rs = s.executeQuery("SELECT potencial,verificado FROM DPERSONALES WHERE codigo='"+codigo+"'");//devuelve v si esta verificado
			if( rs.next() ){
				//String ver = rs.getString("verificado");
				String ver = rs.getString("potencial");
				if( ("V").equals(ver) )
					out = 1;
				else 
					out = 2;
			}else{
				if( ClienteErp.existeClienteErp( sErp,codigo) )
					out = 1;
				else
					out = 3;
			}
		}catch(SQLException e){
			e.printStackTrace();
			new LogWriter(e.getStackTrace());
		}
		return out;
	}
//	 ==================================================================
	public int getAgente()
//	 ==================================================================
	{
		return agente;
	}
//	 ==================================================================
	public String getNombre()
//	 ==================================================================
	{
		return nombre;
	}
//	 ==================================================================
	public String getRazon() 
//	 ==================================================================
	{
		return razon;
	}
//	 ==================================================================
	public int getIvaFijo() 
//	 ==================================================================
	{
		return ivaFijo;
	}
//	 ==================================================================
	public boolean  updateCodigo( int newCodigo, Statement s )
//	 ==================================================================
	{
		
		int oldCode = codigo;
		codigo = newCodigo;
		boolean out = false;
		try{
			String query = "UPDATE INCIDENCIAS SET cod_cliente='" + codigo + "' " +
			"WHERE cod_cliente='" + oldCode + "'";
			s.executeUpdate(query);
			
			query = "UPDATE CABECERA_PEDIDO SET codigo_cliente='" + codigo + "' " +
			"WHERE codigo_cliente='" + oldCode + "'";
			s.executeUpdate(query);
			
			query = "UPDATE DPERSONALES SET codigo='" + codigo + "' " +
			"WHERE codigo='" + oldCode + "'";
			s.executeUpdate(query);
			
		}catch(SQLException e){
			e.printStackTrace();
			new LogWriter(e.getStackTrace());
			out = false;
		}
		return out;
	
	}
	
	/**Asigna un valor a codigo si todavia no lo tiene asignado en cas contrario utilizar updateCodigo*/
//	 ==================================================================
	public void setCodigo(int codigo) 
//	 ==================================================================
	{
		if( this.codigo == null )
			this.codigo = codigo;
	}
//	 ==================================================================
	public int getCodigo()
//	 ==================================================================
	{
		return this.codigo.intValue();
	}
//	public static int getMaxCodigo(SysData sys){};
	
	
	
	
}

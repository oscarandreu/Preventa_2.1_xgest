package ERP;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import data.Articulo;

import system.SysData;

import log.LogWriter;

public class ArticuloErp extends Articulo{
	
	protected String bloqueado;

	
	
	/**
	 * Metodo que devuelve el numero de articulos existentes en la base de datos de xgest 
	 */
//	 ==================================================================
	public int getNumeroArticulos( Statement s)
//	 ==================================================================
	{
		int i = 0;
		
		String query = "SELECT COUNT(*) from fcart" + SysData.getEmpresa();
		ResultSet rs;
		try {
			rs = s.executeQuery( query );
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
	 * Metodo que a partir de un ResultSet instancia los atributos de un objeto Articulo
	 */
	// ==================================================================
	public void setArticulo(ResultSet rs)
	// ==================================================================
	{
		try {
			//aviso = rs.getString("AVISO");
			codigo = rs.getString("CODIGO");
			//codigo2 = rs.getString("CODIGO2");
			//dto1 = rs.getDouble("DTO_1");
			//dto2 = rs.getDouble("DTO_2");
			//dto3 = rs.getDouble("DTO_3");
			//dto4 = rs.getDouble("DTO_4");
			//dto5 = rs.getDouble("DTO_5");
			//esc1desde = rs.getDouble("ESC_1_DESDE");
			//esc1hasta = rs.getDouble("ESC_1_HASTA");
			//esc2desde = rs.getDouble("ESC_2_DESDE");
			//esc2hasta = rs.getDouble("ESC_2_HASTA");
			//esc3desde = rs.getDouble("ESC_3_DESDE");
			//esc3hasta = rs.getDouble("ESC_3_HASTA");
			//esc4desde = rs.getDouble("ESC_4_DESDE");
			//esc4hasta = rs.getDouble("ESC_4_HASTA");
			//esc5desde = rs.getDouble("ESC_5_DESDE");
			//esc5hasta = rs.getDouble("ESC_5_HASTA");
			familia = rs.getString("FAMILIA");
			marca = rs.getString("MARCA");
			nombre = rs.getString("NOMBRE");
			observaciones = rs.getString("OBSERVACIONES");
			//pendienteRec = rs.getDouble("PENDIENTE_REC");
			//pendienteServir = rs.getDouble("PENDIENTE_SERVIR");
			pvp1 = rs.getDouble("PVP_1");
			pvp2 = rs.getDouble("PVP_2");
			pvp3 = rs.getDouble("PVP_3");
			pvp4 = rs.getDouble("PVP_4");
			pvp5 = rs.getDouble("PVP_5");
			pvp6 = rs.getDouble("PVP_6");
			pvpIva = rs.getDouble("PVPIVA");
			//seccion = rs.getString("SECCION");
			//subfamilia = rs.getString("SUBFAMILIA");
			tipoIva = rs.getInt("TIPO_IVA");
			unidadesBulto = rs.getDouble("UNIDADES_BULTO");
			stock = rs.getDouble("STOCK");
			bloqueado = rs.getString("BLOQUEADO");
		//	fechaPendienteRecibir = rs.getDate("FECHA_PEND_REC");
			
			//documento = rs.getString("DOCUMENTO");
			
			// fechaBaja = rs.getDate("FECHA_BAJA");

		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}
	
	/**
	 * Metodo que devuelve la consulta a realizar para obtener un articulo para la PDA, segun su codigo
	 */
// ==================================================================
	public String getQueryArticulosPda()
// ==================================================================
	{
		String query = "SELECT "
			+ "a.ACODAR CODIGO,a.ARESCAR1 CODIGO2,a.AFAMILIA FAMILIA,a.AMARCA MARCA,a.ADESCR NOMBRE," 
			+ "a.AOBSE OBSERVACIONES,a.APVP1 PVP_1,a.APVP2 PVP_2,a.APVP3 PVP_3,a.APVP4 PVP_4,a.ARESNUM5 PVP_5," 
			+ "a.ARESNUM6 PVP_6,a.ARESNUM3 PVPIVA,a.ALINDES LINDES," 
			+ "a.AUNIBUL UNIDADES_BULTO,a.ASTOCK STOCK, a.ATIPIVA TIPO_IVA,a.ABLOQUEADO bloqueado"
			+ " from FCART" + SysData.getEmpresa() + " a ";
			//+ "JOIN TIPOS_IVA TI ON (A.TIPO_IVA = TI.CODIGO) "
			//+ "left JOIN OBT_PDT_RECIBIR_ART (A.CODIGO) OPR ON (1=1) "
			//+ "LEFT JOIN OBT_PDT_SERVIR_ART (A.CODIGO) OPS ON (1=1) "
			//+ "LEFT JOIN OBT_EXISTENCIAS_AFECHA (A.CODIGO, 1, 'NOW') OEA ON (1=1) ";
			
		return query;
	}
	
	/**
	 * Metodo que devuelve la consulta a realizar para obtener un articulo dado su codigo
	 */
// ==================================================================
	public String getQueryArticuloByCodigo(String cod)
// ==================================================================
	{
		String query = "SELECT "
			+ "a.ACODAR CODIGO,a.ARESCAR1 CODIGO2,a.AFAMILIA FAMILIA,a.AMARCA MARCA,a.ADESCR NOMBRE," 
			+ "a.AOBSE OBSERVACIONES,a.APVP1 PVP_1,a.APVP2 PVP_2,a.APVP3 PVP_3,a.APVP4 PVP_4,a.ARESNUM5 PVP_5,"
			+ "a.ARESNUM6 PVP_6,a.ARESNUM3 PVPIVA,a.ALINDES LINDES," 
			+ "a.AUNIBUL UNIDADES_BULTO,a.ASTOCK STOCK,a.ATIPIVA TIPO_IVA,a.ABLOQUEADO bloqueado"
			+ " from FCART" + SysData.getEmpresa() + " a " 
			+ "WHERE a.ACODAR='" + cod + "'";
		return query;
	}
	
	/**
	 * Metodo que devuelve el porcentaje de IVA de un articulo dado
	 */
// ==================================================================
	public double getPorcIva(SysData sys)
// ==================================================================
	{
		double porcIva = 0;
		
		try{
			Statement stErp = sys.getConexionErp().createStatement();
			ResultSet rsErp = stErp.executeQuery("SELECT a.tipiva,b.giva1 porc_iva " +
					"FROM fcart" + SysData.getEmpresa() + " a, fcgen" + SysData.getEmpresa() +" b " +
					"WHERE a.acodar = '" + this.codigo + "' " +
					"AND a.atipiva = b.gcodemp ");
			if(rsErp.next()){
				porcIva = rsErp.getDouble("porc_iva");
			}
		}catch (SQLException ex){
			ex.printStackTrace();
		}
		
		return porcIva;
	}
	
	/**
	 * Metodo que devuelve si un articulo está bloqueado o no
	 */
// ==================================================================
	public boolean esActivo()
// ==================================================================
	{
		return ("N".equals(bloqueado));
	}
	
	/**
	 * Metodo que devuelve si un articulo tiene lineas de descuento o no
	 */
// ==================================================================
	public boolean tieneLineaDto(ResultSet rs)
// ==================================================================
	{
		int ld = 0;

		try{
			ld = rs.getInt("LINDES"); 
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		return ld>0;

	}
	
	/**
	 * Metodo que establece los descuentos segun el ERP
	 */
// ==================================================================
	public void setDescuentos(ResultSet rs, Statement st)
// ==================================================================
	{
		try{
			int ld = rs.getInt("lindes");
			ResultSet rs2 = st.executeQuery("SELECT DDTOTAR1,DDTOTAR2,DDTOTAR3,DDTOTAR3,DDTOTAR4,DDTOTAR5,DDTOTAR6,DDTOTAR7 " 
					+ "FROM FCLDT" + SysData.getEmpresa() 
					+ " WHERE DCOD=" + ld) ;
			if(rs2.next()){
				dto1 = rs2.getDouble("DDTOTAR1"); 
				dto2 = rs2.getDouble("DDTOTAR2"); 
				dto3 = rs2.getDouble("DDTOTAR3"); 
				dto4 = rs2.getDouble("DDTOTAR4"); 
				dto5 = rs2.getDouble("DDTOTAR5"); 
				dto6 = rs2.getDouble("DDTOTAR6"); 
				dto7 = rs2.getDouble("DDTOTAR7");
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}

	}
	
	
	
	
	


}

package Gui;

import java.awt.Color;
import java.awt.Dimension;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import log.LogWriter;
import system.Cargador;
import system.SysData;
import ERP.ArticuloErp;
import Gui.system.QueryPane;
import bbdd.JPanelTableExtendida;
import data.Articulo;

public class GuiLineasPedidos extends GuiJPanel {

	private JSplitPane jSplitPane = null; // @jve:decl-index=0:visual-constraint="2,1"

	private JScrollPane jScrollPane = null;

	private JPanel jPanel = null;

	private JLabel jLabel9;

	private JLabel jLabel8;

	private JLabel jLabel7;

	private JLabel jLabel6;

	private JLabel jLabel5;

	private JLabel jLabel4;

	private JLabel jLabel3;

	private JLabel jLabel2;

	private JLabel jLabel1;

	private JLabel jLabel;

	private JTextField iva;

	private JTextField numeroLinea;

	private JTextField codigoArticulo;

	private JTextField tarifa;

	private JTextField cajas;

	private JTextField unidades;

	private JTextField descuento;

	private JTextField totalIva;

	private JTextField totalSinIva;

	private JScrollPane jScrollPane1 = null;

	private JTextPane observaciones = null;

	private JTextField nombreArticulo = null;
	
	// condicion para las operaciones de BDD
	String condicionWhere = null;
	
	
	// datos para el calculo de los totales sin iva y con iva
	protected Integer totalsiniva = null;
	protected Integer totalconiva = null;

	private JTextField unidadesBulto = null;

	private JLabel jLunidadesbulto = null;

	private JTextField unidadesTotales = null;

	private JLabel jLunidadesTotales = null;
	
	private boolean pedidoValidado = false;

	public GuiLineasPedidos(SysData sys) {
		super(sys);
	}
	
	public GuiLineasPedidos(SysData sys, boolean valor) {
		super(sys);
		pedidoValidado = valor;
	}

	public JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setSize(new java.awt.Dimension(469, 365));
			jSplitPane.setDividerLocation(150);
			jSplitPane.setTopComponent(getJScrollPane());
			jSplitPane.setBottomComponent(getJPanel());
			jSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
		}
		return jSplitPane;
	}

	// ===========================================================================================
	protected JScrollPane getJScrollPane()
	// ===========================================================================================
	{
		model = new JPanelTableExtendida(this, new String[] {"Articulo"},2);
		// Configuracion de la tabla
		model.setPrimaryKeys(new String[] {"codigo_articulo","NUMERO_LINEA", "NUMERO_CABECERA",
				"SERIE" });
		model.setColumnas(new String[] { "a.NUMERO_LINEA" }); //, "b.nombre" });
		model.setColumnasFormateadas(new String[] { "Número" }); //, "Articulo" });
		model.setAnchoColumnas(new int[] { 80, 370 });
		model.setDatabase("LINEA_PEDIDO a"); //, ARTICULOS b");
		model.setWhere(" a.numero_linea=0"); //a.CODIGO_ARTICULO = b.codigo AND a.numero_linea = 0");
		model.setOrder("numero_linea");
		
		//model.tabla.setAutoResizeMode( model.tabla.AUTO_RESIZE_ALL_COLUMNS );
		
		jScrollPane = new JScrollPane(model.getTabla());
		model.tabla.setAutoResizeMode(model.tabla.AUTO_RESIZE_ALL_COLUMNS);
		jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jScrollPane.setBounds(new java.awt.Rectangle(0, 0, 279, 390));
		return jScrollPane;
	}

	// ===========================================================================================
	protected void actualizar(String where)
	// ===========================================================================================
	{
		//model.setWhere(" b.codigo = a.CODIGO_ARTICULO AND " + where);
		model.setWhere(" " + where);
		try {
			model.setData(false);
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}

	// ===========================================================================================
	public void mostrarDatos(String where)
	// ===========================================================================================
	{
		
		Statement sta;
		condicionWhere = where;
		try {
			sta = conect.createStatement();
			String query = "SELECT "
					+ "a.NUMERO_LINEA,a.CODIGO_ARTICULO,a.TARIFAS,a.CAJAS,a.UNIDADES," 
					+ "a.dto_1,a.observaciones,a.TOTAL,a.TOTAL_IVA " 
				//	+ "b.nombre, b.unidades_bulto, c.PORC_IVA " 
					+ "FROM linea_pedido a " //, articulos b, TIPOS_IVA c "
				//	+ "WHERE b.codigo = a.codigo_articulo "
				//	+ "AND c.codigo = b.tipo_iva " + "AND " 
					+ "WHERE " + where;
			System.out.println(query);
			ResultSet resul = sta.executeQuery(query);

			if (resul.next()) {
				
				String codArt = resul.getString("codigo_articulo");
				
				Articulo artErp = new ArticuloErp();
				String queryErp = artErp.getQueryArticuloByCodigo(codArt);
				
				Statement stErp = sys.getConexionErp().createStatement();
				ResultSet rsErp = stErp.executeQuery(queryErp);
				if (rsErp.next()){
					artErp.setArticulo(rsErp);
				}
				
				numeroLinea.setText(resul.getString("NUMERO_LINEA"));
				codigoArticulo.setText(artErp.getCodigo());
				nombreArticulo.setText(artErp.getNombre());
				//iva.setText(resul.getString("PORC_IVA"));
				tarifa.setText(resul.getString("TARIFAS"));
				cajas.setText(resul.getString("cajas"));
				unidades.setText(resul.getString("unidades"));
				descuento.setText(resul.getString("dto_1"));
				new Integer(new Double(artErp.getUnidadesBulto()).intValue()).toString();
				unidadesBulto.setText( new Integer(new Double(artErp.getUnidadesBulto()).intValue()).toString() );
				unidadesTotales.setText(new Integer(new Integer(unidades.getText()) + 
						(new Integer(cajas.getText()) * new Integer(unidadesBulto.getText()) ) ).toString());
				observaciones.setText(resul.getString("observaciones"));
				// calculo del total sin iva
				totalSinIva.setText( resul.getString( "TOTAL" ) );
				if (tarifa.getText().equals("0")){ 
					totalSinIva.setBackground(new Color(244,149,102));
					totalSinIva.setText("0");
				} else totalSinIva.setBackground(null);
			
				
				// calculo del iva total
				totalIva.setText( resul.getString( "TOTAL_IVA" ) );
				if (tarifa.getText().equals("0")){ 
					totalIva.setBackground(new Color(244,149,102));
					totalIva.setText("0");
				} else totalIva.setBackground(null);
			}
			resul.close();
			sta.close();
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}

	// ===========================================================================================
	private JPanel getJPanel()
	// ===========================================================================================
	{
		if (jPanel == null) {
			jLunidadesTotales = new JLabel();
			jLunidadesTotales.setBounds(new java.awt.Rectangle(304,72,64,20));
			jLunidadesTotales.setText("Tot. Unid.");
			jLunidadesbulto = new JLabel();
			jLunidadesbulto.setBounds(new java.awt.Rectangle(120,75,112,18));
			jLunidadesbulto.setText("Unidades por bulto");
			jPanel = new JPanel();
			jLabel9 = new JLabel();
			jLabel9.setBounds(new java.awt.Rectangle(10, 120, 90, 19));
			jLabel9.setText("Observaciones");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new java.awt.Rectangle(10,97,71,19));
			jLabel8.setText("Descuento");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new java.awt.Rectangle(133,97,59,19));
			jLabel7.setText("Total IVA");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(282,97,80,19));
			jLabel6.setText("Total sin IVA");
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(191, 52, 41, 19));
			jLabel5.setText("Cajas");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(303, 52, 64, 19));
			jLabel4.setText("Unidades");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(78, 52, 43, 19));
			jLabel3.setText("Tarifa");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(11, 52, 29, 19));
			jLabel2.setText("IVA%");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(111, 6, 98, 18));
			jLabel1.setText("Codigo Articulo");
			jLabel = new JLabel();
			jLabel.setLocation(new java.awt.Point(13, 6));
			jLabel.setText("Linea:");
			jLabel.setSize(new Dimension(42, 19));
			jPanel.setLayout(null);
			jPanel.add(jLabel, null);
			jPanel.add(jLabel1, null);
			jPanel.add(jLabel2, null);
			jPanel.add(jLabel3, null);
			jPanel.add(jLabel4, null);
			jPanel.add(jLabel5, null);
			jPanel.add(jLabel6, null);
			jPanel.add(jLabel7, null);
			jPanel.add(jLabel8, null);
			jPanel.add(jLabel9, null);
			jPanel.add(getNumeroLinea(), null);
			jPanel.add(getCodigoArticulo(), null);
			jPanel.add(getIva(), null);
			jPanel.add(getTarifa(), null);
			jPanel.add(getCajas(), null);
			jPanel.add(getUnidades(), null);
			jPanel.add(getDescuento(), null);
			jPanel.add(getTotalIva(), null);
			jPanel.add(getTotalSinIva(), null);
			jPanel.add(getJScrollPane1(), null);
			jPanel.add(getNombreArticulo(), null);
			jPanel.add(getUnidadesbulto(), null);
			jPanel.add(jLunidadesbulto, null);
			jPanel.add(getUnidadesTotales(), null);
			jPanel.add(jLunidadesTotales, null);
		}
		return jPanel;
	}

	private JTextField getNumeroLinea() {
		if (numeroLinea == null) {
			numeroLinea = new JTextField();
			numeroLinea.setBounds(new java.awt.Rectangle(59, 6, 45, 19));
			numeroLinea.setEditable(false);
		}
		return numeroLinea;
	}

	/**
	 * This method initializes jTextField2
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getCodigoArticulo() {
		if (codigoArticulo == null) {
			codigoArticulo = new JTextField();
			codigoArticulo.setBounds(new java.awt.Rectangle(213, 6, 236, 19));
			codigoArticulo.setEditable(false);
		}
		return codigoArticulo;
	}

	private JTextField getIva() {
		if (iva == null) {
			iva = new JTextField();
			iva.setLocation(new java.awt.Point(44, 52));
			iva.setSize(new java.awt.Dimension(30, 19));
			iva.setEditable(false);
		}
		return iva;
	}

	private JTextField getTarifa() {
		if (tarifa == null) {
			tarifa = new JTextField();
			tarifa.setBounds(new java.awt.Rectangle(126, 52, 43, 19));
			tarifa.setEditable(false);
			//if (pedidoValidado) tarifa.setEditable(false);
			
		}
		return tarifa;
	}

	private JTextField getCajas() {
		if (cajas == null) {
			cajas = new JTextField();
			cajas.setBounds(new java.awt.Rectangle(235, 52, 63, 19));
			cajas.setEditable(false);
			//if (pedidoValidado) cajas.setEditable(false);
		}
		return cajas;
	}

	private JTextField getUnidades() {
		if (unidades == null) {
			unidades = new JTextField();
			unidades.setBounds(new java.awt.Rectangle(370, 52, 78, 19));
			unidades.setEditable(false);
			//if (pedidoValidado) unidades.setEditable(false);
		}
		return unidades;
	}

	private JTextField getDescuento() {
		if (descuento == null) {
			descuento = new JTextField();
			descuento.setBounds(new java.awt.Rectangle(85,97,43,19));
			descuento.setEditable(false);
		}
		return descuento;
	}

	private JTextField getTotalIva() {
		if (totalIva == null) {
			totalIva = new JTextField();
			totalIva.setBounds(new java.awt.Rectangle(196,97,84,19));
			totalIva.setEditable( false );
		}
		return totalIva;
	}

	private JTextField getTotalSinIva() {
		if (totalSinIva == null) {
			totalSinIva = new JTextField();
			totalSinIva.setBounds(new java.awt.Rectangle(365,97,84,19));
			totalSinIva.setEditable( false );
		}
		return totalSinIva;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBounds(new java.awt.Rectangle(9, 141, 433, 62));
			jScrollPane1.setViewportView(getObservaciones());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTextPane
	 * 
	 * @return javax.swing.JTextPane
	 */
	private JTextPane getObservaciones() {
		if (observaciones == null) {
			observaciones = new JTextPane();
			observaciones.setText("");
			//if (pedidoValidado) observaciones.setEditable(false);
			observaciones.setEditable(false);
		}
		return observaciones;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getNombreArticulo() {
		if (nombreArticulo == null) {
			nombreArticulo = new JTextField();
			nombreArticulo.setBounds(new java.awt.Rectangle(10, 30, 439, 20));
			nombreArticulo.setEditable(false);
		}
		return nombreArticulo;
	}
	
	/**
	 * This method initializes jTunidadesbulto	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getUnidadesbulto() {
		if (unidadesBulto == null) {
			unidadesBulto = new JTextField();
			unidadesBulto.setBounds(new java.awt.Rectangle(235,74,62,19));
			unidadesBulto.setEditable(false);
		}
		return unidadesBulto;
	}

	/**
	 * This method initializes unidadesTotales	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getUnidadesTotales() {
		if (unidadesTotales == null) {
			unidadesTotales = new JTextField();
			unidadesTotales.setBounds(new java.awt.Rectangle(371,71,77,21));
			unidadesTotales.setEditable(false);
		}
		return unidadesTotales;
	}


//  ====================================================	
	public Integer getTotalsiniva()
//  ====================================================
	{
		return totalsiniva;
	}

//  ====================================================	
	public void setTotalsiniva(Integer totalsiniva)
//  ====================================================
	{
		this.totalsiniva = totalsiniva;
	}

//  ====================================================	
	public Integer getTotalconiva()
//  ====================================================
	{
		return totalconiva;
	}

//  ====================================================	
	public void setTotalconiva(Integer totalconiva)
//  ====================================================
	{
		this.totalconiva = totalconiva;
	}
	
	/** Metodo que elimina la linea de pedido seleccionada */
//  =============================================================================			
	public void modificarLineaPedido( Cargador cargador )
//  =============================================================================
	{
		String dto = "0";
		double total = 0;
		double total_iva = 0;
		
		int n = new QueryPane().consultar( "Desea modificar (S/N)" );
		if ( n == 0 ){
			if (tarifa.getText().equals("0")){
				totalSinIva.setText("0");
				totalIva.setText("0");
				
			}else{
				Double nuevasUnidades = Double.parseDouble(unidadesBulto.getText()) * Double.parseDouble(cajas.getText()) 
					+ Double.parseDouble(unidades.getText());
				try {
					Statement st = conect.createStatement();
					ResultSet rs = st.executeQuery("SELECT ESC_1_HASTA, ESC_2_HASTA, ESC_3_HASTA, ESC_4_HASTA, ESC_5_HASTA " +
							"FROM articulos WHERE codigo='" + codigoArticulo.getText() + "'");
					if (rs.next()){
						int tarifaTemp = 0;
						if (nuevasUnidades <= rs.getDouble("ESC_1_HASTA")){
							tarifaTemp = 1;
						}else if (nuevasUnidades <= rs.getDouble("ESC_2_HASTA")){
							tarifaTemp = 2;
						}else if (nuevasUnidades <= rs.getDouble("ESC_3_HASTA")){
							tarifaTemp = 3;
						}else if (nuevasUnidades <= rs.getDouble("ESC_4_HASTA")){
							tarifaTemp = 4;
						}else if (nuevasUnidades <= rs.getDouble("ESC_5_HASTA")){
							tarifaTemp = 5;
						}else tarifaTemp = 1;
						if (tarifaTemp>Double.parseDouble(tarifa.getText()))
							tarifa.setText(Integer.toString(tarifaTemp));
						
					}
				}catch (SQLException ex){
					ex.printStackTrace();
					new LogWriter( ex.getStackTrace() );
				}
				
				
				
				Statement st;
				
				try{
					st = conect.createStatement();
					ResultSet rs = st.executeQuery("SELECT dto_" + tarifa.getText() + 
							" FROM articulos " +
							"WHERE codigo='" + codigoArticulo.getText() + "'");
					if (rs.next()){
						dto = rs.getString("dto_" + tarifa.getText());
					}
					rs.close();
					st.close();
				}catch (SQLException e1){
					e1.printStackTrace();
					new LogWriter( e1.getStackTrace() );
				}
				
				total = recalculoTotal(codigoArticulo.getText(),tarifa.getText(),
						cajas.getText(),unidades.getText(),dto);
				total_iva = recalculoTotalIva(codigoArticulo.getText(),total);
			}
			String query = "UPDATE linea_pedido SET ";
			query += "cajas='" + cajas.getText() + "',";
			query += "unidades='" + unidades.getText() + "',";
			query += "observaciones='" + observaciones.getText() + "',";
			query += "dto_1='" + dto + "',";
			query += "tarifas='" + tarifa.getText() + "',";
			query += "total='" + total + "',";
			query += "total_iva='" + total_iva + "'";
			
			query += " WHERE ";
			query += condicionWhere;
			
			System.out.println( query);
			
			Statement sta;
			try {
				sta = conect.createStatement();
				sta.executeUpdate( query );
				sta.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
				new LogWriter( e1.getStackTrace() );
			}
			
			mostrarDatos(condicionWhere);
		}
	}
	
	
	/** Metodo que elimina la linea de pedido seleccionada */
//  =============================================================================			
	public void eliminarLineaPedido( Cargador cargador )
//  =============================================================================	
	{
		//if (model.getTabla().getSelectedRow() != -1) {
		int n = new QueryPane().consultar("Desea eliminar (S/N)");
		if (n == 0) {
			try {
				String query = "DELETE FROM linea_pedido WHERE ";
				query += condicionWhere;
				System.out.println(query);
				Statement s = conect.createStatement();
				s.executeUpdate(query);
				s.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
				new LogWriter( ex.getStackTrace() );
			}
			this.actualizar();
		}
		
	}

//  =============================================================================
	public double recalculoTotal(String cod,String tar, String caj, String unid, String dto)
//  =============================================================================
	{
		double total= 0;
		
		int tarifa = Integer.parseInt(tar);
		int cajas = Integer.parseInt(caj);
		int unidades = Integer.parseInt(unid);
		double descuento = Double.parseDouble(dto);
		
		if (tarifa != 0){
			try{
				Statement st = conect.createStatement();
				if ( tarifa != 0 ){
					String query = "SELECT PVP_"+tarifa+",DTO_"+tarifa+",UNIDADES_BULTO FROM ARTICULOS WHERE CODIGO ='"+cod+"'";
					ResultSet rs = st.executeQuery(query);
					if (rs.next()){
						if (cajas > 0 ) {
							unidades += cajas * rs.getInt("UNIDADES_BULTO"); 
						}
						total = unidades * rs.getDouble("pvp_"+tarifa);
						double temp = total * (descuento/100);
						total = total - temp;
						System.out.println("Total sin iva sin redondeo :" + total);
						total = Math.rint(total*10000)/10000;
					}else{
						System.out.println("Error en el query");
						return 0;
					}
				}else{
					total = 0;
				}
			}catch(Exception e){
				e.printStackTrace();
				new LogWriter( e.getStackTrace() );
			}
		}
		//System.out.println("Total sin iva: " + total);
		return total;
	}
	
//  =============================================================================
	public double recalculoTotalIva(String cod, double tot)
//  =============================================================================
	{
		double total = tot;
		
		try{
			Statement st = conect.createStatement();
			ResultSet rs = st.executeQuery("SELECT a.TIPO_IVA,b.PORC_IVA FROM ARTICULOS a, TIPOS_IVA b WHERE a.CODIGO ='" + cod + 
					"' AND b.CODIGO = a.TIPO_IVA");
			if (rs.next()){
				total += total * rs.getInt("PORC_IVA")/100;
			}else{
				return 0;
			}
		}catch(Exception e){
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
			
		}
		//System.out.println("Total con iva: " + total);
		return total;
	}



}

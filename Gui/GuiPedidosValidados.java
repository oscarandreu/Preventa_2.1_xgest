package Gui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import data.Cliente;

import log.LogWriter;

import system.Cargador;
import ERP.ClienteErp;
import Gui.system.InfoPane;
import Gui.system.QueryPane;
import bbdd.FrameTableExtendida;
import bbdd.InternalFrameTable;

public class GuiPedidosValidados extends GuiInternalFrame {

	private JPanel panel = null; // @jve:decl-index=0:visual-constraint="10,10"

	private JScrollPane pedidos = null;

	private JSplitPane jSplitPane = null;

	private JPanel jPanel1 = null;

	//private JButton eliminar = null;

	//private JButton modificar = null;

	private JButton cerrar = null;

	private JLabel jLaño = null;

	private JLabel jLnumeropedido = null;

	private JLabel jLobservaciones = null;

	private JTextField jTaño = null;

	private JTextField jTnumeropedido = null;

	private JTextField jTobservaciones = null;

	private JLabel jLdto1 = null;

	private JLabel jLtotalneto = null;

	private JLabel jLtotaliva = null;

	private JLabel jLdto2 = null;

	private JLabel jLdtopp = null;

	private JLabel jLtotal = null;

	private JTextField jTdto1 = null;

	private JTextField jTtotalneto = null;

	private JTextField jTtotaliva = null;

	private JTextField jTdto2 = null;

	private JTextField jTdtopp = null;

	private JTextField jTtotal = null;

	private JTabbedPane jTabbedPane = null;

	private JLabel jLtiva = null;

	private JTextField jTtiva = null;

	private JSplitPane jPanel3 = null;

	private JLabel jLabel = null;

	private JCheckBox jCheckBox = null;

	private JLabel jLabel10 = null;

	private JTextField comboClase = null;

	protected GuiLineasPedidos lineas;

	private JLabel jLabel1 = null;

	private JTextField serie = null;

	private JButton validar = null;

	private String condicionWhere;

	protected String query;

	private JPanel jPanel = null;

	private JPanel jPanel2 = null;

	private JLabel jLabel2 = null;

	private JTextField jTcodigo = null;

	private JLabel jLabel3 = null;

	private JTextField jTnombre = null;

	private JLabel ljFecha = null;

	private JTextField jTHora = null;

// ==================================================================
	public GuiPedidosValidados(Cargador cargador)
// ==================================================================
	{
		super(cargador);
		icono = "tab_comparativa.gif";
		toolTip = "Pedidos validados";
		titulo = "Pedidos validados";
		lineas = new GuiLineasPedidos(sys,true);
	}

	// ==================================================================
	protected JPanel getPanel()
	// ==================================================================
	{
		if (panel == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(37, 425, 205, 21));
			jLabel.setText("filas");
			panel = new JPanel();
			panel.setLayout(null);
			panel.setSize(new java.awt.Dimension(802,468));
			panel.add(getJSplitPane(), null);
		//	panel.add(getEliminar(), null);
		//	panel.add(getModificar(), null);
			panel.add(getCerrar(), null);
			panel.add(jLabel, null);
			filasImportadas = jLabel;
			filasImportadas.setText("Numero de Pedidos Validados : "
					+ model.numeroFilas);
		}
		return panel;
	}

	// ===========================================================================================
	protected JScrollPane getPedidos()
	// ===========================================================================================
	{
		//model = new InternalFrameTable(this);
		model = new FrameTableExtendida(this, new String[]{"Cliente"}, 1);
		// Configuracion de la tabla
		model.setPrimaryKeys(new String[] {"CODIGO_CLIENTE", "NUMERO_CABECERA", "SERIE" });
		model.setColumnas(new String[] { "a.NUMERO_CABECERA", "a.SERIE" });
		model.setColumnasFormateadas(new String[] { "Número", "Serie" });
		//,"Cliente" });
		model.setAnchoColumnas(new int[] { 40, 40 }); //, 200 });
		model.setDatabase("cabecera_pedido a"); //,dpersonales b");
		model.setWhere("a.verificado='V'"); // AND a.Codigo_cliente=b.codigo");
		model.setVerificado(new String[] { "a.verificado", "verificado" });
		// model.setOrder("numero_cabecera");
		pedidos = new JScrollPane(model.getTabla());
		model.tabla.setAutoResizeMode( model.tabla.AUTO_RESIZE_ALL_COLUMNS );
		pedidos.setHorizontalScrollBarPolicy(pedidos.HORIZONTAL_SCROLLBAR_ALWAYS);
		pedidos.setBounds(new java.awt.Rectangle(0, 0, 279, 390));
		return pedidos;
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
					+ "a.NUMERO_CABECERA,a.SERIE,a.ALBARAN,a.Clase,a.Codigo_cliente,"
					+ "a.Dto_1,a.Dto_2,a.Dto_pp,a.FECHA,a.observaciones,a.TOTAL_IVA "
					+ "FROM cabecera_pedido a "
					+ "WHERE "+ where;

			ResultSet resul = sta.executeQuery(query);
			
			
			if (resul.next()) {
				String codigoCliente = resul.getString("codigo_cliente");
				
				Cliente cliErp = new ClienteErp();
				String queryErp = cliErp.getQueryClienteByCodigo(codigoCliente);
				
				Statement stErp = sys.getConexionErp().createStatement();
				ResultSet rsErp = stErp.executeQuery(queryErp);
				if (rsErp.next()){
					cliErp.setClienteFromErp(rsErp);
				}

				this.jTaño.setText(""+resul.getDate("fecha"));
				this.jTHora.setText(""+resul.getTime("fecha"));
				this.jTnumeropedido.setText(resul.getString("numero_cabecera"));
				this.jTobservaciones.setText(resul.getString("observaciones"));
				this.serie.setText(resul.getString("serie"));
				this.jTdto1.setText(resul.getString("dto_1"));
				this.jTdto2.setText(resul.getString("dto_2"));
				this.jTdtopp.setText(resul.getString("dto_pp"));
				this.jTcodigo.setText(codigoCliente);
				this.jTnombre.setText(cliErp.getNombre());
				if ("S".equals(resul.getString("albaran"))) {
					jCheckBox.setSelected(true);
				}
				this.comboClase.setText( resul.getString("clase") ); 
				lineas.actualizar(" a.NUMERO_CABECERA ='"
						+ resul.getString("numero_cabecera") + "'"
						+ " AND a.SERIE = '" + resul.getString("serie") + "'");
				
				jTtotal.setText(resul.getString("total_iva"));
				Double totalConIva = new Double(jTtotal.getText());
				if (totalConIva == 0) {
					jTtotaliva.setText("0");
					jTtotalneto.setText("0");
					
				}else{
					
					/* calculamos el total neto (ARG == 0) de todas las lineas de pedido asociadas a la cabecera 
					 * recorriendo todas las lineas de pedido de la BDD asociadas a la cabecera */
					//double tempNet =  calcularTotal( 0,jTnumeropedido.getText(),serie.getText() );
					double tempNet =  calcularTotalNeto( jTnumeropedido.getText(),serie.getText() );
					System.out.println("Total cabecera: " + tempNet);
					jTtotalneto.setText( Double.toString(Math.rint(tempNet*100)/100) );
					Double totalNeto = new Double(jTtotalneto.getText());
					//Double totalConIva = new Double(jTtotal.getText());
					
					Double totalIva = new Double(totalConIva - totalNeto);
					jTtotaliva.setText( Double.toString(Math.rint(totalIva*100)/100) );
				}
				
				
				/* calculamos el total del iva (ARG == 1) de todas las lineas de pedido asociadas a la cabecera
				 * recorriendo todas las lineas de pedido de la BDD asociadas a la cabecera 
				double tempIva = calcularTotal( 1,jTnumeropedido.getText(),serie.getText() );
				System.out.println("Total cabecera con iva:" + tempIva);
				jTtotaliva.setText( Double.toString(Math.rint(tempIva*100)/100) );
				*/
				System.out.println("Total global: " + new Double(jTtotalneto.getText()).toString() + " + " 
						+ new Double(jTtotaliva.getText()).toString() + " = "
						);
				//Double total = new Double(jTtotalneto.getText()) + new Double(jTtotaliva.getText());
				//jTtotal.setText( sys.formatearDouble2Dec( Double.toString(tempNet + tempIva) ) );
				//jTtotal.setText(resul.getString("total_iva"));
				
			
			}
			resul.close();
			sta.close();
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setBounds(new java.awt.Rectangle(3, 2, 786, 415));
			jSplitPane.setDividerLocation(320);
			jSplitPane.setDividerSize(5);
			jSplitPane.setRightComponent(getJTabbedPane());
			jSplitPane.setLeftComponent(getPedidos());
			jSplitPane.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
				public void propertyChange(
						java.beans.PropertyChangeEvent e) {
					if ((e.getPropertyName()
							.equals("lastDividerLocation"))) {
						model.tabla
						.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
					}
				}
			});
		}
		return jSplitPane;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			ljFecha = new JLabel();
			ljFecha.setBounds(new java.awt.Rectangle(285,45,34,20));
			ljFecha.setText("Hora");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(121,189,42,18));
			jLabel1.setToolTipText("");
			jLabel1.setText("Serie");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new java.awt.Rectangle(28,189,36,17));
			jLabel10.setText("Clase");
			jLtiva = new JLabel();
			jLtiva.setBounds(new java.awt.Rectangle(340,158,45,19));
			jLtiva.setText("T.IVA%");
			jLtotal = new JLabel();
			jLtotal.setText("TOTAL       :");
			jLtotal.setBounds(new java.awt.Rectangle(264,265,71,19));
			
			//fuera
			jLdtopp = new JLabel();
			jLdtopp.setText("Dto PP");
			jLdtopp.setBounds(new java.awt.Rectangle(215,158,42,19));
			jLdtopp.setVisible(false);
			//fuera
			jLdto2 = new JLabel();
			jLdto2.setText("Dto2%");
			jLdto2.setSize(new java.awt.Dimension(36, 19));
			jLdto2.setLocation(new java.awt.Point(116,158));
			jLdto2.setVisible(false);
			
			jLtotaliva = new JLabel();
			jLtotaliva.setText("Total IVA   :");
			jLtotaliva.setBounds(new java.awt.Rectangle(264,240,71,19));
			jLtotalneto = new JLabel();
			jLtotalneto.setText("Total Neto :");
			jLtotalneto.setBounds(new java.awt.Rectangle(264,215,71,19));
			jLdto1 = new JLabel();
			jLdto1.setText("Dto1%");
			jLdto1.setBounds(new java.awt.Rectangle(18,158,36,19));
			jLobservaciones = new JLabel();
			jLobservaciones.setBounds(new java.awt.Rectangle(13,59,119,16));
			jLobservaciones.setText("Observaciones");
			jLnumeropedido = new JLabel();
			jLnumeropedido.setText("Numero Pedido");
			jLnumeropedido.setBounds(new java.awt.Rectangle(13,19,90,19));
			jLaño = new JLabel();
			jLaño.setText("Fecha");
			jLaño.setSize(new java.awt.Dimension(39,19));
			jLaño.setLocation(new java.awt.Point(282,18));
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setPreferredSize(new java.awt.Dimension(90, 150));
			jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pedido", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), new java.awt.Color(51,51,51)));
			jPanel1.setBounds(new java.awt.Rectangle(2,83,451,301));
			jPanel1.add(jLaño, null);
			jPanel1.add(jLnumeropedido, null);
			jPanel1.add(jLobservaciones, null);
			jPanel1.add(jLdto1, null);
			jPanel1.add(jLtotalneto, null);
			jPanel1.add(jLtotaliva, null);
			jPanel1.add(jLdto2, null);
			jPanel1.add(jLdtopp, null);
			jPanel1.add(jLtotal, null);
			jPanel1.add(jLtiva, null);
			jPanel1.add(getJTaño(), null);
			jPanel1.add(getJTnumeropedido(), null);
			jPanel1.add(getJTobservaciones(), null);
			jPanel1.add(getJTdto1(), null);
			jPanel1.add(getJTtotalneto(), null);
			jPanel1.add(getJTtotaliva(), null);
			jPanel1.add(getJTdto2(), null);
			jPanel1.add(getJTdtopp(), null);
			jPanel1.add(getJTtotal(), null);
			jPanel1.add(getJTtiva(), null);
			jPanel1.add(getJCheckBox(), null);
			jPanel1.add(jLabel10, null);
			jPanel1.add(getComboClase(), null);
			jPanel1.add(jLabel1, null);
			jPanel1.add(getSerie(), null);
//			jPanel1.add(getValidar(), null);
			jPanel1.add(ljFecha, null);
			jPanel1.add(getJTHora(), null);
		}
		return jPanel1;
	}

/*	private JButton getEliminar() {
		if (eliminar == null) {
			eliminar = new JButton();
			eliminar.setText("eliminar");
			eliminar.setLocation(new java.awt.Point(314, 425));
			eliminar.setSize(new java.awt.Dimension(100, 26));
			eliminar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (model.tabla.getSelectedRow() != -1) {
						//La pestaña seleccionada es la de la cabecera de pedidos
						if ( jTabbedPane.getSelectedIndex() == 0 ){
							
							System.out.println("Eliminando cabecera pedido");
							int fila = model.tabla.getSelectedRow();
							int n = new QueryPane().consultar("Desea eliminar (S/N)");
							if (n == 0) {
								try {
									query = "DELETE FROM cabecera_pedido WHERE ";
									query += condicionWhere;
									System.out.println(query);
									Statement s = conect.createStatement();
									s.executeUpdate(query);
									s.close();
								} catch (SQLException ex) {
									ex.printStackTrace();
								}
								cargador.actualizarVentanas();
								if (model.numeroFilas == 0) {
									validar.setEnabled(false);
								}
							}
						}
						//La pestaña seleccionada es la de lineas de pedidos
						else{
							System.out.println("Eliminando linea pedido");
							lineas.eliminarLineaPedido( cargador );
						}
					}else{
						new InfoPane("ATENCIÓN","Seleccione un Pedido");
					}
				}
			});
		}
		return eliminar;
	}*/


	private JButton getCerrar() {
		if (cerrar == null) {
			cerrar = new JButton();
			cerrar.setText("Cerrar");
			cerrar.setLocation(new java.awt.Point(554, 425));
			cerrar.setSize(new java.awt.Dimension(100, 26));
			cerrar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cerrar();
				}
			});
		}
		return cerrar;
	}


	private JTextField getJTaño() {
		if (jTaño == null) {
			jTaño = new JTextField();
			jTaño.setLocation(new java.awt.Point(320,18));
			jTaño.setSize(new java.awt.Dimension(111,19));
			jTaño.setEditable(false);
		}
		return jTaño;
	}

	private JTextField getJTnumeropedido() {
		if (jTnumeropedido == null) {
			jTnumeropedido = new JTextField();
			jTnumeropedido.setBounds(new java.awt.Rectangle(102,19,123,19));
			jTnumeropedido.setEditable(false);
		}
		return jTnumeropedido;
	}

	private JTextField getJTobservaciones() {
		if (jTobservaciones == null) {
			jTobservaciones = new JTextField();
			jTobservaciones.setBounds(new java.awt.Rectangle(13,79,427,70));
			jTobservaciones.setEditable(false);
		}
		return jTobservaciones;
	}

	private JTextField getJTdto1() {
		if (jTdto1 == null) {
			jTdto1 = new JTextField();
			jTdto1.setLocation(new java.awt.Point(60,158));
			jTdto1.setSize(new java.awt.Dimension(50, 19));
			jTdto1.setEditable(false);
		}
		return jTdto1;
	}

	private JTextField getJTtotalneto() {
		if (jTtotalneto == null) {
			jTtotalneto = new JTextField();
			jTtotalneto.setBounds(new java.awt.Rectangle(339,215,100,19));
			jTtotalneto.setEditable(false);
		}
		return jTtotalneto;
	}

	private JTextField getJTtotaliva() {
		if (jTtotaliva == null) {
			jTtotaliva = new JTextField();
			jTtotaliva.setBounds(new java.awt.Rectangle(339,240,100,19));
			jTtotaliva.setEditable(false);
		}
		return jTtotaliva;
	}

	private JTextField getJTdto2() {
		if (jTdto2 == null) {
			jTdto2 = new JTextField();
			jTdto2.setLocation(new java.awt.Point(159,158));
			jTdto2.setSize(new java.awt.Dimension(50, 19));
			jTdto2.setEditable(false);
			jTdto2.setVisible(false);
		}
		return jTdto2;
	}

	private JTextField getJTdtopp() {
		if (jTdtopp == null) {
			jTdtopp = new JTextField();
			jTdtopp.setBounds(new java.awt.Rectangle(266,158,50,19));
			jTdtopp.setEditable(false);
			jTdtopp.setVisible(false);
		}
		return jTdtopp;
	}

	private JTextField getJTtotal() {
		if (jTtotal == null) {
			jTtotal = new JTextField();
			jTtotal.setBounds(new java.awt.Rectangle(339,265,100,19));
			jTtotal.setEditable(false);
		}
		return jTtotal;
	}

	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setBounds(new java.awt.Rectangle(21, 14, 459, 393));
			jTabbedPane.addTab("Cabecera del pedido", null, getJPanel() );
			jTabbedPane.addTab("Lineas del pedido", null, getJPanel3());
		}
		return jTabbedPane;
	}


	private JTextField getJTtiva() {
		if (jTtiva == null) {
			jTtiva = new JTextField();
			jTtiva.setBounds(new java.awt.Rectangle(390,158,38,19));
			jTtiva.setEditable(false);
		}
		return jTtiva;
	}

	private JSplitPane getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = lineas.getJSplitPane();
		}
		return jPanel3;
	}

	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new java.awt.Rectangle(172,44,78,27));
			jCheckBox.setEnabled(false);
			jCheckBox.setName("albaran");
			jCheckBox.setText("Albarán");
			jCheckBox.setVisible(false);
		}
		return jCheckBox;
	}

	private JTextField getComboClase() {
		if (comboClase == null) {
			comboClase = new JTextField();
			comboClase.setEditable(false);
			comboClase.setBounds(new java.awt.Rectangle(64,189,52,17));
		}
		return comboClase;
	}

	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getSerie() {
		if (serie == null) {
			serie = new JTextField();
			serie.setBounds(new java.awt.Rectangle(166,189,79,19));
			serie.setEditable(false);
		}
		return serie;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJPanel2(), null);
			jPanel.add(getJPanel1(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(8,45,45,16));
			jLabel3.setText("Nombre");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(11,24,39,16));
			jLabel2.setText("Codigo");
			jPanel2 = new JPanel();
			jPanel2.setLayout(null);
			jPanel2.setBounds(new java.awt.Rectangle(2,0,452,83));
			jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cliente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			jPanel2.add(jLabel2, null);
			jPanel2.add(getJTcodigo(), null);
			jPanel2.add(jLabel3, null);
			jPanel2.add(getJTnombre(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTcodigo() {
		if (jTcodigo == null) {
			jTcodigo = new JTextField();
			jTcodigo.setBounds(new java.awt.Rectangle(53,22,111,21));
			jTcodigo.setEditable(false);
		}
		return jTcodigo;
	}

	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTnombre() {
		if (jTnombre == null) {
			jTnombre = new JTextField();
			jTnombre.setBounds(new java.awt.Rectangle(54,44,368,20));
			jTnombre.setEditable(false);
		}
		return jTnombre;
	}

	/**
	 * This method initializes jTextField2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTHora() {
		if (jTHora == null) {
			jTHora = new JTextField();
			jTHora.setBounds(new java.awt.Rectangle(321,45,110,20));
			jTHora.setPreferredSize(new java.awt.Dimension(4,20));
			jTHora.setEditable(false);
		}
		return jTHora;
	}
	
	/** 
	 * Metodo para calcular el total neto o con iva de los productos
	 * @deprecated 
	 */

//  ======================================================================	
	public double calcularTotal(int op, String cabec, String serie)
//	======================================================================
	{
		double res = 0;
		
		switch (op){
		case 0:		// calculo del total neto de todas las lineas de pedido
			
			// primero obtenemos nos diversos descuentos del cliente
			double dto1 = Double.parseDouble(jTdto1.getText());
			double dto2 = Double.parseDouble(jTdto2.getText());
			double dtopp = Double.parseDouble(jTdtopp.getText());
			
			// recorremos todas las líneas sacando su total neto y aplicandoles descuentos, para despues sumarlas
			try{
				Statement st = conect.createStatement();
				ResultSet rs = st.executeQuery( "SELECT TOTAL " +
						"FROM linea_pedido " +
						"WHERE numero_cabecera = '" + cabec + "' AND serie='" + serie + "'");
				while ( rs.next() ){
					double totlin = rs.getDouble("total");
					double tmp = totlin * (dto1/100);
					totlin = totlin - tmp;
					tmp = totlin * (dto2/100);
					totlin = totlin - tmp;
					tmp = totlin * (dtopp/100);
					totlin = totlin - tmp;
					System.out.println("Total sin iva linea:" + totlin);
					res += totlin;
				}
				System.out.println("Total sin iva pedido:" + res);
				rs.close();
				st.close();
			}catch (SQLException ex){
				ex.printStackTrace();
				new LogWriter( ex.getStackTrace() );
			}
			
			break;
			
		case 1:		// calculo del total de iva de todas las lineas de pedido
			String iva_fijo = null;
			String clase = null;
			try{
				Statement st = conect.createStatement();
				ResultSet rs = st.executeQuery("SELECT b.iva_fijo, a.clase " +
						"FROM cabecera_pedido a, dpersonales b " +
						"WHERE a.numero_cabecera ='" + cabec + "' AND serie='" + serie + "'" + 
						" AND a.codigo_cliente = b.codigo ");
				if (rs.next()){
					iva_fijo = rs.getString("iva_fijo");
					clase = rs.getString("clase");
				}
				rs.close();
				st.close();
			}catch(SQLException e1){
				e1.printStackTrace();
				new LogWriter( e1.getStackTrace() );
			}
			
			// primero vemos si el cliente es de tipo N o no
			if (comboClase.getText().equals("N")){
				
				// si lo es no se le aplica IVA
				System.out.println("Tipo : N");
				res = 0;
				//res = Double.parseDouble( jTtotalneto.getText() );
				
			}else{
				
				// si no lo es, vemos si el Cliente tiene IVA fijo o no
				System.out.println("Tipo : no N, iva fijo:" + iva_fijo);
				if ( iva_fijo.equals("0") || iva_fijo.equals("4")){
					System.out.println("No tiene iva fijo");
					
					// no tiene iva fijo
					try{
						Statement st = conect.createStatement();
						
						ResultSet rs = st.executeQuery("SELECT c.porc_iva, a.total " +
								"FROM linea_pedido a, articulos b, tipos_iva c " +
								"WHERE a.numero_cabecera ='" + cabec + "' " +
							"AND a.serie ='" + serie + "' " +
						"AND a.codigo_articulo = b.codigo AND b.tipo_iva = c.codigo");
						while (rs.next()){
							System.out.println("Aplicando a neto:"+ rs.getString("total") + " iva:" + rs.getString("porc_iva"));
							double totn = Double.parseDouble(rs.getString("total"));
							if (iva_fijo.equals("0")){
								double porciva = Double.parseDouble(rs.getString("porc_iva"));
								res += totn*(porciva/100);
							}else if(iva_fijo.equals("4")){
								res = 0;
							}
							
						}
						rs.close();
						st.close();
					}catch(SQLException ex){
						ex.printStackTrace();
						new LogWriter( ex.getStackTrace() );
					}
					
				}else{
					System.out.println("SI tiene iva fijo:" + iva_fijo);
					// tiene iva fijo
					try{
						Statement st = conect.createStatement();
						ResultSet rs = st.executeQuery("SELECT total FROM linea_pedido " +
								"WHERE numero_cabecera ='" + cabec + "'");
						double ivafijo = Double.parseDouble(iva_fijo);
						while (rs.next()){
							System.out.println("Aplicando a neto:"+ rs.getString("total") + " iva fijo:" + ivafijo);
							double totn = Double.parseDouble(rs.getString("total"));
							res += totn*(ivafijo/100);
						}
						rs.close();
						st.close();
					}catch(SQLException ex){
						ex.printStackTrace();
						new LogWriter( ex.getStackTrace() );
					}
					
				}
				
				
			}
			
			
			break;
			
		}
		return res;
		
	}
	
	/**
	 * Metodo para calcular el numero de unidades totales de una linea de pedido, teniendo en cuenta
	 * las cajas pedidas, las unidades por bulto del articulo concreto y las unidades sueltas pedidas. 
	 */
//  ======================================================================	
	public int calculoUnidades(String caj, String unidBulto, String unid)
//  ======================================================================	
	{	
		System.out.println(new Integer(caj) + ":" +sys.formatFloatToInteger( unidBulto ) + ":" + new Integer(unid));
		return ( new Integer(caj) * sys.formatFloatToInteger(unidBulto) + new Integer(unid) );
	}
	
	/** 
	 * Metodo para calcular el total neto o con iva de los productos 
	 */

//  ======================================================================	
	public double calcularTotalNeto(String cabec, String serie)
//	======================================================================
	{
		double res = 0;
		
		// primero obtenemos los diversos descuentos del cliente
		double dto1 = Double.parseDouble(jTdto1.getText());
		double dto2 = Double.parseDouble(jTdto2.getText());
		double dtopp = Double.parseDouble(jTdtopp.getText());
		
		// recorremos todas las líneas sacando su total neto y aplicandoles descuentos, para despues sumarlas
		try{
			Statement st = conect.createStatement();
			ResultSet rs = st.executeQuery( "SELECT TOTAL " +
					"FROM linea_pedido " +
					"WHERE numero_cabecera = '" + cabec + "' AND serie='" + serie + "'");
			while ( rs.next() ){
				double totlin = rs.getDouble("total");
				double tmp = totlin * (dto1/100);
				totlin = totlin - tmp;
				tmp = totlin * (dto2/100);
				totlin = totlin - tmp;
				tmp = totlin * (dtopp/100);
				totlin = totlin - tmp;
				System.out.println("Total sin iva linea:" + totlin);
				res += totlin;
			}
			System.out.println("Total sin iva pedido:" + res);
			rs.close();
			st.close();
		}catch (SQLException ex){
			ex.printStackTrace();
			new LogWriter( ex.getStackTrace() );
		}

		return res;
		
	}
}


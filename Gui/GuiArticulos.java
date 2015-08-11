package Gui;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import log.LogWriter;

import system.Cargador;
import bbdd.InternalFrameTable;

/**
 * es la gui de gestion del servidor sonde se le meteria la direccion del servidor
 * @author Elena
 *
 */
 public class GuiArticulos extends GuiInternalFrame{
	
//		Declaracion de Botones
		private JButton cerrar = null;
		private JTabbedPane jTabbedPane = null;
		 
		 
//      Declaracion de jpanel 
		private JPanel panel = null;  //  @jve:decl-index=0:visual-constraint="10,10"
		// Declaracion jScrollPane
		private JScrollPane jScrollPane = null;
		private JSplitPane jSplitPane = null;
		
		// Declaracion campos pestaña 1
		private JPanel tabPanel1 = null;
		
		private JLabel jLcodigo = null;
		private JLabel jLcodigo2 = null;
		private JLabel jLnombre = null;
		private JLabel jLmarca = null;
		private JLabel jLseccion = null;
		private JLabel jLfamilia = null;
		private JLabel jLsubfamilia = null;
		private JLabel jLiva = null;
		private JTextField jTcodigo = null;
		private JTextField jTnombre = null;
		private JTextField jTmarca = null;
		private JTextField jTseccion = null;
		private JTextField jTfamilia = null;
		private JTextField jTsubfamilia = null;
		private JTextField jTiva = null;
		private JTextField jTcodigo2 = null;
		
//		 Declaracion campos pestaña 2
		private JPanel tabPanel2 = null;
		
		private JLabel jLtarifa = null;
		private JLabel jLmas_iva = null;
		private JLabel jLdto = null;
		private JLabel jLt1 = null;
		private JLabel jLt2 = null;
		private JLabel jLt3 = null;
		private JLabel jLt4 = null;
		private JLabel jLt5 = null;
			
		private JTextField jTt1_tarifa = null;
		private JTextField jTt2_tarifa = null;
		private JTextField jTt3_tarifa = null;
	    private JTextField jTt4_tarifa = null;
	    private JTextField jTt5_tarifa = null;
	    
	    private JTextField jTt1_iva = null;
		private JTextField jTt2_iva = null;
		private JTextField jTt3_iva = null;
		private JTextField jTt4_iva = null;
		private JTextField jTt5_iva = null;
		
		private JTextField jTt1_dto = null;
		private JTextField jTt2_dto = null;
		private JTextField jTt3_dto = null;
		private JTextField jTt4_dto = null;
		private JTextField jTt5_dto = null;
		
//		Declaracion campos del frame pestaña 2
		private JPanel jtab2_panel2 = null;
		
		private JLabel jLtarifa1 = null;
		private JLabel jLtarifa2 = null;
		private JLabel jLtarifa3 = null;
		private JLabel jLtarifa4 = null;
		private JLabel jLtarifa5 = null;
		
		private JTextField jTtarifa1_desde = null;
		private JTextField jTtarifa1_hasta = null;
		private JTextField jTtarifa2_desde = null;
		private JTextField jTtarifa2_hasta = null;
		private JTextField jTtarifa3_desde = null;
		private JTextField jTtarifa3_hasta = null;
		private JTextField jTtarifa4_desde = null;
		private JTextField jTtarifa4_hasta = null;
		private JTextField jTtarifa5_desde = null;
		private JTextField jTtarifa5_hasta = null;
		
//		 Declaracion campos pestaña 3
		private JPanel jtab3_panel2 = null;
		
		private JLabel jLbulto = null;
		private JLabel jLstock = null;
		private JLabel jLvirtual = null;
		private JLabel jLservir = null;
		private JLabel jLrecibir = null;
		private JLabel jLfecha_p_recibir = null;
		private JLabel jLobservaciones = null;
		private JLabel jLavisos = null;
		
		private JTextField jTbulto = null;
		private JTextField jTstock = null;
		private JTextField jTvirtual = null;
		private JTextField jTservir = null;
		private JTextField jTrecibir = null;
		private JTextField jTfecha_p_recibir = null;
		private JTextArea jTobservaciones = null;
		private JTextArea jTavisos = null;

		private JLabel foto = null;
		
//     	Contador totales	
		private JLabel jLabel = null;
	   
	
//	==================================================================
	public GuiArticulos( Cargador cargador ) 
//	==================================================================
	{
		super( cargador );
		icono = "tab_comparativa.gif";
		toolTip = "Muestra los articulos importados";
		titulo = "Articulos";
	}

	
//	==================================================================
	protected JPanel getPanel() 
//	==================================================================
	{
		if (panel == null) {
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(15,423,248,29));
			jLabel.setText("");
			panel = new JPanel();
			panel.setLayout(null);
			panel.setSize(new java.awt.Dimension(929,481));
			panel.add(getJSplitPane(), null);
			//panel.add(getEliminar(), null);
			//panel.add(getValidar(), null);
			panel.add(getCerrar(), null);
			panel.add(jLabel, null);
			filasImportadas = jLabel;
			filasImportadas.setText("Numero de Articulos Importados : "+model.numeroFilas);
		}
		frame.setBounds(new java.awt.Rectangle(30,30,900,500));
		return panel;
	}
//	===========================================================================================
	protected JScrollPane getJScrollPane() 
//	===========================================================================================
	{
		model = new InternalFrameTable( this){
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		//Configuracion de la tabla
		model.setColumnas( new String[]{"codigo","nombre"} );
		model.setColumnasFormateadas(  new String[]{"Código","Nombre"} );
		model.setAnchoColumnas( new int[]{120,330} );
		model.setDatabase("articulos");
		jScrollPane = new JScrollPane( model.getTabla() );
		
		jScrollPane.setHorizontalScrollBarPolicy(jScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jScrollPane.setBounds(new java.awt.Rectangle(0,0,279,390));
		return jScrollPane;
	}

	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setBounds(new java.awt.Rectangle(3,2,877,400));
			jSplitPane.setDividerLocation(400);
			jSplitPane.setDividerSize(5);
			jSplitPane.setLeftComponent( getJScrollPane() );
			jSplitPane.setRightComponent( getJTabbedPane() );
		}
		return jSplitPane;
	}

	//	===========================================================================================
	public void mostrarDatos(String where )
//	===========================================================================================
	{
			try {
				Statement sta = conect.createStatement();
				ResultSet resul = sta.executeQuery("SELECT * FROM articulos WHERE "+where);
				if( resul.next() ){
						this.jTcodigo.setText(resul.getString("codigo"));
						this.jTcodigo2.setText(resul.getString("codigo2"));
						this.jTnombre.setText(resul.getString("nombre"));
						this.jTmarca.setText(resul.getString("marca"));
						this.jTseccion.setText(resul.getString("seccion"));
						this.jTfamilia.setText(resul.getString("familia"));
						this.jTsubfamilia.setText(resul.getString("subfamilia"));
						this.jTiva.setText(resul.getString("tipo_iva"));
						this.jTobservaciones.setText(resul.getString("observaciones"));
						this.jTavisos.setText(resul.getString("aviso"));
						this.jTtarifa1_desde.setText(resul.getString("ESC_1_DESDE"));
						this.jTtarifa1_hasta.setText(resul.getString("ESC_1_hasta"));
						this.jTtarifa2_desde.setText(resul.getString("ESC_2_DESDE"));
						this.jTtarifa2_hasta.setText(resul.getString("ESC_2_hasta"));
						this.jTtarifa3_desde.setText(resul.getString("ESC_3_DESDE"));
						this.jTtarifa3_hasta.setText(resul.getString("ESC_3_hasta"));
						this.jTtarifa4_desde.setText(resul.getString("ESC_4_DESDE"));
						this.jTtarifa4_hasta.setText(resul.getString("ESC_4_hasta"));
						this.jTtarifa5_desde.setText(resul.getString("ESC_5_DESDE"));
						this.jTtarifa5_hasta.setText(resul.getString("ESC_5_hasta"));
						this.jTtarifa4_desde.setText(resul.getString("ESC_4_DESDE"));
						this.jTbulto.setText(resul.getString("UNIDADES_BULTO"));
						//this.jTfecha_p_recibir.setText(resul.getString("FECHA_PEND_REC"));
						//this.jTrecibir.setText(resul.getString("PENDIENTE_REC"));
						//this.jTservir.setText(resul.getString("PENDIENTE_SERVIR"));
						//this.jTstock.setText(resul.getString("stock"));	
						this.jTt1_tarifa.setText(resul.getString("pvp_1"));
						this.jTt2_tarifa.setText(resul.getString("pvp_2"));
						this.jTt3_tarifa.setText(resul.getString("pvp_3"));
						this.jTt4_tarifa.setText(resul.getString("pvp_4"));
						this.jTt5_tarifa.setText(resul.getString("pvp_5"));
						this.jTt1_dto.setText(resul.getString("dto_1"));
						this.jTt2_dto.setText(resul.getString("dto_2"));
						this.jTt3_dto.setText(resul.getString("dto_3"));
						this.jTt4_dto.setText(resul.getString("dto_4"));
						this.jTt5_dto.setText(resul.getString("dto_5"));
						//this.jTvirtual.setText(resul.getInt("stock")-resul.getInt("PENDIENTE_SERVIR")+resul.getInt("PENDIENTE_REC")+"");
						
						try{
							// Seleccionamos la imagen almacenada en Tecnopolis
							Statement st = sys.getConexionErp().createStatement();
							ResultSet rst = st.executeQuery("select imagen, codigo from articulos where codigo = " 
									+ resul.getString("codigo") + " AND imagen <> 'null'");
							
							if ( rst.next()){
								Blob blob = rst.getBlob("imagen");
								int iLength = (int)(blob.length());
								if (iLength > 10){
									// Creamos una imagen con el tipo BLOB de la imagen
									ImageIcon ii = new ImageIcon( blob.getBytes( 1, iLength ) );
									
									foto.setIcon( ii );
								}
								else{
									foto.setIcon( null );
								}
							}else {
								foto.setIcon( null );
							}
							rst.close();
							st.close();
						}catch (Exception ex){
							ex.printStackTrace();
							new LogWriter( ex.getStackTrace() );
						}

						
						
						resul = sta.executeQuery("select porc_iva from tipos_iva where codigo="+resul.getString("tipo_iva"));
						if(resul.next()){
							this.jTiva.setText(resul.getString("porc_iva"));
							//double valor=(100+resul.getDouble("porc_iva"))/100;
							this.jTt1_iva.setText( ""+Math.rint(Double.parseDouble(jTt1_tarifa.getText())*100 )/100 );
							this.jTt2_iva.setText( ""+Math.rint(Double.parseDouble(jTt2_tarifa.getText())*100 )/100 );
							this.jTt3_iva.setText( ""+Math.rint(Double.parseDouble(jTt3_tarifa.getText())*100 )/100 );
							this.jTt4_iva.setText( ""+Math.rint(Double.parseDouble(jTt4_tarifa.getText())*100 )/100 );
							this.jTt5_iva.setText( ""+Math.rint(Double.parseDouble(jTt5_tarifa.getText())*100 )/100 );
						}	
					}	
				resul.close();
				sta.close();
			} catch (SQLException e) {
				e.printStackTrace();
				new LogWriter( e.getStackTrace() );
			}
		
	}

	
	
	private JButton getCerrar() {
		if (cerrar == null) {
			cerrar = new JButton();
			cerrar.setText("Cerrar");
			cerrar.setLocation(new java.awt.Point(632,429));
			cerrar.setSize(new java.awt.Dimension(100,26));
			cerrar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cerrar();
				}
			});
		}
		return cerrar;
	}
	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("Datos Gnerales", null, getTabPanel1(), null);
			jTabbedPane.addTab("Tarifas", null, getTabPanel2(), null);
			//jTabbedPane.addTab("Stock", null, getJtab3_panel2(), null);
			jTabbedPane.addTab("Otros", null, getJtab3_panel2(), null);
		}
		return jTabbedPane;
	}
	/**
	 * This method initializes tabPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getTabPanel1() {
		if (tabPanel1 == null) {
			foto = new JLabel();
			foto.setBounds(new java.awt.Rectangle(153,246,132,96));
			foto.setText("Foto");
			jLiva = new JLabel();
			jLiva.setText("Iva");
			jLiva.setBounds(new java.awt.Rectangle(16,168,61,17));
			jLsubfamilia = new JLabel();
			jLsubfamilia.setText("Subfamilia");
			jLsubfamilia.setBounds(new java.awt.Rectangle(16,143,60,19));
			jLfamilia = new JLabel();
			jLfamilia.setText("Familia:");
			jLfamilia.setBounds(new java.awt.Rectangle(16,118,59,19));
			jLseccion = new JLabel();
			jLseccion.setText("Seccion");
			jLseccion.setBounds(new java.awt.Rectangle(16,93,51,20));
			jLmarca = new JLabel();
			jLmarca.setText("Marca:");
			jLmarca.setBounds(new java.awt.Rectangle(16,68,45,19));
			jLnombre = new JLabel();
			jLnombre.setText("Nombre");
			jLnombre.setBounds(new java.awt.Rectangle(16,43,50,19));
			jLcodigo2 = new JLabel();
			jLcodigo2.setText("Codigo2:");
			jLcodigo2.setBounds(new java.awt.Rectangle(211,13,55,19));
			jLcodigo = new JLabel();
			jLcodigo.setText("Codigo:");
			jLcodigo.setBounds(new java.awt.Rectangle(16,13,45,19));
			tabPanel1 = new JPanel();
			tabPanel1.setLayout(null);
			tabPanel1.add(jLcodigo, null);
			tabPanel1.add(jLcodigo2, null);
			tabPanel1.add(jLnombre, null);
			tabPanel1.add(jLmarca, null);
			tabPanel1.add(jLseccion, null);
			tabPanel1.add(jLfamilia, null);
			tabPanel1.add(jLsubfamilia, null);
			tabPanel1.add(jLiva, null);
			tabPanel1.add(getJTcodigo(), null);
			tabPanel1.add(getJTnombre(), null);
			tabPanel1.add(getJTmarca(), null);
			tabPanel1.add(getJTseccion(), null);
			tabPanel1.add(getJTfamilia(), null);
			tabPanel1.add(getJTsubfamilia(), null);
			tabPanel1.add(getJTiva(), null);
			tabPanel1.add(getJTcodigo2(), null);
			tabPanel1.add(foto, null);
		}
		return tabPanel1;
	}
	/**
	 * This method initializes jTcodigo	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTcodigo() {
		if (jTcodigo == null) {
			jTcodigo = new JTextField();
			jTcodigo.setBounds(new java.awt.Rectangle(72,13,129,19));
			jTcodigo.setEditable(false);
		}
		return jTcodigo;
	}
	/**
	 * This method initializes jTnombre	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTnombre() {
		if (jTnombre == null) {
			jTnombre = new JTextField();
			jTnombre.setBounds(new java.awt.Rectangle(85,43,369,19));
			jTnombre.setEditable(false);
		}
		return jTnombre;
	}
	/**
	 * This method initializes jTmarca	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTmarca() {
		if (jTmarca == null) {
			jTmarca = new JTextField();
			jTmarca.setBounds(new java.awt.Rectangle(85,68,369,19));
			jTmarca.setEditable(false);
		}
		return jTmarca;
	}
	/**
	 * This method initializes jTseccion	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTseccion() {
		if (jTseccion == null) {
			jTseccion = new JTextField();
			jTseccion.setBounds(new java.awt.Rectangle(85,93,369,19));
			jTseccion.setEditable(false);
		}
		return jTseccion;
	}
	/**
	 * This method initializes jTfamilia	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTfamilia() {
		if (jTfamilia == null) {
			jTfamilia = new JTextField();
			jTfamilia.setBounds(new java.awt.Rectangle(85,118,369,19));
			jTfamilia.setEditable(false);
		}
		return jTfamilia;
	}
	/**
	 * This method initializes jTsubfamilia	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTsubfamilia() {
		if (jTsubfamilia == null) {
			jTsubfamilia = new JTextField();
			jTsubfamilia.setBounds(new java.awt.Rectangle(85,143,369,19));
			jTsubfamilia.setEditable(false);
		}
		return jTsubfamilia;
	}
	/**
	 * This method initializes jTiva	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTiva() {
		if (jTiva == null) {
			jTiva = new JTextField();
			jTiva.setBounds(new java.awt.Rectangle(85,168,369,19));
			jTiva.setEditable(false);
		}
		return jTiva;
	}
	/**
	 * This method initializes jTcodigo2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTcodigo2() {
		if (jTcodigo2 == null) {
			jTcodigo2 = new JTextField();
			jTcodigo2.setBounds(new java.awt.Rectangle(276,13,129,19));
			jTcodigo2.setEditable(false);
		}
		return jTcodigo2;
	}
	/**
	 * This method initializes tabPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getTabPanel2() {
		if (tabPanel2 == null) {
			jLt5 = new JLabel();
			jLt5.setText("T5");
			jLt5.setBounds(new java.awt.Rectangle(10,141,26,19));
			jLt4 = new JLabel();
			jLt4.setText("T4");
			jLt4.setBounds(new java.awt.Rectangle(10,117,26,19));
			jLt3 = new JLabel();
			jLt3.setText("T3");
			jLt3.setBounds(new java.awt.Rectangle(10,93,26,19));
			jLt2 = new JLabel();
			jLt2.setText("T2");
			jLt2.setSize(new java.awt.Dimension(26,19));
			jLt2.setLocation(new java.awt.Point(10,69));
			jLt1 = new JLabel();
			jLt1.setText("T1");
			jLt1.setSize(new java.awt.Dimension(26,19));
			jLt1.setLocation(new java.awt.Point(10,45));
			jLdto = new JLabel();
			jLdto.setText("Dto %");
			jLdto.setBounds(new java.awt.Rectangle(241,15,122,19));
			jLmas_iva = new JLabel();
			jLmas_iva.setBounds(new java.awt.Rectangle(143,16,82,19));
			jLmas_iva.setText("+iva");
			jLtarifa = new JLabel();
			jLtarifa.setText("Tarifa");
			jLtarifa.setSize(new java.awt.Dimension(83,19));
			jLtarifa.setLocation(new java.awt.Point(46,16));
			tabPanel2 = new JPanel();
			tabPanel2.setLayout(null);
			tabPanel2.add(jLtarifa, null);
			tabPanel2.add(jLmas_iva, null);
			tabPanel2.add(jLdto, null);
			tabPanel2.add(jLt1, null);
			tabPanel2.add(jLt2, null);
			tabPanel2.add(jLt3, null);
			tabPanel2.add(jLt4, null);
			tabPanel2.add(jLt5, null);
			tabPanel2.add(getJTt1_tarifa(), null);
			tabPanel2.add(getJTt2tarifa(), null);
			tabPanel2.add(getJTt3_tarifa(), null);
			tabPanel2.add(getJTt4_tarifa(), null);
			tabPanel2.add(getJTt1_iva(), null);
			tabPanel2.add(getJTt2_iva(), null);
			tabPanel2.add(getJTt3_iva(), null);
			tabPanel2.add(getJTt4_iva(), null);
			tabPanel2.add(getJTt1_dto(), null);
			tabPanel2.add(getJTt2_dto(), null);
			tabPanel2.add(getJTt3_dto(), null);
			tabPanel2.add(getJTt4_dto(), null);
			tabPanel2.add(getJTt5_tarifa(), null);
			tabPanel2.add(getJTt5_iva(), null);
			tabPanel2.add(getJTt5_dto(), null);
			tabPanel2.add(getJtab2_panel2(), null);
		}
		return tabPanel2;
	}
	/**
	 * This method initializes jTt1_tarifa	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTt1_tarifa() {
		if (jTt1_tarifa == null) {
			jTt1_tarifa = new JTextField();
			jTt1_tarifa.setLocation(new java.awt.Point(46,45));
			jTt1_tarifa.setSize(new java.awt.Dimension(84,19));
			jTt1_tarifa.setEditable(false);
		}
		return jTt1_tarifa;
	}
	/**
	 * This method initializes jTt2tarifa	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTt2tarifa() {
		if (jTt2_tarifa == null) {
			jTt2_tarifa = new JTextField();
			jTt2_tarifa.setBounds(new java.awt.Rectangle(46,69,84,19));
			jTt2_tarifa.setEditable(false);
		}
		return jTt2_tarifa;
	}
	/**
	 * This method initializes jTt3_tarifa	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTt3_tarifa() {
		if (jTt3_tarifa == null) {
			jTt3_tarifa = new JTextField();
			jTt3_tarifa.setBounds(new java.awt.Rectangle(46,93,84,19));
			jTt3_tarifa.setEditable(false);
		}
		return jTt3_tarifa;
	}
	/**
	 * This method initializes jTt4_tarifa	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTt4_tarifa() {
		if (jTt4_tarifa == null) {
			jTt4_tarifa = new JTextField();
			jTt4_tarifa.setBounds(new java.awt.Rectangle(46,117,84,19));
			jTt4_tarifa.setEditable(false);
		}
		return jTt4_tarifa;
	}
	/**
	 * This method initializes jTt1_iva	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTt1_iva() {
		if (jTt1_iva == null) {
			jTt1_iva = new JTextField();
			jTt1_iva.setBounds(new java.awt.Rectangle(143,44,84,19));
			jTt1_iva.setEditable(false);
		}
		return jTt1_iva;
	}
	/**
	 * This method initializes jTt2_iva	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTt2_iva() {
		if (jTt2_iva == null) {
			jTt2_iva = new JTextField();
			jTt2_iva.setBounds(new java.awt.Rectangle(143,69,84,19));
			jTt2_iva.setEditable(false);
		}
		return jTt2_iva;
	}
	/**
	 * This method initializes jTt3_iva	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTt3_iva() {
		if (jTt3_iva == null) {
			jTt3_iva = new JTextField();
			jTt3_iva.setBounds(new java.awt.Rectangle(143,93,84,19));
			jTt3_iva.setEditable(false);
		}
		return jTt3_iva;
	}
	/**
	 * This method initializes jTt4_iva	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTt4_iva() {
		if (jTt4_iva == null) {
			jTt4_iva = new JTextField();
			jTt4_iva.setBounds(new java.awt.Rectangle(143,117,84,19));
			jTt4_iva.setEditable(false);
		}
		return jTt4_iva;
	}
	/**
	 * This method initializes jTt1_dto	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTt1_dto() {
		if (jTt1_dto == null) {
			jTt1_dto = new JTextField();
			jTt1_dto.setLocation(new java.awt.Point(241,44));
			jTt1_dto.setSize(new java.awt.Dimension(84,19));
			jTt1_dto.setEditable(false);
		}
		return jTt1_dto;
	}
	/**
	 * This method initializes jTt2_dto	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTt2_dto() {
		if (jTt2_dto == null) {
			jTt2_dto = new JTextField();
			jTt2_dto.setBounds(new java.awt.Rectangle(241,69,84,19));
			jTt2_dto.setEditable(false);
		}
		return jTt2_dto;
	}
	/**
	 * This method initializes jTt3_dto	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTt3_dto() {
		if (jTt3_dto == null) {
			jTt3_dto = new JTextField();
			jTt3_dto.setBounds(new java.awt.Rectangle(241,93,84,19));
			jTt3_dto.setEditable(false);
		}
		return jTt3_dto;
	}
	/**
	 * This method initializes jTt4_dto	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTt4_dto() {
		if (jTt4_dto == null) {
			jTt4_dto = new JTextField();
			jTt4_dto.setBounds(new java.awt.Rectangle(241,117,84,19));
			jTt4_dto.setEditable(false);
		}
		return jTt4_dto;
	}
	/**
	 * This method initializes jTt5_tarifa	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTt5_tarifa() {
		if (jTt5_tarifa == null) {
			jTt5_tarifa = new JTextField();
			jTt5_tarifa.setBounds(new java.awt.Rectangle(46,141,84,19));
			jTt5_tarifa.setEditable(false);
		}
		return jTt5_tarifa;
	}
	/**
	 * This method initializes jTt5_iva	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTt5_iva() {
		if (jTt5_iva == null) {
			jTt5_iva = new JTextField();
			jTt5_iva.setBounds(new java.awt.Rectangle(143,141,84,19));
			jTt5_iva.setEditable(false);
		}
		return jTt5_iva;
	}
	/**
	 * This method initializes jTt5_dto	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTt5_dto() {
		if (jTt5_dto == null) {
			jTt5_dto = new JTextField();
			jTt5_dto.setBounds(new java.awt.Rectangle(241,141,84,19));
			jTt5_dto.setEditable(false);
		}
		return jTt5_dto;
	}
	/**
	 * This method initializes jtab2_panel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJtab2_panel2() {
		if (jtab2_panel2 == null) {
			jLtarifa5 = new JLabel();
			jLtarifa5.setText("Tarifa5");
			jLtarifa5.setBounds(new java.awt.Rectangle(15,120,45,19));
			jLtarifa4 = new JLabel();
			jLtarifa4.setText("Tarifa4");
			jLtarifa4.setBounds(new java.awt.Rectangle(15,96,45,19));
			jLtarifa3 = new JLabel();
			jLtarifa3.setText("Tarifa3");
			jLtarifa3.setBounds(new java.awt.Rectangle(15,72,45,19));
			jLtarifa2 = new JLabel();
			jLtarifa2.setText("Tarifa2");
			jLtarifa2.setBounds(new java.awt.Rectangle(15,48,45,19));
			jLtarifa1 = new JLabel();
			jLtarifa1.setText("Tarifa1");
			jLtarifa1.setSize(new java.awt.Dimension(45,19));
			jLtarifa1.setLocation(new java.awt.Point(15,24));
			jtab2_panel2 = new JPanel();
			jtab2_panel2.setLayout(null);
			jtab2_panel2.setBounds(new java.awt.Rectangle(10,176,433,150));
			jtab2_panel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Escalado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, null));
			jtab2_panel2.add(jLtarifa1, null);
			jtab2_panel2.add(jLtarifa2, null);
			jtab2_panel2.add(jLtarifa3, null);
			jtab2_panel2.add(jLtarifa4, null);
			jtab2_panel2.add(jLtarifa5, null);
			jtab2_panel2.add(getJTtarifa1_desde(), null);
			jtab2_panel2.add(getJTtarifa1_hasta(), null);
			jtab2_panel2.add(getJTtarifa2_desde(), null);
			jtab2_panel2.add(getJTtarifa2_hasta(), null);
			jtab2_panel2.add(getJTtarifa3_desde(), null);
			jtab2_panel2.add(getJTtarifa3_hasta(), null);
			jtab2_panel2.add(getJTtarifa4_desde(), null);
			jtab2_panel2.add(getJTtarifa4_hasta(), null);
			jtab2_panel2.add(getJTtarifa5_desde(), null);
			jtab2_panel2.add(getJTtarifa5_hasta(), null);
		}
		return jtab2_panel2;
	}
	/**
	 * This method initializes jTtarifa1_desde	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTtarifa1_desde() {
		if (jTtarifa1_desde == null) {
			jTtarifa1_desde = new JTextField();
			jTtarifa1_desde.setPreferredSize(new java.awt.Dimension(4,19));
			jTtarifa1_desde.setBounds(new java.awt.Rectangle(83,24,106,19));
			jTtarifa1_desde.setEditable(false);
		}
		return jTtarifa1_desde;
	}
	/**
	 * This method initializes jTtarifa1_hasta	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTtarifa1_hasta() {
		if (jTtarifa1_hasta == null) {
			jTtarifa1_hasta = new JTextField();
			jTtarifa1_hasta.setLocation(new java.awt.Point(212,24));
			jTtarifa1_hasta.setSize(new java.awt.Dimension(106,19));
			jTtarifa1_hasta.setEditable(false);
		}
		return jTtarifa1_hasta;
	}
	/**
	 * This method initializes jTtarifa2_desde	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTtarifa2_desde() {
		if (jTtarifa2_desde == null) {
			jTtarifa2_desde = new JTextField();
			jTtarifa2_desde.setBounds(new java.awt.Rectangle(83,48,106,19));
			jTtarifa2_desde.setEditable(false);
		}
		return jTtarifa2_desde;
	}
	/**
	 * This method initializes jTtarifa2_hasta	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTtarifa2_hasta() {
		if (jTtarifa2_hasta == null) {
			jTtarifa2_hasta = new JTextField();
			jTtarifa2_hasta.setBounds(new java.awt.Rectangle(212,48,106,19));
			jTtarifa2_hasta.setEditable(false);
		}
		return jTtarifa2_hasta;
	}
	/**
	 * This method initializes jTtarifa3_desde	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTtarifa3_desde() {
		if (jTtarifa3_desde == null) {
			jTtarifa3_desde = new JTextField();
			jTtarifa3_desde.setBounds(new java.awt.Rectangle(83,72,106,19));
			jTtarifa3_desde.setEditable(false);
		}
		return jTtarifa3_desde;
	}
	/**
	 * This method initializes jTtarifa3_hasta	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTtarifa3_hasta() {
		if (jTtarifa3_hasta == null) {
			jTtarifa3_hasta = new JTextField();
			jTtarifa3_hasta.setBounds(new java.awt.Rectangle(212,72,106,19));
			jTtarifa3_hasta.setEditable(false);
		}
		return jTtarifa3_hasta;
	}
	/**
	 * This method initializes jTtarifa4_desde	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTtarifa4_desde() {
		if (jTtarifa4_desde == null) {
			jTtarifa4_desde = new JTextField();
			jTtarifa4_desde.setBounds(new java.awt.Rectangle(83,96,106,19));
			jTtarifa4_desde.setEditable(false);
		}
		return jTtarifa4_desde;
	}
	/**
	 * This method initializes jTtarifa4_hasta	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTtarifa4_hasta() {
		if (jTtarifa4_hasta == null) {
			jTtarifa4_hasta = new JTextField();
			jTtarifa4_hasta.setBounds(new java.awt.Rectangle(212,96,106,19));
			jTtarifa4_hasta.setEditable(false);
		}
		return jTtarifa4_hasta;
	}
	/**
	 * This method initializes jTtarifa5_desde	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTtarifa5_desde() {
		if (jTtarifa5_desde == null) {
			jTtarifa5_desde = new JTextField();
			jTtarifa5_desde.setBounds(new java.awt.Rectangle(83,120,106,19));
			jTtarifa5_desde.setEditable(false);
		}
		return jTtarifa5_desde;
	}
	/**
	 * This method initializes jTtarifa5_hasta	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTtarifa5_hasta() {
		if (jTtarifa5_hasta == null) {
			jTtarifa5_hasta = new JTextField();
			jTtarifa5_hasta.setBounds(new java.awt.Rectangle(212,120,106,19));
			jTtarifa5_hasta.setEditable(false);
		}
		return jTtarifa5_hasta;
	}
	/**
	 * This method initializes jtab3_panel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJtab3_panel2() {
		if (jtab3_panel2 == null) {
			jLavisos = new JLabel();
			jLavisos.setText("Avisos");
			jLavisos.setSize(new java.awt.Dimension(42,19));
			jLavisos.setLocation(new java.awt.Point(17,225));
			jLobservaciones = new JLabel();
			jLobservaciones.setText("Observaciones");
			jLobservaciones.setSize(new java.awt.Dimension(92,19));
			jLobservaciones.setLocation(new java.awt.Point(13,82));
			//jLfecha_p_recibir = new JLabel();
			//jLfecha_p_recibir.setText("fecha P.de recibir");
			//jLfecha_p_recibir.setBounds(new java.awt.Rectangle(208,88,103,19));
			//jLrecibir = new JLabel();
			//jLrecibir.setText("P. de recibir");
			//jLrecibir.setBounds(new java.awt.Rectangle(239,58,71,19));
			//jLservir = new JLabel();
			//jLservir.setText("P. de servir");
			//jLservir.setSize(new java.awt.Dimension(70,19));
			//jLservir.setLocation(new java.awt.Point(239,28));
			//jLvirtual = new JLabel();
			//jLvirtual.setText("Virtual");
			//jLvirtual.setBounds(new java.awt.Rectangle(9,88,43,19));
			//jLstock = new JLabel();
			//jLstock.setText("Stock");
			//jLstock.setBounds(new java.awt.Rectangle(9,58,44,19));
			jLbulto = new JLabel();
			jLbulto.setText("Unidades Bulto");
			jLbulto.setSize(new java.awt.Dimension(86,19));
			jLbulto.setLocation(new java.awt.Point(9,28));
			jtab3_panel2 = new JPanel();
			jtab3_panel2.setLayout(null);
			jtab3_panel2.add(jLbulto, null);
			//jtab3_panel2.add(jLstock, null);
			//jtab3_panel2.add(jLvirtual, null);
			//jtab3_panel2.add(jLservir, null);
			//jtab3_panel2.add(jLrecibir, null);
			//jtab3_panel2.add(jLfecha_p_recibir, null);
			jtab3_panel2.add(jLobservaciones, null);
			jtab3_panel2.add(jLavisos, null);
			jtab3_panel2.add(getJTbulto(), null);
			//jtab3_panel2.add(getJTstock(), null);
			//jtab3_panel2.add(getJTvirtual(), null);
			//jtab3_panel2.add(getJTservir(), null);
			//jtab3_panel2.add(getJTrecibir(), null);
			//jtab3_panel2.add(getJTfecha_p_recibir(), null);
			jtab3_panel2.add(getJTobservaciones(), null);
			jtab3_panel2.add(getJTavisos(), null);
		}
		return jtab3_panel2;
	}
	/**
	 * This method initializes jTbulto	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTbulto() {
		if (jTbulto == null) {
			jTbulto = new JTextField();
			jTbulto.setLocation(new java.awt.Point(105,28));
			jTbulto.setSize(new java.awt.Dimension(79,19));
			jTbulto.setEditable(false);
		}
		return jTbulto;
	}
	/**
	 * This method initializes jTstock	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTstock() {
		if (jTstock == null) {
			jTstock = new JTextField();
			jTstock.setLocation(new java.awt.Point(105,58));
			jTstock.setSize(new java.awt.Dimension(79,19));
			jTstock.setEditable(false);
		}
		return jTstock;
	}
	/**
	 * This method initializes jTvirtual	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTvirtual() {
		if (jTvirtual == null) {
			jTvirtual = new JTextField();
			jTvirtual.setLocation(new java.awt.Point(105,88));
			jTvirtual.setSize(new java.awt.Dimension(79,19));
			jTvirtual.setEditable(false);
		}
		return jTvirtual;
	}
	/**
	 * This method initializes jTservir	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTservir() {
		if (jTservir == null) {
			jTservir = new JTextField();
			jTservir.setLocation(new java.awt.Point(325,28));
			jTservir.setSize(new java.awt.Dimension(128,19));
			jTservir.setEditable(false);
		}
		return jTservir;
	}
	/**
	 * This method initializes jTrecibir	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTrecibir() {
		if (jTrecibir == null) {
			jTrecibir = new JTextField();
			jTrecibir.setBounds(new java.awt.Rectangle(325,58,128,19));
			jTrecibir.setEditable(false);
		}
		return jTrecibir;
	}
	/**
	 * This method initializes jTfecha_p_recibir	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTfecha_p_recibir() {
		if (jTfecha_p_recibir == null) {
			jTfecha_p_recibir = new JTextField();
			jTfecha_p_recibir.setBounds(new java.awt.Rectangle(325,88,128,19));
			jTfecha_p_recibir.setEditable(false);
		}
		return jTfecha_p_recibir;
	}
	/**
	 * This method initializes jTobservaciones	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTobservaciones() {
		if (jTobservaciones == null) {
			jTobservaciones = new JTextArea();
			jTobservaciones.setBounds(new java.awt.Rectangle(13,104,438,72));
			jTobservaciones.setEditable(false);
		}
		return jTobservaciones;
	}
	/**
	 * This method initializes jTavisos	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTavisos() {
		if (jTavisos == null) {
			jTavisos = new JTextArea();
			jTavisos.setBounds(new java.awt.Rectangle(16,247,438,72));
			jTavisos.setEditable(false);
		}
		return jTavisos;
	}
	
	/**
	 * Devuelve un objeto de tipo BufferedImage de un objeto Image dado
	 * @param img
	 * @return
	 */
	public BufferedImage getBufferedImage(Image img){
		BufferedImage bi = null;
		
		// Create a buffered image with a format that's compatible with the screen
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			// Determine the type of transparency of the new buffered image
			int transparency = Transparency.OPAQUE;
			
			// Create the buffered image
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bi = gc.createCompatibleImage(
					img.getWidth(null), img.getHeight(null), transparency);
		} catch (HeadlessException e) {
			// The system does not have a screen
			new LogWriter( e.getStackTrace() );
		}
		
		if (bi == null) {
			// Create a buffered image using the default color model
			int type = BufferedImage.TYPE_INT_RGB;
			bi = new BufferedImage(img.getWidth(null), img.getHeight(null), type);
		}
		
		// Copy image to buffered image
		Graphics g = bi.createGraphics();
		
		// Paint the image onto the buffered image
		g.drawImage(img, 0, 0, null);
		g.dispose();
		
		return bi;
	}
	
}  //  @jve:decl-index=0:visual-constraint="2,21"

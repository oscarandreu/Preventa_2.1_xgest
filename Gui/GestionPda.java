package Gui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import system.Cargador;
import bbdd.InternalFrameTable;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;

import log.LogWriter;


public class GestionPda  extends GuiInternalFrame{
	
	private JPanel panel = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private JScrollPane jScrollPane = null;
	private JSplitPane jSplitPane = null;
	private JPanel jPanel1 = null;
	private JLabel jLagente = null;
	private JLabel jLmarca = null;
	private JLabel jLModelo = null;
	private JLabel jLobservaciones = null;
	private JLabel jLdistribuidor = null;
	private JLabel jLgarantia = null;
	private JLabel jLfecha = null;
	private JTextField jTagente = null;
	private JTextField jTmarca = null;
	private JTextField jTmodelo = null;
	private JTextField jTdistribuidor = null;
	private JTextField jTgarantia = null;
	private JTextField jTfecha = null;
	private JButton eliminar = null;
	private JButton Modificar = null;
	private JButton cerrar = null;
	private JScrollPane jScrollPane1 = null;
	private JTextPane jTobservaciones = null;
	
//	==================================================================
	public GestionPda( Cargador cargador ) 
//	==================================================================
	{
		super( cargador );
		icono = "tab_comparativa.gif";
		toolTip = "Gestion los dispositivos asociados al sistema";
		titulo = "Gestión de las PDA's";
	}
	
//	===========================================================================================
	public void mostrarDatos(String where )
//	===========================================================================================
	{
		this.where = where;
		try {
			Statement sta = conect.createStatement();
			ResultSet resul = sta.executeQuery("SELECT * FROM ppc WHERE "+where);
			if( resul.next() ){
				this.jTagente.setText(resul.getString("codigo_agente"));
				this.jTdistribuidor.setText(resul.getString("distribuidor"));
				this.jTgarantia.setText(resul.getString("garantia"));
				this.jTfecha.setText(resul.getString("fecha_compra"));
				this.jTmodelo.setText(resul.getString("modelo"));
				this.jTmarca.setText(resul.getString("marca"));
				this.jTobservaciones.setText(resul.getString("observaciones"));
			}
			resul.close();
			sta.close();
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}
//	==================================================================
	protected JPanel getPanel() 
//	==================================================================
	{
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(null);
			panel.setSize(new java.awt.Dimension(840,342));
			panel.add(getJSplitPane(), null);
			panel.add(getEliminar(), null);
			panel.add(getModificar(), null);
			panel.add(getCerrar(), null);
		}
		frame.setBounds(new java.awt.Rectangle(50,50,790,370));
		return panel;
	}
//	===========================================================================================
	protected JScrollPane getJScrollPane() 
//	===========================================================================================
	{
		model = new InternalFrameTable( this);
		//Configuracion de la tabla
		model.setPrimaryKeys(new String[]{"id"} );
		model.setColumnas( new String[]{"marca","modelo","observaciones"} );
		model.setColumnasFormateadas(  new String[]{"Marca","Modelo","Observaciones"} );
		model.setAnchoColumnas( new int[]{100,100,200} );
		model.setDatabase("ppc"); 
		
		jScrollPane = new JScrollPane( model.getTabla() );
		jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		jScrollPane.setBounds(new java.awt.Rectangle(0,0,279,390));
		return jScrollPane;
	}
//	===========================================================================================
	private JButton getEliminar() 
//	===========================================================================================
	{
		if (eliminar == null) {
			eliminar = new JButton();
			eliminar.setBounds(new java.awt.Rectangle(154,297,100, 26));
			eliminar.setText("Eliminar");
			eliminar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Statement sta;
					try {
						sta = conect.createStatement();
						System.out.println("DELETE FROM ppc WHERE "+where);
						sta.execute("DELETE FROM ppc WHERE "+where);
						model.setData( false );
					} catch (SQLException e1) {
						e1.printStackTrace();
						new LogWriter( e1.getStackTrace() );
					}
				}
			});
		}
		return eliminar;
	}
//	===========================================================================================
	private JButton getModificar() 
//	===========================================================================================
	{
		if (Modificar == null) {
			Modificar = new JButton();
			Modificar.setBounds(new java.awt.Rectangle(282,297,100,26));
			Modificar.setText("Modificar");
			Modificar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
				}
			});
		}
		return Modificar;
	}
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setBounds(new java.awt.Rectangle(3,2,821,280));
			jSplitPane.setDividerLocation(320);
			jSplitPane.setDividerSize(5);
			jSplitPane.setRightComponent( getJPanel1() );
			jSplitPane.setLeftComponent( getJScrollPane() );
		}
		return jSplitPane;
	}
	
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			TitledBorder titledBorder = BorderFactory.createTitledBorder(null, "Gestion Pda", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null);
			titledBorder.setTitle("");
			jLfecha = new JLabel();
			jLfecha.setText("Fecha compra");
			jLfecha.setBounds(new java.awt.Rectangle(16,123,85,19));
			jLgarantia = new JLabel();
			jLgarantia.setBounds(new java.awt.Rectangle(16,101,54,19));
			jLgarantia.setText("Garantia");
			jLdistribuidor = new JLabel();
			jLdistribuidor.setText("Distribuidor");
			jLdistribuidor.setBounds(new java.awt.Rectangle(16,79,71,19));
			jLobservaciones = new JLabel();
			jLobservaciones.setText("Observaciones");
			jLobservaciones.setBounds(new java.awt.Rectangle(17,148,92,19));
			jLModelo = new JLabel();
			jLModelo.setText("Modelo");
			jLModelo.setBounds(new java.awt.Rectangle(16,57,49,19));
			jLmarca = new JLabel();
			jLmarca.setText("Marca");
			jLmarca.setBounds(new java.awt.Rectangle(16,35,41,19));
			jLagente = new JLabel();
			jLagente.setText("Agente");
			jLagente.setSize(new java.awt.Dimension(51,19));
			jLagente.setLocation(new java.awt.Point(16,13));
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setPreferredSize(new java.awt.Dimension(190,150));
			jPanel1.setBorder(titledBorder);
			jPanel1.add(jLagente, null);
			jPanel1.add(jLmarca, null);
			jPanel1.add(jLModelo, null);
			jPanel1.add(jLobservaciones, null);
			jPanel1.add(jLdistribuidor, null);
			jPanel1.add(jLgarantia, null);
			jPanel1.add(jLfecha, null);
			jPanel1.add(getJTagente(), null);
			jPanel1.add(getJTmarca(), null);
			jPanel1.add(getJTmodelo(), null);
			jPanel1.add(getJTdistribuidor(), null);
			jPanel1.add(getJTgarantia(), null);
			jPanel1.add(getJTfecha(), null);
			jPanel1.add(getJScrollPane1(), null);
		}
		return jPanel1;
	}

	private JTextField getJTagente() {
		if (jTagente == null) {
			jTagente = new JTextField();
			jTagente.setPreferredSize(new java.awt.Dimension(4,19));
			jTagente.setBounds(new java.awt.Rectangle(93,13,332,19));
		}
		return jTagente;
	}

	private JTextField getJTmarca() {
		if (jTmarca == null) {
			jTmarca = new JTextField();
			jTmarca.setBounds(new java.awt.Rectangle(93,35,332,19));
		}
		return jTmarca;
	}

	private JTextField getJTmodelo() {
		if (jTmodelo == null) {
			jTmodelo = new JTextField();
			jTmodelo.setBounds(new java.awt.Rectangle(93,57,332,19));
		}
		return jTmodelo;
	}

	private JTextField getJTdistribuidor() {
		if (jTdistribuidor == null) {
			jTdistribuidor = new JTextField();
			jTdistribuidor.setBounds(new java.awt.Rectangle(93,79,332,19));
		}
		return jTdistribuidor;
	}

	private JTextField getJTgarantia() {
		if (jTgarantia == null) {
			jTgarantia = new JTextField();
			jTgarantia.setBounds(new java.awt.Rectangle(93,101,332,19));
		}
		return jTgarantia;
	}

	private JTextField getJTfecha() {
		if (jTfecha == null) {
			jTfecha = new JTextField();
			jTfecha.setBounds(new java.awt.Rectangle(105,123,86,19));
		}
		return jTfecha;
	}

	private JButton getCerrar() {
		if (cerrar == null) {
			cerrar = new JButton();
			cerrar.setBounds(new java.awt.Rectangle(411,297,100,26));
			cerrar.setText("Cerrar");
			cerrar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cerrar();
				}
			});
		}
		return cerrar;
	}

	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBounds(new java.awt.Rectangle(16,173,409,92));
			jScrollPane1.setViewportView(getJTobservaciones());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes jTextPane	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getJTobservaciones() {
		if (jTobservaciones == null) {
			jTobservaciones = new JTextPane();
		}
		return jTobservaciones;
	}	
	
}  //  @jve:decl-index=0:visual-constraint="2,21"

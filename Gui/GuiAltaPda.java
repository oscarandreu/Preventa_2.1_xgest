package Gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import system.Cargador;
import system.SysData;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import log.LogWriter;

import Gui.system.InfoPane;
import Gui.system.SelectFecha;


public class GuiAltaPda  extends JInternalFrame{
	
	private JPanel jPanel1 = null;
	private JLabel jLmarca = null;
	private JLabel jLModelo = null;
	private JLabel jLobservaciones = null;
	private JLabel jLdistribuidor = null;
	private JLabel jLgarantia = null;
	private JLabel jLfecha = null;
	private JTextField jTmarca = null;
	private JTextField jTmodelo = null;
	private JTextField jTdistribuidor = null;
	private JTextField jTgarantia = null;
	private JTextField jTfecha = null;
	private JButton validar = null;
	
	protected String codigo;
	protected SysData sys;
	protected Cargador cargador;
	private JButton jButton = null;
	private JScrollPane jScrollPane = null;
	private JTextPane jTobservaciones = null;
	
	/**Clase desde la cual se gestionaran las peticiones de alta paras las pdas*/
//	==================================================================
	public GuiAltaPda( Cargador cargador ,String codigo ) 
//	==================================================================
	{
		super();
		this.cargador = cargador;
		this.sys = cargador.sys;
		this.codigo = codigo;
		initialize();
		setClosable(true);
		setTitle("Datos de alta para PDA");
		setResizable(false);
		setIconifiable(false);
	}
	
	private void initialize() {
		this.setSize(new java.awt.Dimension(442,339));
		this.setTitle("Alta de PDA en su sistema");
		this.setContentPane(getJPanel());
	}
	
//	===========================================================================================
	private JButton getValidar() 
//	===========================================================================================
	{
		if (validar == null) {
			validar = new JButton();
			validar.setText("Validar");
			validar.setBounds(new java.awt.Rectangle(128,264,161,26));
			validar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					String marca = jTmarca.getText();
					String modelo = jTmodelo.getText();
					if( !marca.equals("") && !modelo.equals("")){
						try {
							Connection c = sys.getConexionPropia();
							Statement s = c.createStatement();
							String fecha = jTfecha.getText();
							if( "".equals( fecha ) )
								fecha = "NOW";
							String query = "INSERT INTO PPC (" +
									"id ,marca,modelo,distribuidor,garantia,fecha_compra,observaciones"+
									") VALUES (" +
									"'"+codigo+"',"+
									"'"+marca+"',"+
									"'"+modelo+"',"+
									"'"+jTdistribuidor.getText()+"',"+
									"'"+jTgarantia.getText()+"',"+
									"'"+fecha+"',"+
									"'"+jTobservaciones.getText()+"'"+
									")";
							if( s.executeUpdate(query) == 1 ){
								new  InfoPane("Importante","Alta realizada correctamente.") ;
								dispose();
							}
							cargador.actualizarVentanas();
						} catch (SQLException e1) {
							e1.printStackTrace();
							new LogWriter( e1.getStackTrace() );
						}
					}else{
						new  InfoPane("Importante","Ha de rellenar los datos.") ;
					}
				}
			});
		}
		return validar;
	}
	
	private JPanel getJPanel() {
		if (jPanel1 == null) {
			TitledBorder titledBorder = BorderFactory.createTitledBorder(null, "Gestion Pda", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null);
			titledBorder.setTitle("");
			jLfecha = new JLabel();
			jLfecha.setText("Fecha compra");
			jLfecha.setBounds(new java.awt.Rectangle(13,102,85,19));
			jLgarantia = new JLabel();
			jLgarantia.setBounds(new java.awt.Rectangle(13,80,54,19));
			jLgarantia.setText("Garantia");
			jLdistribuidor = new JLabel();
			jLdistribuidor.setText("Distribuidor");
			jLdistribuidor.setBounds(new java.awt.Rectangle(13,58,71,19));
			jLobservaciones = new JLabel();
			jLobservaciones.setText("Observaciones");
			jLobservaciones.setBounds(new java.awt.Rectangle(12,128,92,19));
			jLModelo = new JLabel();
			jLModelo.setText("Modelo");
			jLModelo.setBounds(new java.awt.Rectangle(13,36,49,19));
			jLmarca = new JLabel();
			jLmarca.setText("Marca");
			jLmarca.setBounds(new java.awt.Rectangle(13,14,41,19));
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setPreferredSize(new java.awt.Dimension(90,150));
			jPanel1.setBorder(titledBorder);
			jPanel1.add(jLmarca, null);
			jPanel1.add(jLModelo, null);
			jPanel1.add(jLobservaciones, null);
			jPanel1.add(jLdistribuidor, null);
			jPanel1.add(jLgarantia, null);
			jPanel1.add(jLfecha, null);
			jPanel1.add(getJTmarca(), null);
			jPanel1.add(getJTmodelo(), null);
			jPanel1.add(getJTdistribuidor(), null);
			jPanel1.add(getJTgarantia(), null);
			jPanel1.add(getJTfecha(), null);
			jPanel1.add(getValidar(), null);
			jPanel1.add(getJButton(), null);
			jPanel1.add(getJScrollPane(), null);
		}
		return jPanel1;
	}
//	==================================================================
	private JTextField getJTmarca() 
//	==================================================================
	{
		if (jTmarca == null) {
			jTmarca = new JTextField();
			jTmarca.setBounds(new java.awt.Rectangle(90,14,332,19));
		}
		return jTmarca;
	}
//	==================================================================
	private JTextField getJTmodelo() 
//	==================================================================
	{
		if (jTmodelo == null) {
			jTmodelo = new JTextField();
			jTmodelo.setBounds(new java.awt.Rectangle(90,36,332,19));
		}
		return jTmodelo;
	}
//	==================================================================
	private JTextField getJTdistribuidor() 
//	==================================================================
	{
		if (jTdistribuidor == null) {
			jTdistribuidor = new JTextField();
			jTdistribuidor.setBounds(new java.awt.Rectangle(90,58,332,19));
		}
		return jTdistribuidor;
	}
//	==================================================================
	private JTextField getJTgarantia()
//	==================================================================
	{
		if (jTgarantia == null) {
			jTgarantia = new JTextField();
			jTgarantia.setBounds(new java.awt.Rectangle(90,80,332,19));
		}
		return jTgarantia;
	}
//	==================================================================
	private JTextField getJTfecha()
//	==================================================================
	{
		if (jTfecha == null) {
			jTfecha = new JTextField();
			jTfecha.setBounds(new java.awt.Rectangle(102,102,86,19));
			jTfecha.setEditable(false);
			jTfecha.setBackground(java.awt.SystemColor.control);
			jTfecha.setEnabled(true);
		}
		return jTfecha;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new java.awt.Rectangle(189,102,23,19));
			jButton.setIcon(new ImageIcon(getClass().getResource("/img/iconVisitas.gif")));
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					new SelectFecha( jTfecha, jButton );
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new java.awt.Rectangle(13,154,407,95));
			jScrollPane.setViewportView(getJTobservaciones());
		}
		return jScrollPane;
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
	
	
}  //  @jve:decl-index=0:visual-constraint="17,13"

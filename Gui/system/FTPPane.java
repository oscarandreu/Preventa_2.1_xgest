package Gui.system;


import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.ExportarWeb;

import org.jibble.simpleftp.SimpleFTP;

import system.SysData;

public class FTPPane extends JFrame {

	private JPanel jPanel = null;
	private JButton aceptar = null;
	private JButton cancelar = null;
	private JTextField jTservidor = null;
	private JTextField jTusuario = null;
	private JTextField jTcontraseña = null;
	private JLabel jLservidor = null;
	private JLabel jLusuario = null;
	private JLabel jLcontraseña = null;
	
	public String servidor = null;
	public String usuario = null;
	public String contraseña = null;
	
	private SysData sys = null;
	private JFrame padre = null;
		
	/**
	 * This method initializes 
	 * 
	 */
	public FTPPane(SysData s, JFrame p) {
		super();
		sys = s;
		padre = p;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new java.awt.Dimension(375,200));
		this.setTitle("Servicio FTP");
		this.setLayout(null);
		this.setBounds(200, 200, 400, 200);
		this.setContentPane(getJPanel());
		this.setVisible(true);
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLcontraseña = new JLabel();
			jLcontraseña.setBounds(new java.awt.Rectangle(40,70,80,25));
			jLcontraseña.setText("Contraseña");
			jLusuario = new JLabel();
			jLusuario.setBounds(new java.awt.Rectangle(40,40,80,25));
			jLusuario.setText("Usuario");
			jLservidor = new JLabel();
			jLservidor.setBounds(new java.awt.Rectangle(40,10,80,25));
			jLservidor.setText("Servidor FTP");
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getAceptar(), null);
			jPanel.add(getCancelar(), null);
			jPanel.add(getJTservidor(), null);
			jPanel.add(getJTusuario(), null);
			jPanel.add(getJTcontraseña(), null);
			jPanel.add(jLservidor, null);
			jPanel.add(jLusuario, null);
			jPanel.add(jLcontraseña, null);
		}
		return jPanel;
	}

	/**
	 * This method initializes aceptar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getAceptar() {
		if (aceptar == null) {
			aceptar = new JButton();
			aceptar.setBounds(new java.awt.Rectangle(50,125,100,25));
			aceptar.setText("ACEPTAR");
			aceptar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (getServidor().equals("")){
						new InfoPane("ATENCION","Debe introducir un Servidor FTP");
						
					}else if (getUsuario().equals("")){
						new InfoPane("ATENCION","Debe introducir un Usuario");
					}else if (getPassword().equals("")){
						new InfoPane("ATENCION","Debe introducir una Contraseña");
					}else{
						cerrar();
						ExportarWeb exportador = new ExportarWeb(sys,padre,getServidor(),getUsuario(),getPassword());
						exportador.start();
					}
					
				}
			});
		}
		return aceptar;
	}

	/**
	 * This method initializes cancelar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getCancelar() {
		if (cancelar == null) {
			cancelar = new JButton();
			cancelar.setBounds(new java.awt.Rectangle(225,125,100,25));
			cancelar.setText("CANCELAR");
			cancelar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					cerrar();
				}
			});
		}
		return cancelar;
	}

	/**
	 * This method initializes jTservidor	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTservidor() {
		if (jTservidor == null) {
			jTservidor = new JTextField();
			jTservidor.setBounds(new java.awt.Rectangle(130,10,200,25));
		}
		return jTservidor;
	}

	/**
	 * This method initializes jTusuario	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTusuario() {
		if (jTusuario == null) {
			jTusuario = new JTextField();
			jTusuario.setBounds(new java.awt.Rectangle(130,40,200,25));
		}
		return jTusuario;
	}

	/**
	 * This method initializes jPcontraseña	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	private JTextField getJTcontraseña() {
		if (jTcontraseña == null) {
			jTcontraseña = new JPasswordField();
			jTcontraseña.setBounds(new java.awt.Rectangle(130,70,200,25));
		}
		return jTcontraseña;
	}
	
	public void cerrar(){
		this.dispose();
	}
	
	public String getServidor(){
		return jTservidor.getText();
	}
	
	public String getUsuario(){
		return jTusuario.getText();
	}
	
	public String getPassword(){
		return jTcontraseña.getText();
	}
		
/*	public void exportacion(String servidor, String usuario, String pass){
		try{
		// Creamos la conexión FTP
			//BarraProgreso barra = new BarraProgreso("FTP",lista.size());
			this.setVisible(false);
			
			SimpleFTP ftpclient = new SimpleFTP();
			
			System.out.println( "FTP:" + servidor + " User:" + usuario + " Pass:" + pass );
			ftpclient.connect( servidor, 21, usuario, pass );
		//	ftpclient.connect("ftp.mobisys.es",21,"mobisys","xlbstnih");
			ftpclient.bin();
			
			// ********** Subimos la BDD **********
			String out = new String();
			File descriptor = new File(".");
			String rutaTmp = new String(descriptor.getAbsolutePath());
			out = rutaTmp.substring(0, rutaTmp.length() - 1);
			
			ftpclient.cwd("db");
			//barra.setJProgress(1); 
			ftpclient.stor(new File( out + "\\web\\db\\productos.mdb" ));
			ftpclient.cwd("..");
			
			// ********** Subimos las fotos *********
			ftpclient.cwd("www");
			ftpclient.cwd("images");
			
			
			// Subimos cada foto de la lista creada antes
			for (int i=0;i<lista.size();i++){
				//barra.setJProgress(i+2); 
				System.out.println( lista.get(i).toString() );
				
				ftpclient.stor(new File(unidad + "\\" + lista.get(i).toString()));
				
				System.out.println(unidad + "\\" + lista.get(i).toString());
			}
			//barra.dispose();
						
			// Cerramos la sesión FTP
			ftpclient.disconnect();
		
		}catch(IOException ex){
			ex.printStackTrace();
			new InfoPane("ERROR","Error en la conexión FTP");
		}
		
	}*/
}  //  @jve:decl-index=0:visual-constraint="259,67"

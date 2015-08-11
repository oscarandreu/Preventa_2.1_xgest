package Gui;

import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import system.Configuracion;
import system.SysData;
import ERP.ErpConnector;


/**
 * es la gui de gestion del servidor sonde se le meteria la direccion del servidor
 * @author Elena
 *
 */
public class GuiConfiguracion  extends Thread{

	private JPanel panel = null;
	private JPanel panelcentral = null;
	JTextField tfDirecionServidor = null;
	private JPanel panelsur = null;
	private JButton aceptar = null;
	private JButton cancelar = null;
	private JLabel jLabelIPServidor = null;
	private JLabel jLabelUsuario = null;
	JTextField tfUsuario = null;
	private JLabel jLabelPassword = null;
	JPasswordField tfPassword = null;
	
	protected Configuracion conf;
	protected SysData sys ;
	
	protected JFrame pantalla;  //  @jve:decl-index=0:visual-constraint="51,38"
	protected boolean isFinish = false;
	protected ErpConnector erpConector;
	
//	==================================================================
	public GuiConfiguracion( SysData sys, ErpConnector erpConector ) 
//	==================================================================
	{
		this.erpConector = erpConector;
		this.sys = sys;
		this.conf = sys.getConfiguracion();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
//	==================================================================
	private void initialize() 
//	==================================================================
	{
		pantalla = new JFrame();
		pantalla.setTitle("Configuración del servidor");
		pantalla.setBounds(new java.awt.Rectangle(100,100,377,323));
		pantalla.setContentPane(getPanel());
		pantalla.setVisible(true);
	}

//	==================================================================
 public void run()
//	==================================================================
 {
	
		
	 while( !this.isFinish ){
		 try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
 }
//	==================================================================
	protected JPanel getPanel() 
//	==================================================================
	{
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(null);
			panel.add(getPanelcentral(), null);
			panel.add(getPanelsur(), null);
		}
		return panel;
	}


//	==================================================================
	private JPanel getPanelcentral() 
//	==================================================================
		{
		if (panelcentral == null) {
			jLabelPassword = new JLabel();
			jLabelPassword.setBounds(new java.awt.Rectangle(14,148,200,20));
			jLabelPassword.setToolTipText("Contraseña de la base de datos");
			jLabelPassword.setText("Contraseña");
			jLabelPassword.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyReleased(java.awt.event.KeyEvent e) {
					if( e.getKeyCode() == KeyEvent.VK_ENTER ){
						aceptar.requestFocusInWindow();
					} 
				}
			});
			jLabelUsuario = new JLabel();
			jLabelUsuario.setBounds(new java.awt.Rectangle(15,88,200,20));
			jLabelUsuario.setToolTipText("Usuario de la base de datos.");
			jLabelUsuario.setText("Usuario");
			jLabelIPServidor = new JLabel();
			jLabelIPServidor.setBounds(new java.awt.Rectangle(15,27,200,20));
			jLabelIPServidor.setToolTipText("Dirección IP del servidor de bases de datos");
			jLabelIPServidor.setText("Direccion IP del servidor");
			panelcentral = new JPanel();
			panelcentral.setLayout(null);
			panelcentral.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), new java.awt.Color(51,51,51)));
			panelcentral.setFont(new Font("DialogInput", Font.BOLD, 12));
			panelcentral.setBounds(new java.awt.Rectangle(6,16,355,222));
			panelcentral.add(geJtfDirecionServidor(), null);
			panelcentral.add(jLabelIPServidor, null);
			panelcentral.add(jLabelUsuario, null);
			panelcentral.add(getTfUsuario(), null);
			panelcentral.add(jLabelPassword, null);
			panelcentral.add(getTfPassword(), null);
		}
		return panelcentral;
	}

	
//	==================================================================
	private JTextField geJtfDirecionServidor() 
//	==================================================================
		{
		if (tfDirecionServidor == null) {
			tfDirecionServidor = new JTextField();
			tfDirecionServidor.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12));
			tfDirecionServidor.setToolTipText("");
			tfDirecionServidor.setBounds(new java.awt.Rectangle(15,52,300,20));
			tfDirecionServidor.setText( conf.getRuta() );
			tfDirecionServidor.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyReleased(java.awt.event.KeyEvent e) {
					if( e.getKeyCode() == KeyEvent.VK_ENTER ){
						tfUsuario.requestFocusInWindow();
					} 
				}
			});
		}
		return tfDirecionServidor;
	}

	/**
	 * This method initializes panelsur	
	 * 	
	 * @return javax.swing.JPanel	
	 */
//	==================================================================
	private JPanel getPanelsur() 
//	==================================================================
	{
		if (panelsur == null) {
			panelsur = new JPanel();
			panelsur.setLayout(null);
			panelsur.setBounds(new java.awt.Rectangle(6,242,355,50));
			panelsur.add(getAceptar(), null);
			panelsur.add(getJButton(), null);
		}
		return panelsur;
	}
//	==================================================================
	public void aceptar() 
//	==================================================================
	{
		conf.setRuta( tfDirecionServidor.getText() );
		conf.setUser( tfUsuario.getText() );
		conf.setPassword( new String (tfPassword.getPassword() ) );
		
		pantalla.dispose();
		isFinish = true;
	}
	
//	==================================================================
	private JButton getAceptar() 
//	==================================================================
	{
		if (aceptar == null) {
			aceptar = new JButton();
			aceptar.setText("aceptar");
			aceptar.setBounds(new java.awt.Rectangle(69,10,100,26));
			
			aceptar.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyReleased(java.awt.event.KeyEvent e) {
					if( e.getKeyCode() == KeyEvent.VK_ENTER ){
						aceptar();
					} 
				}
			});
			aceptar.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseReleased(java.awt.event.MouseEvent e) {
					aceptar();
				}
			});
		}
		return aceptar;
	}


//	==================================================================
	private JButton getJButton() 
//	==================================================================
	{
		if (cancelar == null) {
			cancelar = new  JButton();
			cancelar.setText("cerrar");
			cancelar.setBounds(new java.awt.Rectangle(179,10,100,26));

			cancelar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					erpConector.cancelado = true;
					pantalla.dispose();
					isFinish = true;
				}
			});
		}
		return cancelar;
	}

	/**
	 * This method initializes tfUsuario	
	 * 	
	 * @return javax.swing.JTextField	
	 */
//	==================================================================
	private JTextField getTfUsuario() 
//	==================================================================
	{
		if (tfUsuario == null) {
			tfUsuario = new JTextField();
			tfUsuario.setBounds(new java.awt.Rectangle(14,114,300,20));
			tfUsuario.setToolTipText("");
			tfUsuario.setText( conf.getUser() );
			tfUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyReleased(java.awt.event.KeyEvent e) {
						if( e.getKeyCode() == KeyEvent.VK_ENTER ){
							tfPassword.requestFocusInWindow();
						} 
					}
			});
		}
		return tfUsuario;
	}

	/**
	 * This method initializes tfPassword	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
//	==================================================================
	private JPasswordField getTfPassword() 
//	==================================================================
	{
		if (tfPassword == null) {
			tfPassword = new JPasswordField();
			tfPassword.setBounds(new java.awt.Rectangle(14,174,300,20));
			tfPassword.setText( conf.getPassword() );
			tfPassword.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyReleased(java.awt.event.KeyEvent e) {
					if( e.getKeyCode() == KeyEvent.VK_ENTER ){
						aceptar.requestFocusInWindow();
					} 
				}
			});
		}
		return tfPassword;
	}

}  //  @jve:decl-index=0:visual-constraint="2,21"

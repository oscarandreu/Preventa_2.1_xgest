package Gui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JPanel;

import log.LogWriter;
import system.Cargador;
import system.SysData;

public class PiePrincipal extends JPanel {

	private JButton jButton = null;
	private JLabel fotoSemaforo = null;
	private JLabel jLabel2 = null;
	protected SysData sys;
	protected Cargador cargadorPie;
	private JLabel numeroEventos = null;
	
	// contador de numero de eventos en cola
	private int contadorEventosCola;
	
	public JDesktopPane jdtp = null;
	private JLabel empresa = null;
	
//	==================================================================	
	public PiePrincipal(Cargador carga) 
//	==================================================================
	{
		super();
		this.sys = carga.sys;
		this.cargadorPie = carga;
		
		initialize();
	}
	
//	==================================================================
	public void cambiarSemaforo( int in )
//	==================================================================
	{
		String semaforo = null;;
		switch( in ){
		case 1:
			semaforo = "semaforoV.gif";
			break;
		case 2:
			semaforo = "semaforoA.gif";
			break;
		case 3:
			semaforo = "semaforoR.gif";
			break;
		}
		
		fotoSemaforo.setIcon( SysData.getIcono(semaforo) );
		fotoSemaforo.repaint();
	}
	
//	==============================================================	
	public void setContadorEventosPie(int cantidad )
//	==============================================================
	{
		
		//Almacenamos en número de eventos pendientes en una variables
		this.setContadorEventosCola( cantidad );
		
		//Actualizamos el número de eventos pendientes a mostrar en el PiePrincipal
		numeroEventos.setText("Eventos pendientes: "+this.getContadorEventosCola());
		
		//Vemos si debemos activar o desactivar el botón de MOSTRAR eventos pendientes
		if (this.getContadorEventosCola()==0){
			this.jButton.setEnabled(false);
		}else{
			this.jButton.setEnabled(true);
		}
	}
	
//	==============================================================	
	public void setJDesktop(JDesktopPane jdt)
//	==============================================================
	{
		jdtp = jdt;
	}	

//	==================================================================	
	private JButton getJButton() 
//	==================================================================
	{
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("Mostrar");
			jButton.setBounds(new java.awt.Rectangle(207,6,93,20));
			if(this.getContadorEventosCola()==0){
				jButton.setEnabled(false);
			}else{
				jButton.setEnabled(true);
			}
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
//					TODO Auto-generated Event stub actionPerformed()
					GuiEventos eventos = new GuiEventos(cargadorPie,jdtp);
					eventos.initialize(jdtp);
					
				}
			});
		}
		return jButton;
	}

//	==================================================================	
	private void initialize() 
//	==================================================================
	{
		empresa = new JLabel();
		empresa.setBounds(new java.awt.Rectangle(575,11,205,16));
		empresa.setText("Empresa: " + sys.getEmpresa());
		numeroEventos = new JLabel();
		numeroEventos.setBounds(new java.awt.Rectangle(16,11,184,16));
		
		// vemos los eventos pendientes en la tabla Cola de Eventos
		try{
			Connection c = sys.getConexionPropia();
			Statement s = c.createStatement();
			String query = "SELECT COUNT(*) FROM COLA_EVENTOS" ;
			System.out.println( query );
			ResultSet rs = s.executeQuery( query );
			
			while (rs.next()){
				System.out.println("Eventos en cola:"+rs.getInt("COUNT"));
				
				
				//Actualizamos la variable que almacena el numero de Eventos pendientes en cola
				this.setContadorEventosCola( rs.getInt("COUNT") );
				
				//Mostramos este valor
				numeroEventos.setText("Eventos pendientes: "+this.getContadorEventosCola());
			}
			
			s.close();
			c.close();
		}catch (SQLException ex){
			System.out.println("Error al contabilizar: "+ex.toString());
			new LogWriter( ex.getStackTrace() );
		}
				
		jLabel2 = new JLabel();
		jLabel2.setBounds(new java.awt.Rectangle(823,6,109,22));
		jLabel2.setText("Conexión externa");
		fotoSemaforo = new JLabel();
		fotoSemaforo.setBounds(new java.awt.Rectangle(939,1,80,33));
		fotoSemaforo.setText("");
		cambiarSemaforo(1);
		this.setLayout(null);
		this.setSize(1024, 36);
		this.setPreferredSize(new java.awt.Dimension(254,30));
		this.add(getJButton(), null);
		this.add(fotoSemaforo, null);
		this.add(jLabel2, null);
		this.add(numeroEventos, null);
		this.add(empresa, null);
	}

//	==================================================================
	//Metodo para conseguir el valor almacenado en la variable de eventos pendientes en cola
	public int getContadorEventosCola() 
//	==================================================================
	{
		return contadorEventosCola;
	}

//	==================================================================
	//Método para establecer un nuevo valor en la variable de eventos pendientes en cola
	public void setContadorEventosCola( int contadorEventosCola ) 
//	==================================================================
	{
		this.contadorEventosCola = contadorEventosCola;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"

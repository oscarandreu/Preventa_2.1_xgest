package Gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import log.LogWriter;
import system.Cargador;
import system.SysData;
import ERP.AgenteErp;
import ERP.ArticuloErp;
import ERP.ClienteErp;
import ERP.DatosGeneralesErp;
import Gui.system.FTPPane;
import Gui.system.InfoPane;
import Gui.system.QueryPane;

public class MainGui {
	
	public JFrame jFrame = null;  //  @jve:decl-index=0:visual-constraint="53,26"
	public JDesktopPane jDesktopPane = null;
	public SysData sys = null;
	protected Cargador cargador;
	
	protected JLabel imagen=null;
	protected JMenuBar jJMenuBar = null;
	protected JMenu menuDatosImportados = null;
	protected JMenu menuAdministracion = null;
	protected JMenu menuGestionPDA = null;
	protected JMenu menuAyuda = null;
	protected JMenuItem menuConfServidor = null;
	private JMenuItem GestionPdas = null;
	private JMenuItem rutas = null;
	private JMenuItem AgentesView = null;
	private JMenuItem Clientes = null;
	private JMenuItem Articulos = null;
	private JMenuItem LimpiarDatos = null;
	private JMenuItem ClientesNuevosPDA = null;
	private JMenuItem PedidosPDA = null;
	private JMenuItem VisitasPDA = null;
	private JMenuItem EstadisticasPDA = null;
	private JMenuItem IncidenciasPDA = null;
	private JLabel imagenFondo = null;
	private JMenuItem exportarFotos = null;
	private JMenuItem exportarWEB = null;
	private JMenuItem instalarSWPDA = null;
	private JMenuItem acercaDe = null;
	private JMenuItem tutorial = null;
	private JMenuItem limpiarPedidos = null;
	private JMenuItem limpiarIncidencias = null;
	private JMenu jMenuLimpiar = null;
	private JMenuItem PedidosValidados = null;
	private JMenuItem informacionBDD = null;
	private JMenuItem Cobros = null;
	
//	==================================================================	
	public MainGui( Cargador cargador ) 
//	==================================================================	
	{
		this.sys = cargador.sys;
		this.cargador = cargador;
		getJFrame();
		if( cargador.loader != null){
			cargador.loader.dispose();
		}
		
	}
	
//	==================================================================
	/**
	 * @wbp.parser.entryPoint
	 */
	private JMenuItem getPedidosPDA() 
//	==================================================================
	{
		if (PedidosPDA == null) {
			PedidosPDA = new JMenuItem();
			PedidosPDA.setText("Pedidos PDA");
			PedidosPDA.setIcon( SysData.getIcono("iconpedidospda.gif") );
			PedidosPDA.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					GuiPedidos pedidos= new GuiPedidos ( cargador );
					pedidos.initialize( jDesktopPane );
				}
			});
		}
		
		return PedidosPDA;
	}
//	==================================================================
	/**
	 * @wbp.parser.entryPoint
	 */
	private void getJFrame() 
//	==================================================================
	{
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setExtendedState(Frame.MAXIMIZED_BOTH);
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			flowLayout.setHgap(0);
			flowLayout.setVgap(-5);			
			jFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
			jFrame.setBounds(new java.awt.Rectangle(0,0,1024,768));
			jFrame.setJMenuBar(getJJMenuBar());
			jFrame.setTitle("PREVENTA");
			jFrame.setResizable(true);
			jFrame.setName("mainFrame");
			jFrame.setIconImage( SysData.getIcono("logo mobisys.jpg").getImage() );
			JPanel panel=new JPanel();
			panel.setLayout(new BorderLayout());
			JPanel centro=new JPanel();
			centro.setLayout(new GridLayout(1,1));
			centro.add(getJDesktopPane());
			/*
			JPanel sur=new JPanel();
			sur.setLayout(new GridLayout(1,2));
			sur.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			sur.setBackground(java.awt.SystemColor.control);
			sur.setPreferredSize(new Dimension(254, 30));
			JPanel semaforo=new JPanel();
			semaforo.setLayout(flowLayout);
			semaforo.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			JLabel label=new JLabel("pedidos en cola");
			imagen= new JLabel();
			cambiarSemaforo( 1 );
			semaforo.add(imagen, null);
			sur.add(label, label.getName());
			sur.add(semaforo, null);*/
			panel.add(cargador.pie, BorderLayout.SOUTH);
			panel.add(centro, BorderLayout.CENTER);
			jFrame.setContentPane(panel);
			jFrame.setVisible(true);	
		}
	}
//	==================================================================
	private JDesktopPane getJDesktopPane() 
//	==================================================================
	{
		if (jDesktopPane == null) {
			
			jDesktopPane = new JDesktopPane();
			jDesktopPane.setBounds(new Rectangle(1, 0, 417, 183));			
			jDesktopPane.setBackground(java.awt.SystemColor.control);
			imagenFondo = new JLabel();
			
			imagenFondo.setBounds(new java.awt.Rectangle(0,0,1024,600));			
			imagenFondo.setIcon( SysData.getIcono("fondo aplicacion.jpg") );
			imagenFondo.repaint();	
			jDesktopPane.add(imagenFondo, null);
			
		}
		return jDesktopPane;
	}
	
//	==================================================================
	private JMenuBar getJJMenuBar() 
//	==================================================================
	{
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getMenuDatosImportados());
			jJMenuBar.add(getMenuAdministracion());
			jJMenuBar.add(getMenuGestionPDA());
			jJMenuBar.add(getMenuAyuda());
		}
		return jJMenuBar;
	}
	
//	==================================================================
	private JMenu getMenuAdministracion() 
//	==================================================================
	{
		if (menuAdministracion == null) {
			menuAdministracion = new JMenu();
			menuAdministracion.setText("Administración");
			menuAdministracion.setIcon(new ImageIcon(getClass().getResource("/img/iconadministra.gif")));
			//menuAdministracion.add(getMenuConfServidor());
			menuAdministracion.add(getGestionPdas());
			menuAdministracion.add(getJMenuLimpiar());
			menuAdministracion.add(getExportarFotos());
			menuAdministracion.add(getExportarWEB());
			menuAdministracion.add(getInstalarSWPDA());
			
		}
		return menuAdministracion;
	}
	
//	==================================================================
	private JMenu getMenuDatosImportados() 
//	==================================================================
	{
		if (menuDatosImportados == null) {
			menuDatosImportados = new JMenu();
			menuDatosImportados.setText("Datos Importados");
			menuDatosImportados.setIcon(new ImageIcon(getClass().getResource("/img/Dimportados1.gif")));
			//menuDatosImportados.add(getRutas());
			menuDatosImportados.add(getAgentesView());
			//menuDatosImportados.add(getClientes());
			menuDatosImportados.add(getInformacionBDD());
			//menuDatosImportados.add(getArticulos());
		}
		return menuDatosImportados;
	}
//	==================================================================
	private JMenu getMenuGestionPDA() 
//	==================================================================
	{
		if (menuGestionPDA == null) {
			menuGestionPDA = new JMenu();
			menuGestionPDA.setText("Gestión Datos PDA");
			menuGestionPDA.setIcon(new ImageIcon(getClass().getResource("/img/iconpda.gif")));
			menuGestionPDA.add(getClientesNuevosPDA());
			menuGestionPDA.add(getPedidosPDA());
			menuGestionPDA.add(getPedidosValidados());
			menuGestionPDA.add(getVisitasPDA());
			menuGestionPDA.add(getEstadisticasPDA());
			menuGestionPDA.add(getIncidenciasPDA());
			menuGestionPDA.add(getCobros());
			
		}
		return menuGestionPDA;
	}	
//	==================================================================
	private JMenu getMenuAyuda() 
//	==================================================================
	{
		if (menuAyuda == null) {
			menuAyuda = new JMenu();
			menuAyuda.setText("Ayuda");
			//menuAyuda.add(getMenuConfServidor());
			menuAyuda.setIcon(new ImageIcon(getClass().getResource("/img/help.gif")));
			menuAyuda.add(getAcercaDe());
			menuAyuda.add(getTutorial());
			
		}
		return menuAyuda;
	}	
/*	
//	==================================================================
	private JMenuItem getMenuConfServidor() 
//	==================================================================
	{
		if (menuConfServidor == null) {
			menuConfServidor = new JMenuItem();
			menuConfServidor.setText("Configurar servidor");
			menuConfServidor.setIcon( SysData.getIcono("iconcofserver.gif") );
			menuConfServidor.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					GuiConfiguracion confWeb = new GuiConfiguracion( cargador, sys );
					confWeb.initialize( jDesktopPane );
				}
			});
		}
		return menuConfServidor;
	}
	*/
	/**
	 * This method initializes GestionPdas	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
//	==================================================================
	private JMenuItem getGestionPdas()
//	==================================================================
	{
		if (GestionPdas == null) {
			GestionPdas = new JMenuItem();
			GestionPdas.setText("Gestion Pda");
			GestionPdas.setIcon( SysData.getIcono("iconGestionpda.gif") );
			GestionPdas.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					GestionPda gestPda = new GestionPda ( cargador );
					gestPda.initialize( jDesktopPane );
				}
			});
		}
		return GestionPdas;
	}
	
	/**
	 * This method initializes rutas	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
//	==================================================================
	private JMenuItem getRutas()
//	==================================================================
	{
		if (rutas == null) {
			rutas = new JMenuItem();
			rutas.setText("Rutas");
			rutas.setIcon( SysData.getIcono("iconcRutas.gif") );
			rutas.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					GuiRutas rutas = new GuiRutas ( cargador );
					rutas.initialize( jDesktopPane );
				}
			});
		}
		return rutas;
	}
	
	/**
	 * This method initializes AgentesView	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
//	==================================================================
	private JMenuItem getAgentesView()
//	==================================================================
	{
		if (AgentesView == null) {
			AgentesView = new JMenuItem();
			AgentesView.setText("Agentes");
			AgentesView.setIcon( SysData.getIcono("iconAgentes.gif") );
			AgentesView.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					GuiAgentes clientes = new GuiAgentes ( cargador );
					clientes.initialize( jDesktopPane );
				}
			});
		}
		return AgentesView;
	}
	
	/**
	 * This method initializes Clientes	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
//	==================================================================
	private JMenuItem getClientes()
//	==================================================================
	{
		if (Clientes == null) {
			Clientes = new JMenuItem();
			Clientes.setText("Clientes");
			Clientes.setIcon( SysData.getIcono("iconClientes.gif") );
			Clientes.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					GuiClientes  clientes= new GuiClientes ( cargador ,false);
					clientes.initialize( jDesktopPane );
				}
			});
		}
		return Clientes;
	}
	
	/**
	 * This method initializes Articulos	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
//	==================================================================
	private JMenuItem getArticulos()
//	==================================================================
	{
		if (Articulos == null) {
			Articulos = new JMenuItem();
			Articulos.setText("Articulos");
			Articulos.setIcon( SysData.getIcono("iconproductos.gif") );
			Articulos.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					GuiArticulos  articulos= new GuiArticulos ( cargador );
					articulos.initialize( jDesktopPane );
				}
			});
		}
		return Articulos;
	}
	
	/**
	 * This method initializes jMenuLimpiar	
	 * 	
	 * @return javax.swing.JMenu	
	 */
//	==================================================================
	private JMenu getJMenuLimpiar()
//	==================================================================
	{
		if (jMenuLimpiar == null) {
			jMenuLimpiar = new JMenu();
			jMenuLimpiar.setText("Limpiar datos");
			jMenuLimpiar.setIcon( SysData.getIcono("iconborrar.gif") );
			jMenuLimpiar.add(getLimpiarDatos());
			jMenuLimpiar.add(getLimpiarPedidos());
			jMenuLimpiar.add(getLimpiarIncidencias());
		}
		return jMenuLimpiar;
	}	
	
	/**
	 * This method initializes limpiarPedidos	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
//	==================================================================
	private JMenuItem getLimpiarPedidos()
//	==================================================================
	{
		if (limpiarPedidos == null) {
			limpiarPedidos = new JMenuItem();
			limpiarPedidos.setText("Limpiar pedidos");
			limpiarPedidos.setIcon( SysData.getIcono("iconBorrapedido.gif") );
			limpiarPedidos.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int n = new QueryPane().consultar( "Desea borrar todos los pedidos (S/N)" );
					if ( n == 0 ){
						sys.limpiarBaseDatos(1);
					}
				}
			});
		}
		return limpiarPedidos;
	}

	/**
	 * This method initializes limpiarIncidencias	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
//	==================================================================
	private JMenuItem getLimpiarIncidencias()
//	==================================================================
	{
		if (limpiarIncidencias == null) {
			limpiarIncidencias = new JMenuItem();
			limpiarIncidencias.setText("Limpiar incidencias");
			limpiarIncidencias.setIcon( SysData.getIcono("iconBorrainci.gif") );
			limpiarIncidencias.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int n = new QueryPane().consultar( "Desea borrar todas las incidencias (S/N)" );
					if ( n == 0 ){
						sys.limpiarBaseDatos(2);
					}
				}
			});
		}
		return limpiarIncidencias;
	}

	/**
	 * This method initializes Limpiar Datos	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
//	==================================================================
	private JMenuItem getLimpiarDatos()
//	==================================================================
	{
		if (LimpiarDatos == null) {
			LimpiarDatos = new JMenuItem();
			LimpiarDatos.setText("Limpiar todo los datos");
			LimpiarDatos.setIcon( SysData.getIcono("iconBorramenu.gif") );
			LimpiarDatos.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					int n = new QueryPane().consultar( "Desea borrar toda la base de datos (S/N)" );
					if ( n == 0 ){
						// Limpiamos todas las tablas de la base de datos
						sys.limpiarBaseDatos(0);
						
						// Eliminamos el fichero de configuracion
						File f = new File("configuracion.xml");
						f.delete();
						
						// Cerramos la aplicación, para reiniciar con una nueva empresa
						new InfoPane("ATENCION","Se debe reiniciar la aplicación.");
						Thread.interrupted();
						System.exit(0);
						
					}
					
				}
			});
			
		}
		
		return LimpiarDatos;
	}
	
	/**
	 * This method initializes exportarSD	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
//	==================================================================
	private JMenuItem getExportarFotos()
//	==================================================================
	{
		if (exportarFotos == null) {
			exportarFotos = new JMenuItem();
			exportarFotos.setText("Exportar fotos");
			exportarFotos.setIcon( SysData.getIcono("iconCamara.gif") );
			exportarFotos.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					String unidad = null;
					
					JFileChooser chooser = new JFileChooser();
					chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int returnVal = chooser.showDialog(jFrame,"Guardar fotos en:");
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						unidad = chooser.getSelectedFile().getAbsolutePath();				    	
						System.out.println("Directorio elegido: " + unidad );
					}
					
					if ( unidad != null ) {
													
							if(!DatosGeneralesErp.copiarFotosSd(sys,unidad)){
								new InfoPane("ATENCION","Se produjo un error al copiar las fotos");
							}
						
					}
					
				}
			});
		}
		return exportarFotos;
	}

	/**
	 * This method initializes exportarWEB	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
//	==================================================================
	private JMenuItem getExportarWEB()
//	==================================================================
	{
		if (exportarWEB == null) {
			exportarWEB = new JMenuItem();
			exportarWEB.setText("Exportar a WEB");
			exportarWEB.setIcon( SysData.getIcono("iconWeb.gif") );
			exportarWEB.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("Exportando a web..."); 
					new FTPPane(sys,jFrame);
				}
			});
			exportarWEB.setEnabled(false);
		}
		return exportarWEB;
	}
	
	/**
	 * This method initializes instalarSWPDA	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
//	==================================================================
	private JMenuItem getInstalarSWPDA()
//	==================================================================
	{
		if (instalarSWPDA == null) {
			instalarSWPDA = new JMenuItem();
			instalarSWPDA.setText("Instalar programa de PDA");
			instalarSWPDA.setIcon( SysData.getIcono("iconSoftpda.gif") );
			instalarSWPDA.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("Instalando SW de la PDA...");
					try{
						String out = new String();
						File descriptor = new File(".");
						String rutaTmp = new String(descriptor.getAbsolutePath());
						out = rutaTmp.substring(0, rutaTmp.length() - 1);
						
					    Runtime runTime = Runtime.getRuntime();
						runTime.exec(out + "SoftwarePDA\\setup_PPC.exe");
					}catch(IOException ex){
						ex.printStackTrace();
						System.out.println("Error al ejecutar SW de PDA");
						new LogWriter( ex.getStackTrace() );
					}
				}
			});
		}
		return instalarSWPDA;
	}
	
	/**
	 * This method initializes Clientes Nuevos PDA	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
//	==================================================================
	private JMenuItem getClientesNuevosPDA()
//	==================================================================
	{
		if (ClientesNuevosPDA == null) {
			ClientesNuevosPDA = new JMenuItem();
			ClientesNuevosPDA.setText("Nuevos clientes PDA");
			ClientesNuevosPDA.setIcon( SysData.getIcono("iconcClientesnew.gif") );
			ClientesNuevosPDA.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					GuiClientes  clientes= new GuiClientes ( cargador ,true);
					clientes.initialize( jDesktopPane );
				}
			});
			
		}
		
		return ClientesNuevosPDA;
	}

//	==================================================================
	private JMenuItem getVisitasPDA()
//	==================================================================
	{
		if (VisitasPDA == null) {
			VisitasPDA = new JMenuItem();
			VisitasPDA.setText("Visitas PDA");
			VisitasPDA.setEnabled(false);
			VisitasPDA.setIcon( SysData.getIcono("iconVisitas.gif") );
			VisitasPDA.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					GuiVisitas  visitas= new GuiVisitas ( cargador );
					visitas.initialize( jDesktopPane );
				}
			});

		}
		
		return VisitasPDA;
	}
	
	/**
	 * This method initializes Estadisticas PDA	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
//	==================================================================
	private JMenuItem getEstadisticasPDA() 
//	==================================================================
	{
		if (EstadisticasPDA == null) {
			EstadisticasPDA = new JMenuItem();
			EstadisticasPDA.setText("Estadisticas PDA");
			EstadisticasPDA.setIcon( SysData.getIcono("iconEstadisticas.gif") );
			EstadisticasPDA.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					GuiEstadisticas  estadisticas= new GuiEstadisticas ( sys );
					estadisticas.initialize( jDesktopPane );
					
				}
			});
			
		}
		
		return EstadisticasPDA;
	}
	
	/**
	 * This method initializes Incidencias PDA	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
//	==================================================================
	private JMenuItem getIncidenciasPDA()
//	==================================================================
	{
		if (IncidenciasPDA == null) {
			IncidenciasPDA = new JMenuItem();
			IncidenciasPDA.setText("Incidencias PDA");
			IncidenciasPDA.setIcon( SysData.getIcono("iconIncidencias.gif") );
			IncidenciasPDA.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					GuiIncidencias  incidencias= new GuiIncidencias ( cargador );
					incidencias.initialize( jDesktopPane );
					 
				}
			});

		}
		
		return IncidenciasPDA;
	}
	
	/**
	 * This method initializes PedidosValidados	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
//	==================================================================
	private JMenuItem getPedidosValidados()
//	==================================================================
	{
		if (PedidosValidados == null) {
			PedidosValidados = new JMenuItem();
			PedidosValidados.setText("Pedidos Validados");
			PedidosValidados.setIcon( SysData.getIcono("iconValidados.gif") );
			PedidosValidados.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					GuiPedidosValidados pedidosValidados = new GuiPedidosValidados( cargador );
					pedidosValidados.initialize( jDesktopPane );
				}
			});
		}
		return PedidosValidados;
	}
	
	/**
	 * This method initializes Facturacion	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
//	==================================================================
	private JMenuItem getCobros()
//	==================================================================
	{
		if (Cobros == null) {
			Cobros = new JMenuItem();
			Cobros.setText("Cobros Realizados");
			Cobros.setIcon( SysData.getIcono("iconCobros.gif"));
			Cobros.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					GuiCobros facturas = new GuiCobros( cargador );
					facturas.initialize( jDesktopPane );
				}
			});
		}
		return Cobros;
	}


	/**
	 * This method initializes informacionBDD	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
//	==================================================================
	private JMenuItem getInformacionBDD()
//	==================================================================
	{
		if (informacionBDD == null) {
			informacionBDD = new JMenuItem();
			informacionBDD.setText("Información de la Base de Datos");
			informacionBDD.setIcon( SysData.getIcono("iconInformaBDD.gif"));
			informacionBDD.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try{
						Statement sErp = sys.getConexionErp().createStatement();
						Statement s = sys.getConexionPropia().createStatement();
						new InfoPane("INFORMACION","Agentes importados: " + new AgenteErp().getNumeroAgentes(s) 
								+ "\nClientes disponibles: " + new ClienteErp().getNumeroClientes(sErp) 
								+ "\nArticulos disponibles: " + new ArticuloErp().getNumeroArticulos(sErp) );
					}catch(SQLException ex){
						ex.printStackTrace();
						new LogWriter(ex.getStackTrace());
						
					}
				}
			});
		}
		return informacionBDD;
	}

	
	/**
	 * This method initializes AcercaDe	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
//	==================================================================
	private JMenuItem getAcercaDe() 
//	==================================================================
	{
		if (acercaDe == null){
			acercaDe = new JMenuItem();
			acercaDe.setText("Acerca de");
			acercaDe.setIcon( SysData.getIcono("iconAcerca.gif") );
			acercaDe.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					new InfoPane("Acerca de","Gestión Preventa \nVersión 2.0 \nwww.mobisys.es \nmobisys@mobisys.es",SysData.getIcono("logo mobisys.jpg"),Color.WHITE);
				}
			});
			
		}
		return acercaDe;
	}
	
	/**
	 * This method initializes ManualOnline	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
//	==================================================================
	private JMenuItem getTutorial()
//	==================================================================
	{
		if (tutorial == null){
			tutorial = new JMenuItem();
			tutorial.setText("Tutorial");
			tutorial.setIcon( SysData.getIcono("iconTutorial.gif") );
			tutorial.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Iniciando tutorial...");
					try{
						String out = new String();
						File descriptor = new File(".");
						String rutaTmp = new String(descriptor.getAbsolutePath());
						out = rutaTmp.substring(0, rutaTmp.length() - 1);
						
						Runtime rt = Runtime.getRuntime();
						rt.exec(new String[]{ "rundll32", "url.dll,FileProtocolHandler", out + "tutorial.pdf" });
					}catch(IOException ex){
						ex.printStackTrace();
						System.out.println("Error al abrir al tutorial");
						new LogWriter( ex.getStackTrace() );
					}
				}
			});
			
		}
		return tutorial;
	}





	// Corchete final
}

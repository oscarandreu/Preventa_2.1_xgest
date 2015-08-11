package Gui.system;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;




public class Loader extends JFrame {

	private static final long serialVersionUID = -5764342677968179131L;
 	protected Dimension resolucion = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
 	protected int x1 = resolucion.width /4; 
 	protected int y1 = resolucion.height / 5;
 	protected int x2 = x1*2;
 	protected int y2 = y1*2;
 	public JProgressBar barra = new JProgressBar();
 		
 	/**Crea un cargador de la aplicación que se autodestruye al finalizar la carga
	 * su funcion es mostrar una barra de progreso mientras se carga el cuerpo de la aplicación
	 */
 	
 	public Loader(String str){
 		super();
		setBounds(x1, y1, x2, y2);
		setUndecorated(true);
		setVisible(true);
		getContentPane().setLayout(new FlowLayout());
		

 		JLabel foto = new JLabel();
 		foto.setOpaque(false);
 		foto.setIcon(getIcono("cargador.jpg"));
 		getContentPane().add(foto);
		foto.setBounds(0,0,520,272);
 		barra.setBounds(47,275, 414, 22);
		barra.setStringPainted(true); 
		barra.setString(str);
		barra.setIndeterminate(true);
		getContentPane().add(barra);
 	}
 	
	public Loader() {
		super();
		setBounds(x1, y1, x2, y2);
		setUndecorated(true);
		setVisible(true);
		getContentPane().setLayout(new FlowLayout());
		

 		JLabel foto = new JLabel();
 		foto.setOpaque(false);
 		foto.setIcon(getIcono("cargador.jpg"));
 		getContentPane().add(foto);
		foto.setBounds(0,0,520,272);
 		barra.setBounds(47,275, 414, 22);
		barra.setStringPainted(true); 
		barra.setString("Cargando...");
		barra.setIndeterminate(true);
		getContentPane().add(barra);
	}
//	==================================================================
	protected ImageIcon getIcono(String nombre) 
//	==================================================================
	{
		String out = new String();
		File descriptor = new File(".");
		String rutaTmp = new String(descriptor.getAbsolutePath());
		out = rutaTmp.substring(0, rutaTmp.length() - 1);
		return new ImageIcon(out+"img"+File.separatorChar+nombre);
	}

}

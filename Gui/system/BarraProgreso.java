package Gui.system;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class BarraProgreso  {
	
	private JPanel jPanel = null;
	private JProgressBar jProgressBar = null;
	public JFrame panel;
	public boolean opFinalizada = true;
	protected String titulo;
	protected int maxBarra = 0;
	
//	==================================================================
	public BarraProgreso( String titulo, int maxBarra ) 
//	==================================================================
	{
		//System.out.println("Creando panel...");
		this.titulo = titulo;
		this.maxBarra = maxBarra;
		
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new FlowLayout());
			JLabel label=new JLabel("Progreso " + titulo + " : ");
			jPanel.add(label);
			jPanel.add(getJProgressBar( ), null);
		}
		
		panel = new JFrame();
		panel.add(jPanel);
		panel.setVisible( true );
		panel.setLocation(300,300);
		panel.pack();
	}


//	==================================================================
	public void opFinalizada() 
//	==================================================================
	{
		opFinalizada = true;		
	}
	

//	==================================================================
	private JProgressBar getJProgressBar() 
//	==================================================================
	{
		if (jProgressBar == null) {
			//System.out.println("Creando barra...");
			jProgressBar = new JProgressBar(0,maxBarra);
			jProgressBar.setStringPainted(true);

		}
		return jProgressBar;
	}
	
//	==================================================================
	public synchronized void setJProgress(int n) 
//	==================================================================
	{
		if (jProgressBar != null) {
			if( n>0 && n<= jProgressBar.getMaximum() ){
				jProgressBar.setValue( n );
			}
		}
	}

	public void dispose()
	{
		panel.dispose();
	}
	
}

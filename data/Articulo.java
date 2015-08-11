package data;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import system.SysData;

public abstract class Articulo {

	public String aviso;

	public String codigo;

	public String codigo2;

	public double dto1;

	public double dto2;

	public double dto3;

	public double dto4;

	public double dto5;
	
	public double dto6;
	
	public double dto7;

	public double esc1desde;

	public double esc1hasta;

	public double esc2desde;

	public double esc2hasta;

	public double esc3desde;

	public double esc3hasta;

	public double esc4desde;

	public double esc4hasta;

	public double esc5desde;

	public double esc5hasta;

	public String familia;

	public String marca;

	public String nombre;

	public String observaciones;

	public double pendienteRec;

	public double pendienteServir;

	public double pvp1;

	public double pvp2;

	public double pvp3;

	public double pvp4;

	public double pvp5;
	
	public double pvp6;
	
	public double pvpIva;

	public String seccion;

	public String subfamilia;

	public int tipoIva;

	public double unidadesBulto;

	public double stock;

	public Date fechaPendienteRecibir;

	public Date fechaBaja;
	
	public String documento;
	
//	public SerialBlob imagen = null;

	// ==================================================================
	public Articulo() {	}
	// ==================================================================


	/**
	 * Metodo que devuelve una cadena de datos formateada para el envio a la PDA de un Articulo
	 * @param sys
	 * @return
	 */
//	 ==================================================================
	public String articuloEnvioPda()
//	 ==================================================================
	{
		String datos = new String();
		datos = aviso + "#"
		+ codigo + "#"
		+ codigo2 + "#"
		+ SysData.formatearDoubleToPda(dto1) + "#" 
		+ SysData.formatearDoubleToPda(dto2) + "#" 
		+ SysData.formatearDoubleToPda(dto3) + "#" 
		+ SysData.formatearDoubleToPda(dto4) + "#" 
		+ SysData.formatearDoubleToPda(dto5) + "#" 
		+ SysData.formatearDoubleToPda(dto6) + "#" 
		+ SysData.formatearDoubleToPda(dto7) + "#" 
		+ familia + "#" 
		+ marca + "#" 
		+ nombre + "#" 
		+ observaciones + "#" 
		+ SysData.formatearDoubleToPda(pendienteRec) + "#" 
		+ SysData.formatearDoubleToPda(pendienteServir) + "#" 
		+ SysData.formatearDoubleToPda(pvp1) + "#" 
		+ SysData.formatearDoubleToPda(pvp2) + "#" 
		+ SysData.formatearDoubleToPda(pvp3) + "#" 
		+ SysData.formatearDoubleToPda(pvp4) + "#" 
		+ SysData.formatearDoubleToPda(pvp5) + "#" 
		+ SysData.formatearDoubleToPda(pvp6) + "#"
		+ SysData.formatearDoubleToPda(pvpIva) + "#"
		+ tipoIva + "#"
		+ SysData.formatearDoubleToPda(unidadesBulto) + "#"
		+ SysData.formatearDoubleToPda(stock) + "#"
		+ fechaPendienteRecibir;
		return datos;
		
	}

	
	
	public abstract int getNumeroArticulos(Statement s);
	
	public abstract String getQueryArticulosPda();
	
	public abstract void setArticulo(ResultSet rs);
	
	public abstract String getQueryArticuloByCodigo(String cod);
	
	public String getCodigo() {
		return codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public double getUnidadesBulto() {
		return unidadesBulto;
	}
	
	public abstract double getPorcIva(SysData sys);
	
	public abstract boolean esActivo();		// metodo que nos dice si un articulo esta dado de baja o no
			
	public abstract boolean tieneLineaDto(ResultSet rs); 	// metodo que nos dice si un articulo tiene descuentos o no
	
	public abstract void setDescuentos(ResultSet rs,Statement st);
	
}

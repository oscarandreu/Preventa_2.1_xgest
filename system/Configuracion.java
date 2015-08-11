package system;

import java.io.Serializable;

/**
 * esta clase es contiene los parametros de configuracion que se guardan en el fichero en formato xml
 * configuracion.xml debido a eso imlementa serializable
 */

public class Configuracion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**Empresa que se esta utilizando*/
	protected String empresa = null;
	/**Ruta de la bbdd del erp*/
	protected String ruta = null;
	/**Usuario que hace login en la bbdd del erp*/
	protected String user = null;
	/**password del usuario que hace login en la bbdd del erp*/
	protected String password = null;
	/**Servidor web de mobisys que se encarga de la gestion*/
	protected String servidor = "http://www.mobisys.es";

	
// ==================================================================
	public Configuracion()
// ==================================================================
	{
		super();
	}

	
	public String getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(String empresas) {
		this.empresa = empresas;
	}

	public String getRuta() {
		return this.ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getServidor() {
		return this.servidor;
	}

	public void setServidor(String servidor) {
		this.servidor = servidor;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUser() {
		return this.user;
	}

	public void setUser(String user) {
		this.user = user;
	}


}

package bbdd;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import log.LogWriter;
import system.SysData;
import Gui.GuiInternalFrame;

public class InternalFrameTable extends DefaultTableModel {

	protected SysData sys;

	protected String query;

	public int numeroFilas;

	public JTable tabla;

	protected GuiInternalFrame frame;

	protected String[] primaryKeysFormateadas = null;

	protected String[] primaryKeys = null;

	protected String[] columnas;

	protected String[] columnasFormateadas = null;

	protected int[] anchoColumnas = null;
	
	protected String database;

	protected String order = null;

	protected String where = null;

	protected Vector vectorPrimaryKeys = null;

	/** Array que guarda los datos de las primarykeys */
	protected String[] keys = null;

	protected Vector vectorVerificados = null;

	/**
	 * Campo que sirve para saber si hay ue modificr los colores de las filas en
	 * base a un codigo indicaria el nombre del campo que contiene este codigo
	 * [0] tabla.campo [1] nombreFormateado
	 */
	protected String[] verificado = null;

	protected int codigo;

	// ==================================================================
	public InternalFrameTable(GuiInternalFrame frame)
	// ==================================================================
	{
		super();
		this.frame = frame;
		this.sys = frame.sys;
	}

	/** Genera la consulta a la base datos */
	// ==================================================================
	public String getQuery()
	// ==================================================================
	{
		query = "SELECT ";

		if (primaryKeys != null) {
			int j;
			keys = new String[primaryKeys.length];
			for (j = 0; j < primaryKeys.length; j++) {
				query += primaryKeys[j];
				if (primaryKeysFormateadas != null)
					query += " " + primaryKeysFormateadas[j];
				query += ",";
			}
		}
		for (int i = 0; i < columnas.length; i++) {
			if (i == 0) {
				query += columnas[i];
			} else {
				query += "," + columnas[i];
			}
			if (columnasFormateadas != null) {
				query += " \"" + columnasFormateadas[i] + "\"";
			}
		}
				
		if (verificado != null) {
			query += "," + verificado[0];
			vectorVerificados = new Vector();
		}

		query += " FROM " + database;
		if (where != null)
			query += " WHERE " + where;
		if (order != null)
			query += " ORDER BY " + order;
		return query;
	}

	// ==================================================================
	public void calcularAnchoColumnas()
	// ==================================================================
	{
		TableColumn column = null;
		if (columnas != null) {
			for (int i = 0; i < columnas.length; i++) {
				column = tabla.getColumnModel().getColumn(i);
				column.setPreferredWidth(anchoColumnas[i]);
			}
		}
	}

	/** Metodo que devuelve el valor de verificado de la fila correspondiente */
	// ==================================================================
	public String getValorVectorVerificados(int fila)
	// ==================================================================
	{
		return (String) vectorVerificados.get(fila);
	}

	// ==================================================================
	public JTable getTabla()
	// ==================================================================
	{
		try {
			setData(true);
			// TableSorter sorter = new TableSorter( this );
			// tabla = new JTable(sorter);
			// sorter.setTableHeader(tabla.getTableHeader());
			// tabla.getTableHeader().setToolTipText("Click para ordenar");

			/*
			 * Si hay que utilizar datos de verificación los uso sino creo una
			 * tabla normal S -> por verificar V -> verificado
			 */
			if (verificado != null) {
				tabla = new JTable(this) {
					public TableCellRenderer getCellRenderer(int fila,
							int columna) {
						DefaultTableCellRenderer out = new DefaultTableCellRenderer();
						if ("S".equals(vectorVerificados.get(fila))) {
							out.setBackground(new Color(254, 183, 106));// Rojo
						} else {
							out.setBackground(new Color(238, 251, 239));// Verde
						}
						return out;
					}

				};
			} else {
				tabla = new JTable(this);
			}
			// Modificador para que las tablas se estiren
			tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			tabla.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					int i = tabla.getSelectedRow();
					if (i != -1) {
						frame.mostrarDatos(getWherePrimaryKeys(i));
					}
				}
			});
		} catch (SQLException e1) {
			e1.printStackTrace();
			new LogWriter( e1.getStackTrace() );
		}
		calcularAnchoColumnas();
		return tabla;
	}

	// ==================================================================
	protected String getWherePrimaryKeys(int fila)
	// ==================================================================
	{
		String out = "";
		if (primaryKeys != null) {
			String[] data = new String[primaryKeys.length];
			data = (String[]) vectorPrimaryKeys.get(fila);

			for (int i = 0; i < data.length; i++) {
				if (i == 0) {
					out += primaryKeys[i] + "='" + data[i] + "'";
				} else {
					out += " AND " + primaryKeys[i] + "='" + data[i] + "'";
				}
			}
		} else {
			out += columnas[0] + "='" + (String) tabla.getValueAt(fila, 0)
					+ "'";
		}
		return out;
	}

	// ==================================================================
	@SuppressWarnings("unchecked")
	public void insertarFila(ResultSet rs)
	// ==================================================================
	{
		try {
			// pongo las claves primarias en un vector para busquedas
			// posteriores
			if (primaryKeys != null) {
				for (int i = 0; i < primaryKeys.length; i++) {
					if (primaryKeysFormateadas != null) {
						keys[i] = rs.getString(primaryKeysFormateadas[i]);
					} else {
						keys[i] = rs.getString(primaryKeys[i]);
					}

				}
				vectorPrimaryKeys.add(keys.clone());
			}
			// preparo los datos de la columna
			Vector v = new Vector();
			if (columnasFormateadas != null) {
				for (int i = 0; i < columnasFormateadas.length; i++) {
					v.add(rs.getString(columnasFormateadas[i]));
				}
			} else {
				for (int i = 0; i < columnas.length; i++) {
					System.out.println("col :" + columnas[i] + "rs.getString:" + rs.getString(columnas[i]));
					v.add(rs.getString(columnas[i]));
				}
			}
			// Creo un vector con los datos de verificacion
			if (verificado != null) {
				vectorVerificados.add(rs.getString(verificado[1]));
			}
			insertRow(getRowCount(), v.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}

	/**
	 * Carga los datos de la tabla si true monta las cabeceras si false solo
	 * monta los datos
	 */
	// ==================================================================
	public void setData(boolean setCabecera) throws SQLException
	// ==================================================================
	{
		Connection c = null;
		c = sys.getConexionPropia();
		vectorPrimaryKeys = new Vector();
		Statement s = c.createStatement();
		getQuery();
		System.out.println(query);
		ResultSet rs = s.executeQuery(query);
		for (int i = getRowCount() - 1; i >= 0; i--) {
			removeRow(i);
		}
		if (setCabecera) {
			ResultSetMetaData rsMetaData = rs.getMetaData();
			// Ajusta el numero de las cabeceras si hay primaryKeys que no
			// queremos mostrar
			int ajuste = 0;
			if (primaryKeys != null)
				ajuste = primaryKeys.length;
			// Ajusta el numero de las cabeceras si usamos verificar
			int fin = rsMetaData.getColumnCount();
			if (verificado != null)
				fin--;
			for (int i = 1 + ajuste; i <= fin; i++) {
				addColumn(rsMetaData.getColumnName(i));
			}
		}
		numeroFilas = 0;
		
		// cargo todos los datos en la tabla
		while (rs.next()) {
			insertarFila(rs);
			numeroFilas++;
		}
		rs.close();
		s.close();
		c.close();
	}

	public void setAnchoColumnas(int[] anchoColumnas) {
		this.anchoColumnas = anchoColumnas;
	}

	public void setColumnas(String[] columnas) {
		this.columnas = columnas;
	}

	public void setColumnasFormateadas(String[] columnasFormateadas) {
		this.columnasFormateadas = columnasFormateadas;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public void setPrimaryKeys(String[] primaryKeys) {
		this.primaryKeys = primaryKeys;
	}

	public void setPrimaryKeysFormateadas(String[] primaryKeysFormateadas) {
		this.primaryKeysFormateadas = primaryKeysFormateadas;
	}

	public void setVerificado(String[] verificado) {
		this.verificado = verificado;
	}

}

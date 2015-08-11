package bbdd;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import log.LogWriter;

import system.SysData;
import Gui.GuiJPanel;

public class JPanelTable extends DefaultTableModel {

	protected SysData sys;

	protected String query;

	public int numeroFilas;

	public JTable tabla;

	protected GuiJPanel frame;

	protected String[] primaryKeys = null;

	protected String[] columnas;

	protected String[] columnasFormateadas = null;

	protected int[] anchoColumnas = null;

	protected String database;

	protected String order = null;

	protected String where = null;

	protected Vector vectorPrimaryKeys = null;

	protected String[] keys = null;

	// ==================================================================
	public JPanelTable(GuiJPanel frame)
	// ==================================================================
	{
		super();
		this.frame = frame;
		this.sys = frame.sys;
	}

	// ==================================================================
	public String getQuery()
	// ==================================================================
	{
		query = "SELECT ";

		if (primaryKeys != null) {
			int j;
			keys = new String[primaryKeys.length];
			vectorPrimaryKeys = new Vector();
			for (j = 0; j < primaryKeys.length; j++) {
				query += primaryKeys[j] + ",";
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
			System.out.println(query + ":" + i);
		}

		query += " FROM " + database;
		if (where != null)
			query += " WHERE " + where;
		if (order != null)
			query += " ORDER BY " + order;
		System.out.println(query);
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

	// ==================================================================
	public JTable getTabla()
	// ==================================================================
	{
		try {
			setData(true);
			TableSorter sorter = new TableSorter(this);
			tabla = new JTable(sorter);
			sorter.setTableHeader(tabla.getTableHeader());
			tabla.getTableHeader().setToolTipText("Click para ordenar");
			calcularAnchoColumnas();
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

		return tabla;
	}

	// ==================================================================
	protected String getWherePrimaryKeys(int fila)
	// ==================================================================
	{
		String out = "";
		if (primaryKeys != null) {
			keys = (String[]) vectorPrimaryKeys.get(fila);

			for (int i = 0; i < keys.length; i++) {
				if (i == 0)
					out += primaryKeys[i] + "='" + keys[i] + "'";
				else
					out += " AND " + primaryKeys[i] + "='" + keys[i] + "'";
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
			Vector v = new Vector();
			if (primaryKeys != null) {
				for (int i = 0; i < primaryKeys.length; i++) {
					keys[i] = rs.getString(primaryKeys[i]);
				}
				vectorPrimaryKeys.add(keys.clone());
			}
			if (columnasFormateadas != null) {
				for (int i = 0; i < columnasFormateadas.length; i++) {
					v.add(rs.getString(columnasFormateadas[i]));
				}
			} else {
				for (int i = 0; i < columnas.length; i++) {
					v.add(rs.getString(columnas[i]));
				}
			}
			insertRow(getRowCount(), v.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
			new LogWriter( e.getStackTrace() );
		}
	}

	// ==================================================================
	public void setData(boolean setCabecera) throws SQLException
	// ==================================================================
	{
		getQuery();
		Connection c = null;
		c = sys.getConexionPropia();

		Statement s = c.createStatement();
		ResultSet rs = s.executeQuery(query);
		for (int i = getRowCount() - 1; i >= 0; i--) {
			removeRow(i);
		}
		if (setCabecera) {
			ResultSetMetaData rsMetaData = rs.getMetaData();
			// Ajusta la cuenta si hay primaryKeys que no queremos mostrar
			int ajuste = 0;
			if (primaryKeys != null)
				ajuste = primaryKeys.length;
			for (int i = 1 + ajuste; i <= rsMetaData.getColumnCount(); i++) {
				addColumn(rsMetaData.getColumnName(i));
			}

		}
		numeroFilas = 0;

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

}

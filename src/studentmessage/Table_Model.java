package studentmessage;

import java.util.Vector;
/**
 * �����������ݱ�����
 */

import javax.swing.table.AbstractTableModel;

public class Table_Model extends AbstractTableModel {
	String[] title_name = {"���","ѧ��","����","�Ա�","רҵ","�꼶","����"};
	  private static final long serialVersionUID = -3094977414157589758L;

	        private Vector content = null;


	        public Table_Model() {
	            content = new Vector();
	        }

	        public Table_Model(int count) {
	            content = new Vector(count);
	        }

	        /** 
	         * ����һ���� 
	         * @param row �к� 
	         */
	        public void addRow(int row) {
	            Vector v = new Vector(7);
	            v.add(0, null);
	            v.add(1, null);
	            v.add(2, null);
	            v.add(3,null);
	            v.add(4,null);
	            v.add(5,null);
	            v.add(6,null);
	            content.add(row, v);
	        }

	        /** 
	         * ����һ������ 
	         */
	        public void addRow(int N, String number,String name,String sex,String major,String grade,String birth) {
	            Vector v = new Vector(7);
	            v.add(0,N);
	            v.add(1,number); 
	            v.add(2, name); 
	            v.add(3,sex);
	            v.add(4,major);
	            v.add(5,grade);
	            v.add(6,birth);
	            content.add(v);
	        }

	        public void removeRow(int row) {
	            content.remove(row);
	        }

	        public boolean isCellEditable(int rowIndex, int columnIndex) {
	            if(rowIndex == 2) {
	                return false;
	            }
	            return true;
	        }

	        public void setValueAt(Object value, int row, int col) {
	            ((Vector) content.get(row)).remove(col);
	            ((Vector) content.get(row)).add(col, value);
	            this.fireTableCellUpdated(row, col);
	        }

	        public String getColumnName(int col) {
	            return title_name[col];
	        }

	        public int getColumnCount() {
	            return title_name.length;
	        }

	        public int getRowCount() {
	            return content.size();
	        }

	        public Object getValueAt(int row, int col) {
	            return ((Vector) content.get(row)).get(col);
	        }

	        public Class getColumnClass(int col) {
	            return getValueAt(0, col).getClass();
	        }
		
	}

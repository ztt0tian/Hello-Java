package studentmessage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class ShowAllinformation extends JFrame  {
	private HashMap<String,Student> informationtable;
	private FileInputStream fileinputstream=null;//文件输入流对象
	private ObjectInputStream objectinputstream=null;//对象输入流对象
	private JTextField numberField,nameField,gradeField,birthdayField,sexField,majorField;
	private File systemFile;
    private JTable table = null;
    private Table_Model model = null;
    private JScrollPane s_pan = null;


	public ShowAllinformation(File file) {
		systemFile=file;
		informationtable=new HashMap<String,Student>();
			
		
	}

	public void showAll() {
		try{
			fileinputstream=new FileInputStream(systemFile);
			objectinputstream=new ObjectInputStream(fileinputstream);
			informationtable=(HashMap<String, Student>) objectinputstream.readObject();
			fileinputstream.close();
			objectinputstream.close();
		}catch(Exception ee)
		{
			
		}
		JFrame f=new JFrame("所有学生信息");
	    Student stu[]=new Student[20];
	    Iterator iter = informationtable.entrySet().iterator();
	    int i=0;
	    model = new Table_Model(20);
        table = new JTable(model);
        while (iter.hasNext()) {
        	java.util.Map.Entry entry = (java.util.Map.Entry)iter.next();
        	stu[i]=(Student) entry.getValue();
        	model.addRow(i+1, stu[i].getNumber(), stu[i].getName(),stu[i].getSex(),stu[i].getMajor(),stu[i].getGrade(),stu[i].getBirthday());
            i++;    
          }
        System.out.println(i);
        //model.addRow(i+1, stu[i].getNumber(), stu[i].getName(),stu[i].getSex(),stu[i].getMajor(),stu[i].getGrade(),stu[i].getBirthday());
      
		f.setSize(500, 300);
        s_pan = new JScrollPane(table);
        f.getContentPane().add(s_pan, BorderLayout.CENTER); 
	    f.setVisible(true);  
	   f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	
	}
}


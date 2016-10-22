package studentmessage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import javax.swing.*;

public class Deleteinformation extends JPanel implements ActionListener {

	private HashMap<String,Student> informationtable=null;
	private JTextField numberField,nameField,sexField,majorField,gradeField,birthdayField;
	private JButton deleteButton;
	private FileInputStream fileinputstream=null;//文件输入流对象
	private ObjectInputStream objectinputstream=null;//对象输入流对象
	private FileOutputStream fileoutputstream=null;//文件输出流对象
	private ObjectOutputStream objectoutputstream=null;//对象输出流对象
	private File systemFile;
	private JPanel panel;
	
	public Deleteinformation(File file) {
		systemFile=file;
		informationtable=new HashMap<String,Student>();
		initMessPanel();
		add(panel);
		validate();
	
	}

	public void initMessPanel() {
		JLabel numberLabel=new JLabel("学号:",JLabel.CENTER);
		numberField=new JTextField(10);
		deleteButton=new JButton("删除");
		numberField.addActionListener(this);
		deleteButton.addActionListener(this);
		Box numberBox=Box.createHorizontalBox();
		numberBox.add(numberLabel);
		numberBox.add(numberField);
		numberBox.add(deleteButton);
		JLabel nameLabel=new JLabel("姓名:",JLabel.CENTER);
		nameField=new JTextField(10);
		nameField.setEnabled(false);
		Box nameBox=Box.createHorizontalBox();
		nameBox.add(nameLabel);
		nameBox.add(nameField);
		JLabel sexLabel=new JLabel("性别:",JLabel.CENTER);
		sexField=new JTextField(10);
		sexField.setEnabled(false);
		Box sexBox=Box.createHorizontalBox();
		sexBox.add(sexLabel);
		sexBox.add(sexField);
		JLabel majorLabel=new JLabel("专业:",JLabel.CENTER);
		majorField=new JTextField(10);
		majorField.setEnabled(false);
		Box majorBox=Box.createHorizontalBox();
		majorBox.add(majorLabel);
		majorBox.add(majorField);
		JLabel gradeLabel=new JLabel("年级;",JLabel.CENTER);
		gradeField=new JTextField(10);
		gradeField.setEnabled(false);
		Box gradeBox=Box.createHorizontalBox();
		gradeBox.add(gradeLabel);
		gradeBox.add(gradeField);
		JLabel birthLabel=new JLabel("出生:",JLabel.CENTER);
		birthdayField=new JTextField(10);
		birthdayField.setEnabled(false);
		Box birthBox=Box.createHorizontalBox();
		birthBox.add(birthLabel);
		birthBox.add(birthdayField);
		Box BoxH=Box.createVerticalBox();
		BoxH.add(numberBox);
		BoxH.add(nameBox);
		BoxH.add(sexBox);
		BoxH.add(majorBox);
		BoxH.add(gradeBox);
		BoxH.add(birthBox);
		panel=new JPanel();
		panel.add(BoxH);
					
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==deleteButton||e.getSource()==numberField)
		{
			String number="";
			number=numberField.getText();
			if(number.length()>0){
				try{
					fileinputstream=new FileInputStream(systemFile);
					objectinputstream=new ObjectInputStream(fileinputstream);
					informationtable=(HashMap<String, Student>) objectinputstream.readObject();
					objectinputstream.close();
					fileinputstream.close();
				}catch(Exception ee){}
				if(informationtable.containsKey(number))
				{
					Student stu=new Student();
					stu=informationtable.get(number);
					nameField.setText(stu.getName());
					sexField.setText(stu.getSex());
					majorField.setText(stu.getMajor());
					gradeField.setText(stu.getGrade());
					birthdayField.setText(stu.getBirthday());
					String m="确定删除该学号对应学生的所有信息么？";
					int ok=JOptionPane.showConfirmDialog(this, m, "确认", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(ok==JOptionPane.YES_OPTION){
						informationtable.remove(number);
						try{
							fileoutputstream=new FileOutputStream(systemFile);
							objectoutputstream=new ObjectOutputStream(fileoutputstream);
							objectoutputstream.writeObject(informationtable);
							objectoutputstream.close();
							fileoutputstream.close();
							clearMessage();
						}catch(Exception ee){}
					}
					else if(ok==JOptionPane.NO_OPTION)
					{
						clearMessage();
					}
				}
				else
				{
					String waring="该学号不存在！";
					JOptionPane.showConfirmDialog(this, waring,"警告",JOptionPane.WARNING_MESSAGE);
					numberField.setText(null);
					
				}
				
			}
			else
			{
				String waring="必须输入学号！";
				JOptionPane.showConfirmDialog(this, waring,"警告",JOptionPane.WARNING_MESSAGE);
				
				
			}
		}
		
	}

	public void clearMessage() {
		numberField.setText(null);
		nameField.setText(null);
		gradeField.setText(null);
		birthdayField.setText(null);
		sexField.setText(null);
		majorField.setText(null);
		
	}

}

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
	private FileInputStream fileinputstream=null;//�ļ�����������
	private ObjectInputStream objectinputstream=null;//��������������
	private FileOutputStream fileoutputstream=null;//�ļ����������
	private ObjectOutputStream objectoutputstream=null;//�������������
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
		JLabel numberLabel=new JLabel("ѧ��:",JLabel.CENTER);
		numberField=new JTextField(10);
		deleteButton=new JButton("ɾ��");
		numberField.addActionListener(this);
		deleteButton.addActionListener(this);
		Box numberBox=Box.createHorizontalBox();
		numberBox.add(numberLabel);
		numberBox.add(numberField);
		numberBox.add(deleteButton);
		JLabel nameLabel=new JLabel("����:",JLabel.CENTER);
		nameField=new JTextField(10);
		nameField.setEnabled(false);
		Box nameBox=Box.createHorizontalBox();
		nameBox.add(nameLabel);
		nameBox.add(nameField);
		JLabel sexLabel=new JLabel("�Ա�:",JLabel.CENTER);
		sexField=new JTextField(10);
		sexField.setEnabled(false);
		Box sexBox=Box.createHorizontalBox();
		sexBox.add(sexLabel);
		sexBox.add(sexField);
		JLabel majorLabel=new JLabel("רҵ:",JLabel.CENTER);
		majorField=new JTextField(10);
		majorField.setEnabled(false);
		Box majorBox=Box.createHorizontalBox();
		majorBox.add(majorLabel);
		majorBox.add(majorField);
		JLabel gradeLabel=new JLabel("�꼶;",JLabel.CENTER);
		gradeField=new JTextField(10);
		gradeField.setEnabled(false);
		Box gradeBox=Box.createHorizontalBox();
		gradeBox.add(gradeLabel);
		gradeBox.add(gradeField);
		JLabel birthLabel=new JLabel("����:",JLabel.CENTER);
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
					String m="ȷ��ɾ����ѧ�Ŷ�Ӧѧ����������Ϣô��";
					int ok=JOptionPane.showConfirmDialog(this, m, "ȷ��", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
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
					String waring="��ѧ�Ų����ڣ�";
					JOptionPane.showConfirmDialog(this, waring,"����",JOptionPane.WARNING_MESSAGE);
					numberField.setText(null);
					
				}
				
			}
			else
			{
				String waring="��������ѧ�ţ�";
				JOptionPane.showConfirmDialog(this, waring,"����",JOptionPane.WARNING_MESSAGE);
				
				
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

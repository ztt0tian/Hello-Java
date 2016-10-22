package studentmessage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import javax.swing.*;

public class Queryinformation extends JPanel implements ActionListener {
	
	private Student student=null;
	private StudentPicture studentpicture;//ѧ��ͼ��
	private HashMap<String,Student>informationtable;
	private JTextField numberField,nameField,gradeField,birthdayField,sexField,majorField;
	private JButton queryButton=new JButton("��ѯ");
	private FileInputStream fileinputstream=null;//�ļ�����������
	private ObjectInputStream objectinputstream=null;//��������������
	private FileOutputStream fileoutputstream=null;//�ļ����������
	private ObjectOutputStream objectoutputstream=null;//�������������
	private File systemFile,imgagePic;
	private JPanel messPanel;//������Ϣ����Ƭ������
	

	public Queryinformation(File file) {
		systemFile=file;
		student=new Student();
		studentpicture=new StudentPicture();
		informationtable=new HashMap<String,Student>();
		JLabel numberLabel=new JLabel("������Ҫ��ѯѧ��ѧ��:",JLabel.CENTER);
		numberField=new JTextField(10);
		numberField.addActionListener(this);
		queryButton.addActionListener(this);
		Box numberBox=Box.createHorizontalBox();
		numberBox.add(numberLabel);
		numberBox.add(numberField);
		numberBox.add(queryButton);
		initMessPanel();
		JLabel picLabel=new JLabel("��Ƭ:",JLabel.LEFT);
		JPanel picPanel=new JPanel();
		picPanel.setLayout(new BorderLayout());
		picPanel.add(picLabel,BorderLayout.NORTH);
		picPanel.add(studentpicture, BorderLayout.CENTER);
		JSplitPane splitH=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,messPanel,picPanel);
		setLayout(new BorderLayout());
		add(numberBox,BorderLayout.NORTH);
		add(splitH,BorderLayout.CENTER);
		validate();
	}


	private void initMessPanel() {
		JLabel nameLabel =new JLabel("����:",JLabel.CENTER);
		nameField=new JTextField(10);
		nameField.setEnabled(false);
		Box nameBox=Box.createHorizontalBox();
		nameBox.add(nameLabel);
		nameBox.add(nameField);
		JLabel sexLabel =new JLabel("�Ա�:",JLabel.CENTER);
		sexField=new JTextField(10);
		sexField.setEnabled(false);
		Box sexBox=Box.createHorizontalBox();
		sexBox.add(sexLabel);
		sexBox.add(sexField);
		JLabel gradeLabel =new JLabel("�꼶:",JLabel.CENTER);
		gradeField=new JTextField(10);
		gradeField.setEnabled(false);
		Box gradeBox=Box.createHorizontalBox();
		gradeBox.add(gradeLabel);
		gradeBox.add(gradeField);
		JLabel majorLabel =new JLabel("רҵ:",JLabel.CENTER);
		majorField=new JTextField(10);
		majorField.setEnabled(false);
		Box majorBox=Box.createHorizontalBox();
		majorBox.add(majorLabel);
		majorBox.add(majorField);
		JLabel birthLabel =new JLabel("����:",JLabel.CENTER);
		birthdayField=new JTextField(10);
		birthdayField.setEnabled(false);
		Box birthdayBox=Box.createHorizontalBox();
		birthdayBox.add(birthLabel);
		birthdayBox.add(birthdayField);
		Box BoxH=Box.createVerticalBox();
		BoxH.add(nameBox);
		BoxH.add(sexBox);
		BoxH.add(majorBox);
		BoxH.add(gradeBox);
		BoxH.add(birthdayBox);
		BoxH.add(Box.createVerticalGlue());
		messPanel=new JPanel();
		messPanel.add(BoxH);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==queryButton||e.getSource()==numberField)
		{
			String number="";
			number=numberField.getText();
			if(number.length()>0)
			{
				try{
					fileinputstream=new FileInputStream(systemFile);
					objectinputstream=new ObjectInputStream(fileinputstream);
					informationtable=(HashMap<String, Student>) objectinputstream.readObject();
					fileinputstream.close();
					objectinputstream.close();
				}catch(Exception ee)
				{
					
				}
				if(informationtable.containsKey(number))//��ѧ������
				{
					student=informationtable.get(number);
					nameField.setText(student.getName());
					nameField.setFont(new Font("����",Font.BOLD,12));
					nameField.setBackground(Color.black);
					majorField.setText(student.getMajor());
					majorField.setFont(new Font("����",Font.BOLD,12));
					majorField.setBackground(Color.black);
				    gradeField.setText(student.getGrade());
				    gradeField.setFont(new Font("����",Font.BOLD,12));
					gradeField.setBackground(Color.black);
					birthdayField.setText(student.getBirthday());
					birthdayField.setFont(new Font("����",Font.BOLD,12));
					birthdayField.setBackground(Color.black);
					sexField.setText(student.getSex());
					sexField.setFont(new Font("����",Font.BOLD,12));
					sexField.setBackground(Color.black);
					imgagePic=student.getImagePic();
					studentpicture.setImage(imgagePic);
					studentpicture.repaint();
				}
				else{//�����ѧ�Ų�����
					
					String waring="��ѧ�Ų����ڣ�";
					JOptionPane.showMessageDialog(this, waring,"����",JOptionPane.WARNING_MESSAGE);
					clearMessage();
				}
				
			}
			else{
				//û������ѧ��
		
				String waring="������ѧ�ţ�";
				JOptionPane.showMessageDialog(this, waring,"����",JOptionPane.WARNING_MESSAGE);
				clearMessage();
				
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
		imgagePic=null;
		studentpicture.setImage(imgagePic);
		studentpicture.repaint();
		
	}

}

package studentmessage;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Modifyinformation extends JPanel implements ActionListener{
	
    private Student student=null;
	private StudentPicture studentpicture;//ѧ��ͼ��
	private HashMap<String,Student>informationtable=null;
	private JTextField numberField,nameField,gradeField,birthdayField;
	private JButton picButton;//ѡ����Ƭ��ť
	private JLabel promotLabel;//��ʾ��Ϣ
	private JComboBox majorComBox;//רҵ�б��
	private JRadioButton maleRButton,femaleRButton;//��ѡ��ť��ѡ���л���Ů
	private ButtonGroup buttongroup=null;
	private JButton beginmodifyButton,modifyButton,resetButton;//���밴ť�����ð�ť
	private FileInputStream fileinputstream=null;//�ļ�����������
	private ObjectInputStream objectinputstream=null;//��������������
	private FileOutputStream fileoutputstream=null;//�ļ����������
	private ObjectOutputStream objectoutputstream=null;//�������������
	private File systemFile,imgagePic;
	private JPanel messPanel;//��ʾ������Ϣ������

	/**
	 * ���캯��
	 */
	public Modifyinformation(File file) {
	    systemFile=file;
	    studentpicture=new StudentPicture();
	    informationtable=new HashMap<String,Student>();
	    initMessPanel();
	    picButton=new JButton("ѡ����Ƭ");
	    picButton.addActionListener(this);
	    JPanel picPanel=new JPanel();
	    picPanel.add(picButton);
	    modifyButton=new JButton("�޸�");
		resetButton=new JButton("����");
		modifyButton.addActionListener(this);
		resetButton.addActionListener(this);
		JPanel ButtonPanel=new JPanel();
		ButtonPanel=new JPanel();
		ButtonPanel.setBackground(new Color(216,243,231));
		ButtonPanel.add(modifyButton);
		ButtonPanel.add(resetButton);	
		setLayout(new BorderLayout());
		JSplitPane splitV=new JSplitPane(JSplitPane.VERTICAL_SPLIT,picPanel,studentpicture);
		JSplitPane splitH=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,messPanel,splitV);
		add(splitH,BorderLayout.CENTER);
		add(ButtonPanel,BorderLayout.SOUTH);
		validate();
		
	}
	
	public void initMessPanel() {
		JLabel numberLabel =new JLabel("(��)ѧ��:",JLabel.CENTER);
		numberField=new JTextField(5);
		beginmodifyButton=new JButton("��ʼ�޸�");
		beginmodifyButton.addActionListener(this);
		numberField.addActionListener(this);
		Box numberBox=Box.createHorizontalBox();//���ˮƽbox,����һ����������ʾ������� Box
		numberBox.add(numberLabel);
		numberBox.add(numberField);
		numberBox.add(beginmodifyButton);
		
		JLabel nameLabel =new JLabel("(��)����:",JLabel.CENTER);
		nameField=new JTextField(5);
		Box nameBox=Box.createHorizontalBox();//���ˮƽbox
		nameBox.add(nameLabel);
		nameBox.add(nameField);
		JLabel sexLabel =new JLabel("(��)�Ա�:",JLabel.CENTER);
		maleRButton=new JRadioButton("��",true);
		femaleRButton=new JRadioButton("Ů",false);
		buttongroup=new ButtonGroup();
		buttongroup.add(femaleRButton);
		buttongroup.add(maleRButton);
		Box sexBox=Box.createHorizontalBox();
		sexBox.add(sexLabel);
		sexBox.add(maleRButton);
		sexBox.add(femaleRButton);
		JLabel majorLabel=new JLabel("(��)רҵ:",JLabel.CENTER);
		majorComBox=new JComboBox<String>();
		try{
			//���ļ��ж���רҵ���ƣ����뵽��Ͽ���
			FileReader filereader=new FileReader("רҵ.txt");
			BufferedReader reader=new BufferedReader(filereader);
			String s=null;
			while((s=reader.readLine())!=null)
			{
				majorComBox.addItem(s);
			}
			filereader.close();
			reader.close();
		}catch(IOException e){
			majorComBox.addItem("��ѧ");
			majorComBox.addItem("�������ѧ�뼼��");
		}
		Box majorBox=Box.createHorizontalBox();
		majorBox.add(majorLabel);
		majorBox.add(majorComBox);
		JLabel gradeLabel=new JLabel("(��)�꼶:",JLabel.CENTER);
		gradeField=new JTextField(5);
		Box gradeBox=Box.createHorizontalBox();
		gradeBox.add(gradeLabel);
		gradeBox.add(gradeField);
		JLabel birthdayLabel=new JLabel("(��)����:",JLabel.CENTER);
		birthdayField=new JTextField(5);
		Box birthBox=Box.createHorizontalBox();
		birthBox.add(birthdayLabel);
		birthBox.add(birthdayField);
		Box boxH=Box.createVerticalBox();
		boxH.add(numberBox);
		boxH.add(nameBox);
		boxH.add(sexBox);
		boxH.add(majorBox);
		boxH.add(gradeBox);
		boxH.add(birthBox);
		boxH.add(Box.createVerticalGlue());
		messPanel=new JPanel();
		messPanel.add(boxH);				
		
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==beginmodifyButton||e.getSource()==numberField)
		{
			String number="";
			imgagePic=null;
			student=null;
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
					modifyButton.setEnabled(true);
					picButton.setEnabled(true);
					student=informationtable.get(number);
					nameField.setText(student.getName());
					if(student.getSex().equals("��"))
					{
						maleRButton.setSelected(true);
					}
					else femaleRButton.setSelected(true);
					gradeField.setText(student.getGrade());
					birthdayField.setText(student.getBirthday());
					imgagePic=student.getImagePic();
					studentpicture.setImage(imgagePic);
					studentpicture.repaint();
				}
				else{//�����ѧ�Ų�����
					modifyButton.setEnabled(false);
					picButton.setEnabled(false);
					String waring="��ѧ�Ų����ڣ�";
					JOptionPane.showMessageDialog(this, waring,"����",JOptionPane.WARNING_MESSAGE);
					clearMessage();
				}
				
			}
			else{
				//û������ѧ��
				modifyButton.setEnabled(false);
				picButton.setEnabled(false);
				String waring="������ѧ�ţ�";
				JOptionPane.showMessageDialog(this, waring,"����",JOptionPane.WARNING_MESSAGE);
				clearMessage();
				
			}
		}
		else if(e.getSource()==modifyButton){
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
				if(informationtable.containsKey(number))//ѧ�Ŵ��ھ��޸�
				{
					String question="ȷ���޸ĸ����Ļ�����Ϣô��";
					JOptionPane.showMessageDialog(this, question,"����",JOptionPane.QUESTION_MESSAGE);
					String m="������Ϣ�����޸�";
					int ok=JOptionPane.showConfirmDialog(this, m, "ȷ��", JOptionPane.YES_NO_OPTION	,JOptionPane.INFORMATION_MESSAGE);
					if(ok==JOptionPane.YES_OPTION){
						String name=nameField.getText();
						if(name.length()==0)
							name=student.getName();
						String sex=null;
						if(maleRButton.isSelected())
						{
							sex=maleRButton.getText();
						}else sex=femaleRButton.getText();
						String major=(String) majorComBox.getSelectedItem();
						if(major==null)
							major=student.getMajor();
						String grade=gradeField.getText();
						if(grade.length()==0)
							grade=student.getGrade();
						String birth=birthdayField.getText();
						if(birth.length()==0)
							birth=student.getBirthday();
						if(imgagePic==null)
						{
							imgagePic=student.getImagePic();
						}
						Student stu=new Student();
						stu.setNumber(number);
						stu.setName(name);
						stu.setMajor(major);
						stu.setGrade(grade);
						stu.setBirthday(birth);
						stu.setSex(sex);
						stu.setImagePic(imgagePic);
						try{
							fileoutputstream=new FileOutputStream(systemFile);
							objectoutputstream=new ObjectOutputStream(fileoutputstream);
							informationtable.put(number, stu);
							objectoutputstream.writeObject(informationtable);
							objectoutputstream.close();
							fileoutputstream.close();
							clearMessage();
						}catch(Exception ee){}
						modifyButton.setEnabled(false);
						picButton.setEnabled(false);
						
					}else if(ok==JOptionPane.NO_OPTION){
						modifyButton.setEnabled(true);
						picButton.setEnabled(true);
					}
					
				}
				else{//�����ѧ�Ų�����
					modifyButton.setEnabled(false);
					picButton.setEnabled(false);
					String waring="û�и�ѧ��ѧ���Ļ�����Ϣ��";
					JOptionPane.showMessageDialog(this, waring,"����",JOptionPane.WARNING_MESSAGE);
					clearMessage();
				}
			}
			else{
				//û������ѧ��
				modifyButton.setEnabled(false);
				picButton.setEnabled(false);
				String waring="��������ѧ�ţ�";
				JOptionPane.showMessageDialog(this, waring,"����",JOptionPane.WARNING_MESSAGE);
				clearMessage();
				
			}
			
		}
		else if(e.getSource()==picButton)
		{
			JFileChooser chooser=new JFileChooser();
			FileNameExtensionFilter filter=new FileNameExtensionFilter("JPG&GIF Images","jpg","gif");
			chooser.setFileFilter(filter);
			int state=chooser.showOpenDialog(null);
			File choice=chooser.getSelectedFile();
			if(choice!=null&&state==JFileChooser.APPROVE_OPTION)
			{
				picButton.setText("����ѡ��");
				imgagePic=choice;
				studentpicture.setImage(imgagePic);
				studentpicture.repaint();//��ʾ��Ƭ
				
			}
		}
		else if(e.getSource()==resetButton)
		{
			clearMessage();
			modifyButton.setEnabled(false);
			picButton.setEnabled(false);
			
		}
		
	}

	public void clearMessage() {
		numberField.setText(null);
		nameField.setText(null);
		gradeField.setText(null);
		birthdayField.setText(null);
		picButton.setText("ѡ����Ƭ");
		imgagePic=null;
		studentpicture.setImage(imgagePic);
		studentpicture.repaint();
		
	}

	
}

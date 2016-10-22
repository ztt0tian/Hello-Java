package studentmessage;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Inputinformation extends JPanel implements ActionListener{
	private Student student=null;
	private StudentPicture studentpicture;//ѧ��ͼ��
	private HashMap<String,Student>informationtable;
	private JTextField numberField,nameField,gradeField,birthdayField;
	private JButton picButton;//ѡ����Ƭ��ť
	private JLabel promotLabel;//��ʾ��Ϣ
	private JComboBox<String>majorComBox;//רҵ�б��
	private JRadioButton maleRButton,femaleRButton;//��ѡ��ť��ѡ���л���Ů
	private ButtonGroup buttongroup=null;
	private JButton inputButton,resetButton;//���밴ť�����ð�ť
	private FileInputStream fileinputstream=null;//�ļ�����������
	private ObjectInputStream objectinputstream=null;//��������������
	private FileOutputStream fileoutputstream=null;//�ļ����������
	private ObjectOutputStream objectoutputstream=null;//�������������
	private File systemFile,imgagePic;
	private JPanel ButtonPanel;//¼������ð�ť������
	private JPanel messPanel,picPanel;//������Ϣ����Ƭ������
	public Inputinformation(File file) {
		systemFile=file;
		studentpicture=new StudentPicture();
		informationtable=new HashMap<String,Student>();
		promotLabel=new JLabel("������������Ϣ:",JLabel.LEFT);
		promotLabel.setFont(new Font("����",Font.BOLD,13));
		promotLabel.setForeground(Color.red);
		promotLabel.setOpaque(true);
		promotLabel.setBackground(new Color(216,224,231));
		initMessPanel();
		initButtonPanel();
		initPicPanel();
		setLayout(new BorderLayout());
		JSplitPane splitH=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,messPanel,picPanel);
		add(promotLabel,BorderLayout.NORTH);
		add(splitH, BorderLayout.CENTER);
		add(ButtonPanel, BorderLayout.SOUTH);
		validate();
	}
	/**
	 * ��ʼ����Ƭ���ֵĽ���
	 */
	private void initPicPanel() {
		JLabel picLabel=new JLabel("��Ƭ:",JLabel.LEFT);
		picButton=new JButton("ѡ����Ƭ");
		picButton.addActionListener(this);
		picPanel=new JPanel();
		picPanel.setLayout(new BorderLayout());
		picPanel.add(picLabel,BorderLayout.NORTH);
		picPanel.add(studentpicture, BorderLayout.CENTER);
		picPanel.add(picButton, BorderLayout.SOUTH);
		
	}
	/**
	 * ��ʼ��¼�롢���ð�ť����
	 */
	private void initButtonPanel() {

		inputButton=new JButton("¼��");
		resetButton=new JButton("����");
		inputButton.addActionListener(this);
		resetButton.addActionListener(this);
		ButtonPanel=new JPanel();
		ButtonPanel.setBackground(new Color(216,243,231));
		ButtonPanel.add(inputButton);
		ButtonPanel.add(resetButton);	
	}
	/**
	 * ��ʼ����Ϣ����
	 */
	private void initMessPanel() {
		
		JLabel numberLabel =new JLabel("ѧ��:",JLabel.CENTER);
		numberField=new JTextField(5);
		Box numberBox=Box.createHorizontalBox();//���ˮƽbox,����һ����������ʾ������� Box
		numberBox.add(numberLabel);
		numberBox.add(numberField);
		JLabel nameLabel =new JLabel("����:",JLabel.CENTER);
		nameField=new JTextField(5);
		Box nameBox=Box.createHorizontalBox();//���ˮƽbox
		nameBox.add(nameLabel);
		nameBox.add(nameField);
		JLabel sexLabel =new JLabel("�Ա�:",JLabel.CENTER);
		maleRButton=new JRadioButton("��",true);
		femaleRButton=new JRadioButton("Ů",false);
		buttongroup=new ButtonGroup();
		buttongroup.add(femaleRButton);
		buttongroup.add(maleRButton);
		Box sexBox=Box.createHorizontalBox();
		sexBox.add(sexLabel);
		sexBox.add(maleRButton);
		sexBox.add(femaleRButton);
		JLabel majorLabel=new JLabel("רҵ:",JLabel.CENTER);
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
		JLabel gradeLabel=new JLabel("�꼶:",JLabel.CENTER);
		gradeField=new JTextField(5);
		Box gradeBox=Box.createHorizontalBox();
		gradeBox.add(gradeLabel);
		gradeBox.add(gradeField);
		JLabel birthdayLabel=new JLabel("����:",JLabel.CENTER);
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
	/**
	 *�¼��Ĵ���
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==inputButton)
		{
			String number=numberField.getText();//��ȡ�����ѧ����Ϣ
			if(number.length()>0)
			{
				try//���ļ��ж�ȡ��Ϣ
				{
					fileinputstream=new FileInputStream(systemFile);
					objectinputstream=new ObjectInputStream(fileinputstream);
					informationtable=(HashMap<String, Student>) objectinputstream.readObject();
					fileinputstream.close();
					objectinputstream.close();
					
				}catch(Exception ee){
					
				}
				if(informationtable.containsKey(number))
				{
					String waring="����������Ϣ�Ѵ��ڣ��뵽�޸�ҳ���޸�";
					JOptionPane.showMessageDialog(this, waring,"����",JOptionPane.WARNING_MESSAGE);
				}
				else
				{
					String m="ȷ��¼���ѧ����Ϣ��";
					int ok=JOptionPane.showConfirmDialog(this, m, "ȷ��", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
					if(ok==JOptionPane.YES_OPTION)
					{
						String name=nameField.getText();
						String grade=gradeField.getText();
						String major=(String) majorComBox.getSelectedItem();
						String birth=birthdayField.getText();
						String sex=null;
						if(maleRButton.isSelected())
							sex=maleRButton.getText();
						else
							sex=femaleRButton.getText();
						student=new Student();
						student.setBirthday(birth);
						student.setGrade(grade);
						student.setMajor(major);
						student.setName(name);
						student.setSex(sex);
						student.setImagePic(imgagePic);
						student.setNumber(number);
						try
						{
							fileoutputstream=new FileOutputStream(systemFile);
							objectoutputstream=new ObjectOutputStream(fileoutputstream);
							informationtable.put(number, student);
							objectoutputstream.writeObject(informationtable);
							objectoutputstream.close();
							fileoutputstream.close();
							clearMessage();
						
						}catch(Exception eee)
						{
							
						}
					}
				}
			}
			else
			{
				String waring="����Ҫ����ѧ�ţ�";
				JOptionPane.showMessageDialog(this, waring,"����",JOptionPane.WARNING_MESSAGE);
			}
			
		}
		else if(e.getSource()==picButton){
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
			
			
		}
		
	}
	/**
	 * ����ʾ����Ϣ���
	 */
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

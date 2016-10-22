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
	private StudentPicture studentpicture;//学生图像
	private HashMap<String,Student>informationtable;
	private JTextField numberField,nameField,gradeField,birthdayField;
	private JButton picButton;//选择照片按钮
	private JLabel promotLabel;//提示信息
	private JComboBox<String>majorComBox;//专业列表框
	private JRadioButton maleRButton,femaleRButton;//单选按钮，选择男或者女
	private ButtonGroup buttongroup=null;
	private JButton inputButton,resetButton;//输入按钮，重置按钮
	private FileInputStream fileinputstream=null;//文件输入流对象
	private ObjectInputStream objectinputstream=null;//对象输入流对象
	private FileOutputStream fileoutputstream=null;//文件输出流对象
	private ObjectOutputStream objectoutputstream=null;//对象输出流对象
	private File systemFile,imgagePic;
	private JPanel ButtonPanel;//录入和重置按钮的容器
	private JPanel messPanel,picPanel;//基本信息和照片的容器
	public Inputinformation(File file) {
		systemFile=file;
		studentpicture=new StudentPicture();
		informationtable=new HashMap<String,Student>();
		promotLabel=new JLabel("请输入以下信息:",JLabel.LEFT);
		promotLabel.setFont(new Font("宋体",Font.BOLD,13));
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
	 * 初始化照片部分的界面
	 */
	private void initPicPanel() {
		JLabel picLabel=new JLabel("照片:",JLabel.LEFT);
		picButton=new JButton("选择照片");
		picButton.addActionListener(this);
		picPanel=new JPanel();
		picPanel.setLayout(new BorderLayout());
		picPanel.add(picLabel,BorderLayout.NORTH);
		picPanel.add(studentpicture, BorderLayout.CENTER);
		picPanel.add(picButton, BorderLayout.SOUTH);
		
	}
	/**
	 * 初始化录入、重置按钮界面
	 */
	private void initButtonPanel() {

		inputButton=new JButton("录入");
		resetButton=new JButton("重置");
		inputButton.addActionListener(this);
		resetButton.addActionListener(this);
		ButtonPanel=new JPanel();
		ButtonPanel.setBackground(new Color(216,243,231));
		ButtonPanel.add(inputButton);
		ButtonPanel.add(resetButton);	
	}
	/**
	 * 初始化信息界面
	 */
	private void initMessPanel() {
		
		JLabel numberLabel =new JLabel("学号:",JLabel.CENTER);
		numberField=new JTextField(5);
		Box numberBox=Box.createHorizontalBox();//添加水平box,创建一个从左到右显示其组件的 Box
		numberBox.add(numberLabel);
		numberBox.add(numberField);
		JLabel nameLabel =new JLabel("姓名:",JLabel.CENTER);
		nameField=new JTextField(5);
		Box nameBox=Box.createHorizontalBox();//添加水平box
		nameBox.add(nameLabel);
		nameBox.add(nameField);
		JLabel sexLabel =new JLabel("性别:",JLabel.CENTER);
		maleRButton=new JRadioButton("男",true);
		femaleRButton=new JRadioButton("女",false);
		buttongroup=new ButtonGroup();
		buttongroup.add(femaleRButton);
		buttongroup.add(maleRButton);
		Box sexBox=Box.createHorizontalBox();
		sexBox.add(sexLabel);
		sexBox.add(maleRButton);
		sexBox.add(femaleRButton);
		JLabel majorLabel=new JLabel("专业:",JLabel.CENTER);
		majorComBox=new JComboBox<String>();
		try{
			//从文件中读入专业名称，加入到组合框中
			FileReader filereader=new FileReader("专业.txt");
			BufferedReader reader=new BufferedReader(filereader);
			String s=null;
			while((s=reader.readLine())!=null)
			{
				majorComBox.addItem(s);
			}
			filereader.close();
			reader.close();
		}catch(IOException e){
			majorComBox.addItem("数学");
			majorComBox.addItem("计算机科学与技术");
		}
		Box majorBox=Box.createHorizontalBox();
		majorBox.add(majorLabel);
		majorBox.add(majorComBox);
		JLabel gradeLabel=new JLabel("年级:",JLabel.CENTER);
		gradeField=new JTextField(5);
		Box gradeBox=Box.createHorizontalBox();
		gradeBox.add(gradeLabel);
		gradeBox.add(gradeField);
		JLabel birthdayLabel=new JLabel("出生:",JLabel.CENTER);
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
	 *事件的处理
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==inputButton)
		{
			String number=numberField.getText();//读取输入的学号信息
			if(number.length()>0)
			{
				try//从文件中读取信息
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
					String waring="该生基本信息已存在，请到修改页面修改";
					JOptionPane.showMessageDialog(this, waring,"警告",JOptionPane.WARNING_MESSAGE);
				}
				else
				{
					String m="确定录入该学生信息？";
					int ok=JOptionPane.showConfirmDialog(this, m, "确认", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
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
				String waring="必须要输入学号！";
				JOptionPane.showMessageDialog(this, waring,"警告",JOptionPane.WARNING_MESSAGE);
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
				picButton.setText("重新选择");
				imgagePic=choice;
				studentpicture.setImage(imgagePic);
				studentpicture.repaint();//显示照片
				
			}
				
		}
		else if(e.getSource()==resetButton)
		{
			clearMessage();
			
			
		}
		
	}
	/**
	 * 将显示的信息清空
	 */
	public void clearMessage() {
		numberField.setText(null);
		nameField.setText(null);
		gradeField.setText(null);
		birthdayField.setText(null);
		picButton.setText("选择照片");
		imgagePic=null;
		studentpicture.setImage(imgagePic);
		studentpicture.repaint();
		
	}
	
		

}

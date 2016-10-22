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
	private StudentPicture studentpicture;//学生图像
	private HashMap<String,Student>informationtable=null;
	private JTextField numberField,nameField,gradeField,birthdayField;
	private JButton picButton;//选择照片按钮
	private JLabel promotLabel;//提示信息
	private JComboBox majorComBox;//专业列表框
	private JRadioButton maleRButton,femaleRButton;//单选按钮，选择男或者女
	private ButtonGroup buttongroup=null;
	private JButton beginmodifyButton,modifyButton,resetButton;//输入按钮，重置按钮
	private FileInputStream fileinputstream=null;//文件输入流对象
	private ObjectInputStream objectinputstream=null;//对象输入流对象
	private FileOutputStream fileoutputstream=null;//文件输出流对象
	private ObjectOutputStream objectoutputstream=null;//对象输出流对象
	private File systemFile,imgagePic;
	private JPanel messPanel;//显示基本信息的容器

	/**
	 * 构造函数
	 */
	public Modifyinformation(File file) {
	    systemFile=file;
	    studentpicture=new StudentPicture();
	    informationtable=new HashMap<String,Student>();
	    initMessPanel();
	    picButton=new JButton("选择照片");
	    picButton.addActionListener(this);
	    JPanel picPanel=new JPanel();
	    picPanel.add(picButton);
	    modifyButton=new JButton("修改");
		resetButton=new JButton("重置");
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
		JLabel numberLabel =new JLabel("(旧)学号:",JLabel.CENTER);
		numberField=new JTextField(5);
		beginmodifyButton=new JButton("开始修改");
		beginmodifyButton.addActionListener(this);
		numberField.addActionListener(this);
		Box numberBox=Box.createHorizontalBox();//添加水平box,创建一个从左到右显示其组件的 Box
		numberBox.add(numberLabel);
		numberBox.add(numberField);
		numberBox.add(beginmodifyButton);
		
		JLabel nameLabel =new JLabel("(新)姓名:",JLabel.CENTER);
		nameField=new JTextField(5);
		Box nameBox=Box.createHorizontalBox();//添加水平box
		nameBox.add(nameLabel);
		nameBox.add(nameField);
		JLabel sexLabel =new JLabel("(新)性别:",JLabel.CENTER);
		maleRButton=new JRadioButton("男",true);
		femaleRButton=new JRadioButton("女",false);
		buttongroup=new ButtonGroup();
		buttongroup.add(femaleRButton);
		buttongroup.add(maleRButton);
		Box sexBox=Box.createHorizontalBox();
		sexBox.add(sexLabel);
		sexBox.add(maleRButton);
		sexBox.add(femaleRButton);
		JLabel majorLabel=new JLabel("(新)专业:",JLabel.CENTER);
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
		JLabel gradeLabel=new JLabel("(新)年级:",JLabel.CENTER);
		gradeField=new JTextField(5);
		Box gradeBox=Box.createHorizontalBox();
		gradeBox.add(gradeLabel);
		gradeBox.add(gradeField);
		JLabel birthdayLabel=new JLabel("(新)出生:",JLabel.CENTER);
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
				if(informationtable.containsKey(number))//该学生存在
				{
					modifyButton.setEnabled(true);
					picButton.setEnabled(true);
					student=informationtable.get(number);
					nameField.setText(student.getName());
					if(student.getSex().equals("男"))
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
				else{//输入的学号不存在
					modifyButton.setEnabled(false);
					picButton.setEnabled(false);
					String waring="该学号不存在！";
					JOptionPane.showMessageDialog(this, waring,"警告",JOptionPane.WARNING_MESSAGE);
					clearMessage();
				}
				
			}
			else{
				//没有输入学号
				modifyButton.setEnabled(false);
				picButton.setEnabled(false);
				String waring="请输入学号！";
				JOptionPane.showMessageDialog(this, waring,"警告",JOptionPane.WARNING_MESSAGE);
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
				if(informationtable.containsKey(number))//学号存在就修改
				{
					String question="确定修改该生的基本信息么？";
					JOptionPane.showMessageDialog(this, question,"警告",JOptionPane.QUESTION_MESSAGE);
					String m="基本信息将被修改";
					int ok=JOptionPane.showConfirmDialog(this, m, "确认", JOptionPane.YES_NO_OPTION	,JOptionPane.INFORMATION_MESSAGE);
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
				else{//输入的学号不存在
					modifyButton.setEnabled(false);
					picButton.setEnabled(false);
					String waring="没有该学号学生的基本信息！";
					JOptionPane.showMessageDialog(this, waring,"警告",JOptionPane.WARNING_MESSAGE);
					clearMessage();
				}
			}
			else{
				//没有输入学号
				modifyButton.setEnabled(false);
				picButton.setEnabled(false);
				String waring="必须输入学号！";
				JOptionPane.showMessageDialog(this, waring,"警告",JOptionPane.WARNING_MESSAGE);
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
				picButton.setText("重新选择");
				imgagePic=choice;
				studentpicture.setImage(imgagePic);
				studentpicture.repaint();//显示照片
				
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
		picButton.setText("选择照片");
		imgagePic=null;
		studentpicture.setImage(imgagePic);
		studentpicture.repaint();
		
	}

	
}

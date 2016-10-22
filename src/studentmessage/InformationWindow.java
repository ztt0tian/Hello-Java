package studentmessage;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;

import javax.swing.*;

/**
 * 
 * @author asus-wh
 *
 */
/**
 * 主界面的初始化

 */

public class InformationWindow extends JFrame implements ActionListener {
	
	private Inputinformation inputinformation;
	private Modifyinformation modifyinformation;
	private Queryinformation queryinformation;
	private Deleteinformation deleteinformation;
	private ShowAllinformation showallinformatiopn;
	private JMenuBar bar;
	private JMenu fileMenu;
	private JMenuItem input;
	private JMenuItem modify;
	private JMenuItem query;
	private JMenuItem delete;
	private JMenuItem showall;
	private JMenuItem welcome;
	private HashMap<String,Student>informationTable=null;
	private File file=null;
	private CardLayout card=null;
	private JLabel label=null;
	private JPanel pCenter;
	public InformationWindow() {
		informationTable=new HashMap<String ,Student>();
		initFrame();
		setVisible(true);
		setBounds(100,50,380,350);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter(){
			public void windowCloseing(WindowEvent e){
				int n=JOptionPane.showConfirmDialog(null, "确认退出么？","确认对话框",JOptionPane.YES_NO_OPTION);
				if(n==JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
		setResizable(true);
		validate();
		
	}
	/**
	 * 初始化界面的各个组件
	 */
	private void initFrame() {
		input=new JMenuItem("录入");
		modify=new JMenuItem("修改");
		//JMenuItem z=new JMenuItem("hello");
		query=new JMenuItem("查询");
		delete=new JMenuItem("删除");
		showall=new JMenuItem("显示所有学生信息");
		welcome=new JMenuItem("欢迎界面");
		
		bar=new JMenuBar();
		fileMenu=new JMenu("菜单选项");
		fileMenu.add(input);
		fileMenu.add(modify);
		fileMenu.add(query);
		fileMenu.add(delete);
		fileMenu.add(showall);
		fileMenu.add(welcome);
		bar.add(fileMenu);
		this.setJMenuBar(bar);
		label=new JLabel("学籍管理系统",JLabel.CENTER);
		label.setIcon(new ImageIcon("welcome.jpg"));
		label.setFont(new Font("隶书",Font.BOLD,36));
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setForeground(Color.red);
		informationTable=new HashMap<String,Student>();
		input.addActionListener(this);
		modify.addActionListener(this);
		query.addActionListener(this);
		delete.addActionListener(this);
		welcome.addActionListener(this);
		showall.addActionListener(this);
		card=new CardLayout();
		pCenter=new JPanel();
		pCenter.setLayout(card);
		file=new File("基本信息.txt");
		if(!file.exists()){
			try{
				FileOutputStream out =new FileOutputStream(file);
				ObjectOutputStream oop=new ObjectOutputStream(out);
				oop.writeObject(informationTable);
				oop.close();
				out.close();
			}catch(IOException e)
			{
				System.out.println("文件错误");
			}
		}
		inputinformation=new Inputinformation(file);
		modifyinformation=new Modifyinformation(file);
		queryinformation=new Queryinformation(file);
		deleteinformation=new Deleteinformation(file);
		showallinformatiopn=new ShowAllinformation(file);
	
		pCenter.add("欢迎界面",label);
		pCenter.add("录入界面",inputinformation);
		pCenter.add("修改界面",modifyinformation);
		pCenter.add("查询界面",queryinformation);
		pCenter.add("删除界面",deleteinformation);
		//pCenter.add("显示所有学生信息界面",showallinformatiopn);
		add(pCenter,BorderLayout.CENTER);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==input)
		{
			inputinformation.clearMessage();
			card.show(pCenter, "录入界面");
		}
		else if(e.getSource()==modify)
		{
			modifyinformation.clearMessage();
			card.show(pCenter, "修改界面");
		}
		else if(e.getSource()==query)
		{
			queryinformation.clearMessage();
			card.show(pCenter, "查询界面");
		}
		else if(e.getSource()==delete)
		{
			deleteinformation.clearMessage();
			card.show(pCenter, "删除界面");
			
		}
		else if(e.getSource()==showall)
		{
			showallinformatiopn.showAll();
		}
		
		
	}
	/**
	 * 启动系统
	 */
	public static void main(String args[]){
		new InformationWindow();
	}
	

}

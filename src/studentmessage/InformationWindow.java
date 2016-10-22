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
 * ������ĳ�ʼ��

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
				int n=JOptionPane.showConfirmDialog(null, "ȷ���˳�ô��","ȷ�϶Ի���",JOptionPane.YES_NO_OPTION);
				if(n==JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
		setResizable(true);
		validate();
		
	}
	/**
	 * ��ʼ������ĸ������
	 */
	private void initFrame() {
		input=new JMenuItem("¼��");
		modify=new JMenuItem("�޸�");
		//JMenuItem z=new JMenuItem("hello");
		query=new JMenuItem("��ѯ");
		delete=new JMenuItem("ɾ��");
		showall=new JMenuItem("��ʾ����ѧ����Ϣ");
		welcome=new JMenuItem("��ӭ����");
		
		bar=new JMenuBar();
		fileMenu=new JMenu("�˵�ѡ��");
		fileMenu.add(input);
		fileMenu.add(modify);
		fileMenu.add(query);
		fileMenu.add(delete);
		fileMenu.add(showall);
		fileMenu.add(welcome);
		bar.add(fileMenu);
		this.setJMenuBar(bar);
		label=new JLabel("ѧ������ϵͳ",JLabel.CENTER);
		label.setIcon(new ImageIcon("welcome.jpg"));
		label.setFont(new Font("����",Font.BOLD,36));
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
		file=new File("������Ϣ.txt");
		if(!file.exists()){
			try{
				FileOutputStream out =new FileOutputStream(file);
				ObjectOutputStream oop=new ObjectOutputStream(out);
				oop.writeObject(informationTable);
				oop.close();
				out.close();
			}catch(IOException e)
			{
				System.out.println("�ļ�����");
			}
		}
		inputinformation=new Inputinformation(file);
		modifyinformation=new Modifyinformation(file);
		queryinformation=new Queryinformation(file);
		deleteinformation=new Deleteinformation(file);
		showallinformatiopn=new ShowAllinformation(file);
	
		pCenter.add("��ӭ����",label);
		pCenter.add("¼�����",inputinformation);
		pCenter.add("�޸Ľ���",modifyinformation);
		pCenter.add("��ѯ����",queryinformation);
		pCenter.add("ɾ������",deleteinformation);
		//pCenter.add("��ʾ����ѧ����Ϣ����",showallinformatiopn);
		add(pCenter,BorderLayout.CENTER);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==input)
		{
			inputinformation.clearMessage();
			card.show(pCenter, "¼�����");
		}
		else if(e.getSource()==modify)
		{
			modifyinformation.clearMessage();
			card.show(pCenter, "�޸Ľ���");
		}
		else if(e.getSource()==query)
		{
			queryinformation.clearMessage();
			card.show(pCenter, "��ѯ����");
		}
		else if(e.getSource()==delete)
		{
			deleteinformation.clearMessage();
			card.show(pCenter, "ɾ������");
			
		}
		else if(e.getSource()==showall)
		{
			showallinformatiopn.showAll();
		}
		
		
	}
	/**
	 * ����ϵͳ
	 */
	public static void main(String args[]){
		new InformationWindow();
	}
	

}

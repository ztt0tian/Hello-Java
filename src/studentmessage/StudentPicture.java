package studentmessage;

import java.awt.*;
import java.io.*;

import javax.swing.*;

public class StudentPicture extends JPanel{
	private File imageFile;//���ͼ���ļ�������
	private Toolkit tool;//���𴴽�Image����
	public StudentPicture() {
		tool=getToolkit();
		setBorder(BorderFactory.createLineBorder(Color.BLACK));//����һ�������߽�
		setBorder(BorderFactory.createLoweredBevelBorder());//���ñ߿���ȥ��Ч��
	}
	/**
	 * ����Image����
	 */
	public void setImage(File imagefile){
		this.imageFile=imagefile;
		repaint();
		
	}
	/**
	 * ��ʾ��Ƭ
	 */
	public void paint(Graphics g)
	{
		//paintComponent���Ǳ�����������Լ������Լ�����ķ����ˡ����ֻ��Ϊ�˸ı䱾����������е������
		//ֻ��Ҫ��дpaintComponent�����Ϳ����ˣ������Ҫ���������е�ԭ������ͱ����˵���super.paintComponent(g)
		super.paintComponent(g);
		int w=getBounds().width;
		int h=getBounds().height;
		if(imageFile!=null)
		{
			Image image=tool.getImage(imageFile.getAbsolutePath());
			//System.out.println(imageFile.getAbsolutePath());
			g.drawImage(image, 0, 0,w,h, this);//����ͼ��
		}
		else
			g.drawString("û��ѡ����Ƭͼ��", 20, 30);
	}

}

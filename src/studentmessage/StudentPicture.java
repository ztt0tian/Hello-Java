package studentmessage;

import java.awt.*;
import java.io.*;

import javax.swing.*;

public class StudentPicture extends JPanel{
	private File imageFile;//存放图像文件的引用
	private Toolkit tool;//负责创建Image对象
	public StudentPicture() {
		tool=getToolkit();
		setBorder(BorderFactory.createLineBorder(Color.BLACK));//建立一个线条边界
		setBorder(BorderFactory.createLoweredBevelBorder());//设置边框凹下去的效果
	}
	/**
	 * 设置Image对象
	 */
	public void setImage(File imagefile){
		this.imageFile=imagefile;
		repaint();
		
	}
	/**
	 * 显示照片
	 */
	public void paint(Graphics g)
	{
		//paintComponent就是本身这个容器自己画出自己组件的方法了。如果只是为了改变本身这个容器中的组件，
		//只需要改写paintComponent方法就可以了，如果还要保留容器中的原本组件就别忘了调用super.paintComponent(g)
		super.paintComponent(g);
		int w=getBounds().width;
		int h=getBounds().height;
		if(imageFile!=null)
		{
			Image image=tool.getImage(imageFile.getAbsolutePath());
			//System.out.println(imageFile.getAbsolutePath());
			g.drawImage(image, 0, 0,w,h, this);//绘制图像
		}
		else
			g.drawString("没有选择照片图像", 20, 30);
	}

}

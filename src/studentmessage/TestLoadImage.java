package studentmessage;

	import javax.swing.JFrame;
	import java.awt.*;
	import java.awt.event.*;

	public class TestLoadImage{
	    public static void main(String[] args){
	        JFrame f=new JFrame();
	        MyCanvas mc=new MyCanvas();
	        Image image=Toolkit.getDefaultToolkit().getImage("a.PNG");
	        mc.setImage(image);
	        f.add(mc);
	        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        f.setSize(400,550);
	        f.setVisible(true);
	    }
	}

	class MyCanvas extends Canvas {
	    private Image in;
	    private int image_width;
	    private int image_height;
	    
	    public void setImage(Image in){
	        this.in=in;
	    }
	    
	    public void paint(Graphics g){
	        g.drawImage(in,0,0,this.getWidth(),this.getHeight(),this);
	    }    
	}


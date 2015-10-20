import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class StartPhoto extends JPanel{
	
	CurrentWorkingDirectory dir = new CurrentWorkingDirectory();
	String di = dir.path();
	String d = di.replaceAll("/Naval Conquest","");
	int w,h;
	File AH = new File(d + "/res/1.png");    
	Image AH1 = null;
	
	public StartPhoto(int w1, int h1){
		
		try {
			AH1 = ImageIO.read(AH);
		} catch (IOException e) {
			System.err.println("Could not find image" + d);
		}
		w=w1;
		h=h1;
	}
	
	public void paintComponent(Graphics g){
		g.drawImage(AH1, 0, 0, w/3,h/3,null);
	}
}

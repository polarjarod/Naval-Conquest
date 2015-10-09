import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class PictureLib {
	CurrentWorkingDirectory dir = new CurrentWorkingDirectory();
	String di = dir.path();
	String d = di.replaceAll("/Sea of Warships (RTS)","");   //L to R dig 1 is Side, dig 2 is ship/Plane, 3+4 identify, 5 is orientation
	
	
	File valBomberR = new File(d + "/res/1103R.png");    
	Image valBomberRi = null;
	File valBomberL = new File(d + "/res/1103L.png");    
	Image valBomberLi = null;
	File  danBomberR = new File(d + "/res/1104R.png");    
	Image danBomberRi = null;
	File danBomberL = new File(d + "/res/1104L.png");    
	Image danBomberLi = null;
	File lexingtonR = new File(d + "/res/0002R.png");    
	Image lexingtonRi = null;
	File lexingtonL = new File(d + "/res/0002L.png");    
	Image lexingtonLi = null;
	File akagiR = new File(d + "/res/0001R.png");    
	Image akagiRi = null;
	File akagiL = new File(d + "/res/0001L.png");    
	Image akagiLi = null;
	
	public PictureLib(){
		try {
			lexingtonRi = ImageIO.read(lexingtonR);
		} catch (IOException e) {
			System.err.println("Could not find image" + d);
		}
		try {
			lexingtonLi = ImageIO.read(lexingtonL);
		} catch (IOException e) {
			System.err.println("Could not find image" + d);
		}
		try {
			akagiRi = ImageIO.read(akagiR);
		} catch (IOException e) {
			System.err.println("Could not find image" + d);
		}
		try {
			akagiLi = ImageIO.read(akagiL);
		} catch (IOException e) {
			System.err.println("Could not find image" + d);
		}
		try {
			valBomberRi = ImageIO.read(valBomberR);
		} catch (IOException e) {
			System.err.println("Could not find image" + d);
		}
		try {
			valBomberLi = ImageIO.read(valBomberL);
		} catch (IOException e) {
			System.err.println("Could not find image" + d);
		}
		try {
			danBomberRi = ImageIO.read(danBomberR);
		} catch (IOException e) {
			System.err.println("Could not find image" + d);
		}
		try {
			danBomberLi = ImageIO.read(danBomberL);
		} catch (IOException e) {
			System.err.println("Could not find image" + d);
		}
	}
	public Image lexingtonRi(){
		return lexingtonRi;
	}
	public Image lexingtonLi(){
		return lexingtonLi;
	}
	public Image akagiRi(){
		return akagiRi;
	}
	public Image akagiLi(){
		return akagiRi;
	}
	public Image valBomberRi(){
		return valBomberRi;
	}
	public Image valBomberLi(){
		return valBomberLi;
	}
	public Image danBomberRi(){
		return danBomberRi;
	}
	public Image danBomberLi(){
		return danBomberLi;
	}
}

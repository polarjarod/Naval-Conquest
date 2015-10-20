import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class PictureLib {
	CurrentWorkingDirectory dir = new CurrentWorkingDirectory();
	String di = dir.path();
	String d = di.replaceAll("/Naval Conquest","");   //L to R dig 1 is Side, dig 2 is ship/Plane, 3+4 identify, 5 is orientation
	
	
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
	
	File cargoL = new File(d + "/res/0003L.png");    
	Image cargoLi = null;
	File cargoR = new File(d + "/res/0003R.png");    
	Image cargoRi = null;
	
	File kongoL = new File(d + "/res/0004L.png");    
	Image kongoLi = null;
	File kongoR = new File(d + "/res/0004R.png");    
	Image kongoRi = null;
	File newyorkL = new File(d + "/res/0104L.png");    
	Image newyorkLi = null;
	File newyorkR = new File(d + "/res/0104R.png");    
	Image newyorkRi = null;
	
	File mL = new File(d + "/res/0005L.png");    
	Image mLi = null;
	File mR = new File(d + "/res/0005R.png");    
	Image mRi = null;
	File nhL = new File(d + "/res/0105L.png");    
	Image nhLi = null;
	File nhR = new File(d + "/res/0105R.png");    
	Image nhRi = null;
	
	public PictureLib(){
		try {
			cargoRi = ImageIO.read(cargoR);
		} catch (IOException e) {
			System.err.println("Could not find image" + d);
		}
		try {
			kongoRi = ImageIO.read(kongoR);
		} catch (IOException e) {
			System.err.println("Could not find image" + d);
		}
		try {
			newyorkRi = ImageIO.read(newyorkR);
		} catch (IOException e) {
			System.err.println("Could not find image" + d);
		}
		try {
			cargoLi = ImageIO.read(cargoL);
		} catch (IOException e) {
			System.err.println("Could not find image" + d);
		}
		try {
			kongoLi = ImageIO.read(kongoL);
		} catch (IOException e) {
			System.err.println("Could not find image" + d);
		}
		try {
			newyorkLi = ImageIO.read(newyorkL);
		} catch (IOException e) {
			System.err.println("Could not find image" + d);
		}
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
		try {
			mRi = ImageIO.read(mR);
		} catch (IOException e) {
			System.err.println("Could not find image" + d);
		}
		try {
			mLi = ImageIO.read(mL);
		} catch (IOException e) {
			System.err.println("Could not find image" + d);
		}
		try {
			nhLi = ImageIO.read(nhL);
		} catch (IOException e) {
			System.err.println("Could not find image" + d);
		}
		try {
			nhRi = ImageIO.read(nhR);
		} catch (IOException e) {
			System.err.println("Could not find image" + d);
		}
	}
	public Image mRi(){
		return mRi;
	}
	public Image mLi(){
		return mLi;
	}
	public Image nhRi(){
		return nhRi;
	}
	public Image nhLi(){
		return nhLi;
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
	public Image cargoRi(){
		return cargoRi;
	}
	public Image cargoLi(){
		return cargoLi;
	}
	public Image kongoRi(){
		return kongoRi;
	}
	public Image kongoLi(){
		return kongoLi;
	}
	public Image newyorkRi(){
		return newyorkRi;
	}
	public Image newyorkLi(){
		return newyorkLi;
	}
}

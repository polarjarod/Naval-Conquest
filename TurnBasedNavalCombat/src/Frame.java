
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class Frame extends JFrame{
	
private int encounter;
private boolean turn,next;
private int p2fleet,p2fleetMax,p1fleet,p1fleetMax;
private boolean wantW, wantD, wantS, wantA, plane, fire, rangeDisp;
private int X, Y;
private int w, h;
public Frame(GameBoard can){
	super();
	wantW = false;
	wantA = false;
	wantS = false;
	wantD = false;
	fire = false;
	rangeDisp = false;
	initialize(can);
	this.addKeyListener(new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_PERIOD:
				rangeDisp = !rangeDisp;
				break;
			case KeyEvent.VK_COMMA:
				fire = true;
				break;
			case KeyEvent.VK_UP:
				wantW = false;
				wantA = false;
				wantS = false;
				wantD = false;
				fire = false;
				if(turn){	
				if(p2fleet<p2fleetMax)
					p2fleet++;
					else
					p2fleet=0;	}
				else{
				if(p1fleet<p1fleetMax)
					p1fleet++;
					else
					p1fleet=0; }
					break;
			case KeyEvent.VK_RIGHT:
				wantW = false;
				wantA = false;
				wantS = false;
				wantD = false;
				fire = false;
				plane=false;
				if(turn){	
				if(p1fleet<p1fleetMax)
					p1fleet++;
					else
					p1fleet=0; }
				else{
					if(p2fleet<p2fleetMax)
						p2fleet++;
						else
						p2fleet=0;
				}
					break;
			case KeyEvent.VK_DOWN:
				wantW = false;
				wantA = false;
				wantS = false;
				wantD = false;
				fire = false;
				if(turn){	
				if(p2fleet>0)
					p2fleet--;
					else
					p2fleet=p2fleetMax;	}
				else{
					if(p1fleet>0)
						p1fleet--;
						else
						p1fleet=p1fleetMax;
				}
					break;
			case KeyEvent.VK_LEFT:
				wantW = false;
				wantA = false;
				wantS = false;
				wantD = false;
				plane=false;
				fire = false;
				if(turn){	
				if(p1fleet>0)
					p1fleet--;
					else
					p1fleet=p1fleetMax;	}
				else{
					if(p2fleet>0)
						p2fleet--;
						else
						p2fleet=p2fleetMax;
				}
					break;
			case KeyEvent.VK_W:
				if(!wantD && !wantA && !wantS)
						wantW = true;
				fire = false;
				break;
			case KeyEvent.VK_S:
				if(!wantD && !wantA && !wantW)
					wantS = true;
				fire = false;
				break;
			case KeyEvent.VK_A:
				if(!wantD && !wantW && !wantS)
					wantA = true;
				fire = false;
				break;
			case KeyEvent.VK_D:
				if(!wantW && !wantA && !wantS)
					wantD = true;
				fire = false;
				break;
			case KeyEvent.VK_F:
				fire = false;
				plane = false;
				next=!next;
				break;
			case KeyEvent.VK_1:
				plane=true;
			}
		
		} // end keyPressed
	});
	this.addMouseListener(new MouseAdapter(){
		public void mousePressed(MouseEvent e) {
			Point platz = e.getPoint();
			X = (int) (platz.getX()/(w/50));
			Y = (int) (platz.getY()/(h/50));
		}
	});
}
	public void collide(){
		p1fleet = 0;
		p2fleet = 0;
	}
	public boolean rangeDisp(){
		return rangeDisp;
	}
	
	public boolean planeLaunch(){
		return plane;
	}
	public boolean wantFire(){
		return fire;
	}
	public void setFire(boolean f){
		fire = f;
	}
	public void setPlane(){
		plane=false;
	}
	public int cord1(){
		return X;
	}
	public int cord2(){
		return Y;
	}
	public void setTurn(boolean t){
		turn = t;
	}
	public void setMax(int m, int t){
		p1fleetMax=m;
		p2fleetMax=t;
	}
	public int attacker(){
		if(turn)
		return p1fleet;
		else
		return p2fleet;
		
	}
	public int defender(){
		if(turn)
		return p2fleet;	
		else
		return p1fleet;	
	}
	public int getWant(){
		if(wantW)
			return 1;
		if(wantA)
			return 2;
		if(wantS)
			return 3;
		if(wantD)
			return 4;
		return 0;
	}
	public void resWant(){
		wantW = false;
		wantA = false;
		wantS = false;
		wantD = false;
	}
	
	public int getEncounter(){
		return encounter;
	}
	public boolean turnMove(){
		return next;
	}
	public void initialize(GameBoard can) {
		
		Dimension size;
		
		size = new Dimension(can.getWidth(), can.getHeight());
		w = can.getWidth();
		h=can.getHeight();
		this.setSize(size);
		this.setContentPane(can);
		this.setResizable(true);
		this.setTitle("Naval Battle Command");
		encounter=1;
		can.setEncounter(encounter);
		next=false;
		
			

	}
}


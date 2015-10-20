import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class GameBoard extends JPanel{
	
	private int width,height;
	private int encounter;
	private int attX, attY, defX, defY;
	private boolean fun, rD;
	int shipHP,shipFHP, shipAA, shipArty, shipRange;
	private String shipName;
	private ArrayList<Plane> PP1, PP2;
	private ArrayList<Entity> P1, P2;
	private boolean P1Win, P2Win;
	private PictureLib pic;
	
	
	public GameBoard(int sizeX, int SizeY){
		pic = new PictureLib();
		width = sizeX;
		height = SizeY;
		P1Win = false;
		P2Win = false;
		rD = false;
	}
	public void setRD(boolean r){
		rD = r;
	}
	public void paint(){
		repaint();
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public void setEncounter(int e){
		encounter = e;
	}
	public void setAttack(int x, int y){
		attX = x;
		attY = y;
		fun = true;
	}
	public void shipStats(){
		
	}
	public void setUnit(int x, int y){
		defX = x;
		defY = y;
	}
	public void setStats(Entity e){ 
		shipHP=e.getHP();
		shipFHP=e.regHP();
		shipAA=e.getAA();
		shipArty=e.getGAttack();
		shipName=e.name();
		shipRange = e.gunneryRange();
	}
	public void getPlanes(ArrayList<Plane> P1, ArrayList<Plane> P2){
		PP1 = P1;
		PP2 = P2;
	}
	public void getShips(ArrayList<Entity> P1, ArrayList<Entity> P2){
		this.P1 = P1;
		this.P2 = P2;
	}
	public void P1Win(){
		P1Win = true;
	}
	
	public void P2Win(){
		P2Win = true;
	}
	public void paintComponent(Graphics g) {
		setOpaque(true);
		super.paintComponent(g);
		g.setColor(Color.blue);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.black);
		for(int i = 0; i<51; i++){
			g.drawLine((width/50)*i, 0, (width/50)*i, (height/50)*40);
		}
		for(int o=0; o<41; o++){
			g.drawLine(0, (height/50)*o, width,(height/50)*o);
		}
		g.fillRect(0, (height/50)*40, width, (height/50)*12);
		
			if(fun){
				g.setColor(Color.GREEN);
				switch(encounter){
				case 1 :
					g.drawString("American Shipping Fleet Encounters Japanese Light Attack Squadron", 75, (height/50)*41);
				}
				g.drawRect((width/50)*(attX), (height/50)*(attY), width/50, height/50);
				g.drawString("Ship Name = "+shipName+"\n Ship health = "+shipHP+"/"+shipFHP+"\n Damage = "+shipArty+" AA Power = "+shipAA, 75, (height/50)*43);
				g.setColor(Color.RED);
				g.drawRect((width/50)*(defX), (height/50)*(defY), width/50, height/50);
				
				
				if(rD){
					g.setColor(Color.PINK);
					g.drawRect((width/50)*(attX)-(shipRange*(width/50)),  (height/50)*(attY) - (shipRange*(height/50)), 2*(width/50)*(shipRange+1), 2*(height/50)*(shipRange+1));
					g.setColor(Color.RED);
				}
				
				for(int i = 0; i<P1.size();i++){
					if(P1.get(i).getDirection()){
						
						switch(P1.get(i).Identity()) {
						case 0:
							g.drawImage(pic.lexingtonRi(), ((width/50)*P1.get(i).X())+1, ((height/50) * P1.get(i).Y())+1, width/50-1, height/50-1, null);
							break;
						case 1:
							g.drawImage(pic.cargoRi(), ((width/50)*P1.get(i).X())+1, ((height/50) * P1.get(i).Y())+1, width/50-1, height/50-1, null);
							break;
						case 2:
							g.drawImage(pic.newyorkRi(), ((width/50)*P1.get(i).X())+1, ((height/50) * P1.get(i).Y())+1, width/50-1, height/50-1, null);
							break;
						case 3:
							g.drawImage(pic.nhRi(), ((width/50)*P1.get(i).X())+1, ((height/50) * P1.get(i).Y())+1, width/50-1, height/50-1, null);
							break;
						}
					}
					else{
						switch(P1.get(i).Identity()) {
						case 0:
							g.drawImage(pic.lexingtonLi(), ((width/50)*P1.get(i).X())+1, ((height/50) * P1.get(i).Y())+1, width/50-1, height/50-1, null);
							break;
						case 1:
							g.drawImage(pic.cargoLi(), ((width/50)*P1.get(i).X())+1, ((height/50) * P1.get(i).Y())+1, width/50-1, height/50-1, null);
							break;
						case 2:
							g.drawImage(pic.newyorkLi(), ((width/50)*P1.get(i).X())+1, ((height/50) * P1.get(i).Y())+1, width/50-1, height/50-1, null);
							break;
						case 3:
							g.drawImage(pic.nhLi(), ((width/50)*P1.get(i).X())+1, ((height/50) * P1.get(i).Y())+1, width/50-1, height/50-1, null);
							break;
						}

					}
				}
				for(int o = 0; o<P2.size(); o++){	
					if(P2.get(o).getDirection()){
						
						switch(P2.get(o).Identity()) {
						case 0:
							g.drawImage(pic.akagiRi(), ((width/50)*P2.get(o).X())+1, ((height/50) * P2.get(o).Y())+1, width/50-1, height/50-1, null);
							break;
						case 1:
							g.drawImage(pic.cargoRi(), ((width/50)*P2.get(o).X())+1, ((height/50) * P2.get(o).Y())+1, width/50-1, height/50-1, null);
							break;
						case 2:
							g.drawImage(pic.kongoRi(), ((width/50)*P2.get(o).X())+1, ((height/50) * P2.get(o).Y())+1, width/50-1, height/50-1, null);
							break;
						case 3:
							g.drawImage(pic.mRi(), ((width/50)*P2.get(o).X())+1, ((height/50) * P2.get(o).Y())+1, width/50-1, height/50-1, null);
							break;
						}
					}
					else{
						switch(P2.get(o).Identity()) {
						case 0:
							g.drawImage(pic.akagiLi(), ((width/50)*P2.get(o).X())+1, ((height/50) * P2.get(o).Y())+1, width/50-1, height/50-1, null);
							break;
						case 1:
							g.drawImage(pic.cargoLi(), ((width/50)*P2.get(o).X())+1, ((height/50) * P2.get(o).Y())+1, width/50-1, height/50-1, null);
							break;
						case 2:
							g.drawImage(pic.kongoLi(), ((width/50)*P2.get(o).X())+1, ((height/50) * P2.get(o).Y())+1, width/50-1, height/50-1, null);
							break;
						case 3:
							g.drawImage(pic.mLi(), ((width/50)*P2.get(o).X())+1, ((height/50) * P2.get(o).Y())+1, width/50-1, height/50-1, null);
							break;
						}

					}
				}
				g.setColor(Color.MAGENTA);
				for(int p = 0; p<PP1.size(); p++){
					g.drawRect((PP1.get(p).X())*(width/50), (PP1.get(p).Y())*(height/50), width/50, height/50);
					if(PP1.get(p).getDirection()){
						g.drawImage(pic.danBomberRi(), ((width/50)*PP1.get(p).X())+1, ((height/50) * PP1.get(p).Y())+1, width/50-1, height/50-1, null);
					}
					if(!PP1.get(p).getDirection()){
						g.drawImage(pic.danBomberLi(),((width/50)*PP1.get(p).X())+1, ((height/50) * PP1.get(p).Y())+1, width/50-1, height/50-1, null);
					}
				}
				
				g.setColor(Color.YELLOW);
				for(int p = 0; p<PP2.size(); p++){
					g.drawRect((PP2.get(p).X())*(width/50), (PP2.get(p).Y())*(height/50), width/50, height/50);
					if(PP2.get(p).getDirection()){
						g.drawImage(pic.valBomberRi(), ((width/50)*PP2.get(p).X())+1, ((height/50) * PP2.get(p).Y())+1, width/50-1, height/50-1, null);
					}
					if(!PP2.get(p).getDirection()){
						g.drawImage(pic.valBomberLi(),((width/50)*PP2.get(p).X())+1, ((height/50) * PP2.get(p).Y())+1, width/50-1, height/50-1, null);
					}
				}
				g.setColor(Color.BLACK);
				g.fillRect(0, (height/50)*40, width, (height/50)*12);
				
				
					g.setColor(Color.GREEN);
					switch(encounter){
					case 1 :
						g.drawString("American Shipping Fleet Encounters Japanese Light Attack Squadron", 75, (height/50)*41);
					}
					g.drawRect((width/50)*(attX), (height/50)*(attY), width/50, height/50);
					g.drawString("Ship Name = "+shipName+"\n Ship health = "+shipHP+"/"+shipFHP+"\n Damage = "+shipArty+" AA Power = "+shipAA, 75, (height/50)*43);
					g.setColor(Color.RED);
					g.drawRect((width/50)*(defX), (height/50)*(defY), width/50, height/50);
					
					
				g.setColor(Color.GREEN);
				if(P1Win){
					g.drawString("The United States' Navy has won this Battle", 75, (height/50)*45);
				}
				if(P2Win){
					g.drawString("The Imperial Japanese Navy has won this Battle", 75, (height/50)*45);
				}
				repaint();
			}
		}
	
}

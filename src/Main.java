import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;                                                
import javax.swing.JFrame;
//Jarod, you must change these for each loops to reg. for loops, knew it could not be so easy 

public class Main  {
	
	
	private boolean play = true;
	private boolean turn=true;
	private Frame game;
	private GameBoard can;
	private Arsenal fleets;
	private Combat battle;
	private Entity CV1, CV2;
	private int Doorknocks1=0, Doorknocks2=0;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main enc = new Main();
	}
	public Main() {
		
		start();
	}
	
	public void start() {
		int screenSizeX = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
		int screenSizeY = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight();
		can = new GameBoard(screenSizeX, screenSizeY);
		game = new Frame(can);
		
		run();
	}
	
	public void init() {
		game.setUndecorated(true);
		game.setAlwaysOnTop(true);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setVisible(true);
	
			File curs = new File("src/Cursor.png");
			Image cursor = null;
		
			try{
				cursor = ImageIO.read(curs);
			}catch(Exception e) {System.err.println("Cursor isn't here!!!");}
		
			Cursor Cursor1 = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "blank cursor");
			game.getContentPane().setCursor(Cursor1);
		fleets = new Arsenal(game.getEncounter());
	}
	
	public void run() {
		play = true;
		init();
														while(play){
			if(fleets.getP1Fleet()>-1){
			for(Entity ship : fleets.P1Fleet()){
			 	ship.newTurn();
			 	if(ship.isCV()){
			 		CV1 = ship;
			 		for(int d=1; d<=Doorknocks1; d++){
			 			ship.planeShotDown();
			 		}
			 		Doorknocks1 = 0;
			 	}
			 	if(ship.getHP()<=0)
			 		ship.mFD();
			 	if(fleets.getP1Fleet()<0){
			 		can.P2Win();
					can.paint();
			 	}
			}
			}
			else{
				can.P2Win();
				can.paint();
			}
			for(Plane p : fleets.P1FleetP()){
				p.newTurn();
				if(p.getHP()<=0)
			 		fleets.P1FleetP().remove(p);
			}
			game.resWant();
			game.setTurn(turn);	
			
			for(Entity ship : fleets.P1Fleet()){
				for(int o = 0; o<=fleets.getP2FleetP(); o++){
					if(fleets.P2FleetP().get(o).X()>ship.X()-ship.aaRange() && fleets.P2FleetP().get(o).X()<ship.X()+ship.aaRange())
						if(fleets.P2FleetP().get(o).Y()>ship.Y()-ship.aaRange() && fleets.P2FleetP().get(o).Y()<ship.Y()+ship.aaRange()){
						int h = fleets.P2FleetP().get(o).getHP();
						h-=ship.getAA();
						if(h>0){
							fleets.P2FleetP().get(o).setHP(h);
							
						}
						else{
							fleets.P2FleetP().remove(o);
							Doorknocks2++;
						}
						}
				}
			}
		    while(turn){    //p1Turn--------------------------------------------------------------------------------------------------------------
		    	if(fleets.getP2Fleet()>-1){ 
		    		
		    	}
		    	else{
			  		can.P1Win();
			  		try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			  		start();
			  	}
		    	
		    	try{
		    		Thread.sleep(20);
		    	} catch(Exception e){}
		    	
		   
			   
		  	if(fleets.getP2Fleet()>-1){  	
		    for(int i = 0; i<=fleets.getP2Fleet();i++){
		    	if(fleets.P2Fleet().get(i).donezo())
		    	fleets.P2Fleet().remove(i);	
		    }
		  	}
		  	else{
		  		can.P1Win();
		  		try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		  		start();
		  	}
		    
		    for(int i = 0; i<=fleets.getP2FleetP();i++){
		    	if(fleets.P2FleetP().get(i).donezo())
		    	fleets.P2FleetP().remove(i);	
		    }
		    
		    game.setMax(fleets.getP1Fleet(), fleets.getP2Fleet());
		    can.setAttack(fleets.P1Fleet().get(game.attacker()).X(),fleets.P1Fleet().get(game.attacker()).Y());	
		    can.setStats(fleets.P1Fleet().get(game.attacker()));
		    if(fleets.getP2Fleet()>-1){ 
		    can.setUnit(fleets.P2Fleet().get(game.defender()).X(), fleets.P2Fleet().get(game.defender()).Y());
		    }
		    can.getPlanes(fleets.P1FleetP(), fleets.P2FleetP());
		    can.getShips(fleets.P1Fleet(), fleets.P2Fleet());
			game.setMax(fleets.getP1Fleet(), fleets.getP2Fleet());
			
			if(fleets.P1Fleet().get(game.attacker()).isCV()){
				if(game.planeLaunch()){
					game.setPlane();
					if(fleets.P1Fleet().get(game.attacker()).buyPlane())
					fleets.P1FleetP().add(new Plane(true, game.cord1(), game.cord2(),fleets.P1Fleet().get(game.attacker()).X(),fleets.P1Fleet().get(game.attacker()).Y()));
				}
			}
			 if(fleets.getP1FleetP()>-1)
			 for(Plane p : fleets.P1FleetP()){
			     if(p.bomb()){
			    	 int bombX = p.X();
			    	 int bombY = p.Y();
			    	 for(int i = 0; i<=fleets.getP2Fleet(); i++){
			    		 if(fleets.P2Fleet().get(i).X() >= bombX-1 && fleets.P2Fleet().get(i).X() <= bombX+1)
			    			 if(fleets.P2Fleet().get(i).Y() >= bombY-1 && fleets.P2Fleet().get(i).Y() <= bombY+1){
			    				 int h = fleets.P2Fleet().get(i).getHP();
			    				 h-= ((p.getHP()*2));
			    				 Entity nShip = fleets.P2Fleet().get(i);
			    				 if(h>0){
			    				 nShip.setHP(h);
			    				 fleets.P2Fleet().set(i, nShip);
			    				 }
			    				 else{
			    					 fleets.P2Fleet().remove(i);
			    				 }
			    			 } 
			    	 }
			     p.setMoves(10);
			     p.goBack(CV1.X(), CV1.Y());
			     }
			     for(Entity ship : fleets.P1Fleet()){
			    	 if(ship.isCV())
			    		 CV1 = ship;
			     }
			     if(p.bombed()){
			    	 p.goBack(CV1.X(), CV1.Y());
			     }
				 p.move();
				 if(p.land()){
						p.mFD();
						CV1.planeReturn();
					}
			    }
			
			if(fleets.P1Fleet().get(game.attacker()).canMove()){
				if(game.getWant()==1 ){
					if(fleets.P1Fleet().get(game.attacker()).Y()>0){
						fleets.P1Fleet().get(game.attacker()).moveW();
					game.resWant();
					}
					else	
					game.resWant();
					
				}
				if(game.getWant()==2){
					if(fleets.P1Fleet().get(game.attacker()).X()>0){
						fleets.P1Fleet().get(game.attacker()).moveA();
					game.resWant();
				}
					else	
					game.resWant();
				
				}
				if(game.getWant()==3){
					if(fleets.P1Fleet().get(game.attacker()).Y()<39){
						fleets.P1Fleet().get(game.attacker()).moveS();
					game.resWant();
				}
					else	
					game.resWant();
					
				}
				if(game.getWant()==4){
					if(fleets.P1Fleet().get(game.attacker()).X()<50){
						fleets.P1Fleet().get(game.attacker()).moveD();
					game.resWant();
				}
					else
					game.resWant();
					
				}
			}
			can.paint();
				if(game.turnMove()){ //------------------------------------------------------------------------------------------------
					turn = false;
					game.setTurn(turn);
					System.out.println("T2");
				}
			}
			
			if(fleets.getP2Fleet()>-1){
		    for(Entity ship : fleets.P2Fleet()){
			 	ship.newTurn();
			 	if(ship.isCV()){
			 		CV2 = ship;
			 		for(int d=1; d<=Doorknocks2; d++){
			 			ship.planeShotDown();
			 		}
			 		Doorknocks2 = 0;
			 	}
			 	if(ship.getHP()<=0)
			 		ship.mFD();
			 	if(fleets.getP2Fleet()<0){
			 		can.P1Win();
					can.paint();
			 	}
			}
			}
			else{
				can.P1Win();
				can.paint();
			}
			for(Plane p : fleets.P2FleetP()){
				p.newTurn();
				System.out.println(p.getHP()+ " ");
				if(p.getHP()<=0)
			 		p.mFD();
			}
			
			
		    game.resWant();
		    
		    
		    for(Entity ship : fleets.P2Fleet()){
				for(int o = 0; o<=fleets.getP1FleetP(); o++){
					if(fleets.P1FleetP().get(o).X()>ship.X()-ship.aaRange() && fleets.P1FleetP().get(o).X()<ship.X()+ship.aaRange())
						if(fleets.P1FleetP().get(o).Y()>ship.Y()-ship.aaRange() && fleets.P1FleetP().get(o).Y()<ship.Y()+ship.aaRange()){
						int h = fleets.P1FleetP().get(o).getHP();
						h-=ship.getAA();
						if(h>0){
							fleets.P1FleetP().get(o).setHP(h);
							
						}
						else{
							fleets.P1FleetP().remove(o);
							Doorknocks1++;
						}
						}
				}
			}
			while(!turn){   //p2Turn-----------------------------------------------------------------------------------------------------------------------------
				if(fleets.getP1Fleet()>-1){ 
		    		
		    	}
		    	else{
			  		can.P2Win();
			  		try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			  		start();
			  	}
				try{
					Thread.sleep(20);
				} catch(Exception e){}
				
				
				if(fleets.getP1Fleet()>-1){   
				for(int i = 0; i<fleets.getP1Fleet();i++){
					if(fleets.P1Fleet().get(i).getHP()<0)
						fleets.P1Fleet().get(i).mFD();
				    	if(fleets.P1Fleet().get(i).donezo())
				    	fleets.P1Fleet().remove(i);	
				    }
					}
				else{
				  		can.P2Win();
				  		try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
				  		start();
				  	}
				    
				    for(int i = 0; i<fleets.getP1FleetP();i++){
				    	if(fleets.P1FleetP().get(i).donezo())
				    	fleets.P1FleetP().remove(i);	
				    }
				
				 can.paint();
				game.setMax(fleets.getP1Fleet(), fleets.getP2Fleet());	
				can.setAttack(fleets.P2Fleet().get(game.attacker()).X(),fleets.P2Fleet().get(game.attacker()).Y());	
				can.setStats(fleets.P2Fleet().get(game.attacker()));
				if(fleets.getP1Fleet()>-1){ 
			    can.setUnit(fleets.P1Fleet().get(game.defender()).X(), fleets.P1Fleet().get(game.defender()).Y());	
				}
			    can.getPlanes(fleets.P1FleetP(), fleets.P2FleetP());
			    can.getShips(fleets.P1Fleet(), fleets.P2Fleet());
			game.setMax(fleets.getP1Fleet(), fleets.getP2Fleet());
			
			if(fleets.P2Fleet().get(game.attacker()).isCV()){
				if(game.planeLaunch()){
					game.setPlane();
					if(fleets.P2Fleet().get(game.attacker()).buyPlane())
					fleets.P2FleetP().add(new Plane(false, game.cord1(), game.cord2(),fleets.P2Fleet().get(game.attacker()).X(),fleets.P2Fleet().get(game.attacker()).Y()));
				}
			}
			if(fleets.getP2FleetP()>-1)
			for(Plane p : fleets.P2FleetP()){
				 if(p.bomb()){
			    	 int bombX = p.X();
			    	 int bombY = p.Y();
			    	 for(int i = 0; i<=fleets.getP1Fleet(); i++){
			    		 if(fleets.P1Fleet().get(i).X() >= bombX-1 && fleets.P1Fleet().get(i).X() <= bombX+1)
			    			 if(fleets.P1Fleet().get(i).Y() >= bombY-1 && fleets.P1Fleet().get(i).Y() <= bombY+1){
			    				 int h = fleets.P1Fleet().get(i).getHP();
			    				 h-= ((p.getHP()*2));
			    				 Entity nShip = fleets.P1Fleet().get(i);
			    				 if(h>0){
			    				 nShip.setHP(h);
			    				 fleets.P1Fleet().set(i, nShip);
			    				 }
			    				 else{
			    					 fleets.P1Fleet().remove(i);
			    				 }
			    			 } 
			    	 }
			    p.setMoves(10);
			     p.goBack(CV2.X(), CV2.Y());
			     }
				 for(Entity ship : fleets.P2Fleet()){
			    	 if(ship.isCV())
			    		 CV2 = ship;
			     }
			     if(p.bombed()){
			    	 p.goBack(CV2.X(), CV2.Y());
			     }
				p.move();
				if(p.land()){
					p.mFD();
					CV2.planeReturn();
				}
		    }
			
			if(fleets.P2Fleet().get(game.attacker()).canMove()){
				if(game.getWant()==1){
					if(fleets.P2Fleet().get(game.attacker()).Y()>=0){
						fleets.P2Fleet().get(game.attacker()).moveW();
					game.resWant();
				}
					else
						game.resWant();
				
				}
				if(game.getWant()==2){
					if(fleets.P2Fleet().get(game.attacker()).X()>=0){
						fleets.P2Fleet().get(game.attacker()).moveA();
					game.resWant();
				}
					else
						game.resWant();
					
				}
				if(game.getWant()==3){
					if(fleets.P2Fleet().get(game.attacker()).Y()<=39){
						fleets.P2Fleet().get(game.attacker()).moveS();
					game.resWant();
				}
					else
						game.resWant();
					
				}
				if(game.getWant()==4){
					if(fleets.P2Fleet().get(game.attacker()).X()<50){
						fleets.P2Fleet().get(game.attacker()).moveD();
					game.resWant();
				}
					else
						game.resWant();
					
				}
			}
			can.paint();
				if(!game.turnMove()){
					turn = true;
					game.setTurn(turn);
					System.out.println("T1");
				}
			}
		
			}
		
	}
	

}

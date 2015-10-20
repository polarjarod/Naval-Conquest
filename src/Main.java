import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;                                                
import javax.swing.JFrame;
//Jarod, NETWORKING

public class Main  {
	
	CurrentWorkingDirectory dir = new CurrentWorkingDirectory();
	String di = dir.path();
	String d = di.replaceAll("/Naval Conquest","");
	private boolean play = true;
	private boolean turn=true, team;
	private Frame game;
	private GameBoard can;
	private Arsenal fleets;
	//private Combat battle;
	private Entity CV1, CV2;
	private int Doorknocks1=0, Doorknocks2=0;
	private Serializer s;
    private Socket socket = null;
    private InputStream inStream = null;
    private OutputStream outStream = null;
    private String sidein;
    private Thread readThread, writeThread;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main enc = new Main();
	}
	public Main() {
		
		start();
	}
	

	public void createWriteThread() {
		writeThread = new Thread() {
            public void run() {
                while (socket.isConnected()) {

                    try {
                    	byte[] d = s.serialize(fleets);
                        sleep(500);
                        
                       
                            synchronized (socket) {
                                outStream.write(d);
                                sleep(100);
                            }
                        
                        
                        //System.arraycopy();

                    } catch (IOException i) {
                        i.printStackTrace();
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    }


                }
            }
        };
        writeThread.setPriority(Thread.MAX_PRIORITY);
        writeThread.start();

		
	}
	public void createReadThread() {
		readThread = new Thread() {
            public void run() {
                while (socket.isConnected()) {

                    try {
                        byte[] readBuffer = new byte[1000];
                        int num = inStream.read(readBuffer);

                       if (num > 0) {
                            byte[] arrayBytes = new byte[num];
                            System.arraycopy(readBuffer, 0, arrayBytes, 0, num);
                            fleets = s.deserialize(arrayBytes);
                        }/* else {
                            // notify();
                        }*/
                        ;
                        //System.arraycopy();
                    }catch (SocketException se){
                        System.exit(0);

                    } catch (IOException i) {
                        i.printStackTrace();
                    } catch (ClassNotFoundException e) {
						e.printStackTrace();
					}


                }
            }
        };
        readThread.setPriority(Thread.MAX_PRIORITY);
        readThread.start();
		
	}
	public void start() {
		s = new Serializer();
		int screenSizeX = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
		int screenSizeY = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight();
		can = new GameBoard(screenSizeX, screenSizeY);
		game = new Frame(can);
		StartScreen start = new StartScreen(screenSizeX, screenSizeY);
		while(start.run()){
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		 try {
			 socket = new Socket("192.168.1.135", 3339);
	         System.out.println("Connected");
	         inStream = socket.getInputStream();
	         outStream = socket.getOutputStream();
             byte[] readBuffer = new byte[1000];
             int num = inStream.read(readBuffer);

            if (num > 0) {
                 byte[] arrayBytes = new byte[num];
                 System.arraycopy(readBuffer, 0, arrayBytes, 0, num);
                 sidein= new String(arrayBytes, "UTF-8");
            }
            if(sidein.equals("1"))
            	team = true;
            else if(sidein.equals("2"))
            	team = false;
		 } catch( Exception e){
			 e.printStackTrace();
		 }
		run();
	}
	
	public void init() {
		game.setUndecorated(true);
		game.setAlwaysOnTop(true);
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setVisible(true);
	
			File curs = new File(d + "/res/Cursor.png");
			Image cursor = null;
		
			try{
				cursor = ImageIO.read(curs);
			}catch(Exception e) {System.err.println("Cursor isn't here!!!");}
		
			Cursor Cursor1 = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "blank cursor");
			game.getContentPane().setCursor(Cursor1);
		fleets = new Arsenal(game.getEncounter());
		createReadThread();
		createWriteThread();
	}
	
	public void run() {
		play = true;
		init();
														while(play && team){
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
		    	
		    	
		    	synchronized(socket){
		    		try {
		    			notify();
						readThread.wait();
						
		    		} catch (InterruptedException e) {
						e.printStackTrace();
					}
		    	}



		    	try {
		    	try {
					fleets = s.deserialize(s.serialize(fleets));
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
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
		    	boolean fine = true;
		  for(int i = 0; i<=fleets.getP1Fleet(); i++){
			   for(int o = 0; o <=fleets.getP2Fleet(); o++){
				   if(fine && fleets.P1Fleet().get(i).X() == fleets.P2Fleet().get(o).X() && fleets.P1Fleet().get(i).Y() == fleets.P2Fleet().get(o).Y()){
					  Entity nship1, nship2;
					   int h1,h2;
					 nship1 = fleets.P1Fleet().get(i);
					 nship2 = fleets.P2Fleet().get(o);
					   h1 = fleets.P1Fleet().get(i).getHP()-fleets.P2Fleet().get(o).getHP();
					   h2 = fleets.P2Fleet().get(o).getHP()-fleets.P1Fleet().get(i).getHP();
					   if(h1<=0){
						   fleets.P1Fleet().remove(i);
							   fine = false;
							   
						   game.collide();
						  
					   }
					   else{
						   nship1.setHP(h1);
						   fleets.P1Fleet().set(i,nship1);
					   }
					   if(h2<=0){
						   fleets.P2Fleet().remove(o);
						   game.collide();
						   fine = false;
						   
					   }
					   else{
						   nship2.setHP(h2);
						   fleets.P2Fleet().set(o, nship2);
					   }
				   }
			   }
		  if(!fine){
			  
		  }
		  } 
			   
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
		    can.setRD(game.rangeDisp());
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
			    	 boolean fine2 = true;
			    	 for(int i = 0; i<=fleets.getP2Fleet(); i++){
			    		 if(fine2 && fleets.P2Fleet().get(i).X() >= bombX-1 && fleets.P2Fleet().get(i).X() <= bombX+1)
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
			    					 fine2 = false;
									   
									 game.collide();
			    				 }
			    			 } 
			    	 }
			     p.setMoves(13);
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
	
			if(fleets.P1Fleet().get(game.attacker()).Y()+fleets.P1Fleet().get(game.attacker()).gunneryRange()+1 >= fleets.P2Fleet().get(game.defender()).Y() && fleets.P1Fleet().get(game.attacker()).Y()-fleets.P1Fleet().get(game.attacker()).gunneryRange() <= fleets.P2Fleet().get(game.defender()).Y()){
				if(fleets.P1Fleet().get(game.attacker()).X()+fleets.P1Fleet().get(game.attacker()).gunneryRange()+1 >= fleets.P2Fleet().get(game.defender()).X() && fleets.P1Fleet().get(game.attacker()).X()-fleets.P1Fleet().get(game.attacker()).gunneryRange() <= fleets.P2Fleet().get(game.defender()).X()){
					if(game.wantFire()&&fleets.P1Fleet().get(game.attacker()).Ammo()){
						fleets.P1Fleet().get(game.attacker()).fire();
						int h = fleets.P2Fleet().get(game.defender()).getHP();
						h -= fleets.P1Fleet().get(game.attacker()).getGAttack();
						Entity nShip = fleets.P2Fleet().get(game.defender());
				 
				 if(h>0){
				 nShip.setHP(h);
				 fleets.P2Fleet().set(game.defender(), nShip);
				 }
				 
				 else{
					 fleets.P2Fleet().remove(game.defender());
					  
					 game.collide();
				 }
				 game.setFire(false);
					}
				}	
			}
			
			boolean moveD = true,moveS = true,moveA = true, moveW = true;
			for(int i = 0; i<=fleets.getP1Fleet(); i++){
					int x = fleets.P1Fleet().get(game.attacker()).X();
					int y = fleets.P1Fleet().get(game.attacker()).Y();
				if(x+1 == fleets.P1Fleet().get(i).X() && y == fleets.P1Fleet().get(i).Y())
					moveD = false;
				if(x-1 == fleets.P1Fleet().get(i).X() && y == fleets.P1Fleet().get(i).Y())
					moveA = false;
				if(y+1 == fleets.P1Fleet().get(i).Y() && x == fleets.P1Fleet().get(i).X())
					moveS = false;
				if(y-1 == fleets.P1Fleet().get(i).Y() && x == fleets.P1Fleet().get(i).X())
					moveW = false;							
			}
			 
			if(fleets.P1Fleet().get(game.attacker()).canMove() && moveW || moveA || moveS || moveD){
				if(game.getWant()==1 && moveW){
					if(fleets.P1Fleet().get(game.attacker()).Y()>0){
						fleets.P1Fleet().get(game.attacker()).moveW();
					game.resWant();
					}
					else
					game.resWant();
					
				}
				if(game.getWant()==2 && moveA){
					if(fleets.P1Fleet().get(game.attacker()).X()>0){
						fleets.P1Fleet().get(game.attacker()).moveA();
					game.resWant();
				}
					else	
					game.resWant();
				
				}
				if(game.getWant()==3 && moveS){
					if(fleets.P1Fleet().get(game.attacker()).Y()<39){
						fleets.P1Fleet().get(game.attacker()).moveS();
					game.resWant();
				}
					else	
					game.resWant();
					
				}
				if(game.getWant()==4 && moveD){
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
				
				synchronized(socket){
		    		try {
		    			notify();
						writeThread.wait();
						
		    		} catch (InterruptedException e) {
						e.printStackTrace();
					}
		    	}
				
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
				can.setRD(game.rangeDisp());
				if(fleets.getP1Fleet()>-1){ 
			    can.setUnit(fleets.P1Fleet().get(game.defender()).X(), fleets.P1Fleet().get(game.defender()).Y());	
				}
			    can.getPlanes(fleets.P1FleetP(), fleets.P2FleetP());
			    can.getShips(fleets.P1Fleet(), fleets.P2Fleet());
			    game.setMax(fleets.getP1Fleet(), fleets.getP2Fleet());
			
			
			can.paint();
				if(!game.turnMove()){
					turn = true;
					game.setTurn(turn);
					System.out.println("T1");
				}
			}
		
			}
														while(play && !team){
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
														    
														    	synchronized(socket){
														    		try {
														    			notify();
																		writeThread.wait();
																		
														    		} catch (InterruptedException e) {
																		e.printStackTrace();
																	}
														    	}
														    
														    game.setMax(fleets.getP1Fleet(), fleets.getP2Fleet());
														    can.setAttack(fleets.P1Fleet().get(game.attacker()).X(),fleets.P1Fleet().get(game.attacker()).Y());	
														    can.setStats(fleets.P1Fleet().get(game.attacker()));
														    can.setRD(game.rangeDisp());
														    if(fleets.getP2Fleet()>-1){ 
														    can.setUnit(fleets.P2Fleet().get(game.defender()).X(), fleets.P2Fleet().get(game.defender()).Y());
														    }
														    can.getPlanes(fleets.P1FleetP(), fleets.P2FleetP());
														    can.getShips(fleets.P1Fleet(), fleets.P2Fleet());
															game.setMax(fleets.getP1Fleet(), fleets.getP2Fleet());
															
															
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
																
																synchronized(socket){
														    		try {
														    			notify();
																		readThread.wait();
																		
														    		} catch (InterruptedException e) {
																		e.printStackTrace();
																	}
														    	}
																
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
																
														    	boolean fine = true;
																  for(int i = 0; i<=fleets.getP1Fleet(); i++){
																	   for(int o = 0; o <=fleets.getP2Fleet(); o++){
																		   if(fine && fleets.P1Fleet().get(i).X() == fleets.P2Fleet().get(o).X() && fleets.P1Fleet().get(i).Y() == fleets.P2Fleet().get(o).Y()){
																			  Entity nship1, nship2;
																			   int h1,h2;
																			 nship1 = fleets.P1Fleet().get(i);
																			 nship2 = fleets.P2Fleet().get(o);
																			   h1 = fleets.P1Fleet().get(i).getHP()-fleets.P2Fleet().get(o).getHP();
																			   h2 = fleets.P2Fleet().get(o).getHP()-fleets.P1Fleet().get(i).getHP();
																			   if(h1<=0){
																				   fleets.P1Fleet().remove(i);
																					   fine = false;
																					   
																				   game.collide();

																			   }
																			   else{
																				   nship1.setHP(h1);
																				   fleets.P1Fleet().set(i,nship1);
																			   }
																			   if(h2<=0){
																				   fleets.P2Fleet().remove(o);
																				   game.collide();
																				   fine = false;
															
																			   }
																			   else{
																				   nship2.setHP(h2);
																				   fleets.P2Fleet().set(o, nship2);
																			   }
																		   }
																	   }
																  if(!fine){
																  }
																  } 
																
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
																can.setRD(game.rangeDisp());
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
															    	 boolean fine1 = true;
															    	 for(int i = 0; i<=fleets.getP1Fleet(); i++){
															    		 if(fine1 && fleets.P1Fleet().get(i).X() >= bombX-1 && fleets.P1Fleet().get(i).X() <= bombX+1)
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
															    					 fine1 = false;
																					   
																					 game.collide();
															    				 }
															    			 } 
															    	 }
															    p.setMoves(13);
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
															if(fleets.P2Fleet().get(game.attacker()).Y()+fleets.P2Fleet().get(game.attacker()).gunneryRange()+1 >= fleets.P1Fleet().get(game.defender()).Y() && fleets.P2Fleet().get(game.attacker()).Y()-fleets.P2Fleet().get(game.attacker()).gunneryRange() <= fleets.P1Fleet().get(game.defender()).Y()){
																if(fleets.P2Fleet().get(game.attacker()).X()+fleets.P2Fleet().get(game.attacker()).gunneryRange()+1 >= fleets.P1Fleet().get(game.defender()).X() && fleets.P2Fleet().get(game.attacker()).X()-fleets.P2Fleet().get(game.attacker()).gunneryRange() <= fleets.P1Fleet().get(game.defender()).X()){
															 if(game.wantFire()&&fleets.P2Fleet().get(game.attacker()).Ammo()){
																 	fleets.P2Fleet().get(game.attacker()).fire();
																	int h = fleets.P1Fleet().get(game.defender()).getHP();
																	h -= fleets.P2Fleet().get(game.attacker()).getGAttack();
																	Entity nShip = fleets.P1Fleet().get(game.defender());
																	 
																	 if(h>0){
																	 nShip.setHP(h);
																	 fleets.P1Fleet().set(game.defender(), nShip);
																	 }
																	 
																	 else{
																		 fleets.P1Fleet().remove(game.defender());
																	 }
																	 game.setFire(false);
																}
																}
															}
															
															boolean moveD = true,moveS = true,moveA = true, moveW = true;
															for(int i = 0; i<=fleets.getP2Fleet(); i++){
																	int x = fleets.P2Fleet().get(game.attacker()).X();
																	int y = fleets.P2Fleet().get(game.attacker()).Y();
																if(x+1 == fleets.P2Fleet().get(i).X() && y == fleets.P2Fleet().get(i).Y())
																	moveD = false;
																if(x-1 == fleets.P2Fleet().get(i).X() && y == fleets.P2Fleet().get(i).Y())
																	moveA = false;
																if(y+1 == fleets.P2Fleet().get(i).Y() && x == fleets.P2Fleet().get(i).X())
																	moveS = false;
																if(y-1 == fleets.P2Fleet().get(i).Y() && x == fleets.P2Fleet().get(i).X())
																	moveW = false;							
															}
															
															if(fleets.P2Fleet().get(game.attacker()).canMove() && moveW || moveA || moveS || moveD){
																if(game.getWant()==1 && moveW){
																	if(fleets.P2Fleet().get(game.attacker()).Y()>0){
																		fleets.P2Fleet().get(game.attacker()).moveW();
																	game.resWant();
																}
																	else
																		game.resWant();
																
																}
																if(game.getWant()==2 && moveA){
																	if(fleets.P2Fleet().get(game.attacker()).X()>0){
																		fleets.P2Fleet().get(game.attacker()).moveA();
																	game.resWant();
																}
																	else
																		game.resWant();
																	
																}
																if(game.getWant()==3 && moveS){
																	if(fleets.P2Fleet().get(game.attacker()).Y()<39){
																		fleets.P2Fleet().get(game.attacker()).moveS();
																	game.resWant();
																}
																	else
																		game.resWant();
																	
																}
																if(game.getWant()==4 && moveD){
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

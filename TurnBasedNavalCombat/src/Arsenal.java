import java.util.ArrayList;


public class Arsenal {

	private ArrayList<Entity> player1 = new ArrayList<Entity>();
	private ArrayList<Entity> player2 = new ArrayList<Entity>();
	private ArrayList<Plane> player1P = new ArrayList<Plane>();
	private ArrayList<Plane> player2P = new ArrayList<Plane>();
	
	public Arsenal(int encounter){
		switch(encounter){
		case 1:
			player1.add(new FleetCarrier(true, 5, 35));
			player2.add(new FleetCarrier(false, 45, 35));
			player1.add(new CargoShip(true, 5, 30));
			player2.add(new CargoShip(false, 45, 30));
			player1.add(new CargoShip(true, 5, 25));
			player2.add(new CargoShip(false, 45, 25));
			player1.add(new CargoShip(true, 5, 20));
			player2.add(new CargoShip(false, 45, 20));
			player1.add(new CargoShip(true, 5, 15));
			player2.add(new CargoShip(false, 45, 15));
			player1.add(new CargoShip(true, 10, 27));
			player2.add(new CargoShip(false, 40, 27));
			player1.add(new CargoShip(true, 10, 22));
			player2.add(new CargoShip(false, 40, 22));
			
			break;
		}
	}
	public int getP1Fleet(){
		return player1.size()-1;
	}
	public ArrayList<Entity> P1Fleet(){
		return player1;
		
	}
	public ArrayList<Entity> P2Fleet(){
		return player2;
		
	}
	public int getP2Fleet(){
		return player2.size()-1;
	}
	public int getP1FleetP(){
		return player1P.size()-1;
	}
	public void setP1FleetP(ArrayList<Plane> p){
		player1P=p;
	}
	public void setP2FleetP(ArrayList<Plane> p){
		player2P=p;
	}
	public ArrayList<Plane> P1FleetP(){
		return player1P;
		
	}
	public ArrayList<Plane> P2FleetP(){
		return player2P;
		
	}
	public int getP2FleetP(){
		return player2P.size()-1;
	}
	
	
}

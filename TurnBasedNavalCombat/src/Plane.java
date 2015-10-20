
public class Plane{

	private boolean side;
	private int cord1,cord2, hp;
	private int X,Y;
	private int moves;
	private boolean bomb;
	private boolean bombed;
	private int eX, eY;
	private boolean land;
	private boolean donezo;
	private boolean direction;
	
	public Plane(boolean s, int c1, int c2, int x, int y){
		land = false;
		eX =-100;
		eY =-100;
		side=s;
		cord1=c1;
		cord2=c2;
		hp=150;
		X=x;
		Y=y;
		bomb = false;
		bombed = false;
		donezo = false;
	}
	public void setMoves(int m){
		moves = m;
	}
	public boolean donezo(){
		return donezo;
	}
	public void newTurn(){
		if(side)moves = 3;
		else moves = 2;
	}
	public void goBack(int c1, int c2){
		cord1 = c1;
		cord2 = c2;
		eX = c1;
		eY = c2;
		bomb = false;
		bombed = true;
	}
	public boolean bombed(){
		return bombed;
	}
	public int X(){
		return X;
	}
	public boolean getDirection(){
		return direction;
	}
	public int Y(){
		return Y;
	}
	public boolean bomb(){
		return bomb;
	}
	public boolean land(){
		return land;
	}
	public void move(){
			
			if(Y <  cord2 && moves<9){
				Y++;
				moves++;
			}
			if(Y > cord2 && moves<9){
				Y--;
				moves++;
			}
			if(X < cord1 && moves<9){
				X++;
				moves++;
				direction = true;
			}
			if(X > cord1 && moves<9){
				X--;
				moves++;
				direction = false;
			}
			if(X == eX && Y == eY){
				land = true;
			}
			if(X == cord1 && Y == cord2 && !land)
				bomb=true;
		
	}
	public int getDodge(){
		if(side){
			return 15;
		}
		else{
			return 20;
		}
	}
	public void setHP(int h){
		hp=h;
	}
	public int getHP(){
		return hp;
	}
	public void mFD(){
		X = -10000;
		Y = -10000;
		donezo = true;
		land = false;
	}
}

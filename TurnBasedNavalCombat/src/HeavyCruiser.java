public class HeavyCruiser implements Ship{
	
	private String name;
	private int move, count, hP, range, ammo, shots;
	private int X,Y;
	private int X1, Y1;
	private boolean canMove;
	private final boolean side;
	private boolean donezo;
	private boolean direction;
public HeavyCruiser(boolean s, int x, int y){
	side = s;
	range = 9;
	shots = 0; 
	
	if(side==true){
		name = "Northampton";
		direction = true;
		ammo = 1;
		move = 4;
	}
	else{
		name = "Mogami";
		direction = false;
		ammo = 1;
		move = 5;
	}
	canMove=false;
	X=x;
	Y=y;
	 
	setHP(regHP());
	donezo = false;
	
}
public boolean donezo(){
	return donezo;
}
public void newTurn(){
	canMove = true;
	count=0;
	shots=0;
}
	

public int getAA() {
	if(side == true){
		return 60;
	}
	else{
		return 50;
	}
}


public int getHP() {
	return hP;
}

public int regHP() {
	return 500;
}

public int Speed() {
	return 3;
}


public void setHP(int hp) {
	hP = hp;
}


public int getGAttack() {
	if(side == true){
		return 235;
	}
	else{
		return 250;
	}
}


public int getArmor() {
	return 0;
}


public int X() {
	return X;
}


public int Y() {
	return Y;
}

@Override
public boolean canMove() {
	// TODO Auto-generated method stub
	return canMove;
}

@Override
public int getMove() {
	// TODO Auto-generated method stub
	return move;
}

@Override
public void moveW() {
	// TODO Auto-generated method stub
	if(canMove){
	Y-=1;
	count++;
	}
	if(count>=move)
	canMove=false;
	
}

@Override
public void moveA() {
	
	if(canMove){
	X-=1;
	count++;
	direction = false;
	}
	if(count>=move)
	canMove=false;
	
}

@Override
public void moveS() {
	// TODO Auto-generated method stub
	if(canMove){
	Y+=1;
	count++;
	}
	if(count>=move)
	canMove=false;
	
}

@Override
public void moveD() {
	// TODO Auto-generated method stub
	if(canMove){
	X+=1;
	count++;
	direction = true;
	}
	if(count>=move)
	canMove=false;
	
}

@Override
public String name() {
	return name;
	}

@Override
public boolean isCV() {
	return false;
}

@Override
public void mFD() {
	X=-100;
	Y=-100;
	donezo = true;
}
@Override
public int aaRange() {
	if(side)
	return 6;
	else
	return 5;
}
@Override
public int Identity() {
	return 3;
}
@Override
public boolean getDirection() {
	return direction;
}
@Override
public boolean buyPlane() {
	// TODO Auto-generated method stub
	return false;
}
@Override
public void planeReturn() {
	// TODO Auto-generated method stub
	
}
@Override
public void planeShotDown() {
	// TODO Auto-generated method stub
	
}
@Override
public int gunneryRange() {
	return range;
}
@Override
public boolean Ammo() {
	if(shots<ammo)
		return true;
	return false;
}
@Override
public void fire() {
	shots++;
	
}

}


public class FleetCarrier implements Ship{

	private int squads=0,squadLim, hP, count, move, squadsAir, SA;
	private int X,Y;
	private int X1, Y1;
	private boolean canMove;
	private final boolean side;
	private boolean donezo;
	private boolean direction;
public FleetCarrier(boolean s, int x, int y){
	side = s;
	if(side==true){
		squadLim=10;
		direction = true;
		SA = 4;
	}
	else{
	direction = false;
		squadLim=11;
		SA = 5;
	}
	canMove=false;
	X=x;
	Y=y;
	move = 3;
	setHP(regHP());
	donezo = false;
	squadsAir=0;
}
public boolean donezo(){
	return donezo;
}
public void newTurn(){
	canMove = true;
	count=0;
}
	
public int numSquads(){
	return squads;
}
public void planeReturn(){
	squadLim++;
	squadsAir--;
}
public void planeShotDown(){
	squadsAir--;
}
public boolean buyPlane(){
	if(squadLim>0 && squadsAir<SA){
		squadLim--;
		squadsAir++;
		return true;
	}
	return false;
}

public int getAA() {
	if(side == true){
		return 80;
	}
	else{
		return 50;
	}
}


public int getHP() {
	return hP;
}

public int regHP() {
	return 1000;
}

public int Speed() {
	return 3;
}


public void setHP(int hp) {
	hP = hp;
}


public int getGAttack() {
	if(side == true){
		return 100;
	}
	else{
		return 1;
	}
}


public int getArmor() {
	return 8;
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
	if(side==true){
		return "USS Lexington";
	}
	else{
		return "IJN Akagi";
	}
	}

@Override
public boolean isCV() {
	return true;
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
	return 5;
	else
	return 6;
}
@Override
public int Identity() {
	return 0;
}
@Override
public boolean getDirection() {
	return direction;
}

}

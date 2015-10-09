
public interface Entity {
	public boolean canMove();
	public int X();
	public int Y();
	public int Speed();
	public void moveW();
	public void moveA();
	public void moveS();
	public void moveD();
	public int getMove();
	public int getAA();
	public int getHP();
	public int regHP();
	public void setHP(int hp);
	public int getGAttack();
	public void newTurn();
	public String name();
	public boolean isCV();
	public boolean buyPlane();
	public int getArmor();
	public void planeReturn();
	public void mFD();
	public boolean donezo();
	public int aaRange();
	public void planeShotDown();
	public int Identity();
	public boolean getDirection();
}

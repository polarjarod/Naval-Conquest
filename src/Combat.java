
public class Combat {

public Plane combatP(Ship a, Plane d){
	int hp = d.getHP();
	hp -= (a.getAA()*(100-d.getDodge()));
	d.setHP(hp);
	return d;
}
public Ship combatS(Entity a, Ship d){
	int hp = d.getHP();
	hp -= (a.getGAttack()*(100-d.getArmor()));
	d.setHP(hp);
	return d;
}
public Entity combatB(Plane a, Entity entity){
	int hp = entity.getHP();
	hp -= ((a.getHP()*2)*(100-entity.getArmor()));
	entity.setHP(hp);
	return entity;
}
	
}

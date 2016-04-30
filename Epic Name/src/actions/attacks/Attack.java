package actions.attacks;

import stats.DieRoll;
import stats.Stats;
import actions.Action;
import weapons.entities.Projectile;
import weapons.entities.WeaponEntity;
import world.World;
import entities.GameAgent;

public abstract class Attack extends Action{

	WeaponEntity attack;
	DieRoll dice;
	String primary;
	String secondary;
	public static final int MIN_COOLDOWN=10;
	
	public Attack(WeaponEntity a,DieRoll d,String primaryStat,String secondaryStat,GameAgent doer,World world){
		super(doer,world);
		attack=a;
		dice=d;
		if(!Stats.valid(primaryStat)){
			try {
				throw new Exception("Invalid primary stat");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		primary=primaryStat;
		if(!Stats.valid(secondaryStat)){
			try {
				throw new Exception("Invalid secondary stat");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		secondary=secondaryStat;
	}
	
	public void begin() {
		super.begin();
	}
	
	/*
	 * logic should be handled by the AttackEntity
	 */
	public void execute(){
		super.execute();
	}
	
	public void end(){
		super.end();
	}
	
	public boolean isMobile(){
		return true;
	}
	
	public int getDuration(){
		return Math.max(attack.getCoolDown()+60-doer.stats.getStat(secondary).val,MIN_COOLDOWN);
	}

}
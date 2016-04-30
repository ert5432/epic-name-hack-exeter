package actions.attacks;

import stats.DieRoll;
import weapons.entities.WeaponEntity;
import world.World;
import entities.GameAgent;
import actions.Action;
import actions.ActionInfo;

public abstract class AttackInfo extends ActionInfo{

	protected WeaponEntity attack;
	protected DieRoll damageRoll;
	protected String primaryStat;
	protected String secondaryStat;

	public AttackInfo(WeaponEntity a,DieRoll d,String primaryStat,String secondaryStat){
		this.attack=a;
		this.damageRoll=d;
		this.primaryStat=primaryStat;
		this.secondaryStat=secondaryStat;
	}

}

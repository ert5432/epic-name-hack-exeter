package stats;

public class Stats {

	public static final String STAT_STRENGTH="str",STAT_DEXTERITY="dex",
			STAT_INTELLEGENCE="int",STAT_WISDOM="wis",STAT_CONSTITUTION="con";
	public Stat strength;
	public Stat dexterity;
	public Stat intellegence;
	public Stat wisdom;
	public Stat constitution;
	public int maxHp;
	
	public Stats(int str,int dex,int inte,int wis,int hp) {
		strength=new Stat(str);
		dexterity=new Stat(dex);
		intellegence=new Stat(inte);
		wisdom=new Stat(wis);
		maxHp=hp;
		constitution=new Stat(hp);
	}
	
	public Stat getStat(String stat){
		
		if(stat.equals(STAT_STRENGTH))
			return strength;
		else if(stat.equals(STAT_DEXTERITY))
			return dexterity;
		else if(stat.equals(STAT_INTELLEGENCE))
			return intellegence;
		else if(stat.equals(STAT_WISDOM))
			return wisdom;
		else if(stat.equals(STAT_CONSTITUTION))
			return constitution;
		else
			return null;
	}

	public static boolean valid(String stat) {
		return stat.equals(STAT_STRENGTH)||stat.equals(STAT_DEXTERITY)||stat.equals(STAT_INTELLEGENCE)
			||stat.equals(STAT_WISDOM)||stat.equals(STAT_CONSTITUTION);	
	}
}

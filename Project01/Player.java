//Name : Thanwarat Wongthongtham
//ID : 6288145
//Sec3
import java.util.Arrays;

public class Player {

	public enum PlayerType {Healer, Tank, Samurai, BlackMage, Phoenix, Cherry};
	
	private PlayerType type; 	//Type of this player. Can be one of either Healer, Tank, Samurai, BlackMage, or Phoenix
	private double maxHP;		//Max HP of this player
	private double currentHP;	//Current HP of this player 
	private double atk;			//Attack power of this player
	private int numSpecialTurn;
	public int Count = 0;
	
	private boolean Taunting;
	private boolean Cursed;
	private boolean Sleeping;
	private Player TargetCursed;
	
	//represent 'that' team for StringPattern
	public Arena.Team team;
	public Arena.Row row;
	public int position;
	
	/**
	 * Constructor of class Player, which initializes this player's type, maxHP, atk, numSpecialTurns, 
	 * as specified in the given table. It also reset the internal turn count of this player. 
	 * @param _type
	 */
	
	public Player(PlayerType _type)
	{	
		//INSERT YOUR CODE HERE
		this.type = _type;
		switch(this.type) 
		{
			case Healer:
				this.maxHP = 4790;
				this.atk = 238;
				this.currentHP = 4790;
				this.numSpecialTurn = 4;
				this.Count = 0;
			break;
			
			case Tank:
				this.maxHP = 5340;
				this.atk = 255;
				this.currentHP = 5340;
				this.numSpecialTurn = 4;
				this.Count = 0;
			break;
			
			case Samurai:
				this.maxHP = 4005;
				this.atk = 368;
				this.currentHP = 4005;
				this.numSpecialTurn = 3;
				this.Count = 0;
			break;
				
			case BlackMage:
				this.maxHP = 4175;
				this.atk = 303;
				this.currentHP = 4175;
				this.numSpecialTurn = 4;
				this.Count = 0;
			break;
			
			case Phoenix:
				this.maxHP = 4175;
				this.atk = 209;
				this.currentHP = 4175;
				this.numSpecialTurn = 8;
				this.Count = 0;
			break;
			
			case Cherry:
				this.maxHP = 3560;
				this.atk = 198;
				this.currentHP = 3560;
				this.numSpecialTurn = 4;
				this.Count = 0;
			break;
		}
	}
	/**
	 * to make Player know team,row,position in arena
	 * @param team
	 * @param row
	 * @param position
	 */
	public void setData(Arena.Team team,Arena.Row row,int position) {
		this.team = team;
		this.row = row;
		this.position = position;
	}
	/**
	 * Returns the current HP of this player
	 * @return
	 */
	public double getCurrentHP()
	{
		//INSERT YOUR CODE HERE
		return currentHP;
	}
	
	/**
	 * Returns type of this player
	 * @return
	 */
	
	public Player.PlayerType getType()
	{
		//INSERT YOUR CODE HERE
		return type;
	}
	
	/**
	 * Returns max HP of this player. 
	 * @return
	 */
	
	public double getMaxHP()
	{
		//INSERT YOUR CODE HERE	
		return maxHP;
	}
	
	/**
	 * Returns whether this player is sleeping.
	 * @return
	 */
	
	public boolean isSleeping()
	{
		//INSERT YOUR CODE HERE
		return Sleeping;
	}
	
	/**
	 * Returns whether this player is being cursed.
	 * @return
	 */
	
	public boolean isCursed()
	{
		//INSERT YOUR CODE HERE
		
		return Cursed;
	}
	
	/**
	 * Returns whether this player is alive (i.e. current HP > 0).
	 * @return
	 */
	
	public boolean isAlive()
	{
		//INSERT YOUR CODE HERE
		if(currentHP>0) {
			return true;
		}
		return false;
		
	}
	
	/**
	 * Returns whether this player is taunting the other team.
	 * @return
	 */
	
	public boolean isTaunting()
	{
		//INSERT YOUR CODE HERE
		
		return Taunting;
	}
	
	/**
	 * to attack target
	 * @param target
	 */
	public void attack(Player target)
	{	
		//INSERT YOUR CODE HERE
		if(target!=null) {
			target.currentHP -= this.atk;
		}
		if(target.currentHP < 0) {
			target.currentHP = 0;
			target.Cursed = false;
			target.Sleeping= false;
			target.Taunting = false;
		}
		
	}
	
	//just get Hp percent
	public double getHPpercent(Player player) { 
		return player.currentHP / player.maxHP ;
	}
	//find lowest Hp percent
	
	public Player findHppercent(Player[][] team) { 
		 double[] HP = new double[team[0].length + team[1].length];
		 
		 for(int i=0; i<team[0].length; i++) {
			 if(team[0][i].isAlive()){
				 HP[i] = getHPpercent(team[0][i]);
			 }
			 else{
				 HP[i] = 9999;
			 }
		  }
		 
		  for(int i = team[0].length,j = 0; i < team[0].length*2; i++,j++){
			  if(team[1][j].isAlive()){
				  HP[i] = getHPpercent(team[1][j]);
			  }
			  else{
				  HP[i] = 9999;
			  }
		 }
		 
		 Arrays.sort(HP);
		 
		 for(int i = 0; i < 2; i++) {
			 for(int j = 0; j < team[0].length; j++) {
				 if(HP[0] == getHPpercent(team[i][j])) {
					 return team[i][j];
				 }
			 }
		 }
		   return null;
		 }
	/**
	 * Find ally in Team for Phoenix
	 * @param myTeam
	 * @return
	 */
	public Player FindAlly(Player[][] myTeam) {
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < myTeam[0].length; j++) {
				if(!myTeam[i][j].isAlive()) {
					return myTeam[i][j];
				}
			}
		}
		return null;
	}
	/**
	 * Condition to use special ability
	 * @param myTeam
	 * @param theirTeam
	 */
	public void useSpecialAbility(Player[][] myTeam, Player[][] theirTeam)
	{	
		//INSERT YOUR CODE HERE
		Player target = null;
		switch (this.type) 
		{
		case Healer :
        	if(findHppercent(myTeam).isCursed() == false && findHppercent(myTeam).isAlive()){
				target  =  findHppercent(myTeam);
				if(target.currentHP != target.maxHP) {
					target.currentHP += (0.25 * target.maxHP);
					if(target.currentHP > target.maxHP) target.currentHP = target.maxHP;
					System.out.println("# " + StringPattern() + " Heals " + target.StringPattern());
				}	
        	}
            break;
            
        case Tank :
        	Taunting = true;
        	System.out.println("# " + this.StringPattern() + " is Taunting");
        	break;
        	
        case Samurai :
			target = findTarget(theirTeam);
        	if(target != null) {
				attack(target); 
				attack(target); //atk*2
    		System.out.println("# " + this.StringPattern() + " Double-Slashes " + target.StringPattern());	
        	}
        	break;
        	
		case BlackMage :
        	if(findTarget(theirTeam) != null && findTarget(theirTeam).isAlive()) {
        			this.TargetCursed = findTarget(theirTeam);
        			this.TargetCursed.Cursed = true;
        			 
        	System.out.println("# " + this.StringPattern() + " Curses " + findTarget(theirTeam).StringPattern());
        	}
        	break;
        	
		case Phoenix:
			target = FindAlly(myTeam);
        	if(target != null) {
        		target.Count = 0;
				target.Taunting = false;
				target.Sleeping = false;
				target.Cursed = false;
				target.currentHP += (0.30 * target.maxHP);
				System.out.println("# " + this.StringPattern() + " Revives " + target.StringPattern());
        	}
        	break;
        	
        case Cherry:
        	for(int i = 0; i < 2; i++) {
        		for(int j = 0; j < theirTeam[0].length; j++) {
        			if(theirTeam[i][j].isAlive()) {
        				theirTeam[i][j].Sleeping = true;
        				target = theirTeam[i][j];
        	        	System.out.println("# " + this.StringPattern() + " Feeds a Fortune Cookie to " + target.StringPattern());

        			}
        		}
        	}
        	break;
		}
		
	}
	
	/**
	 * This method is called by Arena when it is this player's turn to take an action. 
	 * By default, the player simply just "attack(target)". However, once this player has 
	 * fought for "numSpecialTurns" rounds, this player must perform "useSpecialAbility(myTeam, theirTeam)"
	 * where each player type performs his own special move. 
	 * @param arena
	 */
	public void takeAction(Arena arena)
	{	
		//INSERT YOUR CODE HERE
		Player target;
		if(this.type == PlayerType.Tank) {
			this.Taunting = false;
		}
		
		if(this.type == PlayerType.BlackMage && this.TargetCursed != null) {
			this.TargetCursed.Cursed = false;
		}
		// if sleep go next
		if(this.isAlive() && (this.isSleeping() == false)) {
			
			Count++;
			switch(this.team)
			{
			
			case A:
				if(this.type.equals(PlayerType.Cherry)) {
					for(int i = 0; i < 2; i++) {
						for(int j = 0; j < arena.getTeam(Arena.Team.B)[0].length; j++) {
							if(arena.getTeam(Arena.Team.B)[i][j].isAlive()) {
							arena.getTeam(Arena.Team.B)[i][j].Sleeping = false;
							}
						}
					}
				}
				if(Count == numSpecialTurn) {
					useSpecialAbility(arena.getTeam(Arena.Team.A), arena.getTeam(Arena.Team.B));
					this.Count = 0;
				}
				else  {
					target = findTarget(arena.getTeam(Arena.Team.B));
					if(target!=null) 
					{
					attack(target);
					System.out.println("# " + this.StringPattern() + " Attacks " +  target.StringPattern());
					}
				}
				break;
				
			case B :

				if(this.type.equals(PlayerType.Cherry)) {
					for(int i = 0; i < 2; i++) {
						for(int j = 0; j < arena.getTeam(Arena.Team.B)[0].length; j++) {
							if(arena.getTeam(Arena.Team.A)[i][j].isAlive()) {
							arena.getTeam(Arena.Team.A)[i][j].Sleeping = false;
							}
						}
					}
				}
				
				if(Count == numSpecialTurn) {
					useSpecialAbility(arena.getTeam(Arena.Team.B), arena.getTeam(Arena.Team.A));
					this.Count = 0;
				}
				else  {
					target = findTarget(arena.getTeam(Arena.Team.A));
					if(target!=null) 
					{
					attack(target);
					System.out.println("# " + this.StringPattern() + " Attacks " +  target.StringPattern());
					}
				}

			break;
			
		    }
	    }
		this.Sleeping = false;
	}
	/**
	 * This method overrides the default Object's toString() and is already implemented for you. 
	 */
	public Player findTarget(Player[][] team)
	{
		if(findlowestHp(team)==null)  return null;
		for(int i = 0; i < team.length; i++) {
			for(int j = 0; j < team[i].length; j++) {
				if(team[i][j].isAlive() && team[i][j].isTaunting()) {
					return team[i][j];
				}
			}
		}
		return findlowestHp(team);
	}
	/**
	 * Check if front side is Alive
	 * @param team
	 * @return
	 */
	public boolean frontIsAlive(Player[][] team) {
		int countFrontAlive = 0 ;
		for(int i=0;i<team[0].length;i++) {
			if(team[0][i].isAlive()) {
				countFrontAlive++;
			}
		}
		if(countFrontAlive>0) { //front row
			return true; 
		}
		else {
			return false;
		}
	}
	/**
	 * find lowest hp 
	 * @param team
	 * @return
	 */
	public Player findlowestHp(Player[][] team){
		double lowestHp = 10000 ;
		int x; // front or back
		if(frontIsAlive(team)) { 
			x=0;
		}
		else {
			x=1;
		}
		for(int i=0;i<team[0].length;i++) {
			if(team[x][i].isAlive() && team[x][i].getCurrentHP()<lowestHp) {
				lowestHp = team[x][i].getCurrentHP();
				
			}
		}
		//if lowesthp is 10000 means somethings wrongs
		if(lowestHp==10000) {
			return null;
		}
		for(int i=0;i<2;i++) {
			for(int j=0;j<team[0].length;j++) {
				if(lowestHp == team[i][j].getCurrentHP()) {
					return team[i][j];
				}
			}	
		 }
	return null;
	}
	/*
	 * Pattern for print useSpecial...
	 */
	public String StringPattern() {
		return this.team.toString() + "[" + this.row.toString() + "]" + "[" + this.position + "]" + " {" +  this.type.toString() + "}";
	}
	@Override
	public String toString()
	{
		return "["+this.type.toString()+" HP:"+this.currentHP+"/"+this.maxHP+" ATK:"+this.atk+"]["
				+((this.isCursed())?"C":"")
				+((this.isTaunting())?"T":"")
				+((this.isSleeping())?"S":"")
				+"]";
	}
	
	
}

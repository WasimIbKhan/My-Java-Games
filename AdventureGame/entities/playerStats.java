package entities;

public class playerStats {

		int HP;
		String weapon;
		int wealth;
		int reputation;
		public playerStats(int hHP, String wWeapon ,int wWealth, int rreputation) {
			HP=hHP;
			weapon=wWeapon;
			wealth=wWealth;
			reputation=rreputation;
		}
		public playerStats() {
			
		}
		
		public void increaseHP(int HPIncrease) {
			 HP=+ HPIncrease;
		}
		public void decreaseHP(int HPDecrease) {
			HP-=HPDecrease;
		}
		public void increaseWealth(int wealthIncrease) {
			wealth+=wealthIncrease;
		}
		public void decreaseWealth(int wealthDecrease) {
			wealth-= wealthDecrease;
		}
		public void increaseReputation(int reputationIncrease) {
			reputation+= reputationIncrease;
		}
		public void decreaseReputation(int reputationDecrease) {
			reputation-= reputationDecrease;
		}
		public void changeWeapon(String currentWeapon) {
			weapon=currentWeapon;
		}
		public String currentWeapon() {
			return weapon;
		}
		public int currentHP() {
			return HP;
		}
		public int currentWealth() {
			return wealth;
		}
		public int currentReputation() {
			return reputation;
		}
}

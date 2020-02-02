
public class UserProfile {

	private String username;
	private int[] scores;
	
	public UserProfile(String info) {
		String[] newInfo = info.split(",");
		this.username = newInfo[0];
		this.scores = new int[0];
		if (newInfo.length > 1) {
			for (int i = 1; i < newInfo.length; i++) {
				this.updateScore(i, Integer.parseInt(newInfo[i]));
			}
		}
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public int getHighestLevel() {
		return this.scores.length;
	}
	
	public int getLevelScore(int lvlID) {
		int n = 0;
		if (lvlID <= this.getHighestLevel()) {
			n = this.scores[lvlID-1];
		}
		return n;
	}
	
	public void updateScore(int lvlID, int score) {
		if (lvlID > this.getHighestLevel()) {
			int[] tempScores = new int[this.scores.length+1];
			for (int i = 0; i < this.scores.length; i++) {
				tempScores[i] = this.scores[i];
			}
			this.scores = tempScores;
		}
		this.scores[lvlID-1] = score;
	}
	
	public String toString() {
		String s = this.username;
		for (int score: this.scores) {
			s += "," + Integer.toString(score);
		}
		return s;
	}

}

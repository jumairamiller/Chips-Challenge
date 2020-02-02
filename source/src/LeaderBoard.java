
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
*This class is responsible for retrieving the scores from each UserProfile and constructing a descending leaderboard.
*
* <br>Creation Date: 19/11/2019
* <br>Last Modified Date: 08/12/2019
* @author Joseff Pugh (975656), Nihal Goindi (976005), Timothy Roger (977422)
* @version 1.7
* 
*/

public class LeaderBoard  {
	
	static ArrayList<UserProfile> users;	//each user profile should be constructed as (String username, int[] scores) at least
	

	/**
	 * Method to create the leaderboard for a certain level.
	 * @param profileList
	 * 					ArrayList of existing UserProfiles
	 * @param level
	 * 					the level of which the user scores will be compared to 
	 * 
	 * @return the constructed sorted leaderboard Map
	 */
	public static Map<String,Integer> getBoard(ArrayList<UserProfile> profileList, Integer level) {		
		
		//retrieves and sorts the leaderboard
		Map<String, Integer> lb = sortScores(getScores(profileList, level));	
		
		return lb;
	}
	
	/**
	 * Gets the scores from users for a specific level and maps them in an unsorted leaderboard.
	 * @param userScores ArrayList - the arraylist of UserProfile instances that exist.
	 * @param level Integer - the value of levelID for which the scores will be displayed.
	 * @return the unsortedLBoard, a Map instance.
	 */
	private static Map<String, Integer> getScores(ArrayList<UserProfile> userScores, Integer level) {	
		
		Map<String, Integer> unsortedLBoard = new HashMap<String, Integer>();
		for(UserProfile user: userScores) {
			unsortedLBoard.put(user.getUsername(), user.getLevelScore(level));
		}
		return unsortedLBoard;
	}
	
	
	/**
	 * Sorts the scores in provided Map and makes a sorted map in descending order.
	 * @param unsortedLBoard Map - The unsorted Map that this method will sort
	 * @return a sorted leader board of type Map.
	 */
	private static  Map<String, Integer> sortScores(Map<String, Integer> unsortedLBoard) {	
		
		
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortedLBoard.entrySet());
		
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> pos1, Map.Entry<String, Integer> pos2) {
				return (pos1.getValue()).compareTo(pos2.getValue());
			}
		});
		Collections.reverse(list);
		
		Map<String, Integer> sortedLBoard = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> user : list) {
			sortedLBoard.put(user.getKey(), user.getValue());
		}
		
		
		return sortedLBoard;
	}
}
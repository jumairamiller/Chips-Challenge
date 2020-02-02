import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
*This class is responsible for reading level files and writing the save file when the game
*is saved.
* <br>Creation Date: 19/11/2019
* <br>Last Modified Date: 7/12/2019
* @author Joseff Pugh (975656), Timothy Roger(977422)
* @version 1.4
*/
public class FileReader {
	
	//Level file path 
	private static final String LEVEL_FN_PATH = "level/";
	private static final String LEVEL_FN_SAVEPATH = "saved/";
	private static final String LEVEL_FN_FILETYPE = ".txt";
	private static final String PROFILES_FN_PATH = "profiles";
	private static final String PROFILES_FN_FILETYPE = ".txt";
	
	/**
	 * Reads the level file, line by line, and returns it as an String[].
	 * @param username String - the username of the player opening the file.
	 * @param levelID int - the levelID of the level to be opened.
	 * @param isSaveFile boolean - signals if the file to be opened is a save file or not.
	 * 		
	 * @return and array of strings to be passed onto the ReadLevelInfo class
	 */
	public static String[] readFile(String username, int levelID, boolean isSaveFile) {
		
		//creates the filename of the file that level information needs to be loaded from.
		String filename = LEVEL_FN_PATH;
		if (isSaveFile) {
			filename += LEVEL_FN_SAVEPATH + username;
		} else {
			filename += Integer.toString(levelID);
		}
		filename += LEVEL_FN_FILETYPE;
		
		return readFile(filename);
	}
	
	
	/**
	 * Reads the save file given by a user, and writes it out as a level file.
	 * @param username 
	 * 					the username of the user who this save file is saved to
	 * @param level
	 * 					the array of strings this method will format into a level file
	 */
	public static void writeFile(String username, String[] level) {
		String data = level[0];
		for(int i = 1; i < level.length; i++) {
			String line = level[i];
			data = data + ("\n" + line);
		}
		
		String filename = LEVEL_FN_PATH + LEVEL_FN_SAVEPATH + username + LEVEL_FN_FILETYPE;	
		writeFile(filename, data);
	}
	
	public static boolean saveCheck(String username) {
		File temp;
		boolean b = false;
		try {
			temp = new File(LEVEL_FN_PATH + LEVEL_FN_SAVEPATH + username + LEVEL_FN_FILETYPE);
			b = temp.exists();
		} catch (Exception e) {}
		return b;
	}

	public static UserProfile[] getProfiles() {
		
		String filename = PROFILES_FN_PATH + PROFILES_FN_FILETYPE;
		
		String[] info = readFile(filename);
		
		UserProfile[] profiles = new UserProfile[info.length];
		
		for (int i = 0; i < info.length; i++) {
			profiles[i] = new UserProfile(info[i]);
		}
		
		return profiles;
	}
	
	public static void addProfile(String profileString) {
		
		
		String data = "";
		
		//profileString can include simply the username or the username and the scores for each level it has.
		data += profileString + "\n"; 
		
		try {
			UserProfile[] currProfiles = getProfiles();
			for (UserProfile p: currProfiles) {
				data += p.toString() + "\n";
			}
		} catch (java.util.NoSuchElementException e) {}
		
		String filename = PROFILES_FN_PATH + PROFILES_FN_FILETYPE;
		
		writeFile(filename, data);
	}
	
	public static UserProfile getUserProfile(String username) {
		UserProfile[] currProfiles = getProfiles();
		UserProfile p = null;
		for (UserProfile profile: currProfiles) {
			if (username.equals(profile.getUsername())) {
				p = profile;
			}
		}
		return p;
	}
	
	public static void removeProfile(String chosenUser) {
		UserProfile[] currProfiles = getProfiles();
		String data = "";
		
		for (UserProfile profile: currProfiles) {
			if (!chosenUser.equals(profile.getUsername())) {
				data += profile.toString() + "\n";
			}
		}
		
		String filename = PROFILES_FN_PATH + PROFILES_FN_FILETYPE;
		
		writeFile(filename, data);
	}
	
	public static void addScore(String username, int lvlID, int score) {
		UserProfile p = getUserProfile(username);
		removeProfile(username);
		p.updateScore(lvlID, score);
		addProfile(p.toString());
	}
		
	private static String[] readFile(String filename) {
		
		File file = new File(filename);
		Scanner in = null;

		try {
			in = new Scanner(file);
		} catch(FileNotFoundException e) {
			System.out.println("Cannot open " + file);
			System.exit(0);
		}
		
		String s = in.nextLine();
		Scanner in2 = new Scanner(s);
		// reads between commas
		in2.useDelimiter(",");
	
		//Arraylist needed to add elements to it
		List<String> infoList = new ArrayList<String>();
		infoList.add(in2.nextLine());
		while(in.hasNextLine()) {
			infoList.add(in.nextLine());
		}
		in2.close();
		
		String[] info = new String[infoList.size()];
		//Change the Arraylist back into an array
		infoList.toArray(info);
		return info;
		
	}
	
	private static void writeFile(String filename, String data) {
		
		File saveFile = new File(filename);	
		FileWriter fr = null;
		try {
			fr = new FileWriter(saveFile);
			fr.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}


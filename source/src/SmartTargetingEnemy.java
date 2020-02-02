import javafx.scene.image.Image;
import java.util.*;

/**
 * This is a SmartTargetingEnemy class which is used to create an enemy that
 * correctly follows the character around the map. 
 * This Class is an implementation of the A* algorithm which was done with the
 * help of https://rosettacode.org/wiki/A*_search_algorithm#Java.
 * and modified to work with the game.
 * <br> Creation Date: 25/11/19.
 * <br> Last Modification Date: 6/12/19.
 * @author Szymon Grzech (988065)
 * @version 1.6
 */
public class SmartTargetingEnemy extends Enemy{
	//Final Variables
	//Image:
	private final static String IMAGE_FN_SMART = ("smart"); //folder name
	
	/**
	 * freeCell is a list of Nodes of coordinates that can be walked on
	 * checkedCell is a list of Nodes of coordinates that had already been checked
	 * path is the list of Nodes that contains a list of coordinates that go
	 * directly to the character from the enemy.
	 * current is a Node that is currently being checked.
	 */
	private List<Node> freeCell = new ArrayList<>();
	private List<Node> checkedCell = new ArrayList<>();
	private List<Node> path = new ArrayList<>();
	private Node current;
	
	/**
	 * Constructs the SmartTargetingEnemy
	 * @param coords the enemies coordinates (x,y)
	 */
	public SmartTargetingEnemy(int[] coords) {
		super(coords);
		/**
		 * constructs the first Node at the current enemies location
		 */
		this.current =  new Node(null, this.coords[0], this.coords[1], 0, 0);
	}
	
	/**
	 * Image of the enemy, 4 different images for 
	 * 4 different directions its going to face.
	 */
	public Image image(String IMAGE_FOLDER) {	
		return new Image(IMAGE_FOLDER + IMAGE_FOLDERNAME_ENEMY + IMAGE_FN_SMART + IMAGE_FN_FILETYPE);
	}
	

	/**
	 * If the enemy didn't find a path to the character then it will move randomly.
	 * @param gameMap a map of Cells
	 */
	public void randomMove(Cell[][] gameMap) {
		
		Random r = new Random();
		int randomInteger = r.nextInt(4);
		
		switch(randomInteger) {
		case 0:
			if(gameMap[this.coords[1] + 1][this.coords[0]] instanceof Floor
					&& !((Floor) gameMap[this.coords[1] + 1][this.coords[0]]).collPresent()) {
				this.coords[1] += 1;
			}else {
				randomMove(gameMap);
			}
			break;
			
		case 1:
			if(gameMap[this.coords[1] - 1][this.coords[0]] instanceof Floor
					&& !((Floor) gameMap[this.coords[1] - 1][this.coords[0]]).collPresent()) {
				this.coords[1] -= 1;
			}else {
				randomMove(gameMap);
			}
			break;
			
		case 2:
			if(gameMap[this.coords[1]][this.coords[0] + 1] instanceof Floor
					&& !((Floor) gameMap[this.coords[1]][this.coords[0] + 1]).collPresent()) {
				this.coords[0] += 1;
			}else {
				randomMove(gameMap);
			}
			break;
			
		case 3:
			if(gameMap[this.coords[1]][this.coords[0] - 1] instanceof Floor
					&& !((Floor) gameMap[this.coords[1]][this.coords[0] - 1]).collPresent()) {
				this.coords[0] -= 1;
			}else {
				randomMove(gameMap);
			}
			break;
		}
		
	}
	
	/**
	 * Once this method is called then it runs the path finding algorithm
	 * and then if it is not empty then it will remove the first node from it
	 * as the first node is the initial location of the enemy. Then it will
	 * set the enemies coordinates (x,y) to the x and y of the node at index 0
	 * if the path list is not empty.
	 */
	public void move(Character ch, Cell[][] gameMap) {
		
		findPathTo(ch, gameMap);
		
		if (!this.path.isEmpty()) {
			this.path.remove(0);
		
			if(!this.path.isEmpty()) {
				
				this.coords[0] = this.path.get(0).getX();
				this.coords[1] = this.path.get(0).getY();
				
			}
			this.path.clear();
			
		}else {
			
			randomMove(gameMap);
			
		}
		
		characterCheck(ch);
	}
	
	/**
	 * Finds the path to the character
	 * @param ch Character - the character to path towards.
	 * @param gameMap Cell[][]- The map to use to find the path on.
	 * @return if no path exists then it will return null 
	 * 		   otherwise it will return the path to the character in a 
	 * 		   List of Nodes
	 */
	private List<Node> findPathTo(Character ch, Cell[][] gameMap){
		
		// Sets the current Cell as checked
		this.checkedCell.add(this.current);
		
		// Calls a function to get a new current Cell
		addNeighborsToFreeCellList(gameMap, ch);
		
		//Repeats the while loop until the character has been found 
		while(this.current.getX() != ch.getCoords()[0] ||  this.current.getY() != ch.getCoords()[1]) {
			
			
			if(this.freeCell.isEmpty()) {
				return null;
			}
			
//			 Gets the first node with the lowest Manhattan distance
//			 as the current node and then removes it from the freeCell list,
//			 then adds it to the checkedCell list, and gets new Nodes by calling
//			 addNeighborsToFreeCellList() function.
			this.current = this.freeCell.get(0);
			this.freeCell.remove(0);
			this.checkedCell.add(this.current);
			addNeighborsToFreeCellList(gameMap, ch);
		}
		
		
		this.path.add(0, this.current);
		
//		Gets the parents of the Nodes until it reaches the enemies current location if the 
//		player has been found and adds it to the path list.
		while(this.current.getX() != this.coords[0] || this.current.getY() != this.coords[1]) {

			this.current = this.current.getParent();
			this.path.add(0, this.current);
		}
		// clears the lists at the end so that the code can run again without any issues
		// as it will check if the nodes have been visited already and if they have then 
		// it will not visit them again causing problems, this prevents that.
		this.checkedCell.clear();
		this.freeCell.clear();
		return this.path;
	}
	
	/**
	 * Looks in a given list for a node 
	 * @param array a list of neighbours found around the node
	 * @param node the node that is been searched around
	 * @return boolean to shot that a new node has been found
	 */
	private boolean findNeighbourInList(List<Node> array, Node node) {
		return array.stream().anyMatch((n) -> (n.getX() == node.getX() && n.getY() == node.getY()));
	}
	
	/**
	 * Calculates the distance between the character and the current node.
	 * @param dx direction x. either -1, 1 or 0.
	 * @param dy direction y. either -1, 1 or 0.
	 * @param ch character.
	 * @return a double value of the distance between character and the current node
	 */
	private double distance(int dx, int dy, Character ch) { 
		return Math.abs(this.current.getX() + dx - ch.getCoords()[0])
				+ Math.abs(this.current.getY() + dy - ch.getCoords()[1]);
	}
	
	/**
	 * Creates a new node that and if it satisfies all the conditions stated below
	 * that it will be added to the freeCell list and the counter g will be increased by 1.
	 * At the end of it, it will sort the list having the smallest counter on top, at index 0,
	 * @param gameMap 2D array of Cells
	 * @param ch the character
	 */
	private void addNeighborsToFreeCellList(Cell[][] gameMap, Character ch) {
		
		Node node;
		
		for(int x = -1; x <= 1; x++) {
			for(int y = -1; y <= 1; y++) {
				if(x != 0 && y != 0) {
					continue;
				}
				node = new Node(this.current, this.current.getX() + x, this.current.getY() + y, this.current.getG(), this.distance(x, y, ch));

				if((x != 0 || y != 0)
						&& this.current.getX() + x > 0 && this.current.getX() + x < gameMap[0].length
						&& this.current.getY() + y > 0 && this.current.getY() + y < gameMap.length
						&& gameMap[this.current.getY() + y][this.current.getX() + x] instanceof Floor
						&& !findNeighbourInList(this.freeCell, node) && !findNeighbourInList(this.checkedCell, node)) {
					
					if(!((Floor) gameMap[this.current.getY() + y][this.current.getX() + x]).collPresent()) {
						node.setG(node.getParent().getG() + 1);
						this.freeCell.add(node);
					}
				}
			}
		}
		Collections.sort(this.freeCell);
	}

}
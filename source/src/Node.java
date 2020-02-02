/**
 * This is a Node class which is used to create a Linked list of nodes 
 * inside the SmartTargetingEnemy Class
 * <br> Creation Date: 25/11/19.
 * <br> Last Modification Date: 30/11/19.
 * @author Szymon Grzech (988065)
 * @version 1.2
 */
public class Node implements Comparable<Object>{
	
	/**
	 * Parent is the previous node to the current one.
	 * x is the coordinate x.
	 * y is the coordinate y.
	 * g is the movement cost to the next cell.
	 * h is the estimated movement cost to move from the enemies location 
	 * to the characters location.
	 */
	private Node parent;
	private int x;
	private int y;
	private double g;
	private double h;
	
	/**
	 * Constructs a Node
	 * @param parent the previous Node
	 * @param x coordinate x
	 * @param y coordinate y
	 * @param g the movement cost to the next cell.
	 * @param h estimated movement cost to move from the enemies location 
	 * to the characters location.
	 */
	public Node(Node parent, int x, int y, double g, double h) {
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.g = g;
		this.h = h;
	}

	/**
	 * Get x coordinate
	 * @return x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Set x coordinate
	 * @param x coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Get y coordinate
	 * @return y coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * Set y coordinate
	 * @param y coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Get g value
	 * @return g value
	 */
	public double getG() {
		return g;
	}
	
	/**
	 * Get g value
	 * @return h value
	 */
	public double getH() {
		return h;
	}
	
	/**
	 * Set g value
	 * @param g cost
	 */
	public void setG(double g) {
		this.g = g;
	}
	
	/**
	 * Set h value
	 * @param h cost
	 */
	public void setH(double h) {
		this.h = h;
	}

	/**
	 * Compares the g and h values to determine which direction will
	 * have the shortest distance to the character from the enemies location.
	 * @param o Object
	 */
	@Override
	public int compareTo(Object o) {
		Node that = (Node) o;
		return (int)((this.g + this.h) - (that.g + that.h));
	}

	/**
	 * Get the parent of the node
	 * @return the parent of the node
	 */
	public Node getParent() {
		return parent;
	}

	/**
	 * Set a parent of the node
	 * @param parent the parent of the node
	 */
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
}

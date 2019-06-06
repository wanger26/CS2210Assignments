/**
 * 
 * @author Jakob Wanger
 * This class implements a node of a graph
 */
public class GraphNode {
	
	private int name; 
	private boolean marked; 

	/**
	 * This is the constructor to create a GraphNode object
	 * @param name the name of the GraphNode
	 */
	public GraphNode(int name) {
		this.name=name; 
		this.marked=false;
	}
	
	/**
	 * Set's the mark of the GraphNode.
	 * @param mark the new mark of GraphNode 
	 */
	public void setMark(boolean mark) {
		this.marked=mark; 
	}
	
	/**
	 * Returns the mark of the GraphNode
	 * @return the mark value of the GraphNode
	 */
	public boolean getMark() {
		return this.marked; 
	}
	
	/**
	 * Returns the name of the GraphNode
	 * @return the name of the GraphNode
	 */
	public int getName() {
		return this.name; 
	}
	
}

/**
 * This class will implement a Node for a Binary Search Tree 
 * @author Jakob Wanger
 *
 */
public class Node {

	private Record data; 
	private Node leftChild;
	private Node rightChild; 
	private Node parent; 
	
	/**
	 * Creates empty a node 
	 */
	public Node () {
		this.data=null; 
		this.leftChild=null; 
		this.rightChild=null; 
		this.parent=null; 
	}
	
	/**
	 * Creates a node with a record 
	 * @param data the data of the record 
	 */
	public Node (Record data) {
		this.data=data; 
		this.leftChild=null; 
		this.rightChild=null; 
		this.parent=null; 
	}
	
	/**
	 * Creates a node with a given record, children and parent 
	 * @param data the data of the record 
	 * @param leftChild the left child of the node
	 * @param rightChild the right child of the node 
	 * @param parent the parent of the node
	 */
	public Node (Record data, Node leftChild, Node rightChild, Node parent) {
		this.data=data; 
		this.leftChild=leftChild; 
		this.rightChild=rightChild; 
		this.parent=parent; 
	}
	
	/**
	 * 
	 * @return returns the record stored in the node
	 */
	public Record getData() {
		return this.data; 
	}
	
	/**
	 * 
	 * @return returns the left child of the node
	 */
	public Node getLeftChild() {
		return this.leftChild; 
	}
	
	/**
	 * 
	 * @return returns the right child of the node 
	 */
	public Node getRightChild () {
		return this.rightChild; 
	}
	
	/**
	 * 
	 * @return the parent of the node 
	 */
	public Node getParent() {
		return this.parent; 
	}
	
	/**
	 * Checks to see if the current node is a leaf (no children)
	 * @return true if it is a leaf, false otherwise 
	 */
	public boolean isLeaf() {
		if (this.leftChild==null && this.rightChild==null) return true; 
		else return false; 
	}
	
	/**
	 * 
	 * @param leftChild the new left child of the node
	 */
	public void setLeftChild(Node leftChild) {
		this.leftChild=leftChild; 
	}
	
	/**
	 * 
	 * @param rightChild the new right child of the node
	 */
	public void setRightChild(Node rightChild) {
		this.rightChild=rightChild; 
	}
	
	/**
	 * 
	 * @param parent the new parent of the node 
	 */
	public void setParent(Node parent) {
		this.parent=parent; 
	}
	
	/**
	 * 
	 * @param data the new record of the node
	 */
	public void setData(Record data) {
		this.data=data;
	}
	
	
}

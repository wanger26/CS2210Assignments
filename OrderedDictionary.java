/**
 * This class implements an ordered dictionary using a binary search tree
 * @author Jakob Wanger
 *
 */

public class OrderedDictionary{
	
	private Node root; 
	
	/**
	 * Creates a Ordered Dictionary
	 */
	public OrderedDictionary() {
		root= new Node(); 
	}
	
	
	
	/**
	 * Returns the Record object with key k, or it returns null if such a record is not in the dictionary.
	 * @param k the key for which to look for in dictionary 
	 * @return the record object with the specified k or null if the record was not found
	 */
	public Record get (Pair k) {
		return getRecursive(root, k).getData(); //calling on recursive helper method to find the node with the key 
	}
	
	/**
	 * Inserts r into the ordered dictionary. It throws a DictionaryException if a record with the same key 
	 * as r is already in the dictionary.
	 * @param r the record to insert into the dictionary
	 * @throws DictionaryException if there is already an entry of the key in the dictionary
	 */
	public void put (Record r) throws DictionaryException{
		Node location= getRecursive(root, r.getKey()); //calling on recursive helper method to find where to insert
		
		if (!location.isLeaf()) throw new DictionaryException();  //if the location of where to insert is a key it must be duplicate therefore throw exception 
		else if (location.isLeaf()) { //else we can enter the key into the leaf
			location.setData(r);
			location.setLeftChild(new Node(null, null, null, location)); //adding empty leaf nodes to keep binary property intact
			location.setRightChild(new Node(null, null, null, location));
		}
	
	}
	
	/**
	 * Removes the record with key k from the dictionary. It throws a DictionaryException if the record is not in the dictionary.
	 * @param k
	 * @throws DictionaryException
	 */
	public void remove (Pair k) throws DictionaryException{
		if (!removeRecursive(root, k)) throw new DictionaryException(); //calls on helper method to remove a node. If returned false means could not remove node therefore must not be in the dictionary therefore throw excpetion
	}
	
	/**
	 * Returns the successor of k (the record from the ordered dictionary with
	 * smallest key larger than k); it returns null if the given key has no
	 * successor. The given key DOES NOT need to be in the dictionary.
	 * @param k the key of which to find its successor
	 * @return the successor of the given key or null if it does not have a successor
	 */
	public Record successor (Pair k) { 
		Node result= successorRecursive(root, k); //calling on helper method to find the successor of the given node recursively  
		if (result==null) return null; //if the result is null return null
		else return result.getData(); //other wise return the Record of the successor
	}

	
	/**
	 * Returns the predecessor of k (the record from the ordered dictionary with
	 * largest key smaller than k; it returns null if the given key has no
	 * predecessor. The given key DOES NOT need to be in the dictionary.
	 * @param k the key of which to find its predecessor 
	 * @return the predecessor of the given key or null if it does not have a predecessor
	 */
	public Record predecessor (Pair k) {
		Node result= predecessorRecursive(root, k); //Calling on helper method to find the predecessor of the given node recursively
		if (result==null) return null; //if the result is null then return null
		else return result.getData();  //other wise return the record of the successor
	}
	
	/**
	 * Returns the record with smallest key in the ordered dictionary. Returns null if the dictionary is empty.
	 * @return record with smallest key, or null if the dictionary is empty
	 */
	public Record smallest () { 
		Node result= smallestRecursive(root); //Calling on helper method to find the smallest key in the dictionary recursively 
		if (result==null) return null; //if the result is null then return null
		else return result.getData(); //other wise return the record of the smallest 
	}
	
	/**
	 * Returns the record with largest key in the ordered dictionary. Returns null if the dictionary is empty.
	 * @return record with the largest key, or null if the dictionary is empty
	 */
	public Record largest () {
		Node result= largestRecursive(root); //Calling on helper method to find the largest key in the dictionary recursively
		if (result==null) return null; //if the result is null then return null
		else return result.getData(); 	//other wise return the record of the largest
	}

	//-------------------------------------------------- Helper Methods------------------------------------------------------------
	
	//Finds a node containing a given key, if not found returns where the node should have been 
	private Node getRecursive(Node current, Pair key) {
		if (current.isLeaf()) return current; 
		else {
			if (current.getData().getKey().compareTo(key)==0) return current; //if they are equal we found the node
			else if (current.getData().getKey().compareTo(key)<0) return getRecursive(current.getRightChild(), key);
			else return getRecursive (current.getLeftChild(), key); 
		}
	}
	
	//Finds a node containing smallest key in the tree
	private Node smallestRecursive(Node current) {
		if (current.isLeaf()) return null; 
		else {
			while (!current.isLeaf()) { //keep going left till we can go no more to find the smallest key 
				current=current.getLeftChild(); 
			}
			return current.getParent(); 
		}
	}
	
	//Finds a node conatining largest key in the tree
	private Node largestRecursive(Node current) {
		if (current.isLeaf()) return null; 
		else {
			while (!current.isLeaf()) { //keep going right till we can go no more to find the largest key
				current=current.getRightChild(); 
			}
			return current.getParent(); 
		}
	}
	
	//Removing a node with a given key in the tree
	private boolean removeRecursive(Node current, Pair k) {
		Node toBeRemoved=getRecursive(root, k); //finding the node which to remove 
		
		if (toBeRemoved.isLeaf()) return false; //if its a leaf the key is not in the tree
		else {
			if (toBeRemoved.getLeftChild().isLeaf() || toBeRemoved.getRightChild().isLeaf()) { //if it has a child that is a leaf we can remove the node by replacing it with one of its children 
				Node parent= toBeRemoved.getParent(); 
				Node otherChild; 
				if (toBeRemoved.getLeftChild().isLeaf()) {
					otherChild= toBeRemoved.getRightChild(); 
				}
				else{
					otherChild= toBeRemoved.getLeftChild(); 
				}
				
				if (toBeRemoved==this.root) {
					otherChild.setParent(null);
					this.root=otherChild; 
				}
				else {
					if (parent.getRightChild()==toBeRemoved) parent.setRightChild(otherChild);
					else parent.setLeftChild(otherChild);
				}
				return true; 
			}//if
			else { //else we need to find the smallest key in the right sub tree to replace the to be removed node 
				Node smallest= smallestRecursive(toBeRemoved.getRightChild()); 
				toBeRemoved.setData(smallest.getData());
				return removeRecursive (smallest, smallest.getData().getKey()); //get rid of duplicate 
			}//else
		}//else 
	}//method 
	
	//Finds its successor of a given node
	private Node successorRecursive(Node r, Pair k) {
		if (r.isLeaf()) return null; //if its a leaf we dont have a successor
		else {
			Node current= getRecursive(r, k); 
			
			if (!current.isLeaf() && !current.getRightChild().isLeaf()) return smallestRecursive(current.getRightChild()); 
			else {
				Node parent= current.getParent(); 
				
				while (current!=this.root && parent.getRightChild()==current) {
					current=parent; 
					parent= current.getParent(); 
				}//while
				
				if (current==this.root) return null; 
				else return parent; 
			}//else
		}//else
	}//method 
	
	//Finds its predecessor of a given node 
	private Node predecessorRecursive(Node r, Pair k) {
		if (r.isLeaf()) return null; 
		else {
			Node current= getRecursive(r, k); 
			
			if (!current.isLeaf() && !current.getLeftChild().isLeaf()) return smallestRecursive(current.getLeftChild());
			else {
				Node parent= current.getParent(); 
				
				while (current!=this.root && parent.getLeftChild()==current) {
					current=parent; 
					parent= parent.getParent(); 
				}//while
				
				if (current==this.root) return null; 
				else return parent; 
			}//else
		}//else
	}//method 
}
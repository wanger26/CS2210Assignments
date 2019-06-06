/**
 * This class represents the key attribute of a record in the ordered dictionary
 * @author Jakob Wanger
 *
 */
public class Pair {

	private String word;
	private String type; 
	
	/**
	 * Creates a pair object configured of a word and a type
	 * @param word the word of the pair object
	 * @param type the type of the pair object
	 */
	public Pair(String word, String type) {
		this.word=word.toLowerCase(); 
		this.type=type; 
	}
	
	/**
	 * Returns the word stored in pair object
	 * @return word stored in pair object
	 */
	public String getWord() {
		return this.word; 
	}
	
	/**
	 * Returns the type stored in pair object 
	 * @return type stored in pair object
	 */
	public String getType() {
		return this.type; 
	}
	
	/**
	 * Compares two keys in lexicographical order
	 * @param k the other key to be compared too 
	 * @return -1 if Key k is greater, 0 if the two keys are equal, 1 if Key k is smaller
	 */
	public int compareTo(Pair k) {
		int equal= this.word.compareTo(k.getWord()); 
		if (equal>0) return 1; 
		else if (equal<0) return -1; 
		else {
			equal= this.type.compareTo(k.getType());
			if (equal>0) return 1; 
			else if (equal<0) return -1; 
			else return 0; 
		}
	}
	
	
}

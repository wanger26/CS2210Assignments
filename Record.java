/**
 * This class represents a record in the ordered dictionary
 * @author Jakob Wanger 
 *
 */
public class Record {

	private Pair key; 
	private String data; 
	
	/**
	 * Initializes a new Record object with the specified key and data
	 * @param key is the key of the record 
	 * @param data is the data of the record
	 */
	public Record (Pair key, String data) {
		this.key=key; 
		this.data=data; 
	}
	
	/**
	 * Returns the key in the Record 
	 * @return key in the Record 
	 */
	public Pair getKey() {
		return this.key; 
	}
	
	/**
	 * Returns the data in the Record 
	 * @return data in the Record 
	 */
	public String getData() {
		return this.data; 
	}
	
}

/**
 * 
 * @author Jakob Wanger
 *
 */
public class DictionaryException extends Exception {
	public DictionaryException() {
		super("Duplicate item or no such item in dictionary");
	}
}

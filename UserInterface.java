/**
 * 
 * @author Jakob Wanger
 * Will provide a simple text-based user interface that will allow users to interact with 
 * the ordered dictionary through the use of the following commands
 */


import java.io.*;
import java.util.*;

public class UserInterface {

	public static void main (String[]args) {
		String fileName= args[0], line;
		OrderedDictionary dict= new OrderedDictionary(); 


		try {
			readInFile(fileName, dict); //calling helper method to fill dictionary
		} catch (IOException e) {
			e.printStackTrace();
		}

		do {
			StringReader keyboard = new StringReader();
			line = keyboard.read("Enter next command: ");

			String [] splitedInput= line.split("\\s+"); //splitting the line at any spaces or multiple sequential spaces  
			if (splitedInput.length==2 && splitedInput[0].equalsIgnoreCase("get")) { //if the command is get followed by a word 
				get(splitedInput[1], dict); //calling on helper method
			}
			else if (splitedInput.length==3 && splitedInput[0].equalsIgnoreCase("delete")) { //if the command is delete followed by word and a type
				delete(splitedInput[1], splitedInput[2], dict); //calling on helper method
			}
			else if (splitedInput.length==4 && splitedInput[0].equalsIgnoreCase("put")) { // if the command is put followed by a word, key and data
				put(splitedInput[1], splitedInput[2], splitedInput[3], dict); //calling on helper method
			}
			else if (splitedInput.length==2 && splitedInput[0].equalsIgnoreCase("list")) { //if the command is list followed by a prefix 
				list(splitedInput[1], dict); //calling on helper method
			}
			else if (splitedInput.length==1 && splitedInput[0].equalsIgnoreCase("smallest")) { //if the command is smallest 
				Record smallest= dict.smallest();
				System.out.println(smallest.getKey().getWord()+", "+smallest.getKey().getType()+", "+smallest.getData()); 
			}
			else if (splitedInput.length==1 && splitedInput[0].equalsIgnoreCase("largest")) { //if the command is largest
				Record smallest= dict.largest();
				System.out.println(smallest.getKey().getWord()+", "+smallest.getKey().getType()+", "+smallest.getData());
			}
			else if (splitedInput.length==1 && splitedInput[0].equalsIgnoreCase("finish")) { //if the command is finish 
				break; 
			}
			else { //if its none of the above its invalid input
				System.out.println("Invalid Input. Please try again");
			}
		}while (true);
	}

	//-------------------------------------------Helper Methods--------------------------------------------------------------------------------------

	/**
	 * This method will perform the user command delete
	 * @param word the word to delete
	 * @param type the type of the word to delete 
	 * @param dict the dictionary from which to delete it from
	 */
	private static void delete (String word, String type, OrderedDictionary dict) {
		try {
			dict.remove(new Pair (word, type));
		} catch (DictionaryException e) {
			System.out.println("No record in the ordered dictionary has key ("+word+", "+type+")"); //if exception is thrown we know the key is not in the dictionary  
		}
	}

	/**
	 * This method will perform the user command get 
	 * @param word the word to get from the dictionary 
	 * @param dict the dictionary from which to get the word
	 */
	private static void get(String word, OrderedDictionary dict) {

		Record foundRecord; //the record found with the specified key
		boolean atLeastOneFound=false; //used to check to see if at least one record is found with the specified word 

		foundRecord= dict.get(new Pair (word,"text"));
		if (foundRecord !=null) { //if its not null means there is an entry 
			System.out.println(foundRecord.getData());
			atLeastOneFound=true; 
		}
		foundRecord= dict.get(new Pair (word,"audio"));
		if (foundRecord !=null) {
			atLeastOneFound=true; 
			SoundPlayer player= new SoundPlayer(); //play the sound
			try {
				player.play(foundRecord.getData());
			} catch (MultimediaException e) {
				e.printStackTrace();
			}
		}
		foundRecord= dict.get(new Pair (word,"image"));
		if (foundRecord !=null) {
			atLeastOneFound=true; 
			PictureViewer picture= new PictureViewer (); //show the picture 
			try {
				picture.show(foundRecord.getData());
			} catch (MultimediaException e) {
				e.printStackTrace();
			}
		}
		if (!atLeastOneFound) { //if we did not find at least one print the appropriate messages
			System.out.println("The word "+word+" is not in the ordered dictionary");
			System.out.print("Preceding word: ");
			Record temp= dict.predecessor(new Pair (word,"")); 
			if (temp!=null) System.out.print(temp.getKey().getWord());
			temp= dict.successor(new Pair (word,"")); 
			System.out.print("\nFollowing word: "); 
			if (temp!=null) System.out.print(temp.getKey().getWord());
			System.out.println();
		}
	}
	/**
	 * This method will perform the user command list
	 * @param prefix the prefix for which to find the start of a word entry in the dictionary 
	 * @param dict the dictionary to find the prefixes for 
	 */
	private static void list(String prefix, OrderedDictionary dict) {
		int counter=0; 
		Record currentRecord=dict.successor(new Pair (prefix, "")); //finding the smallest prefix word  
		while (currentRecord.getKey().getWord().startsWith(prefix)) { //keep looping till the words are no longer prefixes 
			if (counter!=0)System.out.print(", "); //if we are not the first word print a , to separate words 
			System.out.print(currentRecord.getKey().getWord());
			currentRecord= dict.successor(currentRecord.getKey()); //get next successor
			counter++;
		}
		if (counter==0) System.out.println("No words in the ordered dictionary start with prefix "+prefix); //if we couldnt find one prefix word print appropriate message
		else System.out.println();

	}

	/**
	 * This method will perform the user command put
	 * @param word the word part of the key to enter into the dictionary 
	 * @param type the type part of the key to enter into the dictionary 
	 * @param data the data type of the record to enter into the dictionary  
	 * @param dict the dictionary in which to enter the record 
	 */
	private static void put (String word, String type, String data, OrderedDictionary dict) {
		try {
			dict.put(new Record(new Pair (word, type),data));
		} catch (DictionaryException e) { //if an excpetion is thrown it means this is a duplicate key, therefore print appropriate message 
			System.out.println("A record with the given key ("+word+","+type+") us already in the ordered dictionary");
		}
	}

	/**
	 * This method reads in a file and then enters its contents into a dictionary 
	 * @param fileName the filename of which to get the information from
	 * @param dict the dictionary in which to enter the information
	 * @throws IOException if file cannot be opened 
	 */
	private static void readInFile(String fileName, OrderedDictionary dict) throws IOException {
		int lineNumber=0;  
		String word=""; 

		BufferedReader br = new BufferedReader(new FileReader(fileName)); //will be used to read the file line by line

		String readLine; 
		while ((readLine = br.readLine()) != null) {//if we have not reach the end of the file
			lineNumber++; 
			if (lineNumber%2==0) { //if its an even line number we know that the file will provide data for the record 
				String type;
				String splitString[]= readLine.split("\\."); //going to split the read in line where a dot "." is 
				if (splitString.length==2) { //if there is only one dot
					if (splitString[1].equals("jpg") || splitString[1].equals("gif")) type="image"; //and right of the dot is gif or jpg we know its a image file
					else if (splitString[1].equals("wav") || splitString[1].equals("mid")) type="audio"; //similarly if its mid or wave we know its a audio file
					else type="text"; //otherwise its text
				}
				else { //if there is more then one dot or no dots in the line it must be of type text
					type="text"; 
				}

				//Now lets enter the record since we know the type, word (line before this read in one) and data
				try {
					dict.put(new Record (new Pair(word, type), readLine));
				} catch (DictionaryException e) {
					e.printStackTrace();
				}
			}//if
			else { //else we know its the word of the key component of record
				word=readLine; 
			}//else
		}//while
	}//method 

}//class

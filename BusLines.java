/**
 * @author Jakob Wanger
 * This class represents the city map with bus lines
 */

import java.io.*;
import java.util.Iterator;
import java.util.Stack;

public class BusLines {

	private Graph map; 
	private GraphNode start; 
	private GraphNode dest; 
	private int changes; 
	private Stack<GraphNode> path; 

	/**
	 * Constructor for building a city map with its bus lines from the input file.
	 * @param inputFile
	 * @throws MapException If the input file does not exist
	 */
	public BusLines(String inputFile) throws MapException{
		BufferedReader br; 
		int width=0; //the width of the city map
		int length=0; //the length of the city map
		path= new Stack<GraphNode>(); //the path that is taken from the start to the destination

		try {
			br = new BufferedReader(new FileReader(inputFile)); //using buffered reader to read in from the file
		} catch (FileNotFoundException e) {
			throw new MapException("Cannot open file"); 
		} 

		String readLine=null; //the line read in from the file using the buffered reader 
		try {
			readLine = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		} 

		if (readLine!=null) { //if the file is not empty read the first line and store the cities characteristics 
			String splitString[]= readLine.split("\\s"); 
			width= Integer.parseInt(splitString[1]); 
			length= Integer.parseInt(splitString[2]); 
			this.changes= Integer.parseInt(splitString[3]); 
		}

		this.map= new Graph(width*length); //creating map 

		try {
			fillMap(br, width, length); //calling on helper method fill the map 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the graph 
	 * @return the graph
	 */
	public Graph getGraph() {
		return this.map; 
	}

	/**
	 * Returns a Java Iterator containing the nodes along the path from the starting point to the destination, if such a path exists.
	 * @return a Java Iterator containing the nodes along the path from the starting point to the destination. If there is no path 
	 * return null
	 */
	public Iterator<GraphNode> trip(){
		try {
			pathFinder(this.start, this.dest, this.changes, '\0'); //calling on helper method to find a path from the start to the destination with the given number of bus changes
		} catch (GraphException e) {
			e.printStackTrace();
		} 

		if (this.path.isEmpty()) { //if there is no path return null
			return null; 
		}
		else{
			return this.path.iterator(); //else return the iterator
		}


	}

	//-------------------------------------------------------------------------------------------
	//--------------------------------------Helper Methods --------------------------------------
	//-------------------------------------------------------------------------------------------

	//This helper method will help to fill in the map from the provided file
	private void fillMap(BufferedReader br, int width, int length) throws IOException, GraphException{
		int counter=0;
		String line1=br.readLine(); 
		String line2=br.readLine(); 

		while (line1!=null) {
			for (int i=0; i<line1.length(); i++) {
				if (i%2 == 0) {
					GraphNode node,node2; 
					try {node= map.getNode(counter);} //checking to see if the nodes have already been created and are already in map 
					catch(Exception e) {node= new GraphNode(counter);} //if not we need to create them
					try {node2= map.getNode(counter+width);}
					catch(Exception e) {node2= new GraphNode(counter+width);}
					
					if (line1.charAt(i)=='S') { //if the node is the start store it as the start
						this.start=  node; 
					}
					else if (line1.charAt(i)=='D') { //if the node is the destination then store it as dest
						this.dest=node; 
					}
					if ( line2!=null && line2.charAt(i)!=' ') { //if line 2 is not null (reached the end of file) and there is a bus line in line 2 
						map.insertEdge(node, node2, line2.charAt(i));
					}
				}
				else if (i%2 != 0) {
					GraphNode node, node2; 
					try {node= map.getNode(counter);} //checking to see if the nodes have already been created and are already in the map
					catch(Exception e) {node= new GraphNode(counter);} //if not we need to create them
					try {node2= map.getNode(counter+1);}
					catch(Exception e) {node2= new GraphNode(counter+1);}
					if (line1.charAt(i)!=' ') { //if there is a bus line
						map.insertEdge(node, node2, line1.charAt(i));
					}
					counter++; 
				}

			}
			line1=br.readLine(); //reading the next lines
			line2=br.readLine(); 
			counter++; 
		}
	}

	//This is a helper method to find a path from s to t with a given number of bus changes 
	private boolean pathFinder(GraphNode s, GraphNode t, int changes, char lastLine) throws GraphException {
		s.setMark(true);
		path.push(s); //push s on the path stack

		if (s.getName()==this.dest.getName() && changes>=0) { //if we have reached the destination and we have not violated the bus change restriction we have found the path
			return true; 
		}
		else {
			Iterator<GraphEdge> itr= map.incidentEdges(s); 
			while (itr.hasNext() && changes>=0) { //keeping looping till we dont have anymore edges for s or we have exceeded our bus change restriction
				GraphEdge u= itr.next();
				boolean mustChange=false; //will be used to direct a bus change or not
				if (lastLine=='\0') lastLine=u.getBusLine(); 
				else if (lastLine!=u.getBusLine()) {
					mustChange=true; 
				}

				if (!u.firstEndpoint().getMark()) {
					if (mustChange) { //if we have to change busses we need to change our changes restriction to 1 less and add the new bus line
						if (pathFinder(u.firstEndpoint(), t, (changes-1), u.getBusLine())) return true; 
					}
					else {
						if (pathFinder(u.firstEndpoint(), t, changes, lastLine)) return true; 
					}
				}
				else if (!u.secondEndpoint().getMark()) {
					if (mustChange) { //if we have to change busses we need to change our changes restriction to 1 less and add the new bus line
						if (pathFinder(u.secondEndpoint(),t, (changes-1), u.getBusLine())) return true; 
					}
					else {
						if (pathFinder(u.secondEndpoint(),t, changes, lastLine)) return true; 
					}
				}
			}
			GraphNode node= path.pop(); //if we reach this point the node cannot be in the solution
			node.setMark(false); //set the mark to false so we can backtrack and find a solution
			return false; 
		}

	}

}


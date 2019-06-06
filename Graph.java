/**
 * @author Jakob Wanger
 * This class implements a undirected graph using an adjacency list
 */

import java.util.Iterator;
import java.util.LinkedList;

public class Graph implements GraphADT{

	private LinkedList adjacencyList []; 

	public Graph(int n) {

		adjacencyList= new LinkedList [n]; 

		for (int index=0; index<n; index++) {
			adjacencyList[index]= new LinkedList(); 
			adjacencyList[index].add(new GraphNode(index)); 
		}
	}

	/**
	 * Adds to the graph an edge connecting the given vertices
	 * @param u the first node to get an edge with the second 
	 * @param v the second node to get an edge with the first 
	 * @param busLine the bus line character of the new edge
	 * @throws GraphException if either node does not exist or if the edge is already in the graph
	 */
	public void insertEdge(GraphNode u, GraphNode v, char busLine) throws GraphException{
		if (u.getName()<adjacencyList.length && v.getName()<adjacencyList.length && !contains(new GraphEdge(u,v,busLine), u.getName())) { //making sure that vertex's have valid names and that they are not already in the graph
			adjacencyList[u.getName()].add(new GraphEdge(u,v,busLine)); 
			adjacencyList[v.getName()].add(new GraphEdge(u,v,busLine)); 
		}
		else {
			throw new GraphException("Not a valid insert"); 
		}
	}

	/**
	 * Returns the node with the specified name.
 	* @param name the name of the node to get 
 	* @throws GraphException if the node does not exist.
 	* @return GraphNode the node with the specified name
 	*/
	public GraphNode getNode(int name) throws GraphException {
		if (name<adjacencyList.length) //making sure it is valid name before looking for the node
			return (GraphNode)adjacencyList[name].getFirst();
		else throw new GraphException("Not a valid node"); 
	}

	/**
	 * Returns a Java Iterator storing all the edges incident on the specified node.
	 * @param u the node from which to get its edge's
	 * @throws GraphException if the node does not exist
	 * @return Iterator<GraphEdge> null if the node does not have any edges incident on it
	 */
	public Iterator <GraphEdge> incidentEdges(GraphNode u) throws GraphException{
		if (u.getName()>=adjacencyList.length) throw new GraphException("Node does not exits"); //if it is a invalid name throw the exception
		else {
			Iterator <GraphEdge> itr = (Iterator <GraphEdge>)adjacencyList[u.getName()].iterator();
			if (itr.next()==null) return null;  //if the iterator has no values return null
			return itr; //else return the iterator 
		}
	}

	/**
	 * Returns the edge connecting the given vertices.
	 * @param u one of the two given vertices
	 * @param v the other of the two given vertices
	 * @throws GraphException if there is no edge connecting the given vertices or if u or v do not exist.
	 * @return the edge connecting the given vertices.
	 */
	public GraphEdge getEdge (GraphNode u, GraphNode v) throws GraphException {
		if (u.getName()<adjacencyList.length && v.getName()<adjacencyList.length && contains(new GraphEdge(u,v,'1'), u.getName())) { //checking to see if the names of the vertices are valid and that they are already in the graph
			for (int x=1; x<adjacencyList.length; x++) {
				if (((GraphEdge)adjacencyList[u.getName()].get(x)).firstEndpoint()==u && ((GraphEdge)adjacencyList[u.getName()].get(x)).secondEndpoint()==v || //checking to make sure the end points of the edge match. OR if the end points are reversed = still valid
						((GraphEdge)adjacencyList[u.getName()].get(x)).firstEndpoint()==v && ((GraphEdge)adjacencyList[u.getName()].get(x)).secondEndpoint()==u) //because it is a undirected graph so all valid answers are covered 
					return (GraphEdge)adjacencyList[u.getName()].get(x);
			}
		}
		throw new GraphException("No such edge exists");
	}

	/**
	 * Returns true if two vertex are adjacent
	 * @param u one of two the two vertex's
	 * @param v the other of the vertex's
	 * @throws GraphException if either vertex does not exist
	 * @return Returns true is u and v are adjacent, and false otherwise.
	 */
	public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException{
		if (u.getName()<adjacencyList.length &&  v.getName()<adjacencyList.length) //making sure the vertix's have valid names before looking for them 
			return contains(new GraphEdge (u,v,'1'), u.getName());
		else throw new GraphException("Vertex does not exits"); 
	}

	//---------------------------------------------------------------------------------------------------------------
	//-------------------------------------------Helper Methods------------------------------------------------------
	//---------------------------------------------------------------------------------------------------------------
	
	//This helper method helps to check if a graph contains a given edge. It returns true if it does, and false otherwise
	private boolean contains(GraphEdge edge, int location) {
		for (int x=1; x<adjacencyList[location].size(); x++) {
			if (((GraphEdge)adjacencyList[location].get(x)).firstEndpoint()==edge.firstEndpoint() && ((GraphEdge)adjacencyList[location].get(x)).secondEndpoint()==edge.secondEndpoint())
				return true; //if the end points of the in the map match that of the edge passed in. Therefore can conclude the edge is in the graph
			if (((GraphEdge)adjacencyList[location].get(x)).firstEndpoint()==edge.secondEndpoint() && ((GraphEdge)adjacencyList[location].get(x)).secondEndpoint()==edge.firstEndpoint())
				return true; //same but just in reverse order to cover all possibilities since it is a undirected graph
		}
		return false; 
	}




}
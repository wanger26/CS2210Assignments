/**
 * 
 * @author Jakob Wanger
 * This class implements an edge of a graph
 */

public class GraphEdge {
	
	private GraphNode firstEndPoint; 
	private GraphNode secondEndPoint; 
	private char busLine; 
	
	/**
	 * Creates a GraphEdge with 2 end points and a BusLine identifier
	 * @param u the first end point
	 * @param v the second end point 
	 * @param busLine the bus line identifier 
	 */
	public GraphEdge(GraphNode u, GraphNode v, char busLine) {
		this.firstEndPoint=u; 
		this.secondEndPoint=v;
		this.busLine=busLine; 
	}
	
	/**
	 * Returns the first end point of a GraphEdge
	 * @return the firstEndpoint 
	 */
	public GraphNode firstEndpoint() {
		return this.firstEndPoint; 
	}
	
	/**
	 * Returns the second end point of a GraphEdge
	 * @return the secondEndpoint
	 */
	public GraphNode secondEndpoint() {
		return this.secondEndPoint; 
	}
	
	/**
	 * Returns the bus line identifier
	 * @return the BusLine
	 */
	public char getBusLine() {
		return this.busLine; 
	}
}

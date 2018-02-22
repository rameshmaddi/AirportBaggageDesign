/**
 * 
 */
package com.design.airportbag.program.baggage.route;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author rameshmaddi
 *
 */
public abstract class Graph<T, E extends Edge<T>> {

	private int defaultSize = 100;
	private final Set<E> graphEdge = new LinkedHashSet<>(defaultSize);
	


	private final Map<Object, Node<T>> graphNode = new HashMap<>(defaultSize);
	
	
	public void addEdge(E edge){
		if(edge.getFirstNode() == null || edge.getSecondNode()== null){
			throw new IllegalArgumentException("Null Nodes are there");
		}
		
		if(!graphEdge.contains(edge)){
			graphEdge.add(edge);
		}
		
		addNodesFromEdges(edge);
		
	}


	protected void addNodesFromEdges(E edge) {
		
		// In the Map Maintain the Node ID and Node relation
		//check if First Node id exist
		if(!graphNode.containsKey(edge.getFirstNode().getNodeId())){
			graphNode.put(edge.getFirstNode().getNodeId(), edge.getFirstNode());
		}
		//Check if second node id exist
		if(!graphNode.containsKey(edge.getSecondNode().getNodeId())){
			graphNode.put(edge.getSecondNode().getNodeId(), edge.getSecondNode());
		}
		
		//Get Node first and then add Edge to it.
		// makes it easy to determines the Edge for the node
		graphNode.get(edge.getFirstNode().getNodeId()).addEdge(edge);
		graphNode.get(edge.getSecondNode().getNodeId()).addEdge(edge);
		
	}


	public Map<Object, Node<T>> getGraphNode() {
		return graphNode;
	}
	
	/**
	 * Get the list of Graph and nodes.
	 */
	public void forEachNode( Consumer<Node<T>> consumer ) {
		getGraphNode().forEach( ( nodeId, node ) -> consumer.accept( node ) );
	}


	/**
	 * Find a node in the graph by the node id.
	 */
	public Node<T> getNode( Object nodeId ) {
		return getGraphNode().get( nodeId );
	}
	
	public Collection<E> getGraphEdge() {
		return graphEdge;
	}
	
	/**
	 * Cleanup after use.
	 */
	public void cleanup() {
		getGraphEdge().clear();
		getGraphNode().clear();
	}
}

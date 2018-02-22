/**
 * 
 */
package com.design.airportbag.program.baggage.route;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.design.airportbag.program.baggage.domain.Identifiable;

/**
 * @author rameshmaddi
 *
 *	This will contain total weight of a node 
 * @param <T> Type of node
 */
public class Node<T> {
	
	private final T nodeItem;
	private final Object nodeId;
	private final Set<Edge<T>> edges = new HashSet<>( 10 );

	public Node( T nodeItem) {
		if (nodeItem == null){
			throw new IllegalArgumentException("Null Node Pass"); 
		}
		
		this.nodeItem = nodeItem;
		this.nodeId = nodeItem instanceof Identifiable ? ((Identifiable<?>) nodeItem).getId() : nodeItem.toString();
	}
	
	/**
	 * Add an edge connected to this node.
	 */
	public <E extends Edge<T>> void addEdge( E edge ) {
		if ( ! edges.contains( edge ) ) {
			edges.add( edge );
		}
	}


	/**
	 * Get the attached edges.
	 */
	public Collection<Edge<T>> getEdges() {
		return edges;
	}


	/**
	 * Getter for the node identifier.
	 */
	public Object getNodeId() {
		return nodeId;
	}


	/**
	 * Getter for the node data.
	 */
	public T getNodeItem() {
		return nodeItem;
	}


	@Override
	public String toString() {
		return getNodeId() != null ?
			   getNodeId().toString() :
			   super.toString();
	}


	@Override
	public boolean equals( Object o ) {
		return o != null &&
				o instanceof Node &&
				getNodeId() != null &&
				getNodeId().equals( ((Node)o).getNodeId() );
	}

}

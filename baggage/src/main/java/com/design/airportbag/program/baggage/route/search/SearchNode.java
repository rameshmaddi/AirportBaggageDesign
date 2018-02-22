package com.design.airportbag.program.baggage.route.search;

import com.design.airportbag.program.baggage.route.Edge;
import com.design.airportbag.program.baggage.route.Node;

import java.util.Collection;


/**
 * Node decorator that adds some search necessities.
 */
public class SearchNode<T> extends Node<T> implements Comparable<SearchNode<T>> {

	private final Node<T> originalNode;

	private int distance = Integer.MAX_VALUE;
	private Node<T> previous = null;


	/**
	 * Build a node with whatever data you want.
	 */
	public SearchNode( Node<T> node ) {
		super( node == null ? null : node.getNodeItem() );
		this.originalNode = node;
	}


	/**
	 * Get the wrapped node.
	 */
	public Node<T> getNode() {
		return originalNode;
	}


	/**
	 * Set the previous node.
	 */
	public void setPrevious( Node<T> node ) {
		this.previous = node;
	}


	/**
	 * Set the previous node.
	 */
	public Node<T> getPrevious() {
		return previous;
	}


	/**
	 * Distance setter.
	 */
	public void setDistance( int distance ) {
		if ( distance < 0 ) {
			throw new IllegalArgumentException( "Distance cannot be negative" );
		}
		this.distance = distance;
	}


	/**
	 * Distance getter.
	 */
	public int getDistance() {
		return distance;
	}


	/**
	 * Node with infinity value is unreachable.
	 */
	public boolean isUnreachable() {
		return getDistance() == Integer.MAX_VALUE;
	}


	/**
	 * Add an edge connected to this node.
	 */
	public <E extends Edge<T>> void addEdge( E edge ) {
		originalNode.addEdge( edge );
	}


	/**
	 * Get the attached edges.
	 */
	public Collection<Edge<T>> getEdges() {
		return originalNode.getEdges();
	}


	/**
	 * Getter for the node identifier.
	 */
	public Object getNodeId() {
		return originalNode.getNodeId();
	}


	/**
	 * Getter for the node data.
	 */
	public T getNodeItem() {
		return originalNode.getNodeItem();
	}


	@Override
	public String toString() {
		return originalNode.getNodeId() != null ?
			   originalNode.getNodeId().toString() :
			   originalNode.toString();
	}


	@Override
	public boolean equals( Object o ) {
		return o != null &&
				(o instanceof SearchNode || o instanceof Node) &&
				originalNode.getNodeId() != null &&
				originalNode.getNodeId().equals( ((Node)o).getNodeId() );
	}


	@Override
	public int compareTo( SearchNode<T> o ) {
		if ( o == null ) {
			throw new IllegalArgumentException( "Can't compare null search node" );
		}
		return Integer.compare( getDistance(), o.getDistance() );
	}

}

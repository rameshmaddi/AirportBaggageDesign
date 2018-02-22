/**
 * 
 */
package com.design.airportbag.program.baggage.route;

import com.design.airportbag.program.baggage.route.Edge;

/**
 * @author rameshmaddi
 *
 */
public class WeightedEdge<T> implements Edge<T>, Comparable<WeightedEdge<T>> {

	private final Node<T> node1;
	private final Node<T> node2;
	private final int weight;
	
	public WeightedEdge(Node<T> node1, Node<T> node2, int weight) {
		if (weight < 0){
			throw new IllegalArgumentException("Weight Cannot be Zero");
		}
		if(node1 == null || node2 == null 
				|| node1 == node2 || node1.equals(node2)){
			throw new IllegalArgumentException("Nodes can't be null or equals");
		}
		this.weight = weight; 
		
		this.node1 = node1;
		this.node1.addEdge(this);
		
		this.node2 = node2;
		this.node2.addEdge(this);
		
	
	}
	
	@Override
	public Node<T> getFirstNode() {
		return node1;
	}

	@Override
	public Node<T> getSecondNode() {
		return node2;
	}

	@Override
	public Node<T> getOtherNode(Node<T> thisNode) {
		return getFirstNode().equals( thisNode ) ? getSecondNode() : getFirstNode();
	}

	@Override
	public int compareTo(WeightedEdge<T> o) {
		return Integer.compare( getWeight(), o.getWeight() );
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		return obj != null &&
				obj instanceof Edge &&
				getFirstNode () != null &&
				getSecondNode() != null &&
				((getFirstNode().equals( ((Edge<T>) obj).getFirstNode() ) &&
				getSecondNode().equals( ((Edge<T>) obj).getSecondNode())) ||
				(getFirstNode().equals( ((Edge<T>) obj).getSecondNode() ) &&
				getSecondNode().equals( ((Edge<T>) obj).getFirstNode()) ));
	}

}

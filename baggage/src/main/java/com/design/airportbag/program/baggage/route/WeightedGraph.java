/**
 * 
 */
package com.design.airportbag.program.baggage.route;

/**
 * @author rameshmaddi
 * @param <T>
 *
 */
public class WeightedGraph<T> extends Graph<T, WeightedEdge<T>> {
	
	public void addEdge(Node<T> firstNode,Node<T> secondNode, int weight) {
		if(weight < 0){
			throw new IllegalArgumentException("Weight Cannot be less than Zero");
		}
		if(firstNode == null || secondNode == null){
			throw new IllegalArgumentException("Null Nodes");
		}
		
		if(!getGraphNode().containsKey(firstNode.getNodeId())){
			getGraphNode().put(firstNode.getNodeId(), firstNode);
		}
		
		if (!getGraphNode().containsKey(secondNode.getNodeId())) {
			getGraphNode().put(secondNode.getNodeId(), secondNode);
		}
		
		Node<T> first = getGraphNode().get(firstNode.getNodeId());
		Node<T> second = getGraphNode().get(secondNode.getNodeId());
		
		WeightedEdge<T> edge = new WeightedEdge<>(firstNode, secondNode, weight);
		
		first.addEdge(edge);
		second.addEdge(edge);
		
		if ( ! getGraphEdge().contains( edge ) ) {
			getGraphEdge().add( edge );
		}

		addNodesFromEdges( edge );
	}

}

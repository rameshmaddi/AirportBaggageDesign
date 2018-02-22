package com.design.airportbag.program.baggage.route.search;

import com.design.airportbag.program.baggage.route.Node;


/**
 * Identify an object as being search able. The object should be
 * able to accept a search strategy.
 */
public interface Searchable<T> {

	/**
	 * Search the graph for the optimal path between nodes. The search will be done with
	 * whatever search strategy is specified.
	 *
	 * @param startNode node from where to start the search
	 * @param endNode destination node to search for
	 * @return the node path starting with startNode, and ending with endNode.
	 */
	NodePath<T> findOptimalPath( Node<T> startNode, Node<T> endNode );

}

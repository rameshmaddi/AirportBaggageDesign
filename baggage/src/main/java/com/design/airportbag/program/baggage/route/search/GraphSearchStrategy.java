/**
 * 
 */
package com.design.airportbag.program.baggage.route.search;

import com.design.airportbag.program.baggage.route.Node;
import com.design.airportbag.program.baggage.route.search.NodePath;
import com.design.airportbag.program.baggage.route.Edge;
import com.design.airportbag.program.baggage.route.Graph;

/**
 * @author rameshmaddi
 * @param <G>
 * @param <T>
 *
 */
public interface GraphSearchStrategy< T, G extends Graph<T, ? extends Edge<T>>> {

	NodePath<T> findPath( G graph, Node<T> startNode, Node<T> endNode );
}

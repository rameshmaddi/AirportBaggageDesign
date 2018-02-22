package com.design.airportbag.program.baggage.route.search;

import com.design.airportbag.program.baggage.route.Node;
import com.design.airportbag.program.baggage.route.WeightedEdge;
import com.design.airportbag.program.baggage.route.WeightedGraph;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;


/**
 * Weighted graph decorator to add searching.
 */
public class SearchableGraph<T> extends WeightedGraph<T> implements Searchable<T> {

	private final WeightedGraph<T> graph;
	private GraphSearchStrategy<T, WeightedGraph<T>> searchDelegate;


	/**
	 * Wrap the given {@link WeightedGraph} with {@link Searchable} features.
	 * {@code graph} must not be null
	 * @param graph graph to decorate
	 */
	public SearchableGraph( WeightedGraph<T> graph ) {
		if ( graph == null ) {
			throw new IllegalArgumentException( "Null graph" );
		}
		this.graph = graph;
	}


	/**
	 * Set the search strategy to be used.
	 */
	public void setSearchStrategy( GraphSearchStrategy<T, WeightedGraph<T>> searchStrategy ) {
		this.searchDelegate = searchStrategy;
	}


	/**
	 * Getter for search strategy.
	 */
	public GraphSearchStrategy<T, WeightedGraph<T>> getSearchDelegate() {
		return searchDelegate;
	}


	@Override
	public NodePath<T> findOptimalPath( Node<T> startNode, Node<T> endNode ) {
		if ( getSearchDelegate() == null ) {
			throw new IllegalArgumentException( "Search strategy not set" );
		}

		if ( startNode == null || endNode == null ) {
			throw new IllegalArgumentException( "Cannot search null nodes." );
		}

		//	now delegate the searching
		return getSearchDelegate().findPath( graph, startNode, endNode );
	}


	@Override
	public void addEdge( WeightedEdge<T> edge ) {
		graph.addEdge( edge );
	}

	@Override
	public void addEdge( Node<T> first, Node<T> second, int weight ) {
		graph.addEdge( first, second, weight );
	}

	@Override
	public Collection<WeightedEdge<T>> getGraphEdge() {
		return graph.getGraphEdge();
	}

	@Override
	public void forEachNode( Consumer<Node<T>> consumer ) {
		graph.forEachNode( consumer );
	}

	@Override
	public Node<T> getNode( Object nodeId ) {
		return graph.getNode( nodeId );
	}

	@Override
	public Map<Object, Node<T>> getGraphNode() {
		return graph.getGraphNode();
	}

	@Override
	public void cleanup() {
		graph.cleanup();
	}

}

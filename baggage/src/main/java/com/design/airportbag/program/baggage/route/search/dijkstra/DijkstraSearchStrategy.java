package com.design.airportbag.program.baggage.route.search.dijkstra;

import com.design.airportbag.program.baggage.route.Edge;
import com.design.airportbag.program.baggage.route.Node;
import com.design.airportbag.program.baggage.route.WeightedEdge;
import com.design.airportbag.program.baggage.route.WeightedGraph;
import com.design.airportbag.program.baggage.route.search.GraphSearchStrategy;
import com.design.airportbag.program.baggage.route.search.NodePath;
import com.design.airportbag.program.baggage.route.search.SearchNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;


/**
 * Search implementation using Dijkstra's algorithm to find the shortest
 * path between nodes. 
 */
public class DijkstraSearchStrategy<T> implements GraphSearchStrategy<T, WeightedGraph<T>> {

	@Override
	public NodePath<T> findPath( WeightedGraph<T> graph, Node<T> startNode, Node<T> endNode ) {
		if ( graph == null || startNode == null || endNode == null ) {
			throw new IllegalArgumentException( "Cannot search null graph or null nodes." );
		}

		PriorityQueue<SearchNode<T>> nodes = new PriorityQueue<>( graph.getGraphNode().size() );
		initNodes( nodes, graph, startNode );
		buildPath( nodes );
		return collectPathNodes( graph, startNode.getNodeId(), endNode.getNodeId() );
	}

	//	Initialization for queue
	private void initNodes( PriorityQueue<SearchNode<T>> nodes, WeightedGraph<T> graph, Node<T> startNode ) {
		graph.forEachNode( node -> {                         // for each vertex v in Graph:
			SearchNode<T> sn = getOrAddSearchNode( node );   //
			if ( ! node.equals( startNode ) ) {              //   if v != source
				sn.setDistance( Integer.MAX_VALUE );         //       dist[v] <- infinity
				sn.setPrevious( null );                      //       prev[v] <- undefined
			}                                                //   end if
			else {                                           //
				sn.setDistance( 0 );                         // dist[source] <- 0
				sn.setPrevious( node );                      //
			}                                                //
			nodes.add( sn );                                 //   PQ.add( v, dist[v] ) // node is comparable(dist[v])
		});                                                  // end for
	}

	//	find the optimal path
	private void buildPath( PriorityQueue<SearchNode<T>> nodes ) {
		Node<T> v;
		SearchNode<T> su, sv;
		WeightedEdge<T> we;

		while ( ! nodes.isEmpty() ) {                        // while Q is not empty:
			su = nodes.poll();                               //   u = Q.min()
			if ( su.isUnreachable() ) { break; }             //

			for ( Edge<T> edge : su.getEdges() ) {           //   for each
				we = (WeightedEdge<T>)edge;                  //
				v = we.getOtherNode( su.getNode() );         //     neighbor v of u
				sv = getSearchNode( v );                     //
				int uToV = su.getDistance() + we.getWeight();//     alt = dist[u] + length(u, v)
				if (uToV < sv.getDistance() ) {              //     if alt < dist[v]
					nodes.remove( sv );                      //       ( force recalc )
					sv.setDistance( uToV );                  //       dist[v] <- alt
					sv.setPrevious( su.getNode() );          //       prev[v] <- u
					nodes.add( sv );                         //       Q.decrease_priority(v, alt) (dist recalc)
				}                                            //     end if
			}                                                //   end for
		}                                                    // end while
	}

	//	retrieve the path from what's been built above
	private NodePath<T> collectPathNodes( WeightedGraph<T> graph, Object startNodeId, Object endNodeId ) {
		ArrayDeque<Node<T>> deque = new ArrayDeque<>( searchNodes.size() );
		Node<T> startNode = graph.getNode( startNodeId );
		Node<T> endNode = graph.getNode( endNodeId );
		SearchNode<T> node = getSearchNode( endNode );

		//	keep this before we flush the maps
		final int finalDistance = node.getDistance();

		//	walk backwards through the "previous" links
		do {
			deque.push( node.getNode() );
		}
		while ( (node = getSearchNode( node.getPrevious() )) != null && ! node.equals( startNode ) );
		deque.push( startNode );

		clearSearchNodes();
		return new NodePath<>( new ArrayList<>( deque ), finalDistance );
	}


	//	store the previous node links
	private final Map<Object, SearchNode<T>> searchNodes = new HashMap<>( 100 );

	//	clear nodes
	protected void clearSearchNodes() {
		searchNodes.clear();
	}

	//	get node from collection
	protected SearchNode<T> getSearchNode( Node<T> node ) {
		return node == null ? null : searchNodes.get( node.getNodeId() );
	}

	//	if the node isn't in the collection, create/add it
	protected SearchNode<T> getOrAddSearchNode( Node<T> node ) {
		if ( node == null ) {
			throw new IllegalArgumentException( "Null node" );
		}
		if ( ! searchNodes.containsKey( node.getNodeId() ) ) {
			searchNodes.put( node.getNodeId(), node instanceof SearchNode ? (SearchNode)node : new SearchNode<>( node ) );
		}
		return searchNodes.get( node.getNodeId() );
	}

}

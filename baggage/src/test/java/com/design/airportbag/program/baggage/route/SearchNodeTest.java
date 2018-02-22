package com.design.airportbag.program.baggage.route;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.design.airportbag.program.baggage.route.search.SearchNode;


public class SearchNodeTest {

	private Node<String> node1 = new Node<>( "A1" );
	private Node<String> node2 = new Node<>( "A2" );

	@Test (expected = IllegalArgumentException.class)
	public void testNullSearchNode() {
		new SearchNode<>( null );
	}

	@Test
	public void testSearchNodeDistance() {
		SearchNode<String> snode1 = new SearchNode<>( node1 );
		SearchNode<String> snode2 = new SearchNode<>( node2 );

		snode1.setDistance( 4 );
		snode2.setDistance( 2 );

		assertEquals( snode1.compareTo( snode2 ), 1 );
		assertEquals( snode2.compareTo( snode1 ), -1 );
	}

	@Test (expected = IllegalArgumentException.class)
	public void testSearchNodeNullDistance() {
		new SearchNode<>( node1 ).setDistance( -2 );
	}

	@Test (expected = IllegalArgumentException.class)
	public void testSearchNodeNullCompare() {
		new SearchNode<>( node1 ).compareTo( null );
	}

}

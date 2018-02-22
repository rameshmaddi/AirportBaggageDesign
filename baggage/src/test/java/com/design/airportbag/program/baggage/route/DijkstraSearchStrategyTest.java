package com.design.airportbag.program.baggage.route;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Before;
import org.junit.Test;

import com.design.airportbag.program.baggage.domain.TerminalGate;
import com.design.airportbag.program.baggage.route.search.dijkstra.DijkstraSearchStrategy;


public class DijkstraSearchStrategyTest {

	private WeightedGraph<TerminalGate> wg;
	private DijkstraSearchStrategy<TerminalGate> search;
	private Node<TerminalGate> ts  = new Node<>(new TerminalGate( "Concourse_A_Ticketing" ));
	private Node<TerminalGate> a5  = new Node<>(new TerminalGate("A5"));
	private Node<TerminalGate> bc  = new Node<>(new TerminalGate("BaggageClaim"));
	private Node<TerminalGate> a10 = new Node<>(new TerminalGate("A10"));
	private Node<TerminalGate> a1  = new Node<>(new TerminalGate("A1"));
	private Node<TerminalGate> a2  = new Node<>(new TerminalGate("A2"));
	private Node<TerminalGate> a3  = new Node<>(new TerminalGate("A3"));
	private Node<TerminalGate> a4  = new Node<>(new TerminalGate("A4"));
	private Node<TerminalGate> a9  = new Node<>(new TerminalGate("A9"));
	private Node<TerminalGate> a8  = new Node<>(new TerminalGate("A8"));
	private Node<TerminalGate> a7  = new Node<>(new TerminalGate("A7"));
	private Node<TerminalGate> a6  = new Node<>(new TerminalGate("A6"));


	@Before
	public void setup() {
		search = new DijkstraSearchStrategy<>();
		wg = new WeightedGraph<>();
		wg.addEdge(new WeightedEdge<>(ts,  a5,  5));
		wg.addEdge(new WeightedEdge<>(a5,  bc,  5));
		wg.addEdge(new WeightedEdge<>(a5,  a10, 4));
		wg.addEdge(new WeightedEdge<>(a5,  a1,  6));
		wg.addEdge(new WeightedEdge<>(a1,  a2,  1));
		wg.addEdge(new WeightedEdge<>(a2,  a3,  1));
		wg.addEdge(new WeightedEdge<>(a3,  a4,  1));
		wg.addEdge(new WeightedEdge<>(a10, a9,  1));
		wg.addEdge(new WeightedEdge<>(a9,  a8,  1));
		wg.addEdge(new WeightedEdge<>(a8,  a7,  1));
		wg.addEdge(new WeightedEdge<>(a7,  a6,  1));
	}


	@Test (expected = IllegalArgumentException.class)
	public void testNullSearch1() {
		search.findPath( null, new Node<>(new TerminalGate("A1")), new Node<>(new TerminalGate("BaggageClaim")) ).getNodePaths().toArray();
	}

	@Test (expected = IllegalArgumentException.class)
	public void testNullSearch2() {
		search.findPath( wg, null, new Node<>(new TerminalGate("BaggageClaim")) ).getNodePaths().toArray();
	}

	@Test (expected = IllegalArgumentException.class)
	public void testNullSearch3() {
		search.findPath( wg, new Node<>(new TerminalGate("A1")), null ).getNodePaths().toArray();
	}

	@Test
	public void testSearchA1toClaim() {
		Object[] nodeArr =  search.findPath( wg, new Node<>(new TerminalGate("A1")), new Node<>(new TerminalGate("BaggageClaim")) ).getNodePaths().toArray();
		assertArrayEquals(nodeArr, new Node[]{
				new Node<>(new TerminalGate("A1")),
				new Node<>(new TerminalGate("A5")),
				new Node<>(new TerminalGate("BaggageClaim"))});
	}
	@Test
	public void testSearchA5toA4(){
		Object[]  nodeArr = search.findPath( wg, new Node<>(new TerminalGate("A5")), new Node<>(new TerminalGate("A4")) ).getNodePaths().toArray();
		assertArrayEquals(nodeArr, new Node[]{
				new Node<>(new TerminalGate("A5")),
				new Node<>(new TerminalGate("A1")),
				new Node<>(new TerminalGate("A2")),
				new Node<>(new TerminalGate("A3")),
				new Node<>(new TerminalGate("A4"))
			});
	}
	
	@Test
	public void testSearchA5toA7(){
		Object[]  nodeArr =  search.findPath( wg, new Node<>(new TerminalGate("A5")), new Node<>(new TerminalGate("A7")) ).getNodePaths().toArray();
		assertArrayEquals(nodeArr, new Node[]{
				new Node<>(new TerminalGate("A5")),
				new Node<>(new TerminalGate("A10")),
				new Node<>(new TerminalGate("A9")),
				new Node<>(new TerminalGate("A8")),
				new Node<>(new TerminalGate("A7"))
			});
	}


}

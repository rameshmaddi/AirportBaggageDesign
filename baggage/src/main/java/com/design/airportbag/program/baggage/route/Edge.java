package com.design.airportbag.program.baggage.route;

public interface Edge<T> {
	
	Node <T> getFirstNode();
	Node <T> getSecondNode();
	Node<T> getOtherNode( Node<T> thisNode);
}

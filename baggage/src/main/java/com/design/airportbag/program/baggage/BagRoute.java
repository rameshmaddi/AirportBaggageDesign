package com.design.airportbag.program.baggage;

import com.design.airportbag.program.baggage.domain.PassengerBag;
import com.design.airportbag.program.baggage.domain.TerminalGate;
import com.design.airportbag.program.baggage.route.search.NodePath;


public class BagRoute {

	private PassengerBag bag;
	private NodePath<TerminalGate> bagPath;


	public BagRoute( PassengerBag bag, NodePath<TerminalGate> bagPath ) {
		setBag( bag );
		setBagPath( bagPath );
	}


	public PassengerBag getBag() {
		return bag;
	}

	public void setBag( PassengerBag bag ) {
		this.bag = bag;
	}

	public NodePath<TerminalGate> getBagPath() {
		return bagPath;
	}

	public void setBagPath( NodePath<TerminalGate> bagPath ) {
		this.bagPath = bagPath;
	}

}

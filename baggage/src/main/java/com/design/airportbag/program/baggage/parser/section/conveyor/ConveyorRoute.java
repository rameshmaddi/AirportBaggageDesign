package com.design.airportbag.program.baggage.parser.section.conveyor;

import com.design.airportbag.program.baggage.domain.TerminalGate;
import com.design.airportbag.program.baggage.parser.section.SectionEntry;


/**
 * One route in the the conveyor system. Conveyor system is described
 * by the conveyor section of the routing files.
 */
public class ConveyorRoute implements SectionEntry {

	private TerminalGate firstNode;
	private TerminalGate secondNode;
	private int travelTime;

	/**
	 * Get the first gate node.
	 */
	public TerminalGate getFirstTerminal() {
		return firstNode;
	}

	/**
	 * Set the first gate node.
	 */
	public void setFirstTerminal( TerminalGate firstNode ) {
		this.firstNode = firstNode;
	}

	/**
	 * Get the second gate node.
	 */
	public TerminalGate getSecondTerminal() {
		return secondNode;
	}

	/**
	 * Set the second gate node.
	 */
	public void setSecondTerminal( TerminalGate secondNode ) {
		this.secondNode = secondNode;
	}

	/**
	 * Get the time the bag takes from the first node to the second
	 * node on the conveyor.
	 */
	public int getTravelTime() {
		return travelTime;
	}

	/**
	 * Set the time the bag takes from the first node to the second
	 * node on the conveyor.
	 */
	public void setTravelTime( int travelTime ) {
		this.travelTime = travelTime;
	}

}

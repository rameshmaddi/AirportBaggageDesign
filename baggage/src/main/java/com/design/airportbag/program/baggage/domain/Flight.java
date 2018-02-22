package com.design.airportbag.program.baggage.domain;

import java.util.Date;

/**
 * @author rameshmaddi
 * Domain object for flights.
 */
public class Flight implements Identifiable<FlightId> {

	private FlightId flightId;
	private Airport destination;
	private TerminalGate gate;
	private Date flightTime;

	public Flight() {}
	public Flight(FlightId flightId) {
		setFlightId(flightId);
	}

	/**
	 * Get the flight ID
	 */
	public FlightId getFlightId() {
		return flightId;
	}

	/**
	 * Set the flight ID
	 */
	public void setFlightId(FlightId flightId) {
		this.flightId = flightId;
	}

	/**
	 * Check if the flight id is set.
	 */
	public boolean isSetFlightId() {
		return flightId != null;
	}

	/**
	 * Get the destination airport
	 */
	public Airport getDestination() {
		return destination;
	}

	/**
	 * Set the destination airport
	 */
	public void setDestination(Airport destination) {
		this.destination = destination;
	}

	/**
	 * Check if the destination is set.
	 */
	public boolean isSetDestination() {
		return destination != null;
	}

	/**
	 * Set the flight gate
	 */
	public TerminalGate getGate() {
		return gate;
	}

	/**
	 * Get the arrival gate
	 */
	public void setGate(TerminalGate gate) {
		this.gate = gate;
	}

	/**
	 * Check if the gate is set.
	 */
	public boolean isSetGate() {
		return gate != null;
	}

	/**
	 * Get the flight ID
	 */
	public Date getFlightTime() {
		return flightTime;
	}

	/**
	 * Set the flight ID
	 */
	public void setFlightTime(Date flightTime) {
		this.flightTime = flightTime;
	}

	/**
	 * Check if the flight time is set.
	 */
	public boolean isSetFlightTime() {
		return flightTime != null;
	}

	@Override
	public FlightId getId() {
		return getFlightId();
	}

	@Override
	public boolean equals( Object obj ) {
		return ( !isSetFlightId() || ! ( obj instanceof Flight ) ) ?
			   super.equals( obj ) :
			   ((Flight) obj).getFlightId().equals( getFlightId() );
	}

	@Override
	public String toString() {
		return getId().getId();
	}

}

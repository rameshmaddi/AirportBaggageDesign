package com.design.airportbag.program.baggage.domain;

/**
 * @author rameshmaddi
 * Flight ID domain object.
 */
public class FlightId implements Identifiable<String> {

	private final String id;

	/**
	 * Build a flight ID with the given ID.
	 */
	public FlightId(String id) {
		this.id = id;
	}

	/**
	 * Get the flight ID
	 */
	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return getId() == null ? super.toString() : getId();
	}

	@Override
	public boolean equals( Object obj ) {
		return (getId() == null || ! (obj instanceof FlightId)) ?
			   super.equals( obj ) :
			   getId().equals( ((FlightId) obj).getId() );
	}

}

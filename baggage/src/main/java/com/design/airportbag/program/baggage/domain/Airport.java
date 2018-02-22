package com.design.airportbag.program.baggage.domain;


/**
 * @author rameshmaddi
 * Domain object for the airport
 */
public class Airport implements Identifiable<String> {

	private String airportId;

	public Airport(String airportId) {
		setAirportId(airportId);
	}

	/**
	 * Set the airport ID.
	 */
	public void setAirportId( String airportId ) {
		this.airportId = airportId;
	}

	/**
	 * Get the airport id.
	 */
	public String getAirportId() {
		return airportId;
	}

	@Override
	public String getId() {
		return getAirportId();
	}

	@Override
	public boolean equals( Object obj ) {
		return ( getAirportId() == null || ! ( obj instanceof Airport) ) ? super.equals( obj ) : ((Airport) obj).getAirportId().equals( getAirportId() );
	}

	@Override
	public String toString() {
		return getId();
	}

}

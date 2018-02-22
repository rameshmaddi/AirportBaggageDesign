package com.design.airportbag.program.baggage.domain;

/**
 * @author rameshmaddi
 * Domain object for passenger's bag.
 */
public class PassengerBag implements Identifiable<String> {

	private String bagNumber;
	private BagState state = BagState.UNKNOWN;

	public PassengerBag(String bagNumber) {
		setBagNumber(bagNumber);
	}

	/**
	 * Get the bag ID
	 */
	public String getBagNumber() {
		return this.bagNumber;
	}

	/**
	 * Set the bag ID
	 */
	public void setBagNumber( String bagNumber ) {
		this.bagNumber = bagNumber;
	}

	/**
	 * Get the current bag state.
	 */
	public BagState getBagState() {
		return this.state;
	}

	/**
	 * Set the current bag state.
	 */
	public void setBagState( PassengerBag.BagState state ) {
		this.state = state;
	}

	@Override
	public String getId() {
		return getBagNumber();
	}

	@Override
	public boolean equals( Object obj ) {
		return ( getBagNumber() == null || ! (obj instanceof PassengerBag) ) ?
			   super.equals( obj ) :
			   ((PassengerBag) obj).getBagNumber().equals( getBagNumber() );
	}

	@Override
	public String toString() {
		return getBagNumber();
	}


	/**
	 * Bag state
	 */
	public enum BagState {
		/**
		 * Oops, we don't know where your bag is.
		 */
		UNKNOWN,

		/**
		 * Bag is in a holding stage.
		 */
		WAITING,

		/**
		 * Bag is on the flight.
		 */
		IN_TRANSIT,

		/**
		 * Bag is read for arrival.
		 */
		ARRIVAL,

		/**
		 * Bag has been picked up.
		 */
		CLAIMED,

		/**
		 * Bag wasn't picked up, and is held.
		 */
		UNCLAIMED
	}

}

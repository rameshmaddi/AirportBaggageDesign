package com.design.airportbag.program.baggage.domain;

/**
 * @author rameshmaddi
 * Domain object for gates.
 */
public class TerminalGate implements Identifiable<String> {
	private final String gateNumber;

	/**
	 * Create a gate given the gate number.
	 */
	public TerminalGate(String gateNumber) {
		this.gateNumber = gateNumber;
	}

	/**
	 * Get the gate ID
	 */
	public String getGateNumber() {
		return gateNumber;
	}

	@Override
	public boolean equals( Object obj ) {
		return ( ! (obj instanceof TerminalGate) ) ?
			   super.equals( obj ) :
			   ((TerminalGate) obj).getGateNumber().equals( getGateNumber() );
	}

	@Override
	public String getId() {
		return getGateNumber();
	}

	@Override
	public String toString() {
		return getId();
	}

}

/**
 * 
 */
package com.design.airportbag.program.baggage.parser.section;

import com.design.airportbag.program.baggage.parser.section.SectionType;

/**
 * @author rameshmaddi
 *
 */
public enum SectionType {

	/**
	 * Section marker for the Conveyor System.
	 */
	CONVEYOR_SYSTEM("Conveyor System"),

	/**
	 * Section marker for Departures.
	 */
	DEPARTURES("Departures"),

	/**
	 * Section marker for bag routes.
	 */
	BAGS("Bags"),

	/**
	 * Invalid token.
	 */
	UNKNOWN("");

	public static SectionType fromIdentifier( String identifier ) {
		if ( identifier != null && ! identifier.trim().equals("") ) {
			for ( SectionType token : values() ) {
				String tok = token.getIdentifier().replaceAll( "(\\s)*", "" );
				if ( tok.equalsIgnoreCase( identifier.trim().replaceAll( "(\\s)*", "" ) ) ) {
					return token;
				}
			}
		}

		return SectionType.UNKNOWN;
	}
	
	// ID string
	private final String identifier;

	public String getIdentifier() {
		return this.identifier;
	}

	SectionType(String identifier) {
		this.identifier = identifier;
	}

}

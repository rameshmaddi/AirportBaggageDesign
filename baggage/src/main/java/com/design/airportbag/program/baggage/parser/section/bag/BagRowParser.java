package com.design.airportbag.program.baggage.parser.section.bag;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.design.airportbag.program.baggage.domain.Flight;
import com.design.airportbag.program.baggage.domain.FlightId;
import com.design.airportbag.program.baggage.domain.PassengerBag;
import com.design.airportbag.program.baggage.domain.TerminalGate;
import com.design.airportbag.program.baggage.parser.ParseException;
import com.design.airportbag.program.baggage.parser.section.RowParsingDelegate;
import com.design.airportbag.program.baggage.parser.section.SectionRowWrapper;


/**
 * {@link RowParsingDelegate} implementation for parsing Bag section rows. Bag routes are given by
 * the rules:
 * <pre>
 * Section 3: Bag list
 *     Format: &lt;bag_number&gt; &lt;entry_point&gt; &lt;flight_id&gt;
 * </pre><pre>
 * Example:
 *    0003 A2 UA10
 * </pre>
 */
public class BagRowParser implements RowParsingDelegate<BagEntry> {

	//	conveyor row should match this format
	private final static Pattern bagRowPattern = Pattern.compile( "^(\\d+\\s+)(\\w+\\s+)(\\w+)$" );


	@Override
	public SectionRowWrapper<BagEntry> parseSectionRow( String sectionLine ) throws ParseException {
		if ( sectionLine == null ) {
			throw new IllegalArgumentException( "Invalid line (null)." );
		}

		sectionLine = sectionLine.trim();

		if ( sectionLine.contains( "\n" ) || sectionLine.contains( "\r\n" ) ) {
			throw new IllegalArgumentException( "Too many lines." );
		}

		Matcher matcher = bagRowPattern.matcher( sectionLine );
		if ( !matcher.find() ) {
			throw new ParseException( "Bag route line doesn't match pattern " + bagRowPattern.toString() );
		}

		BagEntry route = new BagEntry();
		route.setBag( new PassengerBag( matcher.group( 1 ).trim() ) );
		route.setEntryPoint( new TerminalGate( matcher.group( 2 ).trim() ) );

		String flightId = matcher.group( 3 ).trim();
		if ( "ARRIVAL".equalsIgnoreCase( flightId ) ) {
			route.getBag().setBagState( PassengerBag.BagState.ARRIVAL );
		}
		else {
			route.getBag().setBagState( PassengerBag.BagState.IN_TRANSIT );

			route.setFlight( new Flight() );
			route.getFlight().setGate( route.getEntryPoint() );
			route.getFlight().setFlightId( new FlightId( matcher.group( 3 ).trim() ) );
		}

		return new SectionRowWrapper<>( route );
	}

}

package com.design.airportbag.program.baggage.parser.section.departure;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.design.airportbag.program.baggage.domain.Airport;
import com.design.airportbag.program.baggage.domain.Flight;
import com.design.airportbag.program.baggage.domain.FlightId;
import com.design.airportbag.program.baggage.domain.TerminalGate;
import com.design.airportbag.program.baggage.parser.ParseException;
import com.design.airportbag.program.baggage.parser.section.RowParsingDelegate;
import com.design.airportbag.program.baggage.parser.section.SectionRowWrapper;


/**
 * {@link RowParsingDelegate} implementation for parsing Departures section rows. Departures are given by
 * the rules:
 * <pre>
 * Section 2: Departure list Format:
 *     &lt;flight_id&gt; &lt;flight_gate&gt; &lt;destination&gt; &lt;flight_time&gt;
 * </pre><pre>
 * Example:
 *     UA11 A1 LAX 09:00
 * </pre>
 */
public class DepartureRowParser implements RowParsingDelegate<Departure> {

	//	departure row should match this format
	private final static Pattern departureRowPattern = Pattern.compile( "^(\\w+\\s+)(\\w+\\s+)(\\w+\\s+)(\\d{2}:\\d{2})$" );

	//	string to extract flight time
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat( "HH:mm" );

	@Override
	public SectionRowWrapper<Departure> parseSectionRow( String sectionLine ) throws ParseException {
		if ( sectionLine == null ) {
			throw new IllegalArgumentException( "Invalid line (null)." );
		}

		sectionLine = sectionLine.trim();

		if ( sectionLine.contains("\n") || sectionLine.contains("\r\n") ) {
			throw new IllegalArgumentException( "Too many lines." );
		}

		Matcher matcher = departureRowPattern.matcher( sectionLine );
		if ( !matcher.find() ) {
			throw new ParseException( "Departure line doesn't match pattern " + departureRowPattern.toString() );
		}

		Flight flight = new Flight();
		flight.setGate( new TerminalGate( matcher.group( 2 ).trim() ) );
		flight.setFlightId( new FlightId( matcher.group( 1 ).trim() ) );
		flight.setDestination( new Airport( matcher.group( 3 ).trim() ) );

		try {
			flight.setFlightTime( dateFormat.parse( matcher.group( 4 ).trim() ) );
		}
		catch ( java.text.ParseException e ) {
			throw new ParseException( "Invalid flight time. " + matcher.group( 4 ).trim() );
		}

		return new SectionRowWrapper<>( Departure.fillInFromFlightInfo( flight ) );
	}

}

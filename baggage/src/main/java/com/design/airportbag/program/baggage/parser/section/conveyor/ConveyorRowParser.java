package com.design.airportbag.program.baggage.parser.section.conveyor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.design.airportbag.program.baggage.domain.TerminalGate;
import com.design.airportbag.program.baggage.parser.ParseException;
import com.design.airportbag.program.baggage.parser.section.RowParsingDelegate;
import com.design.airportbag.program.baggage.parser.section.SectionRowWrapper;


/**
 * {@link RowParsingDelegate} implementation for parsing the Conveyor System section rows.
 * Conveyor routes are given by
 * the rules:
 * <pre>
 * Section 1: A weighted bi-directional graph describing the conveyor system.
 *     Format: &lt;Node 1&gt; &lt;Node 2&gt; &lt;travel_time&gt;
 * </pre><pre>
 * Example:
 *    A5 A1 6
 * </pre>
 */
public class ConveyorRowParser implements RowParsingDelegate<ConveyorRoute> {

	//	conveyor row should match this format
	private final static Pattern conveyorRowPattern = Pattern.compile( "^(\\w+\\s+)(\\w+\\s+)(\\d+)$" );


	@Override
	public SectionRowWrapper<ConveyorRoute> parseSectionRow( String sectionLine ) throws ParseException {
		if ( sectionLine == null ) {
			throw new IllegalArgumentException( "Invalid line (null)." );
		}

		sectionLine = sectionLine.trim();

		if ( sectionLine.contains( "\n" ) || sectionLine.contains( "\r\n" ) ) {
			throw new IllegalArgumentException( "Too many lines." );
		}

		Matcher matcher = conveyorRowPattern.matcher( sectionLine );
		if ( !matcher.find() ) {
			throw new ParseException( "Conveyor route line doesn't match pattern " + conveyorRowPattern.toString() );
		}

		ConveyorRoute route = new ConveyorRoute();
		route.setFirstTerminal( new TerminalGate( matcher.group( 1 ).trim() ) );
		route.setSecondTerminal( new TerminalGate( matcher.group( 2 ).trim() ) );
		route.setTravelTime( Integer.parseInt( matcher.group( 3 ).trim() ) );

		return new SectionRowWrapper<>( route );
	}

}

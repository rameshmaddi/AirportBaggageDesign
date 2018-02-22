package com.design.airportbag.program.baggage.parse.conveyor;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.design.airportbag.program.baggage.parser.ParseException;
import com.design.airportbag.program.baggage.parser.section.conveyor.ConveyorRowParser;

/**
 * Tests for the converyor route parsing.
 */
public class ConveyorRouteTest {

	private final String positiveFormaat = "A1 A2 3";

	private ConveyorRowParser parser;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setup() {
		parser = new ConveyorRowParser();
	}

	
	@Test
	public void testConveyorRouteParsePositive() {
		try {
			parser.parseSectionRow(positiveFormaat);
		}
		catch ( ParseException e ) {
			fail( e.getMessage() );
		}
	}


	@Test( expected = IllegalArgumentException.class )
	public void testTooManyLines() throws ParseException {
		parser.parseSectionRow( positiveFormaat + "\n" + positiveFormaat );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testNullLine() throws ParseException {
		parser.parseSectionRow( null );
	}

	
	@Test( expected = NumberFormatException.class )
	public void testInvalidFlightTime() throws ParseException {
		parser.parseSectionRow( "A1 A2 " + Long.toString( (long) Integer.MAX_VALUE + 1000l ) );
	}

	@Test
	public void testTooManyParts() throws ParseException {
		String tooManyParts = positiveFormaat + " 99";
		exception.expect( ParseException.class );
		exception.expectMessage( "Conveyor route line doesn't match pattern ^(\\w+\\s+)(\\w+\\s+)(\\d+)$" );
		parser.parseSectionRow( tooManyParts );
	}

	@Test
	public void testTooFewParts() throws ParseException {
		exception.expect( ParseException.class );
		exception.expectMessage( "Conveyor route line doesn't match pattern ^(\\w+\\s+)(\\w+\\s+)(\\d+)$" );
		parser.parseSectionRow( "A1 A2" );
	}

}

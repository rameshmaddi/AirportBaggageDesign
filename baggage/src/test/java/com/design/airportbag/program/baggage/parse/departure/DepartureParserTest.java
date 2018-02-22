package com.design.airportbag.program.baggage.parse.departure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.design.airportbag.program.baggage.parser.ParseException;
import com.design.airportbag.program.baggage.parser.section.departure.Departure;
import com.design.airportbag.program.baggage.parser.section.departure.DepartureRowParser;

/**
 * Test cases for departure parsing.
 */
public class DepartureParserTest {

	private DepartureRowParser departureParser;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setup() {
		departureParser = new DepartureRowParser();
	}

	@After
	public void tearDown() {}


	@Test
	public void testParseSectionPositive() {
		try {
			Departure departure = departureParser.parseSectionRow( "UA10 A1 MIA 08:00" ).getWrappedRow();
			assertEquals( departure.getFlight().getFlightId().getId(), "UA10" );
		}
		catch (ParseException e) {
			fail( e.getMessage() );
		}
	}


	//=[ bad arguments tests ]==========================================================

	
	@Test( expected = IllegalArgumentException.class )
	public void testTooManyLines() throws ParseException {
		departureParser.parseSectionRow( "UA10 A1 MIA 08:00\nUA11 A2 MIA 04:00" );
		fail();
	}

	@Test( expected = IllegalArgumentException.class )
	public void testNullLine() throws ParseException {
		departureParser.parseSectionRow( null );
	}

	@Test( expected = ParseException.class )
	public void testTooManyElements() throws ParseException {
		departureParser.parseSectionRow( "UA10 A1 MIA 08:00 Extra" );
		fail();
	}

	@Test( expected = ParseException.class )
	public void testTooFewParts() throws ParseException {
		departureParser.parseSectionRow( "UA10 A1 02:00" );
		fail();
	}

	@Test( expected = ParseException.class )
	public void testInvalidMinutes() throws ParseException {
		departureParser.parseSectionRow( "UA10 A1 MIA 08:0" );
		fail();
	}

	@Test( expected = ParseException.class )
	public void testInvalidMinutesLong() throws ParseException {
		departureParser.parseSectionRow( "UA10 A1 MIA 08:011" );
		fail();
	}

	@Test( expected = ParseException.class )
	public void testInvalidHours() throws ParseException {
		departureParser.parseSectionRow( "UA10 A1 MIA 8:00" );
		fail();
	}

	@Test( expected = ParseException.class )
	public void testInvalidHoursLong() throws ParseException {
		departureParser.parseSectionRow( "UA10 A1 MIA 012:00" );
		fail();
	}

	@Test
	public void testInvalidFlightTime() throws ParseException {
		exception.expect( ParseException.class );
		exception.expectMessage( "Departure line doesn't match pattern ^(\\w+\\s+)(\\w+\\s+)(\\w+\\s+)(\\d{2}:\\d{2})$" );
		departureParser.parseSectionRow( "UA10 A1 MIA aa:00" );
	}

}

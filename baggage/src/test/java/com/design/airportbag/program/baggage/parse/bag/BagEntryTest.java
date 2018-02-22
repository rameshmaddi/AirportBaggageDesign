package com.design.airportbag.program.baggage.parse.bag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.design.airportbag.program.baggage.domain.PassengerBag;
import com.design.airportbag.program.baggage.parser.ParseException;
import com.design.airportbag.program.baggage.parser.section.bag.BagEntry;
import com.design.airportbag.program.baggage.parser.section.bag.BagRowParser;

/**
 * Tests for the bag route parser.
 */
public class BagEntryTest {

	private BagRowParser parser;
	private final String positiveFormat = "0003 A2 UA10";
	private final String tooManyElements = "0003 A2 UA10 A3";
	private final String tooFewElements = "0003 A2";

	@Before
	public void setup() {
		parser = new BagRowParser();
	}
	
	@Test
	public void testParsePositiveFormat() {
		try {
			assertNotNull( parser.parseSectionRow( positiveFormat ) );
		}
		catch ( ParseException e ) {
			fail( e.getMessage() );
		}
	}

	
	@Test( expected = IllegalArgumentException.class )
	public void testTooManyLines() throws ParseException {
		parser.parseSectionRow( positiveFormat + "\n" + positiveFormat );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testTooManyLinesWindows() throws ParseException {
		parser.parseSectionRow( positiveFormat + "\r\n" + positiveFormat );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testNullLine() throws ParseException {
		parser.parseSectionRow( null );
	}

	
	@Test( expected = ParseException.class )
	public void testTooManyParts() throws ParseException {
		parser.parseSectionRow( tooManyElements );
	}

	@Test( expected = ParseException.class )
	public void testTooFewParts() throws ParseException {
		parser.parseSectionRow( tooFewElements );
	}

	@Test( expected = ParseException.class )
	public void testInvalidBagId() throws ParseException {
		parser.parseSectionRow( "0a03 A2 UA10" );
	}

	@Test
	public void testValidArrivalState() throws ParseException {
		BagEntry route = parser.parseSectionRow( "0003 A2 ARRIVAL" ).getWrappedRow();
		assertNotNull( route );
		assertEquals( PassengerBag.BagState.ARRIVAL, route.getBag().getBagState() );
	}

	@Test
	public void testValidInTransitState() throws ParseException {
		BagEntry route = parser.parseSectionRow( positiveFormat ).getWrappedRow();
		assertNotNull( route );
		assertEquals( PassengerBag.BagState.IN_TRANSIT, route.getBag().getBagState() );
	}

}

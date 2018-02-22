package com.design.airportbag.program.baggage.parse;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.design.airportbag.program.baggage.parser.RoutingInput;


public class RoutingInputTest {

	@Test
	public void testConstructors() throws IOException {
		RoutingInput input ;

		input = new RoutingInput( getClass().getResourceAsStream( "/routing-input.txt" ) );
		assertNotNull( input );
		assertNotNull( input.getAsReader() );
		assertTrue( input.getAsReader().ready() );
		input.close();

	}

	@Test( expected = IllegalArgumentException.class )
	public void testNullStream() {
		InputStream r = null;
		new RoutingInput( r );
	}



}

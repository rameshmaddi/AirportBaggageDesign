package com.design.airportbag.program.baggage.parse;

import java.io.IOException;

import org.junit.Test;

import com.design.airportbag.program.baggage.parser.ParseException;
import com.design.airportbag.program.baggage.parser.RoutingEvaluator;
import com.design.airportbag.program.baggage.parser.section.SectionParser;


public class RoutingEvaluatorTest {


	@Test (expected =  IllegalArgumentException.class)
	public void testEvaluatorNullInput() throws ParseException, IOException {

		@SuppressWarnings("unused")
		SectionParser parser = RoutingEvaluator.multiSectionParser( null );

	}



}

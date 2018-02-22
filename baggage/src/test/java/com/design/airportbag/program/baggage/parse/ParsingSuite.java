package com.design.airportbag.program.baggage.parse;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.design.airportbag.program.baggage.parse.bag.BagEntryTest;
import com.design.airportbag.program.baggage.parse.conveyor.ConveyorRouteTest;
import com.design.airportbag.program.baggage.parse.departure.DepartureParserTest;
import com.design.airportbag.program.baggage.parse.section.SectionHeaderTokenizerTest;
import com.design.airportbag.program.baggage.parse.section.SectionTypeTest;

/**
 * Suite for parsing tests
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
		DepartureParserTest.class,
		ConveyorRouteTest.class,
		BagEntryTest.class,
		SectionTypeTest.class,
		SectionHeaderTokenizerTest.class,
		RoutingEvaluatorTest.class
})
public class ParsingSuite {}

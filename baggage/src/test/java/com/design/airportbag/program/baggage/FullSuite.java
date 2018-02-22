package com.design.airportbag.program.baggage;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.design.airportbag.program.baggage.parse.ParsingSuite;
import com.design.airportbag.program.baggage.route.RouteTestSuite;

/**
 * Full test suite for the bagrouter project.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
		ParsingSuite.class,
		RouteTestSuite.class,
})
public class FullSuite {}

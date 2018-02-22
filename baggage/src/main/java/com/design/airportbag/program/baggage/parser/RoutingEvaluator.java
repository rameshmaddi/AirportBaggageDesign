/**
 * 
 */
package com.design.airportbag.program.baggage.parser;

import com.design.airportbag.program.baggage.parser.ParseException;
import com.design.airportbag.program.baggage.parser.RoutingInput;
import com.design.airportbag.program.baggage.parser.section.SectionParser;

/**
 * @author rameshmaddi
 * This evaluator will give the parse  multiple types of section
 * data on a single callback. So no need to supply reader to each section parse.
 */
public class RoutingEvaluator {
	
	public static SectionParser multiSectionParser( RoutingInput input ) throws ParseException {
		SectionParser parser = new SectionParser( );
		parser.setSectionInput( input );
		return parser;
	}

}

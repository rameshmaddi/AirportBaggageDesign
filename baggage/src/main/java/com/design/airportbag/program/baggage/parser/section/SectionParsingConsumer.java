/**
 * 
 */
package com.design.airportbag.program.baggage.parser.section;

import com.design.airportbag.program.baggage.parser.ParseException;

/**
 * @author rameshmaddi
 *
 */
public interface SectionParsingConsumer <T extends SectionEntry> {
	
	void accept( T data ) throws ParseException;
}

/**
 * 
 */
package com.design.airportbag.program.baggage.parser.section;

import com.design.airportbag.program.baggage.parser.ParseException;
import com.design.airportbag.program.baggage.parser.section.SectionRowWrapper;

/**
 * @author rameshmaddi
 *
 */
public interface RowParsingDelegate <T extends SectionEntry> {
	
	/**
	 * Parse a single line, returning a parsed section node.
	 *
	 * @param sectionLine one line from the given section type.
	 * @return the parsed section entry as wrapped type {@code T}
	 * @throws ParseException if the line couldn't be parsed
	 */
	SectionRowWrapper<T> parseSectionRow( String sectionLine ) throws ParseException;

}

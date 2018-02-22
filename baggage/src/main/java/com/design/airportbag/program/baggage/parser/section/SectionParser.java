/**
 * 
 */
package com.design.airportbag.program.baggage.parser.section;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.design.airportbag.program.baggage.parser.RoutingInput;
import com.design.airportbag.program.baggage.parser.section.RowParsingDelegate;
import com.design.airportbag.program.baggage.parser.section.SectionEntry;
import com.design.airportbag.program.baggage.parser.section.SectionParsingConsumer;
import com.design.airportbag.program.baggage.parser.section.SectionType;
import com.design.airportbag.program.baggage.parser.ParseException;


/**
 * @author rameshmaddi
 *
 */
public class SectionParser {
	
	private RoutingInput routingInput;
	
	private final Map<SectionType, RowParsingDelegate<? extends SectionEntry>> parserList = new HashMap<>( 100 );
	private final Map<SectionType, SectionParsingConsumer<SectionEntry>> consumerList = new HashMap<>( 100 );

	/**
	 * Add a row parsing strategy for a given type.
	 */
	protected <T extends SectionEntry> void addRowParser( SectionType type, RowParsingDelegate<T> parsingStrategy ) {
		parserList.put( type, parsingStrategy );
	}


	/**
	 * Add a row parsing consumer for a given type.
	 */
	protected void addSectionConsumer( SectionType type, SectionParsingConsumer<SectionEntry> consumer ) {
		consumerList.put( type, consumer );
	}


	/**
	 * Add a row parsing consumer for a given type.
	 */
	public void addSectionConsumer( SectionType type, RowParsingDelegate<? extends SectionEntry> parsingDelegate, SectionParsingConsumer<SectionEntry> consumer ) {
		addRowParser( type, parsingDelegate );
		addSectionConsumer( type, consumer );
	}


	/**
	 * Parse through the {@link RoutingInput} looking for the {@link SectionType}.
	 * Once a section is found, give each line to the {@link Consumer} for processing.
	 * @throws IOException 
	 */
	public void parseSections( ) throws ParseException, IOException {
		checkReadyToParse();

		getSectionInput().forEachLine( ( type, line ) -> {
			if ( parserList.containsKey( type ) && consumerList.containsKey( type ) ) {
				RowParsingDelegate<?> sConsumer = parserList.get( type );
				consumerList.get( type ).accept( sConsumer.parseSectionRow( line ).getWrappedRow() );
			}
		});

		getSectionInput().close();
	}


	/**
	 * Set the reader input.
	 */
	public void setSectionInput( RoutingInput sectionReader ) {
		if ( sectionReader == null ) {
			throw new IllegalArgumentException( "Null reader" );
		}

		this.routingInput = sectionReader;
	}


	/**
	 * Get the input reader.
	 */
	private RoutingInput getSectionInput() {
		return routingInput;
	}


	/**
	 * Checks that all of the necessary parts are ready for parsing. This will
	 * throw a {@link ParseException} if something is missing.
	 */
	protected void checkReadyToParse() throws ParseException {
		if ( getSectionInput() == null ) {
			throw new ParseException( "Reader not set." );
		}
	}
}

/**
 * 
 */
package com.design.airportbag.program.baggage.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import com.design.airportbag.program.baggage.parser.section.SectionHeaderTokenizer;
import com.design.airportbag.program.baggage.parser.section.SectionType;

/**
 * @author rameshmaddi
 *
 */
public class RoutingInput {
	
	private final BufferedReader reader;

	/**
	 * Construct an input using an input stream.
	 */
	public RoutingInput( InputStream in ) {
		if ( in == null ) { throw new IllegalArgumentException( "Null routing input" ); }
		this.reader = new BufferedReader( new InputStreamReader( in ) );
	}
	
	public Reader getAsReader() {
		return reader;
	}

	/**
	 * Close the routing input.
	 */
	public void close() throws IOException{
		if ( reader != null ) {
			reader.close();
		}
	}
	
	/**
	 * For line-based inputs, this iterates through the reader and issues the
	 * callback for each line in the input. If an exception occurs in the consumer,
	 * this will re-throw that exception at the end.
	 *
	 * @param lineConsumer consumer of the input's lines
	 */
	public void forEachLine( SectionConsumer<String> lineConsumer ) throws ParseException {
		if ( lineConsumer == null ) { throw new IllegalArgumentException( "Null consumer" ); }
		if ( getAsReader() == null ) { throw new ParseException( "Must set reader first." ); }

		//try ( BufferedReader reader = new BufferedReader( getAsReader() ) ) {
		try {
			exceptionIterating = null;
			//reader.mark( markReadLimit );

			String line;
			SectionType currentType = SectionType.UNKNOWN;

			while( (line = reader.readLine()) != null && ! line.equals( "" ) ) {
				SectionType itType = getSectionTokenForLine( line );
				if ( ! SectionType.UNKNOWN.equals( itType ) ) {
					currentType = itType;
					continue;
				}

				lineConsumer.accept( currentType, line );
			}

			//reader.reset();
		}
		catch ( Exception e ) {
			ParseException pe = new ParseException( "Error parsing section input. " + e.getMessage(), e );
			exceptionIterating = pe;
			throw pe;
		}
	}


	private ParseException exceptionIterating;
	public ParseException getParseException() { return exceptionIterating; }
	public boolean wasExceptionIterating() { return exceptionIterating != null; }


	/**
	 * Consumer for sections of the routing input.
	 * @param <T> type of data to be fed by the routing input.
	 */
	public interface SectionConsumer<T> {
		void accept( SectionType section, T data ) throws Exception;
	}

	/**
	 * Try to find a section id based on a given line. This will return the
	 * {@link SectionType} for a given line, or UNKNOWN if the type cannot
	 * be found.
	 *
	 * @param line line to check for a section ID.
	 * @return The associated {@link SectionType}, or {@code SectionType.UNKNOWN}
	 */
	protected SectionType getSectionTokenForLine( String line ) {
		if ( line == null || line.trim().equals( "" ) ) {
			return SectionType.UNKNOWN;
		}

		SectionHeaderTokenizer tokenizer = SectionHeaderTokenizer.checkLineForSectionHeader( line );
		return ( tokenizer != null && tokenizer.getSectionHeader() != null ) ?
			   SectionType.fromIdentifier( tokenizer.getSectionHeader() ) :
			   SectionType.UNKNOWN;
	}
	
}

package com.design.airportbag.program.baggage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.design.airportbag.program.baggage.parser.ParseException;
import com.design.airportbag.program.baggage.parser.RoutingInput;
import com.design.airportbag.program.baggage.route.exception.RoutingException;


/**
 * This is just a main runner class.
 */
public class BagRouter {

	public static void main( String [] args ) {
		String inputFile = ( args.length < 1 ) ? "routing-input.txt" : args[0];

		try {
			InputStream fis1, fis2;
			File file = new File( inputFile );
			fis1 = file.exists() ? new FileInputStream( file ) : BagRouter.class.getResourceAsStream( "/" + inputFile );
			fis2 = file.exists() ? new FileInputStream( file ) : BagRouter.class.getResourceAsStream( "/" + inputFile );

			if ( fis1 == null ) {
				throw new FileNotFoundException( inputFile );
			}

			System.out.println();
			System.out.println( "[*] Dumping input " + inputFile );
			System.out.println( "[*] ------------------" );
			new RoutingInput( fis1 ).forEachLine( ( type, line ) -> System.out.println( line ) );
			System.out.println( "[*] ------------------" );

			System.out.println();
			System.out.println();
			System.out.println( "[*] Routing Table" );
			System.out.println( "[*] ------------------" );
			RoutingInput input = new RoutingInput( fis2 );
			RoutingEngine engine = new RoutingEngine();
			engine.executeSearch( input);
			System.out.println( "[*] ------------------" );

			engine.cleanup();
		}
		catch ( RoutingException | ParseException e ) {
			System.err.println( "Error routing. " + e.getMessage() );
		}
		catch ( FileNotFoundException e ) {
			System.err.println( "Could not find file. " + e.getMessage() );
		}

	}

}

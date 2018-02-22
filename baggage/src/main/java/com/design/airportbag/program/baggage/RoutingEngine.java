package com.design.airportbag.program.baggage;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.design.airportbag.program.baggage.domain.PassengerBag;
import com.design.airportbag.program.baggage.domain.TerminalGate;
import com.design.airportbag.program.baggage.parser.ParseException;
import com.design.airportbag.program.baggage.parser.RoutingEvaluator;
import com.design.airportbag.program.baggage.parser.RoutingInput;
import com.design.airportbag.program.baggage.parser.section.SectionParser;
import com.design.airportbag.program.baggage.parser.section.SectionType;
import com.design.airportbag.program.baggage.parser.section.bag.BagEntry;
import com.design.airportbag.program.baggage.parser.section.bag.BagRowParser;
import com.design.airportbag.program.baggage.parser.section.conveyor.ConveyorRoute;
import com.design.airportbag.program.baggage.parser.section.conveyor.ConveyorRowParser;
import com.design.airportbag.program.baggage.parser.section.departure.Departure;
import com.design.airportbag.program.baggage.parser.section.departure.DepartureRowParser;
import com.design.airportbag.program.baggage.route.Node;
import com.design.airportbag.program.baggage.route.WeightedGraph;
import com.design.airportbag.program.baggage.route.exception.RoutingException;
import com.design.airportbag.program.baggage.route.exception.SearchRouteException;
import com.design.airportbag.program.baggage.route.search.GraphSearchStrategy;
import com.design.airportbag.program.baggage.route.search.NodePath;
import com.design.airportbag.program.baggage.route.search.SearchableGraph;
import com.design.airportbag.program.baggage.route.search.dijkstra.DijkstraSearchStrategy;


/**
 * Routing engine.
 */
public class RoutingEngine {

	public final static String searchStrategyClass = System.getProperty( "router.search.strategy", DijkstraSearchStrategy.class.getName() );

	private final int defaultCollectionSize = 100;
	private String baggageClaimId = "BaggageClaim";
	private Router router = new Router();



	/**
	 * Baggage claim ID
	 */
	public void setBaggageClaimId( String baggageClaimId ) {
		this.baggageClaimId = baggageClaimId;
	}


	/**
	 * Run the {@code RoutingEngine}. This expects that the input and output have both
	 * been set with the output. Otherwise an exception is thrown.
	 */
	public void executeSearch( RoutingInput input ) throws RoutingException {
		router.setRoutingInput( input );
		router.execute();
	}


	/**
	 * Cleanup any resources used while parsing.
	 */
	public void cleanup() {
		router.cleanup();
		router.closeIO();
	}


	//	Inner class to help force re-creating IO.
	protected final class Router {

		private RoutingInput routingInput;
		private Node<TerminalGate> baggageClaim;
		private Map<String, Departure> departures = new HashMap<>( defaultCollectionSize );
		private Map<String, BagEntry> passengerBags = new LinkedHashMap<>( defaultCollectionSize );
		//private Map<String, BagEntry> passengerBags = new TreeMap<>( ( o1, o2 ) -> o2.toLowerCase().compareTo( o1.toLowerCase() ) );
		private WeightedGraph<TerminalGate> conveyorRoutes = new WeightedGraph<>();
		private GraphSearchStrategy<TerminalGate, WeightedGraph<TerminalGate>> searchStrategy;


		/**
		 * Run the {@code RoutingEngine}. This expects that the input and output have both
		 * been set with the output. Otherwise an exception is thrown.
		 */
		protected void execute() throws RoutingException {
			if ( routingInput == null ) {
				throw new RoutingException( "Routing input not set." );
			}

			try {
				cleanup();
				parseInput();
				System.out.println(writeRoutes( performSearch() ));
			}
			catch ( IOException | ParseException e ) {
				throw new RoutingException( e.getMessage(), e );
			}
		}


		/**
		 * Write a list of passenger bag routes
		 * @throws SearchRouteException 
		 */
		protected String writeRoutes( List<BagRoute> routes ) throws IOException, SearchRouteException {
			StringBuilder sb = new StringBuilder();
			for ( BagRoute route : routes ) {
				sb.append("Bag No: " + route.getBag().getBagNumber()+ " |");
				sb.append("Bag Path: ");
				route.getBagPath().forEachNode(terminal ->{
					sb.append(terminal.getGateNumber() + " " );
				});
				sb.append("| Distance: "+route.getBagPath().getTotalDistance() + "\n ");
			}
			return sb.toString();
		}


		/**
		 * Search for the list of routes for passenger bags
		 */
		protected List<BagRoute> performSearch() {
			SearchableGraph<TerminalGate> searchableGraph = new SearchableGraph<>( conveyorRoutes );
			searchableGraph.setSearchStrategy( getSearchStrategy() );

			List<BagRoute> bagRoutes = new ArrayList<>( passengerBags.size() );
			passengerBags.forEach( ( bagid, entry ) -> {
				final PassengerBag bag = entry.getBag();
				final boolean isFinal = bag.getBagState().equals( PassengerBag.BagState.ARRIVAL );

				Node<TerminalGate> endNode;
				if (!isFinal) {
					Departure departure = departures.get( entry.getFlight().getFlightId().getId() );
					endNode = new Node<>( departure.getFlightGate() );
				}
				else {
					endNode = baggageClaim;
				}
				Node<TerminalGate> startNode = new Node<>( entry.getEntryPoint() );
				NodePath<TerminalGate> path = searchableGraph.findOptimalPath( startNode, endNode );
				bagRoutes.add( new BagRoute( bag, path ) );
			} );

			return bagRoutes;
		}



		/**
		 * {@link RoutingInput} setter.
		 */
		protected void setRoutingInput( RoutingInput input ) {
			this.routingInput = input;
		}

		/**
		 * Cleanup any resources
		 */
		protected void cleanup() {
			baggageClaim = null;
			departures.clear();
			passengerBags.clear();
			conveyorRoutes.cleanup();
		}

		/**
		 * Close IO resources
		 */
		protected void closeIO() {
			try {
				routingInput.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Do the route parsing.
		 * @throws IOException 
		 */
		protected void parseInput() throws ParseException, IOException {
			SectionParser parser = RoutingEvaluator.multiSectionParser( routingInput );

			parser.addSectionConsumer( SectionType.BAGS, new BagRowParser(), entry -> {
				passengerBags.put( ((BagEntry) entry).getBag().getId(), ((BagEntry) entry) );
			});

			parser.addSectionConsumer( SectionType.DEPARTURES, new DepartureRowParser(), entry -> {
				departures.put( ((Departure) entry).getFlight().getFlightId().getId(), ((Departure) entry) );
			});

			parser.addSectionConsumer( SectionType.CONVEYOR_SYSTEM, new ConveyorRowParser(), entry -> {
				ConveyorRoute conveyor = (ConveyorRoute) entry;
				Node<TerminalGate> node1 = new Node<>( conveyor.getFirstTerminal() );
				Node<TerminalGate> node2 = new Node<>( conveyor.getSecondTerminal() );
				conveyorRoutes.addEdge( node1, node2, conveyor.getTravelTime() );

				if (node1.getNodeId().toString().equalsIgnoreCase( baggageClaimId )) {
					baggageClaim = node1;
				}
				if (node2.getNodeId().toString().equalsIgnoreCase( baggageClaimId )) {
					baggageClaim = node2;
				}
			});

			parser.parseSections();

			if ( baggageClaim == null ) {
				throw new ParseException( "Baggage claim node was not found." );
			}

		}


		/**
		 * Load a search strategy from the system property
		 */
		@SuppressWarnings( "unchecked" )
		protected GraphSearchStrategy<TerminalGate, WeightedGraph<TerminalGate>> getSearchStrategy() {
			if ( searchStrategy != null ) { return searchStrategy; }
			try {
				Class<?> clazz = Class.forName( searchStrategyClass );
				searchStrategy = (GraphSearchStrategy<TerminalGate, WeightedGraph<TerminalGate>>)clazz.newInstance();
				return searchStrategy;
			}
			catch ( ClassNotFoundException | InstantiationException | IllegalAccessException e ) {
				throw new RuntimeException( "Error loading search strategy." );
			}
		}

	}

}

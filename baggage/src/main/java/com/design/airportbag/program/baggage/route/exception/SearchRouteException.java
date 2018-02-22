package com.design.airportbag.program.baggage.route.exception;

import com.design.airportbag.program.baggage.route.exception.RoutingException;

public class SearchRouteException extends RoutingException {

	public SearchRouteException( String message ) { super(message); }
	public SearchRouteException( String message, Throwable t ) { super(message, t); }
}

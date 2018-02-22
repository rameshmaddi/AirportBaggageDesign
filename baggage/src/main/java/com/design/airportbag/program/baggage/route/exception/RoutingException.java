package com.design.airportbag.program.baggage.route.exception;

public class RoutingException extends Exception{

	public RoutingException( String message ) { super(message); }
	public RoutingException( String message, Throwable t ) { super(message, t); }

}

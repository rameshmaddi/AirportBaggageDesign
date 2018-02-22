package com.design.airportbag.program.baggage.domain;

/**
 * @author rameshmaddi
 * @param <T>
 * An object that implements this should have an ID
 */
public interface Identifiable<T> {
	
	T getId();

}

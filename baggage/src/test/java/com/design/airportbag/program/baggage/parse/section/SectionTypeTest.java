package com.design.airportbag.program.baggage.parse.section;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.design.airportbag.program.baggage.parser.section.SectionType;

public class SectionTypeTest {

	private final static String conveyorSystem = "Conveyor System";
	private final static String departures = "Departures";
	private final static String bags = "Bags";
	
	@Test
	public void testPositiveConveyorSystem() {
		assertEquals( SectionType.fromIdentifier( conveyorSystem ), SectionType.CONVEYOR_SYSTEM );
		assertEquals( SectionType.fromIdentifier( "ConveyorSystem" ), SectionType.CONVEYOR_SYSTEM );
		assertEquals( SectionType.fromIdentifier( " Conveyor System " ), SectionType.CONVEYOR_SYSTEM );
	}
	
	@Test
	public void testPositiveDepartures() {
		assertEquals( SectionType.fromIdentifier( departures ), SectionType.DEPARTURES );
		assertEquals( SectionType.fromIdentifier( " Departures " ), SectionType.DEPARTURES );
	}
	
	@Test
	public void testPositiveBags() {
		assertEquals( SectionType.fromIdentifier( bags ), SectionType.BAGS );
		assertEquals( SectionType.fromIdentifier( " Bags " ), SectionType.BAGS );
	}
	
	@Test
	public void testPositiveConveyorSystemCase() {
		assertEquals( SectionType.fromIdentifier( conveyorSystem.toLowerCase() ), SectionType.CONVEYOR_SYSTEM );
		assertEquals( SectionType.fromIdentifier( conveyorSystem.toUpperCase() ), SectionType.CONVEYOR_SYSTEM );
	}
	
	@Test
	public void testPositiveDeparturesCase() {
		assertEquals( SectionType.fromIdentifier( departures.toLowerCase() ), SectionType.DEPARTURES );
		assertEquals( SectionType.fromIdentifier( departures.toUpperCase() ), SectionType.DEPARTURES );
	}
	
	@Test
	public void testPositiveBagsCase() {
		assertEquals( SectionType.fromIdentifier( bags.toLowerCase() ), SectionType.BAGS );
		assertEquals( SectionType.fromIdentifier( bags.toUpperCase() ), SectionType.BAGS );
	}

	@Test
	public void testNull() {
		assertEquals( SectionType.fromIdentifier( null ), SectionType.UNKNOWN );
		assertEquals( SectionType.fromIdentifier( "" ), SectionType.UNKNOWN );
	}
	
	@Test
	public void testSlipsConveyorSystem() {
		assertEquals( SectionType.fromIdentifier( "Conveyor Sistem" ), SectionType.UNKNOWN );
		assertEquals( SectionType.fromIdentifier( "Conveyor" ), SectionType.UNKNOWN );
	}
	
	@Test
	public void testSlipsDepartures() {
		assertEquals( SectionType.fromIdentifier( "Departure" ), SectionType.UNKNOWN );
		assertEquals( SectionType.fromIdentifier( "Departuresss" ), SectionType.UNKNOWN );
		assertEquals( SectionType.fromIdentifier( "Depar turesss" ), SectionType.UNKNOWN );
	}
	
	@Test
	public void testSlipsBags() {
		assertEquals( SectionType.fromIdentifier( "Bag" ), SectionType.UNKNOWN );
		assertEquals( SectionType.fromIdentifier( "Bagsss" ), SectionType.UNKNOWN );
		assertEquals( SectionType.fromIdentifier( "Ba gsss" ), SectionType.UNKNOWN );
	}
	
}

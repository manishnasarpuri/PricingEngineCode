package com.org.engine.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.org.util.Demand;
import com.org.util.PricingStrategy;

public class DemandSupplyDecoratorTest {

	List<Double> surveyPricingList = new ArrayList<Double>();
	@Before
	public void setUp() throws Exception {
		surveyPricingList.add(11.0);
		surveyPricingList.add(12.0);
		surveyPricingList.add(11.0);
		surveyPricingList.add(12.0);
		surveyPricingList.add(10.0);
	}

	@Test
	public void testSupplyHighDemandHighPricing() {
		assertEquals(12.1, new DemandSupplyDecorator(new Product
				("ssd", surveyPricingList, PricingStrategy.LEAST), Demand.H, Demand.H).calculate(),0.0);
	}
	
	@Test
	public void testSupplyLowDemandLowPricing() {
		assertEquals(13.1, new DemandSupplyDecorator(new Product
				("ssd", surveyPricingList, PricingStrategy.LEAST), Demand.L, Demand.L).calculate(),0.0);
	}
	
	@Test
	public void testSupplyLowDemandHighPricing() {
		assertEquals(14.1, new DemandSupplyDecorator(new Product
				("ssd", surveyPricingList, PricingStrategy.LEAST), Demand.L, Demand.H).calculate(),0.0);
	}
	
	@Test
	public void testSupplyHighDemandLowPricing() {
		assertEquals(11.1, new DemandSupplyDecorator(new Product
				("ssd", surveyPricingList, PricingStrategy.LEAST), Demand.H, Demand.L).calculate(),0.0);
	}

}

package com.org.engine.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.org.util.Choice;
import com.org.util.PricingStrategy;

public class WarrantyDecoratorTest {

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
	public void testLeastPricingStategywithWarranty() {
		assertEquals(11.55, new WarrantyDecorator(new Product
				("ssd", surveyPricingList, PricingStrategy.LEAST), Choice.Y).calculate(),0.0);
	}
	
	@Test
	public void testLeastPricingStategywithoutWarranty() {
		assertEquals(10.45, new WarrantyDecorator(new Product
				("ssd", surveyPricingList, PricingStrategy.LEAST), Choice.N).calculate(),0.0);
	}
	
	@Test
	public void testHighestPricingStategywithWarranty() {
		assertEquals(12.600000000000001, new WarrantyDecorator(new Product
				("ssd", surveyPricingList, PricingStrategy.HIGHEST), Choice.Y).calculate(),0.0);
	}
	
	@Test
	public void testHighestPricingStategywithoutWarranty() {
		assertEquals(11.399999999999999, new WarrantyDecorator(new Product
				("ssd", surveyPricingList, PricingStrategy.HIGHEST), Choice.N).calculate(),0.0);
	}

}

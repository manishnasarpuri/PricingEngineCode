package com.org.engine.impl;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.org.util.PricingStrategy;

public class ProductTest {

	List<Double> surveyPricingList = new ArrayList<Double>();

	@Before
	public void setUp() {
		surveyPricingList.add(11.0);
		surveyPricingList.add(12.0);
		surveyPricingList.add(11.0);
		surveyPricingList.add(12.0);
		surveyPricingList.add(10.0);
		System.out.println("@Before - setUp");
	}

	@Test
	public void testLeastPricingStategy() {
		assertEquals(11.0, new Product
				("ssd", surveyPricingList, PricingStrategy.LEAST).calculate(),0.0);
	}
	
	@Test
	public void testHighestPricingStategy() {
		assertEquals(12.0, new Product
				("ssd", surveyPricingList, PricingStrategy.HIGHEST).calculate(),0.0);
	}

}

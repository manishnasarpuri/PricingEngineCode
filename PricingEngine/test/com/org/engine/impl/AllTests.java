package com.org.engine.impl;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DemandSupplyDecoratorTest.class, ProductTest.class, WarrantyDecoratorTest.class })
public class AllTests {

}

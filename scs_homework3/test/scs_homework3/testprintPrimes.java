package scs_homework3;

import static org.junit.Assert.*;
 
 import org.junit.After;
 import org.junit.Before;
 import org.junit.Test;
 
 import scs_homework3.printPrimes;
 
 public class testprintPrimes {
 
	 printPrimes t = new printPrimes();
     @Before
     public void setUp() throws Exception {
    	      }
      
     @After
     public void tearDown() throws Exception {
     }
 
     @Test
     public void testPrintPrimes() {
         t.printPrimes(5);
     }

 }

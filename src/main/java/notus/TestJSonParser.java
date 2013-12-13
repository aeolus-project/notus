package notus;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestJSonParser {

	JSonParser parser = null;
	
	public static void testInstance(JSonParser parser, boolean displayInstance, final String instance, final String[] dimensions, final int nbItems, final int nbBins) {
		
		System.out.println("************* Test Begin *****************");

		System.out.println("Load Instance : " + instance);
		
		parser.loadInstance(new File(instance));

		assertEquals(parser.getInstanceFile() != null, true);	
		
		System.out.println("Parse Instance");
		
		parser.parse(displayInstance);
		
		System.out.println("Check Dimensions = " + Arrays.toString(dimensions));
		
		//[ram]
		assertEquals(parser.getDimensions().getDimensionCount(), dimensions.length);			
		
		for(int i = 0; i < dimensions.length; i++) {
			assertEquals(parser.getDimensions().getName(i), dimensions[i]);			
		}
		
		System.out.println("Check nbItems = " + nbItems);
		//length items = nbItems
		assertEquals(parser.getItems().length, nbItems);

		System.out.println("Check nbBins = " + nbBins);
		//length bins = nbBins
		assertEquals(parser.getBins().length, nbBins);
		
		System.out.println("Check dimensions in items");
		//sizes.length Items = DimensionsCount		
		for(int i = 0; i < parser.getItems().length; i++) {
			assertEquals(parser.getItems()[i].getDimensionCount(), parser.getDimensions().getDimensionCount());	
		}

		System.out.println("Check dimensions in bins");
		//sizes.length Bins = DimensionsCount		
		for(int i = 0; i < parser.getBins().length; i++) {
			assertEquals(parser.getBins()[i].getDimensionCount(), parser.getDimensions().getDimensionCount());	
		}
		
		
		System.out.println("Check total sizes dimensions between bins and items : SizeBins[dim] >= SizeItems[dim]");
		//Get Total Sizes Items
		int[] totalSizeItems = new int[parser.getDimensions().getDimensionCount()];		
		for(int j = 0; j < dimensions.length; j++) {
			totalSizeItems[j] = 0;
			
			for(int i = 0; i < nbItems; i++) {
				totalSizeItems[j] += parser.getItems()[i].getSize(j) * parser.getItems()[i].getArity();
			}
			//System.out.println("total items " + parser.getDimensions().getName(j) + " : " + totalSizeItems[j]);
		}
		
		//Get Total Sizes Bins
		int[] totalSizeBins = new int[parser.getDimensions().getDimensionCount()];
		for(int j = 0; j < dimensions.length; j++) {
			totalSizeBins[j] = 0;
			
			for(int i = 0; i < nbBins; i++) {
				//Test if sizeBin != 0
				assertEquals(parser.getBins()[i].getSize(j) != 0, true);
				totalSizeBins[j] += parser.getBins()[i].getSize(j) * parser.getBins()[i].getArity();
			}
			//System.out.println("total bins " + parser.getDimensions().getName(j) + " : " + totalSizeBins[j]);
		}
		
		//Check if total SizeBins[dim] is greater or equal than total SizeItems[dim]
		for(int j = 0; j < dimensions.length; j++) {
			assertEquals(totalSizeBins[j] >= totalSizeItems[j], true);
			System.out.println("total bins for " + parser.getDimensions().getName(j) + " (" + totalSizeBins[j] 
					+ ") >= total items for " + parser.getDimensions().getName(j) + " (" + totalSizeItems[j] + ")");
			
		}
		
		System.out.println("************* Test End *****************\n");
		
	}
	
	@Before
	public void setUp() throws Exception {
		parser = new JSonParser();
	}

	@After
	public void tearDown() throws Exception {
		parser = null;
	}
	
	@Test
	public void testInstance1() {

		//17 items, 1 bin, 1 dimension
		final String instance = "src/test/resources/veryredundant-highmysqlandnovacompute-wheezy/binpacking.json";		
		final String[] dimensions = new String[] {"ram"};		
		final int nbItems = 17;
		final int nbBins = 1;
		testInstance(parser, false, instance, dimensions, nbItems, nbBins);
	}

	@Test
	public void testInstance2() {

		//17 items, 1 bin, 2 dimensions
		final String instance = "src/test/resources/veryredundant-highmysqlandnovacompute-wheezy/binpacking2.json";		
		final String[] dimensions = new String[] {"ram", "cpu"};		
		final int nbItems = 17;
		final int nbBins = 1;
		testInstance(parser, false, instance, dimensions, nbItems, nbBins);
	}
	
	@Test
	public void testInstance3() {

		//17 items, 1 bin, 2 dimensions
		final String instance = "src/test/resources/veryredundant-highmysqlandnovacompute-wheezy/binpacking3.json";		
		final String[] dimensions = new String[] {"ram", "cpu"};		
		final int nbItems = 17;
		final int nbBins = 2;
		testInstance(parser, false, instance, dimensions, nbItems, nbBins);
	}
		
}

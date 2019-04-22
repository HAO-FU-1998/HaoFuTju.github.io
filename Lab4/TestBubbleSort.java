import static org.junit.Assert.*;

import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestBubbleSort {

	private static BubbleSort bub;
	
	@Before 
	public void setUp() throws Exception {
		bub = new BubbleSort();
	}
	
	@After
	public void tearDown() throws Exception {
		bub = null;
	}
	
	@Test
	public void test1() {
		int arr1[] = new int[]{1,5,2,3,4};
		assertEquals("[1, 2, 3, 4, 5]",Arrays.toString(bub.BubbleSort(arr1)));
	}
	@Test
	public void test2() {
		int arr2[] = new int[]{1,5,5,3,3,4};
		assertEquals("[1, 3, 3, 4, 5, 5]",Arrays.toString(bub.BubbleSort(arr2)));
	} 
	@Test
	public void test3() {
		int arr3[] = new int[]{1};
		assertEquals("[1]",Arrays.toString(bub.BubbleSort(arr3)));
	}
	@Test
	public void test4() {
		int arr4[] = new int[]{};
		assertEquals("[]",Arrays.toString(bub.BubbleSort(arr4)));
	}
}

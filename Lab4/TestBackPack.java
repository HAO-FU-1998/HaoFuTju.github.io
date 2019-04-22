import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;

public class TestBackPack {
	private BackPack bp;
	
	@Before
	public void setUp() throws Exception {
		bp = new BackPack();
	}

	@After
	public void tearDown() throws Exception {
		bp = null;
	}

	@Test
	public void test() {
		int m = 10;
		int n = 3;
		int w[] = {3, 4, 5};
        int p[] = {4, 5, 6};
        boolean result = false;
        String expected = "0\t0\t4\t4\t4\t4\t4\t4\t4\t4\t\n"
        		+ "0\t0\t4\t5\t5\t5\t9\t9\t9\t9\t\n"
        		+ "0\t0\t4\t5\t6\t6\t9\t10\t11\t11\t\n";
        
        if(bp.toString(bp.BackPack_Solution(m, n, w, p)).equals(expected)){
        	result = true;
        }
        assertEquals(true,result);
	}
}


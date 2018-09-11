package tmis;


import java.util.TreeMap;
import org.testng.Assert;
import org.testng.annotations.Test;


public class Compare implements Base {

@Test(priority=3)
public void comparisonmethod() {
	
	System.out.println("DB Values: "+dbmap);
	
	System.out.println("PI Values: "+apimap);
	
	//System.out.println(uimap);
	
	mapComparison(dbmap,apimap, "DB and API");
	
	//mapComparison(apimap,uimap, "API and UI");
	
}

public void mapComparison(TreeMap<String, Float>map1, TreeMap<String, Float>map2, String Message) {
	
	if(map1.equals(map2)) {		
		//Assert.assertTrue(true);
		System.out.println(Message+ " values are matching ");
	
	}else {
		System.out.println(Message+ " values are not matching ");
		Assert.assertTrue(false);
	}
}
}
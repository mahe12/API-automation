package tmis;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.Properties;
import java.util.TreeMap;


public class TeradataConnection implements Base {

	String region;
	String sub_market;

	@Test(priority=0)
	public void extractData() throws Exception {	

		// Creation of URL to be passed to the JDBC driver
		String connurl="jdbc:teradata://simba.vip.paypal.com/";

		// Loading the Teradata JDBC driver
		Class.forName("com.teradata.jdbc.TeraDriver");
		Connection conn=DriverManager.getConnection(connurl, "vipremkumar", "Far.ran-20");	

		File property_file = new File("./src/test/resources/config.properties"); 
		FileInputStream fileInput = new FileInputStream(property_file); 
		Properties prop = new Properties(); 
		prop.load(fileInput);
		Enumeration<?> e = prop.propertyNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();

			if(key.equalsIgnoreCase("core"))
			{		
				region="CORE";
				sub_market="in('AU','UK','NA','DEATCH')";
			}
			else if(key.equalsIgnoreCase("international"))
			{		
				region="INTL";
				sub_market="in('DOMESTIC','GROWTH','CBT')"; 
			}

			StringBuilder query = new StringBuilder("sel \n" + 
					"\n" + 
					"region_name,\n" + 
					"\n" + 
					"sum(buyer_churn),\n" + 
					"\n" + 
					"sum(merchant_churn), sum (buyer_churn+merchant_churn) as ChurnTotal, \n" + 
					"\n" + 
					"sum(BUYER_ACTIVES_ACT), \n" + 
					"\n" + 
					"sum(MERCHANT_ACTIVES_ACT), sum(BUYER_ACTIVES_ACT+MERCHANT_ACTIVES_ACT) as ActivesTotal,\n" + 
					"\n" + 
					"sum(BUYER_RE_ACTIVATION), \n" + 
					"\n" + 
					"sum(MERCHANT_RE_ACTIVATION), sum(BUYER_RE_ACTIVATION+MERCHANT_RE_ACTIVATION) as ReactivationsTotal,\n" + 
					"\n" + 
					"sum(BUYER_NNA_ACTUAL), \n" + 
					"\n" + 
					"sum(MERCHANT_NNA_ACTUAL), sum(BUYER_NNA_ACTUAL+MERCHANT_NNA_ACTUAL) as NNATotal,\n" + 
					"\n" + 
					"sum(BUYER_NEW_ACTIVATION), \n" + 
					"\n" + 
					"sum(MERCHANT_NEW_ACTIVATION), sum(BUYER_NEW_ACTIVATION+MERCHANT_NEW_ACTIVATION) as NewActivationsTotal\n" + 
					"\n" + 
					"from PP_scratch.FACT_CUSTOMERS_TMIS_DLY_ARCH_640 where cal_dt between  '2018/07/01' and '2018/08/05' and Region_Name='"+region+"' and Sub_Market_Name "+sub_market+" \n" + 
					"\n" + 
					"group by 1\n" + 
					"\n" + 
					"order by 1");	

			PreparedStatement stmt=conn.prepareStatement(query.toString());
			ResultSet rs=stmt.executeQuery();
			rs.next();		// to skip first row with headings

			ResultSetMetaData metadata = rs.getMetaData();
		    int columnCount = metadata.getColumnCount();    
		    for (int i = 1; i <= columnCount; i++) {
		    	System.out.println(metadata.getColumnName(i) + ", ");      
		    }
		    System.out.println();
		    while (rs.next()) {
		        String row = "";
		        for (int i = 1; i <= columnCount; i++) {
		            row += rs.getString(i) + ", ";   
		            System.out.println(row);
		        }
		       // System.out.println(row);
		      //  writeToFile(row);

		    }
//			convertNumbers(rs.getFloat("ChurnTotal"), "Churn_"+key,dbmap);
//			convertNumbers(rs.getFloat("ActivesTotal"),"Actives 12M_"+key,dbmap);
//			convertNumbers(rs.getFloat("ReactivationsTotal"),"Re-activations_"+key,dbmap);
//			convertNumbers(rs.getFloat("NNATotal"),"NNA_"+key,dbmap);
//			convertNumbers(rs.getFloat("NewActivationsTotal"),"Activations_"+key,dbmap);
			
		}
	//	System.out.println(dbmap);
		System.out.println("Fetching of DB values completed");
		System.out.println("====================================================================================");

	}

	public void roundOff(String Total, String metricName, TreeMap<String, String> Map) {

		String finalValue;	
		if(Total.length() <= 6) {
			double convertedValue = Integer.parseInt(Total);
			double convertToThousands = convertedValue/1000 ;
			DecimalFormat df = new DecimalFormat("#.#");
			double doublevalue = Double.parseDouble(df.format(convertToThousands));
			finalValue = Double.toString(doublevalue) + "K";		
			Map.put(metricName,finalValue);
		}else if(Total.length() == 9) {
			double convertedValue = Integer.parseInt(Total);
			double convertToMillion = convertedValue/1000000 ;
			DecimalFormat df = new DecimalFormat("#.#");
			double doublevalue = Double.parseDouble(df.format(convertToMillion));
			finalValue = Double.toString(doublevalue) + "M";			
			Map.put(metricName,finalValue);
		}

	}

	public void convertNumbers(float finalvalue,String metricName,TreeMap<String, Float> Map) {

		if(Math.abs(finalvalue)>=1000 && Math.abs(finalvalue)<1000000)
		{
			finalvalue = new BigDecimal(finalvalue/1000).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

		}
		else if(Math.abs(finalvalue)>=1000000 && Math.abs(finalvalue)<1000000000)
		{
			finalvalue = new BigDecimal(finalvalue/1000000).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

		}
		else if(Math.abs(finalvalue)>=1000000000)
		{
			finalvalue = new BigDecimal(finalvalue/1000000000).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

		}
		Map.put(metricName,finalvalue);
	}
}

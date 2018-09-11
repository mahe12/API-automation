package RestAssured;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.json.Json;
import javax.json.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;

import groovy.json.JsonParser;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
public class tmis_api_testing {

	@Test
	public void readAPI() throws JSONException, ScriptException
	{
		Map<String,String> resource = new TreeMap<String,String>();
		LinkedHashMap<String,String> NNAvalues = new LinkedHashMap<String,String>();
		resource.put("metricName", "NNA");
		resource.put("duration", "quarterToDate");
		resource.put("endDate", "2018-08-19");
		resource.put("isSpot", "false");
		File file = new File("./Attachments/TMIS.json");
		Response response = RestAssured.
							given()												
							.relaxedHTTPSValidation()
							.params(resource)
							.contentType(ContentType.JSON)						
							.get("https://edstmisserv18030619.qa.paypal.com:20680/v1/edstmisserv/tmisresource_v2/grid");
	//	System.out.println(response.prettyPeek());
	
		
		JsonPath jsonPath = response.jsonPath();
		
		System.out.println(jsonPath.getString("companies[0].productGroup[0].products[0].regions[0].subMarkets[0].metric.type.actual") ); //core-all
		System.out.println(jsonPath.getString("companies[0].productGroup[0].products[0].regions[0].subMarkets[1].metric.type.actual") ); //core-au-all
		System.out.println(jsonPath.getString("companies[0].productGroup[0].products[0].regions[0].subMarkets[2].metric.type.actual") ); //core-deatch-all
		System.out.println(jsonPath.getString("companies[0].productGroup[0].products[0].regions[0].subMarkets[3].metric.type.actual") ); //core-na-all
		System.out.println(jsonPath.getString("companies[0].productGroup[0].products[0].regions[0].subMarkets[4].metric.type.actual") ); //core-uk-all
		
		
		System.out.println(jsonPath.getString("companies[0].productGroup[0].products[0].regions[1].subMarkets[0].metric.type.actual") ); //global-all
		
		System.out.println(jsonPath.getString("companies[0].productGroup[0].products[0].regions[2].subMarkets[0].metric.type.actual") ); //Intl-all
		System.out.println(jsonPath.getString("companies[0].productGroup[0].products[0].regions[2].subMarkets[1].metric.type.actual") ); //Intl-cbt
		System.out.println(jsonPath.getString("companies[0].productGroup[0].products[0].regions[2].subMarkets[2].metric.type.actual") ); //Intl-domestic
		System.out.println(jsonPath.getString("companies[0].productGroup[0].products[0].regions[2].subMarkets[3].metric.type.actual") ); //Intl-emerging
		
	
		
		
		
		JSONArray subMarketName = new JSONArray(jsonPath.getString("companies.productGroup.products.regions.subMarkets.name"));
		JSONArray RegionName = new JSONArray(jsonPath.getString("companies.productGroup.products.regions.name"));
		JSONArray productName = new JSONArray(jsonPath.getString("companies.productGroup.name"));
		List<String> Nameslist = new ArrayList<String>();
		
		System.out.println(subMarketName);
		System.out.println(RegionName);
		System.out.println(productName);
		
		String[] region_splitter = RegionName.getString(0).replaceAll("\\[", "").replaceAll("\\]","").replace("\"", "").split(",");
		String[] product_splitter = productName.getString(0).replaceAll("\\[", "").replaceAll("\\]","").replace("\"", "").replaceAll("\\d. ", "").split(",");
		String[] subMarket_splitter = subMarketName.getString(0).replaceAll("\\[", "").replaceAll("\\]","").replace("\"", "").split(",");
		
		
		List<String> region_list = Arrays.asList(region_splitter);
		List<String> subMarket_list = Arrays.asList(subMarket_splitter);
		List<String> product_list = Arrays.asList(product_splitter);
		
		//System.out.println(product_list);
		List<String> key = new ArrayList<String>();
		List<String> finalkey = new ArrayList<String>();
		int count=0;
		int j = 0;
		int count1=0;
	
		for(int i=0;i<region_list.size();++i)
		{
			
			//System.out.println("count:"+count);
			if(region_list.get(i).equals("Core") )
			{
				for( j=count1; j< count1+5; ++j)
				{
				
					key.add(region_list.get(i) +"_"+ subMarket_list.get(j) );
					count++;
				}
			}
			else	if(region_list.get(i).equals("International"))
			{
				for( j=count1; j< count1+4; ++j)
				{
					key.add(region_list.get(i) +"_"+ subMarket_list.get(j) );
					count++;
				}
			}
			else if(region_list.get(i).equals("GLOBAL"))
			{
				for( j=count1; j< count1+1; ++j)
				{
					key.add(region_list.get(i) +"_"+ subMarket_list.get(j));
					count++;
				}
			}
		count1=count;
		}
		
		System.out.println(key);
	for(int i =0;i<key.size();i++)
	{
		if(i<10)
		{
			finalkey.add("All_"+key.get(i));
		}
		if(i>=10 && i<20)
		{
			finalkey.add("PayPal_"+key.get(i));
		}
		if(i==40)
		{
			finalkey.add("PayPal Credit_"+key.get(i));
		}
		if(i>=45 && i<54)
		{
			finalkey.add("Braintree_"+key.get(i));
		}
		if(i>=54 && i<64)
		{
			finalkey.add("Venmo_"+key.get(i));
		}
		if(i>=64 && i<=70)
		{
			finalkey.add("Xoom_"+key.get(i));
		}
	}
	System.out.println(finalkey.size());
		JSONArray jsonArrayActual = new JSONArray(jsonPath.getString("companies.productGroup.products.regions.subMarkets.metric.type.actual"));
		//System.out.println(jsonArrayActual);
		String[] actual_splitter = jsonArrayActual.getString(0).replaceAll("\\[", "").replaceAll("\\]","").split(",");
		List<String> Actuallist = Arrays.asList(actual_splitter);
//		for(int i =0;i<key.size();++i)
//		{
//		System.out.println((i+1)+"."+Actuallist.get(i));
//		System.out.println((i+1)+"."+key.get(i));
//		}
		System.out.println(Actuallist.size());
//		System.out.println(finalkey.size());
		for(int i=0; i<finalkey.size();i++)
		{
			if(i<20)
				NNAvalues.put(finalkey.get(i), Actuallist.get(i));
			else if(i==20)
				NNAvalues.put(finalkey.get(i), Actuallist.get(i+20));
			else if(i>20)
				NNAvalues.put(finalkey.get(i), Actuallist.get(i+24));
		}
		System.out.println(NNAvalues);
	}

}

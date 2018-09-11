package tmis;

import org.testng.annotations.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class API implements Base {

	@Test(priority=1)
	public void extractAPI() throws IOException  {

		HashMap<String, String> pathParams = new HashMap<String, String>();
		pathParams.put("resource1", "overview");		

		File property_file = new File("./src/test/resources/config.properties"); 
		FileInputStream fileInput = new FileInputStream(property_file); 
		Properties prop = new Properties(); 
		prop.load(fileInput);
		Enumeration<?> e = prop.propertyNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String bodyname=key.toLowerCase();
			File file=new File("./"+bodyname+".json");

			Response response = RestAssured
					.given()
					.pathParams(pathParams)
					.relaxedHTTPSValidation()
					.body(file)
					.contentType(ContentType.JSON)
					.expect()
					.statusCode(200)
					.when()
					.post("https://edstmisserv18030619.qa.paypal.com:20680/v1/edstmisserv/tmisresource_v2/{resource1}");

			JsonPath jsonPath = response.jsonPath();

			TeradataConnection tc=new TeradataConnection(); //to call convertNumbers method


			tc.convertNumbers(Float.valueOf(jsonPath.getString("dashboardDataTable.rows[0].c[1]").replaceAll("\\D", "")), "NNA_"+bodyname,apimap);
			tc.convertNumbers(Float.valueOf(jsonPath.getString("dashboardDataTable.rows[1].c[1]").replaceAll("\\D", "")), "Activations_"+bodyname,apimap);
			tc.convertNumbers(Float.valueOf(jsonPath.getString("dashboardDataTable.rows[4].c[1]").replaceAll("\\D", "")), "Actives 12M_"+bodyname,apimap);
			tc.convertNumbers(Float.valueOf(jsonPath.getString("dashboardDataTable.rows[7].c[1]").replaceAll("\\D", "")), "Re-activations_"+bodyname,apimap);
			tc.convertNumbers(Float.valueOf(jsonPath.getString("dashboardDataTable.rows[10].c[1]").replaceAll("\\D", "")), "Churn_"+bodyname,apimap);

		}

		System.out.println("Fetching of API values completed");
		System.out.println("====================================================================================");
	}

}
package RestAssured;

import java.io.File;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class PostRequest {
	
	@DataProvider(name="file")
	public Object[][] data(){

		Object[][] data=new Object[3][1];

		data[0][0]="body.json";
		data[1][0]="body2.json";
		data[2][0]="body3.json";

		return data;

	}

	@Test(dataProvider="file")
	public void test1(String body){

		//HashMap<String,String> map=new HashMap<String,String>();

		File file=new File("./"+body);

		Response response = RestAssured
				.given()
				.auth()
				.basic("admin", "Smileplease28#")	
				.contentType(ContentType.JSON)
				//.accept(ContentType.XML)
				.body(file)
				.log().all()
				.post("https://dev61953.service-now.com/api/now/table/change_request");

		System.out.println(response.prettyPrint());

		System.out.println(response.statusCode());

		System.out.println(response.getTime());

		JsonPath jsonPath = response.jsonPath();

	}

	
}

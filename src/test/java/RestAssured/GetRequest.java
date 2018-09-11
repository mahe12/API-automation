package RestAssured;

import java.util.List;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetRequest {

	@Test
	public void test1(){

		//		BasicAuthScheme auth=new BasicAuthScheme();
		//
		//		auth.setUserName("admin");
		//
		//		auth.setPassword("Smileplease28#");
		//
		//		RestAssured.authentication=auth;
		
		//HashMap<String,String> map=new HashMap<String,String>();
		
	//	map.put("number", "");

		Response response = RestAssured
				.given()
				.auth()
				//.preemptive()
				.basic("admin", "Smileplease28#")
				
				//.params(map)
				.param("number", "CHG0040001")
				.queryParam("sysparm_fields", "sys_id,number")
				.pathParam("table", "change_request")
				.accept(ContentType.JSON)
				.log().all()
				.get("https://dev61953.service-now.com/api/now/table/{table}");

		System.out.println(response.prettyPrint());

		System.out.println(response.statusCode());

		System.out.println(response.getTime());

		JsonPath JsonPath = response.jsonPath();

		List<Object> list = JsonPath.getList("result.number");
		
		System.out.println(list.size());

		System.out.println(list.get(0));


	}

}
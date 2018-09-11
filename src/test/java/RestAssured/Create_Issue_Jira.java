package RestAssured;

import java.io.File;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Create_Issue_Jira {
	
	public static Object key_val;
	//public Object key_val;
	 
	@DataProvider(name="body")
	public Object[][] createBody()
	{
		Object[][] body = new Object[1][1];		
		body[0][0] = "createIssueBody.json";
		return body;
		
	}
	@Test(dataProvider="body")
	public void createIssue(String body) throws InterruptedException
	{
		File file = new File("./Attachments/"+body);
		Response response = RestAssured
							.given()
							.auth()
							.preemptive()
							.basic("vikrampk11@gmail.com", "Far.ran-00")
							.contentType(ContentType.JSON)
							.body(file)
							.accept(ContentType.JSON)
							.post("https://vikrampk11.atlassian.net/rest/api/2/issue");	
		System.out.println(response.prettyPrint());
		JsonPath JsonPath = response.jsonPath();
		key_val = JsonPath.get("key");
		System.out.println(key_val);
		Thread.sleep(3000);
		

	}

}

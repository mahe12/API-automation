package RestAssured;

import java.io.File;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Modify_Issue_Jira extends Create_Issue_Jira {
	@DataProvider(name="modify")
	public Object[][] addBody()
	{
		Object[][] modify = new Object[1][1];
		modify[0][0] = ("modifyIssuebody.json");
		return modify;
	}
	
	Object key;
	public void modifyKey(String key_value) {
		// TODO Auto-generated method stub
		 key =  key_value;
	}
	@Test(dataProvider = "modify")
	public void modifyIssue(String body)
	{
		File file = new File("./Attachments/"+body);
		Response response = RestAssured.given()
							.auth()
							.preemptive()
							.basic("vikrampk11@gmail.com", "Far.ran-00")
							.contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.body(file)
							.pathParam("key", key)
							.put("https://vikrampk11.atlassian.net/rest/api/2/issue/{key}");
		
		System.out.println(response.prettyPrint());
						
	}

}

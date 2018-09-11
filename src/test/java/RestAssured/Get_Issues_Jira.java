package RestAssured;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Get_Issues_Jira {
	@Test
	public void getIssueData()
	{
	Response response = RestAssured
						.given()
						.auth()
						.preemptive()
						.basic("vikrampk11@gmail.com", "Far.ran-00")
						.pathParam("key", "QS-1")
						//.queryParam(arg0, arg1)
						.get("https://vikrampk11.atlassian.net/rest/api/2/issue/{key}");
	
	System.out.println(response.prettyPrint());
	}
						

}

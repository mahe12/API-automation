package RestAssured;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Delete_Issue_Jira extends Create_Issue_Jira {
	
	@Test
	public void deleteIssue()
	{
		Response response = RestAssured
							.given()
							.auth()
							.preemptive()
							.basic("vikrampk11@gmail.com", "Far.ran-00")
							.pathParam("key", "QS-34")
							.delete("https://vikrampk11.atlassian.net/rest/api/2/issue/{key}");
	}

}

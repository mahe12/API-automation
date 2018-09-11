package RestAssured;

import java.io.File;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;

public class Add_Attachement_Jira  {
	
	
	
	@DataProvider(name="attach")
	public Object[][] addAttachment()
	{
		Object[][] attach = new Object[1][1];
		attach[0][0] = ("button");
		return attach;
	}
	

	@Test(dataProvider="attach")
	public void Add_attachmentTo_issue(String attach)
	{
	
		//System.out.println("hello");
		File file = new File("./Attachments/"+attach);
		//Create_Issue_Jira cr =new Create_Issue_Jira();
		Object key_val = Create_Issue_Jira.key_val;
		System.out.println(key_val);

		RestAssured
		.given()
		.auth()
		.preemptive()
		.basic("vikrampk11@gmail.com", "Far.ran-00")
		.multiPart(file)
		.header(new Header("X-Atlassian-Token","no-check"))
		.pathParam("key", key_val)
		.post("https://vikrampk11.atlassian.net/rest/api/2/issue/{key}/attachments");
	}


}

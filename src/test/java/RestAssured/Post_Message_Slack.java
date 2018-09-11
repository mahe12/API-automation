package RestAssured;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Post_Message_Slack {

	@DataProvider(name="sendMessage")
	public Object[][] body() throws FileNotFoundException, UnsupportedEncodingException, InterruptedException{
		
		
		String url = "https://vikrampk11.atlassian.net/projects/QS/issues/";
		String key_val = (String) Create_Issue_Jira.key_val;
		String issue_url= url+key_val;
		StringBuffer str1 = new StringBuffer ("{\"channel\":\"CBTSS9ZPS\",\"text\":\"\",\"as_user\":\"true\"}");
		str1.insert (31, issue_url);		
		
		PrintWriter writer = new PrintWriter("./Attachments/"+key_val+"_body.json", "UTF-8");
		writer.println(str1);
		writer.close();
		Object[][] message = new Object[1][1];
		message[0][0]= key_val+"_body.json";
		System.out.println("time to sleep");
		return message;
		
	}
	@Test(dataProvider="sendMessage")
	public void sendMessage(String attach) {
	File file = new File("./Attachments/"+attach);	
	//	TO SEND A DIRECT MESSAGE CALL IM.LIST AND GET THE ID. ASSIGN THAT ID TO CHANNEL FIELD IN BODY
	Response response =RestAssured
						.given()
						.auth()
						.preemptive()
						.oauth2("xoxp-402069284177-402381912116-403690102167-7d674f77ea247145f68e2a51ed9ffa58")
						.body(file)
						.contentType(ContentType.JSON)
						.post("https://slack.com/api/chat.postMessage");
	System.out.println(response.prettyPrint());
		
}
}

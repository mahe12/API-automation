package RestAssured;

import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@Test
public class jsonPathTest
{
public void JsonPathUsage() 
{

	Response response = RestAssured.
						given()
						.get( "http://restapi.demoqa.com/utilities/books/getallbooks");
	
	
	// First get the JsonPath object instance from the Response interface
	JsonPath jsonPathEvaluator = response.jsonPath();

	// Read all the books as a List of String. Each item in the list
	// represent a book node in the REST service Response
	List<Book> allBooks = jsonPathEvaluator.getList("books", Book.class);
	//List<Book> allBooks = Arrays.asList(response.getBody().as(Book[].class));
	// Iterate over the list and print individual book item
	// Note that every book entry in the list will be complete Json object of book
	for(Book book : allBooks)
	{
		System.out.println( book.title +" --- "+ book.author);
	}
}
}


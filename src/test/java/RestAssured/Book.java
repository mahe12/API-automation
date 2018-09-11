package RestAssured;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Book {

	@JsonProperty  String isbn;
	@JsonProperty String title;
	@JsonProperty String subtitle;
	@JsonProperty String author;
	@JsonProperty String published;
	@JsonProperty String publisher;
	@JsonProperty  int pages;
	@JsonProperty  String description;
	@JsonProperty  String website;
}

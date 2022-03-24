package API_test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class herokuapp {
	public static String baseurl = "https://ecommerceservice.herokuapp.com/";
    public Object accessToken;
	public Object id;

	@Test(priority = 0,enabled = false)
	public void signup()
	{
		RestAssured.baseURI = baseurl;
		
		String requestbody = "{\r\n"
				+ "	\"email\": \"kishu176@gmail.com\",\r\n"
				+ "	\"password\": \"kishuj@123\"\r\n"
				+ "}\r\n"
				+ "";
		
		//this time i want to know what my response is in my console
		//there is interface Response fetches o/p frm get/ other method the body and stores in a variable called response
		Response response =given()
				.header("content-type","application/json")
				.body(requestbody)
				
		.when()
		.post("/user/signup")
		
		.then()
		.assertThat().statusCode(201).and().contentType(ContentType.JSON).
		extract().response();
		//is to print the response
		//basically the output format by default
		System.out.println(response.asString());
	}

	@Test(priority = 1)
	public void login()
	{
		RestAssured.baseURI = baseurl;
		
		String requestbody ="{\r\n"
				+ "	\"email\": \"kishu176@gmail.com\",\r\n"
				+ "	\"password\": \"kishuj@123\"\r\n"
				+ "}\r\n"
				+ "";
		// this time i want to know my what response is in my console
		Response response = given()
		  .header("content-Type","application/json")
		  .body(requestbody)
		
		  .when()
		  .post("/user/login")
		
		  .then()
		  .assertThat().statusCode(200).contentType(ContentType.JSON)
		  .extract().response();
		String jsonresponse = response.asString();
		JsonPath responsebody = new JsonPath(jsonresponse);
		System.out.println(responsebody.get("accessToken"));	
		accessToken = responsebody.get("accessToken");
	}
	
	@Test(priority = 2)
	public void get_all_orders() {
		
		RestAssured.baseURI = baseurl;
		Response response = given().
				header("content-Type","application/json")
				 .header("Authorization","bearer "+accessToken)
				
				.when()
				.get("/orders")
				
				.then()
				.assertThat().statusCode(200).and().contentType(ContentType.JSON)
				
				.extract().response();
		String body = response.asString();
		//System.out.println(body);
		JsonPath data =  new JsonPath(body);
		Object name = data.get("name");
		System.out.println(name);
	}
	
	@Test(priority = 3)
	public void create_order() {
		
		RestAssured.baseURI = baseurl;
		
		String requestbody ="{\r\n"
				+ "	\"product\": \"622831f1d7cea500172f1170\",\r\n"
				+ "	\"quantity\": 50\r\n"
				+ "}\r\n"
				+ "";
		Response response = given().
				header("content-Type","application/json")
				 .header("Authorization","bearer "+accessToken)
				 .body(requestbody)
					
				.when()
				.post("/orders")
				
				.then()
				.assertThat().statusCode(201).and().contentType(ContentType.JSON)
				
				.extract().response();
		String body = response.asString();
		System.out.println(body);
		JsonPath data =  new JsonPath(body);
		id = data.get("order._id");
		System.out.println(id);
	}
	

	@Test(priority = 4)
	public void get_order_by_id() {
		
		RestAssured.baseURI = "https://ecommerceservice.herokuapp.com";
		
		Response response = given().
				header("content-Type","application/json")
				 .header("Authorization","bearer "+accessToken)
				
				.when()
				.get("/orders/"+id)
				
				.then()
				.assertThat().statusCode(201).and().contentType(ContentType.JSON)
				
				.extract().response();
		String body = response.asString();
		System.out.println(body);
		/*JsonPath data =  new JsonPath(body);
		Object order_name = data.get("name");
		System.out.println(order_name);*/
	}
	
	@Test(priority = 4)
	public void update_order_by_id() {
		
		RestAssured.baseURI = "https://ecommerceservice.herokuapp.com";
		
		String requestbody ="{\r\n"
				+ "	\"product\": \"622831f1d7cea500172f1170\",\r\n"
				+ "	\"quantity\": 501\r\n"
				+ "}\r\n"
				+ "";

		
		Response response = given().
				header("content-Type","application/json")
				 .header("Authorization","bearer "+accessToken)
				 .body(requestbody)
				
				.when()
				.patch("/orders/"+id)
				
				.then()
				.assertThat().statusCode(200).and().contentType(ContentType.JSON)
				
				.extract().response();
		String body = response.asString();
		//System.out.println(body);
		JsonPath data =  new JsonPath(body);
		Object update = data.get("message");
		System.out.println(update);
	}
	
	
	@Test(priority = 5)
	public void delete() {
		
		RestAssured.baseURI = "https://ecommerceservice.herokuapp.com";
		
		Response response = given().
				header("content-Type","application/json")
				 .header("Authorization","bearer "+accessToken)
				
				.when()
				.delete("/orders/"+id)
				
				.then()
				.assertThat().statusCode(200).and().contentType(ContentType.JSON)
				
				.extract().response();
		String body = response.asString();
		//System.out.println(body);
		JsonPath data =  new JsonPath(body);
		Object delete = data.get("message");
		System.out.println(delete);
	}
}
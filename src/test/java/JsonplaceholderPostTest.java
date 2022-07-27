import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonplaceholderPostTest {

    private static Faker faker;
    private String fakerTitle;
    private String fakerUrl;
    private final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private final String PHOTOS = "photos";

    @BeforeAll
    public static void beforeAll () {
        faker = new Faker();
    }

    @BeforeEach
    public void beforeEach() {
        fakerTitle = faker.name().title();
        fakerUrl = faker.internet().url();
    }

    @Test
    public void postPhotos(){

        System.out.println(fakerTitle);

        JSONObject photo = new JSONObject();
        photo.put("albumId", 1);
        photo.put("title", fakerTitle);
        photo.put("url", fakerUrl);
        photo.put("thumbnailUrl", fakerUrl);


        Response response = given()
                .contentType("application/json")
                .body(photo.toString())
                .when()
                .post(BASE_URL + "/" + PHOTOS)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals(fakerTitle, json.get("title"));
        assertEquals(fakerUrl, json.get("url"));
        assertEquals(fakerUrl, json.get("thumbnailUrl"));
    }
}

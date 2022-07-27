import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class JsonplaceholderPUTPATCHTest {

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
    public void updatePhotoPutTest(){

        JSONObject photo = new JSONObject();
        photo.put("albumId", 1);
        photo.put("title", fakerTitle);
        photo.put("url", fakerUrl);
        photo.put("thumbnailUrl", fakerUrl);

        Response response = given()
                .pathParam("photoId", 1)
                .contentType("application/json")
                .when()
                .body(photo.toString())
                .put(BASE_URL + "/" + PHOTOS + "/{photoId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        Assertions.assertEquals(fakerTitle, json.get("title"));
        Assertions.assertEquals(fakerUrl, json.get("url"));
        Assertions.assertEquals(fakerUrl, json.get("thumbnailUrl"));
    }

    @Test
    public void updatePhotoPatchTest (){

        JSONObject photo = new JSONObject();
        photo.put("title", fakerTitle);

        Response response = given()
                .pathParam("photoId", 1)
                .contentType("application/json")
                .body(photo.toString())
                .when()
                .patch(BASE_URL + "/" + PHOTOS + "/{photoId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        Assertions.assertEquals(fakerTitle, json.get("title"));
    }
}

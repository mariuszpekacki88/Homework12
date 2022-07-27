import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonplaceholderGetTest {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private final String PHOTOS = "photos";

    @Test
    public void getOnePhotos() {
        Response response = given()
                .pathParam("photoId", 1)
                .when()
                .get(BASE_URL + "/" + PHOTOS + "/{photoId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals("accusamus beatae ad facilis cum similique qui sunt", json.get("title"));
        assertEquals("https://via.placeholder.com/600/92c952", json.get("url"));
        assertEquals("https://via.placeholder.com/150/92c952", json.get("thumbnailUrl"));

        System.out.println(response.asString());
    }

    @Test
    public void getAllPhotos (){

        Response response = given()
                .when()
                .get(BASE_URL + "/" + PHOTOS )
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        List<String> titles = json.getList("name");
        assertEquals(5000, titles.size());
    }

    @Test
    public void getPhotoWithQueryParams(){
        Response response = given()
                .queryParam("title", "ad et natus qui")
                .when()
                .get(BASE_URL + "/" + PHOTOS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals("ad et natus qui", json.getList("title").get(0));
    }
}
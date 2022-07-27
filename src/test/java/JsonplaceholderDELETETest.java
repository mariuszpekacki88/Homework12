import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

public class JsonplaceholderDELETETest {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private final String PHOTOS = "photos";

    @Test
    public void DeletePhoto(){
         given()
                .pathParam("photoId", 1)
                .when()
                .delete(BASE_URL + "/" + PHOTOS + "/{photoId}")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}

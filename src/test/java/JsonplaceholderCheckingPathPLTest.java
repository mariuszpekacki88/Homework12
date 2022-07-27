import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

public class JsonplaceholderCheckingPathPLTest {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private final String USER = "users";

    @Test
    public void checkingEmailItDoesNotContainPL(){
        Response response = given()
                .when()
                .get(BASE_URL + "/" + USER)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        List<String> emails = json.getList("email");

        boolean endsWithPLStream = emails.stream()
                .anyMatch(email -> email.endsWith("pl"));
                assertEquals(false, endsWithPLStream);

        for (String email:emails){
            boolean endsWithPLFor = email.endsWith("pl");
            assertEquals(false, endsWithPLFor);
        }
    }
}
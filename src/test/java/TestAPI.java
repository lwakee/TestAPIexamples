import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class TestAPI {

    @Test
    void shouldCheckSpoonAcularAPI() {

        given()
                .baseUri("https://api.spoonacular.com")
                .header("x-api-key", "f1252da49b1940b0880122422f2b78ff")
                .param("query","pasta")
                .param("maxFat","25")
                .param("number",2)
                .when()
                .get("/recipes/complexSearch")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("results[0].id", equalTo(642583))
                .body("results[0].title", equalTo("Farfalle with Peas, Ham and Cream"));
    }

    @Test
    void shouldCheckCardOrdering() {
       // File file = new File("");
        InputStream testData = this.getClass().getClassLoader().getResourceAsStream("testdata.json");
        given()
                .baseUri("https://functions.yandexcloud.net")
                .body(testData)
                .with()
                .contentType(ContentType.JSON)
                .when()
                .post("/d4e8qsrmeednndemfsus")
                .then()
                .statusCode(200)
                .body("data.name",equalTo("Kvkom"))
                .body("success",equalTo(true));

    }
    @Test
    void shouldLogResponseForRequestBySpoonacularAPI() {
        given()
                .baseUri("https://api.spoonacular.com")
                .header("x-api-key", "f1252da49b1940b0880122422f2b78ff")
                .param("query", "pasta")
                .param("maxFat", "25")
                .param("number", 2)
                .when()
                .get("/recipes/complexSearch")
                .then()
                .log()
                .all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .header("content-type", "application/json");
    }
    @Test
    void shouldCheckBankApi(){
        InputStream RESP = this.getClass().getClassLoader().getResourceAsStream("get.json");
        given()
                .baseUri("http://localhost:9999")
                .body(RESP)
                .with()
                .contentType(ContentType.JSON)
                //.param("id", 2)
                .when()
                .get("/api/v1/demo/accounts")
                .then()
                .statusCode(200)
                .body("[0].balance", equalTo(992821429));
    }
    @Test
    void schemaCheck(){
        InputStream testData = this.getClass().getClassLoader().getResourceAsStream("testdata.json");
        given()
                .baseUri("https://functions.yandexcloud.net")
                .body(testData)
                .with()
                .contentType(ContentType.JSON)
                .when()
                .post("/d4e8qsrmeednndemfsus")
                .then()
                .statusCode(200)
                .assertThat().body(matchesJsonSchemaInClasspath("schema.json"))
        ;
    }
}

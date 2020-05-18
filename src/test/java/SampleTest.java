import base.ApplicationConfiguration;
import base.Payload;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import com.fasterxml.jackson.databind.ObjectMapper;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@TestPropertySource("classpath:config.properties")
public class SampleTest {

    @Autowired
    ApplicationConfiguration applicationConfiguration;

    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void testGetRequest(){
        given()
                .get(applicationConfiguration.getURI())
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("title[0]",equalTo("Test title"));
    }

    @Test
    public void testPutRequest() throws JsonProcessingException {

        Payload payload = new Payload(1,"Yellow Bird", "Rebecca Serle");
        String mydata = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(payload);
        given().
                header("Content-Type","application/json")
                .body(mydata)
                .put("http://localhost:3000/posts/1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("author",equalTo("Rebecca Serle"));
        revertUpdateChanges();
    }


    @Test
    public void testPostRequest() throws JsonProcessingException {
        Payload payload = new Payload(11,"In Five Years", "Rebecca Serle");
        String mydata = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(payload);

        given()
                .header("Content-Type","application/json")
                .body(mydata)
                .post(applicationConfiguration.getURI())
                .then()
                .statusCode(HttpStatus.SC_CREATED);

        given()
                .get(applicationConfiguration.getURI()+"11")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id",equalTo(11))
                .body("title",equalTo("In Five Years"))
                .body("author",equalTo("Rebecca Serle"));

    }


    @Test
    public void testDeleteRequest() {

        given().
                header("Content-Type","application/json").
                delete("http://localhost:3000/posts/11").
                then().
                statusCode(HttpStatus.SC_OK);

         given()
                 .get("http://localhost:3000/posts/11")
                 .then()
                 .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    public void revertUpdateChanges() throws JsonProcessingException {
        Payload payload = new Payload(1,"Test title", "Test author");
        String mydata = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(payload);
        given().
                header("Content-Type","application/json")
                .body(mydata)
                .put("http://localhost:3000/posts/1")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

}
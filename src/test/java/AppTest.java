import base.ApplicationConfiguration;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import static io.restassured.RestAssured.given;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.CoreMatchers.*;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@TestPropertySource("classpath:application.properties")
public class AppTest {

@Autowired
ApplicationConfiguration applicationConfiguration;


    @Test
    public void shouldAnswerWithTrue(){
        Response response = RestAssured.get("https://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22");
        int code = response.getStatusCode();
        System.out.println("Status code is --> " + code);
        System.out.println("Status code is --> " + response.body().print());
        Assert.assertEquals(200,code);
    }

    @Test
    public void checkResponseTest(){
       String temperature =  given()
                .queryParam("q",applicationConfiguration.getLocation())
                .queryParam("appid",applicationConfiguration.getAppid())
                .get(applicationConfiguration.getURI()+ applicationConfiguration.getWeatherEndPoint())
                .then()
                .statusCode(HttpStatus.SC_OK).extract().path("main.temp").toString();

       Assert.assertEquals(temperature,"280.32");

    }

    @Test
    public void verify(){
        given()
                .queryParam("q",applicationConfiguration.getLocation())
                .queryParam("appid",applicationConfiguration.getAppid())
                .get(applicationConfiguration.getURI()+ applicationConfiguration.getWeatherEndPoint())
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}

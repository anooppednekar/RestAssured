package base;



import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@Configuration
@Getter
public class ApplicationConfiguration {

@Value("${URI}")
private String URI;

@Value("${weatherEndPoint}")
private String weatherEndPoint;

@Value("${location}")
private String location;

@Value("${appid}")
private String appid;



}

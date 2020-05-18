package base;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payload {

    int id;
    String title;
    String author;

    public Payload(int id, String title, String author){
        this.id= id;
        this.title = title;
        this.author = author;
    }

}

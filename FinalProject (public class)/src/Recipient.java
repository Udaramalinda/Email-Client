import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.time.DateTimeException;

public abstract class Recipient {

    protected String name;
    protected InternetAddress email;


    Recipient(String name, String email) throws AddressException , DateTimeException {
        // get the data that want to make a recipient
        this.name = name;
        this.email = new InternetAddress(email);
    }

    public String getName(){
        return this.name;
    }




}

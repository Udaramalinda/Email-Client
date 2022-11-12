import Email.Email;
import javax.mail.internet.AddressException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.MonthDay;

public class PersonalRecipient extends Recipient implements BirthdayWish{
    private String nick_name;
    private LocalDate birthday;


    public PersonalRecipient(String name, String nick_name, String email, LocalDate birthday) throws AddressException, DateTimeException {
        // Get the data that want to make personal friend
        //other are send to the super class
        super(name, email);
        try {

            this.nick_name = nick_name;
            this.birthday = birthday;
        }catch(DateTimeException a){
            System.out.println("Birthday is Invalid....!");
        }

    }

    public MonthDay getBirthday(){

        return MonthDay.from(this.birthday);
    }

    public Email wish() throws AddressException{
        // return the email object that want to wish if he/she has birthday

        String subject = "Happy Birthday";
        String body = "Hugs and Love on your Birthday " + this.name + "\n" + "-Udara Wijesinghe";

        Email email = new Email(""+this.email,subject,body);
        return email;

    }

}

import Email.Email;
import javax.mail.internet.AddressException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.MonthDay;

public class OfficialFriendRecipient extends OfficialRecipient implements BirthdayWish{

    private LocalDate birthday;

    public OfficialFriendRecipient(String name, String email, String designation, LocalDate birthday) throws AddressException, DateTimeException{
        // Get the data that want to make official friend
        //other are send to the super class
        super(name, email, designation);
        try {
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
        String body = "Wish your a Happy Birthday " + this.name + "\n" + "-Udara Wijesinghe";

        Email email = new Email(""+this.email,subject,body);
        return email;

    }





}

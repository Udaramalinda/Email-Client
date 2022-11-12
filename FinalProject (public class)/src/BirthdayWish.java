import Email.Email;

import javax.mail.internet.AddressException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.MonthDay;

public interface BirthdayWish {

    MonthDay getBirthday();

    String getName();

    Email wish() throws AddressException, DateTimeException;
}

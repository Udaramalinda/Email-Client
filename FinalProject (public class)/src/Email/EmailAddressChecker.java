package Email;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class EmailAddressChecker {

    public boolean addressValid(String mail){
        boolean address_valid = true;

        try{
            // Check for the gives email is correct
            InternetAddress validAddress = new InternetAddress(mail, true);

        }catch(AddressException a){
            // if it is not correct return false
            address_valid = false;
        }
        return address_valid;
    }
}

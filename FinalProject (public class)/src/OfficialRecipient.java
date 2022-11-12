import javax.mail.internet.AddressException;

public class OfficialRecipient extends Recipient{

    protected String designation;

    public OfficialRecipient(String name, String email, String designation) throws AddressException {
        // Get the data that want to make official friend
        //other are send to the super class
        super( name, email );
        this.designation = designation;

    }


}

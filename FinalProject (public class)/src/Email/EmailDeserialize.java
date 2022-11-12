package Email;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class EmailDeserialize {

    private HashMap<LocalDate, ArrayList<Email>> allEmailWithDates;

    public EmailDeserialize() {

        try {
            // read the email object from the file that email objects are serialized
            FileInputStream fileStream = new FileInputStream("EmailSerialize.ser");

            ObjectInputStream os = new ObjectInputStream(fileStream);
            // get and give this to allEmailsWithDates
            this.allEmailWithDates = (HashMap<LocalDate, ArrayList<Email>>) os.readObject();

            fileStream.close();

        } catch (IOException i) {
            i.printStackTrace();

        } catch (ClassNotFoundException c) {
            System.out.println("Email class not found");
            c.printStackTrace();
            return;
        }
    }

    public HashMap<LocalDate, ArrayList<Email>> getAllEmailWithDates(){
        return this.allEmailWithDates;
    }
}

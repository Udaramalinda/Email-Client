package Email;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class EmailSerialize {

    public EmailSerialize() {

    }

    public void writeObjects(HashMap<LocalDate, ArrayList<Email>> allEmailWithDates){
        // write the email objects at the file that are want to serialize

        try {
            FileOutputStream fileStream = new FileOutputStream("EmailSerialize.ser");

            ObjectOutputStream os = new ObjectOutputStream(fileStream);

            os.writeObject( allEmailWithDates );

            fileStream.close();

            System.out.println("DONE.....!");

        } catch (IOException i) {
            i.printStackTrace();
        }

    }


}

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TextWriter {


    public void textOutputWrite(String data) {
        try {
            // write the text in the text file
            FileWriter writer = new FileWriter("clientList.txt", true);
            // store temprary at the buffer
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.write(data);
            bufferedWriter.newLine();
            bufferedWriter.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TextReader {

    private ArrayList<String> dataLines = new ArrayList<String>();



    public TextReader(){

        try {
            // read from text file
            FileReader reader = new FileReader("clientList.txt");
            // store at the buffer
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                // add the data line to the dataLines array
                dataLines.add(line);
            }
            bufferedReader.close();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }

    }

    public ArrayList<String> getDataLines(){
        return dataLines;
    }
}

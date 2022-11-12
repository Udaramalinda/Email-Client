import Email.EmailAddressChecker;

import javax.mail.internet.AddressException;
import java.lang.*;
import java.lang.reflect.Array;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.*;

// Use Factory Method
public class RecipientBuilder {
    // Object for read input from text file
    private TextReader textReader;

    // Object for write to text file
    private TextWriter textWriter;
    // Object for check the email validity
    private EmailAddressChecker emailAddressChecker;
    // String data lines that are read from the text file
    private ArrayList<String> dataLines;
    // All the recipient object array
    private ArrayList<Recipient> recipients_array;
    // Array for who has birthday all the time
    private HashMap<MonthDay, ArrayList<BirthdayWish>> forBirthdayWish;

    RecipientBuilder() throws AddressException{

        // create one object from all these (SIGLETON Design Pattern)
        recipients_array = new ArrayList<Recipient>();
        forBirthdayWish = new HashMap<MonthDay, ArrayList<BirthdayWish>>();
        dataLines = new ArrayList<String>();
        textReader = new TextReader();
        textWriter = new TextWriter();
        emailAddressChecker = new EmailAddressChecker();

        // run the get input method for read text file
        getTextInput();
    }

    public boolean createObject(String data) throws AddressException{
        Recipient recipient = null;

        try{
        // Divide string by ':'
        data = data.replaceAll("\\s", "");

        String[] data_array = data.split(":");

        String recipient_type = data_array[0].toLowerCase();
        String recipient_data = data_array[1];

        //Choose type of them
        if (recipient_type.equalsIgnoreCase("Official")){
            // Divide string by ','
            String[] data_list = recipient_data.split(",");
            String name = data_list[0];
            String email = data_list[1];
            String designation = data_list[2];

            //Check the email is valid
            boolean validEmail = emailAddressChecker.addressValid(email);
            // if the email valid then create the object otherwise not
            if(validEmail) {

                recipient = new OfficialRecipient(name, email, designation);
                recipients_array.add(recipient);

            }
            else if (! validEmail) {
                System.out.println("Email Address is not Valid....!");
                return false;
            }

        }
        // Same as above 'Official friend'
        else if (recipient_type.equalsIgnoreCase("Office_friend")){
            String[] data_list = recipient_data.split(",");
            String name = data_list[0];
            String email = data_list[1];
            String designation = data_list[2];
            String birthday = data_list[3];
            // check the birthday is valid
            birthday = birthday.replace("/","-");
            LocalDate birthdayConverted = LocalDate.parse(birthday);

            boolean validEmail = emailAddressChecker.addressValid(email);
            if(validEmail) {
                recipient = new OfficialFriendRecipient(name, email, designation, birthdayConverted);
                recipients_array.add(recipient);

            }
            else if (! validEmail) {
                System.out.println("Email Address is not Valid....!");
                return false;
            }

        }
        // Same as above 'Personal'
        else if (recipient_type.equalsIgnoreCase("Personal")){
            String[] data_list = recipient_data.split(",");
            String name = data_list[0];
            String nickName = data_list[1];
            String email = data_list[2];
            String birthday = data_list[3];
            // check the birthday is valid
            birthday = birthday.replace("/","-");
            LocalDate birthdayConverted = LocalDate.parse(birthday);

            boolean validEmail = emailAddressChecker.addressValid(email);
            if (validEmail) {
                recipient = new PersonalRecipient(name, nickName, email, birthdayConverted);
                recipients_array.add(recipient);

            }
            else if (! validEmail) {
                System.out.println("Email Address is not Valid....!");
                return false;
            }

        }
        // If the type of recipient is not valid
        else{
            System.out.println("Invalid Category");
            return false;
        }
        // If the array is out of range
        }catch(ArrayIndexOutOfBoundsException a){
            System.out.println("Invalid Input......!");
            return false;
        }catch(NullPointerException b){
            System.out.println("Birthday is Invalid....!");
            return false;
        }catch(DateTimeException c){
            System.out.println("Birthday is Invalid....!");
            return false;
        }


        // check the recipient has a birthday (Can he wish)
        if ( recipient instanceof BirthdayWish){
            // if can we wish him then add to the forBirthdayWish array By date
            // it was divide by month and date only
            MonthDay date = ((BirthdayWish) recipient).getBirthday();

            if (forBirthdayWish.get(date) == null){
                ArrayList<BirthdayWish> array = new ArrayList<BirthdayWish>();
                array.add((BirthdayWish) recipient);
                forBirthdayWish.put(date,array);
            }
            else{
                forBirthdayWish.get(date).add((BirthdayWish) recipient);
            }
        }
        return true;

    }

    public ArrayList<BirthdayWish> todayBirthday(LocalDate date){
        // This is use for wish if this recipient has birthday today
        MonthDay today = MonthDay.from(date);

        // get the today birthday list and return to Email_Client
        ArrayList<BirthdayWish> todayBirthdays = forBirthdayWish.get(today);

        return todayBirthdays;
    }

    public void checkForWrite(String data) throws AddressException{

        //If the user input are correct then add to the text file
        boolean build = createObject(data);
        if (build) {
            textWriter.textOutputWrite(data);
        }

    }

    public void getTextInput() throws AddressException{

        // this is what read from text file that is the data line
        this.dataLines = textReader.getDataLines();
        int sizeOfDataArray = dataLines.size();

        //by using data line array and create the recipient one by one
        if (sizeOfDataArray > 0) {

            for (int i = 0; i < sizeOfDataArray; i++) {
                createObject(dataLines.get(i));
            }
        }
    }

    public void printBirthday(String date){

        //this for email_client case 3
        //try the entered date is valid

        try {

            date = date.replace("/", "-");
            MonthDay monthDay = MonthDay.from(LocalDate.parse(date));
            // get the month and date to get the birthdays

            ArrayList<BirthdayWish> todayBirthday = forBirthdayWish.get(monthDay);

            // if the todayBirthday array is empty
            if (todayBirthday == null) {
                System.out.println("This day haven't any birthday....!");
            } else {
                // else print the name of them one by one

                int sizeTodayBirthdayArray = todayBirthday.size();

                for (int i = 0; i < sizeTodayBirthdayArray; i++) {
                    String name = todayBirthday.get(i).getName();
                    System.out.println(name);
                }
            }
        }catch(DateTimeException b){
            // for check the dat eis valid
            System.out.println("The Date is Not Valid....!");
        }

    }
    // for get the number of recipients
    public int getNumberOfRecipient(){
        return recipients_array.size();
    }



}

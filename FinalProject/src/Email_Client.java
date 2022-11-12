// Name: Udara Malinda Wijesinghe
// Index Number: 200727M
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Email_Client {

    public static void main(String[] args) throws AddressException, DateTimeException {

        // Create a Recipient Builder Object and Email Builder Object
        RecipientBuilder recipientBuilder = new RecipientBuilder();
        EmailBuilder emailBuilder = new EmailBuilder();

        // get the today date for send the birthday wishes
        LocalDate today = LocalDate.now();

        // get the today's birthday list
        ArrayList<BirthdayWish> todayBirthdays = recipientBuilder.todayBirthday(today);

        if (todayBirthdays == null){
            // today haven't any birthday
            System.out.println("Today haven't any birthdays to wish.....");
        }
        else{
            // if today have birthday wish them one by one
            int sizeTodayBirthdays = todayBirthdays.size();

            for (int i=0; i < sizeTodayBirthdays; i++){

                // wish by using wish method that returns email object
                // That that object gives to isThisSend and send wish
                Email email = todayBirthdays.get(i).wish();
                emailBuilder.isThisSend(email,today);
            }
            System.out.println("Finish wishing for birthdays....");
        }


        // After wishing all of them then entered to while loop here

        while (true) {
            try {

                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter option type: \n"
                        + "1 - Adding a new recipient\n"
                        + "2 - Sending an email\n"
                        + "3 - Printing out all the recipients who have birthdays\n"
                        + "4 - Printing out details of all the emails sent\n"
                        + "5 - Printing out the number of recipient objects in the application\n"
                        + "6 - Exit the program");

                int option = scanner.nextInt();




                switch (option) {
                    case 1:
                        // input format - Official: nimal,nimal@gmail.com,ceo
                        // Use a single input to get all the details of a recipient
                        // code to add a new recipient
                        // store details in clientList.txt file
                        // Hint: use methods for reading and writing files

                        System.out.println("Enter The Recipient Data: ");

                        Scanner read_data = new Scanner(System.in);
                        String data = read_data.nextLine();

                        // gives data for check and add
                        recipientBuilder.checkForWrite(data);

                        // Recipient Array check method
                        //inOutHandler.printArray();

                        break;


                    case 2:
                        // input format - email, subject, content
                        // code to send an email

                        System.out.println("Enter Email Details: Email Address, Subject, Content");

                        Scanner read_email_data = new Scanner(System.in);
                        String email_data = read_email_data.nextLine();

                        // gives data for check and send the email
                        emailBuilder.createEmailObject(email_data);

                        break;


                    case 3:
                        // input format - yyyy/MM/dd (ex: 2018/09/17)
                        // code to print recipients who have birthdays on the given date
                        try {

                            System.out.println("Enter The Date: yyyy/MM/dd");

                            Scanner read_birthday = new Scanner(System.in);
                            String birthday = read_birthday.nextLine();

                            // Check the birthday and give the name of them
                            recipientBuilder.printBirthday(birthday);

                            break;

                        }catch(DateTimeParseException a){
                            System.out.println("Enter The Valid Input....!");

                        }catch(DateTimeException b){
                            System.out.println("The Date is Not Valid....!");
                        }

                    case 4:
                        // input format - yyyy/MM/dd (ex: 2018/09/17)
                        // code to print the details of all the emails sent on the input date
                        try {
                            System.out.println("Enter the Date: yyyy/MM/dd");

                            Scanner read_emailDate = new Scanner(System.in);
                            String emailDate = read_emailDate.nextLine();

                            // Check the date and give the emails to them
                            emailBuilder.printEmail(emailDate);

                            break;
                        }catch (DateTimeParseException a){
                            System.out.println("Enter The Valid Input....!");
                        }catch(DateTimeException b){
                            System.out.println("The Date is Not Valid....!");
                        }

                    case 5:
                        // code to print the number of recipient objects in the application

                        System.out.println("Number Of Recipients in Program: " + recipientBuilder.getNumberOfRecipient());

                        break;

                    case 6:

                        // Serialized all the email objects and close it
                        emailBuilder.doSerialize();

                        System.out.println("Program Close....! ");
                        return;

                }
            }catch(InputMismatchException e){
                // Check the chosen input is correct
                System.out.println("Invalid Input Enter The Valid Number......!");
            }
        }



        // start email client
        // code to create objects for each recipient in clientList.txt
        // use necessary variables, methods and classes

    }
}

// create more classes needed for the implementation (remove the  public access modifier from classes when you submit your code)
//---------------------------------------------------------------------------------------------------------------------------
class RecipientBuilder {
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
//---------------------------------------------------------------------------------------------------------------------------
abstract class Recipient {

    protected String name;
    protected InternetAddress email;


    Recipient(String name, String email) throws AddressException , DateTimeException {
        // get the data that want to make a recipient
        this.name = name;
        this.email = new InternetAddress(email);
    }

    public String getName(){
        return this.name;
    }

}
//---------------------------------------------------------------------------------------------------------------------------
class OfficialRecipient extends Recipient{

    protected String designation;

    public OfficialRecipient(String name, String email, String designation) throws AddressException {
        // Get the data that want to make official friend
        //other are send to the super class
        super( name, email );
        this.designation = designation;
    }
}
//---------------------------------------------------------------------------------------------------------------------------
class OfficialFriendRecipient extends OfficialRecipient implements BirthdayWish{

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
        String body = "Wish you a Happy Birthday " + this.name + "\n" + "-Udara Wijesinghe";

        Email email = new Email(""+this.email,subject,body);
        return email;
    }
}
//---------------------------------------------------------------------------------------------------------------------------
class PersonalRecipient extends Recipient implements BirthdayWish{
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
//---------------------------------------------------------------------------------------------------------------------------
interface BirthdayWish {

    MonthDay getBirthday();

    String getName();

    Email wish() throws AddressException, DateTimeException;
}
//---------------------------------------------------------------------------------------------------------------------------
class TextReader {

    private ArrayList<String> dataLines = new ArrayList<String>();

    public TextReader(){
        FileReader reader = null;
        BufferedReader bufferedReader = null;


        try {
            // read from text file
            reader = new FileReader("clientList.txt");
            // store at the buffer
            bufferedReader = new BufferedReader(reader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                // add the data line to the dataLines array
                dataLines.add(line);
            }
            bufferedReader.close();
        }
        catch(IOException ex) {
            System.out.println("File Not Found....!");
            ex.printStackTrace();

        }finally{
            try{
                if (reader != null){
                    reader.close();
                }
                if(bufferedReader != null){
                    bufferedReader.close();
                }
            }catch(IOException cd){
                cd.printStackTrace();
            }
        }

    }

    public ArrayList<String> getDataLines(){
        return dataLines;
    }
}
//---------------------------------------------------------------------------------------------------------------------------
class TextWriter {


    public void textOutputWrite(String data) {
        FileWriter writer = null;
        BufferedWriter bufferedWriter = null;
        try {
            // write the text in the text file
            writer = new FileWriter("clientList.txt", true);
            // store temprary at the buffer
            bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.write(data);
            bufferedWriter.newLine();
            bufferedWriter.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally{
            try{
                if (writer != null){
                    writer.close();
                }
                if (bufferedWriter != null){
                    bufferedWriter.close();
                }
            }catch(IOException cd){
                cd.printStackTrace();
            }

        }
    }
}
//---------------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------------

class Email implements Serializable {

    private InternetAddress emailAddress;
    private String subject;
    private String content;
    private LocalDate sentDate;

    public Email( String emailAddress, String subject, String content ) throws AddressException {
        // get the inputs that want to make a email object
        // coverts the email string to the Internet address object type
        this.emailAddress = new InternetAddress(emailAddress);
        this.subject = subject;
        this.content = content;
        // create the attribute that the time that email object create
        this.sentDate = LocalDate.now();
    }

    public InternetAddress getEmailAddress(){
        return this.emailAddress;
    }

    public String getSubject(){
        return this.subject;
    }

    public String getContent(){
        return this.content;
    }

    public LocalDate getSentDate(){
        return this.sentDate;
    }

}
//---------------------------------------------------------------------------------------------------------------------------

class EmailBuilder {
    // hash map for email objects divide by the dates
    private HashMap<LocalDate, ArrayList<Email>> allEmailWithDates;
    // create object to sent the emails
    private EmailSend emailSend;
    // create the object for email serialize
    private EmailSerialize emailSerialize;
    // create the object for email deserialize

    private EmailDeserialize emailDeserialize;
    // create the object for the check for email validity
    private EmailAddressChecker emailAddressChecker;

    public EmailBuilder()  {

        this.emailSerialize = new EmailSerialize();
        this.emailDeserialize = new EmailDeserialize();

        // get the deserialize object
        HashMap<LocalDate, ArrayList<Email>> temp = emailDeserialize.getAllEmailWithDates();
        if (temp == null){
            this.allEmailWithDates = new HashMap<>();
        }
        else{
            this.allEmailWithDates = temp;
        }

        this.emailAddressChecker = new EmailAddressChecker();

        this.emailSend = new EmailSend();
    }

    public void createEmailObject(String emailData) throws AddressException {
        try {

            String[] eachEmailData = emailData.split(",");

            String emailAddress = eachEmailData[0];
            String subject = eachEmailData[1];
            String content = eachEmailData[2];

            // check the email address is valid

            boolean validAddress = emailAddressChecker.addressValid(emailAddress);

            if ( validAddress == true) {
                //if it is valid then create the email object
                Email email = new Email(emailAddress, subject, content);
                // then save the email object and send it
                saveEmailObjects(email);
            }
            else{
                System.out.println("Email Address is not Valid....!");
            }
        }catch(ArrayIndexOutOfBoundsException a){
            System.out.println("Enter Input are invalid....!");
        }
    }
    public void isThisSend(Email email, LocalDate today){
        // This for check the email that him/her wish once
        ArrayList<Email> todayEmails = allEmailWithDates.get(today);
        int sizeOfTodayEmails;
        if (todayEmails == null){
            sizeOfTodayEmails=0;
        }
        else{
            sizeOfTodayEmails = todayEmails.size();
        }
        boolean isSend = false;
        for (int i=0; i<sizeOfTodayEmails; i++){
            if (email.getEmailAddress().equals(todayEmails.get(i).getEmailAddress()) && email.getSubject().equals(todayEmails.get(i).getSubject())){
                //System.out.println("True coming");
                isSend = true;
                break;
            }
        }
        if(!isSend){
            saveEmailObjects(email);
        }
    }

    public void saveEmailObjects(Email email){

        // send the email
        emailSend.newEmailSend(email);

        LocalDate dateKey = email.getSentDate();

        // Save the email object divide by it's send date
        if ( allEmailWithDates.get(dateKey) == null){
            // if that day doesn't send any email then create array and save it and put it into the hashmap allEmailWithDates
            ArrayList<Email> emailDate = new ArrayList<Email>();
            emailDate.add(email);
            allEmailWithDates.put(dateKey,emailDate);
            //System.out.println(allEmailWithDates.size());
            //System.out.println("new");
        }
        else{
            //if that day already send the emails then add the email object that created array list

            allEmailWithDates.get(dateKey).add(email);
            //System.out.println(allEmailWithDates.size());
            //System.out.println("Same");
        }

    }

    public void printEmail(String emailDate){
        try {
            // this is for print the emails that given date
            // check he given date is correct
            emailDate = emailDate.replace("/", "-");
            LocalDate dateWantEmail = LocalDate.parse(emailDate);

            ArrayList<Email> eachDayEmails = allEmailWithDates.get(dateWantEmail);

            // print if that day doesn't send any emails
            if (eachDayEmails == null) {
                System.out.println("This day haven't sent any Emails....!");
            } else {
                // if that day sand any email the print it one by one
                int sizeOfEachDayEmails = eachDayEmails.size();

                for (int i = 0; i < sizeOfEachDayEmails; i++) {

                    InternetAddress emailAddress = eachDayEmails.get(i).getEmailAddress();
                    String subject = eachDayEmails.get(i).getSubject();
                    String body = eachDayEmails.get(i).getContent();

                    System.out.println("Email Address: " + emailAddress + "\n" +
                            "Subject of Email: " + subject + "\n" +
                            "Content of Email: " + body);
                    System.out.println("----------------------------------------------");
                }
            }
        }catch(DateTimeException b){
            System.out.println("The Date is Not Valid....!");
        }
    }
    // when program is close then  serialize allEmailWithDate hashmap
    public void doSerialize(){
        emailSerialize.writeObjects(this.allEmailWithDates);
    }
}
//---------------------------------------------------------------------------------------------------------------------------

class EmailAddressChecker {

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
//---------------------------------------------------------------------------------------------------------------------------

class EmailSend {

    private SendEmailTLS sendEmailTLS;

    EmailSend(){
        // create the object of SendEmailTLS
        sendEmailTLS = new SendEmailTLS();

    }

    public void newEmailSend( Email email ){

        // get the details that want to send the email
        // by the getter methods
        InternetAddress emailAddress = email.getEmailAddress();
        String subject = email.getSubject();
        String content = email.getContent();

        // then send the email
        sendEmailTLS.sentMail(emailAddress, subject, content);


    }
}
//---------------------------------------------------------------------------------------------------------------------------

class EmailDeserialize {

    private HashMap<LocalDate, ArrayList<Email>> allEmailWithDates;

    public EmailDeserialize() {
        FileInputStream fileStream = null;
        ObjectInputStream os = null;

        try {
            // read the email object from the file that email objects are serialized
            fileStream = new FileInputStream("EmailSerialize.ser");

            os = new ObjectInputStream(fileStream);
            // get and give this to allEmailsWithDates
            this.allEmailWithDates = (HashMap<LocalDate, ArrayList<Email>>) os.readObject();

            fileStream.close();

        } catch (IOException i) {
            System.out.println("File Not Found....!");
            i.printStackTrace();

        } catch (ClassNotFoundException c) {
            System.out.println("Email class not found");
            c.printStackTrace();
            return;
        }finally{
            try{
                if ( fileStream != null){
                    fileStream.close();
                }
                if ( os != null){
                    os.close();
                }
            } catch (IOException cd){
                cd.printStackTrace();
            }
        }
    }

    public HashMap<LocalDate, ArrayList<Email>> getAllEmailWithDates(){
        return this.allEmailWithDates;
    }
}
//---------------------------------------------------------------------------------------------------------------------------


class EmailSerialize {

    public EmailSerialize() {

    }

    public void writeObjects(HashMap<LocalDate, ArrayList<Email>> allEmailWithDates){
        // write the email objects at the file that are want to serialize
        FileOutputStream fileStream = null;
        ObjectOutputStream os = null;

        try {
            fileStream = new FileOutputStream("EmailSerialize.ser");

            os = new ObjectOutputStream(fileStream);

            os.writeObject( allEmailWithDates );

            fileStream.close();

            System.out.println("DONE.....!");

        } catch (IOException i) {
            i.printStackTrace();
        } finally {
            try{
                if ( fileStream != null){
                    fileStream.close();
                }
                if ( os != null){
                    os.close();
                }
            }catch (IOException cd){
                cd.printStackTrace();
            }
        }

    }
}
//---------------------------------------------------------------------------------------------------------------------------


class SendEmailTLS {

    public void sentMail(InternetAddress email, String subject, String content) {

        final String username = "udaralatest@gmail.com";
        final String password = "xcuzqhcfazpdoscg";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("udaralatest@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("" + email) // comma seperated lots of gmails
            );
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);

            System.out.println("Email Sent. Done.");

        } catch (MessagingException e) {
            System.out.println("Error...! Email not send.");
            e.printStackTrace();
        }
    }
}
//---------------------------------------------------------------------------------------------------------------------------










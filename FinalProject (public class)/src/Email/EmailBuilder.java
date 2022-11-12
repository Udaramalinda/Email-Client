package Email;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.lang.reflect.Array;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class EmailBuilder {
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
        ArrayList<Email> todayEmails = allEmailWithDates.get(today);
        boolean isSend = false;
        int sizeOfTodayEmails = todayEmails.size();
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

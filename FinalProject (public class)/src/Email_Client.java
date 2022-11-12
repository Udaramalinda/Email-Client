// your index number

//import libraries

import Email.Email;
import Email.EmailBuilder;

import javax.mail.internet.AddressException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Email_Client {

    public static void main(String[] args) throws AddressException, DateTimeException{

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
                // That that object gives to save and send wish
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
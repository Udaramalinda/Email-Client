# Command-Line Email Client

This is a command-line based email client implementation in Java. The email client allows users to manage a list of recipients, send birthday greetings, and send emails to both official and personal contacts. The recipient details are stored in a text file (`clientList.txt`), and the application maintains recipient objects during runtime. Also application is a simple yet powerful tool for managing your emails. It was developed using Object-Oriented Programming (OOP) concepts to enhance code organization, reusability, and maintainability. Additionally, the application includes functionality to write and read emails from files, providing a convenient way to store and retrieve your messages.
## Features

- **Add a New Recipient:** Users can add a new recipient to the client list via the command-line interface. The details are stored in the `clientList.txt` file.

- **Send Birthday Greetings:** The application automatically sends birthday greetings to recipients on their birthdays. Different messages are sent to official friends and personal recipients.

- **Send Emails:** Users can send emails to recipients through the command-line interface. The messages are saved as objects using object serialization.

- **Print Recipients with Birthday Today:** Users can view the names of recipients who have their birthday on the current date.

- **Print Emails Sent on a Specific Date:** Users can retrieve information on emails sent on a particular date by providing the date as input.

- **Print Number of Recipient Objects:** Users can check the total number of recipient objects currently in the application.

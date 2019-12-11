package bank;

import java.io.IOException;
import java.sql.*;
import java.util.regex.Pattern;

public class Admin extends People {

    Admin() throws IOException {

    }

    //DB search
    public void search() throws SQLException {
        System.out.print("Enter the email:");
        String email_sel = input.next();
        ResultSet result = GetData(email_sel);
        while (result.next()) {
            System.out.println(" Name : " + result.getString("FirstName") + " "
                    + result.getString("lastName") + "\n"
                    + " Email : " + result.getString("email") + "\n"
                    + " Age : " + result.getInt("Age") + "\n"
                    + " Balance : " + result.getInt("balance"));
        }
    }

    public boolean emailvalidator(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."
                + "[a-zA-Z0-9_+&*-]+)*@"
                + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pat.matcher(email).matches();
    }

    public void Activation() throws ClassNotFoundException, SQLException {

        //Email input
        boolean Password_Checker_Flag = true;
        boolean Age_Checker_Flag = true;
        String Entered_Email = null;
        String First_Name;
        String Last_Name;
        String Password = null;
        float Balance;
        int Age = 0;
        System.out.println("Please enter your email");
        Entered_Email = input.next();
        while (true) {
            if (emailvalidator(Entered_Email)) {
                break;
            } else {
                System.out.println("Please Make Sure u Entered A valid Email , Try Again:");
            }
            Entered_Email = input.next();
        }

        // Name input
        System.out.println("Please enter your first name");
        First_Name = input.next();
        System.out.println("Please enter your second name");
        Last_Name = input.next();

        System.out.println("Please enter your age");
        Age = input.nextInt();
        while (Age_Checker_Flag) {

            if (Age < 21) {
                System.out.println("Please make sure you have entered the correct age ");

            } else if (Age >= 21) {

                Age_Checker_Flag = false;
                break;
            }
            Age = input.nextInt();

        }

        System.out.println("Please enter your Password");
        // password input
        Password = input.next();

        while (Password_Checker_Flag) {
            if (Password.length() < 8 || Password.length() > 32) {
                System.out.println("Please enter a password that is  not less than 8 characters and not more  than 32 characters");
                Password = input.next();
            } else {
                break;
            }
        }
        System.out.println("Please Enter the balance");
        Balance = input.nextFloat();
        Class.forName(DRIVER);
        Cursor = DriverManager.getConnection(URL, USER, PASSWORD);

        //This Alert To Verified If The conection Is succeed or Not
        State = Cursor.createStatement();
        String query = "INSERT INTO `account` (FirstName,lastname,Age,Balance,email,PASSWORD) VALUES ('" + First_Name + "','" + Last_Name + "','" + Age + "','" + Balance + "','" + Entered_Email + "','" + Password + "') ";
        State.executeUpdate(query);
        if (Cursor != null) {
            System.out.println("Registerd Succeded!");
        }

    }

    public void deactivation() throws SQLException {
        String GivenEmail;
        String GivenPassword;
        System.out.println("Please Enter your email and password to verify it for deletion");
        GivenEmail = input.next();
        GivenPassword = input.next();
        boolean CheckerForCorrectInfo = login(GivenEmail, GivenPassword);
        while (!CheckerForCorrectInfo) {
            System.out.println("Please make sure that you re-enter the info correctly and enter email and password respectively");
            GivenEmail = input.next();
            GivenPassword = input.next();
            CheckerForCorrectInfo = login(GivenEmail, GivenPassword);
        }

        Cursor = DriverManager.getConnection(URL, USER, PASSWORD);
        State = Cursor.createStatement();
        String query = "DELETE FROM account WHERE email='" + GivenEmail + "'";
        State.executeUpdate(query);
        if (Cursor != null) {
            System.out.println("Your records have been deleted from our database");
        }

    }

}

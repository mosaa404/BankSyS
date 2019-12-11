package bank;

import java.util.Scanner;
import java.io.*;
import java.sql.*;

public class People {

    Scanner input = new Scanner(System.in);

    People() throws IOException {

    }

    //DB Configuration
    static final String DRIVER = "com.mysql.jdbc.Driver";
    static final String URL = "jdbc:mysql://localhost/users";
    String USER = "root";
    String PASSWORD = "";
    Connection Cursor = null;
    Statement State = null;

    public void UpdateBal(String email, float NewBalance) throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);
        Cursor = DriverManager.getConnection(URL, USER, PASSWORD);
        State = Cursor.createStatement();
        String query = "UPDATE account SET `Balance` = '" + NewBalance + "' WHERE email = '" + email + "'";
        State.executeUpdate(query);
        if (Cursor != null) {
            System.out.println("Changing Balance");

        }

    }

    //DB Close connection
    public void closeconnection() throws SQLException, Exception {
        State.close();
        Cursor.close();
    }

    public ResultSet GetData(String email_sel) {
        ResultSet result = null;
        try {
            Class.forName(DRIVER);
            Cursor = DriverManager.getConnection(URL, USER, PASSWORD);
            if (Cursor != null) {
                System.out.println(" Connected And Getting Data");
            }
            State = Cursor.createStatement();
            String sader = "SELECT * FROM account WHERE email= '" + email_sel + "'";
            result = State.executeQuery(sader);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result != null) {
            return result;
        } else {
            return null;
        }

    }

    public boolean login(String Email, String Password) throws SQLException {
        String DatabaseEmail = "";
        String DatabasePassword = "";
        String DatabaseFirstName = "";
        ResultSet result = GetData(Email);
        while (result.next()) {
            DatabaseEmail = result.getString("email");
            DatabasePassword = result.getString("PASSWORD");
            DatabaseFirstName = result.getString("FirstName");
        }

        if (Email.equals(DatabaseEmail) && Password.equals(DatabasePassword)) {
            System.out.println("Hi " + DatabaseFirstName);
            return true;
        } else {
            System.out.println("Incorrect Email or Password");
            //IMPORTANT: This flag is meant for the main as to provide a flag to be given and thus help you make the user
            return false;                                                                               // enter the email and password till he  gets it right.
        }
    }

    public String EmailExist(String email_sel) throws SQLException {
        ResultSet result = GetData(email_sel);
        String Email = "Not Found";
        while (result.next()) {
            Email = result.getString("email");
        }
        return Email;
    }

    public int getBalance(String Email) throws SQLException {
        ResultSet result = GetData(Email);
        int Balance=0;
        while (result.next()) {
            Balance = result.getInt("Balance");
        }
        return Balance;
    }
}

package bank;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Usr extends People {

    Usr() throws IOException {

    }
    public Usr(String Email, String Password) throws IOException, SQLException {
        this.Email = Email;
        this.Password = Password;
        if(!EmailExist(Email).equals("Not Found")){
            if(login(Email, Password) ){  setter();
            }}
        
    }
    
    protected String FirstName, LastName, Email, Password;
    protected int Age;
    protected float Balance;
    
    void setter() throws SQLException{
     ResultSet result=GetData(this.Email);
        
        while(result.next()){
           this.FirstName=result.getString("FirstName");
           this.LastName= result.getString("lastName");
           this.Age=result.getInt("Age");
           this.Balance= result.getFloat("Balance");
        }
    }
    Scanner input = new Scanner(System.in);


    public void PrintUserData() throws SQLException {
       
        System.out.println("Dear Customer, Your Information : ");
        System.out.println("Name : " + FirstName + " " + LastName);
        System.out.println("Your Email : " + Email);
        System.out.println("Your Balance : " + Balance);
        
        }

    public void DepositsMoney() throws ClassNotFoundException, SQLException {
        float value;
        System.out.print("Dear Customer, Enter the Value that you wanna to Deposits : ");
        value = input.nextFloat();
        this.Balance+=value;
        UpdateBal(Email, Balance);
        System.out.println(" Now, Your Balance become  : " + (Balance ));
    }

    public void WithdrawMoney() throws ClassNotFoundException, SQLException {
        Float value;
        System.out.print("Dear Customer, Enter the Value that you wanna to Withdraw : ");
        value = input.nextFloat();
        if(value<=Balance){
        this.Balance=Balance-value;
            UpdateBal(Email, Balance);
        }
        else {
            System.out.println("You don't have that amount of money your Balance is : "+Balance);
        }
    }

    public void TransfarMoney() throws SQLException, ClassNotFoundException {

        System.out.print("Dear  Customer, Enter the value this you wanna to Transfar : ");
        int value = input.nextInt();
        this.Balance=getBalance(this.Email);
        while (value >Balance ) {
            System.out.println("Dear Customer , Please Enter A money You have you are poor : \"" + Balance + "\".");
            value = input.nextInt();
        }

        System.out.println("Enter the Account  of receiver : ");
        String acco_of_recei = input.next();
        if (!EmailExist(acco_of_recei).equals("Not Found")) {
            float SenderBalance = Balance - value;
            UpdateBal(Email, SenderBalance);
            float ReseverBalance = getBalance(acco_of_recei) + value;
            UpdateBal(acco_of_recei, ReseverBalance);
        } else {
            System.out.println(EmailExist(acco_of_recei));
        }

    }

 

}

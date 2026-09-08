/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exercise1;

import java.util.Scanner;

/**
 *
 * @author hp
 */
public class Problem7 
{
    public static void main(String[] args) 
    {
       Scanner scanner = new Scanner(System.in);
       
       System.out.println("Enter Account holder: ");
       String holder = scanner.nextLine();
       System.out.println("Enter Account number: ");
       String Accountnumber = scanner.nextLine();
       System.out.println("Enter account type : ");
       String Accounttype = scanner.nextLine();
       System.out.println("Enter Balance: ");
       double Balance = scanner.nextDouble();
       
        System.out.println("Account holder: " + holder );
        System.out.println("Account number: "+ Accountnumber);
        System.out.println("account type: "+ Accounttype);
        System.out.printf("Balance: %.2f%n",Balance);
        
        scanner.close();
    }
}

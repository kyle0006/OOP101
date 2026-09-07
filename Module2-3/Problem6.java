/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Exercise1;

import java.util.Scanner;

/**
 *
 * @author kdlonzanida
 */
public class Problem6 
{
    public static void main(String[] args) 
    {
       Scanner scanner = new Scanner(System.in);
       System.out.println("Enter Movie Title: ");
       String movietitle = scanner.nextLine();
       System.out.println("Enter Movie Director: ");
       String director = scanner.nextLine();
       System.out.println("Enter Movie Release Year: ");
       int year = scanner.nextInt();
       System.out.println("Enter Movie Runtime: ");
       int runtime = scanner.nextInt();
       System.out.println("Enter Movie Rating: ");
       double rating = scanner.nextDouble();
       
        System.out.println("Movie Title: " + movietitle);
        System.out.println("Movie Director: "+ director);
        System.out.println("Movie Release Year: "+ year);
        System.out.println("Movie Runtime: "+runtime);
        System.out.println("Movie Rating: "+rating);
        
    }
}

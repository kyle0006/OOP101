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
public class Problem6 
{
    public static void main(String[] args) 
    {
       Scanner scanner = new Scanner(System.in);
       System.out.println("By Kyle Gabriel D.V Lonzanida ");
       System.out.print("Enter Movie Title: ");
       String movietitle = scanner.nextLine();
       System.out.print("Enter Movie Director: ");
       String director = scanner.nextLine();
       System.out.print("Enter Movie Release Year: ");
       int year = scanner.nextInt();
       System.out.print("Enter Movie Runtime: ");
       int runtime = scanner.nextInt();
       System.out.print("Enter Movie Rating: ");
       double rating = scanner.nextDouble();
       
       System.out.println("By Kyle Gabriel D.V Lonzanida ");
       System.out.println("Movie Title: " + movietitle);
       System.out.println("Movie Director: "+ director);
       System.out.println("Movie Release Year: "+ year);
       System.out.println("Movie Runtime: "+runtime);
       System.out.println("Movie Rating: "+rating);
        
        scanner.close();
    }
}

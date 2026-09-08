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
public class Problem9 
{
     public static void main(String[] args) 
     {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter character name: ");
        String characterName = scanner.nextLine();
        System.out.print("Enter level: ");
        int level = scanner.nextInt();
        System.out.print("Enter experience points: ");
        long experiencePoints = scanner.nextLong();
        scanner.nextLine(); 
        System.out.print("Enter rank: ");
        String rank = scanner.nextLine();

        System.out.println("Username: " + username);
        System.out.println("Character Name: " + characterName);
        System.out.println("Level: " + level);
        System.out.println("Experience Points: " + experiencePoints);
        System.out.println("Rank: " + rank);

        scanner.close();
    }
}

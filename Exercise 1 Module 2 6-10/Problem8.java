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
public class Problem8 
{
     public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("By Kyle Gabriel D.V Lonzanida ");
        System.out.print("Enter student name: ");
        String studentName = scanner.nextLine();
        System.out.print("Enter subject code: ");
        String subjectCode = scanner.nextLine();
        System.out.print("Enter subject title: ");
        String subjectTitle = scanner.nextLine();
        System.out.print("Enter units: ");
        int units = scanner.nextInt();
        System.out.print("Enter section letter: ");
        char section = scanner.next().charAt(0);

        System.out.println("By Kyle Gabriel D.V Lonzanida ");
        System.out.println("Student Name: " + studentName);
        System.out.println("Subject Code: " + subjectCode);
        System.out.println("Subject Title: " + subjectTitle);
        System.out.println("Units: " + units);
        System.out.println("Section: " + section);

        scanner.close();
    }
}

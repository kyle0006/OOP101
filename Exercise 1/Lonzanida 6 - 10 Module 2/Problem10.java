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
public class Problem10 
{
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter employee name: ");
        String employeeName = scanner.nextLine();
        System.out.print("Enter employee ID: ");
        String employeeId = scanner.nextLine();
        System.out.print("Enter position: ");
        String position = scanner.nextLine();
        System.out.print("Enter hours worked: ");
        double hoursWorked = scanner.nextDouble();
        System.out.print("Enter hourly rate: ");
        double hourlyRate = scanner.nextDouble();

        System.out.println("Employee Name: " + employeeName);
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Position: " + position);
        System.out.println("Hours Worked: " + hoursWorked);
        System.out.printf("Hourly Rate: %.2f%n",hourlyRate);

        scanner.close();
    }
}

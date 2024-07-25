import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class Employee {
    int id;
    String name;
    Double salary;

    Employee(int id, String name, Double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }
}

public class fileHandling {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int id; String name; Double salary;

        do {
            System.out.println("1) Enter a new Employee detail");
            System.out.println("2) Display all Employee details");
            // System.out.println("3) Display a specific employee's details using id");

            char ch = sc.next().charAt(0);

            switch(ch) {
                case '1':
                try {
                    System.out.print("\nEmployee ID: ");
                    id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Employee Name: ");
                    name = sc.nextLine();
                    System.out.print("Employee Salary: ");
                    salary = sc.nextDouble();

                    BufferedWriter writer = new BufferedWriter(new FileWriter("out.txt", true));
                    writer.write("\n" + id + "," + name + "," + salary);
                    writer.close();
                } catch(IOException e) {
                    System.out.println("File Error " + e);
                } catch(Exception e) {
                    System.out.println("Input Error" + e);
                }
                break;
                
                case '2':
                try {
                    BufferedReader reader = new BufferedReader(new FileReader("out.txt"));
                    String current;
                    while((current = reader.readLine()) != null) {
                        String[] pars = current.split(",");
                        System.out.println(pars);
                        reader.close();
                    }
                } catch(IOException e) {
                    System.out.println("File error " + e);
                } catch (Exception e) {
                    System.out.println("Error " + e);
                }
                break;

                default:
                System.out.println("Error");
            }
            System.out.println("Press e to exit");
        } while(sc.next().charAt(0) != 'e');        
        sc.close();
    }
}
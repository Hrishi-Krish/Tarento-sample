import java.util.Scanner;

public class gradeCalculator {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Boolean exit = false;
        char con;
        
        while (!exit){
            System.out.print("\nEnter marks: ");
            int marks = input.nextInt();
            char grade = 'F';

            if (marks > 100) {
                System.out.println("Marks exceed limit, try again");
                continue;
            } 
            if (marks >= 90) grade = 'A';
            else if (marks >= 80) grade = 'B';
            else if (marks >= 70) grade = 'C';
            else if (marks >= 60) grade = 'D';
            
            System.out.println("The grade is " + grade);
            System.out.print("Press y to continue: ");
            con = input.next().charAt(0);
            if (con != 'y') exit = true;
        }
        
    }
}

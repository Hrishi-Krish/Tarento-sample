import java.util.Scanner;

class Calculator {
    
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        double num1, num2, res;
        Boolean exit = false;
        char op, ch;

        while(!exit){
            System.out.println("Enter two numbers");
            num1 = input.nextDouble();
            num2 = input.nextDouble();
            

            System.out.println("Choose from the following options");
            System.out.println("1: Addition");
            System.out.println("2: Subtraction");
            System.out.println("3: Multiplication");
            System.out.println("4: Division");
            System.out.println("5: Remainder");
            op = input.next().charAt(0);

            switch(op) {
                case '1':
                res = num1 + num2;
                System.out.println(num1 + " + " + num2 + " = " + res);
                break;

                case '2':
                res = num1 - num2;
                System.out.println(num1 + " - " + num2 + " = " + res);
                break;

                case '3':
                res = num1 * num2;
                System.out.println(num1 + " x " + num2 + " = " + res);
                break;

                case '4':
                if (num2 == 0) {
                    System.out.println("Division by zero not possible");
                    break;
                }
                res = num1 / num2;
                System.out.println(num1 + " / " + num2 + " = " + res);
                break;

                case '5':
                if (num2 == 0) {
                    System.out.println("Division by zero not possible");
                    break;
                }
                res = num1 % num2;
                System.out.println(num1 + " mod " + num2 + " = " + res);
                break;

                default:
                System.out.println("Undefined input");
            }
            
            System.out.print("Press y to continue: ");
            ch = input.next().charAt(0);

            if (ch != 'y') exit = true;
        }
    }
}
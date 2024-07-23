import java.util.Scanner;
// Defining the custom exception
class ProductNotFoundException extends Exception {
    public ProductNotFoundException(String errString) {
        super(errString);
    }
}

// Creating a product object
class product { 
    private int regNo;
    private String name;

    String getName() {
        return name;
    }

    int getRegNo() {
        return regNo;
    }

    void setName(String name) {
        this.name = name;
    }
    
    void setRegNo(int regNo) {
        this.regNo = regNo;
    }

    void trap(String name) throws IllegalAccessException {
        System.out.println("This method is just a trap");
        throw new IllegalAccessException("Intruder Alert");
    }
}

// Main class
public class exceptionHandling {
    public static void main(String[] args) {
        int n;
        System.out.print("Enter the number of products: ");
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        product[] newArrivals = new product[n];
        for (int i = 0; i < n; i++) {
            newArrivals[i] = new product();
            System.out.print("\nRegister Number: ");
            newArrivals[i].setRegNo(sc.nextInt());
            System.out.print("Product Name: ");
            newArrivals[i].setName(sc.next());
        }
        // One time search option
        do {
            System.out.print("\nPress 1 to search via register number and 2 to search via name: ");
            int choice = sc.nextInt();

            if (choice == 1) {
                System.out.print("Enter the register number to search for: ");
                int regNo = sc.nextInt();
                int i;
                try {
                    for (i = 0; i < n; i++) {
                        if (newArrivals[i].getRegNo() == regNo) {
                            System.out.println("\nRegister Number: " + regNo);
                            System.out.println("Product Name: " + newArrivals[i].getName());
                            System.out.println("Entry Number: " + (i+1));
                            break;
                        }
                    }
                    if (i == n) 
                        throw new ProductNotFoundException("Product with given register number not found");
                    else if 
                        (i == 3) newArrivals[i].trap("lol");
                } catch (ProductNotFoundException e) {
                    System.out.println(e);
                } catch (Exception e) {
                    System.out.println("Error encountered \n" + e);
                } finally {
                    System.out.println("Exiting program");
                }
            }
            else if (choice == 2) {
                System.out.println("Enter the product name to search for: ");
                String productName = sc.next();
                int i;
                try {
                    for (i = 0; i < n; i++) {
                        if (newArrivals[i].getName().equals(productName)) {
                            System.out.println("\nRegister Number: " + newArrivals[i].getRegNo());
                            System.out.println("Product Name: " + productName);
                            System.out.println("Entry Number: " + (i + 1));
                            break;
                        }
                    }
                    if (i == n)
                        throw new ProductNotFoundException("Product with entered name not found");
                    else if (i == 3)
                        newArrivals[i].trap("lol");
                } catch (ProductNotFoundException e) {
                    System.out.println(e);
                } catch (Exception e) {
                    System.out.println("Error encountered \n" + e);
                } finally {
                    System.out.println("Executing finally block");
                }
            }
            
            System.out.print("Press e to exit: ");

        } while(sc.next().charAt(0) != 'e');

        sc.close();
    }
}

import java.util.ArrayList;
import java.util.Scanner;

class Book {
    double price;
    String title, author, ISBN;

    Book(double price, String title, String author, String ISBN) {
        this.price = price;
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
    }
}

public class arraryList {
    public static void main(String[] args) {
        ArrayList<Book> collection = new ArrayList<>();
        double price;
        String title, author, ISBN;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("1) Add a new Book\n2) Remove an existing Book\n3) Display current collection of books");
            char option = sc.next().charAt(0);
            switch(option) {
                case '1':
                System.out.println("Enter the details of the book");

                System.out.print("Title: ");
                sc.nextLine();
                title = sc.nextLine();
                System.out.print("Author: ");
                author = sc.nextLine();
                System.out.print("ISBN: ");
                ISBN = sc.next();
                System.out.print("price: ");
                try{
                    price = sc.nextDouble();    
                } catch(Exception e) {
                    System.out.println("Error " + e);
                    break;
                }
                Book newOne = new Book(price, title, author, ISBN);
                collection.add(newOne);
                break;
                
                case '2':
                System.out.print("Enter the ISBN of the book to delete: ");
                ISBN = sc.next();
                for (Book b: collection) {
                    if (b.ISBN.equals(ISBN)) collection.remove(b);
                    System.out.println("book removed");
                    break;
                }
                break;

                case '3':
                System.out.println("List of books in collection");
                for (Book b: collection) {
                    System.out.println("\nBook Title: " + b.title);
                    System.out.println("Book Author: " + b.author);
                    System.out.println("Book ISBN: " + b.ISBN);
                    System.out.println("Book Price: " + b.price);
                }
                break;
                
                default:
                System.out.println("Option does not exist");
            }
            System.out.println("Press e to exit");
        } while(sc.next().charAt(0) != 'e');

    }
}

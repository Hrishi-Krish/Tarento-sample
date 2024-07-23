import java.util.HashMap;
import java.util.Scanner;

class Book {
    private Double price;
    private String title, author, ISBN;

    String getTitle() {
        return title;
    }

    String getAuthor() {
        return author;
    }
    
    String getISBN() {
        return ISBN;
    }

    Double getPrice() {
        return price;
    }

    void setTitle(String title) {
        this.title = title;
    }

    void setAuthor(String author) {
        this.author = author;
    }

    void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    void setPrice(Double price) {
        this.price = price;
    }

    Book(String title, String author, String ISBN, Double price) {
        setTitle(title);
        setAuthor(author);
        setISBN(ISBN);
        setPrice(price);
    }

    void display() {
        System.out.println("\nISBN: " + getISBN());
        System.out.println("title: " + getTitle());
        System.out.println("author: " + getAuthor());
        System.out.println("price: " + getPrice());
    }
}

// class BookAlreadyExistsException extends Exception {
//     BookAlreadyExistsException(String errMsg) {
//         super(errMsg);
//     }
// }

public class hashMapExample {
    public static void main(String[] args) {
        HashMap<String, Book> bookList = new HashMap<>();
        Scanner sc = new Scanner(System.in);
        String title, author, ISBN;
        Double price;

        do {
            System.out.println("\n1) Add a new book");
            System.out.println("2) Get details of a book via ISBN");
            System.out.println("3) Display details of all books");
            System.out.println("4) Remove a book via ISBN");
            
            char ch = sc.next().charAt(0);
            sc.nextLine();

            switch(ch) {
                case '1':
                System.out.print("\nBook title: ");
                title = sc.nextLine();
                System.out.print("Book author: ");
                author = sc.nextLine();
                System.out.print("Book ISBN: ");
                ISBN = sc.nextLine();
                System.out.print("Book price: ");
                try{
                    price = sc.nextDouble();
                } catch(Exception e) {
                    System.out.println("Error " + e);
                    break;
                }
                
                Book newBook = new Book(title, author, ISBN, price);
                if (bookList.containsKey(ISBN)) {
                    System.out.println("Book with given ISBN already exists");
                    break;
                }
                bookList.put(ISBN, newBook);
                break;

                case '2':
                System.out.print("Enter ISBN: ");
                ISBN = sc.nextLine();
                if (bookList.containsKey(ISBN)) {
                    bookList.get(ISBN).display();
                } else {
                    System.out.println("Book with given ISBN does not exist");
                }
                break;

                case '3':
                for (String book: bookList.keySet()) {
                    bookList.get(book).display();
                }
                break;

                case '4':
                    System.out.print("Enter ISBN: ");
                    ISBN = sc.nextLine();
                    if (bookList.containsKey(ISBN)) {
                        bookList.remove(ISBN);
                        System.out.println("Book removed");
                    } else {
                        System.out.println("Book with given ISBN does not exist");
                    }
                    break;

                default:
                System.out.println("Error");
            }
            System.out.println("\nPress e to exit");
        } while(sc.next().charAt(0) != 'e');
    }
}

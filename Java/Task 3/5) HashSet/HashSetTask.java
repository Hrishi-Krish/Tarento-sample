import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

class Library {
    String bookID, genre;
    Library(String bookID,String genre) {
        this.bookID = bookID;
        this.genre = genre;
    }
}

public class HashSetTask {
    public static void main(String[] args) {
        ArrayList<Library> list = new ArrayList<>();
        HashSet<String> genres = new HashSet<>();
        Scanner sc = new Scanner(System.in);
        String bookID, genre;

        do {
            System.out.println("1) Enter a new book");
            System.out.println("2) Display all books");
            char ch = sc.next().charAt(0);
            sc.nextLine();
            switch(ch) {
                case '1':
                System.out.print("\nEnter book ID: ");
                bookID = sc.nextLine();
                System.out.print("\nEnter book genre: ");
                genre = sc.nextLine();
                Library newBook = new Library(bookID, genre);
                list.add(newBook);
                if (! genres.contains(genre)) {
                    genres.add(genre);
                }
                break;

                case '2':
                for (Library b: list) {
                    System.out.println("\nBookID: " + b.bookID);
                    System.out.println("Genre: " + b.genre);
                }
                break;

                default:
                System.out.println("Error");
            }
            System.out.print("Press e to exit: ");
        } while(sc.next().charAt(0) != 'e');
        System.out.println("The final list of genres are\n");
        for (String g: genres) {
            System.out.println(g);
        }
        sc.close();
    }    
}

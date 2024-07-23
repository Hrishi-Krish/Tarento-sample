
public class stringOperations {
    public static void main(String[] args) {
        String example = "Hello World";

        System.out.println("Length of " + example + " is " + example.length());
        System.out.println("Uppercase of " + example + " is " + example.toUpperCase());
        System.out.println("A substring of " + example + " is " + example.substring(0, 5));
        System.out.println("Replacing a character in " + example + " gives the string: "+ example.replace('l', 'w') );
    }
}

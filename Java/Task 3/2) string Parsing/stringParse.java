public class stringParse {
    public static void main(String[] args) {
        System.out.println("\nParsing\n-------");
        // String declaration
        String number = "127";
        System.out.println("String number = " + number);
        System.out.println("String number + 2 = " + (number + 2) );
        // ParseInt
        System.out.println("\nparseInt of number + 2 = " + (Integer.parseInt(number) + 2));
        System.out.println("parseInt with base 8 of number = " + Integer.parseInt(number, 8) );
        System.out.println("parseInt with base 16 of number = " + Integer.parseInt(number, 16));
        System.out.println("parseInt with base 9 of number = " + Integer.parseInt(number, 9));
        System.out.println("parseInt with negative number = " + Integer.parseInt("-127"));
        System.out.println("parseInt with base 2 of 100101 = " + Integer.parseInt("100101", 2));
        // ParseDouble 
        System.out.println("\nparseDouble of 101.231 = " + Double.parseDouble("101.231"));
        System.out.println("parseDouble of -101.231 = " + Double.parseDouble("-101.231"));
        System.out.println("parseDouble of 101.231e4 = " + Double.parseDouble("101.231e4"));
        // ParseBoolean
        System.out.println("\nparseBoolean of true = " + Boolean.parseBoolean("true"));
        System.out.println("parseBoolean of TrUe = " + Boolean.parseBoolean("TrUe"));
        System.out.println("parseBoolean of false = " + Boolean.parseBoolean("false"));
        System.out.println("parseBoolean of 100 = " + Boolean.parseBoolean("100"));
    
        // valueOf
        System.out.println("\nvalueOf\n-------");
        System.out.println("Integer 899: " + Integer.valueOf("899"));
        System.out.println("Double 899.76: " + Double.valueOf("899.76"));
        System.out.println("Boolean true: " + Boolean.valueOf("true"));
        System.out.println("Boolean 1: " + Boolean.valueOf("1"));
        
        // to string
        System.out.println("\nTo String\n---------");
        System.out.println("1 to String " + String.valueOf(1));
        System.out.println("1.8981 to String " + String.valueOf(1.8981));
        System.out.println("-1826.12 to String " + String.valueOf(-1826.12));
        char [] ex = {'c', 'h', 'a', 'r'};
        System.out.println("['c', 'h', 'a', 'r'] to String " + String.valueOf(ex));
        System.out.println("true to String " + String.valueOf(true));
    }
}

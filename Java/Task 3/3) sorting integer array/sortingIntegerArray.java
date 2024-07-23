import java.util.Scanner;

public class sortingIntegerArray {
    public static void main(String[] args) {
        int n;
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter the number of integers: ");
        n = sc.nextInt();
        int[] numbers = new int[n];
        System.out.println("Enter the integers ");
        int i,j;
        for (i=0; i < n; i++) numbers[i] = sc.nextInt();

        for (i = 1; i < n; i++) {
            for (j = i ; j > 0 && numbers[j] < numbers[j-1]; j--) {
                int temp = numbers[j];
                numbers[j] = numbers[j-1];
                numbers[j-1] = temp;
            }
        }

        System.out.println("The sorted array is");
        for (i = 0; i < n; i++) System.out.print(numbers[i] + " ");
    }
}

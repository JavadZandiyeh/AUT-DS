import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String in = scanner.nextLine();
        String[] input = in.split(" ");
        String[] output = new String[input.length];

        int counter = 1;
        int l = input.length;
        for(int i = 0; i <= Math.floor(l/2) - 1; i++){
            output[counter] = input[i];
            counter += 2;
        }
        counter = 0;
        for (int i = l - 1; i > Math.floor(l/2) - 1; i--){
            output[counter] = input[i];
            counter += 2;
        }

        for (int i = 0; i < l; i++)
            System.out.print(output[i] + " ");
    }
}

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        String[] Names = new String[100000];
        int[] Numbers = new int[100000];
        int pointer = -1;

        ArrayList<String> output = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();


        for (int i = 0; i < n; i++) {
            String command = scanner.next();
            int number = scanner.nextInt();

            if(command.startsWith("i")){
                pointer += 1;
                Names[pointer] = command;
                Numbers[pointer] = number;
            }else if(command.equals("t")) {
                do {
                    if (pointer < 0)
                        break;
                    if(number > Numbers[pointer]){
                        number -= Numbers[pointer];
                        pointer -= 1;
                    }else if(number == Numbers[pointer]){
                        number = 0;
                        pointer -= 1;
                    }else if(number < Numbers[pointer]){
                        Numbers[pointer] -= number;
                        number = 0;
                    }
                } while (number > 0);

                if(pointer >= 0)
                    output.add(Names[pointer]);
                else
                    output.add("main");
            }
        }

        for (String s: output)
            System.out.println(s);
    }
}
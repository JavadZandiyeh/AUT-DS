import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] answer = new int[n];


        for (int i = 0; i < n; i++){
            String commands = scanner.next();
            int size = commands.length();
            HashMap<Integer, Boolean> hashMap = new HashMap<>();
            int x = size;
            int y = size;
            for (int j = 0; j < size; j++){
                int math = 0;
                switch (commands.charAt(j)){
                    case 'N': {
                        math = (((4 * size) + 1) * (x - 1)) + y + 1 + (2 * size);
                        x -= 1;
                        break;
                    }
                    case 'S': {
                        math = (((4 * size) + 1) * x) + y + 1 + (2 * size);
                        x += 1;
                        break;
                    }
                    case 'W': {
                        math = (((4 * size) + 1) * x) + y;
                        y -= 1;
                        break;
                    }
                    case 'E': {
                        math = (((4 * size) + 1) * x) + y + 1;
                        y += 1;
                        break;
                    }
                }
                if (hashMap.get(math) != null)
                    answer[i] += 1;
                else {
                    hashMap.put(math, true);
                    answer[i] += 5;
                }
            }
        }

        for (int i = 0; i < n; i++)
            System.out.println(answer[i]);
    }
}

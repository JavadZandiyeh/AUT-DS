import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String input;
        Scanner scanner = new Scanner(System.in);
        input = scanner.nextLine();

        String[] nums_String = input.split(" ");
        int[] nums = new int[nums_String.length];

        for (int j = 0; j < nums_String.length; j++)
            nums[j] = Integer.parseInt(nums_String[j]);

        int counter = 1;
        ArrayList<Integer> sub = new ArrayList<>();

        int j = 0;
        while(j < nums.length) {
            if (counter == nums[j]) {
                counter++;
                j++;
            }
            else if (sub.size() == 0) {
                sub.add(nums[j]);
                j++;
            }
            else if (sub.get(sub.size() - 1) == counter) {
                sub.remove(sub.size() - 1);
                counter++;
            }
            else if (sub.get(sub.size() - 1) > nums[j]) {
                sub.add(nums[j]);
                j++;
            }
            else if (sub.get(sub.size() - 1) < nums[j]){
                System.out.print("no");
                return;
            }
        }

        System.out.print("yes");
    }
}
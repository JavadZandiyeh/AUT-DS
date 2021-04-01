import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //n is numbers of ball
        //k is numbers of digits in each ball
        //h is numbers of digits in red ball
        //t is numbers of changes have to be done
        int n,k,h,t;
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        k = scanner.nextInt();
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();

        for (int i = 0; i < n; i++){
            lists.add(new ArrayList<Integer>());
            for(int j = 0; j < k; j++)
                lists.get(i).add(scanner.nextInt());
        }

        h = scanner.nextInt();
        int[] arr_red_ball = new int[h];
        for(int i = 0; i < h; i++)
            arr_red_ball[i] = scanner.nextInt();
        t = scanner.nextInt();


        for (int i = 0; i < t; i++) {
            int command = arr_red_ball[(i%h)];
            ArrayList<Integer> current_list = lists.get(0);
            for (int j = 0; j < Math.abs(command % k); j++){
                if(command > 0){
                    current_list.add(current_list.get(0));
                    current_list.remove(0);
                }else if(command < 0){
                    current_list.add(0, current_list.get(k - 1));
                    current_list.remove(k);
                }
            }
            lists.add(current_list);
            lists.remove(0);
        }

        for (int i = 0; i < n; i++)
            System.out.print(lists.get(i).get(0));

    }
}

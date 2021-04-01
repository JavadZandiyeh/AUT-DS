import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static String[][] table;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        table = new String[n + 2][m + 2];

        for (int a = 0; a < (n+2); a++) {
            table[a][0] = "1";
            table[a][m+1] = "1";
        }
        for (int b = 0; b < (m+2); b++){
            table[0][b] = "1";
            table[n+1][b] = "1";
        }


        for (int i = 1; i < n + 1; i++){
            for (int  j = 1; j < m + 1; j++)
                table[i][j] = scanner.next();
        }


        String flag = scanner.next();

        ArrayList<node> arrayList = new ArrayList<>();
        for (int i = 1; i < n + 1; i++){
            for (int  j = 1; j < m + 1; j++) {
                if (table[i][j].equals(flag))
                    arrayList.add(new node(i, j, 1));
            }
        }

        int[] answers = new int[arrayList.size()];
        int i = 0;
        for (node no: arrayList) {
            answers[i] = bfs(no);
            i++;
        }

        int g = 0;
        int max = 1;
        int size = answers.length;
        for (int w = 0; w < size; w++) {
            if (answers[w] > max) {
                max = answers[w];
                g = w;
            }
        }

        System.out.println(answers[g] + "\n" + (arrayList.get(g).x - 1) + " " + (arrayList.get(g).y - 1));
    }

    public static int bfs(node no){
        LinkedList<node> linkedList = new LinkedList<>();
        linkedList.addLast(no);
        int ans = 1;

        while (linkedList.size() != 0){
            node p_node = linkedList.getFirst();
            linkedList.removeFirst();
            int x = p_node.x;
            int y = p_node.y;
            int state = p_node.state;


            if((!table[x - 1][y - 1].equals("1")) &&
                    table[x - 1][y - 1].equals(String.valueOf((char) (table[x][y].charAt(0) + 1)))){
                linkedList.addLast(new node(x - 1, y - 1, state + 1));
            }

            if((!table[x - 1][y].equals("1")) &&
                    table[x - 1][y].equals(String.valueOf((char) (table[x][y].charAt(0) + 1)))){
                linkedList.addLast(new node(x - 1, y, state + 1));
            }

            if((!table[x - 1][y + 1].equals("1")) &&
                    table[x - 1][y + 1].equals(String.valueOf((char) (table[x][y].charAt(0) + 1)))){
                linkedList.addLast(new node(x - 1, y + 1, state + 1));
            }

            if((!table[x][y - 1].equals("1")) &&
                    table[x][y - 1].equals(String.valueOf((char) (table[x][y].charAt(0) + 1)))){
                linkedList.addLast(new node(x, y - 1, state + 1));
            }

            if((!table[x][y + 1].equals("1")) &&
                    table[x][y + 1].equals(String.valueOf((char) (table[x][y].charAt(0) + 1)))){
                linkedList.addLast(new node(x, y + 1, state + 1));
            }

            if((!table[x + 1][y - 1].equals("1")) &&
                    table[x + 1][y - 1].equals(String.valueOf((char) (table[x][y].charAt(0) + 1)))){
                linkedList.addLast(new node(x + 1, y - 1, state + 1));
            }

            if((!table[x + 1][y].equals("1")) &&
                    table[x + 1][y].equals(String.valueOf((char) (table[x][y].charAt(0) + 1)))){
                linkedList.addLast(new node(x + 1, y, state + 1));
            }

            if((!table[x + 1][y + 1].equals("1")) &&
                    table[x + 1][y + 1].equals(String.valueOf((char) (table[x][y].charAt(0) + 1)))){
                linkedList.addLast(new node(x + 1, y + 1, state + 1));
            }

            ans = state;
        }

        return ans;
    }

    public static class node {
        int x, y, state;
        public node(int x, int y, int state){
            this.x = x;
            this.y = y;
            this.state = state;
        }
    }
}

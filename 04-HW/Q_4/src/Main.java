import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static node[] nodes;
    public static int n;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        nodes = new node[n];

        for (int i = 0; i < n; i++)
            nodes[i] = new node(i + 1);

        for (int i = 0; i < n - 1; i++){
            int m = scanner.nextInt();
            nodes[m - 1].addNeighbour(i + 2);
        }
        dfs(nodes[0]);
    }

    public static class node{
        int num;
        node parent;
        ArrayList<node> neighbours;
        public node(int num){
            this.num = num;
            neighbours = new ArrayList<>();
            parent = null;
        }

        public void addNeighbour(int n){
            neighbours.add(nodes[n -1]);
        }
    }

    public static void dfs(node current){
        if(current.num == n){
            LinkedList<Integer> answer = new LinkedList<>();
            while (current != null){
                answer.addFirst(current.num);
                current = current.parent;
            }
            for (Integer i: answer)
                System.out.print(i + " ");
        }else {
            for (node n : current.neighbours) {
                n.parent = current;
                dfs(n);
            }
        }
    }
}

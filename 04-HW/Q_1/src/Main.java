import java.util.*;

public class Main {

    public static int[][] A;
    public static String[][] Q;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n,m;
        n = scanner.nextInt();
        m = scanner.nextInt();
        Q = new String[n][m];
        A = new int[n][m];

        ArrayList<node> arrayOfOs = new ArrayList<>();

        for (int i = 0; i < n; i++){
            String[] arr = scanner.next().split("");
            for (int j = 0; j < m; j++) {
                Q[i][j] = arr[j];
                if (Q[i][j].equals("M"))
                    A[i][j] = 0;
                else if(Q[i][j].equals("X"))
                    A[i][j] = -1;
                else if (Q[i][j].equals("O"))
                    arrayOfOs.add(new node(i, j, 0));
            }
        }

        for (node no: arrayOfOs){
            int[][] visited = new int[n+2][m+2];
            for (int a = 0; a < (n+2); a++) {
                visited[a][0] = 1;
                visited[a][m+1] = 1;
            }
            for (int b = 0; b < (m+2); b++){
                visited[0][b] = 1;
                visited[n+1][b] = 1;
            }
            LinkedList<node> linkedList = new LinkedList<>();
            A[no.x][no.y] = bfs(visited, linkedList, no.x, no.y);
        }

        for (int i = 0; i < n; i++){
            for (int j = 0; j < m; j++)
                System.out.print(A[i][j] + " ");
            System.out.println();
        }
//        for(int i = 0; i < n; i++){
//            for(int j = 0; j < m; j++) {
//                if(Q[i][j].equals("O")){
//                    int[][] visited = new int[n+2][m+2];
//                    for (int a = 0; a < (n+2); a++) {
//                        visited[a][0] = 1;
//                        visited[a][m+1] = 1;
//                    }
//                    for (int b = 0; b < (m+2); b++){
//                        visited[0][b] = 1;
//                        visited[n+1][b] = 1;
//                    }
//                    LinkedList<node> linkedList = new LinkedList<>();
//                    A[i][j] = bfs(visited, linkedList, i, j);
//                }
//                System.out.print(A[i][j] + " ");
//            }
//            System.out.println();
//        }
    }


    public static int bfs(int[][] visited, LinkedList<node> linkedList, int i, int j){
        visited[i+1][j+1] = 1;

        linkedList.addLast(new node(i, j, 0));

        while (linkedList.size() != 0) {
            node p_node = linkedList.getFirst();
            linkedList.removeFirst();
            int x = p_node.x;
            int y = p_node.y;
            int state = p_node.state;

            if (visited[x][y+1] != 1) {
                visited[x][y+1] = 1;
                if(Q[x-1][y].equals("M"))
                    return (state+1);
                else if(Q[x-1][y].equals("O"))
                    linkedList.addLast(new node(x-1, y, state+1));
            }

            if (visited[x+2][y+1] != 1) {
                visited[x+2][y+1] = 1;
                if(Q[x+1][y].equals("M"))
                    return (state+1);
                else if(Q[x+1][y].equals("O"))
                    linkedList.addLast(new node(x+1, y, state+1));
            }

            if (visited[x+1][y] != 1) {
                visited[x+1][y] = 1;
                if(Q[x][y-1].equals("M"))
                    return (state+1);
                else if(Q[x][y-1].equals("O"))
                    linkedList.addLast(new node(x, y-1, state+1));
            }

            if (visited[x+1][y+2] != 1) {
                visited[x+1][y+2] = 1;
                if(Q[x][y+1].equals("M"))
                    return (state+1);
                else if(Q[x][y+1].equals("O"))
                    linkedList.addLast(new node(x, y+1, state+1));
            }
        }

        return -1;
    }

    public static class node{
        int x,y,state;
        public node(int x, int y, int state){
            this.x = x;
            this.y = y;
            this.state = state;
        }
    }
}

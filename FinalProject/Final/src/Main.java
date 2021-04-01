import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import static java.lang.Math.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        LinkedList<Edge>[] adjacencyList = new LinkedList[n];
        HashMap<Long, Integer> hashMap_index = new HashMap<>();
        HashMap<Long, Node> hashMap_node = new HashMap<>();
        MinHeapNodes minHeapNodes = new MinHeapNodes(n);

        for (int i = 0; i < n; i++){
            long id = scanner.nextLong();
            double y = scanner.nextDouble();
            double x = scanner.nextDouble();
            Node node = new Node(id, y, x);
            adjacencyList[i] = new LinkedList<>();
            hashMap_index.put(id, i);
            hashMap_node.put(id, node);
            minHeapNodes.insert(node);
        }

        for (int i = 0; i < m; i++){
            long idFirst = scanner.nextLong();
            long idLast = scanner.nextLong();
            int indexFirst = hashMap_index.get(idFirst);
            int indexLast = hashMap_index.get(idLast);
            adjacencyList[indexFirst].addFirst(new Edge(hashMap_node.get(idFirst), hashMap_node.get(idLast)));
            adjacencyList[indexLast].addFirst(new Edge(hashMap_node.get(idLast), hashMap_node.get(idFirst)));
        }

        int num_commands = scanner.nextInt();
        MinHeapUser minHeapUser = new MinHeapUser(num_commands);
        for (int i = 0; i < num_commands; i++) {
            double time = scanner.nextDouble();
            long idStart = scanner.nextLong();
            long idEnd = scanner.nextLong();
            User user = new User(time, idStart, idEnd);
            minHeapUser.insert(user);
        }

        ArrayList<User> arrayList = new ArrayList<>();
        while (!minHeapUser.isEmpty()) {
            User currentUser = minHeapUser.removeFirst();

            for(User u: arrayList){
                if (((120 * u.timeTakes) + u.timeRequest) < currentUser.timeRequest){
                    for (Edge eg: u.path_edges)
                        eg.decreaseTraffic();
                }
            }

            Node currentNode = hashMap_node.get(currentUser.start);
            minHeapNodes.setValue(currentNode, 0);

            while (!minHeapNodes.isEmpty()) {
                currentNode = minHeapNodes.removeFirst();
                LinkedList<Edge> neighborEdges = adjacencyList[hashMap_index.get(currentNode.id)];
                for (Edge edge : neighborEdges) {
                    Node otherSideNode = edge.getOtherSide(currentNode);
                    double new_dist = edge.weight + currentNode.distance;
                    if (otherSideNode.distance > new_dist) {
                        minHeapNodes.setValue(otherSideNode, new_dist);
                        otherSideNode.prevEdge = edge;
                    }
                }
            }

            Node firstNode = hashMap_node.get(currentUser.start);
            Node endNode = hashMap_node.get(currentUser.end);
            currentUser.path.addFirst(endNode.id);
            while (!endNode.equals(firstNode)){
                Edge eg = endNode.prevEdge;
                currentUser.insertTime(eg.weight);
                eg.increaseTraffic();
                currentUser.path_edges.add(eg);
                endNode = eg.getOtherSide(endNode);
                currentUser.path.addFirst(endNode.id);
            }
            arrayList.add(currentUser);
            currentUser.write_details();

            //restart
            for (Node node: hashMap_node.values()) {
                node.distance = Integer.MAX_VALUE;
                node.prevEdge = null;
                minHeapNodes.insert(node);
            }
        }
    }

    public static class Edge{
        Node nodeFirst, nodeLast;
        int traffic;
        double weight;
        public Edge(Node nodeFirst, Node nodeLast){
            this.nodeFirst = nodeFirst;
            this.nodeLast = nodeLast;
            traffic = 0;
            loadWeight();
        }

        public void decreaseTraffic(){
            if(traffic != 0) {
                traffic--;
                loadWeight();
            }
        }

        public void increaseTraffic(){
            traffic++;
            loadWeight();
        }

        private void loadWeight() {
            weight = (sqrt(pow((nodeFirst.x - nodeLast.x), 2) +
                    pow((nodeFirst.y - nodeLast.y), 2))) * (1 + (0.3 * traffic));
        }

        public Node getOtherSide(Node node){
            if (node.equals(nodeFirst))
                return nodeLast;
            else if (node.equals(nodeLast))
                return nodeFirst;
            else return null;
        }

    }

    private static class Node {
        long id;
        double y, x;
        double distance;
        Edge prevEdge;

        public Node(long id, double y, double x) {
            this.id = id;
            this.y = y;
            this.x = x;
            this.distance = Integer.MAX_VALUE;
            prevEdge = null;
        }
    }

    public static class MinHeapUser{
        private User[] Heap;
        private int size;
        private int maxsize;
        private static final int FRONT = 0;

        public MinHeapUser(int maxsize) {
            this.maxsize = maxsize;
            this.size = 0;
            Heap = new User[this.maxsize];
        }

        private int parent(int pos) {
            return (pos-1) / 2;
        }
        private int leftChild(int pos) {
            return (2 * pos) + 1;
        }
        private int rightChild(int pos) {
            return (2 * pos) + 2;
        }

        private void swap(int fpos, int spos) {
            User tmp = Heap[fpos];
            Heap[fpos] = Heap[spos];
            Heap[spos] = tmp;
        }

        public void insert(User element) {
            if (size >= maxsize)
                return;
            Heap[size] = element;
            int current = size;

            if (current != FRONT) {
                while (Heap[current].timeRequest < Heap[parent(current)].timeRequest) {
                    swap(current, parent(current));
                    current = parent(current);
                }
            }
            size++;
        }

        private void minHeapify(int pos) {
            if (leftChild(pos) >= size && rightChild(pos) >= size) //no child
                return;
            if(leftChild(pos) < size && rightChild(pos) >= size) { //left child(yes) right child(no)
                if (Heap[leftChild(pos)].timeRequest < Heap[pos].timeRequest) {
                    swap(pos, leftChild(pos));
                    minHeapify(leftChild(pos));
                }
            }else if (leftChild(pos) < size && rightChild(pos) < size) { //left child(yes) right child(yes)
                boolean a = Heap[pos].timeRequest > Heap[leftChild(pos)].timeRequest;
                boolean b = Heap[pos].timeRequest > Heap[rightChild(pos)].timeRequest;
                if (a || b) {
                    if (Heap[leftChild(pos)].timeRequest < Heap[rightChild(pos)].timeRequest) {
                        swap(pos, leftChild(pos));
                        minHeapify(leftChild(pos));
                    } else {
                        swap(pos, rightChild(pos));
                        minHeapify(rightChild(pos));
                    }
                }
            }
        }

        public User removeFirst() {
            User popped = Heap[FRONT];
            Heap[FRONT] = Heap[size - 1];
            size--;
            minHeapify(FRONT);
            return popped;
        }

        public boolean isEmpty(){
            return size == 0;
        }
    }

    public static class MinHeapNodes{
        private Node[] Heap;
        private int size;
        private int maxsize;
        private final int FRONT = 0;

        public MinHeapNodes(int maxsize) {
            this.maxsize = maxsize;
            this.size = 0;
            Heap = new Node[this.maxsize];
        }

        private int parent(int pos) {
            return (pos-1) / 2;
        }
        private int leftChild(int pos) {
            return (2 * pos) + 1;
        }
        private int rightChild(int pos) {
            return (2 * pos) + 2;
        }

        private void swap(int fpos, int spos) {
            Node tmp = Heap[fpos];
            Heap[fpos] = Heap[spos];
            Heap[spos] = tmp;
        }

        public void insert(Node element) {
            if (size >= maxsize)
                return;
            Heap[size] = element;
            int current = size;

            if (current != FRONT) {
                while (Heap[current].distance < Heap[parent(current)].distance) {
                    swap(current, parent(current));
                    current = parent(current);
                }
            }
            size++;
        }

        private int minHeapify(int pos) {
            if (leftChild(pos) >= size && rightChild(pos) >= size) //no child
                return 0;
            if(leftChild(pos) < size && rightChild(pos) >= size) { //left child(yes) right child(no)
                if (Heap[leftChild(pos)].distance < Heap[pos].distance) {
                    swap(pos, leftChild(pos));
                    minHeapify(leftChild(pos));
                }else return 0;
            }else if (leftChild(pos) < size && rightChild(pos) < size) { //left child(yes) right child(yes)
                boolean a = Heap[pos].distance > Heap[leftChild(pos)].distance;
                boolean b = Heap[pos].distance > Heap[rightChild(pos)].distance;
                if (a || b) {
                    if (Heap[leftChild(pos)].distance < Heap[rightChild(pos)].distance) {
                        swap(pos, leftChild(pos));
                        minHeapify(leftChild(pos));
                    } else {
                        swap(pos, rightChild(pos));
                        minHeapify(rightChild(pos));
                    }
                }else return 0;
            }
            return 1;
        }

        public Node removeFirst() {
            Node popped = Heap[FRONT];
            Heap[FRONT] = Heap[size - 1];
            size--;
            minHeapify(FRONT);
            return popped;
        }

        public boolean isEmpty(){
            return size == 0;
        }

        public void setValue(Node node, double new_distance){
            int pos = 0;
            for (int i = 0; i < size; i++){
                if (Heap[i].equals(node)) {
                    pos = i;
                    break;
                }
            }
            node.distance = new_distance;
            int t = minHeapify(pos);
            if (t == 0) {
                while (pos != 0 && Heap[pos].distance < Heap[parent(pos)].distance) {
                    swap(pos, parent(pos));
                    pos = parent(pos);
                }
            }
        }
    }

    public static class User{
        double timeTakes;
        double timeRequest;
        long start, end;
        Edge lastEdge;
        LinkedList<Long> path;
        ArrayList<Edge> path_edges;

        public User(double time, long start, long end){
            this.timeRequest = time;
            this.start = start;
            this.end = end;
            timeTakes = 0;
            lastEdge = null;
            path = new LinkedList<>();
            path_edges = new ArrayList<>();
        }

        public void insertTime(double d){
            timeTakes += d;
        }

        public void write_details(){
            System.out.print("\n-----------------user details-----------------\n");
            System.out.println("time of request : " + timeRequest);
            System.out.println("time takes to reach : " + (120 * timeTakes));
            System.out.print("path : ");
            for (long i: path)
                System.out.print(i + " , ");
        }
    }
}

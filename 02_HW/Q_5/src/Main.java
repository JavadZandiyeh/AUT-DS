import java.util.Scanner;

public class Main {

    private static StringBuilder output = new StringBuilder();
    public static void main(String[] args) {
        doubly_linked_list dll = new doubly_linked_list();
        Scanner scanner = new Scanner(System.in);

        String input;
        do {
            input = scanner.next();
            switch (input){
                case "push": dll.push(scanner.nextInt()); break;
                case "pop": dll.pop(); break;
                case "print": dll.print(); break;
                case "findMiddle": dll.findMiddle(); break;
                case "removeMiddle": dll.removeMiddle(); break;
                case "finish":  System.out.print(output.toString()); return;
            }
        }while (true);
    }

    private static class doubly_linked_list {
        int length = 0;
        node head = null;
        node middle = null;
        node top = null;

        private void push(int data){
            if(length == 0){
                head = new node(data);
                middle = top = head;
                head.prev = null;
                head.next = null;
            }else {
                node new_node = new node(data);
                top.next = new_node;
                new_node.prev = top;
                new_node.next = null;
                top = top.next;

                if(length % 2 == 0)
                    middle = middle.next;
            }
            length ++;
        }

        private void pop(){
            if(length == 0)
                return;
            if(length == 1) {
                top = middle = head ;
                length --;
                return;
            }

            top = top.prev;
            top.next = null;
            length--;
            if(length % 2 == 0)
                middle = middle.prev;
        }

        private void print(){
            if(length == 0)
                output.append("\n");
            else {
                node iterator = top;
                do {
                    output.append(iterator.data + " ");
                    iterator = iterator.prev;
                } while (iterator != null);
                output.append("\n");
            }
        }

        private void findMiddle(){
            if(length == 0)
                output.append("-1\n");
            else
                output.append(middle.data + "\n");
        }

        private void removeMiddle(){
            if(length == 0)
                return;
            if(length == 1) {
                middle = head = top = null;
                length--;
                return;
            }
            if(length == 2){
                head = middle = top;
                top.prev = null;
                length--;
                return;
            }

            node helper = middle.prev;
            node helper1 = middle.next;
            helper.next = helper1;
            helper1.prev = helper;

            if(length % 2 != 0)
                middle = helper;
            else
                middle = helper1;

            length--;
        }
    }

    private static class node {
        int data;
        node prev = null;
        node next = null;
        public node(int data){
            this.data = data;
        }
    }
}
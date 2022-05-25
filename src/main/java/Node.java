import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Node implements Runnable {
    private ConcurrentLinkedQueue<Integer> messages;
    private Node nextNode;
    private PrintWriter latency;
    private PrintWriter throughput;

    public Node(int id)  throws IOException {
        this.messages = new ConcurrentLinkedQueue<>();
        latency = new PrintWriter(new FileWriter(BlockingQueueExample.HOME_PATH + BlockingQueueExample.LOG_NAME  + "/Latency" + id + ".txt"), true);
        throughput = new PrintWriter(new FileWriter(BlockingQueueExample.HOME_PATH + BlockingQueueExample.LOG_NAME + "/Throughput" + id + ".txt"), true);
    }

    public void setNext(Node next) {
        nextNode = next;
    }

    public void addMessage(Integer msg) {
        messages.add(msg);
    }
    public void printing(){
        System.out.println(messages.toString());
    }
    public void run() {
        while (true) {
            Integer message_top = messages.poll();
            if (message_top == null) {
                return;
            }
            if (Thread.currentThread().getId() == BlockingQueueExample.START_ID) {
                latency.println("Start: Latency: " + System.nanoTime() + " ns. " + message_top);
                throughput.println("Node: throughput point");
            }
            if (message_top == Thread.currentThread().getId()) {
                latency.println("End Latency message " + System.nanoTime() + " ns. " + message_top);
                System.out.println("Success thread: " + message_top);
            } else {
                nextNode.addMessage(message_top);
            }
        }
    }
}

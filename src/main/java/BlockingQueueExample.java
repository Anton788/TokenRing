import java.io.IOException;

public class BlockingQueueExample {
    private static int THREADS_NUMBER;
    private static int MESSAGES_NUMBER;
    public static String LOG_NAME;
    public static int START_ID;
    public static String HOME_PATH;

    public static void main(String[] args)  throws IOException {
        THREADS_NUMBER = 8;
        MESSAGES_NUMBER = 40;
        LOG_NAME = "LOGS_" + THREADS_NUMBER + "_" + MESSAGES_NUMBER;
        HOME_PATH = "C:/Users/ilina/Documents/";
        Node[] tokens = new Node[THREADS_NUMBER];
        Thread[] threads = new Thread[THREADS_NUMBER];
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = new Node(i);
        }
        for (int i = 0; i < tokens.length; i++){
            if (i + 1 != tokens.length ) {
                tokens[i].setNext(tokens[i + 1]);
            } else {
                tokens[i].setNext(tokens[0]);
            }
        }
        for (int i = 0; i < tokens.length; i++) {
            threads[i] = new Thread(tokens[i]);
        }
        START_ID = (int)threads[0].getId();
        System.out.println("Start" + START_ID);
        Integer[] msgs = new Integer[MESSAGES_NUMBER];
        for(int i = 0; i < msgs.length; i++) {
            msgs[i] = START_ID + (int) (Math.random() * THREADS_NUMBER);
            System.out.println(msgs[i]);
            tokens[0].addMessage(msgs[i]);
        }
        for ( Thread t: threads) {
            t.start();
        }
    }
}

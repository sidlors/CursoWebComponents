package service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.AsyncContext;
import javax.servlet.ServletRequest;

public class Handler implements Runnable {

    private static final Handler self;
    private static final String[] words = {
        "long", "short", "big", "small", "clever", "foolish", "tidy", "disorganized"
    };
    private Thread myThread;
    private boolean stop = false;
    private List<AsyncContext> queue = new ArrayList<AsyncContext>(100);
    private List<AsyncContext> inProgress = new ArrayList<AsyncContext>(100);

    static {
        self = new Handler();
    }

    private Handler() {
    }

    public static Handler getHandler() {
        return self;
    }

    public void stop() {
        stop = true;
    }

    public synchronized void addJob(AsyncContext job) {
        queue.add(job);
        System.out.println("Added a job, queue length is " + queue.size());
        if (myThread == null) {
            System.out.println("Started handler thread");
            myThread = new Thread(this);
            myThread.start();
        }
    }

    public void run() {
        while (!stop) {
            System.out.println("Handler loop running");
            try {
                Thread.sleep(5000 + ((int) (Math.random() * 5000)));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            // check the "queue"
            synchronized (this) {
                List<AsyncContext> l = inProgress;
                l.clear();
                inProgress = queue;
                queue = l;
            }
            if (!inProgress.isEmpty()) {
                System.out.println("Queue contains " + inProgress.size() + " elements");
                // Create the new information
                String value = "The word is: " + words[(int) (Math.random() * words.length)] + " and the number is: " + Math.random() * 1000000;
                Iterator<AsyncContext> iac = inProgress.iterator();
                while (iac.hasNext()) {
                    // generate responses
                    AsyncContext ac = iac.next();
                    ServletRequest req = ac.getRequest();
                    req.setAttribute("value", value);
                    System.out.println("Handler dispatching to a jsp");
                    ac.dispatch("asyncResponse.jsp");
                }
            }

        }
        System.out.println("Stopping handler thread");
    }
}

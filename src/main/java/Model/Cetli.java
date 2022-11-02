package Model;
import com.example.todoapplication.HelloApplication;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Cetli is a note card in which we store a Task we want to do
 * and other useful information about the mainTask.
 *
 */
public class Cetli {
    /**
     * the numberOfInstance gives us the iD of the object.
     */
    private static int numberOfInstance = 0;
    /**
     * Special unique identifier of the Cetli.
     */
    private int iD;
    /**
     * The description of the Task we want to do.
     */
    private String mainTask;

    private String taskTree;
    /**
     * if The Task is Finished
     */
    private boolean isFinished = false;
    /**
     * if the Task passed the Deadline
     */
    private boolean isDeadLineOver = false;
    /**
     * @see PriorityLevel
     */
    private PriorityLevel priorityLevel;
    //private DeadLine deadLine;

    private LocalDateTime deadLine;

    /**
     * This Method is only called if the Task is passed the deadline.
     */
    public void deadLineNotification(){
        System.out.println("This is a DeadLIne Notification from:"+iD);
    }
    /**
     *  scheduledExecutor is Start a Thread which Helps count the time.
     *  if the Task Passed the deadline call deadLineNotification method.
     *
     */
    public void scheduledExecutor(){
        System.out.println("scheduledExecutor Start:"+ iD + " at:" + LocalDateTime.now().toString());

        ScheduledExecutorService scheduler
                = Executors.newScheduledThreadPool(11);
        LocalDateTime now = LocalDateTime.now();
        long delay = deadLine.getSecond()-now.getSecond() +
                60*(deadLine.getMinute()-now.getMinute()) +
                60*60*(deadLine.getHour()-now.getHour())+
                60*60*24*(deadLine.getDayOfMonth()-now.getDayOfMonth())+
                (long) 60*60*24*365*(deadLine.getYear()-now.getYear());

        System.out.println("delay is :"+delay);
        scheduler.schedule(new Task(), delay,TimeUnit.SECONDS);
        scheduler.shutdown();
    }

    /**
     * The default constructor inicialise some value and
     * Put the Cetli object in the CetliContainer
     * @see CetliContainer
     * and also call scheduledExecutor
     */
    public Cetli(int i, String s, String red, String s1, int i1, int i2) {
        this.iD = numberOfInstance++;
        this.mainTask = "Empty Task";
        this.priorityLevel = PriorityLevel.Green;
        //this.deadLine = DeadLine.BeforeIDie;
        this.deadLine = LocalDateTime.now().plusSeconds(10);
        CetliContainer.add(this);
        scheduledExecutor();
    }
    /**
     * pass the given values and
     * Put the Cetli object in the CetliContainer
     * @see CetliContainer
     * and also call scheduledExecutor
     */
    public Cetli(int iD, String mainTask, String priorityLevel, String deadLine) {
        this.iD = iD;
        this.mainTask = mainTask;
        this.priorityLevel = PriorityLevel.valueOf(priorityLevel);
        //this.deadLine = deadLine;
        this.deadLine = LocalDateTime.parse(deadLine,HelloApplication.formatter);

        Tree.Node root = new Tree.Node<>(mainTask);
        this.taskTree = "taskTree";

        CetliContainer.add(this);
        scheduledExecutor();
    }
    public Cetli(int iD, String mainTask, String priorityLevel, String deadLine,String taskTree) {
        this(iD,mainTask,priorityLevel,deadLine);
        this.taskTree = "taskTree";
    }


    @Override
    public String toString(){
        return this.iD +" "+this.mainTask +" "+this.priorityLevel+" "+this.getDeadLine()+" "+this.isFinished+" "+this.isDeadLineOver;
    }

    class Task implements Runnable {
        @Override
        public void run()
        {
            deadLineNotification();
        }
    }

    public int getID() {
        return iD;
    }

    public void setID(int iD) {
        this.iD = iD;
    }

    public String getMainTask() {
        return mainTask;
    }

    public void setMainTask(String mainTask) {
        this.mainTask = mainTask;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }
    public boolean isDeadLineOver() {
        return isDeadLineOver;
    }

    public void setDeadLineOver(boolean deadLineOver) {
        isDeadLineOver = deadLineOver;
    }

    public PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }
    public String getPriorityLevelString() {
        return priorityLevel.toString();
    }

    public void setPriorityLevel(PriorityLevel priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public LocalDateTime getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDateTime deadLine) {
        scheduledExecutor();
        this.deadLine = deadLine;
    }
    public String getDeadLineString() {
        return deadLine.format(HelloApplication.formatter);
    }
    public String getTaskTree() {

        return "Tree";
        //return taskTree;
    }
}

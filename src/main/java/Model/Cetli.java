package Model;
import com.example.todoapplication.HelloApplication;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Cetli is a note card in which we store a Task we want to do
 * and other useful information about the task.
 *
 */
public class Cetli {
    /**
     * the numberOfInstance gives us the iD of the object.
     */
    private static int numberOfInstance = 100;
    /**
     * Special unique identifier of the Cetli.
     */
    private int iD;
    /**
     * The description of the Task we want to do.
     */

    private String parent;

    private String task;
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

    private LocalDateTime dated;

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
        System.out.println("scheduledExecutor Start:"+ iD + " at:" + LocalDateTime.now());

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
    public Cetli() {
        this.task = "Empty Task";
        this.parent = "root";
        this.priorityLevel = PriorityLevel.Green;
        //this.deadLine = DeadLine.BeforeIDie;
        this.deadLine = LocalDateTime.now().plusSeconds(10);
        this.dated = LocalDateTime.now();
        CetliContainer.add(this);
        //scheduledExecutor();
    }
    /**
     * pass the given values and
     * Put the Cetli object in the CetliContainer
     * @see CetliContainer
     * and also call scheduledExecutor
     */
    public Cetli(String task, String parent, String priorityLevel, String deadLine, String dated) {
        this.task = task;
        this.parent = parent;
        this.priorityLevel = PriorityLevel.valueOf(priorityLevel);
        //this.deadLine = deadLine;
        this.deadLine = LocalDateTime.parse(deadLine, HelloApplication.formatter);
        this.dated = LocalDateTime.parse(dated, HelloApplication.formatter);
        CetliContainer.add(this);
        //scheduledExecutor();
    }

    @Override
    public String toString(){
        return this.iD +" "+this.task+" "+this.parent+" "+this.priorityLevel+" "+this.getDeadLine()+" "+this.isFinished+" "+this.isDeadLineOver;
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

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
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

    public String getParent() {
        return parent;
    }
    public void setParent(String parent) {
        this.parent = parent;
    }

    public LocalDateTime getDeadLine() {
        return deadLine;
    }
    public String getDeadLineString() {
        return deadLine.format(HelloApplication.formatter);
    }

    public LocalDateTime getDated() {
        return dated;
    }
    public String getDatedString() {
        return dated.format(HelloApplication.formatter);
    }

    public void setDeadLine(LocalDateTime deadLine) {
        scheduledExecutor();
        this.deadLine = deadLine;
    }
    public void setDated(LocalDateTime dated) {
        this.dated = dated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cetli cetli = (Cetli) o;
        return iD == cetli.iD && isFinished == cetli.isFinished && isDeadLineOver == cetli.isDeadLineOver && Objects.equals(parent, cetli.parent) && Objects.equals(task, cetli.task) && priorityLevel == cetli.priorityLevel && Objects.equals(deadLine, cetli.deadLine) && Objects.equals(dated, cetli.dated);
    }
}

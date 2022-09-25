package Model;
import java.time.LocalDateTime;
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
    private static int numberOfInstance = 0;
    /**
     * Special unique identifier of the Cetli.
     */
    private int iD;
    /**
     * The description of the Task we want to do.
     */
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
    public Cetli() {
        this.iD = numberOfInstance++;
        this.task = "Empty Task";
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
    public Cetli(String task, PriorityLevel priorityLevel, LocalDateTime deadLine) {
        this.iD = numberOfInstance++;
        this.task = task;
        this.priorityLevel = priorityLevel;
        //this.deadLine = deadLine;
        this.deadLine = deadLine;
        CetliContainer.add(this);
        scheduledExecutor();
    }

    @Override
    public String toString(){
        return this.iD +" "+this.task+" "+this.priorityLevel+" "+this.getDeadLine()+" "+this.isFinished+" "+this.isDeadLineOver;
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
}

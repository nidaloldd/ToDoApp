package Model;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Cetli {
    private static int numberOfInstance = 0;
    private int iD;
    private String task;
    private boolean isFinished = false;
    private boolean isDeadLineOver = false;
    private PriorityLevel priorityLevel;
    //private DeadLine deadLine;
    private LocalDateTime deadLine;

    public void deadLineNotification(){
        System.out.println("This is a DeadLIne Notification from:"+iD);
    }

    public void scheduledExecutor(){
        System.out.println("scheduledExecutor Start:"+ iD + " at:" + LocalDateTime.now().toString());

        ScheduledExecutorService scheduler
                = Executors.newScheduledThreadPool(11);

        long delay = deadLine.getSecond()-LocalDateTime.now().getSecond() +
                60*(deadLine.getMinute()-LocalDateTime.now().getMinute()) +
                60*60*(deadLine.getHour()-LocalDateTime.now().getHour())+
                60*60*24*(deadLine.getDayOfMonth()-LocalDateTime.now().getDayOfMonth())+
                (long) 60*60*24*365*(deadLine.getYear() - LocalDateTime.now().getYear());

        System.out.println("delay is :"+delay);
        scheduler.schedule(new Task(), delay,TimeUnit.SECONDS);
        scheduler.shutdown();
    }

    public Cetli() {
        this.iD = numberOfInstance++;
        this.task = "Empty Task";
        this.priorityLevel = PriorityLevel.Green;
        //this.deadLine = DeadLine.BeforeIDie;
        this.deadLine = LocalDateTime.now().plusSeconds(10);
        CetliContainer.add(this);
        scheduledExecutor();
    }
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

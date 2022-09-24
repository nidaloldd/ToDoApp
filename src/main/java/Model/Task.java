package Model;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Task {
    private static int numberOfInstance = 0;
    private int iD;
    private String task;
    private boolean isFinished = false;
    private boolean isDeadLineOver = false;
    private String priorityLevel;
    //private DeadLine deadLine;
    private String deadLine;

    public void deadLineNotification(){
        System.out.println("This is a DeadLIne Notification from:"+iD);
    }


    public Task() {
        this.iD = numberOfInstance++;
        this.task = "Empty Task";
        this.priorityLevel = "Green";
        //this.deadLine = DeadLine.BeforeIDie;
        this.deadLine = "2020.06.23";
    }
    public Task(String task, String priorityLevel, String deadLine) {
        this.iD = numberOfInstance++;
        this.task = task;
        this.priorityLevel = priorityLevel;
        //this.deadLine = deadLine;
        this.deadLine = deadLine;
    }

    @Override
    public String toString(){
        return this.iD +" "+this.task+" "+this.priorityLevel+" "+this.getDeadLine()+" "+this.isFinished+" "+this.isDeadLineOver;
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

    public String getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }
}

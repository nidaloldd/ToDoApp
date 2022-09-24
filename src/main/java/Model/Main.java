package Model;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        Cetli cetli1 = new Cetli();
        Cetli cetli2 = new Cetli("asd",PriorityLevel.Red, LocalDateTime.now().plusSeconds(20));
        Cetli cetli3 = new Cetli("asd",PriorityLevel.Red, LocalDateTime.now().plusSeconds(30));
        Cetli cetli4 = new Cetli("asd",PriorityLevel.Red, LocalDateTime.now().plusSeconds(50));
        Cetli cetli5 = new Cetli("asd",PriorityLevel.Red, LocalDateTime.now().plusSeconds(130));

    }
}

package Database;

import Model.Cetli;
import Model.Tree;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DB {
    final String URL = "jdbc:derby:sampleDB;create=true";
    final String USERNAME = "";
    final String PASSWORD = "";

    //Létrehozzuk a kapcsolatot (hidat)
    Connection conn = null;
    Statement createStatement = null;
    DatabaseMetaData dbmd = null;

    public DB(){
        //Megpróbáljuk életre kelteni
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("A híd létrejött");
        } catch (SQLException ex) {
            System.out.println("Valami baj van a connection (híd) létrehozásakor.");
            System.out.println(""+ex);
        }

        //Ha életre kelt, csinálunk egy megpakolható teherautót
        if (conn != null){
            try {
                createStatement = conn.createStatement();
            } catch (SQLException ex) {
                System.out.println("Valami baj van van a createStatament (teherautó) létrehozásakor.");
                System.out.println(""+ex);
            }
        }

        //Megnézzük, hogy üres-e az adatbázis? Megnézzük, létezik-e az adott adattábla.
        try {
            dbmd = conn.getMetaData();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a DatabaseMetaData (adatbázis leírása) létrehozásakor..");
            System.out.println(""+ex);
        }

        try {
            ResultSet rs = dbmd.getTables(null, "APP", "TODO", null);
            if(!rs.next())
            {
                createStatement.execute("create table todo(task varchar(20), parent varchar(20), priorityLevel varchar(20), deadLine varchar(20), dated varchar(20))");
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van az adattáblák létrehozásakor.");
            System.out.println(""+ex);
        }
    }

    public ArrayList<Cetli> getAllToDo(){
        String sql = "select * from todo";
        ArrayList<Cetli> tasks = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            tasks = new ArrayList<>();

            while (rs.next()){
                Cetli actualCetli = new Cetli(rs.getString("task"),rs.getString("parent"),rs.getString("priorityLevel"),rs.getString("deadline"),rs.getString("dated"));
                tasks.add(actualCetli);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a taskok kiolvasásakor");
            System.out.println(""+ex);
        }
        return tasks;
    }

    public void addTask(Cetli cetli){
        try {
            String sql = "insert into todo (task, parent, priorityLevel, deadLine, dated) values (?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, cetli.getTask());
            preparedStatement.setString(2, cetli.getParent());
            preparedStatement.setString(3, cetli.getPriorityLevelString());
            preparedStatement.setString(4, cetli.getDeadLineString());
            preparedStatement.setString(5, cetli.getDatedString());


            preparedStatement.execute();


        } catch (SQLException ex) {
            System.out.println("Valami baj van a task hozzáadásakor");
            System.out.println(""+ex);
        }
    }

    public void updateTask(Cetli cetli){
        try {
            String sql = "update todo set task = ?, parent = ?, priorityLevel = ? , deadLine = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, cetli.getTask());
            preparedStatement.setString(2, cetli.getParent());
            preparedStatement.setString(3, cetli.getPriorityLevelString());
            preparedStatement.setString(4, cetli.getDeadLineString());
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a task hozzáadásakor");
            System.out.println(""+ex);
        }
    }

    public void removeTask(Cetli cetli){
        try {
            String sql = "delete from todo where task = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, cetli.getTask());
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a task törlésekor");
            System.out.println(""+ex);
        }
    }
    
}

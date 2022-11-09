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
            ResultSet rs = dbmd.getTables(null, "APP", "CONTACTS", null);
            if(!rs.next())
            {
                createStatement.execute("create table contacts(id INT not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),task varchar(20), priorityLevel varchar(20), deadLine varchar(30), taskTree varchar(30))");
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van az adattáblák létrehozásakor.");
            System.out.println(""+ex);
        }
    }

    public ArrayList<Cetli> getAllContacts(){
        String sql = "select * from contacts";
        ArrayList<Cetli> users = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            users = new ArrayList<>();

            while (rs.next()){
                Cetli actualCetli = new Cetli(rs.getInt("iD"),rs.getString("task"),rs.getString("priorityLevel"),rs.getString("DeadLine"), rs.getString("taskTree"));
                users.add(actualCetli);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a userek kiolvasásakor");
            System.out.println(""+ex);
        }
        return users;
    }

    public void addContact(Cetli cetli){
        try {
            String sql = "insert into contacts (task, priorityLevel, deadLine, taskTree) values (?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, cetli.getMainTask());
            preparedStatement.setString(2, cetli.getPriorityLevelString());
            preparedStatement.setString(3, cetli.getDeadLineString());
            preparedStatement.setString(4, cetli.getTaskTree());


            preparedStatement.execute();


        } catch (SQLException ex) {
            System.out.println("Valami baj van a contact hozzáadásakor");
            System.out.println(""+ex);
        }
    }

    public void updateContact(Cetli cetli){
        try {
            String sql = "update contacts set task = ?, priorityLevel = ? , deadLine = ? where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, cetli.getMainTask());
            preparedStatement.setString(2, cetli.getPriorityLevelString());
            preparedStatement.setString(3, cetli.getDeadLineString());
            preparedStatement.setInt(4, cetli.getID());
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a contact hozzáadásakor");
            System.out.println(""+ex);
        }
    }

    public void removeContact(Cetli cetli){
        try {
            String sql = "delete from contacts where id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, cetli.getID());
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a contact törlésekor");
            System.out.println(""+ex);
        }
    }

}

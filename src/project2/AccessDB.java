/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccessDB {

    private static final String USER_NAME = ""; //your DB username
    private static final String PASSWORD = ""; //your DB password
    private static final String URL = "";  //url of the DB host

    Connection conn;

    public AccessDB() {
        establishConnection();
    }

    public static void main(String[] args) {
        AccessDB dbManager = new AccessDB();
        HashMap<String, Set<Integer>> userData = new HashMap();
        ArrayList<Pet> pets = new ArrayList<Pet>();
        Pet pet = new Pet("jon");
        pets.add(pet);
        System.out.println(dbManager.getConnection());
        boolean found = dbManager.getUser("testuser", pets);
        //dbManager.saveDB(false, false, "bob", pet, pets);
        //System.out.println(userData);
        //dbManager.getPets(userData, pets, "test_user");
    }

    public Connection getConnection() {
        return this.conn;
    }

    //Establish connection
    public void establishConnection() {
        //Establish a connection to Database
        String url = "jdbc:derby://localhost:1527/PDCProj2DB; create=true";
        String user = "pdc";
        try {
            conn = DriverManager.getConnection(url, user, user);
        } catch (SQLException ex) {
            Logger.getLogger(AccessDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("DB Connected");
    }

    public ResultSet queryDB(String sql) {

        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
    ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(sql);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return resultSet;
    }

    public void updateDB(String sql) {

        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean getUser(String name, ArrayList<Pet> pets) {
        String sql = "SELECT * FROM USERS WHERE NAME = '" + name + "'";

        ResultSet user = queryDB(sql);
        try {
            if (user.next()) {
                sql = "SELECT * FROM PETS";
                ResultSet allpets = queryDB(sql);
                //System.out.println(user.getString("NAME"));
                sql = "SELECT * FROM PETS_TO_USERS WHERE USERID = " + user.getString("ID");
                ResultSet userpets = queryDB(sql);
                while (allpets.next()) {
                    //System.out.println("id: " + allpets.getString("ID"));
                    while (userpets.next()) {
                        //System.out.println("id: " + allpets.getString("ID"));
                        //System.out.println("petid: " + userpets.getString("PETID"));
                        if (allpets.getString("ID").equals(userpets.getString("PETID"))) {
                            Pet temp = new Pet(allpets.getString("NAME"), allpets.getInt("HUNGER"), allpets.getInt("HAPPY"), allpets.getInt("TRAINING"), allpets.getInt("LEVEL"), allpets.getInt("FLAGS"));
                            pets.add(temp);
                        }
                    }
                    userpets.beforeFirst();
                    //System.out.println(allpetset.getString("ID"));
                }
                //System.out.println(pets);
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccessDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void saveDB(boolean found, boolean cont, String name, Pet pet, ArrayList<Pet> pets) {
        if (!cont) //if not continuing a save
        {
            String sql;
            int userID = 1;
            if (!found) { //new user
                sql = "INSERT INTO USERS (Name) VALUES ('" + name + "')";
                updateDB(sql);
                sql = "SELECT MAX(ID) FROM USERS";
                ResultSet rs = queryDB(sql);
                try {
                    if (rs.next()) {        //save user ID as variable
                        userID = rs.getInt("1");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AccessDB.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            if (found)
            {
                sql = "SELECT ID FROM USERS WHERE NAME = ('"+ name + "')";
                ResultSet rs = queryDB(sql);
                try {
                    if (rs.next()) {        //save user ID as variable
                        userID = rs.getInt("ID");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AccessDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            sql = "INSERT INTO PETS (Name, Hunger, Happy, Training, Level, Flags) VALUES ('" + pet.getName() + "', " + pet.getHunger() + ", " + pet.getHappy() + ", " + pet.getTraining() + ", " + pet.getLevel() + ", " + pet.getFlags() + ")";
            updateDB(sql);
            sql = "SELECT MAX(ID) FROM PETS";
            ResultSet maxset = queryDB(sql);
            int petID = 1;
            try {
                if (maxset.next()) {
                    petID = maxset.getInt("1");
                }
            } catch (SQLException ex) {
                Logger.getLogger(AccessDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            sql = "INSERT INTO PETS_TO_USERS VALUES ("+petID+", "+userID+")";
            updateDB(sql);
        }
        else //continuing save - update record
        {
            System.out.println("use save");
            String sql = "SELECT * FROM PDC.PETS_TO_USERS INNER JOIN PETS ON PETID = ID WHERE NAME = '"+pet.getName()+"'";
            ResultSet rs = queryDB(sql);
            int petID = 0;
            try {
                if (rs.next())
                {
                    petID = rs.getInt("ID");
                }
            } catch (SQLException ex) {
                Logger.getLogger(AccessDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            sql = "UPDATE PETS SET Name = '"+pet.getName()+"', Hunger = "+ pet.getHunger()+", Happy = " + pet.getHappy() + ", Training = " + pet.getTraining()+", Level = " +pet.getLevel()+ ", Flags = "+pet.getFlags() + "WHERE ID = "+petID;
            updateDB(sql);
        }
    }
}

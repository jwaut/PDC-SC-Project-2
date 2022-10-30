/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package project2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ent
 */
public class AccessDBTest {
    
    public AccessDBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of establishConnection method, of class AccessDB.
     */
    @Test
    public void testEstablishConnection() {
        System.out.println("establishConnection");
        AccessDB instance = new AccessDB();
        instance.establishConnection();
        assertTrue(instance.getConnection()!=null);
    }

    /**
     * Test of queryDB method, of class AccessDB.
     */
    @Test
    public void testQueryDB() {
        System.out.println("queryDB");
        String sql = "SELECT NAME FROM USERS WHERE ID = 1";
        AccessDB instance = new AccessDB();
        String expResult = "testuser";
        ResultSet rs = instance.queryDB(sql);
        String result = "";
        try {
            if (rs.next())
            {
                result = rs.getString("NAME");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccessDBTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(expResult, result);
    }

    /**
     * Test of getUser method, of class AccessDB.
     */
    @Test
    public void testGetUser() {
        System.out.println("getUser");
        String name = "testuser";
        ArrayList<Pet> pets = new ArrayList<Pet>();
        AccessDB instance = new AccessDB();
        boolean expResult = true;
        boolean result = instance.getUser(name, pets);
        assertEquals(expResult, result);
    }
    
        @Test
    public void testGetUserNotFound() {
        System.out.println("getUser");
        String name = "awdesziujokcm";
        ArrayList<Pet> pets = new ArrayList<Pet>();
        AccessDB instance = new AccessDB();
        boolean expResult = false;
        boolean result = instance.getUser(name, pets);
        assertEquals(expResult, result);
    }
            @Test
    public void testGetUserPet() {
        System.out.println("getUser");
        String name = "testuser";
        ArrayList<Pet> pets = new ArrayList<Pet>();
        AccessDB instance = new AccessDB();
        String expResult = "testpet";
        boolean temp = instance.getUser(name, pets);
        String result = pets.get(0).getName();
        assertEquals(expResult, result);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project2;

import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

public class Model {

    Game game;
    String name; //current users username
    String petName;
    Pet pet;
    ArrayList<Pet> pets = new ArrayList<Pet>(); //current users pets
    AccessDB db = new AccessDB();

    public Model()
    {
        name = null;
        game = null;
        petName = null;
        pet = null;
    }
    public void startGame()
    {
        game = new Game(name, pet);
    }
    public boolean submitName(String name) {
        boolean found = db.getUser(name, pets);
        this.name = name;
        return found;
    }
}

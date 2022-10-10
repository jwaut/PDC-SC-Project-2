/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package project2;

/** This class is used to store
 *  data pertaining to the pet
 */

public class Pet {
    private int hunger, happy, training, level, flags;
    private String name;
    Pet(String name)
    {
        this.name = name;
        this.hunger = 0;
        this.happy = 100;
        this.training = 0;
        this.level = 1;
        this.flags = 0;
    }
    Pet(String name, int hunger, int happy, int training, int level, int flags) //for getting a pet from file
    {
        this.name = name;
        this.hunger = hunger;
        this.happy = happy;
        this.training = training;
        this.level = level;
        this.flags = flags;
    }
    public boolean boundsCheck()   //ensure variables are within range 0-100
    { 
        if (flags >= 5) //Pets with 5 flags run away and are no longer playable
        {
            System.out.println(name + " has run away.");
            return true;
        }
        if (hunger >= 100)
        {
            System.out.println(name + " is starving! Feed them quick!");
            flags+=1;
            hunger = 100;
        }
        if (hunger < 0)
            hunger = 0;
        if (happy > 100)
            happy = 100;
        if (happy <= 0)
        {
            System.out.println(name + " is unhappy. Try playing with them to cheer them up");
            happy = 0;
            flags+=1;
        }
            
        if (training > 100)
        {
            training = 0;
            level+=1;
        }
        return false;
    }
    public String toString()
    {
        return name + ", Level " + level + "\nHunger: " + hunger + "/100\nHappiness: "+happy + "/100\nTraining: "+training + "/100";
    }
    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getHappy() {
        return happy;
    }

    public void setHappy(int happy) {
        this.happy = happy;
    }

    public int getTraining() {
        return training;
    }

    public void setTraining(int training) {
        this.training = training;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}

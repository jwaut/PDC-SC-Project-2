/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project2;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class is used to play the game itself and allows for interacting with
 * the pet
 */
public class Game {

    private Pet pet;
    private String userName;
    private String alert;

    public String getAlert() {
        if (!pet.getAlert().equals("")) {
            this.alert = this.alert + "</br>" + pet.getAlert();
        }
        return this.alert;
    }

    Game(String userName, String petName) {
        this.userName = userName;
        this.pet = new Pet(petName);
        this.alert = "";
    }

    Game(String userName, Pet pet) {
        this.userName = userName;
        this.pet = pet;
        this.alert = "";
    }

    void feed() {

        if (pet.getHunger() <= 0) {
            alert = pet.getName() + " is not hungry";
            System.out.println(pet.getName() + " is not hungry");
        } else {
            alert = "";
            pet.setHunger(pet.getHunger() - ThreadLocalRandom.current().nextInt(2, 10 + 1));
        }
        if (pet.boundsCheck()) {
            alert = "BREAK";
        }
    }

    void play() {

        int amount = ThreadLocalRandom.current().nextInt(10, 20 + 1);
        pet.setHappy(pet.getHappy() + amount);
        if (pet.boundsCheck()) {
            alert = "BREAK";
        }
    }

    void train() {

        if (pet.getHappy() < ThreadLocalRandom.current().nextInt(1, 25 + 1)) {
            alert = pet.getName() + " is sick of training";
            System.out.println(pet.getName() + " is sick of training");
        } else {
            alert = "";
            int hungerThreshold = ThreadLocalRandom.current().nextInt(25, 75 + 1);
            if (pet.getHunger() > hungerThreshold) {
                int hungerMult = pet.getHunger() / hungerThreshold;
                pet.setHappy(pet.getHappy() - ThreadLocalRandom.current().nextInt(5, 20 + 1) * hungerMult);
            }
            pet.setTraining(pet.getTraining() + ThreadLocalRandom.current().nextInt(2, 10 + 1));
            pet.setHunger(pet.getHunger() + ThreadLocalRandom.current().nextInt(5, 20 + 1));
        }
        if (pet.boundsCheck()) {
            alert = "BREAK";
        }
    }

    Pet getPet() {
        return pet;
    }
}

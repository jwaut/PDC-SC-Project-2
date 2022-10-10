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

    Game(String userName, String petName) {
        this.userName = userName;
        this.pet = new Pet(petName);
    }

    Game(String userName, Pet pet) {
        this.userName = userName;
        this.pet = pet;
    }

    void feed() {
        if (pet.getHunger() <= 0) {
            System.out.println(pet.getName() + " is not hungry");
        } else {
            pet.setHunger(pet.getHunger() - ThreadLocalRandom.current().nextInt(2, 10 + 1));
        }
    }

    void play() {
        int amount = ThreadLocalRandom.current().nextInt(10, 20 + 1);
        pet.setHappy(pet.getHappy() + amount);
    }

    void train() {
        if (pet.getHappy() < ThreadLocalRandom.current().nextInt(1, 25 + 1)) {
            System.out.println(pet.getName() + " is sick of training");
        } else {
            int hungerThreshold = ThreadLocalRandom.current().nextInt(25, 75 + 1);
            if (pet.getHunger() > hungerThreshold) {
                int hungerMult = pet.getHunger() / hungerThreshold;
                pet.setHappy(pet.getHappy() - ThreadLocalRandom.current().nextInt(5, 20 + 1) * hungerMult);
            }
            pet.setTraining(pet.getTraining() + ThreadLocalRandom.current().nextInt(2, 10 + 1));
            pet.setHunger(pet.getHunger() + ThreadLocalRandom.current().nextInt(5, 20 + 1));
        }
    }

    void action() { //Play the game
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println(pet.toString());
            System.out.println("A. Feed " + pet.getName() + "\nB. Play with " + pet.getName() + "\nC. Train " + pet.getName() + "\nX. Save & Quit Game");
            String input = scan.next();
            if (input.equalsIgnoreCase("A")) {
                feed();
            }
            if (input.equalsIgnoreCase("B")) {
                play();
            }
            if (input.equalsIgnoreCase("C")) {
                train();
            }
            if (input.equalsIgnoreCase("X")) {
                scan.close();
                break;
            }
            if (pet.boundsCheck())
            {
                System.out.println("You have lost.");
                break;
            }
            
        }
    }

    Pet getPet() {
        return pet;
    }
}

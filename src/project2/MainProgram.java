/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package project2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

/**
 * This class prepares the game by loading save data from files and
 * instantiating the game, and also saving upon exit
 */
public class MainProgram {

    public static void main(String[] args) {
        Game game = null;
        System.out.println("Welcome to Fun Virtual Pet Game !!");
        String name; //current users username
        String petName;
        ArrayList<Pet> pets = new ArrayList<Pet>(); //current users pets
        HashMap<String, Set<Integer>> userData = new HashMap(); //stores all usernames & IDs of user pets
        Scanner scan = new Scanner(System.in);
        boolean play = false;
        while (true) //main menu loop
        {
            System.out.println("\nA. Start Game \r\n" + "B. Exit");
            String input = scan.nextLine();
            if (input.equalsIgnoreCase("B")) {
                break;
            }
            if (input.equalsIgnoreCase("A")) {
                play = true;
                break;
            } else {
                System.out.println("Please enter 'A' or 'B'.");
            }
        }

        if (play) {
            petName = "";
            AccessFile file = new AccessFile();
            boolean useSave = false;
            while (true) {
                System.out.println("Please enter your username: ");
                name = scan.nextLine();
                if (StringUtils.containsWhitespace(name)) {
                    System.out.println("Sorry, usernames cannot contain spaces.");
                } else {
                    break;
                }
            }
            file.getUsers(userData);    //add users data from file to userData

            boolean found = false;
            for (String key : userData.keySet()) {  //search user data for input username
                if (userData.get(name) != null) //found
                {
                    found = true;
                    System.out.println("Welcome back " + name + "!");
                }
                if (found) {
                    break;
                }
            }
            if (found == true) {    //if user found to be existing in file
                //search pets.txt
                file.getPets(userData, pets, name);
                if (pets.size() > 0)
                {
                    while (true) {
                    System.out.println("Would you like to continue an existing save? (Y/N)");
                    String cont = scan.next();
                    if (cont.equalsIgnoreCase("Y")) {
                        useSave = true;
                        break;
                    }
                    if (cont.equalsIgnoreCase("N")) {
                        System.out.println("Starting a new save");
                        break;
                    } else {
                        System.out.println("Please enter 'Y' or 'N'.");
                    }
                }
                }
                
                if (useSave) {  //allow the user to continue from a saved pet
                    System.out.println("Existing pets:");
                    for (int i = 0; i < pets.size(); i++) {
                        System.out.println(i + 1 + ". " + pets.get(i).getName());
                    }
                    System.out.println("Please enter the number next to the pet you want to continue: ");
                    int chosenPet = 0;
                    while (true) {
                        try {
                            chosenPet = scan.nextInt();
                        } catch (Exception e) {
                            System.out.println("Please enter a number");
                            scan.nextLine();
                            continue;
                        }
                        for (int i = 0; i < pets.size(); i++) { //searchiing pets for users input
                            if (chosenPet == i + 1) {
                                System.out.println("Now playing with " + pets.get(i).getName());
                                game = new Game(name, pets.get(i));
                            }
                        }
                        if (game != null) {
                            break;
                        } else {
                            System.out.println("Please only enter the number next to the pet you wish to choose");
                        }
                    }
                } else {    //name a new pet
                    boolean exists;
                    while (true) {
                        System.out.println("Enter a pet name: ");
                        exists = false;
                        petName = scan.next();
                        if (StringUtils.containsWhitespace(petName)) {
                            System.out.println("Sorry, pet names cannot contain spaces.");
                            continue;
                        }
                        for (Pet pet : pets) {  //check pet does not already exist
                            if (pet.getName().equalsIgnoreCase(petName)) {
                                exists = true;
                                while (true) {
                                    System.out.println("You already have a pet with that name. Continue with that pet instead? (Y/N)");
                                    String input = scan.next();
                                    if (input.equalsIgnoreCase("Y")) {
                                        game = new Game(name, pet);
                                        useSave = true;
                                        break;
                                    }
                                    if (input.equalsIgnoreCase("N")) {
                                        System.out.println("Please enter a different name");
                                        break;
                                    } else {
                                        System.out.println("Please enter 'Y' or 'N'.");
                                    }
                                }
                            }
                        }
                        if (exists == false) {
                            game = new Game(name, new Pet(petName));
                        }
                        if (game != null) {
                            break;
                        }
                    }

                }
            } else { //new user
                userData.put(name, new HashSet<Integer>());
                while (true) {
                    System.out.println("Enter a pet name: ");
                    petName = scan.next();
                    if (StringUtils.containsWhitespace(petName)) {
                        System.out.println("Sorry, pet names cannot contain spaces.");
                        continue;
                    } else {
                        break;
                    }
                }
                game = new Game(name, new Pet(petName));
            }
            game.action();
            System.out.println("Goodbye!");
            Pet pet = game.getPet();
            file.save(userData, useSave, name, pet);
        }

    }

}

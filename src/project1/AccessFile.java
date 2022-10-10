/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class AccessFile {   //Class is for file read/writing

    FileReader fr = null;   //to read file
    PrintWriter pw = null;
    BufferedReader inputStream = null;
    final String USERS = "./resources/users.txt";   //location of users file
    final String PETS = "./resources/pets.txt";     //location of pets file

    void getUsers(HashMap<String, Set<Integer>> userData) {
        try {   //get users.txt
            fr = new FileReader(USERS);
            inputStream = new BufferedReader(fr);
            String line = null;
            

            while ((line = inputStream.readLine()) != null) {
                Set<Integer> userPets = new HashSet<Integer>(); //store pet IDs
                userPets.clear();
                String[] data = line.split(" ", 0);
                for (int i = 1; i < data.length; i++) {
                    userPets.add(Integer.valueOf(data[i]));
                }
                userData.put(data[0], userPets);
            }
            fr.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Error reading from file ");
            System.exit(0);
        }
    }

    void getPets(HashMap<String, Set<Integer>> userData, ArrayList<Pet> pets, String name) {
        try {
            fr = new FileReader(PETS);
            inputStream = new BufferedReader(fr);
            String line = null;
            while ((line = inputStream.readLine()) != null) {
                String[] data = line.split(" ", 0);
                if (userData.get(name).contains((Integer.valueOf(data[0])))) //if this pet belongs to current user
                {   //add this pet to the pets arrayList
                    Pet temp = new Pet(data[1], (Integer.valueOf(data[2])), (Integer.valueOf(data[3])), (Integer.valueOf(data[4])), (Integer.valueOf(data[5])), Integer.valueOf(data[6]));
                    if (temp.getFlags() < 5)    //Pets with 5 flags run away and are no longer playable
                    {
                        pets.add(temp);
                    }
                    
                }
            }
            fr.close();
            inputStream.close();

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Error reading from file ");
            System.exit(0);
        }
    }

    void save(HashMap<String, Set<Integer>> userData, boolean useSave, String name, Pet pet) {

        try {   //save user
            //System.out.println("Saving");
            pw = new PrintWriter(new FileOutputStream(USERS));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        Set<Integer> allPetIDs = new HashSet<Integer>();
        if (!useSave) //if the user chose to create a new pet rather than continue a save
        {
            for (Set<Integer> IDs : userData.values()) {
                allPetIDs.addAll(IDs);
            }
        }
        Set<Integer> userPetIDs = new HashSet<Integer>();
        for (String key : userData.keySet()) {  //iterate through each user
            //System.out.println(key);
            String line = key;
            for (int data : userData.get(key)) //add each users pets
            {
                line = line + " " + data;
            }
            if (key.equalsIgnoreCase(name))
            {
                if (!useSave)
                    line = line + " " + (Collections.max(allPetIDs) + 1);    //add new pet
                for (Integer data : userData.get(name))
                {
                    userPetIDs.add(data);
                }
            }
            pw.println(line);
        }
        pw.close();
        HashMap<Integer, Pet> pets = new HashMap(); //PETS & THEIR ID
        try {   //save pets
            fr = new FileReader(PETS);
            inputStream = new BufferedReader(fr);
            String line = null;
            while ((line = inputStream.readLine()) != null) {
                String[] data = line.split(" ", 0);
                Pet temp = new Pet(data[1], (Integer.valueOf(data[2])), (Integer.valueOf(data[3])), (Integer.valueOf(data[4])), (Integer.valueOf(data[5])), Integer.valueOf(data[6]));
                pets.put(Integer.valueOf(data[0]), temp);
            }
            fr.close();
            inputStream.close();
            pw = new PrintWriter(new FileOutputStream(PETS));
            for (Integer ID : pets.keySet()) {
                line = "";
                String temp;
                if (useSave && userPetIDs.contains(ID) && pet.getName().equalsIgnoreCase(pets.get(ID).getName()))
                {
                    temp = pet.getName() + " " + pet.getHunger() + " " + pet.getHappy() + " " + pet.getTraining() + " " + pet.getLevel() + " " + pet.getFlags();
                }
                else    //leave unchanged
                {
                    temp = pets.get(ID).getName() + " " + pets.get(ID).getHunger() + " " + pets.get(ID).getHappy() + " " + pets.get(ID).getTraining() + " " + pets.get(ID).getLevel() + " " + pets.get(ID).getFlags();
                
                }
                line = line + ID + " " + temp;
                pw.println(line);
            }
            if (!useSave)
            {
                int ID = Collections.max(allPetIDs) + 1;
                String temp = pet.getName() + " " + pet.getHunger() + " " + pet.getHappy() + " " + pet.getTraining() + " " + pet.getLevel() + " " + pet.getFlags();
                line = "";
                line = line + ID + " " + temp;
                pw.println(line);
            }
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Error reading from file ");
            System.exit(0);
        }

    }
}

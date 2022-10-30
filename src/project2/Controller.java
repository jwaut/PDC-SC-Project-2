/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {

    private View view;
    private Model model;
    private boolean cont = false;

    Controller() {
        view = new View();
        model = new Model();
    }

    public static void main(String[] args) {
        Controller control = new Controller();
        control.view.getButton3().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button pressed");
                control.view.pressPlay();

            }
        });
        control.view.getButton1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.view.setVisible(false);
                control.view.dispose();
            }
        });
        control.view.getSubmit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (control.model.name == null) {
                    String name = control.view.submitName();
                    if (control.model.submitName(name)) {
                        control.view.getLabel().setText("Would you like to continue an existing save?");
                        control.view.getYes().setVisible(true);
                        control.view.getNo().setVisible(true);
                    } else {
                        control.view.getLabel().setText("Enter a pet name: ");
                        control.view.getTextField().setVisible(true);
                        control.view.getSubmit().setVisible(true);
                    }
                } else {
                    String petName = control.view.getTextField().getText();
                }
            }
        });
        control.view.getYes().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.view.getYes().setVisible(false);
                control.view.getNo().setVisible(false);
                control.view.getPetSelect().setVisible(true);
                control.view.getLabel().setText("Select a pet to continue");
                //control.view.getSubmit().setVisible(true);
                for (Pet pet : control.model.pets) {
                    control.view.getPetSelect().addItem(pet.getName());
                }
                control.view.getSelectButton().setVisible(true);
            }
        });
        control.view.getSelectButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.model.petName = control.view.getPetSelect().getSelectedItem().toString();
                for (Pet pet : control.model.pets) {
                    if (control.model.petName == pet.getName())
                    {
                        control.model.pet = pet;
                        control.model.startGame();
                        control.view.startGame();
                        control.refresh();
                    }
                }
            }
        });
        
        control.view.getNo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        control.view.setVisible(true);
    }
    
    void refresh()
    {
        view.getLabel().setText("<html>"+model.pet.toString()+"A. Feed " + model.pet.getName() + "<br/>B. Play with " + model.pet.getName() + "<br/>C. Train " + model.pet.getName() + "<br/>X. Save & Quit Game</html>");
    }
}

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
    private boolean found;

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
                control.model.db.saveDB(control.found, control.cont, control.model.name, control.model.pet, control.model.pets);
                control.view.setVisible(false);
                control.view.dispose();
            }
        });
        control.view.getPlayWith().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.model.game.play();
                control.refresh();
            }
        });
        control.view.getFeed().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.model.game.feed();
                control.refresh();
            }
        });
        control.view.getTrain().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.model.game.train();
                control.refresh();
            }
        });
        control.view.getSubmit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (control.model.name == null) {
                    String name = control.view.submitName();
                    control.found = control.model.submitName(name);
                    if (control.found) {
                        control.view.getLabel().setText("Would you like to continue an existing save?");
                        control.view.getYes().setVisible(true);
                        control.view.getNo().setVisible(true);
                    } else {
                        control.view.getLabel().setText("Enter a pet name: ");
                        control.view.getTextField().setText("");
                        control.view.getTextField().setVisible(true);
                        control.view.getSubmit().setVisible(true);
                    }
                } else {
                    control.model.petName = control.view.getTextField().getText();
                    control.model.pet = new Pet(control.model.petName);
                    control.model.startGame();
                    control.view.startGame();
                    control.view.getSelectButton().setVisible(false);
                    control.view.getPetSelect().setVisible(false);
                    control.view.getTextField().setVisible(false);
                    control.view.getSubmit().setVisible(false);
                    control.refresh();
                }

            }
        });
        control.view.getYes().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.cont = true;
                control.view.getYes().setVisible(false);
                control.view.getNo().setVisible(false);
                
                //control.view.getSubmit().setVisible(true);
                for (Pet pet : control.model.pets) {
                    if (pet.getFlags()<5)
                    {
                        System.out.println(pet.getFlags());
                        control.view.getPetSelect().addItem(pet.getName());
                    }
                }
                if (control.view.getPetSelect().getItemCount() == 0)
                {
                    control.view.getPetSelect().setVisible(false);
                     control.view.getLabel().setText("Sorry you have no pets to continue");
                     control.view.getNo().setVisible(true);
                     control.view.getNo().setText("New pet");
                }
                else
                {
                    control.view.getLabel().setText("Select a pet to continue");
                    control.view.getPetSelect().setVisible(true);
                    control.view.getSelectButton().setVisible(true);
                }
            }
        });
        control.view.getSelectButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.model.petName = control.view.getPetSelect().getSelectedItem().toString();
                for (Pet pet : control.model.pets) {
                    if (control.model.petName == pet.getName()) {
                        control.model.pet = pet;
                        control.model.startGame();
                        control.view.startGame();
                        control.view.getSelectButton().setVisible(false);
                        control.view.getPetSelect().setVisible(false);
                        control.refresh();
                    }
                }
            }
        });

        control.view.getNo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.cont = false;
                control.view.getYes().setVisible(false);
                control.view.getNo().setVisible(false);
                control.view.getLabel().setText("Enter a pet name: ");
                control.view.getTextField().setText("");
                control.view.getTextField().setVisible(true);
                control.view.getSubmit().setVisible(true);
            }
        });
        control.view.setVisible(true);
    }

    void refresh() {
        view.getAlert().setText("<html>" + model.game.getAlert() + "</html>");
        if (view.getAlert().getText().contains("BREAK")) {
            view.getLabel().setVisible(false);
            view.getAlert().setVisible(false);
            view.getPlayWith().setEnabled(false);
            view.getFeed().setEnabled(false);
            view.getTrain().setEnabled(false);
            view.getAlert().setText("Game over");
            //refresh();
        }
        view.getAlert().setVisible(true);
        view.getLabel().setText("<html><br/>" + model.game.getPet().toString() + "</html>");
        

    }
}

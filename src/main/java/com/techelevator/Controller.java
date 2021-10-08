package com.techelevator;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private Machine vendingMachine;
    private UserInterface userInterface;
    private User user;

    public Controller(Machine vendingMachine, UserInterface userInterface, User user) {
        this.vendingMachine = vendingMachine;
        this.userInterface = userInterface;
        this.user = user;
        startProgram();
    }

    public void startProgram() {

        Boolean isUserPresent = true;

        while (isUserPresent) {
            userInterface.displayMainMenu();
            String userSelection = userInterface.getUserInput();
            switch (userSelection) {
                case "1":
                    //(1) Display Vending Machine Items
                    //System.out.println("You selected #1");
                    vendingMachine.displayProducts();
                    System.out.println();
                    //userInterface.displayMainMenu();
                    break;
                case "2":
                    //(2) Purchase
                    //System.out.println("You selected #2");
                    purchase();
                    break;
                case "3":
                    //(3) Exit
                    //System.out.println("You selected #3");
                    isUserPresent = false;
                    break;
                default:
                    System.out.println("Please choose a number between 1 and 3");
                    break;
            }

        }


    }

    public void displayVendingMachineItems(){
        vendingMachine.displayProducts();
    }

    public void purchase(){
        Boolean isUserPresent = true;

        while (isUserPresent) {

            //Display Purchase Menu
            userInterface.displayPurchaseMenu(user.getCurrentMoneyProvided());

            //Get User Input
            String purchaseMenuUserSelection = userInterface.getUserInput();

            //If (1) Feed Money...
            if (purchaseMenuUserSelection.equals("1")) {
                user.addMoney(userInterface.feedMoney());
            }

            //If (2) Select Product...
            else if (purchaseMenuUserSelection.equals("2")) {
                String slotChoice = userInterface.selectProduct();

                //Check if slot is valid
                // If valid, Enter Product into Purchase List, subtract price of product from currentMoneyProvided
                if (vendingMachine.isSlotValid(slotChoice)){

                    if (vendingMachine.checkProductStock(slotChoice) < 1) {
                        System.out.println("Product is sold out. Please make another selection.");
                    } else {
                        double priceOfPurchase = vendingMachine.getProduct(slotChoice).getPrice();
                        if (user.getCurrentMoneyProvided() >= priceOfPurchase) {
                            user.setCurrentMoneyProvided(user.getCurrentMoneyProvided() - priceOfPurchase);
                            user.addProductToPurchaseList(vendingMachine.getProduct(slotChoice));
                            userInterface.printProductInfo(vendingMachine.getProduct(slotChoice));
                        } else {
                            System.out.println("Please insert more money");
                        }
                    }




                    //List<Product> productsToPurchase = user.getProductsToPurchase();
                    //for (Product item : productsToPurchase)
                    //    System.out.println(item.getName());
                }

                else {
                    System.out.println("This is not a valid choice");
                }
            }

            //If (3) Finish Transaction...
            else if (purchaseMenuUserSelection.equals("3")) {
                userInterface.finishTransaction();
                isUserPresent = false;
            }

            //If input is not 1, 2, or 3, prompt user and get new input
            else {
                System.out.println("Please enter a selection between 1 and 3");
            }
        }
    }

    public void exit() {

    }

}
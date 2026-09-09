package gogocoffeecafe;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

class Customer extends Thread {
    private int customerID;
    private int lastBaristaNumber;
    private volatile boolean c_running = true;
    private Semaphore seat;

    public Customer(int customerId, Semaphore semaphore) {
        this.customerID = customerId;
        this.seat = semaphore;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setLastBaristaNumber(int lastBaristaNumber) {
        this.lastBaristaNumber = lastBaristaNumber;
    }

    public int getLastBaristaNumber() {
        return lastBaristaNumber;
    }
    
    public void consumingDrink(int customerID, String drink) throws InterruptedException{
        Random randomdrinkingProgress = new Random();
        int drinkingProgress = randomdrinkingProgress.nextInt(2) + 2; // Random number between 2 or 3
        
        System.out.println("Customer " + this.customerID + " is sipping " + drink + ": 80% left");
        Thread.sleep(drinkingProgress*500);
        System.out.println("Customer " + this.customerID + " is sipping " + drink + ": 60% left");
        Thread.sleep(drinkingProgress*500);
        System.out.println("Customer " + this.customerID + " is sipping " + drink + ": 40% left");
        Thread.sleep(drinkingProgress*500);
        System.out.println("Customer " + this.customerID + " is sipping " + drink + ": 20% left");
        Thread.sleep(drinkingProgress*500);
        System.out.println("Customer " + this.customerID + " has finished drinking " + drink);

    }
    
    public void stopCustomerThread(){
        System.out.println("Customer " + this.customerID + " has left the cafe.");

        c_running = false;
    }
    
    
    public void customerOrdering(){
        Barista[] baristas = {GoGoCoffeeCafe.barista1, GoGoCoffeeCafe.barista2, GoGoCoffeeCafe.barista3};

        int nextBaristaNumber = (lastBaristaNumber + 1) % 3;

        synchronized (baristas[nextBaristaNumber]) {
            Random randomDrink = new Random(System.currentTimeMillis());
            int drinkChoice = randomDrink.nextInt(10) + 1; // Random number from 1-10
            
            if (drinkChoice == 1){
                try {
                    System.out.println("Customer " + this.customerID + " ordered juice from Barista " + (nextBaristaNumber + 1) + " and payed RM7");

                    baristas[nextBaristaNumber].serveDrink(this, "Juice");
                    
                    try {
                        baristas[nextBaristaNumber].wait();
                        consumingDrink(this.customerID, "Juice");
                        
                    } catch (InterruptedException e) {
                    }
                    
                } catch (InterruptedException ex) {
                }
                
            } else if (drinkChoice == 2 || drinkChoice == 3) {
                try {
                    System.out.println("Customer " + this.customerID + " ordered expresso from Barista " + (nextBaristaNumber + 1) + " and payed RM6");

                    baristas[nextBaristaNumber].serveDrink(this, "Expresso");
                    
                    try {
                        baristas[nextBaristaNumber].wait();
                        consumingDrink(this.customerID, "Expresso");
                        
                    } catch (InterruptedException e) {
                    }
                    
                } catch (InterruptedException ex) {
                }
                
            } else {
                try {
                    System.out.println("Customer " + this.customerID + " ordered Cappuccino from Barista " + (nextBaristaNumber + 1) + " and payed RM9");

                    baristas[nextBaristaNumber].serveDrink(this, "Cappuccino");
                    
                    try {
                        baristas[nextBaristaNumber].wait();
                        consumingDrink(this.customerID, "Juice");
                        
                    } catch (InterruptedException e) {
                    }
                } catch (InterruptedException ex) {
                }
            }
        }
        
    }
    
    @Override
    public void run() {
        try {
            if (GoGoCoffeeCafe.seats.tryAcquire()) {
                System.out.println("Customer " + customerID + " has found a seat.");
                
                this.customerOrdering();
                
                GoGoCoffeeCafe.seats.release();
            } else {
                if (GoGoCoffeeCafe.queue.tryAcquire()){
                    System.out.println("Customer " + customerID + " is waiting in queue.");
                GoGoCoffeeCafe.queue.acquire();
                while (true) {
                    if (GoGoCoffeeCafe.seats.tryAcquire()) {
                        GoGoCoffeeCafe.queue.release();
                        System.out.println("Customer " + customerID + " has found a seat from queue.");
                        
                        this.customerOrdering();
                        
                        GoGoCoffeeCafe.seats.release();
                        break;
                    } else {
                        Thread.sleep(500);
                    }
                }
                } else {
                    System.out.println("Customer " + this.customerID + " left. Queue too long.");
                    
                }
                
            }
        } catch (InterruptedException e) {
            System.err.println("Interrupted while waiting for a seat.");
        }
        
        this.stopCustomerThread();
    }
}



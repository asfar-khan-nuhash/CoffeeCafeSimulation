package gogocoffeecafe;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class GoGoCoffeeCafe {
    
    public static Barista barista1 = new Barista("1");
    public static Barista barista2 = new Barista("2");
    public static Barista barista3 = new Barista("3");
    
    public static Machines expresso = new Machines("Expresso");
    public static Machines milkFrothing = new Machines("Milk Frothing");
    public static Machines juiceTap = new Machines("Juice Tap");
    
    public static programOff programOff = new programOff();
    
    public static Semaphore seats = new Semaphore(10);
    public static Semaphore queue = new Semaphore(5);

    public static void main(String[] args) throws InterruptedException {
        
        System.out.println("\n\nGOGOCOFFEE CAFE IS OPEN! \n\n");
        System.out.println("Baristas are on standby.\n\n");
        
        Random random = new Random();
        Customer[] customers = new Customer[20];
        
        for (int i = 0; i < customers.length; i++) {
            customers[i] = new Customer(i + 1, seats);
            customers[i].setLastBaristaNumber(i % 3);

            // Random delay of 0, 1, or 2 seconds before starting the customer thread
            int delayInSeconds = random.nextInt(3); // Generates 0, 1, or 2
            try {
                Thread.sleep(delayInSeconds * 1000);
            } catch (InterruptedException e) {
            }
            
            System.out.println("Customer " + customers[i].getCustomerID() + " has arrived at the GoGoCoffeeCafe.");
            customers[i].start();
            
            
        }
        programOff.start();
    
    }
}

package gogocoffeecafe;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

class Barista extends Thread {
    
    private String baristaName;
    int totalSale;
    int totalJuiceSold, totalCappuccinoSold, totalExpressoSold = 0;
    private volatile boolean b_running = true;

    public String getBaristaName() {
        return baristaName;
    }
    public void setBaristaName(String baristaName) {
        this.baristaName = baristaName;
    }
    public Barista(String name){
        this.baristaName = name;
    }
    
    public void baristaGoesHome(){
        System.out.println("\nBarista " + this.baristaName + ": Total Number of Cappuccino Sold:\t\t" + totalCappuccinoSold);
        System.out.println("Barista " + this.baristaName + ": Total Number of Expresso Sold:\t\t" + totalExpressoSold);
        System.out.println("Barista " + this.baristaName + ": Total Number of Juice Sold:\t\t\t" + totalJuiceSold);
        
        System.out.println("\nBarista " + this.baristaName + ": Total Revenue:\tRM" + totalSale);
        
        b_running = false;
    }
    
    public synchronized void serveDrink(Customer customer, String drink) throws InterruptedException {
        System.out.println("Barista " + this.baristaName + " preparing " + drink + " for customer " + customer.getCustomerID());
        if ("Juice".equals(drink)){
            totalJuiceSold++;
            totalSale = totalSale + 7;
            GoGoCoffeeCafe.juiceTap.useMachine(this);
        } else if ("Expresso".equals(drink)) {
            totalExpressoSold++;
            totalSale = totalSale + 6;
            GoGoCoffeeCafe.expresso.useMachine(this);
        } else {
            totalCappuccinoSold++;
            totalSale = totalSale + 9;
            GoGoCoffeeCafe.expresso.useMachine(this);
            Thread.sleep(500);
            GoGoCoffeeCafe.milkFrothing.useMachine(this);
        }
        System.out.println("Barista " + this.baristaName + " served " + drink + " to customer " + customer.getCustomerID());
        notify(); //This notifies waiting customer threads
    }
}


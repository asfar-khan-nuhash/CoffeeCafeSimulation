package gogocoffeecafe;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Machines extends Thread{
    
    String machine_name; //Expresso Machine, Milk Frothing Machine, Juice Tap.
    private Lock lock = new ReentrantLock();
    
    public Machines(String name){
        this.machine_name = name;
    }
    
    public void useMachine(Barista barista){
        while (true){
            if (lock.tryLock()) {
                try {
                    System.out.println("Barista " + barista.getBaristaName() + " is using " + this.machine_name + " machine: 25% Done!");
                    Thread.sleep(500);
                    System.out.println("Barista " + barista.getBaristaName() + " is using " + this.machine_name + " machine: 50% Done!");
                    Thread.sleep(500);
                    System.out.println("Barista " + barista.getBaristaName() + " is using " + this.machine_name + " machine: 75% Done!");
                    Thread.sleep(500);
                } finally {
                    System.out.println("Barista " + barista.getBaristaName() + " is done using " + this.machine_name + " machine.");
                    
                    lock.unlock();
                    break;
                }
            } else {
                System.out.println("Barista " + barista.getBaristaName() + " attempted to use " + this.machine_name + " machine but it's in use.");
                try {
                Thread.sleep(1000); // Wait for the thread to start again after some time
            } catch (InterruptedException e) {
            }
            }
        }
    }

}

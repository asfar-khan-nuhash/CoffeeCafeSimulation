
package gogocoffeecafe;

public class programOff extends Thread{
    private volatile boolean program_running = true;
    public int customer_count = 0;
    
    public void closeProgram() throws InterruptedException{
        GoGoCoffeeCafe.barista1.baristaGoesHome();
        Thread.sleep(500);
        GoGoCoffeeCafe.barista2.baristaGoesHome();
        Thread.sleep(1000);
        GoGoCoffeeCafe.barista3.baristaGoesHome();

        program_running = false;
    }
    
    public void run() {
        try {
            Thread.sleep(37000);
            closeProgram();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

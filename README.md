# GoGo Coffee Cafe

A multithreaded cafe simulation written in Java. Twenty customers compete for
ten seats, three baristas, and three shared machines, coordinated with
semaphores, locks, and monitor-based signalling.

## About the project

Each customer is represented by a thread that arrives at a random moment, tries to 
find a seat, orders a drink, and leaves once finished. Because customers arrive 
fasterthan they can be served, the interesting behaviour is what happens under
contention: queueing, machine conflicts, and customers giving up and walking
out.

## Concurrency techniques

- **Semaphores** — a 10-permit semaphore controls seating and a 5-permit
  semaphore controls the queue. A customer who can acquire neither leaves.
- **ReentrantLock** — each machine uses `tryLock`, so a barista who finds a
  machine occupied reports it and retries rather than blocking indefinitely.
- **synchronized with wait and notify** — a customer waits on the barista
  object while their drink is prepared and is signalled when it's ready.
- **volatile flags** — used for the shutdown state shared across threads.

## How the simulation runs

1. Customers arrive at random intervals and try to acquire a seat.
2. Without a seat, a customer takes a queue slot and polls until one frees up.
   If the queue is also full, they leave.
3. Seated customers order from the next barista in rotation. Cappuccino is the
   most common order and the most demanding, since it needs the espresso
   machine followed by the milk frother.
4. Drinking is simulated in stages so the customer holds their seat for a
   realistic period.
5. After 37 seconds a shutdown thread closes the cafe, and each barista prints
   the drinks they sold and the revenue they took.

## Built with

Java SE and the `java.util.concurrent` package. The project was developed in
NetBeans, and the `nbproject` files are included so it opens directly there.

## Instructions to run

**In NetBeans:** open the project folder and run it. The main class is
`gogocoffeecafe.GoGoCoffeeCafe`.

**From the command line,** with a JDK installed:

```bash
javac -d build/classes src/gogocoffeecafe/*.java
java -cp build/classes gogocoffeecafe.GoGoCoffeeCafe
```

The simulation prints to standard output and ends on its own after about
40 seconds.

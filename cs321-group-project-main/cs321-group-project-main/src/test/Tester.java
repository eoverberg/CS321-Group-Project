package test;

/**
 * Generic test clas so I can run any test class "automatically" by making it inherit this class and having a run function.
 */
public abstract class Tester {
    public Tester() throws Exception {
        System.out.println(getClass().getName());
        run();
    }

    public abstract void run() throws Exception;
}

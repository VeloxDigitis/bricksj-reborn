package me.veloxdigitis.bricksj.logger;

public class StandardOutputLogs implements LogListener {

    @Override
    public void log(String line) {
        System.out.println(line);
    }

    @Override
    public void close() {
        System.out.println("Finished logging");
    }

}

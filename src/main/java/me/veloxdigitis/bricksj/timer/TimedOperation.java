package me.veloxdigitis.bricksj.timer;

public class TimedOperation<T> {

    private static final long MAX_TIME = 2000;

    private final long currentTime;
    private final long maxTime;

    private int time = 0;
    private T data;

    public TimedOperation(long maxTime) {
        this.maxTime = maxTime;
        this.currentTime = System.currentTimeMillis();
    }

    public TimedOperation() {
        this(MAX_TIME);
    }

    public TimedOperation<T> setDataAndStop(T data) {
        this.data = data;
        this.time = (int) (System.currentTimeMillis() - currentTime);
        return this;
    }

    public int getTime() {
        return time;
    }

    public T getData() {
        return data;
    }

}

package me.veloxdigitis.bricksj.timer;

public class TimedOperation<T> {

    private final long currentTime;

    private int time = 0;
    private T data;

    public TimedOperation() {
        this.currentTime = System.currentTimeMillis();
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

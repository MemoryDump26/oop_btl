package timer;

import entity.Entity;
import input.Command;

public class Timer {

    private long startTime;
    private long pauseTime;
    private long duration;
    private boolean oneShot;
    private Command c;
    private Entity e;

    private boolean running;
    private boolean pausing;

    public Timer(long duration, boolean oneShot) {
        startTime = 0;
        pauseTime = 0;
        running = false;
        pausing = false;
    }

    public void start() {
        startTime = System.nanoTime();
        pauseTime = 0;
        running = true;
        pausing = false;
    }

    public void stop() {
        startTime = 0;
        pauseTime = 0;
        running = false;
        pausing = false;
    }

    public void pause() {
        pauseTime = System.nanoTime() - startTime;
        startTime = 0;
        running = true;
        pausing = true;
    }

    public void resume() {
        startTime = System.nanoTime() - pauseTime;
        pauseTime = 0;
        running = true;
        pausing = false;
    }

    public boolean isRunning() {return running;}
    public boolean isPausing() {return pausing;}

    public long getElapsedTimeInSecond() {
        if (!running) return 0;
        if (!pausing) return (System.nanoTime() - startTime) / 1000000000;
        else return pauseTime / 1000000000;
    }
}

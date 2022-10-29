package timer;

public class Timer {

    private long startTime = 0;
    private long pauseTime = 0;
    private double duration;

    private boolean running = false;
    private boolean pausing = false;

    public Timer(double duration) {
        this.duration = duration;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
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
    public boolean isFinished() {
        if (!running) return false;
        if (getElapsedTimeInSecond() >= duration) return true;
        else return false;
    }

    public double getElapsedTimeInSecond() {
        if (!running) return 0;
        if (!pausing) return (double)(System.nanoTime() - startTime) / 1000000000;
        else return (double)pauseTime / 1000000000;
    }
}

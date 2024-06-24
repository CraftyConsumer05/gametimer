import java.util.Timer;
import java.util.TimerTask;

public class GameClock {
    private int hours;
    private int minutes;
    private Timer timer;

    public GameClock() {
        this.hours = 0;
        this.minutes = 0;
        this.timer = new Timer();
    }

    public void start() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                advanceTime();
                displayTime();
            }
        };
        timer.scheduleAtFixedRate(task, 0, 10000); // Schedule task to run every 10 seconds
    }

    private void advanceTime() {
        minutes += 10;
        if (minutes >= 60) {
            minutes = 0;
            hours++;
            if (hours >= 24) {
                hours = 0;
            }
        }
    }

    private void displayTime() {
        System.out.printf("Current Game Time: %02d:%02d%n", hours, minutes);
    }

    public void stop() {
        timer.cancel();
    }

    public static void main(String[] args) {
        GameClock gameClock = new GameClock();
        gameClock.start();
    }
}

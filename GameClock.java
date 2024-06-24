import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class GameClock {
    private int hours;
    private int minutes;
    private Timer timer;
    private long startTime;
    private JFrame frame;
    private JLabel timeLabel;
    private JButton startButton;

    public GameClock() {
        this.hours = 0;
        this.minutes = 0;
        this.timer = new Timer();
        createUI();
    }

    private void createUI() {
        frame = new JFrame("Game Clock");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 100);
        frame.setLayout(new BorderLayout());

        timeLabel = new JLabel(String.format("%02d:%02d", hours, minutes));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setFont(new Font("Serif", Font.BOLD, 32));
        frame.add(timeLabel, BorderLayout.CENTER);

        startButton = new JButton("Start");
        startButton.addActionListener(e -> start());
        frame.add(startButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void start() {
        resetTime();
        startTime = System.currentTimeMillis();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                advanceTime();
                updateUITime();
                printElapsedRealLifeTime();
            }
        };
        timer.scheduleAtFixedRate(task, 0, 10000); // Schedule task to run every 10 seconds
        startButton.setEnabled(false);
    }

    private void resetTime() {
        this.hours = 0;
        this.minutes = 0;
        updateUITime();
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

    private void updateUITime() {
        SwingUtilities.invokeLater(() -> timeLabel.setText(String.format("%02d:%02d", hours, minutes)));
    }

    private void printElapsedRealLifeTime() {
        long elapsedMillis = System.currentTimeMillis() - startTime;
        long elapsedSeconds = elapsedMillis / 1000;
        long elapsedMinutes = elapsedSeconds / 60;
        long elapsedHours = elapsedMinutes / 60;
        elapsedMinutes = elapsedMinutes % 60;
        elapsedSeconds = elapsedSeconds % 60;

        System.out.printf("Elapsed Real Life Time: %02d:%02d:%02d%n", elapsedHours, elapsedMinutes, elapsedSeconds);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameClock::new);
    }
}

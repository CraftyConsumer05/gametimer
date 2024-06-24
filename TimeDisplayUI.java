import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TimeDisplayUI extends Application {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private Label elapsedTimeLabel;
    private Label simulatedTimeLabel;

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.getChildren().addAll(
                elapsedTimeLabel = new Label("Elapsed time: 00:00:00"),
                simulatedTimeLabel = new Label("Simulated time: 00:00:00")
        );

        Scene scene = new Scene(root, 250, 100);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Time Display");
        primaryStage.show();

        Instant startTime = Instant.now();
        final LocalTime[] simulatedTime = {LocalTime.of(0, 0, 0)}; // start at 00:00:00

        ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(() -> {
            Instant currentTime = Instant.now();
            Duration elapsedDuration = Duration.between(startTime, currentTime);

            Platform.runLater(() -> {
                elapsedTimeLabel.setText("Elapsed time: " + formatDuration(elapsedDuration));
                simulatedTime[0] = simulatedTime[0].plusMinutes(5); // advance 5 minutes every 10 seconds
                simulatedTimeLabel.setText("Simulated time: " + simulatedTime[0].format(TIME_FORMATTER));

                if (simulatedTime[0].isAfter(LocalTime.of(23, 59, 59))) {
                    simulatedTime[0] = LocalTime.of(0, 0, 0); // reset to 00:00:00 if past 23:59:59
                }
            });
        }, 0, 10, TimeUnit.SECONDS);
    }

    private String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long minutes = seconds / 60;
        long hours = minutes / 60;
        minutes %= 60;
        seconds %= 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
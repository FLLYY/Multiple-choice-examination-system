package dao.impl;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.time.Duration;


public class DigitalClock implements Runnable {
    private volatile boolean isRunning = true;
    private Label timerLabel;
    private Duration duration;
    private ExamMachine examMachine; // 添加 ExamMachine 成员变量

    public DigitalClock(Label timerLabel, ExamMachine examMachine) {
        this.timerLabel = timerLabel;
        this.duration = Duration.ofMinutes(1); // 设置60分钟的倒计时
        this.examMachine = examMachine; // 设置 ExamMachine 实例的引用
    }

    public static Label startDigitalClock(ExamMachine examMachine) {
        Label clockLabel = new Label();
        clockLabel.setFont(new Font(24));
        clockLabel.setStyle("-fx-font-weight: bold");
        clockLabel.setTextFill(Color.RED);

        DigitalClock digitalClock = new DigitalClock(clockLabel,examMachine);
        Thread clockThread = new Thread(digitalClock);
        clockThread.setDaemon(true);
        clockThread.start();
        return clockLabel;
    }
    @Override
    public void run() {
        while (isRunning && !duration.isZero()) {
            Platform.runLater(() -> {
                // 更新UI显示
                timerLabel.setText(formatDuration(duration));
            });

            try {
                Thread.sleep(1000); // 每秒更新一次显示
                duration = duration.minusSeconds(1); // 每次减少一秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // 倒计时结束后的处理
        Platform.runLater(() -> {
            timerLabel.setText("Countdown Finished");
            examMachine.handleSubmitAnswers(examMachine.paper);
            // 可以在此处添加其他结束后的逻辑处理
        });
    }


    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();
        long seconds = duration.minusHours(hours).minusMinutes(minutes).getSeconds();
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
package com.example.demo.main;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import java.io.File;

public class MediaDemo extends Application {
    private static final String MEDIA_DIR = "D:/抖音视频/";

    @Override
    public void start(Stage primaryStage) {
        MediaView mediaView = new MediaView();
        //mediaView.setSmooth​(true);

        Button playButton = new Button(">");
        playButton.setOnAction(e -> {
            MediaPlayer mPlayer = mediaView.getMediaPlayer();
            if (playButton.getText().equals(">")) {
                mPlayer.play();
                playButton.setText("||");
            } else {
                mPlayer.pause();
                playButton.setText(">");
            }
        });


        Button rewindButton = new Button("<<");
        rewindButton.setOnAction(e -> mediaView.getMediaPlayer().seek(Duration.ZERO));

        /*Slider slVolume = new Slider();
        slVolume.setPrefWidth(150);
        slVolume.setMaxWidth(Region.USE_PREF_SIZE);
        slVolume.setMinWidth(30);
        slVolume.setValue(50);
        mediaPlayer.volumeProperty().bind(
            slVolume.valueProperty().divide(100));
        */
        // 播放进度条
        Slider slTime = new Slider();
        HBox.setHgrow(slTime,Priority.ALWAYS);
        slTime.setMinWidth(50);
        slTime.setMaxWidth(Double.MAX_VALUE);
        //slTime.setShowTickLabels(true);
        slTime.valueProperty().addListener(ov -> {
            Duration total = mediaView.getMediaPlayer().getTotalDuration();
            // 当前进度 total * progress / 100.0
            Duration s1 = total.multiply(slTime.getValue()).divide(100.0);
            mediaView.getMediaPlayer().seek(s1);
        });

        HBox progressHBox = new HBox(20);
        progressHBox.setAlignment(Pos.CENTER);
        progressHBox.setMinHeight(30);
        progressHBox.getChildren().add(slTime);

        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(playButton, rewindButton,
                new Label("Progress"), slTime);

        BorderPane pane = new BorderPane();
        pane.setCenter(mediaView);
        pane.setBottom(hBox);

        mp4List(pane, mediaView);

        Scene scene = new Scene(pane, 670, 500);
        primaryStage.setTitle("MeidaDemo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * 媒体播放列表
     */
    public void mp4List(BorderPane pane, MediaView mediaView) {
        File dir = new File(MEDIA_DIR);
        String[] mp4s = dir.list((f, n) -> n.matches(".*\56mp4"));
        VBox vBox = new VBox();
        ToggleGroup group = new ToggleGroup();
        for(int i = 0; i < mp4s.length; i++) {
            RadioButton radioButt = new RadioButton(mp4s[i]);
            radioButt.setToggleGroup(group);
            radioButt.setOnAction(e -> {
                MediaPlayer mp = mediaView.getMediaPlayer();
                if (mp != null) {
                    mp.dispose();
                }
            MediaPlayer mPlayer = new MediaPlayer(new Media("file:/" + MEDIA_DIR + radioButt.getText()));
                // MediaPlayer mPlayer = new MediaPlayer(new Media( MEDIA_DIR + radioButt.getText()));
                mPlayer.play();
                mediaView.setMediaPlayer(mPlayer);
                System.gc();
            });
            vBox.getChildren().add(radioButt);
        }
        pane.setRight(vBox);
    }
}
package org.zoho;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;

public class Main extends Application {
    Label timeLabel = new Label("00:00");
    Label durationLabel = new Label("00:00");
    Label divider = new Label("/");
    Label volumeLabel = new Label("Volume:");
    Slider progessbar = new Slider();
    Slider volumeBar = new Slider();

    private MusicPlayer musicPlayer = new MusicPlayer(timeLabel,durationLabel,progessbar,volumeBar);

    public void start(Stage stage){
        Text title = new Text("Javafx Mp3 player");
        title.setFont(Font.font("Arial",20));

        Button prevBtn = new Button("Prev");
        Button nextBtn = new Button("Next");
        Button pauseBtn = new Button("Pause");
        Button playBtn = new Button("play");

        prevBtn.setOnAction(event -> musicPlayer.prev());
        nextBtn.setOnAction(event-> musicPlayer.next());
        pauseBtn.setOnAction(event-> musicPlayer.pause());
        playBtn.setOnAction(event-> musicPlayer.play());

        Button loadBtn = new Button("Load Song");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose An Audio File");

        fileChooser.getExtensionFilters().addAll(
              new FileChooser.ExtensionFilter("Audio FIles","*.mp3","*.wav")
        );

        loadBtn.setOnAction(event->{
            File selectedFile = fileChooser.showOpenDialog(stage);

            if(selectedFile!=null){
                musicPlayer.loadExternalFIle(selectedFile);
                title.setText(selectedFile.getName());
            }
        });


        HBox buttonLayout = new HBox(10);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().addAll(prevBtn,playBtn,nextBtn,pauseBtn,loadBtn);


        progessbar.setPrefWidth(200);
        progessbar.setShowTickMarks(false);
        progessbar.setShowTickLabels(false);

        HBox timeLayout = new HBox(10);
        timeLayout.setAlignment(Pos.BOTTOM_CENTER);
        timeLayout.getChildren().addAll(timeLabel,divider,durationLabel,progessbar);

        volumeBar.setMax(1);
        volumeBar.setMin(0);
        volumeBar.setValue(0.5);
        volumeBar.setPrefWidth(100);

        HBox volumeLayout = new HBox(10);
        volumeLayout.setAlignment(Pos.BASELINE_RIGHT);
        volumeLayout.getChildren().addAll(volumeBar,volumeLabel);

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(title,buttonLayout,timeLayout,volumeLayout);

        Scene scene = new Scene(root, 350, 150);
        stage.setTitle("MP3 Player");
        stage.setScene(scene);

        stage.setOnCloseRequest(event-> musicPlayer.quit());

        stage.show();

    }


}
package org.zoho;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;

public class Main extends Application {
    private MusicPlayer musicPlayer = new MusicPlayer();

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

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(title,buttonLayout);

        Scene scene = new Scene(root, 350, 150);
        stage.setTitle("MP3 Player");
        stage.setScene(scene);

        stage.setOnCloseRequest(event-> musicPlayer.quit());

        stage.show();

    }


}
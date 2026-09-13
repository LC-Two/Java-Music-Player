package org.zoho;

import javafx.application.Application;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main extends Application {
    Label timeLabel = new Label("00:00");
    Label durationLabel = new Label("00:00");
    Label divider = new Label("/");
    Label volumeLabel = new Label("Volume:");
    Label titleLabel = new Label("No Song Selected");
    Slider progessbar = new Slider();
    Slider volumeBar = new Slider();
    ImageView albumCover = new ImageView();
    ListView<String> playListView = new ListView<>();
    Set<String> loadedFilePaths = new HashSet<>();


    public void start(Stage stage){
        Text title = new Text("Javafx Mp3 player");
        title.setFont(Font.font("Arial",20));

        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD,18));
        ImageView prevIcon = new ImageView(new Image(getClass().getResourceAsStream("/prev.png")));
        ImageView nextIcon = new ImageView(new Image(getClass().getResourceAsStream("/nextIcon.png")));
        ImageView playIcon = new ImageView(new Image(getClass().getResourceAsStream("/playIcon.png")));
        ImageView pauseIcon = new ImageView(new Image(getClass().getResourceAsStream("/pauseIcon.png")));
        ImageView uploadIcon = new ImageView(new Image(getClass().getResourceAsStream("/uploadFile.png")));

        Button prevBtn = new Button();
        prevBtn.setGraphic(prevIcon);

        Button nextBtn = new Button();
        nextBtn.setGraphic(nextIcon);

        Button playPauseBtn = new Button();
        playPauseBtn.setGraphic(playIcon);

        MusicPlayer musicPlayer = new MusicPlayer(timeLabel,durationLabel,progessbar,volumeBar,albumCover,titleLabel,playListView);



//        Button pauseBtn = new Button();
//        pauseBtn.setGraphic(pauseIcon);
//
//        Button playBtn = new Button();
//        playBtn.setGraphic(playIcon);



        prevBtn.setOnAction(event -> musicPlayer.prev());
        nextBtn.setOnAction(event-> musicPlayer.next());

        playPauseBtn.setOnAction(event->{
            boolean isPlaying = musicPlayer.togglePlayPause();

            if(isPlaying){
                playPauseBtn.setGraphic(pauseIcon);
            }
            else{
                playPauseBtn.setGraphic(playIcon);
            }

        });
//        pauseBtn.setOnAction(event-> musicPlayer.pause());
//        playBtn.setOnAction(event-> musicPlayer.play());

        playListView.setPrefWidth(250);
        playListView.setOnMouseClicked(event->{
            if(event.getClickCount()==2){
                int selectedIndex = playListView.getSelectionModel().getSelectedIndex();
                musicPlayer.playSongAtIndex(selectedIndex);
            }
        });


        Button loadBtn = new Button();
        loadBtn.setGraphic(uploadIcon);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose An Audio File");

        fileChooser.getExtensionFilters().addAll(
              new FileChooser.ExtensionFilter("Audio Files","*.mp3","*.wav")
        );

        loadBtn.setOnAction(event->{
           List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);

            if(selectedFiles!=null && !selectedFiles.isEmpty()){

                List<File> uniqueFiles = new ArrayList<>();
                for(File file:selectedFiles){
                    if(loadedFilePaths.add(file.getAbsolutePath())) {
                        playListView.getItems().add(file.getName());
                        uniqueFiles.add(file);
                    }
                }
                if(!uniqueFiles.isEmpty()) {
                    musicPlayer.addFilesToPlaylist(uniqueFiles);
                }

            }
        });


        HBox buttonLayout = new HBox(10);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().addAll(prevBtn,playPauseBtn,nextBtn,loadBtn);


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
        volumeLayout.setAlignment(Pos.BOTTOM_CENTER);
        volumeLayout.getChildren().addAll(volumeBar,volumeLabel);

        albumCover.setFitHeight(200);
        albumCover.setFitWidth(200);
        albumCover.setPreserveRatio(true);

        VBox playerControls = new VBox(20);
        playerControls.setAlignment(Pos.CENTER);
        playerControls.setPrefWidth(400);
        playerControls.getChildren().addAll(title,albumCover,titleLabel,buttonLayout,timeLayout,volumeLayout);

        VBox playListContainer = new VBox(5);
        playListContainer.setAlignment(Pos.CENTER);
        Label queueLabel = new Label("Up Next");
        playListContainer.setPrefWidth(400);
        playListContainer.setPrefHeight(200);
        playListContainer.getChildren().addAll(queueLabel,playListView);

        HBox root = new HBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(playerControls,playListContainer);


        Scene scene = new Scene(root, 900, 500);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        scene.addEventFilter(KeyEvent.KEY_PRESSED, event->{
            if(event.getCode()== KeyCode.SPACE){
                playPauseBtn.fire();
                event.consume();
            }
            else if(event.getCode()==KeyCode.RIGHT){
                musicPlayer.next();
                event.consume();
            }
            else if(event.getCode()==KeyCode.LEFT){
                musicPlayer.prev();
                event.consume();
            }
        });

        stage.setTitle("MP3 Player");
        stage.setScene(scene);

        stage.setOnCloseRequest(event-> musicPlayer.quit());

        Image appIcon =  new Image(getClass().getResourceAsStream("/AppIcon.jpg"));
        stage.getIcons().add(appIcon);

        stage.show();

    }


}
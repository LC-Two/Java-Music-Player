package org.zoho;

import javafx.application.Application;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
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
import java.util.List;

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

    private MusicPlayer musicPlayer = new MusicPlayer(timeLabel,durationLabel,progessbar,volumeBar,albumCover,titleLabel);

    public void start(Stage stage){
        Text title = new Text("Javafx Mp3 player");
        title.setFont(Font.font("Arial",20));

        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD,18));

        Button prevBtn = new Button("Prev");
        Button nextBtn = new Button("Next");
        Button pauseBtn = new Button("Pause");
        Button playBtn = new Button("play");

        prevBtn.setOnAction(event -> musicPlayer.prev());
        nextBtn.setOnAction(event-> musicPlayer.next());
        pauseBtn.setOnAction(event-> musicPlayer.pause());
        playBtn.setOnAction(event-> musicPlayer.play());

        playListView.setPrefWidth(250);
        playListView.setOnMouseClicked(event->{
            if(event.getClickCount()==2){
                int selectedIndex = playListView.getSelectionModel().getSelectedIndex();
                musicPlayer.playSongAtIndex(selectedIndex);
            }
        });


        Button loadBtn = new Button("Load Song");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose An Audio File");

        fileChooser.getExtensionFilters().addAll(
              new FileChooser.ExtensionFilter("Audio Files","*.mp3","*.wav")
        );

        loadBtn.setOnAction(event->{
           List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);

            if(selectedFiles!=null && !selectedFiles.isEmpty()){
                for(File file:selectedFiles){
                    playListView.getItems().add(file.getName());
                }
                musicPlayer.addFilesToPlaylist(selectedFiles);

            }
        });


        HBox buttonLayout = new HBox(10);
        buttonLayout.setAlignment(Pos.CENTER);
        buttonLayout.getChildren().addAll(prevBtn,playBtn,pauseBtn,nextBtn,loadBtn);


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
        playListContainer.getChildren().addAll(queueLabel,playListView);

        HBox root = new HBox(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(playerControls,playListContainer);


        Scene scene = new Scene(root, 700, 500);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        stage.setTitle("MP3 Player");
        stage.setScene(scene);

        stage.setOnCloseRequest(event-> musicPlayer.quit());

        stage.show();

    }


}
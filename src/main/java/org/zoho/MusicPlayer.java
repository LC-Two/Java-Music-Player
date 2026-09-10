package org.zoho;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import javafx.util.Duration;

public class MusicPlayer {
    private String[] songs;
    private MediaPlayer mediaPlayer;
    private int currentSongIndex =0;
    private Label timeLabel;
    private Label durationLabel;
    private Slider progressBar;

    private String formatTime(Duration duration){
        int totalSeconds = (int)Math.floor(duration.toSeconds());
        int minutes = totalSeconds/60;
        int seconds = totalSeconds%60;

        return String.format("%02d:%02d",minutes,seconds);
    }

    public MusicPlayer(Label timeLabel, Label durationLabel, Slider progressBar){
        this.timeLabel=timeLabel;
        this.durationLabel=durationLabel;
        this.progressBar= progressBar;

        songs = new String[]{
            "src/main/resources/Gintama_ED_25.mp3",
            "src/main/resources/Gintama_ED_30.mp3",
            "src/main/resources/Raga of Revenge.mp3",
            "src/main/resources/The Climb.mp3"
        };
        loadMusic(currentSongIndex);
    }

    private void loadMusic(int index){
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }

        File file = new File(songs[index]);

        if(!file.exists()){
            System.out.println("Cannot find File "+file.getPath());
            return;
        }

        String uriString = file.toURI().toString();
        Media media = new Media(uriString);
        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setOnEndOfMedia(this::next);

        mediaPlayer.setOnReady(()->{
            Duration totalDuration = media.getDuration();
            durationLabel.setText(formatTime(totalDuration));
            progressBar.setMax(totalDuration.toSeconds());
        });

        mediaPlayer.currentTimeProperty().addListener((observable, oldTime,newTime)->{
            timeLabel.setText(formatTime(newTime));
            if(!progressBar.isPressed()){
                progressBar.setValue(newTime.toSeconds());
            }
        });

       progressBar.valueProperty().addListener((observable,oldTime,newTime)->{
           if(progressBar.isPressed()){
               mediaPlayer.seek(Duration.seconds(newTime.doubleValue()));
           }
       });

    }

    public void loadExternalFIle(File file){
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }

        String uriString = file.toURI().toString();
        Media media = new Media(uriString);
        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setOnEndOfMedia(this::next);

        mediaPlayer.setOnReady(()->{
            Duration totalDuration = media.getDuration();
            durationLabel.setText(formatTime(totalDuration));
            progressBar.setMax(totalDuration.toSeconds());
        });

        mediaPlayer.currentTimeProperty().addListener((observable, oldTime,newTime)->{
            timeLabel.setText(formatTime(newTime));

            if(!progressBar.isPressed()){
                progressBar.setValue(newTime.toSeconds());
            }
        });

        progressBar.valueProperty().addListener((observable,oldTime,newTime)->{
            if(progressBar.isPressed()){
                mediaPlayer.seek(Duration.seconds(newTime.doubleValue()));
            }
        });

        play();
    }


    public void play(){
        if(mediaPlayer != null) mediaPlayer.play();
    }

    public void pause(){
        if(mediaPlayer !=null ) mediaPlayer.pause();
    }

    public void stop(){
        if(mediaPlayer != null) mediaPlayer.stop();
    }

    public void quit(){
        if(mediaPlayer != null) mediaPlayer.dispose();
    }

    public void next(){
        currentSongIndex++;
        if(currentSongIndex >=songs.length) currentSongIndex =0;

        loadMusic(currentSongIndex);
        play();
    }

    public void prev(){
        currentSongIndex--;
        if(currentSongIndex <0) currentSongIndex =songs.length-1;

        loadMusic(currentSongIndex);
        play();
    }

}

package org.zoho;

import javafx.beans.Observable;
import javafx.collections.ObservableMap;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import javafx.util.Duration;

public class MusicPlayer {
//    private String[] songs;
    private MediaPlayer mediaPlayer;
//    private int currentSongIndex =0;
    private Label timeLabel;
    private Label durationLabel;
    private Slider progressBar;
    private Slider volumeSlider;
    private ImageView albumCover;
    private Label titleLabel;
    private ListView playListView;


    private List<File> playList = new ArrayList<>();
    private int currentIndex=0;

    private String formatTime(Duration duration){
        int totalSeconds = (int)Math.floor(duration.toSeconds());
        int minutes = totalSeconds/60;
        int seconds = totalSeconds%60;

        return String.format("%02d:%02d",minutes,seconds);
    }

    public MusicPlayer(Label timeLabel, Label durationLabel, Slider progressBar, Slider volumeSlider, ImageView albumCover, Label titleLabel, ListView playListView){
        this.timeLabel=timeLabel;
        this.durationLabel=durationLabel;
        this.progressBar= progressBar;
        this.volumeSlider=volumeSlider;
        this.albumCover=albumCover;
        this.titleLabel=titleLabel;
        this.playListView = playListView;

//        songs = new String[]{
//                "src/main/resources/CHiCO with HoneyWorks - Hikari Shoumeiron.mp3",
//                "src/main/resources/THREE LIGHTS DOWN KINGS - Glorious Days.mp3",
//                "src/main/resources/Anirudh Ravichander - Raga of Revenge (From  DC ).mp3",
//                "src/main/resources/Miley Cyrus - The Climb.mp3"
//        };

        volumeSlider.valueProperty().addListener((observable,oldValue,newValue)->{
            if(mediaPlayer!=null){
                mediaPlayer.setVolume(newValue.doubleValue());
            }
        });

        progressBar.valueProperty().addListener((observable,oldTime,newTime)->{
            if(progressBar.isPressed()){
                mediaPlayer.seek(Duration.seconds(newTime.doubleValue()));
            }
        });

//        loadMusic(currentSongIndex);
    }

//    private void loadMusic(int index){
//        if(mediaPlayer != null){
//            mediaPlayer.stop();
//            mediaPlayer.dispose();
//        }
//
//        File file = new File(songs[index]);
//
//        if(!file.exists()){
//            System.out.println("Cannot find File "+file.getPath());
//            return;
//        }
//
//        String uriString = file.toURI().toString();
//        Media media = new Media(uriString);
//        mediaPlayer = new MediaPlayer(media);
//
//        mediaPlayer.setVolume(volumeSlider.getValue());
//
//        mediaPlayer.setOnEndOfMedia(this::next);
//
//        mediaPlayer.setOnReady(()->{
//            Duration totalDuration = media.getDuration();
//            durationLabel.setText(formatTime(totalDuration));
//            progressBar.setMax(totalDuration.toSeconds());
//
//            ObservableMap<String,Object> metadata = media.getMetadata();
//            if(metadata.containsKey("image")){
//                Image albumArt = (Image) metadata.get("image");
//                albumCover.setImage(albumArt);
//            }
//            else{
//                albumCover.setImage(null);
//            }
//
//            String songTitle =(String)metadata.get("title");
//            String artistName = (String) metadata.get("artist");
//
//            if(songTitle!=null && artistName!=null){
//                titleLabel.setText(songTitle+" - "+artistName);
//            }
//            else if (songTitle!=null ) {
//                titleLabel.setText(songTitle);
//            }
//            else{
//                titleLabel.setText(file.getName());
//            }
//        });
//
//        mediaPlayer.currentTimeProperty().addListener((observable, oldTime,newTime)->{
//            timeLabel.setText(formatTime(newTime));
//            if(!progressBar.isPressed()){
//                progressBar.setValue(newTime.toSeconds());
//            }
//        });
//
//       progressBar.valueProperty().addListener((observable,oldTime,newTime)->{
//           if(progressBar.isPressed()){
//               mediaPlayer.seek(Duration.seconds(newTime.doubleValue()));
//           }
//       });
//
//
//
//    }

//    public void loadExternalFIle(File file){
//        if(mediaPlayer!=null){
//            mediaPlayer.stop();
//            mediaPlayer.dispose();
//        }
//
//        String uriString = file.toURI().toString();
//        Media media = new Media(uriString);
//        mediaPlayer = new MediaPlayer(media);
//
//        mediaPlayer.setVolume(volumeSlider.getValue());
//
//        mediaPlayer.setOnEndOfMedia(this::next);
//
//        mediaPlayer.setOnReady(()->{
//            Duration totalDuration = media.getDuration();
//            durationLabel.setText(formatTime(totalDuration));
//            progressBar.setMax(totalDuration.toSeconds());
//
//            ObservableMap<String,Object> metadata = media.getMetadata();
//            if(metadata.containsKey("image")){
//                Image albumArt = (Image) metadata.get("image");
//                albumCover.setImage(albumArt);
//            }
//            else{
//                albumCover.setImage(null);
//            }
//
//            String songTitle =(String)metadata.get("title");
//            String artistName = (String) metadata.get("artist");
//
//            if(songTitle!=null && artistName!=null){
//                titleLabel.setText(songTitle+" - "+artistName);
//            }
//            else if (songTitle!=null ) {
//                titleLabel.setText(songTitle);
//            }
//            else{
//                titleLabel.setText(file.getName());
//            }
//        });
//
//        mediaPlayer.currentTimeProperty().addListener((observable, oldTime,newTime)->{
//            timeLabel.setText(formatTime(newTime));
//
//            if(!progressBar.isPressed()){
//                progressBar.setValue(newTime.toSeconds());
//            }
//        });
//
//        progressBar.valueProperty().addListener((observable,oldTime,newTime)->{
//            if(progressBar.isPressed()){
//                mediaPlayer.seek(Duration.seconds(newTime.doubleValue()));
//            }
//        });
//
//        play();
//    }

    public void addFilesToPlaylist(List<File> files){
        boolean wasEmpty = playList.isEmpty();
        playList.addAll(files);

        if(wasEmpty){
            currentIndex=0;
            playCurrentSong();
        }
    }


    private void playCurrentSong(){
        if(playList.isEmpty()) return;

        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }

        playListView.getSelectionModel().select(currentIndex);
        playListView.scrollTo(currentIndex);

        File currentFile = playList.get(currentIndex);
        Media media = new Media(currentFile.toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setVolume(volumeSlider.getValue());

        mediaPlayer.setOnEndOfMedia(this::next);

        mediaPlayer.setOnReady(()->{
            Duration totalDuration = media.getDuration();
            durationLabel.setText(formatTime(totalDuration));
            progressBar.setMax(totalDuration.toSeconds());

            ObservableMap<String,Object> metadata = media.getMetadata();
            if(metadata.containsKey("image")){
                Image albumArt = (Image) metadata.get("image");
                albumCover.setImage(albumArt);
            }
            else{
                albumCover.setImage(null);
            }

            String songTitle =(String)metadata.get("title");
            String artistName = (String) metadata.get("artist");

            if(songTitle!=null && artistName!=null){
                titleLabel.setText(songTitle+" - "+artistName);
            }
            else if (songTitle!=null ) {
                titleLabel.setText(songTitle);
            }
            else{
                titleLabel.setText(currentFile.getName());
            }
        });

        mediaPlayer.currentTimeProperty().addListener((observable, oldTime,newTime)->{
            timeLabel.setText(formatTime(newTime));

            if(!progressBar.isPressed()){
                progressBar.setValue(newTime.toSeconds());
            }
        });


        play();


    }


    public void playSongAtIndex(int index){
        if(index>=0 && index<playList.size()){
            currentIndex=index;
            playCurrentSong();
        }
    }

    public boolean togglePlayPause(){
        if(mediaPlayer==null) return false;

        if(mediaPlayer.getStatus()==MediaPlayer.Status.PLAYING){
            mediaPlayer.pause();
            return false;
        }

        mediaPlayer.play();
        return true;
    }



    public void play(){
        if(mediaPlayer != null) {
            mediaPlayer.play();
        }
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
        currentIndex++;
        if(currentIndex >=playList.size()) currentIndex =0;

        playCurrentSong();
        play();
    }

    public void prev(){
        currentIndex--;
        if(currentIndex <0) currentIndex =playList.size()-1;

        playCurrentSong();
        play();
    }

}

package org.zoho;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class MusicPlayer {
    private String[] songs;
    private MediaPlayer mediaPlayer;
    private int currentSongIndex =0;

    public MusicPlayer(){
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

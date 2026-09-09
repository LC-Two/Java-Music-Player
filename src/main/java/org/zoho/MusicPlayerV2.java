package org.zoho;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MusicPlayerV2 {
    private String[] songs;
    private Clip clip;
    private int currentSongIndex=0;

    public MusicPlayerV2(){
        songs = new String[]{"src/main/resources/Gintama_ED_25.wav",
                "src/main/resources/TheClimb.wav"};
    }

    private void loadMusic(int index){
        if(clip !=null && clip.isOpen()){
            clip.close();
        }

        File file = new File(songs[index]);

        if(!file.exists()){
            System.out.println("cannot find file "+file.getPath());
        }

        try(AudioInputStream audioInput = AudioSystem.getAudioInputStream(file)){
            clip= AudioSystem.getClip();
            clip.open(audioInput);
            play();
        }
        catch (UnsupportedAudioFileException e){
            System.out.println("Audio file is not supported");
        }
        catch (IOException e){
            System.out.println("Something went wrong when reading files");
        }
        catch(LineUnavailableException e){
            System.out.println("Audio line Unavailable");
        }
    }

    private void play(){
        if(clip!=null)  clip.start();
    }

    private void stop(){
        if(clip!=null) clip.stop();
    }

    private void quit(){
        if(clip!=null) clip.close();
        System.out.println("Closing....GOODBYE!!");
    }
    private void next(){
        if(clip!=null){
            currentSongIndex++;
            if(currentSongIndex==songs.length) currentSongIndex=0;

            System.out.println("SKipped to the next song");
            loadMusic(currentSongIndex);
        }
    }

    private void prev(){
        if(clip!=null){
            currentSongIndex--;
            if(currentSongIndex<0) currentSongIndex= songs.length-1;

            System.out.println("SKipped to the previous song");
            loadMusic(currentSongIndex);
        }
    }



    public void playMusic(){
        Scanner scanner = new Scanner(System.in);
        String response ="";

        loadMusic(currentSongIndex);

        while(!response.equals("Q")){
            System.out.println("P -> Play");
            System.out.println("S -> Stop");
            System.out.println("N -> Next");
            System.out.println("Q -> Quit");
            System.out.println("B -> Previous");
            response=scanner.nextLine().toUpperCase();

            switch (response){
                case "P" -> play();
                case "S" -> stop();
                case "Q" -> quit();
                case "N" -> next();
                case "B" -> prev();
                default -> System.out.println("Invalid Choice");
            }
        }
        scanner.close();
    }

}

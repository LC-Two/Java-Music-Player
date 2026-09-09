package org.zoho;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class MusicPlayer {

    private String filePath1 = "src/main/resources/Gintama_ED_25.wav";
    private String filePath2 = "src/main/resources/TheClimb.wav";

    File file1 = new File(filePath1);
    File file2 = new File(filePath2);



    public void playSong(){

        try(AudioInputStream audioInput= AudioSystem.getAudioInputStream(file1);
            Scanner scanner = new Scanner(System.in)){

            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);

            String response="";

            while(!response.equals("Q")){
                System.out.println("P -> Play");
                System.out.println("S -> Stop");
                System.out.println("R -> Reset");
                System.out.println("Q -> Quit");
                response=scanner.nextLine();

                switch(response){
                    case "P" -> clip.start();
                    case "S" -> clip.stop();
                    case "R" -> clip.setMicrosecondPosition(0);
                    case "Q" -> clip.close();
                    default -> System.out.println("Invalid Choice!");
                }
            }


        }
        catch (FileNotFoundException e){
            System.out.println("Cannot Locate resource");
        }
        catch(UnsupportedAudioFileException e){
            System.out.println("Unsupported File");
        }
        catch(LineUnavailableException e){
            System.out.println("Unable to access audio resource");
        }
        catch(IOException e){
            System.out.println("Something went wrong");
        }

    }


}

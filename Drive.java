import java.util.*;
import java.io.*;

public class Drive{
  public static void main(String[] args) {
    try {
      WordSearch grid = new WordSearch(175,112, "pokemon.txt");
      //this takes up the whole screen
      System.out.println(grid);
    }catch(FileNotFoundException e) {
      System.out.println("File not found: " + "pokemon.txt");
      System.exit(1);
    }
  /*try {
    WordSearch grid0 = new WordSearch(50,50, "words.txt", 100);
    System.out.println(grid0);
  }catch(FileNotFoundException e) {
    System.out.println("File not found: " + "words.txt");
    System.exit(1);
  }*/
  int defaultRow = 10;
  int defaultCol = 10;
  int defaultSeed = 0;
  if (args.length > 0) {
    defaultRow = Integer.parseInt(args[0]);
  }
  if (args.length > 1) {
    defaultCol = Integer.parseInt(args[1]);
  }
  if (args.length > 3) {
    defaultSeed = Integer.parseInt(args[3]);
  }
  try {
    WordSearch grid1 = new WordSearch(defaultRow, defaultCol, "pokemon.txt", defaultSeed);
    System.out.println(grid1);
  }catch(FileNotFoundException e) {
    System.out.println("File not found: " + "pokemon.txt");
    System.exit(1);
  }
}
}

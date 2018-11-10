import java.util.*;
import java.io.*;

public class Drive{
  public static void main(String[] args) {
    try {
      WordSearch grid = new WordSearch(10,10, "words.txt");
      System.out.println(grid);
      grid.addWordHorizontal("pikachu", 2, 2);
      System.out.println(grid);
      grid.addWordDiagonal("venasaur", 0, 0); // shoould not do anything
      System.out.println(grid);
      grid.addWordDiagonal("arbok", 2, 5);
      System.out.println(grid);
      grid.addWordVertical("ekans", 1, 4);
      System.out.println(grid);
    }catch(FileNotFoundException e) {
      System.out.println("File not found: " + "words.txt");
      System.exit(1);
    }
    }
  }

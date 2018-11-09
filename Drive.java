import java.util.*;
import java.io.*;

public class Drive{
  public static void main(String[] args) {
    try {
      WordSearch grid = new WordSearch(10,10, "words.txt");
      System.out.println(grid);
    }catch(FileNotFoundException e) {
      System.out.println("File not found: " + "words.txt");
      System.exit(1);
    }
  }
}

import java.util.*;
import java.io.*;

public class Drive{
  public static void main(String[] args) {
    try {
      WordSearch grid = new WordSearch(10,10, "words.txt");
      //System.out.println(grid);
      grid.addWord("pikachu", 2, 2, 0, 1); //pikachu horizontally
      //System.out.println(grid);
      grid.addWord("venasaur", 1, 3, 1, 0); // should not do anything
      //System.out.println(grid);
      grid.addWord("arbok", 2, 5, 1, 1); //arbok diagonally
      //System.out.println(grid);
      grid.addWord("ekans", 1, 4, 1, 0); //ekans vertically
      //System.out.println(grid);
      grid.addWord("raichu", 7, 8, 0, -1); // raichu spelled backwards horizontally
      //System.out.println(grid);
      grid.addWord("espeon", 9, 0, -1, 0); // espeon spelled backwards vertically;
      //System.out.println(grid);
      grid.addWord("ekans", 9, 0, -1, 1); // ekans diagonal up right
      //System.out.println(grid);
      // TECHNICALLY NOT IN MY WORDS TEXT
      grid.addWord("pan", 2, 2, 1, -1); //pan diagonal down left
      //System.out.println(grid);
      // TECHNICALLY NOT IN MY WORDS TEXT
      grid.addWord("pun", 2, 2, -1, -1);; //pun diagonal up left
      System.out.println(grid);
    }catch(FileNotFoundException e) {
      System.out.println("File not found: " + "words.txt");
      System.exit(1);
    }
  try {
    WordSearch grid0 = new WordSearch(10,10, "words.txt", 100);
    //System.out.println(grid0);
    grid0.addWord("pikachu", 2, 2, 0, 1); //pikachu horizontally
    //System.out.println(grid0);
    grid0.addWord("venasaur", 1, 3, 1, 0); // should not do anything
    //System.out.println(grid0);
    grid0.addWord("arbok", 2, 5, 1, 1); //arbok diagonally
    //System.out.println(grid0);
    grid0.addWord("ekans", 1, 4, 1, 0); //ekans vertically
    //System.out.println(grid0);
    grid0.addWord("raichu", 7, 8, 0, -1); // raichu spelled backwards horizontally
    //System.out.println(grid0);
    grid0.addWord("espeon", 9, 0, -1, 0); // espeon spelled backwards vertically;
    //System.out.println(grid0);
    grid0.addWord("ekans", 9, 0, -1, 1); // ekans diagonal up right
    //System.out.println(grid0);
    // TECHNICALLY NOT IN MY WORDS TEXT
    grid0.addWord("pan", 2, 2, 1, -1); //pan diagonal down left
    //System.out.println(grid0);
    // TECHNICALLY NOT IN MY WORDS TEXT
    grid0.addWord("pun", 2, 2, -1, -1);; //pun diagonal up left
    System.out.println(grid0);
  }catch(FileNotFoundException e) {
    System.out.println("File not found: " + "words.txt");
    System.exit(1);
  }
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
    WordSearch grid1 = new WordSearch(defaultRow, defaultCol, "words.txt", defaultSeed);
    //System.out.println(grid1);
    grid1.addWord("pikachu", 2, 2, 0, 1); //pikachu horizontally
    //System.out.println(grid1);
    grid1.addWord("venasaur", 1, 3, 1, 0); // should not do anything
    //System.out.println(grid1);
    grid1.addWord("arbok", 2, 5, 1, 1); //arbok diagonally
    //System.out.println(grid1);
    grid1.addWord("ekans", 1, 4, 1, 0); //ekans vertically
    //System.out.println(grid1);
    grid1.addWord("raichu", 7, 8, 0, -1); // raichu spelled backwards horizontally
    //System.out.println(grid1);
    grid1.addWord("espeon", 9, 0, -1, 0); // espeon spelled backwards vertically;
    //System.out.println(grid1);
    grid1.addWord("ekans", 9, 0, -1, 1); // ekans diagonal up right
    //System.out.println(grid1);
    // TECHNICALLY NOT IN MY WORDS TEXT
    grid1.addWord("pan", 2, 2, 1, -1); //pan diagonal down left
    //System.out.println(grid1);
    // TECHNICALLY NOT IN MY WORDS TEXT
    grid1.addWord("pun", 2, 2, -1, -1);; //pun diagonal up left
    System.out.println(grid1);
  }catch(FileNotFoundException e) {
    System.out.println("File not found: " + "words.txt");
    System.exit(1);
  }
}
}

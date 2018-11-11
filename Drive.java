import java.util.*;
import java.io.*;

public class Drive{
  public static void main(String[] args) {
  int defaultRow = 10;
  int defaultCol = 10;
  int defaultSeed = 0;
  String fileName = "";
  if (args.length > 0) defaultRow = Integer.parseInt(args[0]);
  if (args.length > 1) defaultCol = Integer.parseInt(args[1]);
  if (args.length > 2) fileName = args[2];
  if (args.length > 3) defaultSeed = Integer.parseInt(args[3]);
  try {
    if (defaultSeed != 0) {
      WordSearch grid1 = new WordSearch(defaultRow, defaultCol, fileName, defaultSeed);
      System.out.println(grid1);
  }
    else {
      WordSearch grid1 = new WordSearch(defaultRow, defaultCol, fileName);
      System.out.println(grid1);
    }
  }catch(FileNotFoundException e) {
    System.out.println("File not found: " + fileName + " Please create one");
    System.exit(1);
  }
}
}

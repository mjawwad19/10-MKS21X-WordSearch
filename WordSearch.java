import java.util.*; //random, scanner, arraylist
import java.io.*; //file, filenotfoundexception


public class WordSearch{
    private char[][]data;
    private int seed;
    private Random randgen;
    private ArrayList<String> wordsToAdd;
    private ArrayList<String> wordsAdded;

    //choose a randSeed using the clock random
    /**Initialize the grid to the size specified
     *and fill all of the positions with '_'
     *@param row is the starting height of the WordSearch
     *@param col is the starting width of the WordSearch
    helpConstruct is repetitive code in constructors
    fillAllUnder fills in all blank spaces with a randomly generated letter
    only when the user doesn't specify they want the anwer key*/
    private void helpConstruct(int rows, int cols, String fileName) throws FileNotFoundException {
      if (rows <= 0 ||
          cols <= 0) throw new IllegalArgumentException("there is no such thing as a negative/0 row height or column width");
      data = new char[rows][cols];
      clear();
      File f = new File(fileName);
      Scanner in = new Scanner(f);
      wordsToAdd = new ArrayList<>();
      wordsAdded = new ArrayList<>();
      while (in.hasNext()) {
        wordsToAdd.add(in.nextLine().toUpperCase());
      }
      addAllWords();
    }
    private void fillAllUnder() {
      Random r = new Random();
      int letter = Math.abs(r.nextInt()%26) + 'A';
      for (int i= 0; i < data. length; i++) {
        for (int j = 0; j < data[i].length; j++) {
          if (data[i][j] == '_') {
            data[i][j] = (char) letter;
            letter = Math.abs(r.nextInt()%26) + 'A';
          }
        }
      }
    }
    public WordSearch(int rows, int cols, String fileName, int randSeed, boolean answer) throws FileNotFoundException {
      seed = randSeed;
      randgen = new Random(seed);
      helpConstruct(rows, cols, fileName);
      if (!answer) {
        fillAllUnder();
      }
    }

    /**Set all values in the WordSearch to underscores'_'*/
    private void clear(){
      for (int i = 0; i < data.length; i++) {
        for (int j = 0; j < data[i].length; j++) {
          data[i][j] = '_';
        }
      }
    }
    // just makes life easier by reading the arrayLists of the words :)
    private String ALToString(ArrayList<String> a) {
      String out = "";
      for (int i = 0; i < a.size(); i++) {
        if (i != a.size() - 1) out += a.get(i) + ", ";
        else out += a.get(i);
      }
      return out;
    }
    /**Each row is a new line, there is a space between each letter
     *@return a String with each character separated by spaces, and rows
     *separated by newlines.
     */
    public String toString() {
         String out = "|";
         for (int i = 0; i < data.length; i++) {
             for (int j = 0; j < data[i].length; j++) {
               if (data[i][j] == '_') {out += ' ';}
               else{
                 out += data[i][j];}
                 if (j != data[i].length-1) out += ' ';
             }
             if (i != data.length-1) out += "|" + '\n' + "|";
         }
         out += "|" + '\n';
         out += "Words: " + ALToString(wordsAdded);
         //System.out.println("Words not added: " + ALToString(wordsToAdd));
         out += " (seed: " + seed + ")";
         return out;
     }
     /**Attempts to add a given word to the specified position of the WordGrid.
      *The word is added in the direction rowIncrement,colIncrement
      *Words must have a corresponding letter to match any letters that it overlaps.
      *
      *@param word is any text to be added to the word grid.
      *@param row is the vertical locaiton of where you want the word to start.
      *@param col is the horizontal location of where you want the word to start.
      *@param rowIncrement is -1,0, or 1 and represents the displacement of each letter in the row direction
      *@param colIncrement is -1,0, or 1 and represents the displacement of each letter in the col direction
      *@return true when: the word is added successfully.
      *        false when: the word doesn't fit, OR  rowchange and colchange are both 0,
      *        OR there are overlapping letters that do not match
      */

    private boolean addWord(int r, int c, String word, int rowIncrement, int colIncrement) {
      int len = word.length();
      word = word.toUpperCase();
      int x = r + (len -1) * rowIncrement;
      int y = c + (len -1) * colIncrement;
      if (r < 0 ||
          c < 0 || // can't have negative row or col
          r >= data.length ||
          c >= data[0].length ||  //can't have a row or col that is greater than the size initialized with
          rowIncrement == 0 && colIncrement == 0 ||
          rowIncrement > 1 ||
          colIncrement < -1 || //now I'm just babying the user oof
          x < 0 ||
          x >= data.length || // these 4 right here is if the word CANNOT fit because too big!
          y < 0 ||
          y >= data[0].length) return false;
      for (int i = 0; i < len; i++) {
        if (data[r +i*rowIncrement] [c +i*colIncrement] != '_' &&
            data[r +i*rowIncrement] [c +i*colIncrement] != word.charAt(i)) return false;
      }
      for (int i = 0; i < len; i++) {
        data[r +i*rowIncrement][c +i*colIncrement] = word.charAt(i);
      }
      return true;
    }
    // Bless Victor and Ethan for explaining so much for this to work all the way
    private void addAllWords() {
      int countFail = 0; //Mr K's positional tries
      int row, col, rowIncrement,colIncrement,rowSize,colSize;
      String word;
      boolean added; //whether the specifc word was added or not

      while (wordsToAdd.size()>= 1) {
        countFail = 0;
        added = false;
        int index = (Math.abs(randgen.nextInt()%wordsToAdd.size()));
        word = wordsToAdd.get(index).toUpperCase();
        int len = word.length();
        rowIncrement = randgen.nextInt()%2;
        colIncrement = randgen.nextInt()%2;
        /* thanks to an explanation from Victor, I should also randomly generate
        the position on the grid because otherwise it would
        a) take too long
        b) then the randomness of word choice is ruined because only a few would
        actually work in a specific position, which would ALSO add more time*/
        rowSize = Math.abs(data.length + 1 - len* colIncrement);
        colSize = Math.abs(data[0].length + 1 - len * rowIncrement);
        /*this is to help choose the ideal position to add the word, similar to helper
        Thanks to Ethan for the + 1 tip btw I had areas with blank spots and he was able
        to point this out*/
          while (countFail < 3600 && added == false) {
            row = Math.abs(randgen.nextInt()% rowSize);
            col = Math.abs(randgen.nextInt()% colSize);
            if (addWord(row, col, word, rowIncrement, colIncrement)) {
              wordsAdded.add(wordsToAdd.remove(index));
              added = true;
            }
            else countFail ++;
          }
        if (added == false) wordsToAdd.remove(index);
        //remove a word from being chosen aaain if it cant be added ever - Discussion with Victor
      }
    }
    public static void main(String[] args) {
    int defaultRow = 0;
    int defaultCol = 0;
    int defaultSeed;
    Random randgen = new Random();
    defaultSeed = Math.abs(randgen.nextInt()%10000);
    String fileName = "";
    boolean answer = false;
    try {
      if (args.length > 2) {
        fileName = args[2];
        defaultRow = Integer.parseInt(args[0]);
        defaultCol = Integer.parseInt(args[1]);
      }
      if (args.length > 3) {
        if (Integer.parseInt(args[3]) > 0 && Integer.parseInt(args[3]) <9999)
         defaultSeed = Integer.parseInt(args[3]);
        else {
          System.out.println( "Please enter a seed within the range of 0 to 10,000");
          System.exit(1);
        }
      }
      if (args.length > 4 && (args[4].equals("key"))) answer = true;
      WordSearch grid = new WordSearch(defaultRow, defaultCol, fileName, defaultSeed, answer);
      System.out.println(grid);
    }catch(NumberFormatException e) {
      System.out.println( "Please enter a seed within the range of 0 to 10,000, not a string.");
    }catch(FileNotFoundException e) {
      System.out.println("File not found: " + fileName + " Please create this file if you would like to use it in the same directory");
      System.exit(1);
    }catch(IllegalArgumentException e) {
      System.out.println("Please enter arguments in this order: row col fileName [seed [answer]]. Enter key in place of answer if you want the solution. Note, a row or column may not be <= 0 \n");
      System.exit(1);
    }
  }
}

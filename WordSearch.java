import java.util.*; //random, scanner, arraylist
import java.io.*; //file, filenotfoundexception


public class WordSearch{
    private char[][]data;
    //Props to Ethan for suggesting it would be much easier to have the rows and columns saved so they can be used in addWords
    private int width, height;
    //the random seed used to produce this WordSearch
    private int seed;
    //a random Object to unify your random calls
    private Random randgen;
    //all words from a text file get added to wordsToAdd, indicating that they have not yet been added
    private ArrayList<String> wordsToAdd;
    //all words that were successfully added get moved into wordsAdded.
    private ArrayList<String> wordsAdded;

    //choose a randSeed using the clock random
    /**Initialize the grid to the size specified
     *and fill all of the positions with '_'
     *@param row is the starting height of the WordSearch
     *@param col is the starting width of the WordSearch
     */
    //the helper is the repetitive code in my constructors//
    private void helpConstruct(int rows, int cols, String fileName) throws FileNotFoundException {
      if (rows <= 0 ||
          cols <= 0) throw new IllegalArgumentException("there is no such thing as a negative/0 row height or column width");
      data = new char[rows][cols];
      width = cols;
      height = rows;
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

    public WordSearch(int rows, int cols, String fileName, int randSeed, boolean answer) throws FileNotFoundException {
      seed = randSeed;
      randgen = new Random(seed);
      helpConstruct(rows, cols, fileName);
      if (!answer) {
        fillAllUnder();
      }
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
      if (r < 0 ||
          c < 0 || // can't have negative row or col
          r >= height ||
          c >= width ||  //can't have a row or col that is greater than the size initialized with
          rowIncrement == 0 && colIncrement == 0 ||
          rowIncrement > 1 ||
          colIncrement < -1 || //now I'm just babying the user oof
          r + (len -1) * rowIncrement < 0 ||
          r + (len -1) * rowIncrement >= height || // these 4 right here is if the word CANNOT fit!
          c + (len -1) * colIncrement < 0 ||
          c + (len - 1) * colIncrement >= width) return false;
      // this is a combination of col + len > data[row] // row + len > data[col]
      for (int i = 0; i < len; i++) {
        if (data[r +i*rowIncrement] [c +i*colIncrement] != '_' &&
            data[r +i*rowIncrement] [c +i*colIncrement] != word.charAt(i)) return false;
      }
      for (int i = 0; i < len; i++) {
        data[r +i*rowIncrement][c +i*colIncrement] = word.charAt(i);
      }
      return true;
    }
    // Bless Victor and Ethan for explaining so much for this to work
    private void addAllWords() {
      int countFail = 0; //Mr K's positional tries
      int index = 0;
      int row, col, rowIncrement,colIncrement,rowSize,colSize;
      String word;
      boolean added; //whether the specifc word was added or not
      while (!wordsToAdd.isEmpty()) {
        countFail = 0;
        added = false;
        index = Math.abs(randgen.nextInt()%wordsToAdd.size());
        //randomly select a word from wordsToAdd (step 1)
        word = wordsToAdd.get(index).toUpperCase();
        int len = word.length();
        // for consistency with other fxns
        rowIncrement = randgen.nextInt()%2;
        colIncrement = randgen.nextInt()%2;
        // randomly select a direction to go from
        /* thanks to an explanation from Victor, I should also randomly generate
        the position on the grid because otherwise it would
        a) take too long
        b) then the randomness of word choice is ruined because only a few would
        actually work in a specific position, which would ALSO add more time*/
        rowSize = height + 1 - len* colIncrement;
        colSize = width + 1 - len * rowIncrement;
        // this is to help choose the ideal position to add the word, similar to helper
        if (rowSize > 0 && colSize > 0) {
          while (countFail < 3600 && added == false) {
            row = randgen.nextInt()% rowSize;
            if (row < 0) row += rowSize;
            col = randgen.nextInt()% colSize;
            if (col < 0) col += colSize;
            if (addWord(row, col, word, rowIncrement, colIncrement)) {
              wordsAdded.add(wordsToAdd.remove(index));
              //remove a successfully added word and put it into wordsAdded
              added = true;
              //move on to the NEXT word assuming there is a next word to Add
            }
            else countFail ++;
          }
        }
        if (added == false) wordsToAdd.remove(index);
        //remove a word from being chosen aaain if it cant be added ever
      }
    }
    public static void main(String[] args) {
    int defaultRow = 10;
    int defaultCol = 10;
    int defaultSeed;
    Random randgen = new Random();
    defaultSeed = Math.abs(randgen.nextInt()%10000);
    String fileName = "";
    boolean answer = false;
    String help0 = "Please enter arguments in this order: row col fileName [seed [answer]] \n";
    String help1 = "Please enter a seed within the range of 0 to 10,000";
    if (args.length > 2) {
      fileName = args[2];
      defaultRow = Integer.parseInt(args[0]);
      defaultCol = Integer.parseInt(args[1]);
    }
    else System.out.println(help0);
    if (args.length > 3) {
      if (Integer.parseInt(args[3]) > 0 && Integer.parseInt(args[3]) <10000) defaultSeed = Integer.parseInt(args[3]);
      else System.out.println(help1);
    }
    if (args.length > 4 && (args[4].equals("key"))) answer = true;

    try {
        WordSearch grid1 = new WordSearch(defaultRow, defaultCol, fileName, defaultSeed, answer);
        System.out.println(grid1);
    }catch(FileNotFoundException e) {
      System.out.println("File not found: " + fileName + " Please create one");
      System.exit(1);
    }
  }
}

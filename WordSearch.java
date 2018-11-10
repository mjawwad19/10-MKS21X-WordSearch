import java.util.*; //random, scanner, arraylist
import java.io.*; //file, filenotfoundexception


public class WordSearch{
    private char[][]data;
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
    private void helpConstruct(int rows, int cols, String fileName) throws FileNotFoundException {
      if (rows <= 0 ||
          cols <= 0) throw new IllegalArgumentException("there is no such thing as a negative row or column");
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
    }
    public WordSearch(int rows, int cols, String fileName) throws FileNotFoundException {
      randgen = new Random();
      seed = randgen.nextInt();
      helpConstruct(rows, cols, fileName);
    }
    public WordSearch(int rows, int cols, String fileName, int randSeed) throws FileNotFoundException {
      seed = randSeed;
      randgen = new Random(seed);
      helpConstruct(rows, cols, fileName);
    }
    public WordSearch(int rows, int cols) {
      if (rows <= 0 ||
          cols <= 0) throw new IllegalArgumentException("there is no such thing as a negative row or column");
      data = new char[rows][cols];
      width = cols;
      height = rows;
      clear();
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
                 out += data[i][j];
                 if (j != data[i].length-1) out += ' ';
             }
             if (i != data.length-1) out += "|" + '\n' + "|";
         }
         out += "|" + '\n';
         out += "Words: " + ALToString(wordsAdded);
         //System.out.println("Words not added: " + ALToString(wordsToAdd));
         return out;
     }

    /*private helper method for all add methods
    after Mr.K hinted we could use a helper to clean up repetitive code, I came up
    with this:
    takes all the previous return false conditionals;
    since the only difference between the add is the adding index of the word direction,
    a 0 or 1 can dictate if I want horizontal (0,1), vertical (1,0) or diagonal (1,1)*/
    private boolean addWord(int r, int c, String word, int rowIncrement, int colIncrement) {
      int len = word.length();
      word = word.toUpperCase();
      if (r < 0 ||
          c < 0 ||
          r >= height ||
          c >= width ||
          rowIncrement == 0 && colIncrement == 0 ||
          rowIncrement > 1 ||
          colIncrement < -1) return false;
      // this if is from all addWords previously made nothing new
      if (r + (len -1) * rowIncrement < 0 || r + (len -1) * rowIncrement >= height ||
          c + (len -1) * colIncrement < 0 || c + (len - 1) * colIncrement >= width) return false;
      // this is a combination of col + len > data[row] // row + len > data[col]
      for (int i = 0; i < len; i++) {
        if (data[r +i*rowIncrement] [c +i*colIncrement] != '_' &&
            data[r +i*rowIncrement] [c +i*colIncrement] != word.charAt(i)) return false;
      }
      for (int i = 0; i < len; i++) {
        data[r +i*rowIncrement][c +i*colIncrement] = word.charAt(i);
      }
      wordsToAdd.remove(word);
      wordsAdded.add(word);
      return true;
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
    public boolean addWord(String word, int row, int col, int rowIncrement, int colIncrement) {
      return addWord(row, col, word, rowIncrement, colIncrement);
    }
  }

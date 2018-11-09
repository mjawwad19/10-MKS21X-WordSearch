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
    public WordSearch(int rows, int cols, String fileName) throws FileNotFoundException {
      randgen = new Random();
      seed = randgen.nextInt();
      //copied over from original constructor
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
        wordsToAdd.add(in.nextLine());
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
                 out += data[i][j];
                 if (j != data[i].length-1) out += ' ';
             }
             if (i != data.length-1) out += "|" + '\n' + "|";
         }
         out += "|" + '\n';
         out += "Words: " + ALToString(wordsToAdd) + ALToString(wordsAdded);
         return out;
     }

    /*private helper method for all add methods
    after Mr.K hinted we could use a helper to clean up repetitive code, I came up
    with this:
    takes all the previous return false conditionals;
    since the only difference between the add is the adding index of the word direction,
    a 0 or 1 can dictate if I want horizontal (0,1), vertical (1,0) or diagonal (1,1)*/
    private boolean addWord(String word, int row, int col, int r, int c) {
      int len = word.length();
      if (row < 0 ||
          col < 0 ||
          row >= height ||
          col >= width) return false;
      // this if is from all addWords previously made nothing new
      if (row + (len -1) * r < 0 || row + (len -1) * r >= height ||
          col + (len -1) * c < 0 || col + (len - 1) * c >= width) return false;
      // this is a combination of col + len > data[row] // row + len > data[col]
      for (int i = 0; i < len; i++) {
        if (data[row +i*r] [col +i*c] != '_' &&
            data[row +i*r] [col +i*c] != word.charAt(i)) return false;
      }
      for (int i = 0; i < len; i++) {
        data[row +i*r][col +i*c] = word.charAt(i);
      }
      return true;
    }

    /**Attempts to add a given word to the specified position of the WordGrid.
     *The word is added from left to right, must fit on the WordGrid, and must
     *have a corresponding letter to match any letters that it overlaps.
     *
     *@param word is any text to be added to the word grid.
     *@param row is the vertical locaiton of where you want the word to start.
     *@param col is the horizontal location of where you want the word to start.
     *@return true when the word is added successfully. When the word doesn't fit,
     * or there are overlapping letters that do not match, then false is returned
     * and the board is NOT modified.
     */
    public boolean addWordHorizontal(String word,int row, int col){
      return addWord(word, row, col, 0, 1);
    }

    /**Attempts to add a given word to the specified position of the WordGrid.
     *The word is added from top to bottom, must fit on the WordGrid, and must
     *have a corresponding letter to match any letters that it overlaps.
     *
     *@param word is any text to be added to the word grid.
     *@param row is the vertical locaiton of where you want the word to start.
     *@param col is the horizontal location of where you want the word to start.
     *@return true when the word is added successfully. When the word doesn't fit,
     *or there are overlapping letters that do not match, then false is returned.
     *and the board is NOT modified.
     */
    public boolean addWordVertical(String word,int row, int col){
      return addWord(word, row, col, 1, 0);
    }

    /**Attempts to add a given word to the specified position of the WordGrid.
    *The word is added from top left to bottom right, must fit on the WordGrid,
    *and must have a corresponding letter to match any letters that it overlaps.
    *
    *@param word is any text to be added to the word grid.
    *@param row is the vertical locaiton of where you want the word to start.
    *@param col is the horizontal location of where you want the word to start.
    *@return true when the word is added successfully. When the word doesn't fit,
    *or there are overlapping letters that do not match, then false is returned.
    */
   public boolean addWordDiagonal(String word,int row, int col){
     return addWord(word, row, col, 1, 1);
   }
}

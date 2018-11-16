import java.util.*; //random, scanner, arraylist
import java.io.*; //file, filenotfoundexception

public class WordSearch{
    private char[][]data;
    private int seed;
    private Random randgen;
    private ArrayList<String> wordsToAdd;
    private ArrayList<String> wordsAdded;
    private int maxWordsToAdd;

    /*fillAllUnder fills in all blank spaces with a randomly generated letter
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
      maxWordsToAdd = wordsToAdd.size();
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
         out += "| \nWords:  " + ALToString(wordsAdded) + "(seed: " + seed + ")";
         return out;
     } // fixed toString after talking with Mr.K if I could add the extra stuff [aka debugging stuff] he said no
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

    private boolean addWord(String word, int r, int c, int rowIncrement, int colIncrement) {
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
      wordsAdded.add(word);
      wordsToAdd.remove(word); //Peihua suggested I move my removal and addition to the arrayLists here to clean up my addAllWords in class
      return true;
    }
    // Bless Victor, Peihua, and Ethan for explaining so much for this to work all the way
    private void addAllWords() {
         String word;
         for (int i = 0; wordsToAdd.size() > 0 && i < maxWordsToAdd; i++) {
           int index = Math.abs(randgen.nextInt() % wordsToAdd.size());
           word = wordsToAdd.get(index);
           int rowInc = randgen.nextInt() % 2;
           int colInc = randgen.nextInt() % 2;
           while (rowInc == 0 && colInc == 0) {
             rowInc = randgen.nextInt() % 2;
             colInc = randgen.nextInt() % 2;
           } // this is to prevent extra fails  when I try addWord :/
           for (int fail = 0; fail < data.length*data[0].length && !addWord(word,
                                                  Math.abs(randgen.nextInt() % (data.length + 1)),
                                                  Math.abs(randgen.nextInt() % (data[0].length + 1)), rowInc, colInc); fail++);
         } /*
         Peihua actually showed me the syntax for this for loop and helped clean up my code aLOT so big shout out
         Victor explained to me I should randomize the location each time I try to add / explained random to me
         because I was absent
         Ethan caught why some of my words were not adding when they could have with the + 1 for length
         Peihua also explained the secret of getting the most words in,
         and suggested I use cleaner for loops instead
         when I can: DO NOT REMOVE BAD WORDS!

         THANK YOU FOR UNCLUTTERING MY CLUNKY METHOD <3*/
       }
    public static void main(String[] args) {
    int defaultRow = 0;
    int defaultCol = 0;
    int defaultSeed;
    Random randgen = new Random();
    defaultSeed = Math.abs(randgen.nextInt()%10001); //Mr K said 0 to 100000 inclusive so 10000 has to be possible
    String fileName = "";
    boolean answer = false;
    String help0 = "note a seed is within the range of 0 to 10,000 inclusive";
    String help1 = "Please enter arguments in this order: row col fileName [seed [answer]]. Enter key in place of answer if you want the solution. Note, a row or column may not be <= 0 ";
    try {
      if (args.length > 2) {
        fileName = args[2];
        defaultRow = Integer.parseInt(args[0]);
        defaultCol = Integer.parseInt(args[1]);
      }
      if (args.length > 3) {
        if (Integer.parseInt(args[3]) >= 0 && Integer.parseInt(args[3]) <= 10000)
         defaultSeed = Integer.parseInt(args[3]);
      }
      if (args.length > 4 && (args[4].equals("key"))) answer = true;
      WordSearch grid = new WordSearch(defaultRow, defaultCol, fileName, defaultSeed, answer);
      System.out.println(grid);
    }catch(NumberFormatException e) {
      System.out.println(help0 + " not a string.\n " + help1);
    }catch(FileNotFoundException e) {
      System.out.println("File not found: " + fileName + " Please create this file if you would like to use it in the same directory \n" + help1 + help0);
      System.exit(1);
    }catch(IllegalArgumentException e) {
      System.out.println(help1 + help0);
      System.exit(1);
    }
  }
}

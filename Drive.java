public class Drive{
  public static void main(String[] args) {
    WordSearch grid = new WordSearch(2,5);
    System.out.println(grid);
    /*Expected
    _ _ _ _ _
    _ _ _ _ _
    */
    System.out.println(grid.addWordHorizontal("hey", 0, 1)); //true;
    grid.addWordHorizontal("hey", 0, 1);
    System.out.println(grid);
    System.out.println(grid.addWordHorizontal("dogma", 0, 1)); //false;
    grid.addWordHorizontal("dogma", 0, 1);
    System.out.println(grid); //nochange
  }
}

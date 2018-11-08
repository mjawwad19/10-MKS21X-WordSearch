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
    /*Expected
    _ h e y _
    _ _ _ _ _
    */
    System.out.println(grid.addWordHorizontal("dogma", 0, 1)); //false;
    grid.addWordHorizontal("dogma", 0, 1);
    System.out.println(grid); //no change
    System.out.println(grid.addWordHorizontal("hay", 0, 1)); //false;
    grid.addWordHorizontal("hay", 0, 1);
    System.out.println(grid); // no change
    System.out.println(grid.addWordVertical("do", 0, 0)); //true
    grid.addWordVertical("do", 0,0);
    System.out.println(grid);
    /*Expected
    d h e y _
    o _ _ _ _
    */
    System.out.println(grid.addWordVertical("ho", 0, 1)); //true
    grid.addWordVertical("ho", 0,1);
    System.out.println(grid);
    /*Expected
    d h e y _
    o o _ _ _
    */
    System.out.println(grid.addWordVertical("hoo", 0, 1)); //false
    grid.addWordVertical("hoo", 0,1);
    System.out.println(grid); // no change
    System.out.println(grid.addWordHorizontal("oomph", 1, 0)); //false
    grid.addWordVertical("oomph", 1,0);
    System.out.println(grid);
    /*Expected
    d h e y _
    o o m p h
    */
  }
}

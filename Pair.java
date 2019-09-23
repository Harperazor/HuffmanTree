/* <Hengpu zhang> <B00754648>
 * Assignment 3
 * Pair class
*/
public class Pair{
   
   private char letter;
   private double prob;
   //constructor
   public Pair(){
   }
   public Pair(char l,double p){
      letter=l;
      prob=p;
   }
   
   //get and set methods
   public void setLetter(char l){
      letter=l;
   }
   public void setProb(double p){
      prob=p;
   }
   public char getLetter(){
      return letter;
   }
   public double getProb(){
      return prob;
   }
   
   //toString method
   public String toString(){
      return "('" + letter + "', " + prob + ")";
   }
}
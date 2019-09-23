/* <Hengpu zhang> <B00754648>
 * Assignment 3
 * HuffmanTree class
*/
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
public class HuffmanTree{
   public static void main(String[]args) throws IOException{
      File file=new File("Frequency.txt");
      Scanner inputFile=new Scanner(file);
      //store the items into the Arraylist
      ArrayList<Pair> freqs = new ArrayList<Pair>();
      String letter,prob;
      char letterC;
      double probD;
      while(inputFile.hasNext()){
         letter=inputFile.nextLine();
         prob=inputFile.nextLine();
         //convert string to char(letter) and double(probabilities)
         letterC=letter.charAt(0);
         probD=Double.parseDouble(prob);
         Pair item=new Pair(letterC,probD);
         freqs.add(item);
      }
      inputFile.close();
      
      //build the Huffman tree
      
      //Queue S
      ArrayList<BinaryTree<Pair>> S = new ArrayList<BinaryTree<Pair>>();
      for(int i=freqs.size()-1;i>=0;i--){
         BinaryTree<Pair> node=new BinaryTree<Pair>();
         node.setData(freqs.get(i));
         S.add(node);
      }
      
      //Queue T
      ArrayList<BinaryTree<Pair>> T = new ArrayList<BinaryTree<Pair>>();
      
      while(!S.isEmpty()){
         boolean NoItem=false;
         BinaryTree<Pair> A=new BinaryTree<Pair>();
         BinaryTree<Pair> B=new BinaryTree<Pair>();
         BinaryTree<Pair> P=new BinaryTree<Pair>();
         //If T is empty
         if(T.isEmpty()){
            A=S.get(0);
            S.remove(0);
            B=S.get(0);
            S.remove(0);
         }
         //If T is not empty,
         else{
            if(S.get(0).getData().getProb() >= T.get(0).getData().getProb()){
               A=T.get(0);
               T.remove(0);
            }
            else{
               A=S.get(0);
               S.remove(0);
            }
            if(S.isEmpty()){
               //if nothing left in S
               B=T.get(0);
               T.remove(0);
               NoItem=true;
            }
            if(T.isEmpty()){
               //if nothing left in T
               B=S.get(0);
               S.remove(0);
               NoItem=true;;
            }
            if(!NoItem){
               if(S.get(0).getData().getProb()>T.get(0).getData().getProb()){
                  B=T.get(0);
                  T.remove(0);
               }
               else{
                  B=S.get(0);
                  S.remove(0);
               }
            }
         }
         P.attachLeft(A);
         P.attachRight(B);
         probD=A.getData().getProb() + B.getData().getProb();
         Pair item=new Pair('&',probD);
         P.setData(item);
         T.add(P); 
      }
      
      while(T.size()>1){
         BinaryTree<Pair> A=new BinaryTree<Pair>();
         BinaryTree<Pair> B=new BinaryTree<Pair>();
         BinaryTree<Pair> P=new BinaryTree<Pair>();
         A=T.get(0);
         T.remove(0);
         B=T.get(0);
         T.remove(0);
         P.attachLeft(A);
         P.attachRight(B);
         probD=A.getData().getProb() + B.getData().getProb();
         Pair item=new Pair('&',probD);
         P.setData(item);
         T.add(P); 
      }
      
      //Derive the Huffman codes
      BinaryTree<Pair> HuffmanTree = new BinaryTree<Pair>();
      HuffmanTree=T.get(0);
      String[] code=findEncoding(HuffmanTree);
      
      //write encode to a text file
      PrintStream encodeFile = new PrintStream("Encoded.txt");
      System.setOut(encodeFile);
      
      //Encode each letter using the Huffman codes
      File encode=new File("Pokemon.txt");
      Scanner inputFile2=new Scanner(encode);
      String line;
      while(inputFile2.hasNext()){
         line=inputFile2.nextLine();
         char[] lineC=line.toCharArray();
         for(int i=0;i<lineC.length;i++){
            if(lineC[i]==' '){
               System.out.print(" ");
            }
            else{
               int index=((byte)lineC[i])-65;
               System.out.print(code[index]);
            }
         }
         System.out.println();
      }
      inputFile2.close();
      
      //Write the decoded file into another text file
      PrintStream decodeFile = new PrintStream("Decoded.txt");
      System.setOut(decodeFile);
      
      //Decode
      File decode=new File("Encoded.txt");
      Scanner inputFile3=new Scanner(decode);
      BinaryTree<Pair> curr=new BinaryTree<Pair>();
      curr=HuffmanTree;
      while(inputFile3.hasNext()){
         line=inputFile3.nextLine();
         char[] lineC=line.toCharArray();
         for(int i=0;i<lineC.length;i++){
            if(lineC[i]==' '){
               System.out.print(" ");
            }
            else if(lineC[i]=='0'){
               curr=curr.getLeft();
               if(curr.getLeft()==null && curr.getRight()==null){
                  System.out.print(curr.getData().getLetter());
                  curr=HuffmanTree;
               }
            }
            else{
               curr=curr.getRight();
               if(curr.getLeft()==null && curr.getRight()==null){
                  System.out.print(curr.getData().getLetter());
                  curr=HuffmanTree;
               }
            }
         }
         System.out.println();
      }
      inputFile3.close();
   }
   
   //methods to find the encoding
   public static void findEncoding(BinaryTree<Pair> t, String[] a, String prefix){
      if (t.getLeft()==null&& t.getRight()==null){
         a[((byte)(t.getData().getLetter()))-65]= prefix;
      }
      else{
         findEncoding(t.getLeft(), a, prefix+"0");
         findEncoding(t.getRight(), a, prefix+"1");
      }
   }
   public static String[] findEncoding(BinaryTree<Pair> t){
      String[] result = new String[26];
      findEncoding(t, result, "");
      return result;
   }
   
}
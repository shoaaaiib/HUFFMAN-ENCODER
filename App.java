// Huffman Coding in Java

import java.util.*;



public class App {
  

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    System.out.print("Enter the path of file you wish to encode: ");
    String filePath = sc.nextLine();
    Encoder.encode(filePath);

    sc.close();
  }
}

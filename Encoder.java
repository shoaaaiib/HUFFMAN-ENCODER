import java.util.*;
import java.io.*;

class HuffmanNode {
    int item;
    char c;
    HuffmanNode left;
    HuffmanNode right;
}

// For comparing the nodes
class ImplementComparator implements Comparator<HuffmanNode> {
    public int compare(HuffmanNode x, HuffmanNode y) {
      return x.item - y.item;
    }
}

public class Encoder {

    static HashMap<Character, String> huffmanCodes = new HashMap<Character, String>();
    static HashMap<Character, Integer> charCountMap = new HashMap<Character, Integer>();

    public static void encode(String filePath){
        File file = new File(filePath);
        createCountMap(file);
        createHuffmanTree();
        for(Map.Entry<Character, Integer> m : charCountMap.entrySet()){    
            System.out.println(m.getKey()+" "+m.getValue());    
        }
        for(Map.Entry<Character, String> m : huffmanCodes.entrySet()){    
            System.out.println(m.getKey()+" "+m.getValue());    
        }
        createEncodedFile(file);
    }

    public static void createCountMap(File file){
        try{

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
      
            int c;
            while((c = br.read()) != -1){
      
              char ch = (char)c;
      
              if (charCountMap.containsKey(ch)) {
        
                // If char is present in charCountMap,
                // incrementing it's count by 1
                charCountMap.put(ch, charCountMap.get(ch) + 1);
              }
              else {
      
                // If char is not present in charCountMap,
                // putting this char to charCountMap with 1 as it's value
                charCountMap.put(ch, 1);
              }
            }
            br.close();
            fr.close();

      
        } catch(IOException e){System.out.println(e);}
    }

    public static void createHuffmanTree(){

        PriorityQueue<HuffmanNode> q = new PriorityQueue<HuffmanNode>(charCountMap.size(), new ImplementComparator());

        for (Map.Entry<Character, Integer> m : charCountMap.entrySet()) {

            HuffmanNode hn = new HuffmanNode();

            hn.c = m.getKey();
            hn.item = m.getValue();

            hn.left = null;
            hn.right = null;

            q.add(hn);
        }

        HuffmanNode root = null;

        while (q.size() > 1) {

            HuffmanNode x = q.peek();
            q.poll();

            HuffmanNode y = q.peek();
            q.poll();

            HuffmanNode f = new HuffmanNode();

            f.item = x.item + y.item;
            f.c = '~';
            f.left = x;
            f.right = y;
            root = f;

            q.add(f);
        }
        generateCode(root, "");
    }

    public static void generateCode(HuffmanNode root, String s) {
        if (root.left == null && root.right == null) {
            huffmanCodes.put(root.c, s);
            return;
        }
        generateCode(root.left, s + "0");
        generateCode(root.right, s + "1");
    }

    public static void createEncodedFile(File file){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("encoded.txt"));
            BufferedReader br = new BufferedReader(new FileReader(file));
            int i;
            while((i = br.read()) != -1){
                char c = (char)i;
                bw.write(huffmanCodes.get(c));
            }
            br.close();
            bw.close();
        } catch (IOException e){System.out.println(e);}
    }
}
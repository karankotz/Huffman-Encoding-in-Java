
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;



//the code for the heap functions and to create the nodes has been referred from the below links
//http://www.sanfoundry.com/java-program-implement-min-heap/
//https://stackoverflow.com/questions/19330731/tree-implementation-in-java-root-parents-and-children
//https://www.javascan.com/686/java-program-to-implement-min-heap

//Heap class to set the heap for the class heap
class Heap {
    private Node[] heap;
    private int size = 0;
    //Get the size of the heap
    public int getSize() {
        return size;
    }
    //Constructor for the heap which initializes the heap at first
    public Heap(int i){
        heap = new Node[i];
    }
    //insert the node in minHeap
    public void insert(Node k) {
        size++;
        int i = size;
        while(i > 1 && heap[parent(i)].getFreq() > k.getFreq()) {
            heap[i] = heap[parent(i)];
            i = parent(i);
        }
        heap[i] = k;
    }
    //Get the minimum of the node
    public Node getMin(){
        if(size != 0)
            return heap[1];
        return null;
    }
    //Delete the minimum node from the Heap
    public Node delMin() {
        if(size != 0) {
            Node min = heap[1];
            heap[1] = heap[size];
            size--;
            heapify(1);
            return min;
        }
        return null;
    }
    //Heapify method to set the property of the minheap and arrange the elements as per the minheap
    public void heapify(int i) {
        int l = left(i);
        int r = right(i);
        int smallest;
        if(r <= size) {
            if(heap[l].getFreq() < heap[r].getFreq())
                smallest = l;
            else
                smallest = r;
            if(heap[i].getFreq() > heap[smallest].getFreq()) {
                swap(i, smallest);
                heapify(smallest);
            }
        }
        else if(l == size && heap[i].getFreq() > heap[l].getFreq()) {
            swap(i, l);
        }
    }
    //method to swap the nodes
    private void swap(int i, int l)
    {
        Node tmp = heap[i];
        heap[i] = heap[l];
        heap[l] = tmp;
    }
    //method to return the parent position of the node in the heap
    public int parent(int i) {
        return i/2;
    }
    //method to return the left position of the node in the heap
    public int left(int i) {
        return 2*i;
    }
    //method to return the right position of the node in the heap
    public int right(int i) {
        return 2*i+1;
    }
}


//Class Node for each of the Node in the tree
//References :http://www.sanfoundry.com/java-program-implement-min-heap/
//References :https://stackoverflow.com/questions/19330731/tree-implementation-in-java-root-parents-and-children
//https://www.javascan.com/686/java-program-to-implement-min-heap
class Node {
    private Node parent;
    private Node left;
    private Node right;
    private int key = -1;
    private int freq = -1;
    private boolean isLeaf = false;
    int h_length;
    //Blank node constructor
    public Node() {
    }

    //Node constructor that takes the input as the key and the frequency
    public Node(int freq, int i) {
        this.key = i;
        this.freq = freq;
    }
    //returns the parent of the node
    public Node getParent() {
        return parent;
    }
    //sets the parent of the node
    public void setParent(Node parent) {
        this.parent = parent;
    }
    //Returns the left node of the node
    public Node getLeft() {
        return left;
    }
    //sets the left node of the node
    public void setLeft(Node left) {
        this.left = left;
    }
    //returns the right node of the node
    public Node getRight() {
        return right;
    }
    //sets the right node of the node
    public void setRight(Node right) {
        this.right = right;
    }
    //gets the key of the node
    public int getKey() {
        return key;
    }
    //Set the key
    public void setKey(int key) {
        this.key = key;
    }
    //set the frequency of the node
    public void setFreq(int freq) {
        this.freq = freq;
    }
    //get the frequency
    public int getFreq() {
        return freq;
    }
    //set the node as the leaf node
    public void setToLeaf() {
        isLeaf = true;
    }
    //check if the node is the leaf or not
    public boolean isLeaf() {
        return isLeaf;
    }
}


public class Main {

    //Variables of the count of the actual bits for 32 bit encoding
    int count;
    int original_count_of_bits;

    //Variables of the count of the actual bits for 64 bit encoding
    int count1;
    int original_count_of_bits1;

    ////Variables of the count of the actual bits for 128 bit encoding
    int count2;
    int original_count_of_bits2;



    //total_huffman variable for the calculation of the number of bits in the 32 bit encoding
    int total_huffman;
    //total_huffman2 for the calculation of the number of the bits for the 64 bit encoding
    int total_huffman2;
    //total_huffman2 for the calculation of the number of the bits for the 128 bit encoding
    int total_huffman3;
   //Each frequency array for the corresponding characters and the ascii table
    int[] freq = new int[256];
    int[] freq1 = new int[256];
    int[] freq2 = new int[256];

    //initailze the heap objects for each of the encodings
    Heap heap;
    Heap heap1;
    Heap heap2;
    //method to build the huffman tree considering the 32 characters
    public void build_32(String PATH)
    {

        try {
            //method to record the frequencies for the 32 characters
                calculatefrequency_32(PATH);
        }
        catch (FileNotFoundException ex) {
            System.out.println("File not found");
            System.exit(0);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        //instantiate heap with the max size of 256
        heap = new Heap(256);
        //create a minheap
        for(int i = 0; i < freq.length; ++i) {
            //set the frequency only if not equal to 0
            if(freq[i] != 0) {
                //instantiate the new node with the repsective frequency
                Node node = new Node(freq[i], i);
                //set to the leafnode
                node.setToLeaf();
                //insert the node in the heap
                heap.insert(node);
            }
        }

        heap = buildtree(heap);
        //method to generate the minimum of the node
        genCode(heap.getMin(), "");
        //print the code length, bits saved and other relevant information
        printtotallength();

    }

    public void build_64(String PATH)
    {
        try {
            //method to record the frequencies for the 64 characters
            calculatefrequency_64(PATH);

        }
        catch (FileNotFoundException ex) {
            System.out.println("File not found");
            System.exit(0);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        //intialize the heap with the max size of 256
        heap1 = new Heap(256);
        //build the min heap by storing the frequencies in each node
        for(int s = 0; s < freq1.length; ++s) {

            if(freq1[s] != 0)
            {
                Node node1 = new Node(freq1[s], s);
                node1.setToLeaf();
                heap1.insert(node1);
            }
        }
        //build the huffman tree based on the huffman algorithm
        heap1 = buildtree(heap1);
        //generate and print the huffman code for each character in the tree
        genCode(heap1.getMin(), "");
        //print the bits saved, and other relevant data
        printtotallength();


    }
    //Method to build huffman code considering all the 128 characters
    public void build_128(String PATH)
    {
        try {
            //calculate frequencies including all the 128 characters
            calculatefrequency_128(PATH);

        }
        //File not found
        catch (FileNotFoundException ex)
        {
            System.out.println("File not found");
            System.exit(0);
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        //initialize heap with the max size
        heap2 = new Heap(256);
        //build the min heap by storing the frequencies in each node
        for(int s = 0; s < freq2.length; ++s) {
            if(freq2[s] != 0)
            {
                //same detailed comments as above please refer build_32 method
                Node node2 = new Node(freq2[s], s);
                node2.setToLeaf();
                heap2.insert(node2);
            }
        }
        //Build the huffman tree and return the root
        heap2 = buildtree(heap2);
        //generate the huffman code for each of the node
        genCode(heap2.getMin(), "");
        //Print the bits saved and the other relevant information
        printtotallength();

    }

    private void calculatefrequency_32(String PATH)
            throws FileNotFoundException, IOException {
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        for (char ch = 'a'; ch <= 'z'; ++ch)
            map.put(Character.valueOf(ch), 0);

        map.put(' ', 0);
        map.put('.', 0);
        map.put(',', 0);
        map.put('!', 0);
        map.put('?', 0);
        map.put('\'', 0);
        //bufferred stream to read the characters from the file
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(PATH));
        //variables to record the bytes in the file and the count of the occurences of the characters
        int byt = 0;
        count = 0;
        //read each byte from the file and then store it in the frequency array iff the character is there in the hashmap
        //declared above
        while ((byt = bis.read()) != -1) {

            if (map.containsKey((char)byt))
            {
                freq[byt] = freq[byt] + 1;

                count++;
            }

        }

        original_count_of_bits = count *5;
        bis.close();

    }

    //Method that will also include more symbols i.e.. 64 characters to calculate the frequencies
    private void calculatefrequency_64(String PATH)
            throws FileNotFoundException, IOException {
        //Create a hashmap that will store the required characters that need to matched
        Map<Character, Integer> map1 = new HashMap<Character, Integer>();

        //Store the Lower case letters as well for the lower case letters

        for (char ch1 = 'a'; ch1 <= 'z'; ++ch1)
            map1.put(Character.valueOf(ch1), 0);

        //Store the Upper case letter as well to count for the 64 letters

        for (char ch1 = 'A'; ch1 <= 'Z'; ++ch1)
            map1.put(Character.valueOf(ch1), 0);

        //52 characters small plus Big and the special characters so the count is now 58
        map1.put(' ', 0);
        map1.put('.', 0);
        map1.put(',', 0);
        map1.put('!', 0);
        map1.put('?', 0);
        map1.put('\'', 0);
        //Now storing 8 numbers to make this 64

        for(int h=0; h<=7; h++)
        {
            map1.put((char)h, 0);
        }
       //File input stream object
        BufferedInputStream bis1 = new BufferedInputStream(new FileInputStream(PATH));
        //set the byte variable to read the bytes and count1 will record the count of the characters
        int byt1 = 0;
        count1 = 0;
        //read each byte from the file and then store it in the frequency array iff the character is there in the hashmap
        //declared above
        while ((byt1 = bis1.read()) != -1) {

            if (map1.containsKey((char)byt1))
            {
                //increament the frequency count
                freq1[byt1] = freq1[byt1] + 1;

                count1++;
            }

        }
      //System.out.println(count1 * 6);
        //calculate and record  the original bits
        original_count_of_bits1 = count1 * 6;
        bis1.close();

    }


    //This method calculates the frequeuncies considering all the 128 characters
    private void calculatefrequency_128(String PATH)
            throws FileNotFoundException, IOException
    {
        //Create a hashmap that will store the required characters that need to matched
        Map<Character, Integer> map2 = new HashMap<Character, Integer>();

        for (int m =0; m < 128; m++)
        {
            map2.put((char)m, 0);
            //System.out.println((char)m);
        }
        //File input stream object
        BufferedInputStream bis2 = new BufferedInputStream(new FileInputStream(PATH));

        int byt2 = 0;
        count2 = 0;
        //read the byte from the file and then store it in the frequency array iff the character is there in the hashmap
        //declared above
        while ((byt2 = bis2.read()) != -1)
        {

            if (map2.containsKey((char)byt2))
            {
                //increment the frequency count
                freq2[byt2] = freq2[byt2] + 1;

                count2++;
            }

        }
        //System.out.println(count2 * 7);
        //needed for the comparison as we need to compare with the 7 bit length character
        original_count_of_bits2 = count2 * 7;
        bis2.close();

    }

//Referred to study the algorithm : https://www.siggraph.org/education/materials/HyperGraph/video/mpeg/mpegfaq/huffman_tutorial.html
//Huffman tree which is built with the help of the Huffman algorithm
    private Heap buildtree(Heap heap)
    {
        //get the size of the heap
        int size = heap.getSize();
        //repeat untill size
        for(int i = 0; i < (size-1); ++i)
        {
            //initialize the new node
            Node z = new Node();
            //extract the first minimum and set it to the left of the node
            z.setLeft(heap.delMin());
            //extract the second minimum and ser it to the right of the node
            z.setRight(heap.delMin());
            //set the frequency of the node as the sum of the left and the right nodes
            z.setFreq(z.getLeft().getFreq() + z.getRight().getFreq());
            //Insert the node to the heap again
            heap.insert(z);
        }
        return heap;
    }
    //This method takes the root node as the input and recursively prints codes of the characters and prints the huffman code
    private void genCode(Node node, String code)
    {

        if(node != null) {

            if (node.isLeaf()) {

                if(choice == 1) {
                    total_huffman = total_huffman + freq[node.getKey()] * code.length();
                }
                if(choice == 2)
                {
                    total_huffman2 = total_huffman2 + freq1[node.getKey()] * code.length();
                }
                if(choice == 3)
                {
                    total_huffman3 = total_huffman3 + freq2[node.getKey()] * code.length();
                }
                //print the corresponding character and the code length of the character that is encoded using huffman code
                System.out.println("" + (char) node.getKey() + " " + " " + code + "" + " " + code.length() + "");
            }
            else{
               //Traverse recursively
                genCode(node.getLeft(), code + "0");
                genCode(node.getRight(), code + "1");
            }
        }

        }

        void printtotallength()
        {
            //Print results accordingly with respect to the input selected
            //For the 32 bit comparison
            if(choice == 1) {
                System.out.println("For 32 character comparison, the actual total number of the bits are "+ original_count_of_bits);
                System.out.println("Total Bits of all the characters in the Huffman Encoding: " + total_huffman);
                System.out.print("Total Bits Saved: ");
                System.out.println(original_count_of_bits - total_huffman);
            }
            //For 64 bit comparison
            if(choice == 2)
            {
                System.out.println("For 64 character comparison, the actual total number of the bits are "+ original_count_of_bits1);
                System.out.println("Total Bits of all the characters in the Huffman Encoding: " + total_huffman2);
                System.out.print("Total Bits Saved: ");
                System.out.println(original_count_of_bits1 - total_huffman2);
            }
            //For 128 bit comparison
            if(choice == 3)
            {
                System.out.println("For 128 character comparison, the actual total number of the bits are "+ original_count_of_bits2);
                System.out.println("Total Bits of all the characters in the Huffman Encoding: " + total_huffman3);
                System.out.print("Total Bits Saved: ");
                System.out.println(original_count_of_bits2 - total_huffman3);

            }
        }
   static int choice;
    public static void main(String[] args) {

        Main m1 = new Main();
        //Specify the path
        String PATH = "C:/Applied Algorithms/Bhagvadgita.txt";

        System.out.println("For 32 character encoding enter 1");
        System.out.println("For 64 character  encoding enter 2");
        System.out.println("For 128 bit encoding enter 3");
        System.out.println("To Exit enter  0");
        Scanner sc = new Scanner(System.in);
        while(((choice = sc.nextInt()) != 0))
        {
            //Huffman Code for 32 bit
           if(choice == 1) {
               m1.build_32(PATH);
           }
           else if (choice == 2)
           {
               //Huffman Code for the 64 Bit
               m1.build_64(PATH);
           }
           else if(choice == 3)
           {
               //Huffman code for the 128 bit
               m1.build_128(PATH);
           }
           else
           {
               System.out.println("Invalid Choice");
           }
        }

        System.out.println("Program Terminated");
    }
}

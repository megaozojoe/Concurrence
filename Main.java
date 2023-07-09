//code by Matthew del Real
import java.lang.Math;
import java.util.Arrays;

class concurrence implements Runnable{ //creating the concurrency object
  public int arraySize; //hods the array Size
  public int[] Arr;
  public long sum = 0; //Sum of the array half
  

  @Override
  public void run(){
    for(int j = 0; j < arraySize; j++){
      sum = sum + Arr[j]; //getting sum of the half array
    }
  }
}

class Main {

  
  //this is the run sequence for sequential
  public static void runSeq(int[] bigArr, int arrSize){
    long sum = 0; //long to avoid size issues
    for(int j = 0; j < arrSize; j++){
      sum = sum + bigArr[j]; //getting sum
    }
    System.out.println("Sequential Sum is " + sum);
    
  }
  
  public static void main(String[] args) {
    //setting up variables
    int size = 25000000;
    int min = 1;
    int max = 10;
    int range = max - min + 1;

    //creating integer array
    int[] arr = new int[size];
    int counter = 0;

    //fill the array with numbers
    while(counter < size){
      arr[counter] = (int)(Math.random()*range) + min;
      counter++;
    }

    
    //running and timing the sequential number
    long startTime = System.nanoTime();
    runSeq(arr,size);
    long endTime = System.nanoTime();
    long duration = (endTime - startTime);

    System.out.println("Sequential Time is " + duration  + " nanoseconds");


    //running concurrently

    //creating the objects
    concurrence c1 = new concurrence();
    concurrence c2 = new concurrence();

    //setting the objects up as half and half. Each then copyhalf the original array
    c1.arraySize = size / 2;
    c1.Arr = Arrays.copyOfRange(arr, 0, size/2);

    c2.arraySize = size / 2;
    c2.Arr = Arrays.copyOfRange(arr, size/2, size);

    //creating the threads
    Thread t1 = new Thread(c1);
    Thread t2 = new Thread(c2);

    long conStartTime = System.nanoTime();//second timing of execution
    //starting the threads
    t1.start();
    t2.start();

    //joinging the threads
    try{
      t1.join();
      t2.join();
    }catch(InterruptedException e){
      System.out.println(e);
    }

    //adding total of the halves
    long totalSum = c1.sum + c2.sum;
    
    long conEndTime = System.nanoTime();
    long conDuration = (conEndTime - conStartTime);//time of concurrent execution

    //printing results
    System.out.println("Concurrent Sum is " + totalSum);
    System.out.println("Concurrent Time is " + conDuration + " nanaoseconds");
    
    
  }
  
}
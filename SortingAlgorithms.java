import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.text.SimpleDateFormat;

/**
* This method creates a random dataset of integers in the range [-10000, 10000].
* Creates a list of integers of the specified size.
* where each integer is randomly generated in the range -10000 to 10000.
* This is necessary for testing sorting algorithms or other data processing tasks.
* @param size the number of integers to include in the dataset
* @return array an array of randomly generated integers in the range [-10000, 10000]
*/
public class SortingAlgorithms
{
    public static List<Integer> generateRandomDataset(int size)
    {
        List<Integer> array = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < size; i++)
        {
            array.add(rand.nextInt(20001) - 10000);
        }
        return array;
    }

/**
* This method calculates the execution times of various sorting algorithms.
* measures the time (in nanoseconds) required to sort a given list using five different sorting algorithms:
*Radix Sort, Shell Sort, Heap Sort, Insertion Sort, and Java's built-in Collections sort.
* The execution times for each sorting algorithm are stored in an array and returned.
* @param array of a list of integers to sort
* @return array of long values ​​where:
*/
    public static long[] calculateExecutionTime(List<Integer> array)
    {
        long[] executionTimes = new long[5];
        long startTime = 0;
        long endTime = 0;

        List<Integer> radixSortArray = new ArrayList<>(array);
        List<Integer> shellSortArray = new ArrayList<>(array);
        List<Integer> heapSortArray = new ArrayList<>(array);
        List<Integer> insertionSortArray = new ArrayList<>(array);
        List<Integer> javaSortArray = new ArrayList<>(array);

        startTime = System.nanoTime();
        radixSort(radixSortArray);
        endTime = System.nanoTime();
        executionTimes[0] = (endTime - startTime);

        startTime = System.nanoTime();
        shellSort(shellSortArray);
        endTime = System.nanoTime();
        executionTimes[1] = (endTime - startTime);


        startTime = System.nanoTime();
        heapSort(heapSortArray);
        endTime = System.nanoTime();
        executionTimes[2] = (endTime - startTime);


        startTime = System.nanoTime();
        insertionSort(insertionSortArray);
        endTime = System.nanoTime();
        executionTimes[3] = (endTime - startTime);


        startTime = System.nanoTime();
        javaCollectionSort(javaSortArray);
        endTime = System.nanoTime();
        executionTimes[4] = (endTime - startTime);

        return executionTimes;
    }

    /**
* This method displays the execution times of various sorting algorithms on the Console.
* Gets a range of execution times (in nanoseconds) and prints them to the console in a formatted form.
* Displays each time in milliseconds.
* With up to 5 decimal places.
* The times correspond to the following sorting algorithms;
* Index 0: Radix Sort
* Index 1: Shell Sort
* Index 2: Heap Sort
* Index 3: Insertion Sort
* Index 4: Java Collections.sort
* @param executionTimes array of execution times (in nanoseconds) for different sorting algorithms
*/

    public static void writeToConsole(long[] executionTimes)
    {
        System.out.println("Execution Times (ms):\n");
        System.out.printf("1 - Radix Sort: %10.5f ns\n", executionTimes[0] / 1_000_000.0);
        System.out.printf("2 - Shell Sort: %10.5f ns\n", executionTimes[1] / 1_000_000.0);
        System.out.printf("3 - Heap Sort: %10.5f ns\n", executionTimes[2] / 1_000_000.0);
        System.out.printf("4 - Insertion Sort: %10.5f ns\n", executionTimes[3] / 1_000_000.0);
        System.out.printf("5 - Java Collection Sort: %10.5f ns\n", executionTimes[4] / 1_000_000.0);
    }

/**
* This method writes a random array, a sorted array, and the execution times of the sorting algorithms to a file.
* This method creates a file in the specified directory.
* Writes a random array, a sorted version of that array (using Insertion Sort), and the execution times (in milliseconds) of various sorting algorithms.
* The filename is dynamically generated based on the size of the random array.
* @param filePath the directory in which to create the file.
* @param randomArray a list of randomly generated integers to write to the file.
* @param executionTimes an array of execution times (in nanoseconds) for different sorting algorithms
*/
    public static void writeToFile(String filePath, List<Integer> randomArray, long[] executionTimes)
    {
        String fileName = generateFileName(randomArray.size());
        String fullPath = filePath + "/" + fileName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath)))
        {
            writer.write("Random Array:\n");
            writer.write(randomArray.toString() + "\n\n");

            insertionSort(randomArray);

            writer.write("Sorted Array:\n");
            writer.write(randomArray.toString() + "\n\n");

            writer.write("Execution Times (ms):\n");
            writer.write(String.format("1 - Radix Sort: %10.5f ns\n", executionTimes[0] / 1_000_000.0));
            writer.write(String.format("2 - Shell Sort: %10.5f ns\n", executionTimes[1] / 1_000_000.0));
            writer.write(String.format("3 - Heap Sort: %10.5f ns\n", executionTimes[2] / 1_000_000.0));
            writer.write(String.format("4 - Insertion Sort: %10.5f ns\n", executionTimes[3] / 1_000_000.0));
            writer.write(String.format("5 - Java's Collection Sort: %10.5f ns\n", executionTimes[4] / 1_000_000.0));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        System.out.println("\nData has been written to " + filePath);
    }
/**
* This method creates a filename based on the size of the array and the current timestamp.
* The filename is created as "Array_Size_<arraySize>_Date_<timestamp>.txt".
* where the timestamp follows the pattern "yyyy-MM-dd_HH-mm-ss".
* @param arraySize the size of the array used in the filename
* @return a dynamically created filename containing the array size and the current timestamp
*/
    private static String generateFileName(int arraySize)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String date = dateFormat.format(new Date());
        return "Array_Size_" + arraySize + "_Date_" + date + ".txt";
    }

/**
* This method sorts a list of integers in descending order using Java's Collections.sort method.
* It uses a custom comparator to compare integers in reverse order (largest to smallest).
* The input list is modified in place.
* @param arr the list of integers to sort in descending order.
*/
    public static void javaCollectionSort(List<Integer> arr)
    {
        if (true) {
            Collections.sort(arr, new Comparator<Integer>()
            {
                @Override
                public int compare(Integer o1, Integer o2)
                {
                    return o2.compareTo(o1);
                }
            });
        }
    }

/**
* This method sorts a list of integers using the Radix Sort algorithm.
* This application processes both positive and negative integers by splitting them into two lists
*one for negatives and one for positives.
* Each list is sorted separately using the internal Radix Sort algorithm, and negatives are converted back to their original negative values ​​before being combined with positives in sorted order.
* @param list the list of integers to sort
*/
    public static void radixSort(List<Integer> list)
    {
        if (list == null || list.isEmpty())
        {
            return;
        }

        List<Integer> negatives = new ArrayList<>();
        List<Integer> positives = new ArrayList<>();

        for (int num : list)
        {
            if (num < 0)
            {
                negatives.add(-num);
            }
            else
            {
                positives.add(num);
            }
        }

        radixSortInternal(positives);
        radixSortInternal(negatives);

        Collections.reverse(negatives);
        for (int i = 0; i < negatives.size(); i++)
        {
            negatives.set(i, -negatives.get(i));
        }

        list.clear();
        list.addAll(negatives);
        list.addAll(positives);
    }

/**
* This method implements the internal Radix Sort logic for a list of non-negative integers.
* It uses the Radix Sort algorithm to sort the given list of integers by their individual digit positions, starting from the least significant digit and proceeding to the most significant digit.
* @param list the list of non-negative integers to be sorted
*/
    private static void radixSortInternal(List<Integer> list)
    {
        int max = getMax(list);
        for (int exp = 1; max / exp > 0; exp *= 10)
        {
            countingSort(list, exp);
        }
    }

/**
*This method sorts the list by the digits in the given positional value (exponent) using Counting Sort.
* It is used as a subroutine of the Radix Sort algorithm to sort integers by their individual digit positions, starting from the least significant digit.
* @param list list of non-negative integers to sort
* @param exp the positional value (exponent) representing the current digit to sort.
*/

    private static void countingSort(List<Integer> list, int exp)
    {
        int n = list.size();
        List<Integer> output = new ArrayList<>(Collections.nCopies(n, 0));
        int[] count = new int[10];

        for (int num : list)
        {
            int digit = (num / exp) % 10;
            count[digit]++;
        }

        for (int i = 1; i < 10; i++)
        {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--)
        {
            int num = list.get(i);
            int digit = (num / exp) % 10;
            output.set(count[digit] - 1, num);
            count[digit]--;
        }

        for (int i = 0; i < n; i++)
        {
            list.set(i, output.get(i));
        }
    }

/**
* This method Finds the maximum value in a list of Integers.
* Iterates through the list to determine the largest integer.
* Required for algorithms such as Radix Sort that require knowledge of the maximum value to determine the number of iterations.
* @param list list of integers
* @return maximum value in the list
*/
    private static int getMax(List<Integer> list)
    {
        int max = list.get(0);
        for (int num : list)
        {
            if (num > max)
            {
                max = num;
            }
        }
        return max;
    }

/**
* This method sorts a list of integers in ascending order using the Heap Sort algorithm.
* Converts the list to a max-heap and then pops the elements one by one, preserving the heap property
* Produces a sorted list.
* @param list the list of integers to sort
*/
    public static void heapSort(List<Integer> list)
    {
        int n = list.size();

        // Build heap (rearrange list)
        for (int i = n / 2 - 1; i >= 0; i--)
        {
            heapify(list, n, i);
        }

        // One by one extract an element from heap
        for (int i = n - 1; i > 0; i--)
        {
            // Move current root to end
            swap(list, 0, i);

            // Call max heapify on the reduced heap
            heapify(list, i, 0);
        }
    }
/**
* This method provides the heap property for a subtree rooted at the specified index.
* Assumes that binary trees are rooted at the left and right children of the specified node and already satisfy the heap property.
* Adjusts the subtree rooted at the specified node to preserve the maximum heap property.
* @param list list representing the heap
* @param n size of the heap
* @param i index of the root node of the subtree
*/
    private static void heapify(List<Integer> list, int n, int i)
    {
        int largest = i; // Initialize largest as root
        int left = 2 * i + 1; // left = 2*i + 1
        int right = 2 * i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (left < n && list.get(left) > list.get(largest))
        {
            largest = left;
        }

        // If right child is larger than largest so far
        if (right < n && list.get(right) > list.get(largest))
        {
            largest = right;
        }

        // If largest is not root
        if (largest != i)
        {
            swap(list, i, largest);

            // Recursively heapify the affected sub-tree
            heapify(list, n, largest);
        }
    }

/**
* This method Swaps two elements in a list at the specified indexes.
* Swaps the values ​​of elements at the given indexes in the given list.
* @param list the list of elements to swap
* @param a the index of the first element to swap
* @param b the index of the second element to swap
*/
    private static void swap(List<Integer> list, int a, int b)
    {
        int temp = list.get(a);
        list.set(a, list.get(b));
        list.set(b, temp);
    }
/**
* This method Sorts a list of integers in ascending order using the insertion sort algorithm.
* The insertion sort algorithm splits the list into sorted and unsorted parts.
* Iteratively selects an element from the unsorted part and places it in the correct position in the sorted part.
* @param list of integers to be sorted
*/
    public static void insertionSort(List<Integer> list)
    {
        for (int i = 1; i < list.size(); i++)
        {
            int key = list.get(i);
            int j = i - 1;

            while (j >= 0 && list.get(j) > key)
            {
                list.set(j + 1, list.get(j));
                j--;
            }

            list.set(j + 1, key);
        }
    }
/**
* This method sorts a list of integers in ascending order using the Shell sort algorithm.
* Shell sort is an insertion sort that allows swapping of elements that are very far apart.
* It starts by sorting pairs of elements that are very far apart and gradually reduces the space between the elements to be compared.
* @param list the list of integers to sort
*/
    public static void shellSort(List<Integer> list)
    {
        int n = list.size();

        for (int gap = n / 2; gap > 0; gap /= 2)
        {
            for (int i = gap; i < n; i++)
            {
                int temp = list.get(i);
                int j;

                for (j = i; j >= gap && list.get(j - gap) > temp; j -= gap)
                {
                    list.set(j, list.get(j - gap));
                }

                list.set(j, temp);
            }
        }
    }


}
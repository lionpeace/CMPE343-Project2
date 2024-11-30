import java.util.Arrays;
import java.util.Random; // to generate random numbers
import java.util.Comparator;

public class SortingAlgorithms {

    // radix sort (sorts the array by digits)
    public void radixSort(int[] array) {
    // find the largest number in the array 
    int max = array[0]; 
    for (int i = 1; i < array.length; i++) {
        if (array[i] > max) {
            max = array[i]; 
        }
    }

    for (int currentDigit = 1; max / currentDigit > 0; currentDigit *= 10) {
        countingSort(array, currentDigit); // based on the current digit
    }
}

private void countingSort(int[] array, int currentDigit) {
    int n = array.length;
    int[] output = new int[n]; // to store sorted values
    int[] cnt = new int[10];  

    // calculate the current number for each element
    for (int i = 0; i < n; i++) {
        cnt[(array[i] / currentDigit) % 10]++; // increase the count for the digit at the current place
    }

    // update the count array 
    for (int i = 1; i < 10; i++) {
        cnt[i] += cnt[i - 1]; //  cnt[i] value gives the position of the digit in the output
    }

    // output array to use count array by iterating in reverse order
    for (int i = n - 1; i >= 0; i--) {
        int digit = (array[i] / currentDigit) % 10; // get the current digit
        output[cnt[digit] - 1] = array[i]; // correct position
        cnt[digit]--; 
    }

    // copy the sorted values 
    System.arraycopy(output, 0, array, 0, n);
}

    // Shell Sort
    public void shellSort(int[] array) {
        int l = array.length;
        for (int gap = l / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < l; i++) {
                int temp = array[i];
                int j;
                for (j = i; j >= gap && array[j - gap] > temp; j -= gap) {
                    array[j] = array[j - gap];
                }
                array[j] = temp;
            }
        }
    }

    // Heap Sort
    public void heapSort(int[] array) {
        int l = array.length;

        // build max-heap
        for (int i = l / 2 - 1; i >= 0; i--) { 
            rebuildHeap(array, l, i); // ensure max-heap property
        }

        for (int i = l - 1; i > 0; i--) {
            // swap the root
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            rebuildHeap(array, i, 0); // restore heap property for remaining elements
        }
    }

    private void rebuildHeap(int[] array, int length, int i) {
        while (true) {
        int largest = i; // root
        int left = 2 * i + 1; // left child
        int right = 2 * i + 2; // right child

        if (left < length && array[left] > array[largest]) {
            largest = left;
        }

        if (right < length && array[right] > array[largest]) {
            largest = right;
        }

       // if the root is the largest stop the loop
        if (largest == i) {
            break;
        }

        // swap the root with the largest child
        int swap = array[i];
        array[i] = array[largest];
        array[largest] = swap;

        // move to the affected subtree
        i = largest; // update i 
    }
}

    // Insertion Sort
    public void insertionSort(int[] arr) {
        int l = arr.length;
        for (int i = 1; i < l; ++i) {
            int curr = arr[i]; // store the current element
            int j = i - 1;

             // shift elements
            while (j >= 0 && arr[j] > curr) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = curr; //correct position
        }
    }

    // test method
    public void testSortingAlgorithms() {
        Random rand = new Random();
        int n = 1000; // sample 
        int[] originalArray = rand.ints(n, -10000, 10000).toArray();

        int[] array1 = Arrays.copyOf(originalArray, originalArray.length);
        int[] array2 = Arrays.copyOf(originalArray, originalArray.length);
        int[] array3 = Arrays.copyOf(originalArray, originalArray.length);
        int[] array4 = Arrays.copyOf(originalArray, originalArray.length);

        long startTime, endTime;

        System.out.println("Sorting Test:");

        // Radix Sort
        startTime = System.nanoTime();
        radixSort(array1);
        endTime = System.nanoTime();
        System.out.println("Radix Sort: " + (endTime - startTime) + " ns");

        // Shell Sort
        startTime = System.nanoTime();
        shellSort(array2);
        endTime = System.nanoTime();
        System.out.println("Shell Sort: " + (endTime - startTime) + " ns");

        // Heap Sort
        startTime = System.nanoTime();
        heapSort(array3);
        endTime = System.nanoTime();
        System.out.println("Heap Sort: " + (endTime - startTime) + " ns");

        // Insertion Sort
        startTime = System.nanoTime();
        insertionSort(array4);
        endTime = System.nanoTime();
        System.out.println("Insertion Sort: " + (endTime - startTime) + " ns");
    }

    public static void main(String[] args) {
        SortingAlgorithms sorter = new SortingAlgorithms();
        sorter.testSortingAlgorithms();
    }
}

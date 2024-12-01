import java.util.*;
import java.io.*;

public class SortingAlgorithms {

    // Generate a random dataset between -10,000 and 10,000
    public static List<Integer> generateRandomDataset(int size) {
        List<Integer> dataset = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < size; i++) {
            dataset.add(rand.nextInt(20001) - 10000); // Random numbers between -10,000 and 10,000
        }

        return dataset;
    }

    // Write the dataset to a file
    public static void writeDatasetToFile(List<Integer> dataset, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Integer num : dataset) {
                writer.write(num.toString());
                writer.newLine(); // Write each number to a new line
            }
            System.out.println("Dataset successfully written to '" + filename + "'.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask the manager for dataset size
        System.out.println("Please enter the dataset size (between 1000 and 10000):");
        int datasetSize = scanner.nextInt();

        // Validate dataset size
        if (datasetSize < 1000 || datasetSize > 10000) {
            System.out.println("Invalid dataset size! Please enter a value between 1000 and 10000.");
            return;
        }

        // Generate the dataset
        List<Integer> dataset = generateRandomDataset(datasetSize);

        // Write the dataset to a file
        writeDatasetToFile(dataset, "random_array.txt");

        // Run sorting algorithms
        runSortingAlgorithms(dataset);

        // Ask the user if they want to return to the Manager menu
        System.out.println("Sorting completed. Press 'y' to return to the Manager menu.");
        scanner.nextLine(); // Consume the remaining newline
        String input = scanner.nextLine();
        if (input.equalsIgnoreCase("y")) {
            System.out.println("Returning to the Manager menu...");
            // Here, you can implement returning to the main menu or further actions
        }
    }

    // Run the sorting algorithms and compare the results
    public static void runSortingAlgorithms(List<Integer> dataset) {
        System.out.println("Running sorting algorithms...");

        // 1. Radix Sort
        List<Integer> radixSorted = new ArrayList<>(dataset);
        long radixTime = measureExecutionTime(() -> radixSort(radixSorted));
        System.out.println("Radix Sort time: " + radixTime + " ms");

        // 2. Shell Sort
        List<Integer> shellSorted = new ArrayList<>(dataset);
        long shellTime = measureExecutionTime(() -> shellSort(shellSorted));
        System.out.println("Shell Sort time: " + shellTime + " ms");

        // 3. Heap Sort
        List<Integer> heapSorted = new ArrayList<>(dataset);
        long heapTime = measureExecutionTime(() -> heapSort(heapSorted));
        System.out.println("Heap Sort time: " + heapTime + " ms");

        // 4. Insertion Sort
        List<Integer> insertionSorted = new ArrayList<>(dataset);
        long insertionTime = measureExecutionTime(() -> insertionSort(insertionSorted));
        System.out.println("Insertion Sort time: " + insertionTime + " ms");

        // 5. Validation: Compare with Java's Collections.sort()
        List<Integer> collectionsSorted = new ArrayList<>(dataset);
        Collections.sort(collectionsSorted);

        if (radixSorted.equals(collectionsSorted) &&
                shellSorted.equals(collectionsSorted) &&
                heapSorted.equals(collectionsSorted) &&
                insertionSorted.equals(collectionsSorted)) {
            System.out.println("All sorting algorithms are correct!");
        } else {
            System.out.println("There are some sorting errors!");
        }
    }

    // Measure execution time of a task
    private static long measureExecutionTime(Runnable task) {
        long startTime = System.currentTimeMillis();
        task.run();
        return System.currentTimeMillis() - startTime;
    }

    // Radix Sort implementation
    public static void radixSort(List<Integer> list) {
        // Separate negative and positive numbers
        List<Integer> negativeNumbers = new ArrayList<>();
        List<Integer> positiveNumbers = new ArrayList<>();

        for (int num : list) {
            if (num < 0) {
                negativeNumbers.add(-num); // Convert negative to positive for sorting
            } else {
                positiveNumbers.add(num);
            }
        }

        // Radix Sort for positive numbers
        if (!positiveNumbers.isEmpty()) {
            radixSortHelper(positiveNumbers);
        }

        // Radix Sort for negative numbers
        if (!negativeNumbers.isEmpty()) {
            radixSortHelper(negativeNumbers);
            Collections.reverse(negativeNumbers); // Reverse order to get correct order for negative numbers
        }

        // Merge the negative and positive numbers
        list.clear();
        for (int num : negativeNumbers) {
            list.add(-num); // Restore the negative values
        }
        list.addAll(positiveNumbers);
    }

    private static void radixSortHelper(List<Integer> list) {
        int max = Collections.max(list);
        int exp = 1;
        while (max / exp > 0) {
            countingSortByDigit(list, exp);
            exp *= 10;
        }
    }

    private static void countingSortByDigit(List<Integer> list, int exp) {
        int n = list.size();
        List<Integer> output = new ArrayList<>(Collections.nCopies(n, 0));
        int[] count = new int[10]; // Digits: 0-9

        // Count the occurrences of each digit
        for (int i = 0; i < n; i++) {
            count[(list.get(i) / exp) % 10]++;
        }

        // Cumulative count
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // Place the numbers in the correct position in the output list
        for (int i = n - 1; i >= 0; i--) {
            int digit = (list.get(i) / exp) % 10;
            output.set(count[digit] - 1, list.get(i));
            count[digit]--;
        }

        // Copy the sorted output back to the original list
        for (int i = 0; i < n; i++) {
            list.set(i, output.get(i));
        }
    }

    // Shell Sort implementation
    public static void shellSort(List<Integer> list) {
        int n = list.size();
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = list.get(i);
                int j = i;
                while (j >= gap && list.get(j - gap) > temp) {
                    list.set(j, list.get(j - gap));
                    j -= gap;
                }
                list.set(j, temp);
            }
        }
    }

    // Heap Sort implementation
    public static void heapSort(List<Integer> list) {
        int n = list.size();

        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(list, n, i);
        }

        // Extract elements one by one
        for (int i = n - 1; i > 0; i--) {
            Collections.swap(list, 0, i);
            heapify(list, i, 0);
        }
    }

    private static void heapify(List<Integer> list, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && list.get(left) > list.get(largest)) {
            largest = left;
        }

        if (right < n && list.get(right) > list.get(largest)) {
            largest = right;
        }

        if (largest != i) {
            Collections.swap(list, i, largest);
            heapify(list, n, largest);
        }
    }

    // Insertion Sort implementation
    public static void insertionSort(List<Integer> list) {
        int n = list.size();
        for (int i = 1; i < n; i++) {
            int key = list.get(i);
            int j = i - 1;
            while (j >= 0 && list.get(j) > key) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }
}

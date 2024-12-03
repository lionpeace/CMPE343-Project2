import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.text.SimpleDateFormat;

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

    public static void writeToConsole(long[] executionTimes)
    {
        System.out.println("Execution Times (ms):\n");
        System.out.printf("1 - Radix Sort: %10.5f ns\n", executionTimes[0] / 1_000_000.0);
        System.out.printf("2 - Shell Sort: %10.5f ns\n", executionTimes[1] / 1_000_000.0);
        System.out.printf("3 - Heap Sort: %10.5f ns\n", executionTimes[2] / 1_000_000.0);
        System.out.printf("4 - Insertion Sort: %10.5f ns\n", executionTimes[3] / 1_000_000.0);
        System.out.printf("5 - Java Collection Sort: %10.5f ns\n", executionTimes[4] / 1_000_000.0);
    }

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

    private static String generateFileName(int arraySize)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String date = dateFormat.format(new Date());
        return "Array_Size_" + arraySize + "_Date_" + date + ".txt";
    }


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

    private static void radixSortInternal(List<Integer> list)
    {
        int max = getMax(list);
        for (int exp = 1; max / exp > 0; exp *= 10)
        {
            countingSort(list, exp);
        }
    }

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

    private static void swap(List<Integer> list, int a, int b)
    {
        int temp = list.get(a);
        list.set(a, list.get(b));
        list.set(b, temp);
    }

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
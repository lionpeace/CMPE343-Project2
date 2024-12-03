import java.util.Arrays;

public class SortingAlgorithms {

    // Radix Sort
    public static void radixSort(int[] array) {
        if (array == null || array.length == 0) return;

        // Negatif ve pozitifleri ayır
        int[] positives = Arrays.stream(array).filter(x -> x >= 0).toArray();
        int[] negatives = Arrays.stream(array).filter(x -> x < 0).map(Math::abs).toArray();

        // Pozitifleri sırala
        radixSortInternal(positives);

        // Negatifleri sırala (ters çevrilecek şekilde)
        radixSortInternal(negatives);
        for (int i = 0; i < negatives.length / 2; i++) {
            int temp = negatives[i];
            negatives[i] = negatives[negatives.length - 1 - i];
            negatives[negatives.length - 1 - i] = temp;
        }
        for (int i = 0; i < negatives.length; i++) {
            negatives[i] = -negatives[i];
        }

        // Negatifleri ve pozitifleri birleştir
        System.arraycopy(negatives, 0, array, 0, negatives.length);
        System.arraycopy(positives, 0, array, negatives.length, positives.length);
    }

    private static void radixSortInternal(int[] array) {
        int n = array.length;
        int max = Arrays.stream(array).max().orElse(0);

        for (int exp = 1; max / exp > 0; exp *= 10) {
            countSort(array, exp);
        }
    }

    private static void countSort(int[] array, int exp) {
        int n = array.length;
        int[] output = new int[n];
        int[] count = new int[10];

        // Sayımları yap
        for (int i = 0; i < n; i++) {
            count[(array[i] / exp) % 10]++;
        }

        // Birikimli toplam oluştur
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        // Sonuç dizisini oluştur
        for (int i = n - 1; i >= 0; i--) {
            int digit = (array[i] / exp) % 10;
            output[count[digit] - 1] = array[i];
            count[digit]--;
        }

        // Orijinal diziye geri kopyala
        System.arraycopy(output, 0, array, 0, n);
    }

    // Shell Sort
    public static void shellSort(int[] array) {
        for (int gap = array.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < array.length; i++) {
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
    public static void heapSort(int[] array) {
        int n = array.length;

        // Diziyi heap'e dönüştür
        for (int i = n / 2 - 1; i >= 0; i--) heapify(array, n, i);

        // Elemanları heap'ten çıkar
        for (int i = n - 1; i > 0; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            heapify(array, i, 0);
        }
    }

    private static void heapify(int[] array, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && array[left] > array[largest]) largest = left;
        if (right < n && array[right] > array[largest]) largest = right;

        if (largest != i) {
            int swap = array[i];
            array[i] = array[largest];
            array[largest] = swap;

            heapify(array, n, largest);
        }
    }

    // Insertion Sort
    public static void insertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;

            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }
}

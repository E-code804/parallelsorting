import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * This class provides a standard and parallel implementation of
 * the Quick Sort algorithm. The parallelization of Quick Sort uses the
 * Fork-Join framework in Java.
 */
public class QuickSort {
    /**
     * Sorts an array using the Quick Sort algorithm.
     *
     * @param arr  The array to be sorted.
     * @param low  The starting index of the portion to be sorted.
     * @param high The ending index of the portion to be sorted.
     */
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int partIndex = partition(arr, low, high);
            quickSort(arr, low, partIndex - 1);
            quickSort(arr, partIndex + 1, high);
        }
    }

    /**
     * Partitions the array for Quick Sort.
     *
     * @param arr  The array to be partitioned.
     * @param low  The starting index of the portion to be partitioned.
     * @param high The ending index of the portion to be partitioned.
     * @return The index of the pivot element after partitioning.
     */
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        i++;
        swap(arr, i, high);
        return i;
    }

    /**
     * Swaps two elements in the array.
     *
     * @param arr The array in which elements are swapped.
     * @param i   The index of the first element to be swapped.
     * @param j   The index of the second element to be swapped.
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Sorts an array using parallel quick sort.
     *
     * @param arr The array to be sorted.
     */
    public static void parallelQuickSort(int[] arr) {
        SortTask mainTask = new SortTask(arr, 0, arr.length - 1);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(mainTask);
    }

    /**
     * Represents a recursive task for parallelizing Quick Sort using the Fork-Join
     * framework.
     */
    private static class SortTask extends RecursiveAction {
        private int[] arr;
        private int low, high;

        /**
         * Constructs a SortTask for a specific portion of an array.
         *
         * @param arr  The array to be sorted.
         * @param low  The starting index of the portion to be sorted.
         * @param high The ending index of the portion to be sorted.
         */
        public SortTask(int[] arr, int low, int high) {
            this.arr = arr;
            this.low = low;
            this.high = high;
        }

        /**
         * Performs the computation of sorting the portion of the array.
         */
        @Override
        protected void compute() {
            if (low < high) {
                int partIndex = QuickSort.partition(arr, low, high);
                SortTask leftSortTask = new SortTask(arr, low, partIndex - 1);
                SortTask righSortTask = new SortTask(arr, partIndex + 1, high);
                invokeAll(leftSortTask, righSortTask);
            }
        }
    }
}

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * This class provides a standard and parallel implementation of
 * the Merge Sort algorithm. The parallelization of Merge Sort uses the
 * Fork-Join framework in Java.
 */
public class MergeSort {
    /**
     * Sorts the given array using the merge sort algorithm.
     *
     * @param arr   the array to be sorted
     * @param left  the left index of the subarray to be sorted
     * @param right the right index of the subarray to be sorted
     */
    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, right, mid);
        }
    }

    /**
     * Merges two subarrays of the array.
     *
     * @param arr   the array to be merged
     * @param left  the left index of the first subarray
     * @param right the right index of the second subarray
     * @param mid   the middle index, separating the two subarrays
     */
    private static void merge(int[] arr, int left, int right, int mid) {
        // Find sizes of two subarrays to be merged
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // Create temp arrays
        int L[] = new int[n1];
        int R[] = new int[n2];

        // Copy data to temp arrays
        for (int i = 0; i < n1; ++i) {
            L[i] = arr[left + i];
        }
        for (int j = 0; j < n2; ++j) {
            R[j] = arr[mid + 1 + j];
        }

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        // Copy remaining elements of L[] if any
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        // Copy remaining elements of R[] if any
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    /**
     * Sorts an array using parallel merge sort.
     *
     * @param arr The array to be sorted.
     */
    public static void parallelMergeSort(int[] arr) {
        SortTask mainTask = new SortTask(arr, 0, arr.length - 1);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(mainTask);
    }

    /**
     * A recursive task representing a portion of the array to be sorted in
     * parallel.
     */
    private static class SortTask extends RecursiveAction {
        private int[] arr;
        private int left, right;

        /**
         * Constructs a SortTask for a specific portion of an array.
         *
         * @param arr   The array to be sorted.
         * @param left  The left index of the portion to be sorted.
         * @param right The right index of the portion to be sorted.
         */
        public SortTask(int[] arr, int left, int right) {
            this.arr = arr;
            this.left = left;
            this.right = right;
        }

        /**
         * Performs the computation of sorting the portion of the array.
         */
        @Override
        protected void compute() {
            if (left < right) {
                int mid = (left + right) / 2;
                SortTask leftArrTask = new SortTask(arr, left, mid);
                SortTask rightArrTask = new SortTask(arr, mid + 1, right);
                // Invoke declared tasks
                invokeAll(leftArrTask, rightArrTask);
                MergeSort.merge(arr, left, right, mid);
            }
        }
    }
}
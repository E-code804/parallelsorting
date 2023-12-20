import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;

/**
 * This Driver class asks users which divide-and-conquer algorithm they want to
 * time in their standard vs. parallelized implementations. Arrays.sort is also
 * available to test.
 */
public class Driver {
    /**
     * Times the amount it takes to sort in array using standard Merge Sort in
     * milliseconds.
     *
     * @param arr the array to be sorted
     */
    static void testMergeSort(int[] arr) {
        long startTime = System.currentTimeMillis();
        MergeSort.mergeSort(arr, 0, arr.length - 1);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Total time for Merge Sort: " + elapsedTime + "ms.");
    }

    /**
     * Times the amount it takes to sort in array using parallel Merge Sort in
     * milliseconds.
     *
     * @param arr the array to be sorted
     */
    static void testParallelMergeSort(int[] arr) {
        long startTime = System.currentTimeMillis();
        MergeSort.parallelMergeSort(arr);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Total time for Parallel Merge Sort: " + elapsedTime + "ms.");
    }

    /**
     * Times the amount it takes to sort in array using standard Quick Sort in
     * milliseconds.
     *
     * @param arr the array to be sorted
     */
    static void testQuickSort(int[] arr) {
        long startTime = System.currentTimeMillis();
        QuickSort.quickSort(arr, 0, arr.length - 1);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Total time for Quick Sort: " + elapsedTime + "ms.");
    }

    /**
     * Times the amount it takes to sort in array using parallel Quick Sort in
     * milliseconds.
     *
     * @param arr the array to be sorted
     */
    static void testParallelQuickSort(int[] arr) {
        long startTime = System.currentTimeMillis();
        QuickSort.parallelQuickSort(arr);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Total time for Parallel Quick Sort: " + elapsedTime + "ms.");
    }

    /**
     * Times the amount it takes to sort in array using Arrays.sort in milliseconds.
     *
     * @param arr the array to be sorted
     */
    static void testArraysSort(int[] arr) {
        long startTime = System.currentTimeMillis();
        Arrays.sort(arr);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Total time for Arrays.sort: " + elapsedTime + "ms.");
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java MergeSort <array_size>");
            return;
        }

        int size = Integer.parseInt(args[0]);
        int[] arr = new int[size];
        int[] arr2 = new int[size];
        Random rand = new Random();

        for (int i = 0; i < size; i++) {
            arr[i] = arr2[i] = rand.nextInt(size);
        }

        System.out.println("Select a sorting algorithm:");
        System.out.println("1. QuickSort (qs)");
        System.out.println("2. MergeSort (ms)");
        System.out.println("3. Arrays.sort (as)");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.next();

        // Printing arr before being sorted
        // System.out.println(Arrays.toString(arr));
        // System.out.println(Arrays.toString(arr2));

        switch (choice) {
            case "qs":
                // Testing timing for QS and Parallel QS.
                testQuickSort(arr2);
                testParallelQuickSort(arr);
                break;
            case "ms":
                // Testing timing for MS and Parallel MS
                testMergeSort(arr2);
                testParallelMergeSort(arr);
                break;
            case "as":
                // Testing timing for Arrays.sort
                testArraysSort(arr);
                break;
            default:
                System.out.println("You did not select as proper option, please try again.");
                break;
        }
        scanner.close();

        // Printing arr after being sorted
        // System.out.println(Arrays.toString(arr));
        // System.out.println(Arrays.toString(arr2));
    }
}

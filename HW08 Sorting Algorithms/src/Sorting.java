import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.PriorityQueue;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Hieu Nguyen
 * @version 1.0
 * @userid pnguyen337
 * @GTID 903681705
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Null array or comparator is not acceptable "
                    + "for insertion sort.");
        }

        for (int i = 1; i < arr.length; i++) {
            int j = i;
            while (j > 0 && comparator.compare(arr[j - 1], arr[j]) > 0) {
                T temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
                j--;
            }
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Null array or comparator is not acceptable "
                    + "for cocktail sort.");
        }

        int length = arr.length;
        int lastSwappedFront = 0;
        int lastSwappedBack = arr.length - 1;

        //In case array is empty or only has one element
        if (length == 0 || length == 1) {
            return;
        }

        while (lastSwappedFront < lastSwappedBack) {
            int lastSwapped = lastSwappedFront;

            for (int i = lastSwappedFront; i < lastSwappedBack; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    swap(arr, i + 1, i);
                    lastSwapped = i;
                }
            }
            lastSwappedBack = lastSwapped;

            for (int i = lastSwappedBack; i > lastSwappedFront; i--) {
                if (comparator.compare(arr[i], arr[i - 1]) < 0) {
                    swap(arr, i - 1, i);
                    lastSwapped = i;
                }
            }
            lastSwappedFront = lastSwapped;
        }
    }

    /**
     * Helper method for swapping two data in an array.
     *
     * @param arr the array containing the data
     * @param i the first data
     * @param j the second data
     * @param <T> data type to swap
     */
    private static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Null array or comparator is not acceptable "
                    + "for merge sort.");
        }

        int length = arr.length;

        //In case array is empty or only has one element
        if (length == 0 || length == 1) {
            return;
        }

        int middle = length / 2;

        T[] left = (T[]) new Object[middle];
        T[] right = (T[]) new Object[length - middle];

        for (int i = 0; i < middle; i++) {
            left[i] = arr[i];
        }
        for (int i = middle; i < length; i++) {
            right[i - middle] = arr[i];
        }

        mergeSort(left, comparator);
        mergeSort(right, comparator);

        merging(arr, left, right, comparator);
    }


    /**
     * Merge helper method for merging sorted elements together.
     *
     * @param arr the array to be sorted
     * @param left left index of subarray
     * @param right right/center index of subarray
     * @param comparator the Comparator used to compare the data in arr
     * @param <T> data type to sort
     */
    private static <T> void merging(T[] arr, T[] left, T[] right, Comparator<T> comparator) {
        int lIndex = 0;
        int rIndex = 0;
        int arrIndex = 0;

        while (lIndex < left.length && rIndex < right.length) {

            if (comparator.compare(left[lIndex], right[rIndex]) <= 0) {
                arr[arrIndex] = left[lIndex];
                lIndex++;

            } else {
                arr[arrIndex] = right[rIndex];
                rIndex++;

            }
            arrIndex++;

        }

        while (lIndex < left.length) {
            arr[arrIndex] = left[lIndex];
            lIndex++;
            arrIndex++;

        }

        while (rIndex < right.length) {
            arr[arrIndex] = right[rIndex];
            rIndex++;
            arrIndex++;

        }
    }


    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Null array, rand, or comparator is not acceptable "
                    + "for quick sort.");
        }
        quickHelper(arr, comparator, rand, 0, arr.length - 1);
    }

    /**
     * Quick helper method for quick sorting recursively.
     *
     * @param arr the array that needs to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @param first first index of array
     * @param last last index of array
     * @param <T> data type to sort
     */
    private static <T> void quickHelper(T[] arr, Comparator<T> comparator, Random rand, int first, int last) {
        if (last > first) {
            int pivot = partition(arr, comparator, rand, first, last);
            quickHelper(arr, comparator, rand, first, pivot - 1);
            quickHelper(arr, comparator, rand, pivot + 1, last);
        }
    }

    /**
     * Quick helper method for determining pivot, partitioning the array, and sorting it.
     *
     * @param arr the array that needs to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @param first first/starting index of array
     * @param last last/ending index of array
     * @return the pivot index position
     * @param <T> data type to sort
     */
    private static <T> int partition(T[] arr, Comparator<T> comparator, Random rand, int first, int last) {
        int pivotIdx = rand.nextInt(last - first + 1) + first;
        T pivotValue = arr[pivotIdx];

        swap(arr, first, pivotIdx);

        int start = first + 1;
        int end = last;
        while (start <= end) {
            while (start <= end && comparator.compare(arr[start], pivotValue) <= 0) {
                start++;
            }
            while (start <= end && comparator.compare(arr[end], pivotValue) >= 0) {
                end--;
            }
            if (start <= end) {
                swap(arr, start, end);
                start++;
                end--;
            }
        }
        swap(arr, first, end);
        return end;
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Null array is not acceptable for radix sort.");
        }

        //In case array is empty
        if (arr.length == 0) {
            return;
        }

        int max = findMax(arr, arr.length);
        int digits = countDigits(max);

        int power = 1;
        for (int i = 1; i <= digits - 1; i++) {
            power *= 10;
        }
        for (int divisor = 1; divisor <= power; divisor *= 10) {
            distribute(arr, divisor);
        }
    }

    /**
     * LSD Radix helper method to find the max value element to determine total digits for
     * numbers of iterations.
     *
     * @param arr the array that needs to be sorted
     * @param length the length of array
     * @return the max value element of an array
     */
    private static int findMax(int[] arr, int length) {
        int max = arr[0];
        for (int i = 1; i < length; i++) {
            if (arr[i] == Integer.MAX_VALUE || arr[i] == Integer.MIN_VALUE) {
                max = Integer.MAX_VALUE;
            } else if (Math.abs(arr[i]) > max) {
                max = Math.abs(arr[i]);
            }
        }
        return max;
    }

    /**
     * LSD Radix helper method to count total digits in the max value element.
     *
     * @param num the max value element
     * @return the amount of digits from the element
     */
    private static int countDigits(int num) {
        int count = 0;
        while (num > 0) {
            num /= 10;
            count++;
        }
        return count;
    }

    /**
     * LSD Radix helper method to distribute elements in buckets and sort them.
     *
     * @param arr the array that needs to be sorted
     * @param divisor the divisor for sorting in base of 10
     */
    private static void distribute(int[] arr, int divisor) {
        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<>();
        }

        for (int num : arr) {
            int bucketIndex = (num / divisor) % 10 + 9;
            buckets[bucketIndex].add(num);
        }

        int index = 0;
        for (LinkedList<Integer> bucket : buckets) {
            for (int num : bucket) {
                arr[index++] = num;
            }
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data is not acceptable for heap sort.");
        }
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(data);
        int[] sortedArr = new int[data.size()];

        for (int i = 0; i < sortedArr.length; i++) {
            sortedArr[i] = minHeap.remove();
        }

        return sortedArr;
    }
}

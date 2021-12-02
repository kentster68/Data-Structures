import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Kent Barber
 * @version 1.0
 * @userid kbarber9
 * @GTID 903326160
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: Old Sorting algorithms I have written
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
            throw new IllegalArgumentException("Can not deal with null data.");
        }

        for (int i = 1; i < arr.length; i++) {
            int j = i;
            while (j > 0 && comparator.compare(arr[j - 1], arr[j]) > 0) {
                T temp = arr[j - 1];
                arr[j - 1] = arr[j];
                arr[j] = temp;

                j--;
            }
        }
    }

    /**
     * public static void insertionSort(int[] arr, comp) {
     * for (int i = 1; i < arr.length; i++) {
     *   int j = i;
     *   while (j > 0 && comp.compare(arr[j-1], arr[j] ) > 0 ){
     *     T temp = arr[j-1];
     *     arr[j-1] = arr[j];
     *     arr[j] = temp 
     *      j--;
     *   }
     * }
     */

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
            throw new IllegalArgumentException("Can not deal with null data");
        } else {
            boolean swapsMade = true;
            int begin = 0;
            int end = arr.length - 1;


            int swapAfter = end - 1;
            int swapBefore = begin + 1;

            while (swapsMade) {
                swapsMade = false;
                for (int i = begin; i < end; i++) {
                    if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                        T temp = arr[i];
                        arr[i] = arr[i + 1];
                        arr[i + 1] = temp;
                        if (!swapsMade) {
                            swapsMade = true;
                        }
                        swapAfter = i;
                    }
                }
                if (!swapsMade) {
                    break;
                }
                swapsMade = false;
                end = swapAfter;

                for (int i = end; i > begin; i--) {
                    if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                        T temp = arr[i];
                        arr[i] = arr[i - 1];
                        arr[i - 1] = temp;
                        if (!swapsMade) {
                            swapsMade = true;
                        }
                        swapBefore = i;
                    }
                }
                begin = swapBefore;
                if (!swapsMade) {
                    break;
                }
            }
        }
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
            throw new IllegalArgumentException("Can not handle null data.");
        } else {
            mergeSortHelper(arr, comparator);
        }
    }

    /**
     * Private helper method in order to do a merge sort
     * @param arr
     * @param comparator
     * @param <T>
     */
    private static <T> void mergeSortHelper(T[] arr, Comparator<T> comparator) {
        if (arr.length > 1) {
            int length = arr.length;
            int middle = length / 2;
            int lengthLeft = middle;
            int lengthRight = length - lengthLeft;
            T[] leftArr = (T[]) new Object[lengthLeft];
            T[] rightArr = (T[]) new Object[lengthRight];

            for (int i = 0; i < lengthLeft; i++) {
                leftArr[i] = arr[i];
            }

            for (int j = lengthLeft; j < length; j++) {
                rightArr[j - lengthLeft] = arr[j];
            }
            mergeSortHelper(leftArr, comparator);
            mergeSortHelper(rightArr, comparator);

            int left = 0;
            int right = 0;
            int curr = 0;

            while (left < middle && right < length - middle) {
                if (comparator.compare(leftArr[left], rightArr[right]) <= 0) {
                    arr[curr] = leftArr[left];
                    left++;
                } else {
                    arr[curr] = rightArr[right];
                    right++;
                }
                curr++;
            }
            while (left < middle) {
                arr[curr] = leftArr[left];
                left++;
                curr++;
            }
            
            while (right < arr.length - middle) {
                arr[curr] = rightArr[right];
                right++;
                curr++;
            }
        }
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
     * determine the number of iterations you need.
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
            throw new IllegalArgumentException("The array is null.");
        }
        int largest = 0;
        int current;
        for (int i = 0; i < arr.length; i++) {
            current = arr[i];
            if (arr[i] <= 0) {
                current = -current;
            }
            if (current > largest) {
                largest = current;
            }
        }
        int useful = 0;
        while (largest != 0) {
            largest = largest / 10;
            useful++;
        }
        LinkedList<Integer>[] counter = (LinkedList<Integer>[]) new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            counter[i] = new LinkedList<>();
        }
        int mod = 10;
        int dev = 1;
        boolean cont = true;
        while (cont) {
            cont = false;
            for (int i = 0; i < arr.length; i++) {
                int beforeMod = arr[i] / dev;
                if (beforeMod != 0) {
                    cont = true;
                }
                int bucket = beforeMod % mod + 9;
                counter[bucket].add(arr[i]);
            }
            int idx = 0;
            for (int i = 0; i < counter.length; i++) {
                while (!counter[i].isEmpty()) {
                    arr[idx] = counter[i].remove();
                    idx++;
                }
            }
            dev = dev * 10;
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
            throw new IllegalArgumentException("Can not deal with null data");
        }
        quickSortHelper(arr, comparator, rand, 0, arr.length - 1);
    }

    /**
     * Private helper method to do a quicksort
     * @param arr
     * @param comparator
     * @param rand
     * @param left
     * @param right
     * @param <T>
     */
    private static <T> void quickSortHelper(T[] arr, Comparator<T> comparator,
                                      Random rand, int left, int right) {
        if (left >= right) {
            return;
        }
        int piv = rand.nextInt(right - left + 1) + left;
        int i = left + 1;
        int j = right;
        swap(arr, left, piv);
        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], arr[left]) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], arr[left]) >= 0) {
                j--;
            }
            if (i < j) {
                swap(arr, i, j);
                j--;
                i++;

            }
        }
        swap(arr, left, j);
        if (left < j) {
            quickSortHelper(arr, comparator, rand, left, j - 1);
        }
        if (right > i) {
            quickSortHelper(arr, comparator, rand, i, right);
        }
    }

    /**
     * Helper method in order to swap values
     * @param arr array to look for values
     * @param i first value
     * @param j second value
     * @param <T> data type
     */
    private static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // 3,2,4,10,9,1,11,5,6
}

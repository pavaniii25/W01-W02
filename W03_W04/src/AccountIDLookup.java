import java.util.*;

class AccountIdLookup {


    public int linearSearchFirst(String[] arr, String target) {
        int comparisons = 0;

        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i].equals(target)) {
                System.out.println("Linear First -> Comparisons: " + comparisons);
                return i;
            }
        }

        System.out.println("Linear First -> Comparisons: " + comparisons);
        return -1;
    }


    public int linearSearchLast(String[] arr, String target) {
        int comparisons = 0;
        int result = -1;

        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i].equals(target)) {
                result = i;
            }
        }

        System.out.println("Linear Last -> Comparisons: " + comparisons);
        return result;
    }


    public int binarySearch(String[] arr, String target) {
        int low = 0, high = arr.length - 1;
        int comparisons = 0;

        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;

            int cmp = arr[mid].compareTo(target);

            if (cmp == 0) {
                System.out.println("Binary Search -> Comparisons: " + comparisons);
                return mid;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        System.out.println("Binary Search -> Comparisons: " + comparisons);
        return -1;
    }


    public int firstOccurrence(String[] arr, String target) {
        int low = 0, high = arr.length - 1;
        int result = -1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (arr[mid].equals(target)) {
                result = mid;
                high = mid - 1; // search left
            } else if (arr[mid].compareTo(target) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return result;
    }


    public int lastOccurrence(String[] arr, String target) {
        int low = 0, high = arr.length - 1;
        int result = -1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (arr[mid].equals(target)) {
                result = mid;
                low = mid + 1; // search right
            } else if (arr[mid].compareTo(target) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return result;
    }


    public int countOccurrences(String[] arr, String target) {
        int first = firstOccurrence(arr, target);
        int last = lastOccurrence(arr, target);

        if (first == -1) return 0;
        return last - first + 1;
    }
}

class Main4 {
    public static void main(String[] args) {

        String[] logs = {"accA", "accB", "accB", "accC"}; // sorted

        AccountIdLookup lookup = new AccountIdLookup();


        int first = lookup.linearSearchFirst(logs, "accB");
        int last = lookup.linearSearchLast(logs, "accB");

        System.out.println("Linear First Index: " + first);
        System.out.println("Linear Last Index: " + last);


        int index = lookup.binarySearch(logs, "accB");
        int count = lookup.countOccurrences(logs, "accB");

        System.out.println("Binary Index: " + index);
        System.out.println("Count: " + count);
    }
}
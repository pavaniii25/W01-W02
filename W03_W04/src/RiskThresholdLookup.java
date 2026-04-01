class RiskThresholdLookup {

    // 🔹 LINEAR SEARCH (unsorted)
    public int linearSearch(int[] arr, int target) {
        int comparisons = 0;

        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i] == target) {
                System.out.println("Linear Search -> Comparisons: " + comparisons);
                return i;
            }
        }

        System.out.println("Linear Search -> Comparisons: " + comparisons);
        return -1;
    }

    // 🔹 BINARY SEARCH (find insertion point = lower_bound)
    public int insertionPoint(int[] arr, int target) {
        int low = 0, high = arr.length;
        int comparisons = 0;

        while (low < high) {
            int mid = (low + high) / 2;
            comparisons++;

            if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        System.out.println("Insertion Point -> Comparisons: " + comparisons);
        return low;
    }

    // 🔹 FLOOR (largest ≤ target)
    public int floor(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        int result = -1;
        int comparisons = 0;

        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;

            if (arr[mid] == target) {
                System.out.println("Floor -> Comparisons: " + comparisons);
                return arr[mid];
            }

            if (arr[mid] < target) {
                result = arr[mid];
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        System.out.println("Floor -> Comparisons: " + comparisons);
        return result;
    }

    // 🔹 CEILING (smallest ≥ target)
    public int ceiling(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        int result = -1;
        int comparisons = 0;

        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;

            if (arr[mid] == target) {
                System.out.println("Ceiling -> Comparisons: " + comparisons);
                return arr[mid];
            }

            if (arr[mid] > target) {
                result = arr[mid];
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        System.out.println("Ceiling -> Comparisons: " + comparisons);
        return result;
    }
}

class Main5 {
    public static void main(String[] args) {

        int[] risks = {10, 25, 50, 100}; // sorted

        RiskThresholdLookup lookup = new RiskThresholdLookup();

        int target = 30;

        // ✅ Linear Search (unsorted scenario)
        int linearResult = lookup.linearSearch(risks, target);
        System.out.println("Linear Result: " + linearResult);

        // ✅ Binary Search Variants
        int insertPos = lookup.insertionPoint(risks, target);
        int floor = lookup.floor(risks, target);
        int ceiling = lookup.ceiling(risks, target);

        System.out.println("Insertion Index: " + insertPos);
        System.out.println("Floor: " + floor);
        System.out.println("Ceiling: " + ceiling);
    }
}


import java.util.*;

class Asset {
    String name;
    double returnRate;   // %
    double volatility;   // risk measure

    public Asset(String name, double returnRate, double volatility) {
        this.name = name;
        this.returnRate = returnRate;
        this.volatility = volatility;
    }

    @Override
    public String toString() {
        return name + ":" + returnRate + "%";
    }
}

class PortfolioSorter {


    public void mergeSort(Asset[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);

            merge(arr, left, mid, right);
        }
    }

    private void merge(Asset[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Asset[] L = new Asset[n1];
        Asset[] R = new Asset[n2];

        for (int i = 0; i < n1; i++) L[i] = arr[left + i];
        for (int j = 0; j < n2; j++) R[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left;

        while (i < n1 && j < n2) {
            // Stable: <= keeps original order
            if (L[i].returnRate <= R[j].returnRate) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }

        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }


    public void quickSort(Asset[] arr, int low, int high) {
        if (low < high) {
            // Hybrid optimization
            if (high - low < 10) {
                insertionSort(arr, low, high);
                return;
            }

            int pivotIndex = medianOfThree(arr, low, high);
            swap(arr, pivotIndex, high);

            int pi = partition(arr, low, high);

            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }


    private int partition(Asset[] arr, int low, int high) {
        Asset pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (compare(arr[j], pivot) < 0) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }


    private int compare(Asset a1, Asset a2) {
        if (a1.returnRate != a2.returnRate) {
            return Double.compare(a2.returnRate, a1.returnRate); // DESC
        }
        return Double.compare(a1.volatility, a2.volatility); // ASC
    }


    private int medianOfThree(Asset[] arr, int low, int high) {
        int mid = (low + high) / 2;

        Asset a = arr[low];
        Asset b = arr[mid];
        Asset c = arr[high];

        if (compare(a, b) < 0) {
            if (compare(b, c) < 0) return mid;
            else if (compare(a, c) < 0) return high;
            else return low;
        } else {
            if (compare(a, c) < 0) return low;
            else if (compare(b, c) < 0) return high;
            else return mid;
        }
    }


    private void insertionSort(Asset[] arr, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            Asset key = arr[i];
            int j = i - 1;

            while (j >= low && compare(arr[j], key) > 0) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    private void swap(Asset[] arr, int i, int j) {
        Asset temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

class Main3 {
    public static void main(String[] args) {

        Asset[] assets = {
                new Asset("AAPL", 12, 0.30),
                new Asset("TSLA", 8, 0.60),
                new Asset("GOOG", 15, 0.25)
        };

        PortfolioSorter sorter = new PortfolioSorter();


        sorter.mergeSort(assets, 0, assets.length - 1);
        System.out.println("Merge Sorted: " + Arrays.toString(assets));


        sorter.quickSort(assets, 0, assets.length - 1);
        System.out.println("Quick Sorted (DESC): " + Arrays.toString(assets));
    }
}
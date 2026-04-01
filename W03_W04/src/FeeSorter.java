import java.util.*;

class Transaction {
    String id;
    double fee;
    String timestamp; // HH:MM

    public Transaction(String id, double fee, String timestamp) {
        this.id = id;
        this.fee = fee;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return id + ":" + fee + "@" + timestamp;
    }
}

class FeeSorter {

    // 🔹 Bubble Sort (by fee)
    public void bubbleSort(List<Transaction> list) {
        int n = list.size();
        boolean swapped;
        int passes = 0, swaps = 0;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            passes++;

            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).fee > list.get(j + 1).fee) {
                    Collections.swap(list, j, j + 1);
                    swapped = true;
                    swaps++;
                }
            }

            if (!swapped) break; // early termination
        }

        System.out.println("Bubble Sort -> Passes: " + passes + ", Swaps: " + swaps);
    }

    // 🔹 Insertion Sort (by fee + timestamp)
    public void insertionSort(List<Transaction> list) {
        for (int i = 1; i < list.size(); i++) {
            Transaction key = list.get(i);
            int j = i - 1;

            while (j >= 0 && compare(list.get(j), key) > 0) {
                list.set(j + 1, list.get(j)); // shift right
                j--;
            }
            list.set(j + 1, key);
        }
    }

    // 🔹 Comparator logic
    private int compare(Transaction t1, Transaction t2) {
        if (t1.fee != t2.fee) {
            return Double.compare(t1.fee, t2.fee);
        }
        return t1.timestamp.compareTo(t2.timestamp);
    }

    // 🔹 Outlier detection (>50)
    public List<Transaction> findHighFeeOutliers(List<Transaction> list) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : list) {
            if (t.fee > 50) {
                result.add(t);
            }
        }
        return result;
    }
}

class Main {
    public static void main(String[] args) {

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("id1", 10.5, "10:00"));
        transactions.add(new Transaction("id2", 25.0, "09:30"));
        transactions.add(new Transaction("id3", 5.0, "10:15"));

        FeeSorter sorter = new FeeSorter();

        // ✅ Bubble Sort (small batch)
        sorter.bubbleSort(transactions);
        System.out.println("Bubble Sorted: " + transactions);

        // ✅ Insertion Sort (medium batch)
        sorter.insertionSort(transactions);
        System.out.println("Insertion Sorted: " + transactions);

        // ✅ Outliers
        List<Transaction> outliers = sorter.findHighFeeOutliers(transactions);
        System.out.println("Outliers (>50): " + outliers);
    }
}
import java.util.*;

class Client {
    String name;
    int riskScore;
    double accountBalance;

    public Client(String name, int riskScore, double accountBalance) {
        this.name = name;
        this.riskScore = riskScore;
        this.accountBalance = accountBalance;
    }

    @Override
    public String toString() {
        return name + ":" + riskScore;
    }
}

class RiskScoreRanking {


    public void bubbleSort(Client[] arr) {
        int n = arr.length;
        int swaps = 0;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].riskScore > arr[j + 1].riskScore) {
                    // swap
                    Client temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                    swaps++;
                    swapped = true;


                    System.out.println("Swap: " + Arrays.toString(arr));
                }
            }

            if (!swapped) break;
        }

        System.out.println("Total swaps: " + swaps);
    }


    public void insertionSort(Client[] arr) {
        for (int i = 1; i < arr.length; i++) {
            Client key = arr[i];
            int j = i - 1;

            while (j >= 0 && compare(arr[j], key) < 0) {
                arr[j + 1] = arr[j]; // shift right
                j--;
            }
            arr[j + 1] = key;
        }
    }


    private int compare(Client c1, Client c2) {
        if (c1.riskScore != c2.riskScore) {
            return Integer.compare(c1.riskScore, c2.riskScore);
        }
        return Double.compare(c1.accountBalance, c2.accountBalance);
    }


    public List<Client> getTopRiskClients(Client[] arr, int topN) {
        List<Client> result = new ArrayList<>();
        for (int i = 0; i < Math.min(topN, arr.length); i++) {
            result.add(arr[i]);
        }
        return result;
    }
}

class Main1 {
    public static void main(String[] args) {

        Client[] clients = {
                new Client("clientC", 80, 5000),
                new Client("clientA", 20, 2000),
                new Client("clientB", 50, 3000)
        };

        RiskScoreRanking ranking = new RiskScoreRanking();


        ranking.bubbleSort(clients);
        System.out.println("Bubble Sorted (ASC): " + Arrays.toString(clients));


        ranking.insertionSort(clients);
        System.out.println("Insertion Sorted (DESC): " + Arrays.toString(clients));


        List<Client> topClients = ranking.getTopRiskClients(clients, 3);
        System.out.println("Top Risks: " + topClients);
    }
}

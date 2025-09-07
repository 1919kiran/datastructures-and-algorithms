package org.kiran.arrays;

import java.util.Arrays;

public class FractionalKnapsack {
    /**
     * Intuition: Select the items in decreasing order of value/weight ratio
     */
    public double fractionalKnapsack(int W, Item[] arr, int n) {
        // sort items by value/weight ratio in descending order
        Arrays.sort(arr, (a, b) -> {
            double r1 = (double) a.value / a.weight; // ratio of first item
            double r2 = (double) b.value / b.weight; // ratio of second item
            return Double.compare(r2, r1); // sort higher ratio first
        });

        int currentWeight = 0;    // how much weight is in the knapsack now
        double finalValue = 0.0;  // total value collected

        // iterate through items
        for (int i = 0; i < n; i++) {
            if (currentWeight + arr[i].weight <= W) {
                // if full item fits
                currentWeight += arr[i].weight; // add item weight
                finalValue += arr[i].value;     // add item value
            } else {
                // take fraction of the item
                int remain = W - currentWeight; // remaining capacity
                finalValue += arr[i].value * ((double) remain / arr[i].weight); // add proportional value
                break; // knapsack is full
            }
        }

        return finalValue; // return the maximum value achievable
    }
}

class Item {
    int value;   // the value of the item
    int weight;  // the weight of the item

    Item(int value, int weight) { // constructor
        this.value = value;   // set value
        this.weight = weight; // set weight
    }
}

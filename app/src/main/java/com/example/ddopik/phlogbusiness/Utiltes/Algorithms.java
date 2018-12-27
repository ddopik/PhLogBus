package com.example.ddopik.phlogbusiness.Utiltes;

import android.util.Log;

public class Algorithms {


    private static int draftArr[] = {1, 2, 3, 5, 6};

    public static void main() {

        int arrSize = draftArr.length;
        BinarySearch(draftArr, arrSize, 10);

    }

    private static void BinarySearch(int arr[], int arrSize, int query) {

        int midIndex = arrSize / 2;
        Log.e("Algorithms","mid is--->"+midIndex);

    }


}

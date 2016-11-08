package com.sahil.algo;

import java.io.IOException;

/**
 * Created by sahil.lone on 11/7/2016.
 * 1. Since we can start from any point we can conclude that we have to take each hill element as starting point as potential candidate for longest path.
 * 2. Also we know that a longest path can contain multiple sub paths so we can mark elements as visited so we dont try for the subpath elements as starting point candiates as they will already be included in the superset path.
 * 3. Since we concluded to drop the visited elements as starting elements based on assumption we have already taken that into account while traversing superset path but for that to work and to maximize the chance of dropping maximum number
 * of starting  element candidates we will start from highest point and so on , so we will sort the elements in ascending order.
 * 4. SOrting should not be done directly on input array as that will disturb the hill positions as well. So sort will be done in duplicate array and the output should be the coordinates of array rather than value for the max element and so on.
 */
public class Application {

    public static void main(String[] args) {
        try {
            DownHillProblem downHillProblem = new DownHillProblem();
            System.out.println("OUTPUT : " + downHillProblem.processInput());
        } catch (Exception e) {
            throw new RuntimeException("Input DATA not As Expected", e);
        }
    }
}

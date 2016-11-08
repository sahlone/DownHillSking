package com.sahil.algo;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by sahil.lone on 11/7/2016.
 * 1. Since we can start from any point we can conclude that we have to take each hill element as starting point as potential candidate for longest path.
 * 2. Also we know that a longest path can contain multiple sub paths so we can mark elements as visited so we dont try for the subpath elements as starting point candiates as they will already be included in the superset path.
 * 3. Since we concluded to drop the visited elements as starting elements based on assumption we have already taken that into account while traversing superset path but for that to work and to maximize the chance of dropping maximum number
 * of starting  element candidates we will start from highest point and so on , so we will sort the elements in ascending order.
 * 4. SOrting should not be done directly on input array as that will disturb the hill positions as well. So sort will be done in duplicate array and the output should be the coordinates of array rather than value for the max element and so on.
 */
public class DownHillProblem {


    private static final String inputURL = "http://s3-ap-southeast-1.amazonaws.com/geeks.redmart.com/coding-problems/map.txt";
    private static final String INPUT_DELIMITTER = " ";
    private Integer maxLength = 0;
    private Integer maxDrop = 0;
    private Integer noOfRows = 0;
    private Integer noOfColumns = 0;
    private Integer[][] inputDownHillData = {};
    private Set<String> visitedElements = new HashSet<String>();

    public String processInput() throws MalformedURLException, IOException {
        InputStream inputStream = new URL(inputURL).openStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String lineRead = null;
        lineRead = bufferedReader.readLine();
        if (null == lineRead) {
            throw new RuntimeException("NO DATA TO READ FROM FILE" + inputURL);
        }
        StringTokenizer stringTokenizer = new StringTokenizer(lineRead, INPUT_DELIMITTER);
        if (!stringTokenizer.hasMoreElements()) {
            throw new RuntimeException("DATA NOT VALID" + inputURL);
        }
        noOfRows = Integer.valueOf(stringTokenizer.nextToken());
        if (!stringTokenizer.hasMoreElements()) {
            throw new RuntimeException("DATA NOT VALID" + inputURL);
        }
        noOfColumns = Integer.valueOf(stringTokenizer.nextToken());

        inputDownHillData = new Integer[noOfRows][noOfColumns];
        TreeMap<Integer, List<Integer>> sortedInputMap = new TreeMap<Integer, List<Integer>>();


        int inputRowsParsed = 0;
        while ((lineRead = bufferedReader.readLine()) != null && inputRowsParsed < noOfRows) {
            stringTokenizer = new StringTokenizer(lineRead, INPUT_DELIMITTER);
            int inputColumnsParsed = 0;
            while (stringTokenizer.hasMoreElements() && inputColumnsParsed < noOfColumns) {
                String elevationValue = stringTokenizer.nextToken();
                int elevationValueInt = Integer.valueOf(elevationValue);
                inputDownHillData[inputRowsParsed][inputColumnsParsed] = elevationValueInt;
                if (sortedInputMap.get(elevationValueInt) == null) {
                    List<Integer> coordinates = new ArrayList<Integer>(2);
                    coordinates.add(inputRowsParsed);
                    coordinates.add(inputColumnsParsed);
                    sortedInputMap.put(elevationValueInt, coordinates);
                } else {
                    sortedInputMap.get(elevationValueInt).add(inputRowsParsed);
                    sortedInputMap.get(elevationValueInt).add(inputColumnsParsed);
                }
                inputColumnsParsed += 1;
            }
            inputRowsParsed += 1;
        }


        int lastCordinatesKey = Integer.MAX_VALUE;
        Map.Entry<Integer, List<Integer>> lowerEntry = sortedInputMap.lowerEntry(lastCordinatesKey);
        String output = maxLength + "" + maxDrop;
        while (lowerEntry != null) {
            List<Integer> values = lowerEntry.getValue();
            lastCordinatesKey = lowerEntry.getKey();
            for (int index = 0; index < values.size(); index += 2) {
                int rowCount = values.get(index);
                int columnCount = values.get(index + 1);
                int currentPathLength = 0;
                int startPointElevation = inputDownHillData[rowCount][columnCount];
                backTrackRecursively(rowCount, columnCount, currentPathLength, startPointElevation);
            }
            lowerEntry = sortedInputMap.lowerEntry(lastCordinatesKey);
        }
        return output = maxLength + "" + maxDrop;

    }

    private void backTrackRecursively(int rowCount, int columnCount, int currentPathLength, int startPointElevation) {
        if (visitedElements.contains(rowCount + ":" + columnCount)) {
            return;
        }
        visitedElements.add(rowCount + ":" + columnCount);
        currentPathLength = currentPathLength + 1;
        if (currentPathLength > maxLength) {
            maxLength = currentPathLength;
            maxDrop = startPointElevation - inputDownHillData[rowCount][columnCount];

        } else if (currentPathLength == maxLength) {
            int currentDrop = startPointElevation - inputDownHillData[rowCount][columnCount];
            if (currentDrop > maxDrop) {
                maxDrop = currentDrop;
                maxLength = currentPathLength;
            }
        }
        int currentElevation = inputDownHillData[rowCount][columnCount];
        //east
        if (columnCount + 1 < noOfColumns && inputDownHillData[rowCount][columnCount + 1] < currentElevation) {
            backTrackRecursively(rowCount, columnCount + 1, currentPathLength, startPointElevation);
        }
        //west
        if (columnCount - 1 > 0 && inputDownHillData[rowCount][columnCount - 1] < currentElevation) {
            backTrackRecursively(rowCount, columnCount - 1, currentPathLength, startPointElevation);
        }
        //north
        if (rowCount - 1 > 0 && inputDownHillData[rowCount - 1][columnCount] < currentElevation) {
            backTrackRecursively(rowCount - 1, columnCount, currentPathLength, startPointElevation);
        }
        //south
        if (rowCount + 1 < noOfRows && inputDownHillData[rowCount + 1][columnCount] < currentElevation) {
            backTrackRecursively(rowCount + 1, columnCount, currentPathLength, startPointElevation);
        }
    }





    public static class ArrayIndexComparator implements Comparator<Integer> {
        private final String[] array;

        public ArrayIndexComparator(String[] array) {
            this.array = array;
        }

        public Integer[] createIndexArray() {
            Integer[] indexes = new Integer[array.length];
            for (int i = 0; i < array.length; i++) {
                indexes[i] = i; // Autoboxing
            }
            return indexes;
        }

        @Override
        public int compare(Integer index1, Integer index2) {
            // Autounbox from Integer to int to use as array indexes
            return array[index1].compareTo(array[index2]);
        }
    }

}

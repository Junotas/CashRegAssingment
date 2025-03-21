package com.lulea;
import java.util.Date;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static int menu() {
        System.out.println("""
                1. Insert items
                2. Remove an item
                3. Display a list of items
                4. Register a sale
                5. Display sales history
                6. Sort and display sales history table
                q. Quit
                Your Selection:""");
        return input();
    }

    public static int input() {
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                String s = scanner.next();
                if (s.equalsIgnoreCase("q")) return -1;
                System.out.println("Invalid input");
            }
        }
        return -1;
    }

    public static boolean checkFull(final int[][] items, final int noOfItems) {
        int count = 0;
        for (int i = 0; i < items[0].length; i++) {
            if (items[0][i] == 0) count++;
        }
        return count < noOfItems;
    }

    public static int[][] extendArray(final int[][] items, final int noOfItems) {
        int newSize = items[0].length + noOfItems;
        int[][] newArray = new int[3][newSize];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(items[i], 0, newArray[i], 0, items[0].length);
        }
        return newArray;
    }

    public static int[][] insertItems(final int[][] items, final int lastItemId, final int noOfItems) {
        int[][] result = items;
        if (checkFull(result, noOfItems)) {
            result = extendArray(result, noOfItems);
        }
        int nextId = lastItemId + 1;
        int inserted = 0;
        for (int i = 0; i < result[0].length && inserted < noOfItems; i++) {
            if (result[0][i] == 0) {
                result[0][i] = nextId++;
                result[1][i] = (int) (Math.random() * 10) + 1;
                result[2][i] = ((int) (Math.random() * 10) + 1) * 100;
                inserted++;
            }
        }
        return result;
    }

    public static int removeItem(final int[][] items, final int itemId) {
        for (int i = 0; i < items[0].length; i++) {
            if (items[0][i] == itemId) {
                items[0][i] = items[1][i] = items[2][i] = 0;
                return 0;
            }
        }
        return -1;
    }

    public static void printItems(final int[][] items) {
        int[] ids = new int[items[0].length];
        int count = 0;
        for (int i = 0; i < items[0].length; i++) {
            if (items[0][i] != 0) {
                ids[count++] = items[0][i];
            }
        }
        java.util.Arrays.sort(ids, 0, count);
        for (int id : ids) {
            for (int i = 0; i < items[0].length; i++) {
                if (items[0][i] == id) {
                    System.out.printf("ItemID: %d Qty: %d Price: %d%n", items[0][i], items[1][i], items[2][i]);
                }
            }
        }
    }

    public static int sellItem(final int[][] sales, final Date[] salesDate, final int[][] items, final int itemIdToSell, final int amountToSell) {
        for (int i = 0; i < items[0].length; i++) {
            if (items[0][i] == itemIdToSell) {
                if (items[1][i] < amountToSell) return items[1][i];
                items[1][i] -= amountToSell;

                for (int j = 0; j < sales[0].length; j++) {
                    if (sales[0][j] == 0) {
                        sales[0][j] = itemIdToSell;
                        sales[1][j] = amountToSell;
                        sales[2][j] = amountToSell * items[2][i];
                        salesDate[j] = new Date();
                        break;
                    }
                }
                return 0;
            }
        }
        return -1;
    }

    public static void printSales(final int[][] sales, final Date[] salesDate) {
        for (int i = 0; i < sales[0].length; i++) {
            if (sales[0][i] != 0) {
                System.out.printf("%s - ItemID: %d Qty: %d Total: %d%n", salesDate[i], sales[0][i], sales[1][i], sales[2][i]);
            }
        }
    }

    public static void sortedTable(final int[][] sales, final Date[] salesDate) {
        for (int i = 0; i < sales[0].length - 1; i++) {
            for (int j = i + 1; j < sales[0].length; j++) {
                if (sales[0][i] > sales[0][j] && sales[0][j] != 0) {
                    for (int k = 0; k < 3; k++) {
                        int temp = sales[k][i];
                        sales[k][i] = sales[k][j];
                        sales[k][j] = temp;
                    }
                    Date tempDate = salesDate[i];
                    salesDate[i] = salesDate[j];
                    salesDate[j] = tempDate;
                }
            }
        }
        printSales(sales, salesDate);
    }

    // Main method is provided in CodeGrade
}

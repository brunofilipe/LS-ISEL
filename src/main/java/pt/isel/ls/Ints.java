package pt.isel.ls;

public class Ints {

    public static int max(int a, int b) {
        return a >= b ? a : b;
    }

    public static int indexOfBinary(int[] a, int fromIndex, int toIndex, int n) {
        if (fromIndex > toIndex)
            throw new IllegalArgumentException("from(" + fromIndex + ") > to(" + toIndex + ")");
        if (a.length == 0)
            throw new IllegalArgumentException("length(" + a.length + ") is illegal");
        if (toIndex > a.length)
            throw new IllegalArgumentException("to(" + toIndex + ") > length (" + a.length + ")");

        int low = fromIndex;
        int high = toIndex - 1;
        int mid;
        while (low < high) {
            mid = low + (high - low) / 2;
            if (n > a[mid]) low = mid + 1;
            else if (n < a[mid]) high = mid - 1;
            else return mid;
        }
        return -1;
    }
}
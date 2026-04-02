import java.util.*;

public class BookMyStayApp
{

    public static void main(String[] args) {

        String input = "A man a plan a canal Panama";
        String normalized = input.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

        // Method 1: Reverse String (Loop)
        long start1 = System.nanoTime();
        boolean result1 = reverseCheck(normalized);
        long end1 = System.nanoTime();

        // Method 2: Stack
        long start2 = System.nanoTime();
        boolean result2 = stackCheck(normalized);
        long end2 = System.nanoTime();

        // Method 3: Deque
        long start3 = System.nanoTime();
        boolean result3 = dequeCheck(normalized);
        long end3 = System.nanoTime();

        // Method 4: Recursion
        long start4 = System.nanoTime();
        boolean result4 = recursiveCheck(normalized, 0, normalized.length() - 1);
        long end4 = System.nanoTime();

        // Display results
        System.out.println("Input: " + input);
        System.out.println("--------------------------------------");

        System.out.println("Reverse Loop: " + result1 +
                " | Time: " + (end1 - start1) + " ns");

        System.out.println("Stack: " + result2 +
                " | Time: " + (end2 - start2) + " ns");

        System.out.println("Deque: " + result3 +
                " | Time: " + (end3 - start3) + " ns");

        System.out.println("Recursion: " + result4 +
                " | Time: " + (end4 - start4) + " ns");
    }

    // Reverse Loop Method
    public static boolean reverseCheck(String str) {
        String reversed = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            reversed += str.charAt(i);
        }
        return str.equals(reversed);
    }

    // Stack Method
    public static boolean stackCheck(String str) {
        Stack<Character> stack = new Stack<>();
        for (char ch : str.toCharArray()) {
            stack.push(ch);
        }
        for (char ch : str.toCharArray()) {
            if (ch != stack.pop()) {
                return false;
            }
        }
        return true;
    }

    // Deque Method
    public static boolean dequeCheck(String str) {
        Deque<Character> deque = new LinkedList<>();
        for (char ch : str.toCharArray()) {
            deque.addLast(ch);
        }
        while (deque.size() > 1) {
            if (deque.removeFirst() != deque.removeLast()) {
                return false;
            }
        }
        return true;
    }

    // Recursive Method
    public static boolean recursiveCheck(String str, int start, int end) {
        if (start >= end) return true;
        if (str.charAt(start) != str.charAt(end)) return false;
        return recursiveCheck(str, start + 1, end - 1);
    }
}
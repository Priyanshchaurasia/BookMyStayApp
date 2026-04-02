public class BookMyStayApp {

    public static void main(String[] args) {

        // Input string
        String original = "level";

        // Reverse using loop
        String reversed = "";

        for (int i = original.length() - 1; i >= 0; i--) {
            reversed = reversed + original.charAt(i);
        }

        // Compare original and reversed
        if (original.equals(reversed)) {
            System.out.println("The string \"" + original + "\" is a Palindrome.");
        } else {
            System.out.println("The string \"" + original + "\" is NOT a Palindrome.");
        }
        System.out.println("----------------------------S");
    }
}
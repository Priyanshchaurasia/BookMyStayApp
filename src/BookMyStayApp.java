public class BookMyStayApp
{

    public static void main(String[] args) {

        // Input string
        String input = "madam";

        // Check palindrome using recursion
        boolean isPalindrome = checkPalindrome(input, 0, input.length() - 1);

        // Display result
        if (isPalindrome) {
            System.out.println("The string \"" + input + "\" is a Palindrome.");
        } else {
            System.out.println("The string \"" + input + "\" is NOT a Palindrome.");
        }
    }

    // Recursive function
    public static boolean checkPalindrome(String str, int start, int end) {

        // Base condition
        if (start >= end) {
            return true;
        }

        // Check mismatch
        if (str.charAt(start) != str.charAt(end)) {
            return false;
        }

        // Recursive call (move inward)
        return checkPalindrome(str, start + 1, end - 1);
    }
}
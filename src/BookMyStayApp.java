import java.util.*;

// Step 1: Strategy Interface
interface PalindromeStrategy {
    boolean isPalindrome(String input);
}

// Step 2: Stack Strategy
class StackStrategy implements PalindromeStrategy {

    public boolean isPalindrome(String input) {
        Stack<Character> stack = new Stack<>();

        for (char ch : input.toCharArray()) {
            stack.push(ch);
        }

        for (char ch : input.toCharArray()) {
            if (ch != stack.pop()) {
                return false;
            }
        }

        return true;
    }
}

// Step 3: Deque Strategy
class DequeStrategy implements PalindromeStrategy {

    public boolean isPalindrome(String input) {
        Deque<Character> deque = new LinkedList<>();

        for (char ch : input.toCharArray()) {
            deque.addLast(ch);
        }

        while (deque.size() > 1) {
            if (deque.removeFirst() != deque.removeLast()) {
                return false;
            }
        }

        return true;
    }
}

// Step 4: Context Class
class PalindromeContext {
    private PalindromeStrategy strategy;

    public PalindromeContext(PalindromeStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(PalindromeStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean check(String input) {
        return strategy.isPalindrome(input);
    }
}

// Step 5: Main Application
public class BookMyStayApp
{

    public static void main(String[] args) {

        String input = "racecar";

        // Choose strategy dynamically
        PalindromeContext context = new PalindromeContext(new StackStrategy());

        boolean result = context.check(input);
        System.out.println("Using Stack Strategy: " + result);

        // Switch strategy at runtime
        context.setStrategy(new DequeStrategy());

        result = context.check(input);
        System.out.println("Using Deque Strategy: " + result);
    }
}
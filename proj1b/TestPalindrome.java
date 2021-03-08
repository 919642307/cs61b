import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {

    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }
    @Test
    public void testIsPalindrome(){
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("aba"));
        assertFalse(palindrome.isPalindrome("ab"));
        assertTrue(palindrome.isPalindrome(""));

    }
    @Test
    public void testPalindrome(){
        CharacterComparator offByOne = new OffByOne();
        assertTrue(palindrome.isPalindrome("",offByOne));
        assertTrue(palindrome.isPalindrome("a",offByOne));
        assertTrue(palindrome.isPalindrome("flake",offByOne));
        assertFalse(palindrome.isPalindrome("aabaa",offByOne));
        assertFalse(palindrome.isPalindrome("noon",offByOne));
        assertTrue(palindrome.isPalindrome("cb",offByOne));
        assertFalse(palindrome.isPalindrome("cB",offByOne));

    }
}

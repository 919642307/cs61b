import java.security.cert.TrustAnchor;

public class Palindrome{
    public Deque<Character> wordToDeque(String word){
        Deque<Character> list = new LinkedListDeque<>();
        for (int i = 0;i<word.length();i++){
            list.addLast(word.charAt(i));
        }
        return list;
    }
    public boolean isPalindrome(String string){
        Deque<Character> list = new LinkedListDeque<>();
        list = this.wordToDeque(string);
        return isPalindromeHelper(list);
    }
    private boolean isPalindromeHelper(Deque<Character> list){
        if (list.size() == 0 || list.size() == 1){
            return true;
        }
        else {
          char first = list.removeFirst();
          char last = list.removeLast();
          if (first != last) {
                return false;
          }
          return isPalindromeHelper(list);
        }
    }
    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque<Character> list = new LinkedListDeque<>();
        list = this.wordToDeque(word);
        return isPalindromeHelper(list, cc);
    }
    private boolean isPalindromeHelper(Deque<Character> list, CharacterComparator cc){
        if (list.size() == 0 || list.size() ==1){
            return true;
        }else{
            char first = list.removeFirst();
            char last = list.removeLast();
            if (!cc.equalChars(first, last)) {
                return false;
            }
            return isPalindromeHelper(list, cc);
        }
    }



}
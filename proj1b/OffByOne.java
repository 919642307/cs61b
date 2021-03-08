public class OffByOne implements CharacterComparator{
    @Override
    public boolean equalChars(char x,char y) {
        int diff;
        diff = x - y;
        return diff == 1 || diff == -1;
    }
}
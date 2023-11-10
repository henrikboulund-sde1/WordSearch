package dk.easv.be;

public class History {
    private final Word SearchWord;

    private final boolean Found;

    public History(Word word, boolean found)
    {
        SearchWord = word;
        Found = found;
    }

    public boolean isFound() {
        return Found;
    }

    public Word getSearchWord() {
        return SearchWord;
    }
}

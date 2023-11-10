package dk.easv.be;

public class Word {
    private String Word;
    private int CharCount;
    private boolean BeginsWithVowel;

    public Word(String word)
    {
        Word = word;
    }

    public Word(String word, int charCount, boolean beginsWithVowel)
    {
        Word = word;
        CharCount = charCount;
        BeginsWithVowel = beginsWithVowel;
    }

    public String getWord()
    {
        return Word;
    }


    public int getCharCount() {
        return CharCount;
    }

    public boolean isBeginsWithVowel() {
        return BeginsWithVowel;
    }
}

package dk.easv.bll;

import dk.easv.be.Word;
import dk.easv.dal.WordsData;

import java.util.ArrayList;
import java.util.List;

public class WordsLogic
{
    private final static WordsLogic Instance = new WordsLogic();
    private final WordsData DataAccess = new WordsData();
    private final List<Word> Words;


    private WordsLogic()
    {
        Words = new ArrayList<>();
    }

    public static WordsLogic GetInstance()
    {
        return Instance;
    }

    public List<Word> GetWords()
    {
        if(Words.isEmpty())
        {
            List<String> result = DataAccess.GetWords();
            for(int i = 0; i<=result.size()-1; i++)
            {
                String word = result.get(i);
                Word wordObj = new Word(word);
                Words.add(wordObj);
            }
        }
        return Words;
    }

    /**
     * Finds all words beginning with the specified first letter.
     * @param firstLetter Is the letter the first letter should be.
     * @return A list of the Word business entity.
     */
    public List<Word> GetWordsBeginningWith(Character firstLetter)
    {
        List<Word> filteredList = new ArrayList<>();
        for(int i = 0; i<=Words.size()-1; i++)
        {
            Word wordObj = Words.get(i);
            String word = wordObj.getWord();
            char character = word.charAt(0);
            char nextCharacter = word.charAt(1);
            boolean nextCharacterEqual = character == nextCharacter;

            if (character == firstLetter && !nextCharacterEqual)
            {
                filteredList.add(wordObj);
            }
        }
        return filteredList;
    }

    /**
     * Return the amount of words that is in memory.
     * @return
     */
    public int GetWordsCount()
    {
        return Words.size();
    }

    /**
     * Tells if the specified words begins with a vowel.
     * @param word The word to specify if it begins with a vowel.
     * @return Returns true, if the first letter is a vowel otherwise false.
     */
    public boolean WorkingWordBeginsWithVowel(String word)
    {
        return "eaiouEAIOU".indexOf(word.charAt(0)) >= 0;
    }

    /**
     * Tells how many letters the word consist of.
     * @param word The word to count the amount of letters.
     * @return The numbers of letters the specified words contains.
     */
    public int WorkingWordLetterCount(String word)
    {
        return word.toCharArray().length;
    }
}

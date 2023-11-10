package dk.easv.bll;

import dk.easv.be.Word;

import java.util.ArrayList;
import java.util.List;

public class SearchLogic {
    private int ExactSearchIndex = -1;

    /**
     * Searches for the specified search term.
     * @param searchTerm The term to search form.
     * @param exactSearch Is true if we search for full word and false if we search for words containing the search term.
     * @return
     */
    public List<String> SearchWord(String searchTerm, boolean exactSearch)
    {
        WordsLogic wordsLogic = WordsLogic.GetInstance();
        List<Word> words = wordsLogic.GetWords();
        List<String> result = new ArrayList<>();

       for(int i = 0; i<=words.size()-1; i++)
       {
           if(exactSearch)
           {
               if (searchTerm.equals(words.get(i).getWord()))
               {
                   result.add(words.get(i).getWord());
                   ExactSearchIndex = i;
               }
           }
           else
           {
               String m_word = words.get(i).getWord();
               if (m_word.contains(searchTerm))
               {
                   result.add(m_word);
                   ExactSearchIndex = -1;
               }
           }
       }
       return result;
    }

    /**
     * Gets the last index where the search term was found.
     * @return The index where the last search term was found. If last search was for words containing this will be -1.
     */
    public int GetExactSearchIndex()
    {
        return ExactSearchIndex;
    }
}

package dk.easv.dal;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class WordsData
{
    private Path WordFile = Paths.get("/Users/henrikboulund/Documents/WordSearch/brit-a-z.txt");

    /**
     * Get the list from the file specified.
     * @return An arraylist of Strings, containing just the words.
     */
    public List<String> GetWords()
    {
        List<String> words;
        try
        {
            words = Files.readAllLines(WordFile, StandardCharsets.ISO_8859_1);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        return words;
    }
}

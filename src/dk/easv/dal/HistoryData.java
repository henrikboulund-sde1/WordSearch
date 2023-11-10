package dk.easv.dal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class HistoryData
{
    File HistoryFile = new File("/Users/henrikboulund/Documents/WordSearch/history.txt");

    public List<String> GetHistories()
    {
        try
        {
            return Files.readAllLines(HistoryFile.toPath(), StandardCharsets.ISO_8859_1);
        }
        catch (IOException e)
        {
            return null;
        }
    }

    public void WriteHistory(String historyLine)
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(HistoryFile.toPath().toString(), true));
            bw.newLine();
            bw.append(historyLine);
            bw.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public boolean ClearHistory()
    {
       boolean deleteResult = HistoryFile.delete();
       if(deleteResult)
       {
           try
           {
               return HistoryFile.createNewFile();
           } catch (IOException e)
           {
               throw new RuntimeException(e);
           }
       }
       return false;
    }
}

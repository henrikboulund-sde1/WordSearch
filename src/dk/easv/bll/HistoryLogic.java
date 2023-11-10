package dk.easv.bll;

import dk.easv.dal.HistoryData;

import java.util.ArrayList;
import java.util.List;

public class HistoryLogic
{
    HistoryData HistData = new HistoryData();
    List<String> histories = new ArrayList<>();


    public List<String> GetHistories(boolean forceTotalReload)
    {
        if(histories.isEmpty() || forceTotalReload)
        {
            histories = HistData.GetHistories();
        }
        return histories;
    }

    public void WriteHistory(String searchWord, boolean result)
    {
        String resultStr = "";
        if(!result)
        {
            resultStr = "no ";
        }
        HistData.WriteHistory("Search for '" + searchWord + "' and found " + resultStr + "results.");
    }

    public boolean ClearHistory()
    {
        return HistData.ClearHistory();
    }

}

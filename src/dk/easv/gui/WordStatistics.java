package dk.easv.gui;

import dk.easv.bll.WordsLogic;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WordStatistics {

    @FXML
    private Label lblNumbersOfLetters;

    @FXML
    private Label lblBeginsWithVowel;

    @FXML
    private Label lblStatisticsFor;

    @FXML
    protected void initialize()
    {

    }

    public void SetTitle(String word)
    {
        lblStatisticsFor.setText("Statistics for: " + word);
    }

    public void SetBeginWithVowelLabel(boolean beginsWithVowel)
    {
        lblBeginsWithVowel.setText(beginsWithVowel ? "Yes" : "No");
    }

    public void SetNumbersOfLetterLabel(int numbersOfLetter)
    {
        lblNumbersOfLetters.setText(String.valueOf(numbersOfLetter));
    }
}

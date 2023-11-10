package dk.easv.gui;

import dk.easv.be.Word;
import dk.easv.bll.HistoryLogic;
import dk.easv.bll.SearchLogic;
import dk.easv.bll.WordsLogic;
import dk.easv.gui.enums.ListReadStyle;
import dk.easv.gui.enums.SearchStates;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainController {

    @FXML
    private ListView lstAllWords;

    @FXML
    private Label lblTotalWordCount;

    @FXML
    private ListView lstWordsWithA;

    @FXML
    private ListView lstWordsWithN;

    @FXML
    private TextField txtWordSearch;

    @FXML
    private Label lblSearchResult;

    @FXML
    private Label lblSelectedWord;

    @FXML
    private ListView lstHistory;

    @FXML
    private RadioButton rbtnWholeWord;

    @FXML
    private RadioButton rbtnPartOfWord;

    @FXML
    private Button btnClearSearchResult;

    private SearchStates SearchState = SearchStates.WholeWord;
    private final WordsLogic WordLogic = WordsLogic.GetInstance();
    private final SearchLogic SearchEngine = new SearchLogic();
    private final HistoryLogic HistLogic = new HistoryLogic();

    /**
     * Code that is done when the program starts.
     */
    @FXML
    protected void initialize() {
        //Fills in all words
        FillListView(null, lstAllWords, ListReadStyle.ReadAllWords);

        FillListView(null, lstHistory, ListReadStyle.ReadHistory);

        //Present the word count in the respective label
        lblTotalWordCount.setText(String.format("%,d", WordLogic.GetWordsCount()));
        lblSearchResult.setText("-");
        lblSelectedWord.setText("-");

        ToggleGroup toggleGroup = new ToggleGroup();

        rbtnPartOfWord.setToggleGroup(toggleGroup);
        rbtnWholeWord.setToggleGroup(toggleGroup);

    }

    /**
     * Fills the corresponding ListViews.
     * @param firstLetter is the first letter to be found if needed. Set as null if none.
     * @param listView is the listview that will be populated.
     * @param readStyle is used to determine if we read all words, some words or historical information.
     */
    private void FillListView(Character firstLetter, ListView listView, ListReadStyle readStyle)
    {
        List<String> strList = new ArrayList<>();
        List<Word> wordList = new ArrayList<>();

        if (readStyle == ListReadStyle.ReadWordsBeginningWith)
        {
            wordList = WordLogic.GetWordsBeginningWith(firstLetter);
        }
        else if(readStyle == ListReadStyle.ReadAllWords)
        {
            wordList = WordLogic.GetWords();
        }
        else if(readStyle == ListReadStyle.ReadHistory)
        {
            strList = HistLogic.GetHistories(true);
        }
        if(readStyle == ListReadStyle.ReadAllWords || readStyle == ListReadStyle.ReadWordsBeginningWith) {
            for (int i = 0; i <= wordList.size() - 1; i++)
            {
                listView.getItems().add(wordList.get(i).getWord());
            }
        }
        else
        {
            for (int i = 0; i <= strList.size() - 1; i++)
            {
                listView.getItems().add(strList.get(i));
            }
        }
        if(readStyle == ListReadStyle.ReadHistory)
        {
            listView.scrollTo(listView.getItems().size()-1);
        }
    }

    /**
     * Reloads the history list to get latest update.
     */
    public void ReloadHistory()
    {
        lstHistory.getItems().clear();
        FillListView(null, lstHistory, ListReadStyle.ReadHistory);
        lstHistory.scrollTo(lstHistory.getItems().size()-1);
    }

    public void onSearchClick(ActionEvent actionEvent) {
        String searchWord = txtWordSearch.getText();
        boolean exactSearch = SearchState.equals(SearchStates.WholeWord) ? true : false;
        List<String> result = SearchEngine.SearchWord(searchWord, exactSearch);
        if (!result.isEmpty())
        {
            if(SearchState == SearchStates.WholeWord)
            {
                int index = SearchEngine.GetExactSearchIndex();
                lblSearchResult.setText(searchWord + " is found!");
                lstAllWords.getSelectionModel().select(index);
                lstAllWords.scrollTo(index);
                HistLogic.WriteHistory(searchWord, true);
            }
            else
            {
                int size = result.size();
                String resultStr = "";
                if(size > 1)
                {
                    resultStr = size + " results are found!";
                }
                else
                {
                    resultStr = result.get(0) + " results are found!";
                }
                lblSearchResult.setText(resultStr);
                lstAllWords.getItems().removeAll();
                lstAllWords.getItems().setAll(result);
            }
        }
        else
        {
            lblSearchResult.setText(searchWord + " is NOT found!");
            HistLogic.WriteHistory(searchWord, false);
        }
        ReloadHistory();
    }

    public void onBtnReadAClick(ActionEvent actionEvent)
    {
        FillListView('a', lstWordsWithA, ListReadStyle.ReadWordsBeginningWith);
    }

    public void onBtnReadNClick(ActionEvent actionEvent)
    {
        FillListView('n', lstWordsWithN, ListReadStyle.ReadWordsBeginningWith);
    }

    public void onClearHistoryClick(ActionEvent actionEvent)
    {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Are you sure?");
        confirm.setHeaderText("Are you sure to clear history?");
        confirm.setContentText("Clearing the history is a destructive action and cannot be regret when first cleared!");
        Optional<ButtonType> option = confirm.showAndWait();
        if(ButtonType.OK.equals(option.get()))
        {
            boolean clearHistory = HistLogic.ClearHistory();
            if(clearHistory)
            {
                ReloadHistory();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Operation completed!");
                alert.setHeaderText("Clearing history has been completed.");
                alert.setContentText("The history has been cleared!");
                alert.showAndWait();
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Operation failed!");
                alert.setHeaderText("Clearing history has failed!");
                alert.setContentText("Clearing the history has failed. Please check the access to needed files or try again.");
                alert.showAndWait();
            }
        }
    }

    public void lstAllWordsOnMouseClick(MouseEvent mouseEvent) throws IOException {
        String selectedWord = lstAllWords.getSelectionModel().getSelectedItem().toString();
        lblSelectedWord.setText(selectedWord);
        if(mouseEvent.getClickCount() == 2)
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WordStatistics.fxml"));
            Parent load = loader.load();
            Stage stage = new Stage();
            WordStatistics statisticsWindow = loader.getController();
            statisticsWindow.SetTitle(selectedWord);
            statisticsWindow.SetBeginWithVowelLabel(WordLogic.WorkingWordBeginsWithVowel(selectedWord));
            statisticsWindow.SetNumbersOfLetterLabel(WordLogic.WorkingWordLetterCount(selectedWord));
            stage.setTitle("Word statistics");
            stage.setResizable(false);
            stage.setScene(new Scene(load));
            stage.show();
        }
    }

    public void rbtnPartOfWordOnAction(ActionEvent actionEvent) {
        if(rbtnPartOfWord.isSelected())
        {
            this.SearchState = SearchStates.PartOfWord;
            btnClearSearchResult.setDisable(false);
        }
    }

    public void rbtnWholeWordOnAction(ActionEvent actionEvent) {
        if(rbtnWholeWord.isSelected())
        {
            this.SearchState = SearchStates.WholeWord;
            btnClearSearchResult.setDisable(true);
            FillListView(null, lstAllWords, ListReadStyle.ReadAllWords);
        }
    }

    public void onClearSearchResult(ActionEvent actionEvent) {
        txtWordSearch.clear();
        FillListView(null, lstAllWords, ListReadStyle.ReadAllWords);
    }
}

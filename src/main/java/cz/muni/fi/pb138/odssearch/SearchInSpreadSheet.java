package cz.muni.fi.pb138.odssearch;

import java.util.List;

/**
 * Created by artemis on 13.05.2016.
 */
public interface SearchInSpreadSheet {

    void searchAllSheets();

    void printAllSheets();

    List<Result> getResults();
}

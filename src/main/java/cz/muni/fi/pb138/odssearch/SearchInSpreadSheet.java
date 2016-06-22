package cz.muni.fi.pb138.odssearch;

import java.util.List;

/**
 * Interface defining how the spreadSheet file will be searched.
 * Created by Karolína Božková on 13.5.16.
 */
public interface SearchInSpreadSheet {
    /**
     * Searches all sheets of a file for a given expression.
     *
     * @return  list of Result objects. If no results found in the whole file - empty list returned.
     * @throws cz.muni.fi.pb138.odssearch.ServiceFailureException in case of null spreadSheet
     */
    List<Result> searchAllSheets() throws ServiceFailureException;

}

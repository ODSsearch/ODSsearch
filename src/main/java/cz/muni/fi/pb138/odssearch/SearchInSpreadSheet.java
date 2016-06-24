package cz.muni.fi.pb138.odssearch;

import java.util.List;

/**
 * Interface defining how the spreadSheet file will be searched.
 * Created by Karolína Božková on 13.5.16.
 */
public interface SearchInSpreadSheet {
    /**
     * Searches all valid sheets of a file for a given expression. Sheet is valid if its header is
     * valid (at least second cell is nonempty).
     *
     * @return  list of Result objects.
     * @throws cz.muni.fi.pb138.odssearch.ServiceFailureException in case of null spreadSheet
     */
    List<Result> searchAllSheets() throws ServiceFailureException;

}

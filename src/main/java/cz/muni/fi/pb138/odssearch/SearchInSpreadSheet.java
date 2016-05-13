package cz.muni.fi.pb138.odssearch;

import org.jopendocument.dom.spreadsheet.SpreadSheet;

import java.util.List;

/**
 * Created by karbo on 13.5.16.
 */
public interface SearchInSpreadSheet {

    List<Result> searchAllSheets();

}

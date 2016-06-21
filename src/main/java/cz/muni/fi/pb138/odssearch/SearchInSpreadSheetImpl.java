package cz.muni.fi.pb138.odssearch;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



/**
 * Created by Karolína Božková on 13.5.16.
 */
public class SearchInSpreadSheetImpl implements SearchInSpreadSheet {

    private List<Result> results = new ArrayList<>();
    private SpreadSheet spreadSheet;
    private String term;
    private boolean sensitive = false;

    private List<String> getRow(int row, Sheet sheet) {
        int cols = sheet.getColumnCount();
        List<String> line = new ArrayList<>();

        for (int i = 0; i < cols; i++) {
            line.add(sheet.getValueAt(i, row).toString());
        }
        return line;
    }

    private Result searchSheet(Sheet sheet, String term) {
        int cols = sheet.getColumnCount();
        int rows = sheet.getRowCount();
        Result result = new Result(sheet.getName(), getRow(0,sheet));

        for (int row = 1; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (sensitive) {
                    if (sheet.getValueAt(col, row).toString().contains(term)) {
                        result.appendRow(getRow(row, sheet));
                    }
                }
                else {
                    if (sheet.getValueAt(col, row).toString().toLowerCase().contains(term.toLowerCase())) {
                        result.appendRow(getRow(row, sheet));
                    }
                }
            }
        }
        return result;
    }

    @Override
    public List<Result> searchAllSheets(){
        for (int i = 0; i < spreadSheet.getSheetCount(); i++) {
            results.add(searchSheet(spreadSheet.getSheet(i), term));
        }
        return Collections.unmodifiableList(results);
    }

    public SearchInSpreadSheetImpl(SpreadSheet spreadSheet, String term, boolean sensitive) {
        this.spreadSheet = spreadSheet;
        this.term = term;
        this.sensitive = sensitive;
    }

}

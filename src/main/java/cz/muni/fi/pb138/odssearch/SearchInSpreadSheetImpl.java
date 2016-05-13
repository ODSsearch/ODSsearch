package cz.muni.fi.pb138.odssearch;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by karbo on 4.5.16.
 */
public class SearchInSpreadSheetImpl implements SearchInSpreadSheet {
    private List<Result> results = new ArrayList<>();
    private SpreadSheet spreadSheet;
    private String term;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    private void printSheet(Sheet sheet) {
        int cols = sheet.getColumnCount();
        int rows = sheet.getRowCount();

        System.out.println("Sheet: " + sheet.getName());

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (col != 0)  System.out.print(", ");
                System.out.print(sheet.getValueAt(col, row).toString());
            }
            System.out.println();
        }
    }

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
        Result result = new Result(sheet.getName());

        for (int row = 1; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (sheet.getValueAt(col, row).toString().contains(term)) {
                    result.appendRow(getRow(row, sheet));
                }
            }
        }
        return result;
    }

    @Override
    public void printAllSheets() {
        for (int i = 0; i < spreadSheet.getSheetCount(); i++) {
            printSheet(spreadSheet.getSheet(i));
            System.out.println("---");
        }
    }

    @Override
    public void searchAllSheets() {
        for (int i = 0; i < spreadSheet.getSheetCount(); i++) {
            results.add(searchSheet(spreadSheet.getSheet(i), term));
        }
    }


    public SearchInSpreadSheetImpl(SpreadSheet spreadSheet, String term) {
        this.spreadSheet = spreadSheet;
        this.term = term;
    }

    public List<Result> getResults() {
        return results;
    }
}






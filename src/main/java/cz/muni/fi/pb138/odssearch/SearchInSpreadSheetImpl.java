package cz.muni.fi.pb138.odssearch;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of SearchInSpreadSheet interface. Searches given file for given expression.
 * Created by Karolína Božková on 13.5.16.
 */
public class SearchInSpreadSheetImpl implements SearchInSpreadSheet {

    private List<Result> results = new ArrayList<Result>();
    private SpreadSheet spreadSheet;
    private String expression;
    private boolean sensitive = false;

    private int getColumnCount(Sheet sheet){
        int columnCount = 1;
        while (columnCount < sheet.getColumnCount() && !sheet.getValueAt(columnCount,0).toString().isEmpty()){
            columnCount++;
        }
        return columnCount;
    }

    private List<String> getRow(int row, Sheet sheet) {
        int cols = getColumnCount(sheet);
        List<String> line = new ArrayList<String>();

        for (int i = 0; i < cols; i++) {
            line.add(sheet.getValueAt(i, row).toString());
        }
        return line;
    }

    private boolean containsSensitive(List<String> line, String expression){
        for (String item : line ) {
            if (item.contains(expression)) return true;
        }
        return false;
    }

    private boolean containsInsensitive(List<String> line, String expression){
        for (String item : line ) {
            if (item.toLowerCase().contains(expression.toLowerCase())) return true;
        }
        return false;
    }

    private Result searchSheetSensitive(Sheet sheet) {
        int rows = sheet.getRowCount();
        Result result = new Result(sheet.getName(), getRow(0, sheet));

        for (int row = 1; row < rows; row++) {
            List<String> line = getRow(row, sheet);
            if (containsSensitive(line, expression)) {result.appendRow(line);}
        }
        return result;
    }

    private Result searchSheetInsensitive(Sheet sheet) {
        int rows = sheet.getRowCount();
        Result result = new Result(sheet.getName(), getRow(0, sheet));

        for (int row = 1; row < rows; row++) {
            List<String> line = getRow(row, sheet);
            if (containsInsensitive(line, expression)) {result.appendRow(line);}
        }
        return result;
    }
    
    @Override
    public List<Result> searchAllSheets() throws ServiceFailureException {
        
        if (sensitive){
            for (int i = 0; i < spreadSheet.getSheetCount(); i++) {
                results.add(searchSheetSensitive(spreadSheet.getSheet(i)));
            }
        }
        else {
            for (int i = 0; i < spreadSheet.getSheetCount(); i++) {
                results.add(searchSheetInsensitive(spreadSheet.getSheet(i)));
            }
        }
        return Collections.unmodifiableList(results);
    }

    /**
     * Class constructor, registers parameters needed for searching the file.
     * @param spreadSheet   SpreadSheet file, *.ods document to search through, if null throws exception
     * @param expression    String expression to search for, if null throws exception
     * @param sensitive     Boolean parameter, if true - searching wil be case sensitive,
     *                      else case insensitive. Default value is false.
     * @throws ServiceFailureException If parameters does not meet conditions
     */
    public SearchInSpreadSheetImpl(SpreadSheet spreadSheet, String expression, boolean sensitive) throws ServiceFailureException {
        validate(spreadSheet, expression);
        this.spreadSheet = spreadSheet;
        this.expression = expression.trim();
        this.sensitive = sensitive;
    }
    
    private void validate(SpreadSheet spreadSheet, String expression) throws ServiceFailureException {
        if(spreadSheet == null) {
            throw new ServiceFailureException("Null spread sheet to be searched");
        }
        
        if(expression == null) {
            throw new ServiceFailureException("Null expression");
        }
        
        if(expression.compareTo("") == 0) {
            throw new ServiceFailureException("Empty expression");
        }
    }
}

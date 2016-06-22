package cz.muni.fi.pb138.odssearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Result class stores name of a sheet, header of the table and rows resulting from search.
 * Created by Karolína Božková on 10.5.16.
 */
public class Result {
    private String sheetName;
    private List<List<String>> rows = new ArrayList<List<String>>();
    private List<String> header = new ArrayList<>();

    /**
     * Constructor that registers name of the sheet and header of the table it contains.
     * @param sheetName String name of the sheet
     * @param header    list containing names of columns the table contains, expected on the
     *                  very 1st row of the table from the 1st column
     */
    public Result(String sheetName, List<String> header) {
        this.sheetName = sheetName;
        this.header = header;
    }

    /**
     * Returns the name of the sheet in witch results have been found.
     * @return  name of the sheet as a string
     */
    public String getSheetName() {
        return sheetName;
    }

    /**
     * Returns unmodifiable list of rows resulting from search.
     * @return  unmodifiable list of rows. Rows are represented as list of strings, each
     *          string represents value of a cell.
     */
    public List<List<String>> getRows() {
        return Collections.unmodifiableList(rows);
    }

    /**
     * Returns header of the table contained in the sheet.
     * @return  list of strings, each string represents name of a column
     */
    public List<String> getHeader() {
        return header;
    }

    /**
     * Registers another row, that contains searched expression.
     * @param row   row that contains expression
     */
    public void appendRow(List<String> row){
        rows.add(row);
    }

    @Override
    public String toString() {
        String str = "Sheet: " + sheetName + '\n' + header + '\n';
        for (List<String> row : rows) {
            str = str.concat(row + "\n");
        }
        return str;
    }
}

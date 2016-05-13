package cz.muni.fi.pb138.odssearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karolína Božková on 10.5.16.
 * Result class stores name of a sheet and rows resulting from search.
 */
public class Result {
    private String sheetName;
    private List<List<String>> rows = new ArrayList<List<String>>();

    public Result(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getSheetName() {
        return sheetName;
    }

    public List<List<String>> getRows() {
        return rows;
    }

    public void appendRow(List<String> row){
        rows.add(row);
    }

    public void printRows(){
        for (List<String> row: rows) {
            for (String item : row) {
                System.out.print(item + ", ");
            }
            System.out.println(" ");
        }
    }

    @Override
    public String toString() {
        return "Result of search in spreadsheet" + sheetName;
    }
}

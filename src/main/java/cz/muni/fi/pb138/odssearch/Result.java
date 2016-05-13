package cz.muni.fi.pb138.odssearch;

import java.util.ArrayList;

/**
 * Created by Karolína Božková on 10.5.16.
 * Result class stores name of a sheet and rows resulting from search.
 */
public class Result {
    private String sheetName;
    private ArrayList<String[]> rows = new ArrayList<String[]>();

    public Result(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getSheetName() {
        return sheetName;
    }

    public ArrayList<String[]> getRows() {
        return rows;
    }

    public void appendRow(String[] row){
        rows.add(row);
    }

    public void printRows(){
        for (String[] row: rows) {
            for (String item : row) {
                System.out.print(item + ", ");
            }
            System.out.println(" ");
        }
    }
}

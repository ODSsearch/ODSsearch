package cz.muni.fi.pb138.odssearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;


/**
 * Created by Karolína Božková on 10.5.16.
 * Result class stores name of a sheet and rows resulting from search.
 */
public class Result {
    private String sheetName;
    private List<List<String>> rows = new ArrayList<List<String>>();
    private List<String> header = new ArrayList<>();

    public Result(String sheetName, List<String> header) {
        this.sheetName = sheetName;
        this.header = header;
    }

    public List<String> getHeader() {
        return header;
    }

    public String getSheetName() {
        return sheetName;
    }

    public List<List<String>> getRows() {
        return Collections.unmodifiableList(rows);
    }

    public void appendRow(List<String> row){
        rows.add(row);
    }

    public void printRows(){
        if (rows.isEmpty()){
            System.out.println("No matches found.");
        }
        else {
            for (List<String> row : rows) {
                for (String item : row) {
                    System.out.print(item + ", ");
                }
                System.out.println(" ");
            }
        }
    }

    @Override
    public String toString() {
        return "Result of search in spreadsheet" + sheetName;
    }
}

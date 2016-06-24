package cz.muni.fi.pb138.odssearch;

import org.jopendocument.dom.spreadsheet.SpreadSheet;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Karolína Božková on 3.5.16.
 * Class to run SearchInSpreadSheet as console app.
 */
public class Main {
    /**
     * Takes no parameters. Asks for file path, expression and case sensitivity, searches
     * file accordingly and writes result to standard output.
     * @param args
     */
    public static void main(String[] args) {
        try {
            //read input and load file
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter file path:");
            String path = reader.readLine().trim();
            if (path.length() == 0 || path.isEmpty()) throw new ServiceFailureException("No filepath.");
            File file = new File(path);
            System.out.print("Enter searching expression: ");
            String expression = reader.readLine();
            System.out.print("Case Sensitive search (y/n):");
            boolean sensitive = reader.readLine().toLowerCase().startsWith("y");

            //search
            SearchInSpreadSheet spreadSheet = new SearchInSpreadSheetImpl(SpreadSheet.createFromFile(file),
                    expression, sensitive);
            List<Result> results = spreadSheet.searchAllSheets();

            //print results
            System.out.println("printing results... ");
            for (Result result : results) {
                if (!result.getRows().isEmpty()) {
                    System.out.println(result.toString());
                }
            }
        } catch (Throwable ex) {
            System.out.println(ex.toString());
        }
    }
}

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
    // /home/karbo/IdeaProjects/pokusODS/pokus/src/main/resources/tableFile.ods

    public static void main(String[] args) {
        try{
            // Load the file.
            File file = new File("src/main/resources/tableFile.ods");

            //read input
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter term: ");
            String term = reader.readLine();

            SearchInSpreadSheet spreadSheet = new SearchInSpreadSheetImpl(SpreadSheet.createFromFile(file), term, false);
            List<Result> results = spreadSheet.searchAllSheets();

            //print results
            for (Result result : results) {
                if (!result.getRows().isEmpty()) {
                    System.out.println("Sheet: " + result.getSheetName());
                    result.printRows();
                }
            }
        } catch(Throwable ex){
            System.out.println(ex.toString());
        }



    }




}

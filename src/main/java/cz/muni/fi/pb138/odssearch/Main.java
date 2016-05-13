package cz.muni.fi.pb138.odssearch;


import org.jopendocument.dom.spreadsheet.SpreadSheet;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * Created by Karolína Božková on 3.5.16.
 * Class to run SearchInSpreadSheetImpl as console app.
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

            SearchInSpreadSheetImpl spreadSheet = new SearchInSpreadSheetImpl(SpreadSheet.createFromFile(file), term);
            spreadSheet.searchAllSheets();
            spreadSheet.printAllSheets();
            //print results
            System.out.println("searching for < " + spreadSheet.getTerm() + " >");
            for (Result result : spreadSheet.getResults() ) {
                System.out.println("Sheet: " + result.getSheetName());
                result.printRows(); //TODO - when no rows found (rows are null)
            }

        } catch(Throwable ex){
            System.out.println(ex.toString());
        }

    }

}

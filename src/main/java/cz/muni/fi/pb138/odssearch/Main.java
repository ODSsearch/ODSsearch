package cz.muni.fi.pb138.odssearch;


import org.jopendocument.dom.spreadsheet.SpreadSheet;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

/**
 * Created by Karolína Božková on 3.5.16.
 * Class to run SearchInSpreadSheet as console app.
 */
public class Main {
    // /home/karbo/IdeaProjects/pokusODS/pokus/src/main/resources/tableFile.ods


    public static void main(String[] args) {
        try{
            // Load the file.
            File file = new File(Main.class.getClassLoader().getResource("tableFile.ods").getFile());

            //read input
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter term: ");
            String term = reader.readLine();

            SearchInSpreadSheet spreadSheet = new SearchInSpreadSheet(SpreadSheet.createFromFile(file), term);
            //print results
            for (Result result : spreadSheet.getResults() ) {
                System.out.println("Sheet: " + result.getSheetName());
                result.printRows(); //TODO - when no rows found (rows are null)
            }

        } catch(Throwable ex){
            System.out.println(ex.toString());
        }



    }




}

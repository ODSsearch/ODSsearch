package cz.muni.fi.pb138.odssearch.web;

import cz.muni.fi.pb138.odssearch.Result;
import cz.muni.fi.pb138.odssearch.SearchInSpreadSheet;
import cz.muni.fi.pb138.odssearch.SearchInSpreadSheetImpl;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Web Search Controller class is used for creating a web form used for full text search in ODS files
 */
@Controller
public class WebSearchController {

    /**
     * Handling form's GET request.
     * @param model Model interface
     * @return      Return template
     */
    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String searchForm(Model model) {
        return "uploadForm";
    }

    /**
     * Handling form's POST request.
     * @param searchExpression      Searched term given from Web form
     * @param file                  File in which given expression should be searched
     * @param caseSensitive         Boolean variable if search should be case in/sensitive
     * @param redirectAttributes    Attributes for the presentation part
     * @return                      Return redirect
     * @throws IOException          If parameters does not meet conditions
     */
    @RequestMapping(method = RequestMethod.POST, value = "/")
    public String handleSearchForm(@RequestParam("searchExpression") String searchExpression,
                                   @RequestParam("file") MultipartFile file,
                                   @RequestParam(value = "caseSensitive", required = false) boolean caseSensitive,
                                   RedirectAttributes redirectAttributes) throws IOException {

        if (!file.isEmpty()) {
            try {
                File source = multipartToFile(file);
                SearchInSpreadSheet spreadSheet = new SearchInSpreadSheetImpl(SpreadSheet.createFromFile(source), searchExpression, caseSensitive);

                List<Result> results = spreadSheet.searchAllSheets();
                if (caseSensitive) {
                    redirectAttributes.addFlashAttribute("searchExpression", "Searched expression (case sensitive): " + searchExpression);
                } else {
                    redirectAttributes.addFlashAttribute("searchExpression", "Searched expression (case insensitive): " + searchExpression);
                }
                redirectAttributes.addFlashAttribute("results", results);

            } catch (NullPointerException e) {
                redirectAttributes.addFlashAttribute("message", "Search failed " + searchExpression + " => file type is not *.ods");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("message", "Search failed " + searchExpression + " => " + e.getMessage());
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "Search failed " + searchExpression + " because the file was empty");
        }
        return "redirect:/";
    }

    private File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File convFile = new File(multipart.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipart.getBytes());
        fos.close();
        return convFile;
    }
}
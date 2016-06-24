package cz.muni.fi.pb138.odssearch;

import org.junit.Before;
import static org.junit.Assert.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import java.io.IOException;
import java.util.List;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


/**
 * Test class for SearchInSpreadSheetImpl
 * @author Miroslav Kubus
 */
public class SearchInSpreadSheetImplTest {
    
    private SearchInSpreadSheetImpl searcher;
    private SpreadSheet spreadSheet;
    
    @Before
    public void setUp() throws IOException {
        final Object[][] subjects = new Object[5][3];
        subjects[0] = new Object[]{1, "MB101", "D"};
        subjects[1] = new Object[]{2, "PB138", "A"};
        subjects[2] = new Object[]{3, "IB002", "FA"};
        subjects[3] = new Object[]{5, "IB001", "C"};
        subjects[4] = new Object[]{4, "mb101", "FFE"};
        
        String[] columns = new String[]{"ID", "Subject", "Grade"};
        TableModel model = new DefaultTableModel(subjects, columns);
        
        spreadSheet = SpreadSheet.createEmpty(model);
        spreadSheet.getFirstSheet().setName("SUBJECTS");
    }
    
    @Rule
    public ExpectedException expectedException= ExpectedException.none();
    
    @Test
    public void specificSensitiveSearchTest() throws ServiceFailureException {
        searcher = new SearchInSpreadSheetImpl(spreadSheet, "PB138", true);
        List<Result> results = searcher.searchAllSheets();
            
        assertEquals(1, results.size());
        assertEquals(1, results.get(0).getRows().size());
        assertEquals("SUBJECTS", results.get(0).getSheetName());
        List<String> foundSubject = results.get(0).getRows().get(0);
        assertEquals("2", foundSubject.get(0));
        assertEquals("PB138", foundSubject.get(1));
        assertEquals("A", foundSubject.get(2));
    }
    
    @Test
    public void specificInsensitiveSearchTest() throws ServiceFailureException {
        searcher = new SearchInSpreadSheetImpl(spreadSheet, "mb", false);
        List<Result> results = searcher.searchAllSheets();
            
        assertEquals(1, results.size());
        assertEquals(2, results.get(0).getRows().size());
        assertEquals("SUBJECTS", results.get(0).getSheetName());
        
        List<String> foundSubject = results.get(0).getRows().get(0);
        assertEquals("1", foundSubject.get(0));
        assertEquals("MB101", foundSubject.get(1));
        assertEquals("D", foundSubject.get(2));
        
        foundSubject = results.get(0).getRows().get(1);
        assertEquals("4", foundSubject.get(0));
        assertEquals("mb101", foundSubject.get(1));
        assertEquals("FFE", foundSubject.get(2));
    }
    
    @Test
    public void emptyResultTest() throws ServiceFailureException {
        searcher = new SearchInSpreadSheetImpl(spreadSheet, "VB001", true);
        List<Result> results = searcher.searchAllSheets();
        
        assertEquals(spreadSheet.getSheetCount(), results.size());
    }
    
    @Test
    public void twoSensitiveResultsOfSearchTest() throws ServiceFailureException {
        searcher = new SearchInSpreadSheetImpl(spreadSheet, "IB", true);
        List<Result> results = searcher.searchAllSheets();
        
        assertEquals(1, results.size());
        assertEquals("SUBJECTS", results.get(0).getSheetName());
        assertEquals(2, results.get(0).getRows().size());
        
        List<String> firstSubject = results.get(0).getRows().get(0);
        assertEquals("3", firstSubject.get(0));
        assertEquals("IB002", firstSubject.get(1));
        assertEquals("FA", firstSubject.get(2));
        
        List<String> secondSubject = results.get(0).getRows().get(1);
        assertEquals("5", secondSubject.get(0));
        assertEquals("IB001", secondSubject.get(1));
        assertEquals("C", secondSubject.get(2));     
    }   
    
    private void prepareSecondSheet() {
        Sheet second = spreadSheet.addSheet("V-SUBJECTS");
        second.ensureColumnCount(2);
        second.ensureRowCount(3);
        
        second.setValueAt("ID", 0, 0);
        second.setValueAt("Subject", 1, 0);
        second.setValueAt("10", 0, 1);
        second.setValueAt("VB001", 1, 1);
        second.setValueAt("11", 0, 2);
        second.setValueAt("VB003", 1, 2);
    }
    
    @Test
    public void secondSheetSearch() throws ServiceFailureException {        
        prepareSecondSheet();
        searcher = new SearchInSpreadSheetImpl(spreadSheet, "VB001", true);
        List<Result> results = searcher.searchAllSheets();
            
        assertEquals(2, results.size());
        assertEquals(0, results.get(0).getRows().size());
        assertEquals("SUBJECTS", results.get(0).getSheetName());
        assertEquals(1, results.get(1).getRows().size());
        assertEquals("V-SUBJECTS", results.get(1).getSheetName());
        
        List<String> foundSubject = results.get(1).getRows().get(0);
        
        assertEquals(2, foundSubject.size());
        assertEquals("10", foundSubject.get(0));
        assertEquals("VB001", foundSubject.get(1));
    }
    
    @Test
    public void searchInBothSheets() throws ServiceFailureException {
        prepareSecondSheet();
        searcher = new SearchInSpreadSheetImpl(spreadSheet, "001", true);
        List<Result> results = searcher.searchAllSheets();
        
        assertEquals(2, results.size());
        assertEquals(1 , results.get(0).getRows().size());
        assertEquals("SUBJECTS", results.get(0).getSheetName());
        assertEquals("V-SUBJECTS", results.get(1).getSheetName());
        
        List<String> foundSubject = results.get(0).getRows().get(0);
        
        assertEquals(3, foundSubject.size());
        assertEquals("5", foundSubject.get(0));
        assertEquals("IB001", foundSubject.get(1));
        assertEquals("C", foundSubject.get(2));
        
        foundSubject = results.get(1).getRows().get(0);
        assertEquals(2, foundSubject.size());
        assertEquals("10", foundSubject.get(0));
        assertEquals("VB001", foundSubject.get(1));
    }
    
    @Test (expected = ServiceFailureException.class)
    public void nullSpreadSheetTest() throws ServiceFailureException {
        searcher = new SearchInSpreadSheetImpl(null, "001", true);
    }

    @Test (expected = ServiceFailureException.class)
    public void nullExpressionTest() throws ServiceFailureException {
        searcher = new SearchInSpreadSheetImpl(spreadSheet, null, true);
    }      
    
    @Test (expected = ServiceFailureException.class)
    public void emptyExpressionTest() throws ServiceFailureException {
        searcher = new SearchInSpreadSheetImpl(spreadSheet, "", true);
    }
    
    @Test (expected = ServiceFailureException.class)
    public void spaceExpressionTest() throws ServiceFailureException {
        searcher = new SearchInSpreadSheetImpl(spreadSheet, " ", true);
    }
    
    @Test
    public void headerTest() throws ServiceFailureException {
        searcher = new SearchInSpreadSheetImpl(spreadSheet, "0", true);
        List<Result> result = searcher.searchAllSheets();
        
        assertEquals(1, result.size());
        
        List<String> header = result.get(0).getHeader();
        assertEquals(3, header.size());
        assertEquals("ID", header.get(0));
        assertEquals("Subject", header.get(1));
        assertEquals("Grade", header.get(2));
    }
}

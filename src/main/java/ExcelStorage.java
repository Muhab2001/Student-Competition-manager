import models.Competition;
import models.Student;
import models.Team;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ExcelStorage {

    BufferedInputStream inStream;
    BufferedOutputStream outStream; // TODO: you should flush after writing immediately !!


    public ExcelStorage() throws FileNotFoundException {
        inStream = new BufferedInputStream(new FileInputStream("storage.xlsx"));
        outStream = new BufferedOutputStream(new FileOutputStream("storage.xlsx"));
    }

    // can be used to both edit teams and competition value itself
    public static void edit(int CompetitionID, Competition newState){}

    public static void addCompetition(Competition competition){}


    public static void deleteCompetition(int CompetitionID){}


//    public static Team getTeam(int CompetitionID, int TeamId){
//        return new Team(); // temporary setup to prevent errors
//
//    }

//    public static Competition getCompetition(int CompetitionId){
//        return new Competition(); // temporary setup to avoid errors
//    }

    public static Competition[] getAllCompetitions(){
        return new Competition[]{}; // temporary setup to avoid errors
    }

    public static void authenticate(int username, String password){}

    // test run for apache poi functions
    public static void run(){
        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Employee Data");

        //This data needs to be written (Object[])
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        data.put("1", new Object[] {"ID", "NAME", "LASTNAME"});
        data.put("2", new Object[] {1, "Amit", "Shukla"});
        data.put("3", new Object[] {2, "Lokesh", "Gupta"});
        data.put("4", new Object[] {3, "John", "Adwards"});
        data.put("5", new Object[] {4, "Brian", "Schultz"});

        //Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset)
        {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 1;
            for (Object obj : objArr)
            {
                Cell cell = row.createCell(cellnum++);
                if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
            }
        }
        try
        {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File("howtodoinjava_demo.xlsx"));
            workbook.write(out);
            out.close();
            System.out.println("howtodoinjava_demo.xlsx written successfully on disk.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void read(){
        try
        {
            FileInputStream file = new FileInputStream(new File("howtodoinjava_demo.xlsx"));

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            // TODO: different sheets are accessed by index and not name
            XSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext())
                {
                    Cell cell = cellIterator.next();
                    //Check the cell type and format accordingly
                    switch (cell.getCellType())
                    {
                        case NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t");
                            break;
                        case STRING:
                            System.out.print(cell.getStringCellValue() + "\t");
                            break;
                    }
                }
                System.out.println("");
            }
            file.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}

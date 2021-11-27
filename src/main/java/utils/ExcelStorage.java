package utils;

import models.Competition;
import models.Student;
import models.Team;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;

public class ExcelStorage {

    BufferedInputStream inStream;
    BufferedOutputStream outStream; // TODO: you should flush after writing immediately !!
    public ExcelStorage INSTANCE = new ExcelStorage();

    private ExcelStorage() throws FileNotFoundException {
        inStream = new BufferedInputStream(new FileInputStream("storage.xlsx"));
        outStream = new BufferedOutputStream(new FileOutputStream("storage.xlsx"));
    }




//    public static Competition getCompetition(int CompetitionId){
//        return new Competition(); // temporary setup to avoid errors
//    }
    // at the end
    public static ArrayList<Competition> getAllCompetitions(){
        return new ArrayList<Competition>(); // temporary setup to avoid errors
    }

    // at the end
    public static void saveChanges(Competition[] competitions){

    }

    // test run for apache poi functions
    public static void run(){
        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Employee Data2");

        //This data needs to be written (Object[])
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        data.put("3", new Object[] {"Student ID", "NAME", "LASTNAME"});
        data.put("4", new Object[] {1, "Amit", "Shukla"});
        data.put("5", new Object[] {2, "Lokesh", "Gupta"});
        data.put("6", new Object[] {3, "John", "Adwards"});
        data.put("7", new Object[] {4, "Brian", "Schultz"});

        //Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset)
        {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 4;
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

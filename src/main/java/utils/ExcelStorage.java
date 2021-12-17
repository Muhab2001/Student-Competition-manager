package utils;
import models.Competition;
import models.Student;
import models.Team;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * a singleton class that manages the data traffic between the structured
 * excel spreadsheet and KFUPM Medalist application
 */
public class ExcelStorage {

    public ExcelStorage INSTANCE = new ExcelStorage();
    private ExcelStorage() throws FileNotFoundException {

    }
    //TODO: delete the testing main method
//    public static void main(String[] args) throws IOException {
//        try {
//            FileInputStream file = new FileInputStream(new File("storage.xlsx"));
//
//            //Create Workbook instance holding reference to .xlsx file
//            XSSFWorkbook workbook = new XSSFWorkbook(file);
//
//            saveChanges(getAllCompetitions());
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
    /**
     * iterates over every single sheet in a structured excel spreadsheet as the following
     *
     */
    public static ArrayList<Competition> getAllCompetitions() {
        ArrayList<Competition> competitions = new ArrayList<>(); // to save competitions for the CompetitionMemory
        try {
            FileInputStream file = new FileInputStream(new File("storage.xlsx")); // file name directory
            DateFormat dateFormatter = new SimpleDateFormat("M/d/yyyy"); // an accepted date format by the excel API
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            if(workbook.getSheetAt(0).getRow(0) != null){
                for (int i = 0; i < workbook.getNumberOfSheets(); i++) {

                    // getting description data to format a Competition Object
                    Sheet sheet = workbook.getSheetAt(i);
                    // instantiating competition descriptors data
                    String dueDate = "", name = "", websiteLink = "";
                    int teamSize = -1, index = i;
                    int rowStart = 0;
                    int rowEnd = 3;
                    for (int j = rowStart; j <= rowEnd; j++) {
                        Row row = sheet.getRow(j);
                        Iterator<Cell> cellIterator = row.cellIterator();


                        Cell cell = cellIterator.next(); // we skip the static header column
                        cell = cellIterator.next(); // we only take the value
                        switch (j) {
                            case 0 -> name = cell.getStringCellValue();
                            case 1 -> websiteLink = cell.getStringCellValue();
                            case 2 -> dueDate = dateFormatter.format(cell.getDateCellValue());
                            case 3 -> teamSize = (int) cell.getNumericCellValue();
                        }


                    }

                    // create a competition object to store descriptors and accept team objects
                    Competition competition = new Competition(dueDate, name, teamSize, websiteLink, index);

                    // populate the teams' data in the competition object;
                    rowStart = 6; // the row to start fetching student information
                    int teamIndex = 0; // index to keep track of team index
                    int studentCounter = 0; // index to keep track of student count, and switch team objects
                    Team team = new Team(teamIndex, competition.teamSize); // team object to store student groups
                    Row studentRow = sheet.getRow(rowStart);
                    while (studentRow != null) {
                        Iterator<Cell> cellIterator = studentRow.cellIterator();
                        Student tmpStudent = new Student(studentCounter++);
                        int j = 0;
                        studentInfoIterator:
                        while (j < 5) {
                            Cell cell = cellIterator.next();
                            switch (j) {
                                case 0: // using the first column as the presence indicator
                                    if (cell.getCellType() != CellType.NUMERIC) {
                                        break studentInfoIterator; // skip the empty row to the next students
                                    }
                                    break;
                                case 1: // reading student id column, and converting it to a string
                                    tmpStudent.id = cell.getStringCellValue();
                                    break;
                                case 2: // reading the student name column
                                    tmpStudent.name = cell.getStringCellValue();
                                    break;
                                case 3: // reading the student major column
                                    tmpStudent.major = cell.getStringCellValue();
                                    break;
                                case 4: // reading a possible rank column
                                    if (cell.getCellType() == CellType.NUMERIC) { // if the rank is not empty, save it under the team object
                                        team.rank = (int) cell.getNumericCellValue();

                                    }

                            }
                            j++;
                        }

                        team.students.add(tmpStudent);
                  /*
                  when reaching full capacity, reset student counter,
                  increment the team index, add the full team to the competition
                  and create a new empty team for the next teamsSet
                   */
                        if (teamSize == studentCounter) {
                            studentCounter = 0;
                            competition.teams.add(team);
                            team = new Team(++teamIndex, competition.teamSize);
                        }

                        studentRow = sheet.getRow(++rowStart); // go for the next team;
                    }

                    competitions.add(competition);
                }
                CompetitionsMemory.competitions = competitions;
            }else{
                CompetitionsMemory.competitions = new ArrayList<>();
            }

            file.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return  competitions; // temporary setup to avoid errors
    }
    /**
     *  a function that saves the current CompetitionsMemory into the excel sheet by the following
     *
     * @param competitions: the list of all competitions stored in a `CompetitionsMemory`object
     */
    public static void saveChanges(ArrayList<Competition> competitions) {

        //Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        // creating a uniform date format
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(workbook.createDataFormat().getFormat("M/d/yyyy"));

        if(!getAllCompetitions().isEmpty()){//create a sheet for each competition
            for (Competition competition : competitions) {
                XSSFSheet sheet = workbook.createSheet(competition.name);
                // STEP1: populating descriptors' data first
                Map<String, Object[]> descriptionData = new TreeMap<>();

                descriptionData.put("1", new Object[]{"Competition Name", competition.name});
                descriptionData.put("2", new Object[]{"Competition Link", competition.websiteLink});
                descriptionData.put("3", new Object[]{"Competition Date", competition.dueDate});
                descriptionData.put("4", new Object[]{"Team Size", competition.teamSize});

                // mapping the values in the sheet
                int rownum = 0;
                Set<String> keyset = descriptionData.keySet();
                for (String key : keyset) {
                    Row row = sheet.createRow(rownum++);
                    Object[] objArr = descriptionData.get(key);
                    int cellNum = 0;
                    for (Object obj : objArr) {
                        Cell cell = row.createCell(cellNum++);
                        if (rownum == 3 && cellNum == 2) {
                            cell.setCellValue(LocalDate.parse((String) obj, dateTimeFormatter));
                            cell.setCellStyle(dateCellStyle);
                        } else {
                            if (obj instanceof String) {
                                cell.setCellValue((String) obj);
                            } else {
                                cell.setCellValue((Integer) obj);
                            }
                        }

                    }
                }

                //STEP2:  populate the teams information

                // styling teams borders
                CellStyle style = workbook.createCellStyle();
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                style.setBorderTop(BorderStyle.THIN);
                style.setBorderLeft(BorderStyle.THIN);

                Map<Integer, Object[]> teamsData = new TreeMap<>();

                // add the descriptor column
                teamsData.put(1, new Object[]{"", "Student ID", "Student Name", "Major", "Rank"});
                //
                int studentIndex = 1;
                int currentKey = 2;
                for (Team team : competition.teams) {
                    for (Student student : team.students) {
                        if (student.index == 0) {
                            if (team.rank != -1)
                                teamsData.put(currentKey++, new Object[]{studentIndex++, student.id, student.name, student.major, team.rank});
                            else
                                teamsData.put(currentKey++, new Object[]{studentIndex++, student.id, student.name, student.major, "-"});
                        } else {
                            if (student.name.length() == 0) {
                                teamsData.put(currentKey++, new Object[]{"-", "-", "-", "-", "-"});
                            } else
                                teamsData.put(currentKey++, new Object[]{studentIndex++, student.id, student.name, student.major, "-"});
                        }
                    }
                }
                // mapping team values on the sheet
                Set<Integer> dataset = teamsData.keySet();
                rownum = 5;
                for (Integer key : dataset) {
                    Row row = sheet.createRow(rownum++);
                    Object[] objArr = teamsData.get(key);
                    int cellnum = 0;

                    for (Object obj : objArr) {
                        Cell cell = row.createCell(cellnum++);
                        if (obj instanceof String) {
                            cell.setCellValue((String) obj);
                        } else if (obj instanceof Integer) {
                            cell.setCellValue((Integer) obj);
                        }
                        cell.setCellStyle(style);
                    }
                }

                for (int i = 0; i < 5; i++) {
                    sheet.autoSizeColumn(i);
                }


            }
        }else {
            workbook= new XSSFWorkbook();
            workbook.createSheet("empty competition");
        }

        try {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File("storage.xlsx"));
            workbook.write(out);
            out.close();
            System.out.println("run successful");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

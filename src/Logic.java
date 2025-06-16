import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Logic {
    int amountOfLines = 0;
    ArrayList<Long> TotalTime_ns_arr = new ArrayList<>();
    ArrayList<Long> TotalQueries_arr = new ArrayList<>();
    ArrayList<Long> CreateTime_ns_arr = new ArrayList<>();
    ArrayList<Long> ReadTime_ns_arr = new ArrayList<>();
    ArrayList<Long> UpdateTime_ns_arr = new ArrayList<>();
    ArrayList<Long> DeleteTime_ns_arr = new ArrayList<>();
    String batchSize = "";
    long TotalTime_ns = 0;
    long TotalQueries = 0;
    long CreateTime_ns = 0;
    long ReadTime_ns = 0;
    long UpdateTime_ns = 0;
    long DeleteTime_ns = 0;
    String header = "";

    public void convertFileToLine() {
        File out = new File("out/out.csv");
        try {
            out.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }

        File folder = new File("in/");
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles == null){
            System.out.print("No Files In Dir");
            System.exit(404);
        }
        for (File f : listOfFiles) {
            readLines(f);
            calcLinesAvg();
            String createdLine = createFormatedString(f);
            System.out.println(createdLine);
            writeCSVLine(out, createdLine);
        }
    }

    private String createFormatedString(File f) {
        String out = f.getName() + "," + TotalTime_ns + "," + TotalQueries + "," + batchSize + "," + CreateTime_ns + ","
                + ReadTime_ns
                + "," + UpdateTime_ns + "," + DeleteTime_ns;
        return out;
    }

    private void writeCSVLine(File f, String in) {

        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(f, true));
            if (f.length() < 10) {
                writer.append(header);
            }
            writer.newLine();
            writer.append(in);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void calcLinesAvg() {
        for (long l : TotalTime_ns_arr) {
            TotalTime_ns = TotalTime_ns + l;
        }
        for (long l : TotalQueries_arr) {
            TotalQueries = TotalQueries + l;
        }
        for (long l : CreateTime_ns_arr) {
            CreateTime_ns = CreateTime_ns + l;
        }
        for (long l : ReadTime_ns_arr) {
            ReadTime_ns = ReadTime_ns + l;
        }
        for (long l : UpdateTime_ns_arr) {
            UpdateTime_ns = UpdateTime_ns + l;
        }
        for (long l : DeleteTime_ns_arr) {
            DeleteTime_ns = DeleteTime_ns + l;
        }

        TotalTime_ns = TotalTime_ns / amountOfLines;
        TotalQueries = TotalQueries / amountOfLines;
        CreateTime_ns = CreateTime_ns / amountOfLines;
        ReadTime_ns = ReadTime_ns / amountOfLines;
        UpdateTime_ns = UpdateTime_ns / amountOfLines;
        DeleteTime_ns = DeleteTime_ns / amountOfLines;

    }

    private void readLines(File f) {
        try {
            Scanner myReader = new Scanner(f);
            boolean isNotEnd = true;

            for (int i = 0; isNotEnd; i++) {
                String buffer = myReader.nextLine();
                amountOfLines++;
                if (buffer.equals(",")) {
                    amountOfLines = amountOfLines - 2;
                    i = 0;
                    isNotEnd = false;
                }
                if (i != 0) {
                    int n = 0;
                    String[] splitBuffer = buffer.split(",");
                    for (String s : splitBuffer) {

                        switch (n) {
                            case 1:
                                n++;
                                TotalTime_ns_arr.add(Long.parseLong(s));
                                break;
                            case 2:
                                TotalQueries_arr.add(Long.parseLong(s));
                                n++;
                                break;
                            case 3:
                                if (batchSize.length() == 0) {
                                    batchSize = s;
                                }
                                n++;
                                break;
                            case 4:
                                CreateTime_ns_arr.add(Long.parseLong(s));
                                n++;
                                break;
                            case 5:
                                ReadTime_ns_arr.add(Long.parseLong(s));
                                n++;
                                break;
                            case 6:
                                UpdateTime_ns_arr.add(Long.parseLong(s));
                                n++;
                                break;
                            case 7:
                                DeleteTime_ns_arr.add(Long.parseLong(s));
                                n = 0;
                                break;
                            case 0:
                                n++;
                                break;
                        }

                    }
                } else if (header == "") {
                    header = buffer;

                }
            }

            myReader.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}

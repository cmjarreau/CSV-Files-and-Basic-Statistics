import org.apache.commons.csv.*;
import java.util.Scanner;

public class Runner {

    public static void main(String[] args) {
        // switch statement between parsing questions or temperature questions
        System.out.print("Enter 1 for parse data question or 2 for temperature question: ");
        Scanner scanner = new Scanner(System.in);
        int questionType = scanner.nextInt();

        switch (questionType) {
            case 1:
                System.out.println("parse data question");
                parsingQuestionSet();
                break;
            case 2:
                System.out.println("temperature question");
                temperatureQuestionSet();
                break;
        }
    }

    public static void parsingQuestionSet() {
        FileService fileHandler = new FileService();
        ParsingExportData1 ped = new ParsingExportData1();

        CSVParser parser = fileHandler.getCSVParser();

        /*
         * Question 1
         */
        System.out.println("What is the name of the country that is listed as the second country that exports both cotton and flowers?");
        ped.listExportersTwoProducts(parser, "cotton", "flowers");

        /*
         * Question 2
         */
        parser = fileHandler.refreshCSVParser();
        System.out.println("How many countries export cocoa?");
        int cocoaCount = ped.numberOfExporters(parser, "cocoa");
        System.out.println("number of cocoa exporters: " + cocoaCount);

        /*
         * Question 3
         */
        parser = fileHandler.refreshCSVParser();
        // What is the name of the third country (on the third line of the output)
        // listed whose exports are valued at one trillion US dollars or more?
        // (Hint: Their value in the CSV file should be greater than $999,999,999,999.)
        System.out.println("What is the name of the third country (on the third line of the output) " +
                "listed whose exports are valued at one trillion US dollars or more?");
        ped.bigExporters(parser, "$999,999,999,999");
    }

    public static void temperatureQuestionSet() {
        FileService fileHandler = new FileService();
        Temperature temp = new Temperature();
        CSVParser parser = fileHandler.getCSVParser();

        /*
         * Question 1 (4)
         */
        System.out.println("Sample Question: determine the lowest humidity in the file for June 29th, 2014 (weather-2014-06-29.csv).");
        System.out.println("What was the lowest humidity reading on that day?");
        CSVRecord csv = temp.lowestHumidityInFile(parser);
        System.out.format("Lowest Humidity was %s at %s", csv.get("Humidity"), csv.get("DateUTC"));
        System.out.println();

        parser = fileHandler.refreshCSVParser();

        /*
         * Question 2 (5)
         */
        //Run your program from programming exercise Parsing Weather Data to determine
        // the lowest humidity in the file for July 22nd, 2014 (weather-2014-07-22.csv).
        //At what time of day did that humidity occur?
        System.out.println("Sample Question: determine the lowest humidity in the file for July 22nd, 2014 (weather-2014-07-22.csv).");
        System.out.println("At what time of day did that humidity occur?");
        csv = temp.lowestHumidityInFile(parser);
        System.out.format("Lowest Humidity was %s at %s", csv.get("Humidity"), csv.get("DateUTC"));
        System.out.println();

        /*
         * Question 3 (6)
         */
        // determine the lowest humidity reading in the entire year of 2013.
        System.out.println("Sample Question: determine the lowest humidity reading in the entire year of 2013");
        System.out.println("What was the lowest humidity reading?");
        CSVRecord lowestHumidityManyFiles = temp.lowestHumidityInManyFiles();
        System.out.format("Lowest Humidity was %s at %s", lowestHumidityManyFiles.get("Humidity"), lowestHumidityManyFiles.get("DateUTC"));

        /*
         * Question 4 (7)
         */
        System.out.println();
        System.out.println("At what time of day did that lowest humidity occur?");
        System.out.println(lowestHumidityManyFiles.get("DateUTC"));

        /*
         * Question 5 (8)
         */
        System.out.println("Sample Question: determine the average temperature in Fahrenheit on August 10, 2013 (weather-2013-08-10.csv).");
        parser = fileHandler.refreshCSVParser();
        Double averageTemperatureInFile = temp.averageTemperatureInFile(parser);
        System.out.format("Average temperature in file is %f", averageTemperatureInFile);
        System.out.println();

        /*
         * Question 6 (9)
         */
        parser = fileHandler.refreshCSVParser();
        System.out.println("Sample Question: determine the average temperature in Fahrenheit for those temperature readings " +
                "when the humidity is greater than or equal to 80 on September 2, 2013 (weather-2013-09-02.csv).");
        try {
            Double averageTempWithHighHumidity = temp.averageTemperatureWithHighHumidityInFile(parser, 80);
            System.out.format("Average temperature when high humidity is %f", averageTempWithHighHumidity);
            System.out.println();
        } catch (NullPointerException err) {
            System.out.println("No temperatures with that humidity");
        }

        /*
         * Question 7 (10)
         */
        System.out.println("Sample Question: determine which day of the year had the coldest temperature in 2013.");
        String coldestFile = temp.fileWithColdestTemperature();
        System.out.format("The coldest day was in file : %s", coldestFile);
        System.out.println();


        /*
         * Question 8 (11)
         */
        System.out.println("Sample Question: What was the coldest temperature recorded in 2013?");
        coldestFile = temp.fileWithColdestTemperature();
        System.out.format("The coldest day was in file : %s", coldestFile);

        // what year - 2012, 2013, 2014, 2015
        //String fileResourcePath = "data/2013/"+coldestFile;
        String fileResourcePath = "data/2013/"+coldestFile;
        //if (coldestFile.contains("2012")) {
        //    fileResourcePath = fileResourcePath.concat("2012/"+coldestFile);
        //} else if (coldestFile.contains("2013")) {
        //    fileResourcePath = fileResourcePath.concat("2013/"+coldestFile);
        //} else if (coldestFile.contains("2014")) {
        //    fileResourcePath = fileResourcePath.concat("2014/"+coldestFile);
        //} else {
        //    fileResourcePath = fileResourcePath.concat("2015/"+coldestFile);
        //}
        System.out.println();
        //System.out.println(fileResourcePath);

        //FileResource file = new FileResource(fileResourcePath);
        parser = fileHandler.refreshCSVParser(fileResourcePath);
        CSVRecord coldestHour = temp.coldestHourInFile(parser);
        System.out.format("The coldest temperature on that day was: %s ", coldestHour.get("TemperatureF"));
    }
}

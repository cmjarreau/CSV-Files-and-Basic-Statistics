import edu.duke.*;
import org.apache.commons.csv.*;

import java.io.*;

public class Temperature {
    public Temperature() {}

    public CSVRecord coldestHourInFile(CSVParser parser) {
        CSVRecord coldestHourRecord = null;
        Double coldestTemp = null;
        for (CSVRecord record : parser) {
            Double currentTemp = Double.parseDouble(record.get("TemperatureF"));
            //System.out.println(currentTemp + " <- current || coldest -> " + coldestTemp);
            if (coldestHourRecord == null) {
                coldestHourRecord = record;
            }
            if (coldestTemp == null) {
                coldestTemp = currentTemp;
            } else if (currentTemp <= coldestTemp && currentTemp > -100) {
                coldestTemp = currentTemp;
                coldestHourRecord = record;
            }
        }
        return coldestHourRecord;
    }

    public String fileWithColdestTemperature() {
        DirectoryResource dr = new DirectoryResource();
        String coldestFile = null;
        CSVRecord coldestTemp = null;
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVParser parser = fr.getCSVParser();
            CSVRecord currentColdestFile = coldestHourInFile(parser);
            //System.out.println(currentColdestFile);
            if (coldestTemp == null) {
                //System.out.println("coldest temp was null. assigning value");
                coldestTemp = currentColdestFile;
                //System.out.println("coldest temp " + coldestTemp.get("TemperatureF"));
                coldestFile = f.getName();
            } else {
                Double coldestTempTemperature = Double.parseDouble(coldestTemp.get("TemperatureF"));
                //System.out.println("--- coldest temp temperature " + coldestTempTemperature);
                //System.out.println("--- coldest file " + currentColdestFile);
                Double currentTempTemperature = Double.parseDouble(currentColdestFile.get("TemperatureF"));
                //System.out.println("--- current temp temperature " + currentTempTemperature);

                if (currentTempTemperature <= coldestTempTemperature) { // && currentTempTemperature > -100) {
                    coldestTemp = currentColdestFile;
                    coldestFile = f.getName();
                }
            }
        }
        return coldestFile;
    }

    public CSVRecord lowestHumidityInFile(CSVParser parser) {
        CSVRecord lowestHumidityRecord = null;
        Double lowestHumidity = null;
        for (CSVRecord record : parser) {
            if (lowestHumidityRecord == null) {
                lowestHumidityRecord = record;
            }
            if (!record.get("Humidity").equals("N/A")) {
                Double currentHumidity = Double.parseDouble(record.get("Humidity"));
                if (lowestHumidity == null) {
                    lowestHumidity = currentHumidity;
                } else if (currentHumidity < lowestHumidity) {
                    lowestHumidity = currentHumidity;
                    lowestHumidityRecord = record;
                }
            }
        }
        return lowestHumidityRecord;
    }

    public CSVRecord lowestHumidityInManyFiles() {
        DirectoryResource dr = new DirectoryResource();
        CSVRecord lowestHumidityRecord = null;
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVParser parser = fr.getCSVParser();
            CSVRecord currentLowestHumidityFile = lowestHumidityInFile(parser);
            if (lowestHumidityRecord == null) {
                lowestHumidityRecord = currentLowestHumidityFile;
            } else {
                if (!currentLowestHumidityFile.get("Humidity").equals("N/A")) {
                    Double lowestHumidityValue = Double.parseDouble(lowestHumidityRecord.get("Humidity"));
                    Double currentHumidityValue = Double.parseDouble(currentLowestHumidityFile.get("Humidity"));
                    if (currentHumidityValue < lowestHumidityValue) {
                        lowestHumidityRecord = currentLowestHumidityFile;
                    }
                }
            }
        }
        return lowestHumidityRecord;
    }

    public Double averageTemperatureInFile(CSVParser parser) {
        int count = 0;
        Double averageTemp = null;
        for (CSVRecord record : parser) {
            //first make sure that the temperature is greater than -100
            // degress to throw out the bogus values
            if (Double.parseDouble(record.get("TemperatureF")) > -100) {
                Double currTemp = Double.parseDouble(record.get("TemperatureF"));
                if (averageTemp == null) {
                    averageTemp = currTemp;
                } else {
                    averageTemp = averageTemp + Double.parseDouble(record.get("TemperatureF"));
                }
                count++;
            }
        }
        return (double) averageTemp / count;
    }

    public Double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value) {
        // this method returns a double that represents the average temperature of only
        // those temperatures when the humidity was greater than or equal to a value

        int count = 0;
        Double averageTemp = null;
        for (CSVRecord record : parser) {
            // so if humidity >= some value
            if (Double.parseDouble(record.get("Humidity")) >= (double) value) {
                Double currTemp = Double.parseDouble(record.get("TemperatureF"));
                if (averageTemp == null) {
                    averageTemp = currTemp;
                } else {
                    averageTemp = averageTemp + Double.parseDouble(record.get("TemperatureF"));
                }
                count++;
            }
        }
        return (double) averageTemp / count;
    }
}

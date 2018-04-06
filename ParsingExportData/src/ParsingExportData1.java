import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class ParsingExportData1 {
    public ParsingExportData1() {}

    public String countryInfo(CSVParser parser, String country) {
        String countryExistsInRecord = "";
        String exports = "";
        String value = "";
        for (CSVRecord record : parser) {
            String currentCountry = record.get("Country");
            //System.out.println("--- " + currentCountry);
            if (currentCountry.equals(country)) {
                countryExistsInRecord = country;
                exports = record.get("Exports");
                value = record.get("Value (dollars)");
                break;
            }
        }
        if (!countryExistsInRecord.isEmpty()) {
            return countryExistsInRecord + ": " + exports + ": " + value;
        } else {
            return "NOT FOUND";
        }
    }

    public void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2) {
        // this method prints the names of all of the countries that have both export item 1 and 2
        //gold and diamonds - nambia and south africa
        //String export1 = "";
        for (CSVRecord record : parser) {
            String currentExports = record.get("Exports");
            //System.out.println(currentExports);
            String currentCountry = record.get("Country");
            //System.out.println(currentCountry);
            if (currentExports.contains(exportItem1) && currentExports.contains(exportItem2)) {
                System.out.println(currentCountry);
            }
        }
    }

    public int numberOfExporters(CSVParser parser, String exportItem) {
        //this method returns the number of countries that export exportItem
        int number = 0;
        for (CSVRecord record : parser) {
            String exportsList = record.get("Exports");
            if (exportsList.contains(exportItem)) {
                number++;
            }
        }
        return number;
    }

    public void bigExporters(CSVParser parser, String amount) {
        for (CSVRecord record : parser) {
            int valueLen = record.get("Value (dollars)").length();
            if (valueLen > amount.length()) {
                System.out.print(record.get("Country") + " ");
                System.out.println(record.get("Value (dollars)"));
            }
        }
    }
}


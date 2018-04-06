import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;

import java.io.IOException;

public class FileService {
    CSVParser parser;
    public FileService() {
        FileResource fr = new FileResource();
        this.parser = fr.getCSVParser();
    }

    public CSVParser getCSVParser() {
        return this.parser;
    }

    public CSVParser refreshCSVParser() {
        try {
            this.parser.close();
        } catch (IOException ex) {
            System.out.println("Failed to close parser: " + ex);
        }
        FileResource fr = new FileResource();

        this.parser = fr.getCSVParser();
        return this.parser;
    }

    public CSVParser refreshCSVParser(String filename) {
        try {
            this.parser.close();
        } catch (IOException ex) {
            System.out.println("Failed to close parser: " + ex);
        }
        FileResource fr = new FileResource(filename);

        this.parser = fr.getCSVParser();
        return this.parser;
    }
}

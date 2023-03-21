package filehandling;

import app.Wage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Abstract class Writer
 *
 * @author Václav Kurel
 */
public abstract class Writer {

    /**
     * Metoda pro nalezení cesty k souborům v adresáři data
     */
    public static File dataDirectory = new File(System.getProperty("user.dir") + File.separator + "data");
    
    public void checkDataDirectory(){
        Writer.dataDirectory.mkdir();
    }
    
    /**
     * Method for saving the results of the calculated wages
     * @param resultFile - result file name
     * @param wages - list with employee's wages
     * @throws IOException
     */
    public abstract void saveResults(String resultFile, List<Wage> wages) throws IOException;
    
    
}

package filehandling;

import app.Wage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Abstraktní třída zapisovač
 *
 * @author Václav Kurel
 */
public abstract class Writer {

    /**
     * Metoda pro nalezení cesty k souborům v adresáři data
     */
    public static File dataDirectory = new File(System.getProperty("user.dir") + File.separator + "data");

    /**
     * Metoda pro uložení výsledků vypočtené mzdy
     * @param resultFile - název výsledného souboru
     * @param wages - list s mzdami zaměstnanců
     * @throws IOException
     */
    public abstract void saveResults(String resultFile, List<Wage> wages) throws IOException;
    
}

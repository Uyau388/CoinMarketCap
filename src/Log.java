import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
    public Logger logger;
    public FileHandler fileHandler;


    public Log(String fileName) throws IOException {
        File file = new File(fileName);

        // Tạo nếu file chưa có
        if (!file.exists()){
            file.createNewFile();
        }

        fileHandler =  new FileHandler(fileName, true);
        logger = Logger.getLogger(Log.class.getName()); // Log
        logger.addHandler(fileHandler);

        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);
    }

}

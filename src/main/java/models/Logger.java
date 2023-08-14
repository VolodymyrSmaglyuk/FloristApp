package models;

import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class Logger {
    public static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger
            (Logger.class.getName());
    public static void SetupLogger() throws IOException {
        Handler fileHandler = new FileHandler("C:\\LP\\ПП\\JavaFXtest\\Logger.log");
        Logger.logger.setUseParentHandlers(false);
        fileHandler.setFormatter(new Logger.MyFormatter());
        Logger.logger.addHandler(fileHandler);
    }

    static class MyFormatter extends Formatter {
        @Override
        public String format(LogRecord record) {

            Date date = new Date(record.getMillis());
            return "\nDate: " + date + "\nLevel :" + record.getLevel() + "\nClass:" + record.getSourceClassName() +
                    " Method name: " + record.getSourceMethodName() + "\nMessage: " + record.getMessage() + "\n";
        }
    }
}

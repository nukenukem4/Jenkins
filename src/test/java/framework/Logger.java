package framework;

import org.apache.logging.log4j.LogManager;
import org.testng.Reporter;


public class Logger {
    private static final Logger LOGGER = new Logger();
    private static org.apache.logging.log4j.Logger log = LogManager.getLogger();

    private Logger() {
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public void info(Object message) {
        Reporter.log(message.toString());
        log.info(message);
    }
}
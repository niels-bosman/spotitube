package spotitube;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class App
{
    public static void main(String[] args)
    {
        // Lab research: How to log in different ways.
        Logger logger = LoggerFactory.getLogger(App.class);

        logger.info("Info log");
        logger.warn("Warning log");
        logger.error("Error log");
    }
}

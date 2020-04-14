/**
 * 
 */
package fr.eni.encheres.log;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Classe en charge de
 * @author loan.pirotais
 * @version EniEncheres - v1.0
 * @date 7 avr. 2020
 */
public class MonLogger {
	
	public static FileHandler fh = null;
	public static ConsoleHandler ch = null;
	
	public static Logger getLogger(String classname) {
		Logger logger =  Logger.getLogger(classname);
		
		logger .setLevel(Level.FINEST);
        logger .setUseParentHandlers(false);

        if(ch == null)
        {
            ch = new ConsoleHandler();
            ch.setLevel(Level.FINEST);
        }

        if(fh == null)
        {
            try
            {
            	fh = new FileHandler("C:/log/beDeveloper.log");
                //fh = new FileHandler("erreurs.log");
            }
            catch (SecurityException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
            	System.out.println(e.getMessage());
                e.printStackTrace();
            }
            fh.setLevel(Level.ALL);
            fh.setFormatter(new SimpleFormatter());
        }

        logger .addHandler(ch);
        logger .addHandler(fh);

        return logger ;
	}

}

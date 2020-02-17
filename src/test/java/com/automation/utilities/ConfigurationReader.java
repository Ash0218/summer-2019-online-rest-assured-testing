package com.automation.utilities; // 012420 - copied

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {
    // this class will be responsible for loading properties file and will
    //  provide access to values based on key names.
    // We use this class to load custom .properties files
    private static Properties configFile; // 1

    static { // 2
        try { // 4
            FileInputStream fileInputStream = new FileInputStream("configuration.properties"); // 3
            // this class makes us to connect other files outside this class
            //  to get information. For that, we have to connect with the
            //  configuration.properties file. (initialize properties file)
            // FileInputStream on the right side throws an error -> need
            //  try/ catch to solve this.
            // Make sure that "configuration.properties" is same name with
            //  configuration.properties file.

            configFile = new Properties(); // 7
            // load your properties file

            configFile.load(fileInputStream); // 8
            // .load -> error -> replace Exception
            // load configuration.properties file

            fileInputStream.close(); // 12
            // close input stream
            // 1:28

        } catch (IOException e){ // 5
            // If exception occurs, code inside a catch block will be executed.
            // Any class that is related to InputOutput produce checked exception
            //  without handling checked exception, you cannot run a code.
            // IOException is the parent exception for all input, output.

            System.out.println("Failed to load properties file!"); // 9

            e.printStackTrace(); // 6
            // it means print error information
        }

    }


    public static String getProperty(String key){ // 10
        return configFile.getProperty(key); // 11
    }

}


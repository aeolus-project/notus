/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package notus;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;

//import com.google.gson.Gson;
import org.json.simple.JSONValue;

import com.sun.tools.javac.util.Paths;

/**
 *
 * @author mohamed
 */
public class JSONUtils {

    public static <T> String serializeData(T data) throws Exception {
         return JSONValue.toJSONString(data);
    }

    public static <T> T deSerializeData(String infoData) throws Exception {
        return (T) JSONValue.parse(infoData);
    }

    
    public static <T> T deSerializeData(File fileData) throws Exception {
        return deSerializeData(deserializeAsString(fileData));
    }
    
    /**
     * Load a text file contents as a <code>String<code>.
     * This method does not perform enconding conversions
     *
     * @param file The input file
     * @return The file contents as a <code>String</code>
     * @exception IOException IO Error
     */
    public static String deserializeAsString(File file)
    throws IOException {
        int len;
        char[] chr = new char[4096];
        final StringBuffer buffer = new StringBuffer();
        final FileReader reader = new FileReader(file);
        try {
            while ((len = reader.read(chr)) > 0) {
                buffer.append(chr, 0, len);
            }
        } finally {
            reader.close();
        }
        return buffer.toString();
    }
}

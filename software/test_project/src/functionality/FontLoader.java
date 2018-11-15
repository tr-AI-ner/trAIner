package functionality;


import java.awt.Font;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A cache of the dynamically loaded fonts found in the fonts directory. This is suited to work on Windows and Unix based platforms.
 */
public class FontLoader {

    private static String[] names = { "ChakraPetch-SemiBold.ttf" };

    private static Map<String, Font> cache = new ConcurrentHashMap<String, Font>(names.length);
    static {
        for (String name : names) {
            cache.put(name, getFont(name));
        }
    }

    public static Font getFont(String name) {
        Font font;
        if (cache != null) {
            if ((font = cache.get(name)) != null) {
                return font;
            }
        }
        String fName = "/" + name;
        try {
            String userDir = System.getProperty("user.dir");
            System.out.println(userDir);
            font = Font.createFont(Font.TRUETYPE_FONT, new File(userDir + "/software/test_project/resources/"+name)).deriveFont(16.f);
        } catch (Exception ex) {
            try{
                String userDir = System.getProperty("user.dir");
                font = Font.createFont(Font.TRUETYPE_FONT, new File(userDir + "/resources/"+name)).deriveFont(16.f);
                System.err.println(fName + " not loaded.  Using serif font.");
            } catch (Exception ex2) {
                ex2.printStackTrace();
                System.err.println(fName + " not loaded.  Using serif font.");
                font = new Font("serif", Font.PLAIN, 24);
            }
        }

        return font;
    }
}





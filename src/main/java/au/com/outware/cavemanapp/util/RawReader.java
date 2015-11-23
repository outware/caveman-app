package au.com.outware.cavemanapp.util;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Tim Mutton
 * Copyright © 2015 Outware Mobile. All rights reserved.
 */
public class RawReader {
    public static String readStringFromRaw(int resId, Context context) {
        String contents = null;

        try {
            InputStream is = context.getResources().openRawResource(resId);
            int size = is.available();
            byte[] buffer = new byte[size];
            //noinspection ResultOfMethodCallIgnored
            is.read(buffer);
            is.close();
            contents = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return contents;
    }
}
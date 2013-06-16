package com.philit.ehr.util;

import java.lang.reflect.Field;

public class AudioUtil {
    
    static {
        try {
            int encode = -1;
            int outputFormat = -1;
            Class<?> mediaRecorderClass = Class.forName("android.media.MediaRecorder");
            Class<?>[] innerClasses = mediaRecorderClass.getDeclaredClasses();
            for (Class<?> innerClass : innerClasses) {
                if (innerClass.getName().equals("android.media.MediaRecorder$OutputFormat")) {
                    Field f = innerClass.getDeclaredField("MPEG_4");
                    if (f != null) {
                        outputFormat = f.getInt(f);
                    }
                }
                else if (innerClass.getName().equals("android.media.MediaRecorder$AudioEncoder")) {
                    Field f = innerClass.getDeclaredField("AAC");
                    if (f != null) {
                        encode = f.getInt(f);
                    }
                }
            }
            ENCODER = encode;
            OUTPUTFORMAT = outputFormat;
        } catch (ClassNotFoundException e1) {
        } catch (SecurityException e) {
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }
    }

    public static int ENCODER;
    public static int OUTPUTFORMAT;
    
    public static boolean supportMP4() {
        return !(ENCODER == -1 || OUTPUTFORMAT == -1);
    }
}

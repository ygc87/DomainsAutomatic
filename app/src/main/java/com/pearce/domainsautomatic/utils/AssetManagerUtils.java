package com.pearce.domainsautomatic.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class AssetManagerUtils {
    public static Set<String> filterDomains(String fileName, Context context, String suffixReg) {
        Set<String> results = new HashSet<>();
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        try {
            inputReader =
                    new InputStreamReader(context.getResources().getAssets().open(fileName));
            bufReader = new BufferedReader(inputReader);
            String line = "";
            while (!TextUtils.isEmpty(line = bufReader.readLine())) {
                String[] columns = line.split("\\s+");
                for (String column : columns) {
                    if (!TextUtils.isEmpty(column) && column.contains(".")) {
                        String suffix = column.substring(column.lastIndexOf(".") + 1);
                        Pattern suffixPattern = Pattern.compile(suffixReg, Pattern.CASE_INSENSITIVE);
                        if (suffixPattern.matcher(suffix).find()) {
                            Log.e("Panda", "column: " + column);
                            results.add(column);
                            break;
                        }
                    }
                }
            }
            return results;
        } catch (Exception e) {
            e.printStackTrace();
            closeInputStreamReader(inputReader);
            closeBufferedReader(bufReader);
        } finally {

        }

        return results;
    }


    private static void closeInputStreamReader(InputStreamReader isr) {
        if (isr != null) {
            try {
                isr.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private static void closeBufferedReader(BufferedReader isr) {
        if (isr != null) {
            try {
                isr.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}

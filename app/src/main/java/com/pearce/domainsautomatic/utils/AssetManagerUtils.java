package com.pearce.domainsautomatic.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
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

    public static void generateConfig(Context context) {
        ArrayList<String> copy = readAssetFile("copy.txt", context);
        ArrayList<String> mirror = readAssetFile("mirror.txt", context);
        ArrayList<String> keyword = readAssetFile("keyword.txt", context);
        ArrayList<String> sensitiveWords = readAssetFile("sensitive_words.txt", context);
        ArrayList<String> ours = readAssetFile("ours.txt", context);
        ArrayList<String> theirs = readAssetFile("theirs.txt", context);

        int count = copy.size();
        String result = "";
        for (int i = 0; i < count; i++) {
            result = copy.get(i);                                                    // domain
            result += "||";                                                          // separator
            result += mirror.get(i);                                                 // mirror
            result += "||";                                                          // separator
            result += randomItem(keyword, sensitiveWords, 3, "-");  // title
            result += "||";                                                          // separator
            result += randomItem(keyword, sensitiveWords, 4, "-");  // keyword
            result += "||";                                                          // separator
            result += randomItem(keyword, sensitiveWords, 4, "-");  // description
            result += "||";                                                          // separator
            result += randomItem(theirs, sensitiveWords, 2, ",");   // theirs
            result += "||";                                                          // separator
            result += randomItem(ours, sensitiveWords, 2, ",");     // ours
            Log.e("Panda", "config item: " + result);
        }
    }

    private static String randomItem(ArrayList<String> lib,
                                     ArrayList<String> sensitiveWords,
                                     int deep,
                                     String separator) {
        String result = "";
        for (int i = 0; i < deep; i++) {
            result += filterSensitiveWord(lib, sensitiveWords);
            if (i < deep - 1) {
                result += separator;
            }
        }
        return result;
    }

    private static String filterSensitiveWord(ArrayList<String> lib,
                                              ArrayList<String> sensitiveWords) {
        String result = "";
        Random random = new Random();
        String temp = lib.get(random.nextInt(lib.size()));
        if (sensitiveWords.contains(temp)) {
            Log.e("Panda", "sensitive word is: " + temp);
            result = filterSensitiveWord(lib, sensitiveWords);
        } else {
            result = temp;
        }

        return result;
    }
    public static void generateVHost(Context context) {

        ArrayList<String> copy = readAssetFile("copy.txt", context);
        Log.e("Panda", "===========================================================================");
        Log.e("Panda", "========================= vhost start =====================================");
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        for (String item : copy) {
            try {
                inputReader =
                        new InputStreamReader(context.getResources().getAssets().open("vhost.txt"));
                bufReader = new BufferedReader(inputReader);
                String line = "";
                while (!TextUtils.isEmpty(line = bufReader.readLine())) {
                    Log.e("Panda", "vhost is: " + line.replaceAll("XXX", item));
                }
                Log.e("Panda", "vhost is: ");
            } catch (Exception e) {
                e.printStackTrace();
                closeInputStreamReader(inputReader);
                closeBufferedReader(bufReader);
            } finally {

            }
        }
        Log.e("Panda", "========================= vhost end  ======================================");
        Log.e("Panda", "===========================================================================");
    }

    private static ArrayList<String> readAssetFile(String fileName, Context context) {
        Set<String> results = new HashSet<>();
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        try {
            inputReader =
                    new InputStreamReader(context.getResources().getAssets().open(fileName));
            bufReader = new BufferedReader(inputReader);
            Log.e("Panda", "===========================================================================");
            Log.e("Panda", "========================= read " + fileName + " start =====================================");
            String line = "";
            while (!TextUtils.isEmpty(line = bufReader.readLine())) {
                if (!results.contains(line)) {
                    results.add(line);
                    Log.e("Panda", "word is: " + line);
                }
            }
            Log.e("Panda", "========================= read " + fileName + " end =====================================");
            Log.e("Panda", "===========================================================================");
        } catch (Exception e) {
            e.printStackTrace();
            closeInputStreamReader(inputReader);
            closeBufferedReader(bufReader);
        } finally {

        }
        return new ArrayList<>(results);
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

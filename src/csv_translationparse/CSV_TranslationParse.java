/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csv_translationparse;

import au.com.bytecode.opencsv.CSVReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author escolarea
 */
public class CSV_TranslationParse {

    /**
     * @param args the command line arguments
     */
    
    public static List<Map> finalTranslationsList = new ArrayList();
    public static List<Map> nibFinalTranslationsList = new ArrayList();
    
    public static List<String[]> nibTranslationsStrings = new ArrayList();
    public static List<String[]> translationsStrings = new ArrayList();
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        CSVReader reader = new CSVReader(new FileReader("TWD_Translations.csv"));
        String [] nextLine;
        
        //Reads the CSV and inserts the arrays of strings (rows on the csv) to
        //the respective list
        while ((nextLine = reader.readNext()) != null) {
            // nextLine[] is an array of values from the line
            if(nextLine[1].equals("iOS"))
            {
                if(nextLine[3].equals("Translations Strings"))
                {
                    translationsStrings.add(nextLine);
                } else if(nextLine[3].equals("nibtranslations strings"))
                {
                    nibTranslationsStrings.add(nextLine);
                }
            }
        }
        
        
        for (int i = 0; i < translationsStrings.size(); i++) {
            String[] strings = translationsStrings.get(i);
            
            Map<String, String> allTranslationsMap = new HashMap<String, String>();
            allTranslationsMap.put("FieldName", strings[5].trim());
            allTranslationsMap.put("Description", strings[6]);
            
            allTranslationsMap.put("English", strings[8]);
            allTranslationsMap.put("German", strings[9]);
            allTranslationsMap.put("Italian", strings[10]);
            allTranslationsMap.put("Bulgarian", strings[11]);
            allTranslationsMap.put("Serbian", strings[12]);
            allTranslationsMap.put("Slovenian", strings[13]);
            allTranslationsMap.put("Japanese", strings[14]);
            allTranslationsMap.put("Chinese", strings[15]);
            allTranslationsMap.put("UK", strings[16]);
            allTranslationsMap.put("Australia", strings[17]);
            allTranslationsMap.put("Turkish", strings[18]);
            allTranslationsMap.put("Arabic", strings[19]);
            allTranslationsMap.put("Polish", strings[20]);
            allTranslationsMap.put("Russian", strings[21]);
            allTranslationsMap.put("Spanish", strings[22]);
            
            //For printing purposes
            for (int j = 0; j < strings.length; j++) {
                //System.out.print(strings[j] + " ");
            }
            
            finalTranslationsList.add(allTranslationsMap);
        }
        
        for (int i = 0; i < nibTranslationsStrings.size(); i++) {
            String[] strings = nibTranslationsStrings.get(i);
            
            Map<String, String> allTranslationsMap = new HashMap<String, String>();
            allTranslationsMap.put("FieldName", strings[5].trim());
            allTranslationsMap.put("Description", strings[6]);
            
            allTranslationsMap.put("English", strings[8]);
            allTranslationsMap.put("German", strings[9]);
            allTranslationsMap.put("Italian", strings[10]);
            allTranslationsMap.put("Bulgarian", strings[11]);
            allTranslationsMap.put("Serbian", strings[12]);
            allTranslationsMap.put("Slovenian", strings[13]);
            allTranslationsMap.put("Japanese", strings[14]);
            allTranslationsMap.put("Chinese", strings[15]);
            allTranslationsMap.put("UK", strings[16]);
            allTranslationsMap.put("Australia", strings[17]);
            allTranslationsMap.put("Turkish", strings[18]);
            allTranslationsMap.put("Arabic", strings[19]);
            allTranslationsMap.put("Polish", strings[20]);
            allTranslationsMap.put("Russian", strings[21]);
            allTranslationsMap.put("Spanish", strings[22]);
            
            nibFinalTranslationsList.add(allTranslationsMap);
        }
        
        createTranslationsForCountry("EN-US", "English");
        createTranslationsForCountry("AR", "Arabic");
        createTranslationsForCountry("BG", "Bulgarian");
        createTranslationsForCountry("ZH", "Chinese");
        createTranslationsForCountry("DE", "German");
        createTranslationsForCountry("JA", "Japanese");
        createTranslationsForCountry("PL", "Polish");
        createTranslationsForCountry("RU", "Russian");
        createTranslationsForCountry("SR", "Serbian");
        createTranslationsForCountry("SL", "Slovenian");
        createTranslationsForCountry("ES", "Spanish");
        createTranslationsForCountry("TR", "Turkish");
        createTranslationsForCountry("IT", "Italian");
        createTranslationsForCountry("EN-GB", "UK");
        createTranslationsForCountry("EN-AU", "Australia");
        
        createNIBTranslationsForCountry("EN-US", "English");
        createNIBTranslationsForCountry("AR", "Arabic");
        createNIBTranslationsForCountry("BG", "Bulgarian");
        createNIBTranslationsForCountry("ZH", "Chinese");
        createNIBTranslationsForCountry("DE", "German");
        createNIBTranslationsForCountry("JA", "Japanese");
        createNIBTranslationsForCountry("PL", "Polish");
        createNIBTranslationsForCountry("RU", "Russian");
        createNIBTranslationsForCountry("SR", "Serbian");
        createNIBTranslationsForCountry("SL", "Slovenian");
        createNIBTranslationsForCountry("ES", "Spanish");
        createNIBTranslationsForCountry("TR", "Turkish");
        createNIBTranslationsForCountry("IT", "Italian");
        createNIBTranslationsForCountry("EN-GB", "UK");
        createNIBTranslationsForCountry("EN-AU", "Australia");
        
        compareTranslationsWithCSV("Translations.strings.csv");
    }
    
    public static void createTranslationsForCountry(String code, String country) throws FileNotFoundException, IOException
    {
        File directory = new File(code.toLowerCase() + ".lproj");
        directory.mkdir();
        
        BufferedWriter translationsFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(code + ".lproj/Translations.strings"), "UTF-16LE"));
        translationsFile.write(0xFEFF);
        
        for (int j = 0; j < finalTranslationsList.size(); j++) {
            Map<String, String> map = finalTranslationsList.get(j);
            
            System.out.println("Description: " + map.get("Description"));
            System.out.println("FieldName: " + map.get("FieldName"));
            System.out.println(code + ": " + map.get(country).replace("\"", ""));
            System.out.println();
            
            translationsFile.write("/* " + map.get("Description") + " */");
            translationsFile.write("\n");
            translationsFile.write("\"" + map.get("FieldName") + "\" = \"" + map.get(country).replace("\"", "") + "\";");
            translationsFile.write("\n");
            translationsFile.write("\n");
        }
        translationsFile.close();
    }
    
    public static void createNIBTranslationsForCountry(String code, String country) throws FileNotFoundException, IOException
    {
        File directory = new File("nibfiles");
        directory.mkdir();
        
        BufferedWriter translationsFile = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("nibfiles/" + code + ".NibTranslations.strings"), "UTF-16LE"));
        translationsFile.write(0xFEFF);
        
        for (int j = 0; j < nibFinalTranslationsList.size(); j++) {
            Map<String, String> map = nibFinalTranslationsList.get(j);
            
            System.out.println("Description: " + map.get("Description"));
            System.out.println("FieldName: " + map.get("FieldName"));
            System.out.println(code + ": " + map.get(country).replace("\"", ""));
            System.out.println();
            
            translationsFile.write("/* " + map.get("Description") + " */");
            translationsFile.write("\n");
            translationsFile.write("\"" + map.get("FieldName") + "\" = \"" + map.get(country).replace("\"", "") + "\";");
            translationsFile.write("\n");
            translationsFile.write("\n");
        }
        translationsFile.close();
    }
    
    public static void compareTranslationsWithCSV(String translationsFile){
        try {
            List<String[]> csvTranslationsList = new ArrayList();
            List<String[]> csvTranslationsListCopy = new ArrayList();
            
            CSVReader reader = new CSVReader(new FileReader(translationsFile));
            String [] nextLine;
            //Reads the CSV and inserts the arrays of strings (rows on the csv) to
            //the respective list
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line
                csvTranslationsList.add(nextLine);
                csvTranslationsListCopy.add(nextLine);
            }
            
            
            for (int i = 0; i < csvTranslationsList.size(); i++)
            {
                String fieldName = convert(csvTranslationsList.get(i)[1].trim(), "UTF-8");
                for (int j = 0; j < finalTranslationsList.size(); j++) 
                {
                    Map<String, String> map = finalTranslationsList.get(j);
                    String mapFieldName = convert(map.get("FieldName").trim(),"UTF-16LE");
                    mapFieldName = mapFieldName.substring(0, mapFieldName.length() - 2);
                    
                    if(fieldName.compareTo(mapFieldName) == 0)
                    {
                        //System.out.println("REMOVE: " + fieldName);
                        
                        csvTranslationsListCopy.set(i, new String[0]);
                        break;
                    }
                }
            }
            
            for(int i = 0; i < csvTranslationsListCopy.size(); i++)
            {
                String[] row = csvTranslationsListCopy.get(i);
                if(row.length != 0)
                {
                    System.out.println("\"" + row[1] + "\" = \"" + row[2] + "\";");
                }
            }
            
        } catch (Exception ex) {
            Logger.getLogger(CSV_TranslationParse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String convert(String s, String encoding) {  
  
        StringBuilder buf = new StringBuilder();  

        try {  
            byte[] bytes = s.getBytes(encoding);  
            for (int i = 0; i < bytes.length; i++) {  
                String byteAsHex = Integer.toHexString(0xFF & bytes[i]);  
                buf.append("%");
                buf.append(byteAsHex);  
            }  
        }  
        catch (UnsupportedEncodingException e) {  
            System.out.println("That's not a valid encoding");  
        }  

        return buf.toString();  

    }
}

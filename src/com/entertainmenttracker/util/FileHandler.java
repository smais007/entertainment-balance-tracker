package com.entertainmenttracker.util;

import com.entertainmenttracker.model.DailyEntry;
import java.io.*;
import java.util.ArrayList;


public class FileHandler {
    
    private static final String FILE_NAME = "entertainment_data.txt";
    

    public static void saveEntries(ArrayList<DailyEntry> entries) {
        try {
            FileWriter fileWriter = new FileWriter(FILE_NAME);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            
            for (DailyEntry entry : entries) {
                bufferedWriter.write(entry.toFileString());
                bufferedWriter.newLine();  // Add new line after each entry
            }
            
            bufferedWriter.close();
            System.out.println("Data saved successfully!");
            
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
    

    public static ArrayList<DailyEntry> loadEntries() {
        ArrayList<DailyEntry> entries = new ArrayList<>();
        
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                System.out.println("No existing data file found. Starting fresh.");
                return entries;  
            }
            
            FileReader fileReader = new FileReader(FILE_NAME);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                try {
                    DailyEntry entry = DailyEntry.fromFileString(line);
                    if (entry != null) {
                        entries.add(entry);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
            
            bufferedReader.close();
            System.out.println("Loaded " + entries.size() + " entries from file.");
            
        } catch (FileNotFoundException e) {
            System.out.println("Data file not found. Starting with empty list.");
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
        
        return entries;
    }
}

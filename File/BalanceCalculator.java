package File;

import Entry.DailyEntry;


public class BalanceCalculator {
    
    public static final String BALANCED = "BALANCED";
    public static final String ENTERTAINMENT_HEAVY = "ENTERTAINMENT HEAVY";
    public static final String HIGH_RISK = "HIGH RISK";
    public static final String STUDY_FOCUSED = "STUDY FOCUSED";
    
    public static double calculateEntertainmentPercentage(DailyEntry entry) {
        int totalTime = entry.getTotalTime();
        
        if (totalTime == 0) {
            return 0;
        }
        
        int entertainmentTime = entry.getEntertainmentTime();
        double percentage = ((double) entertainmentTime / totalTime) * 100;
        
        return percentage;
    }
    

    public static double calculateStudyPercentage(DailyEntry entry) {
        int totalTime = entry.getTotalTime();
        
        if (totalTime == 0) {
            return 0;
        }
        
        double percentage = ((double) entry.getStudyMinutes() / totalTime) * 100;
        
        return percentage;
    }
    

    public static String calculateStatus(DailyEntry entry) {
        int entertainmentTime = entry.getEntertainmentTime();
        int studyMinutes = entry.getStudyMinutes();
        int totalTime = entry.getTotalTime();
        
        if (totalTime == 0) {
            return BALANCED;
        }
        
        double entertainmentPercentage = calculateEntertainmentPercentage(entry);
        double studyPercentage = calculateStudyPercentage(entry);
        
        if (entertainmentPercentage >= 70) {
            return HIGH_RISK;
        }
        
        if (studyPercentage >= 70) {
            return STUDY_FOCUSED;
        }
        
        if (studyMinutes >= entertainmentTime) {
            return BALANCED;
        }
        
        if (entertainmentTime > studyMinutes) {
            return ENTERTAINMENT_HEAVY;
        }
        
        return BALANCED;
    }
    

    public static String getStatusExplanation(String status) {
        if (status.equals(BALANCED)) {
            return "Good job! Your study and entertainment time are balanced.";
        } else if (status.equals(ENTERTAINMENT_HEAVY)) {
            return "Warning: You spent more time on entertainment than studying.";
        } else if (status.equals(HIGH_RISK)) {
            return "Alert: Entertainment time is 70% or more of your day!";
        } else if (status.equals(STUDY_FOCUSED)) {
            return "Great! You focused on studying today (70% or more).";
        }
        return "";
    }
}

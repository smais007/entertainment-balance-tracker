package Entry;

public class DailyEntry {
    
    private String date;           
    private int gamingMinutes;     
    private int movieMinutes;      
    private int studyMinutes;      
    private String notes;          

    public DailyEntry(String date, int gamingMinutes, int movieMinutes, int studyMinutes, String notes) {
        this.date = date;
        this.gamingMinutes = gamingMinutes;
        this.movieMinutes = movieMinutes;
        this.studyMinutes = studyMinutes;
        this.notes = notes;
    }
      
    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public int getGamingMinutes() {
        return gamingMinutes;
    }
    
    public void setGamingMinutes(int gamingMinutes) {
        this.gamingMinutes = gamingMinutes;
    }
    
    public int getMovieMinutes() {
        return movieMinutes;
    }
    
    public void setMovieMinutes(int movieMinutes) {
        this.movieMinutes = movieMinutes;
    }
    
    public int getStudyMinutes() {
        return studyMinutes;
    }
    
    public void setStudyMinutes(int studyMinutes) {
        this.studyMinutes = studyMinutes;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
   
    public int getEntertainmentTime() {
        return gamingMinutes + movieMinutes;
    }
    
   
    public int getTotalTime() {
        return getEntertainmentTime() + studyMinutes;
    }
    
    public String toFileString() {
        return date + "|" + gamingMinutes + "|" + movieMinutes + "|" + studyMinutes + "|" + notes;
    }
   
    public static DailyEntry fromFileString(String line) {

        String[] parts = line.split("\\|");
        
        if (parts.length >= 4) {
            String date = parts[0];
            int gaming = Integer.parseInt(parts[1]);
            int movie = Integer.parseInt(parts[2]);
            int study = Integer.parseInt(parts[3]);
            String notes = (parts.length > 4) ? parts[4] : "";
            
            return new DailyEntry(date, gaming, movie, study, notes);
        }
        
        return null;
    }
}

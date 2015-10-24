package sl314.model;

public class LeagueDTO {

    private String name;
    private int year;

    public LeagueDTO() {
    }
    
    

    public LeagueDTO(String name, int year) {
        this.name = name;
        this.year = year;
    }
    
    public LeagueDTO( int year,String name) {
        this.name = name;
        this.year = year;
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    
}

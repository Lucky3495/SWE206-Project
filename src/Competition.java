import java.util.Date;

public abstract class Competition {
    private String name;
    private String url;
    private Date dueDate;
    public Competition(String name, String url, Date dueDate){
        this.name= name;
        this.url= url;
        this.dueDate = dueDate;
    }
    public String getName(){
        return this.name;
    }
    public String getUrl(){
        return this.url;
    }
    public Date getDueDate(){
        return this.dueDate;
    }
    public abstract boolean isUpdated();
    
    public String toString() {
    	return String.format("%s %s %s", name, url, dueDate.toString());
    }
}

import java.util.ArrayList;
import java.util.Date;

public class TeamComp extends Competition{
    private ArrayList<Team> teams;

    public TeamComp(String name, String url, Date dueDate) {
        super(name, url, dueDate);
        teams = new ArrayList<>();
    }
    public void addTeam(Team team){
        teams.add(team);
    }
    public boolean isUpdated(){
        for(Team x: teams){
            if(!x.isUpdated()){
                return false;
            }
        }
        return true;
    }
}

import java.util.ArrayList;

public class Team {
    private Competition competition;
    private int rank;
    private ArrayList<Student> students;
    private String name;

    public Team(Competition competition, int rank, String name) {
        this.competition = competition;
        this.rank = rank;
        this.name = name;
        students = new ArrayList<>();
    }
    public void addStudent(Student student){
        students.add(student);
    }

    public Competition getCompetition() {
        return competition;
    }
    public int getRank() {
        return rank;
    }
    public String getName() {
        return name;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUpdated(){
        for(Student x: students){
            if(!x.hasRank()){
                return false;
            }
        }
        return true;
    }
}

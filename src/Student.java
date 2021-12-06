public class Student {
    private Competition competition;
    private int rank;
    private String id;
    private String name;
    private String major;
    public Student(Competition competition, String id, String name, String major){
        this.competition=competition;
        this.id= id;
        this.name = name;
        this.major= major;
    }
    public Student(Competition competition, int rank, String id, String name, String major){
        this(competition,id,name,major);
        this.rank=rank;
    }

    public Competition getCompetition() {
        return competition;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMajor() {
        return major;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMajor(String major) {
        this.major = major;
    }
    public boolean hasRank(){
        return (this.rank!=0);
    }
}

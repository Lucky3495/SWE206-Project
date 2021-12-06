import java.util.ArrayList;
import java.util.Date;

public class IndieComp extends Competition{
    private ArrayList<Student> students;
    public IndieComp(String name, String url, Date dueDate){
        super(name,url,dueDate);
        students = new ArrayList<>();
    }
    public void addStudent(Student student){
        students.add(student);
    }
    public boolean isUpdated(){
        for(Student x:students){
            if(!x.hasRank()){
                return false;
            }
        }
        return true;
    }
}

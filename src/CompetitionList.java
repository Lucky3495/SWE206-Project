import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CompetitionList {
    private ArrayList<Competition> comps;

    public CompetitionList() {
        comps = new ArrayList<>();
    }
    
    
    public void addCompetition(Competition competition){
        comps.add(competition);
    }
    
    
    public boolean isUpdated(){
        for(Competition x: comps){
            if(!x.isUpdated()){
                return false;
            }
        }
        return true;
    }
    
    
    public void addStudent(Student student, IndieComp comp){
        for(Competition x: comps){
            if(x instanceof IndieComp){
                if(((IndieComp)x).equals(comp)){
                    ((IndieComp)x).addStudent(student);
                }
            }
        }
    }
    
    
    public void addTeam(Team team, TeamComp comp){
        for(Competition x: comps){
            if(x instanceof TeamComp){
                if(((TeamComp)x).equals(comp)){
                    ((TeamComp)x).addTeam(team);
                }
            }
        }
    }


    public void deleteCompetition(String name){
        for(Competition x: comps){
            if(x.getName().equals(name)){
                comps.remove(x);
            }
        }
    }
    
    // reads the competitions in an Excel file w
    // each competition is in a sheet
    public void readExcelFile(XSSFWorkbook w) {
    	// get the number of sheets in the Excel workbook
		int nSheets = w.getNumberOfSheets();
		
		// iterate over the sheets
		for(int i = 0; i < nSheets; i++) {
			XSSFSheet s = w.getSheetAt(i); // get the sheet in i'th position
			
			// if the 7'th column in the student info is empty then this is an individual competition 
			if(s.getRow(4).getCell(6) == null) {
				readIndieCompSheet(s);
			}
			// if it has data then it's a team competition
			else {
				readTeamCompSheet(s);
			}
		}
    }
    
    // method that reads an IndieComp from an XSSFSheet and adds it to comps
	public void readIndieCompSheet(XSSFSheet s) {
		// read the competition info from the sheet
		String cn = s.getRow(0).getCell(1).toString();
		String url = s.getRow(1).getCell(1).toString();
		Date cd = s.getRow(2).getCell(1).getDateCellValue();
		
		// create the IndieComp object
		IndieComp c = new IndieComp(cn, url, cd);
		
		// initialize r as row 6 (row 6 is where student info starts) 
		XSSFRow r = s.getRow(5);
		// iterate over all rows that contain student info
		for(int i = 5; r != null; r = s.getRow(++i)) {
			// get the info from the row
			String name, major, id;
			name = r.getCell(2).toString();
			major = r.getCell(3).toString();
			id = r.getCell(1).getRawValue(); // toString gives scientific notation (2.222458E8) so we use getRawValue
			
			// if the student has a rank get its numeric value, if not set the rank to 0
			// getNumericCellValue gives a double so we cast to int
			int rank = r.getCell(4).getCellType() == CellType.NUMERIC? (int)(r.getCell(4).getNumericCellValue()) : 0;
			
			// create and add a Student object to the competition c
			c.addStudent(new Student(c, rank, id, name, major));
		}
		
		// return the IndieComp c
		comps.add(c);
	}
	
	// method that reads a TeamComp from an XSSFSheet and adds it to comps
	public void readTeamCompSheet(XSSFSheet s) {
		// read the competition info from the sheet
		String cn = s.getRow(0).getCell(1).toString();
		String url = s.getRow(1).getCell(1).toString();
		Date cd = s.getRow(2).getCell(1).getDateCellValue();
		
		// create the IndieComp object
		TeamComp c = new TeamComp(cn, url, cd);
		
		// create a hashmap to use the team number to find the Team object
		HashMap<String, Team> teams = new HashMap<>();
		
		// initialize r as row 6 (row 6 is where student info starts) 
		XSSFRow r = s.getRow(5);
		// iterate over all rows that contain student info
		for(int i = 5; r != null; r = s.getRow(++i)) {
			// get the info from the row
			String name, major, id, teamName;
			name = r.getCell(2).toString();
			major = r.getCell(3).toString();
			id = r.getCell(1).getRawValue(); // toString gives scientific notation (2.222458E8) so we use getRawValue
			teamName = r.getCell(4).toString();
			
			// if the student has a rank get its numeric value, if not set the rank to -1
			// getNumericCellValue gives a double so we cast to int
			int rank = r.getCell(6).getCellType() == CellType.NUMERIC? (int)(r.getCell(6).getNumericCellValue()) : 0;
			
			// if the team is not in the Hashmap
			if((teams.get(teamName)) == null) {
				Team t = new Team(c, rank, r.getCell(5).toString()); // create a Team object for the team
				c.addTeam(t); // add the team object to the competition c
				teams.put(teamName, t); // add the Team object to the Hashmap
				t.addStudent(new Student(c, rank, id, name, major)); // add the student to the team 
			}
			else {
				Team t = teams.get(teamName); // get the team object from the Hashmap
				// the team is already assigned to a competition if it is in the Hashmap
				
				t.addStudent(new Student(c, rank, id, name, major)); // add the student to the team
			}
			
		}
		
		// return the TeamComp c
		comps.add(c);
	}
}

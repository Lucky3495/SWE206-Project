import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ApacheTesting {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		XSSFWorkbook w = new XSSFWorkbook(new FileInputStream("Competitions Participations.xlsx"));
		
		ArrayList<Competition> lc = new ArrayList<>();
		int nSheets = w.getNumberOfSheets();
		for(int i = 0; i < nSheets; i++) {
			XSSFSheet s = w.getSheetAt(i);
			if(s.getRow(4).getCell(6) == null) {
				lc.add(readIndieCompSheet(s));
			}
			else {
				lc.add(readTeamCompSheet(s));
			}
		}
		
		
		
		System.out.println(lc.toString());
	}
	
	public static IndieComp readIndieCompSheet(XSSFSheet s) {
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
		return c;
	}
	
	public static TeamComp readTeamCompSheet(XSSFSheet s) {
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
		return c;
	}
}

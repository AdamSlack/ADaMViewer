import java.util.ArrayList;

public class ADaMStudy {

	// ADaM Study data  members
	private String studyID;
	private ArrayList<ADaMDomain> domains;
	
	// ADaM Study Constructor;
	public ADaMStudy(){
		studyID = "";
		domains = new ArrayList<ADaMDomain>();
	}

	public void addDomain(ADaMDomain theDomain){
		domains.add(theDomain);
	}
	
	// ADaM Study Getters
	public String getStudyID() {
		return studyID;
	}
	public ArrayList<ADaMDomain> getDomains() {
		return domains;
	}
	
	// ADaM Study Setters
	public void setStudyID(String studyID) {
		this.studyID = studyID;
	}
	public void setDomains(ArrayList<ADaMDomain> domains) {
		this.domains = domains;
	}
	
	
}
 
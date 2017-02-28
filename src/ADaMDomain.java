import java.util.ArrayList;

public class ADaMDomain {

	// ADaM Domain Data Members
	private String studyID;
	private String domainName;
	private ArrayList<ADaMVariable> variables;
	
	// ADaM Domain Constructor
	public ADaMDomain(){
		variables	= new ArrayList<ADaMVariable>();
		studyID 	= "";
		domainName 	= "";
	}

	// ADaM Domain Getters
	public String getStudyID() {
		return studyID;
	}
	public String getDomainName() {
		return domainName;
	}
	public ArrayList<ADaMVariable> getVariables() {
		return variables;
	}
	
	// ADaM Domain Setters
	public void setStudyID(String studyID) {
		this.studyID = studyID;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public void setVariables(ArrayList<ADaMVariable> variables) {
		this.variables = variables;
	}
	
	// add a variable to the list of variables.
	public void addADaMVariable(ADaMVariable var){
		variables.add(var);
	}
		
	
}

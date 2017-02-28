import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.Vector;

public class ADaMModel {
	
	// ADaM Model Data Members
	private static TreeMap<String, ADaMStudy> ADaMStudies = new TreeMap<String,ADaMStudy>();
	private static ArrayList<ADaMDomain> leftoverADaMDomains = new ArrayList<ADaMDomain>();
	private static ArrayList<ADaMVariable> leftoverADaMVariables = new ArrayList<ADaMVariable>();
	
	private static ArrayList<String> ADaMHeadings = initialiseADaMHeadings();
	
	// ADaM Model Constructor
	public ADaMModel()
	{
		
	}
	
	// Initialises a list of standard Headings found in ADaM MDDT spreadsheet
	public static ArrayList<String> initialiseADaMHeadings(){	
		return new ArrayList<String>(Arrays.asList("VariableName"
												  ,"VariableLabel"
												  ,"Origin"
												  ,"MethodType"
												  ,"SourceDerivation"
												  ,"DocumentOID"
												  ,"ReviewCommitteeNotes"
												  ,"CodeListRef"
												  ,"NeedVLM"
												  ,"XMLDataType"
												  ,"Length"
												  ,"SignificantDigits"
												  ,"DisplayFormat"
												  ,"Mandatory"
												  ,"CoreVariable"
												  ,"ImplementationNotes"));
	}

	
	// ADaM Model Getters
	public static TreeMap<String, ADaMStudy> getADaMStudies() {
		return ADaMStudies;
	}

	public static ArrayList<ADaMDomain> getLeftoverADaMDomains() {
		return leftoverADaMDomains;
	}

	public static ArrayList<ADaMVariable> getLeftoverADaMVariables() {
		return leftoverADaMVariables;
	}

	public static ArrayList<String> getADaMHeadings() {
		return ADaMHeadings;
	}
	
	// ADaM Model Setters
	public static void setADaMStudies(TreeMap<String, ADaMStudy> aDaMStudies) {
		ADaMStudies = aDaMStudies;
	}
	public static void setLeftoverADaMDomains(ArrayList<ADaMDomain> leftoverADaMStudies) {
		ADaMModel.leftoverADaMDomains = leftoverADaMStudies;
	}
	public static void setLeftoverADaMVariables(ArrayList<ADaMVariable> leftoverADaMVariables) {
		ADaMModel.leftoverADaMVariables = leftoverADaMVariables;
	}
	
	// add an ADaM Domain to the list of leftover ADaMDomains.
	public static void addLeftoverADaMDomain(ADaMDomain domain){
		leftoverADaMDomains.add(domain);
	}


}

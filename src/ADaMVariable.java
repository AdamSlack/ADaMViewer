
public class ADaMVariable {

	
	// ADaM Variable Data members
	private String studyID;
	private String domainName;
	private String variableName;
	private String variableLabel;
	private String Origin;
	private String methodType;
	private String sourceDerivation;
	private String documentOID;
	private String reviewCommitteeNotes;
	private String codeListRef;
	private String needVLM;
	private String XMLDataType;
	private String Length;
	private String significantDigits;
	private String displayFormat;
	private String mandatory;
	
	private String studySpecificDerivation;
	private String coreVariable;
	
	// ADaM Variable Constructor
	public ADaMVariable()
	{
		// Initialise variables to be empty strings.
		studyID 				= "";
		domainName 				= "";
		variableName 			= "";
		variableLabel 			= "";
		Origin 					= "";
		methodType 				= "";
		sourceDerivation 		= "";
		documentOID 			= "";
		reviewCommitteeNotes 	= "";
		codeListRef				= "";
		needVLM 				= "";
		XMLDataType 			= "";
		Length 					= "";
		significantDigits 		= "";
		displayFormat 			= "";
		mandatory 				= "";
	}
	
	// ADaM Variable Getters

	public String getStudyID() {
		return studyID;
	}
	public String getDomainName() {
		return domainName;
	}
	public String getVariableName() {
		return variableName;
	}
	public String getVariableLabel() {
		return variableLabel;
	}
	public String getOrigin() {
		return Origin;
	}
	public String getMethodType() {
		return methodType;
	}
	public String getSourceDerivation() {
		return sourceDerivation;
	}
	public String getDocumentOID() {
		return documentOID;
	}
	public String getReviewCommitteeNotes() {
		return reviewCommitteeNotes;
	}
	public String getCodeListRef() {
		return codeListRef;
	}
	public String getNeedVLM() {
		return needVLM;
	}
	public String getXMLDataType() {
		return XMLDataType;
	}
	public String getLength() {
		return Length;
	}
	public String getSignificantDigits() {
		return significantDigits;
	}
	public String getDisplayFormat() {
		return displayFormat;
	}
	public String getMandatory() {
		return mandatory;
	}

	public String getStudySpecificDerivation(){
		return studySpecificDerivation;
	}
	public String getCoreVariable() {
		return coreVariable;
	}
	
	// ADaM Variable Setters
	public void setStudyID(String studyID) {
		this.studyID = studyID;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public void setVariableLabel(String variableLabel) {
		this.variableLabel = variableLabel;
	}

	public void setOrigin(String origin) {
		Origin = origin;
	}

	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}

	public void setSourceDerivation(String sourceDerivation) {
		this.sourceDerivation = sourceDerivation;
	}

	public void setDocumentOID(String documentOID) {
		this.documentOID = documentOID;
	}

	public void setReviewCommitteeNotes(String reviewCommitteeNotes) {
		this.reviewCommitteeNotes = reviewCommitteeNotes;
	}

	public void setCodeListRef(String codeListRef) {
		this.codeListRef = codeListRef;
	}

	public void setNeedVLM(String needVLM) {
		this.needVLM = needVLM;
	}

	public void setXMLDataType(String xMLDataType) {
		XMLDataType = xMLDataType;
	}

	public void setLength(String length) {
		Length = length;
	}

	public void setSignificantDigits(String significantDigits) {
		this.significantDigits = significantDigits;
	}

	public void setDisplayFormat(String displayFormat) {
		this.displayFormat = displayFormat;
	}

	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}
	
	// ADaM Variabb
	
	
	public void setStudySpecificDerivation(String studySpecificDerivation)
	{
		this.studySpecificDerivation = studySpecificDerivation;
	}

	
	public void setCoreVariable(String coreVariable) {
		this.coreVariable = coreVariable;
	}
	
}

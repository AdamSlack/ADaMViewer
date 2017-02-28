import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ADaMController {
	
	// Class Constructor
	public ADaMController(){
		
	}
	
	// Excel File reader.
	public static void readFile(File theFile) throws IOException{
		
		// create an input stream to read xls file.
		FileInputStream fis = new FileInputStream(theFile);
		
		// Create an excel Workbook from the input file stream.
		Workbook wb = new XSSFWorkbook(fis);
		
		// set missing cell policy to create Nulls as blanks.
		wb.setMissingCellPolicy(Row.CREATE_NULL_AS_BLANK);
		
		// create a data formatter to handle data in Workbook.
		DataFormatter df = new DataFormatter();
		
		// Create an iterator to go through each sheet in the workbook
		Iterator<Sheet> it = wb.iterator();
		
		// Iterate over each sheet in the workbook using the iterator
		while(it.hasNext()){
			
			// get the current sheet
			Sheet currSheet = it.next();
			
			// get the current Sheet's title.
			String sheetTitle = currSheet.getSheetName();
			
			ArrayList<String> exceptions = new ArrayList<String>(Arrays.asList("TOC", "Instructions", "Version History"));
			
			// Check that the current sheet being read is an actual domain.
			if(!exceptions.contains(sheetTitle)){
				// create a Row iterator to go through each row in current sheet.
				Iterator<Row> rit = currSheet.iterator();
				
				// create an array to hold Arrays containing Row Cell contents.
				ArrayList<ArrayList<String>> sheetRowContents = new ArrayList<ArrayList<String>>();
				
				// iterate over each row in the sheet.
				while(rit.hasNext()){
					
					// get the current Row.
					Row currRow = rit.next();
					
					// initialise a list to hold each cell's content in the row.
					ArrayList<String> rowCellContents = new ArrayList<String>();
					
					// get the number of cells in current row.
					int cellCount = currRow.getLastCellNum();
					String contents = "";
					// loop over each cell in the row (not using iterator as null cells need to be handled.)
					for(int i = 0; i < cellCount; i++){
						// get the current cell. creating a blank cell if contents null
						Cell currCell = currRow.getCell(i, Row.CREATE_NULL_AS_BLANK);
						
						// get the contents of the cell, using data formatter.
						contents = df.formatCellValue(currCell);
						// add the contents to the array of cell contents.
						rowCellContents.add(contents);
					}
					// Add the current Row Cell Contents Array to the Array of Rows in the sheet
					sheetRowContents.add(rowCellContents);
				}
				
				// Create a ADaMDomain using the Sheet Contents Array of Row Arrays
				createADaMDomain(sheetTitle, sheetRowContents);
			}
			
		}
		// now all ADaM Domains have been made for each study. need to map each domain to it's study.
		assignADaMDomains();
		
		wb.close();
		fis.close();
	}
	
	private static void assignADaMDomains() {
		
		// for each domain not yet assigned to a study
		for(ADaMDomain d : ADaMModel.getLeftoverADaMDomains())
		{
			// if the study is already in the map.
			if(ADaMModel.getADaMStudies().containsKey(d.getStudyID().toUpperCase())){
				// get it and add this domain to it.
				ADaMModel.getADaMStudies().get(d.getStudyID().toUpperCase()).addDomain(d);
			}
			else{
				// otherwise create a new ADaM Study
				ADaMStudy s = new ADaMStudy();
				
				// set the study ID/name
				s.setStudyID(d.getStudyID());
				
				// add the current domain to the new study.
				s.addDomain(d);
				
				// add the new study with it's domain to the Model.
				ADaMModel.getADaMStudies().put(d.getStudyID(), s);
			}
		}
		
		
	}

	// Create a Study's Domain
	public static void createADaMDomain(String domainName, ArrayList<ArrayList<String>> domainSheet){
		// Get the first row of the domain Sheet. (contains headings)
		ArrayList<String> domainHeadings = domainSheet.get(0);
		
		// get the index of the column diving the General headings and the study names
		int dividePos = domainHeadings.indexOf("Mandatory");
		
		// use the divide position to create two heading arrays.
		// General headings.
		ArrayList<String> generalHeadings = 
				new ArrayList<String>(domainHeadings.subList(0,  dividePos + 1));
		// Study Names
		ArrayList<String> studyNames = 
				new ArrayList<String>(domainHeadings.subList(dividePos + 1, domainHeadings.size()));
		
		// For each study in the domain. create all variables
		for(int i = 0; i < studyNames.size(); i++){
			
			// get the name of the current study.
			String studyName = studyNames.get(i);
			
			// create a blank ADaM Domain for the current study.
			ADaMDomain d = new ADaMDomain();
			
			// set the domain name and the study it belongs to.
			d.setDomainName(domainName);
			d.setStudyID(studyName);
					
			// for each row in the domain sheet. (after the headings row.)
			for(int j = 1; j < domainSheet.size(); j++){
				
				// get the current Row of values that represents a variable.
				ArrayList<String> currentVariableValues = domainSheet.get(j);
				
				// check if the row is a valid variable.
				if(currentVariableValues.size() > 1
						&& !currentVariableValues.get(1).equals(null)
						&& currentVariableValues.get(1).length() > 1){
					
					// Split the current Variable Values into general values and study specific values
					// General Values
					ArrayList<String> generalValues = 
							new ArrayList<String>(currentVariableValues.subList(0, dividePos + 1));
					// Study Specific Values
					ArrayList<String> studyValues = 
							new ArrayList<String>(currentVariableValues.subList(dividePos + 1,
												  currentVariableValues.size()));
					
					// create a new ADaM Variable for the current Domain.
					ADaMVariable variable = new ADaMVariable();
					
					// create an empty string to represent the study specific derivation instructions.
					String studySpecifiDerivation = "";
					
					// if the current study is in the list of study values, then the index of the loop
					// will be less than the length of the study values found.
					if(i < studyValues.size()){
						studySpecifiDerivation = studyValues.get(i);
					}
					
					try{
						variable = createVariable(studyName, domainName, studySpecifiDerivation,
								  generalValues, generalHeadings);
					}
					catch(java.lang.IndexOutOfBoundsException e){
						System.out.println("Study: " + studyName + " domain: " + domainName );
					}
					
					// set the variable's name and the domain and study it belongs to.
					// variable.setVariableName(generalValues.get(0));
					// variable.setDomainName(domainName);
					// variable.setStudyID(studyName);
					
					// add the variable to the domain.
					d.addADaMVariable(variable);
				}
			} // End of domain Sheet Loop.
			
			ADaMModel.addLeftoverADaMDomain(d);
		} // End of Study Names Loop.
	}
	
	// Create a Domain's Variable
	public static ADaMVariable createVariable(String studyID, String domainName, String studySpecificDerivation,
							   ArrayList<String> generalValues,
							   ArrayList<String> foundHeadings){
		// create a new variable
		ADaMVariable var = new ADaMVariable();
		System.out.println("Study: " + studyID + " Domain: " + domainName + " Variable: " + generalValues.get(0));
		// assign the study, domain and study specific derivation instructions.
		var.setStudyID(studyID);
		var.setDomainName(domainName);
		var.setStudySpecificDerivation(studySpecificDerivation);
		
		if(generalValues.get(0).equalsIgnoreCase("HSCTNREL")){
			System.out.println("WAIT");
		}
		
		// there is a set amount of possible headings that could appear in the DDT.
		ArrayList<String> possibleHeadings = ADaMModel.getADaMHeadings();
		
		// there is a set amount of possible ways to assign a value to a ADaMVariable's data member
		ArrayList<SetDataStrategy> possibleOptions = createStrategyList();
		
		// for each of the headings found in the domain.
		for(int i = 0; i < foundHeadings.size(); i++){
			// check if the current heading found in the domain is one that can be handled.
			int index = possibleHeadings.indexOf(foundHeadings.get(i));
			
			// if it was found. it can be dealt with.
			if(index != -1){
				// get the relevant assignment strategy, and use it.
				possibleOptions.get(index).setData(var, generalValues.get(i));	
			}
			
		}
		
		return var;
	}

	
	public static ArrayList<String> getListOfStudies() {
		
		// intialise an empty list of studies titles.
		ArrayList<String> studyTitles = new ArrayList<String>();
		
		// iterate over the map of ADaM Studies in the Model.
		for(Map.Entry<String, ADaMStudy> entry : ADaMModel.getADaMStudies().entrySet())
		{
			// add the current study name to the list of titles.
			studyTitles.add(entry.getKey());
		}
		
		// return the list of study Titles
		return studyTitles;
	}

	public static ArrayList<String> getListOfDomains(String studyID)
	{
		ArrayList<String> domainTitles = new ArrayList<String>();
		// fetch the study, and create a list of strings of the domain titles.
		if(ADaMModel.getADaMStudies().containsKey(studyID.toUpperCase()))
		{
			// get the domains from the specified ADaM study.
			ArrayList<ADaMDomain> domains = ADaMModel.getADaMStudies().get(studyID.toUpperCase()).getDomains();
			
			// for each domain in the study...
			for(ADaMDomain d : domains)
			{
				// add to a list of domain names.
				domainTitles.add(d.getDomainName());
			}
		}
		
		// return the list of domains present in the study.
		return domainTitles;
	}
	
	public static ArrayList<String> getListOfVariables(String studyID, String domainID){
		ArrayList<String> variables = new ArrayList<String>();
	
		// check selected study is valid
		if(ADaMModel.getADaMStudies().containsKey(studyID.toUpperCase())){
			
			// get the domains from the model
			ArrayList<ADaMDomain> domains = ADaMModel.getADaMStudies().get(studyID.toUpperCase()).getDomains();
			
			// initialise a variable for the selected domain.
			ADaMDomain selectedDomain = new ADaMDomain();
			
			// find the domain specified.
			for(ADaMDomain d : domains){
				if(d.getDomainName().equalsIgnoreCase(domainID)){
					// if found, store it.
					selectedDomain = d;
				}
			}
			
			// go through all the variables in the domain and create a list of their names.
			for(ADaMVariable v : selectedDomain.getVariables()){
				variables.add(v.getVariableName());
			}
		}
		
		return variables;
	}

	
	// Strategy Design Pattern.
	// Set of classes used to assign values to newly created variable.
	public interface SetDataStrategy{
		public void setData(ADaMVariable variable, String value);
	}
	
	// Strategies for each variable's data member.	
	static class SetDomainName 				implements SetDataStrategy{
		public void setData(ADaMVariable variable, String value){
			variable.setDomainName(value);
		}
	}
	static class SetVariableName 			implements SetDataStrategy{
		public void setData(ADaMVariable variable, String value){
			variable.setVariableName(value);
		}
	}
	static class SetCodeListRef 			implements SetDataStrategy{
		public void setData(ADaMVariable variable, String value){
			variable.setCodeListRef(value);
		}
	}
	static class SetDisplayFormat 			implements SetDataStrategy{
		public void setData(ADaMVariable variable, String value){
			variable.setDisplayFormat(value);
		}
	}
	static class SetDocumentOID 			implements SetDataStrategy{
		public void setData(ADaMVariable variable, String value){
			variable.setDocumentOID(value);
		}
	}
	static class SetLength 					implements SetDataStrategy{
		public void setData(ADaMVariable variable, String value){
			variable.setLength(value);
		}
	}
	static class SetMandatory 				implements SetDataStrategy{
		public void setData(ADaMVariable variable, String value){
			variable.setMandatory(value);
		}
	}
	static class SetMethodType 				implements SetDataStrategy{
		public void setData(ADaMVariable variable, String value){
			variable.setMethodType(value);
		}
	}
	static class SetNeedVLM 				implements SetDataStrategy{
		public void setData(ADaMVariable variable, String value){
			variable.setNeedVLM(value);
		}
	}
	static class SetOrigin 					implements SetDataStrategy{
		public void setData(ADaMVariable variable, String value){
			variable.setOrigin(value);
		}
	}
	static class SetReviewCommitteeNotes 	implements SetDataStrategy{
		public void setData(ADaMVariable variable, String value){
			variable.setReviewCommitteeNotes(value);
		}
	}
	static class SetSignificantDigits 		implements SetDataStrategy{
		public void setData(ADaMVariable variable, String value){
			variable.setSignificantDigits(value);
		}
	}
	static class SetSourceDerivation 		implements SetDataStrategy{
		public void setData(ADaMVariable variable, String value){
			variable.setSourceDerivation(value);
		}
	}
	static class SetVariableLabel 			implements SetDataStrategy{
		public void setData(ADaMVariable variable, String value){
			variable.setVariableLabel(value);
		}
	}
	static class SetXMLDataType 			implements SetDataStrategy{
		public void setData(ADaMVariable variable, String value){
			variable.setXMLDataType(value);
		}
	}
	static class SetCoreVariable			implements SetDataStrategy{
		public void setData(ADaMVariable variable, String value){
			variable.setCoreVariable(value);
		}
	}
	
	// not the best use of a array... makes use of lookup array in Model.
	public static ArrayList<SetDataStrategy> createStrategyList(){
		return new ArrayList<SetDataStrategy>(Arrays.asList(new SetVariableName()
											               ,new SetVariableLabel()
											               ,new SetOrigin()
											               ,new SetMethodType()
											               ,new SetSourceDerivation()
											               ,new SetDocumentOID()
											               ,new SetReviewCommitteeNotes()
											               ,new SetCodeListRef()
											               ,new SetNeedVLM()
											               ,new SetXMLDataType()
											               ,new SetLength()
											               ,new SetSignificantDigits()
											               ,new SetDisplayFormat()
											               ,new SetMandatory()
											               ,new SetCoreVariable()
											               ,new SetReviewCommitteeNotes()));
	}

	public static ADaMVariable getVariable(String studyID, String domainName, String variableName) {
		ADaMStudy s =  ADaMModel.getADaMStudies().get(studyID.toUpperCase());
		
		ADaMDomain selectedDomain = new ADaMDomain();
		ADaMVariable selectedVar = new ADaMVariable();
		for(ADaMDomain d : s.getDomains())
		{
			if(d.getDomainName().equalsIgnoreCase(domainName)){
				selectedDomain = d;
			}
		}
		for(ADaMVariable v : selectedDomain.getVariables()){
			if (v.getVariableName().equalsIgnoreCase(variableName)){
				selectedVar = v;
			}
		}
		
		return selectedVar;
	}
	
	
	
}

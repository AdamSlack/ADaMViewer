import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JEditorPane;
import java.awt.Toolkit;

public class ADaMView {

	private JFrame SmellyMelon;
	private static JList studyList;
	private static JList variableList;
	private static JList domainList;

	private static String selectedStudyID;
	private static String selectedDomainID;
	private static String selectedVariableName;
	
	private static JTextField variableNameField;
	private static JTextField variableLabelField;
	private static JTextField originField;
	private static JTextField lengthField;
	private static JTextField XMLDataTypeField;
	private static JTextField codeListRefField;
	private static JTextField coreVariableField;
	private static JTextField mandatoryField;
	private static JEditorPane reviewCommitteeNotesField;
	private static JTextField needVLMField;
	private static JTextField displayFormatField;
	private static JTextField significantDigitsField;
	private static JTextField methodTypeField;
	private static JTextField documentOIDField;
	private static JEditorPane sourceDerivationField;
	private static JEditorPane studySpecificDerivationField;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ADaMView window = new ADaMView();
					window.SmellyMelon.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ADaMView() {
		initialize();
	}

	/**
	 * Initialise the contents of the frame.
	 */
	private void initialize() {
		SmellyMelon = new JFrame();
		SmellyMelon.setIconImage(Toolkit.getDefaultToolkit().getImage(ADaMView.class.getResource("/resources/watermelon-36905_960_720.png")));

		SmellyMelon.setTitle("SmellyMelon");
		SmellyMelon.setBounds(100, 100, 917, 655);
		SmellyMelon.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SmellyMelon.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel root = new JPanel();
		SmellyMelon.getContentPane().add(root, BorderLayout.CENTER);
		root.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel panel = new JPanel();
		root.add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[15%,grow][15%,grow][grow][grow][grow][grow]", "[150px:n,grow][150px:n,grow]"));
		
		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Studies", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_8, "cell 0 0,grow");
		panel_8.setLayout(new MigLayout("", "[100px:n,grow]", "[150px:n,grow]"));
		
		JScrollPane studiesScrollPane = new JScrollPane();
		panel_8.add(studiesScrollPane, "cell 0 0,grow");
		
		JPanel studiesPanel = new JPanel();
		studiesScrollPane.setViewportView(studiesPanel);
		studiesPanel.setLayout(new BorderLayout(0, 0));
		
		studyList = new JList();
		studyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		studyList.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				JList list = (JList)e.getSource();
				if(e.getClickCount() > 1)
				{
					selectedStudyID = (String)studyList.getSelectedValue();
					displayDomains(ADaMController.getListOfDomains(selectedStudyID));
					ADaMView.clearVariables();
				}
			}
		});
		
		studiesPanel.add(studyList, BorderLayout.CENTER);
		
		JPanel panel_10 = new JPanel();
		panel_10.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "ADaM Domain Variables", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_10, "cell 1 0 1 2,grow");
		panel_10.setLayout(new MigLayout("", "[100px:n,grow]", "[150px:n,grow][150px:n,grow]"));
		
		JScrollPane variablesScrollPane = new JScrollPane();
		panel_10.add(variablesScrollPane, "cell 0 0 1 2,grow");
		
		JPanel variablesPanel = new JPanel();
		variablesScrollPane.setViewportView(variablesPanel);
		variablesPanel.setLayout(new BorderLayout(0, 0));
		
		variableList = new JList();
		variableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		variableList.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				
				JList list = (JList)e.getSource();
				if(e.getClickCount() > 1)
				{
					// get the selected item in the list
					selectedVariableName = (String)variableList.getSelectedValue();
					
					// get the variable from the model
					ADaMVariable selectedVariable = ADaMController.getVariable(selectedStudyID, selectedDomainID, selectedVariableName); // TODO: implement it.
					
					// go through each display strategy and execute it.
					for(DisplayDataStrategy ds : createStrategyList()){
						ds.DisplayData(selectedVariable);
					}
				}
				
			}
		});
		
		variablesPanel.add(variableList, BorderLayout.CENTER);
		
		JPanel detailsPanel = new JPanel();
		panel.add(detailsPanel, "cell 2 0 4 2,grow");
		detailsPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		detailsPanel.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new MigLayout("", "[grow]", "[][][][grow]"));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, "cell 0 0 2 1,grow");
		panel_2.setLayout(new MigLayout("", "[50%,grow][50%,grow]", "[333px,grow]"));
		
		JPanel panel_12 = new JPanel();
		panel_2.add(panel_12, "cell 0 0,grow");
		panel_12.setLayout(new MigLayout("", "[grow]", "[333px,grow]"));
		
		JPanel panel_5 = new JPanel();
		panel_12.add(panel_5, "cell 0 0,grow");
		panel_5.setLayout(new MigLayout("", "[100px:n][100px:n,grow]", "[grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow][grow]"));
		
		JLabel lblVariableName = new JLabel("Variable Name");
		panel_5.add(lblVariableName, "cell 0 0,alignx trailing");
		
		variableNameField = new JTextField();
		panel_5.add(variableNameField, "cell 1 0,grow");
		variableNameField.setColumns(10);
		
		JLabel lblVariableLabel = new JLabel("Variable Label");
		panel_5.add(lblVariableLabel, "cell 0 1,alignx trailing");
		
		variableLabelField = new JTextField();
		panel_5.add(variableLabelField, "cell 1 1,grow");
		variableLabelField.setColumns(10);
		
		JLabel lblOrigin = new JLabel("Origin");
		panel_5.add(lblOrigin, "cell 0 2,alignx trailing");
		
		originField = new JTextField();
		panel_5.add(originField, "cell 1 2,grow");
		originField.setColumns(10);
		
		JLabel lblLength = new JLabel("Length");
		panel_5.add(lblLength, "cell 0 3,alignx trailing");
		
		lengthField = new JTextField();
		panel_5.add(lengthField, "cell 1 3,grow");
		lengthField.setColumns(10);
		
		JSeparator separator = new JSeparator();
		panel_5.add(separator, "cell 0 4 2 1");
		
		JLabel lblXmlDataType = new JLabel("XML Data Type");
		panel_5.add(lblXmlDataType, "cell 0 5,alignx trailing");
		
		XMLDataTypeField = new JTextField();
		panel_5.add(XMLDataTypeField, "cell 1 5,grow");
		XMLDataTypeField.setColumns(10);
		
		JLabel lblCodeListRef = new JLabel("Code List Ref");
		panel_5.add(lblCodeListRef, "cell 0 6,alignx trailing");
		
		codeListRefField = new JTextField();
		panel_5.add(codeListRefField, "cell 1 6,grow");
		codeListRefField.setColumns(10);
		
		JLabel lblCoreVariable = new JLabel("Core Variable");
		panel_5.add(lblCoreVariable, "cell 0 7,alignx trailing");
		
		coreVariableField = new JTextField();
		panel_5.add(coreVariableField, "cell 1 7,grow");
		coreVariableField.setColumns(10);
		
		JLabel lblMandatory = new JLabel("Mandatory");
		panel_5.add(lblMandatory, "cell 0 8,alignx trailing");
		
		mandatoryField = new JTextField();
		panel_5.add(mandatoryField, "cell 1 8,grow");
		mandatoryField.setColumns(10);
		
		JLabel lblNeedVlm = new JLabel("Need VLM");
		panel_5.add(lblNeedVlm, "cell 0 9,alignx right");
		
		needVLMField = new JTextField();
		panel_5.add(needVLMField, "flowx,cell 1 9,growx");
		needVLMField.setColumns(10);
		
		JLabel lblDisplayFormat = new JLabel("Display Format");
		panel_5.add(lblDisplayFormat, "cell 0 10,alignx right");
		
		displayFormatField = new JTextField();
		panel_5.add(displayFormatField, "cell 1 10,growx");
		displayFormatField.setColumns(10);
		
		JLabel lblSignificantDigits = new JLabel("Significant Digits");
		panel_5.add(lblSignificantDigits, "cell 0 11,alignx right");
		
		significantDigitsField = new JTextField();
		panel_5.add(significantDigitsField, "cell 1 11,growx");
		significantDigitsField.setColumns(10);
		
		JLabel lblMethodType = new JLabel("Method Type");
		panel_5.add(lblMethodType, "cell 0 12,alignx right");
		
		methodTypeField = new JTextField();
		panel_5.add(methodTypeField, "cell 1 12,growx");
		methodTypeField.setColumns(10);
		
		JLabel lblDocumentOid = new JLabel("Document OID");
		panel_5.add(lblDocumentOid, "cell 0 13,alignx right");
		
		documentOIDField = new JTextField();
		panel_5.add(documentOIDField, "cell 1 13,growx");
		documentOIDField.setColumns(10);
		
		JPanel panel_11 = new JPanel();
		panel_2.add(panel_11, "cell 1 0,grow");
		panel_11.setLayout(new MigLayout("", "[grow]", "[333px,grow]"));
		
		JPanel ReviewCommitteeNotesPanel = new JPanel();
		panel_11.add(ReviewCommitteeNotesPanel, "cell 0 0 2 1,grow");
		ReviewCommitteeNotesPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Implementation and Review Committee Notes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		ReviewCommitteeNotesPanel.setLayout(new BorderLayout(0, 0));
		
		reviewCommitteeNotesField = new JEditorPane();
		reviewCommitteeNotesField.setEditable(false);
		ReviewCommitteeNotesPanel.add(reviewCommitteeNotesField);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Study Specific Derivation", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.add(panel_6, "cell 0 1 1 3,grow");
		panel_6.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_6.add(scrollPane, "cell 0 0,grow");
		
		studySpecificDerivationField = new JEditorPane();
		scrollPane.setViewportView(studySpecificDerivationField);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Source Derivation", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.add(panel_7, "cell 1 2 1 2,grow");
		panel_7.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_7.add(scrollPane_1, "cell 0 0,grow");
		
		sourceDerivationField = new JEditorPane();
		scrollPane_1.setViewportView(sourceDerivationField);
		
		JPanel panel_9 = new JPanel();
		panel_9.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "ADaM Domains", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_9, "cell 0 1,grow");
		panel_9.setLayout(new MigLayout("", "[100px:n,grow]", "[150px:n,grow]"));
		
		JScrollPane domainsScrollPane = new JScrollPane();
		panel_9.add(domainsScrollPane, "cell 0 0,grow");
		
		JPanel domainsPanel = new JPanel();
		domainsScrollPane.setViewportView(domainsPanel);
		domainsPanel.setLayout(new BorderLayout(0, 0));
		
		domainList = new JList();
		domainList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		domainList.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				JList list = (JList)e.getSource();
				if(e.getClickCount() > 1)
				{
					selectedDomainID = (String)domainList.getSelectedValue();
					displayVariables(ADaMController.getListOfVariables(selectedStudyID, selectedDomainID));
					
				}
			}
		});
		domainsPanel.add(domainList, BorderLayout.CENTER);
		
		JMenuBar menuBar = new JMenuBar();
		SmellyMelon.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		// Add a open item to the file menu... file -> open -> ....
		mnFile.add(createFileOpenMenuOption());
	}
	
	private static JMenuItem createFileOpenMenuOption()
	{
		// File -> Open. File Chooser.
				JMenuItem mntmOpen = new JMenuItem("Open");
				mntmOpen.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent arg0) {
						
						// Create a file Chooser object. default browsing location - > //filesrv09/stat
						/*\\amg103\\utilities\\mddt*/
						JFileChooser fc = new JFileChooser("\\\\filesrv09\\stat");
						
						// create a file filter
						FileNameExtensionFilter filter = new FileNameExtensionFilter("Spreadsheets", "xlsx", "xls", "gif", "jpeg");
						
						// apply the filter
						fc.setFileFilter(filter);
						// get the return value of the filechooser dialog.
						int returnVal = fc.showOpenDialog(null);
						
						// if there was a selection, try and open the file.
						if(returnVal == JFileChooser.APPROVE_OPTION){
							try{
								// Get the selected file.
								File selection = fc.getSelectedFile();
								
								// Read the selected file
								ADaMController.readFile(selection);
								
								// NOTE: now done in the ADaMController. See loadFile().
								// put the file contents into the model
								//ADaMController.assignDomains();
								
								// Display the model in the UI
								displayStudies(ADaMController.getListOfStudies());
							}
							catch(IOException e){
								// TODO: change print line to be a UI Alert.
								System.out.println("Error opening File: " + e);
							}
							
						}
					}
				});
				
				// return the newly created Menu Item.
				return mntmOpen;
	}

	protected static void displayStudies(ArrayList<String> aDaMStudies) {
		getStudyList().setListData(new Vector<String>(aDaMStudies));
	}
	
	public static JList getStudyList() {
		return studyList;
	}

	public static void displayDomains(ArrayList<String> domainTitles) {
		getDomainList().setListData(new Vector<String>(domainTitles));
	}
	
	public static void displayVariables(ArrayList<String> variableNames) {
		getVariableList().setListData(new Vector<String>(variableNames));
	}

	public static void clearVariables() {
		getVariableList().setListData(new Vector<String>());
		
	}
	public static JList getVariableList() {
		return variableList;
	}
	public static JList getDomainList() {
		return domainList;
	}

	
	// interface for the display strategy needed for each variable's data member.
	// right now each thing is just being shoved into a text box. but things will change
	// when i get round to it. like check boxes and things...
	public interface DisplayDataStrategy{
		public void DisplayData(ADaMVariable variable);
	}
	
	// Strategies for each variable's data member.
	static class DisplayVariableName 			implements DisplayDataStrategy{
		public void DisplayData(ADaMVariable variable){
			String varData = variable.getVariableName();
			variableNameField.setText(varData);
		}
	} // Used
	static class DisplayCodeListRef 			implements DisplayDataStrategy{
		public void DisplayData(ADaMVariable variable){
			String varData = variable.getCodeListRef();
			codeListRefField.setText(varData);
		}
	}
	static class DisplayDisplayFormat 			implements DisplayDataStrategy{
		public void DisplayData(ADaMVariable variable){
			String varData = variable.getDisplayFormat();
			displayFormatField.setText(varData);
		}
	}
	static class DisplayDocumentOID 			implements DisplayDataStrategy{
		public void DisplayData(ADaMVariable variable){
			String varData = variable.getDocumentOID();
			documentOIDField.setText(varData);
		}
	}
	static class DisplayLength 					implements DisplayDataStrategy{
		public void DisplayData(ADaMVariable variable){
			String varData = variable.getLength();
			lengthField.setText(varData);
		}
	}
	static class DisplayMandatory 				implements DisplayDataStrategy{
		public void DisplayData(ADaMVariable variable){
			String varData = variable.getMandatory();
			mandatoryField.setText(varData);
		}
	}
	static class DisplayMethodType 				implements DisplayDataStrategy{
		public void DisplayData(ADaMVariable variable){
			String varData = variable.getMethodType();
			methodTypeField.setText(varData);
		}
	}
	static class DisplayNeedVLM 				implements DisplayDataStrategy{
		public void DisplayData(ADaMVariable variable){
			String varData = variable.getNeedVLM();
			needVLMField.setText(varData);
		}
	}
	static class DisplayOrigin 					implements DisplayDataStrategy{
		public void DisplayData(ADaMVariable variable){
			String varData = variable.getOrigin();
			originField.setText(varData);
		}
	} // Used
	static class DisplayReviewCommitteeNotes 	implements DisplayDataStrategy{
		public void DisplayData(ADaMVariable variable){
			String varData = variable.getReviewCommitteeNotes();
			reviewCommitteeNotesField.setText(varData);
		}
	}
	static class DisplaySignificantDigits 		implements DisplayDataStrategy{
		public void DisplayData(ADaMVariable variable){
			String varData = variable.getSignificantDigits();
			significantDigitsField.setText(varData);
		}
	}
	static class DisplaySourceDerivation 		implements DisplayDataStrategy{
		public void DisplayData(ADaMVariable variable){
			String varData = variable.getSourceDerivation();
			sourceDerivationField.setText(varData);
		}
	}
	static class DisplaystudySpecificDerivation implements DisplayDataStrategy{
		public void DisplayData(ADaMVariable variable){
			String varData = variable.getSourceDerivation();
			studySpecificDerivationField.setText(varData);
		}
	}
	static class DisplayVariableLabel 			implements DisplayDataStrategy{
		public void DisplayData(ADaMVariable variable){
			String varData = variable.getVariableLabel();
			variableLabelField.setText(varData);
		}
	}
	static class DisplayXMLDataType 			implements DisplayDataStrategy{
		public void DisplayData(ADaMVariable variable){
			String varData = variable.getXMLDataType();
			XMLDataTypeField.setText(varData);
		}
	}
	static class DisplayCoreVariable			implements DisplayDataStrategy{
		public void DisplayData(ADaMVariable variable){
			String varData = variable.getCoreVariable();
			coreVariableField.setText(varData);
		}
	}
	
	public static ArrayList<DisplayDataStrategy> createStrategyList(){
		return new ArrayList<DisplayDataStrategy>(Arrays.asList(new DisplayVariableName()
											               ,new DisplayVariableLabel()
											               ,new DisplayOrigin()
											               ,new DisplayMethodType()
											               ,new DisplaySourceDerivation()
											               ,new DisplayDocumentOID()
											               ,new DisplayReviewCommitteeNotes()
											               ,new DisplayCodeListRef()
											               ,new DisplayNeedVLM()
											               ,new DisplayXMLDataType()
											               ,new DisplayLength()
											               ,new DisplaySignificantDigits()
											               ,new DisplayDisplayFormat()
											               ,new DisplayMandatory()
											               ,new DisplayCoreVariable()
											               ,new DisplaystudySpecificDerivation()
											               ));
	}
	
	public static JEditorPane getSourceDerivationField() {
		return sourceDerivationField;
	}
	public static JEditorPane getStudySpecificDerivationField() {
		return studySpecificDerivationField;
	}
}


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.text.WordUtils;

public class Data {
	
	private final String log = "C:\\Users\\Benjamin\\Dropbox\\Concordia\\Coop\\Applications\\Log\\log.txt";
	private final String path = "C:\\Users\\Benjamin\\Dropbox\\Concordia\\Coop\\Applications\\Answered";
			
	private final String companyRegex = "(?<=Organization:\\s{1,100})([A-Za-z, -éèûçêâàùÉÈÊÂÇÛÙïÏëËüÜôÔöÖ]+)";
	private final String jobTitleRegex = "(?<=\\(JOB ID: [0-9]{5}\\)\\s{1,100})([A-Za-zéèûçêâàùÉÈÊÂÇÛÙïÏëËüÜôÔöÖ, -]+)";
	private final String jobNumberRegex = "(\\(JOB ID: [0-9]{5}\\))";
	private final String firstNameRegex = "(?<=Job Contact First Name:\\s{1,100})([A-Za-zéèûçêâàùÉÈÊÂÇÛÙïÏëËüÜôÔöÖ.-]+)";
	private final String lastNameRegex = "(?<=Job Contact Last Name:\\s{1,100})([A-Za-zéèûçêâàùÉÈÊÂÇÛÙïÏëËüÜôÔöÖ.-]+)";
	private final String formOfAddressRegex = "(?<=Salutation:\\s{1,100})([A-Za-z.]+)";
	private final String cityRegex = "(?<=City:\\s{1,100})(.*)";
	private final String addressRegex = "(?<=Address Line One:\\s{1,100})(.*)";
	private final String postalRegex = "(?<=Postal Code / Zip Code:\\s{1,100})(.*)";
	
	
	private String company;
	private String jobTitle;
	private String jobNumber;
	private String jobNumberFR;
	private String firstName;
	private String lastName;
	private String title;
	private String formOfAddress;
	private String copyPaste;
	private String city;
	private String address1;
	private String postal;
	private String name;
	private String date;
	private int number;
	private String address2;
	private String dateFr;
	private String titleFR;
	private String dateExport;
	
	
	
	public Data(String copyPaste){
		this.copyPaste = copyPaste;
		this.jobNumber = (returnPattern(jobNumberRegex, this.copyPaste)).replaceAll("\\s+$", "");
		this.number = Integer.parseInt(returnPattern("([0-9]{5})", this.jobNumber));
		this.jobNumberFR = "(EMPLOI #: " + this.number + ")";
		this.company = (returnPattern(companyRegex, this.copyPaste)).replaceAll("\\s+$", "");
		this.company = WordUtils.capitalize((this.company).toLowerCase());
		this.jobTitle = (returnPattern(jobTitleRegex, this.copyPaste)).replaceAll("\\s+$", "");
		this.firstName = (returnPattern(firstNameRegex, this.copyPaste)).replaceAll("\\s+$", "");
		this.lastName = (returnPattern(lastNameRegex, this.copyPaste)).replaceAll("\\s+$", "");
		this.formOfAddress = (returnPattern(formOfAddressRegex, this.copyPaste)).replaceAll("\\s+$", "");
		this.formOfAddress = (titleEN()).replaceAll("\\s+$", "");
		this.city = (returnPattern(cityRegex, this.copyPaste)).replaceAll("\\s+$", "");
		this.address1 = (returnPattern(addressRegex, this.copyPaste)).replaceAll("\\s+$", "");
		this.postal = (returnPattern(postalRegex, this.copyPaste)).replaceAll("\\s+$", "");
		this.postal = this.postal.substring(0,3) + " " + (this.postal).substring(3);
		this.name = firstName + " " + lastName;
		this.address2 = city + ", QC " + postal;
		DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
		Calendar cal = Calendar.getInstance();
		date = dateFormat.format(cal.getTime());
		dateFr = dateFR();
		titleFR = titleFR();
		if(!checkNumber()){
			writeNumber();
			saveCopyPaste();
		}
	}
	
	



	public String getCompany() {
		return company;
	}





	public void setCompany(String company) {
		this.company = company;
	}





	public String getJobTitle() {
		return jobTitle;
	}





	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}





	public String getJobNumber() {
		return jobNumber;
	}





	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}





	public String getJobNumberFR() {
		return jobNumberFR;
	}





	public void setJobNumberFR(String jobNumberFR) {
		this.jobNumberFR = jobNumberFR;
	}





	public String getFirstName() {
		return firstName;
	}





	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}





	public String getLastName() {
		return lastName;
	}





	public void setLastName(String lastName) {
		this.lastName = lastName;
	}





	public String getTitle() {
		return title;
	}





	public void setTitle(String title) {
		this.title = title;
	}





	public String getFormOfAddress() {
		return formOfAddress;
	}





	public void setFormOfAddress(String formOfAddress) {
		this.formOfAddress = formOfAddress;
	}





	public String getCopyPaste() {
		return copyPaste;
	}





	public void setCopyPaste(String copyPaste) {
		this.copyPaste = copyPaste;
	}





	public String getCity() {
		return city;
	}





	public void setCity(String city) {
		this.city = city;
	}





	public String getAddress1() {
		return address1;
	}





	public void setAddress1(String address1) {
		this.address1 = address1;
	}





	public String getPostal() {
		return postal;
	}





	public void setPostal(String postal) {
		this.postal = postal;
	}





	public String getName() {
		return name;
	}





	public void setName(String name) {
		this.name = name;
	}





	public String getDate() {
		return date;
	}





	public void setDate(String date) {
		this.date = date;
	}





	public int getNumber() {
		return number;
	}





	public void setNumber(int number) {
		this.number = number;
	}





	public String getAddress2() {
		return address2;
	}





	public void setAddress2(String address2) {
		this.address2 = address2;
	}





	public String getDateFr() {
		return dateFr;
	}





	public void setDateFr(String dateFr) {
		this.dateFr = dateFr;
	}





	public String getTitleFR() {
		return titleFR;
	}





	public void setTitleFR(String titleFR) {
		this.titleFR = titleFR;
	}
	
	public String getDateExport(){
		return dateExport;
	}





	public String toString(){
		return jobTitle + " " + jobNumber + " " + company + " \n" + formOfAddress
				+ " " + name + " \n" + address1 + "\n" + city + ", QC " + postal + "\n" + date;
	}
	
	private String returnPattern(String regex, String copyPaste){
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(copyPaste);
		if (matcher.find()){
			return matcher.group(1);
		}
		else{
			return null;
		}
	}
	
	public boolean writeNumber(){
		PrintWriter write = null;
		
		try{
			write = new PrintWriter(new FileOutputStream(new File(log), true));
			write.println(this.number);
			write.close();
			return true;
		}
		catch(Exception e){
			write.close();
			return false;
		}
	}
	
	public boolean checkNumber(){
		Scanner scan = null;
		try{
			scan = new Scanner(new FileReader(log));
		}
		catch(FileNotFoundException e){
			System.exit(0);
		}
		
		if(!scan.hasNext()){
			scan.close();
			return false;
		}
		
		while(scan.hasNextLine()){
			if (number == Integer.parseInt(scan.nextLine())){
				return true;
			}
		}
		return false;
	}
	
	private String titleFR(){
		if ((this.formOfAddress).matches("Mr")){
			return "M.";
		}
		else if ((this.formOfAddress).matches("Ms") || (this.formOfAddress).matches("Mrs")){
			return "Mme";
		}
		else{
			return this.formOfAddress;
		}
	}
	
	private String titleEN(){
		if ((this.formOfAddress).matches("M\\.")){
			return "Mr.";
		}
		else if ((this.formOfAddress).matches("Mme") || (this.formOfAddress).matches("Mrs")){
			return "Ms.";
		}
		else{
			return this.formOfAddress;
		}
	}
	
	private String dateFR(){
		DateFormat month = new SimpleDateFormat("MM");
		DateFormat day = new SimpleDateFormat("dd");
		DateFormat year = new SimpleDateFormat("yyyy");
		Calendar cal = Calendar.getInstance();
		String mois = month.format(cal.getTime());
		String jour = day.format(cal.getTime());
		String annee = year.format(cal.getTime());
		this.dateExport = jour + "-" + mois + "-" + annee;
		
		int temp = Integer.parseInt(mois);
		switch (temp){
		case 1:
			mois = "janvier";
			break;
		case 2:
			mois = "février";
			break;
		case 3:
			mois = "mars";
			break;
		case 4:
			mois = "avril";
			break;
		case 5:
			mois = "mai";
			break;
		case 6:
			mois = "juin";
			break;
		case 7:
			mois = "juillet";
			break;
		case 8:
			mois = "août";
			break;
		case 9:
			mois = "septembre";
			break;
		case 10:
			mois = "octobre";
			break;
		case 11:
			mois = "novembre";
			break;
		case 12:
			mois = "décembre";
			break;
		default:
			mois = "";
			break;
			
		}
		
		return "Le " + jour + " " + mois + " " + annee;
		
	}
	
	public boolean saveCopyPaste(){
		String fileName;
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal = Calendar.getInstance();
		String date = dateFormat.format(cal.getTime());
		fileName = date + " " + this.company;
		
		String temp = path + fileName + ".txt";
		File file = new File(temp);
		int i = 2;
		while (file.exists()){
			temp += i;
			++i;
			file = new File(temp);
		}
		
		PrintWriter write = null;
		
		try{
			write = new PrintWriter(temp);
			write.println(this.copyPaste);
			write.close();
			return true;
		}
		catch(Exception e){
			write.close();
			return false;
		}
	}
}


public class Driver2 {

	public static void main(String[] args) {
		CoverLetterGenerator coverLetter = new CoverLetterGenerator();
		Data data = coverLetter.getData();
		System.out.println(data.getDate());
		System.out.println("\n" + data.getName());
		System.out.println("\n" + data.getCompany());
		System.out.println(data.getAddress1() + "\n" + data.getAddress2());
		System.out.println();
		System.out.println();
		System.out.println("Re: " + data.getJobTitle() + " " + data.getJobNumber());
		System.out.println("\n");
		System.out.println("Dear " + data.getFormOfAddress() + " " + data.getLastName());
		
		

	}

}

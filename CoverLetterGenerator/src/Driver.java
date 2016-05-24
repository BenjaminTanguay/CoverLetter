import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.docx4j.Docx4jProperties;
import org.docx4j.convert.out.pdf.PdfConversion;
import org.docx4j.convert.out.pdf.viaXSLFO.PdfSettings;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.utils.Log4jConfigurator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.text.WordUtils;
import org.apache.log4j.Level;

public class Driver {
	

	private static final String studentName = "Benjamin Tanguay";
	private static final String program = "Software Engineering";
	private static final String programFR = "génie logiciel";
	private static final String university = "Concordia University";
	private static final String université = "l'Université Concordia";
	private static final String term = "2016 fall term";
	private static final String termFR = "l'automne 2016";
	
	private static final String export = "C:\\Users\\Benjamin\\Dropbox\\Concordia\\Coop\\Applications\\Export\\";
	private static final String pathDocx = "C:\\Users\\Benjamin\\Dropbox\\Concordia\\Coop\\Applications";
	private static final String nameDocx = "\\TEMPLATE_JAVA.docx";
	private static final String pathObjEN = "C:\\Users\\Benjamin\\Dropbox\\Concordia\\Coop\\Applications\\Modeles\\Anglais\\Objet\\";
	private static final String pathWebEN = "C:\\Users\\Benjamin\\Dropbox\\Concordia\\Coop\\Applications\\Modeles\\Anglais\\Web\\";
	private static final String pathObjFR = "C:\\Users\\Benjamin\\Dropbox\\Concordia\\Coop\\Applications\\Modeles\\Francais\\Objet\\";
	private static final String pathWebFR = "C:\\Users\\Benjamin\\Dropbox\\Concordia\\Coop\\Applications\\Modeles\\Francais\\Web\\";
	
	private static CoverLetterGenerator coverLetter;
	private static Data info;
	private static Scanner scan; 
	
	public static void main(String[] args) {
		
		scan = new Scanner(System.in);
		System.out.println("Bienvenue au gestionaire de création de Lettre de présentation. \n Veuillez copier le formulaire de l'annonce.");
		
		while(true){
			System.out.println("\nLe formulaire est-il copié?\n1. Oui\n2. Non\n");
			
			switch(menuInputCheck(scan.nextLine(), 2, 1)){
			case 1:
				coverLetter = new CoverLetterGenerator();
				info = coverLetter.getData();
				menuLangue();
				break;
			case 2:
				fermeture();
			}
		}
		
	
		
		
		
		
		
		
		
	}
	
	private static void menuLangue(){
		while (true){
			System.out.println("Quelle langue de lettre de présentation?\n1. Anglais\n2. Français\n3. Quit\n");
			switch (menuInputCheck(scan.nextLine(), 3, 1)){
			case 1:
				menuType(1);
				return;
			case 2:
				menuType(2);
				return;
			case 3:
				fermeture();
				break;
			}
		}
	}
	
	private static void menuType(int i){
		infoCheck(i);
		while (true){
			String document;
			System.out.println("Quel type de lettre de présentation?\n1. Object Oriented programming\n2. Web\n3. Fast mode\n4.Retourner au menu précédent.\n");
			switch (menuInputCheck(scan.nextLine(), 3, 1)){
			case 1:
				document = contenu(i, 1);
				export(document, i);
				return;
			case 2:
				document = contenu(i, 2);
				export(document, i);
				return;
			case 3:
				String text = read(path(1, 1) + "fast.txt");
				ArrayList<ArrayList<String>> array = arrayGenerator(text);
				displayArray(array);
				translate(array, i, 1);
				document = documentBuild(array, i, 1);
				export(document, i);
				return;
			case 4:
				return;
			}
		}
	}
	
	private static String path(int i, int j){
		String path = "";
		if (i == 1 && j == 1){
			path = pathObjEN;
		}
		else if (i == 1 && j == 2){
			path = pathWebEN;
		}
		else if (i == 2 && j == 1){
			path = pathObjFR;
		}
		else if (i == 2 && j == 2){
			path = pathWebFR;
		}
		return path;
	}
	
	private static String contenu(int i, int j){
		String[] select = {"intro.txt", "development.txt", "conclusion.txt"};
		ArrayList<ArrayList<String>> output = new ArrayList<ArrayList<String>>();
		
		for (int k = 0; k < 3; ++k){
			String path = path(i, j) + select[k];
			
			
			boolean test = true;
			boolean flag = true;
			while (flag){
				String text = read(path);
				ArrayList<ArrayList<String>> array = arrayGenerator(text);
				displayArray(array);
				System.out.println("\nQue voulez-vous faire?\n1. Sélectionner un paragraphe à ajouter au document." +
				"\n2. Ajouter un nouveau paragraphe à la base de donnée. "
				+ "\n3. Ajouter un nouveau paragraphe à la base de donnée en se basant sur un paragraphe existant."
				+ "\n4. Modifier un paragraphe existant dans la base de donnée."
				+ "\n5. Supprimer un paragraphe existant."
				+ "\n6. Poursuivre vers la prochaine section.");
				switch (menuInputCheck(scan.nextLine(), 6, 1)){
				case 1:
					System.out.println("Quel paragraphe souhaitez-vous ajouter au document?");
					int selection = menuInputCheck(scan.nextLine(), array.size() - 1, 0);
					output.add(new ArrayList<String>());
					for (int m = 0; m < (array.get(selection)).size(); ++m){
						(output.get(output.size() - 1)).add((array.get(selection)).get(m));
					}
					System.out.println("\nOutput text so far:\n---------------------------------------------------------------------\n");
					displayArray(output);
					System.out.println("\n---------------------------------------------------------------------\n");
					break;
				case 2:
					array.add(new ArrayList<String>());
					int counter = 0;
					boolean control = true;
					while (control){
						System.out.println("Veuillez entrer la phrase #" + counter);
						test = true;
						while (test){
							String input = scan.nextLine();
							System.out.println("Vous avez entré: " + input + "\nEst-ce que cette information est correcte?\n1. Oui\n2. Non\n");
							switch(menuInputCheck(scan.nextLine(), 2, 1)){
							case 1:
								test = false;
								(array.get(array.size()-1)).add(input);
								++counter;
								break;
							case 2:
								test = true;
								break;
							}
						}
					}
					break;
				case 3:
					System.out.println("Quel paragraphe souhaitez-vous utiliser comme base?");
					selection = menuInputCheck(scan.nextLine(), array.size() - 1, 0);
					ArrayList<String> arrayTemp = new ArrayList<String>();
					for (int n = 0; n < (array.get(selection)).size(); ++n){
						arrayTemp.add((array.get(selection)).get(n));
					}
					test = true;
					while (test){
						displaySelection(array, selection);
						System.out.println("Que voulez-vous faire?\n1. Remplacer une phrase"
								+ "\n2. Ajouter une phrase\n3. Supprimer une phrase\n4. Quitter ce menu\n");
						int choice;
						boolean inputCheck;
						switch (menuInputCheck(scan.nextLine(), 4, 1)){
						case 1:
							displaySelection(array, selection);
							System.out.println("Quelle phrase voulez-vous remplacer?");
							choice = menuInputCheck(scan.nextLine(), arrayTemp.size() - 1, 0);
							inputCheck = true;
							while (inputCheck){
								System.out.println("Quelle est la nouvelle phrase?");
								String temporaire = scan.nextLine();
								System.out.println("Vous avez entré: " + temporaire + "\nEst-ce exact?\n1. Oui\n2. Non");
								switch(menuInputCheck(scan.nextLine(),2, 1)){
								case 1:
									inputCheck = false;
									arrayTemp.set(choice, temporaire);
									break;
								case 2:
									inputCheck = true;
									break;
								}
							}
							break;
						case 2:
							displaySelection(array, selection);
							System.out.println("Où voulez-vous insérer la nouvelle phrase? (La nouvelle phrase sera insérée "
									+ "avant le texte existant à la ligne désignée. Pour ajouter une phrase à la fin du paragraphe,"
									+ " veuillez choisir la ligne suivante la dernière ligne ou il y a du texte.");
							choice = menuInputCheck(scan.nextLine(), arrayTemp.size(), 0);
							inputCheck = true;
							while (inputCheck){
								System.out.println("Quelle est la nouvelle phrase?");
								String temporaire = scan.nextLine();
								System.out.println("Vous avez entré: " + temporaire + "\nEst-ce exact?\n1. Oui\n2. Non");
								switch(menuInputCheck(scan.nextLine(),2, 1)){
								case 1:
									inputCheck = false;
									if (choice == arrayTemp.size()){
										arrayTemp.add(temporaire);
									}
									else{
										arrayTemp.add(choice, temporaire);
									}
									break;
								case 2:
									inputCheck = true;
									break;
								}
							}
							break;
						case 3:
							displaySelection(array, selection);
							System.out.println("Quelle phrase voulez-vous supprimer?");
							choice = menuInputCheck(scan.nextLine(), arrayTemp.size(), 0);
							
							System.out.println("Êtes-vous certain de vouloir supprimer la phrase #" + choice + "\n1. Oui\n2. Non\n");
							switch(menuInputCheck(scan.nextLine(), 2, 1)){
							case 1:
								arrayTemp.remove(choice);
								arrayTemp.trimToSize();
								break;
							case 2:
								break;
							}
						case 4:
							test = false;
							break;
						}
					}
					array.add(arrayTemp);
					commitToDatabase(array, path);
					break;
				case 4:
					System.out.println("Quel paragraphe souhaitez-vous modifier?");
					selection = menuInputCheck(scan.nextLine(), array.size() - 1, 0);
					displaySelection(array, selection);
					test = true;
					while (test){
						System.out.println("Que voulez-vous faire?\n1. Remplacer une phrase"
								+ "\n2. Ajouter une phrase\n3. Supprimer une phrase\n4. Quitter ce menu\n");
						int choice;
						boolean inputCheck;
						switch (menuInputCheck(scan.nextLine(), 4, 1)){
						case 1:
							System.out.println("Quelle phrase voulez-vous remplacer?");
							choice = menuInputCheck(scan.nextLine(), (array.get(selection)).size() - 1, 0);
							inputCheck = true;
							while (inputCheck){
								System.out.println("Quelle est la nouvelle phrase?");
								String temporaire = scan.nextLine();
								System.out.println("Vous avez entré: " + temporaire + "\nEst-ce exact?\n1. Oui\n2. Non");
								switch(menuInputCheck(scan.nextLine(),2, 1)){
								case 1:
									inputCheck = false;
									(array.get(selection)).set(choice, temporaire);
									break;
								case 2:
									inputCheck = true;
									break;
								}
							}
							break;
						case 2:
							System.out.println("Où voulez-vous insérer la nouvelle phrase? (La nouvelle phrase sera insérée "
									+ "avant le texte existant à la ligne désignée. Pour ajouter une phrase à la fin du paragraphe,"
									+ " veuillez choisir la ligne suivante la dernière ligne ou il y a du texte.");
							choice = menuInputCheck(scan.nextLine(), (array.get(selection)).size(), 0);
							inputCheck = true;
							while (inputCheck){
								System.out.println("Quelle est la nouvelle phrase?");
								String temporaire = scan.nextLine();
								System.out.println("Vous avez entré: " + temporaire + "\nEst-ce exact?\n1. Oui\n2. Non");
								switch(menuInputCheck(scan.nextLine(),2, 1)){
								case 1:
									inputCheck = false;
									if (choice == (array.get(selection)).size()){
										(array.get(selection)).add(temporaire);
									}
									else{
										(array.get(selection)).add(choice, temporaire);
									}
									break;
								case 2:
									inputCheck = true;
									break;
								}
							}
							break;
						case 3:
							System.out.println("Quelle phrase voulez-vous supprimer?");
							choice = menuInputCheck(scan.nextLine(), (array.get(selection)).size(), 0);
							
							System.out.println("Êtes-vous certain de vouloir supprimer la phrase #" + choice + "\n1. Oui\n2. Non\n");
							switch(menuInputCheck(scan.nextLine(), 2, 1)){
							case 1:
								(array.get(selection)).remove(choice);
								(array.get(selection)).trimToSize();
								break;
							case 2:
								break;
							}
						case 4:
							test = false;
							break;
						}
					}
					commitToDatabase(array, path);
					break;
				case 5:
					displayArray(array);
					System.out.println("Quel paragraphe souhaitez-vous modifier?");
					int choice = menuInputCheck(scan.nextLine(), array.size() - 1, 0);
					System.out.println("Voulez-vous vraiment supprimer le paragraphe #" + choice +"?\n1. Oui\n2. Non\n" );
					switch(menuInputCheck(scan.nextLine(), 1, 2)){
					case 1:
						array.remove(choice);
						array.trimToSize();
						break;
					case 2:
						break;
					}
					break;
				case 6:
					flag = false;
					break;
				}
			}
		}
		translate(output, i, j);
		return documentBuild(output, i, j);
	}
	
	private static String documentBuild(ArrayList<ArrayList<String>> output, int i, int j){
		
		String document = "";
		
		for (int n = 0; n < output.size(); ++n){
			for (int m = 0; m < (output.get(n)).size(); ++m){
				document += (output.get(n)).get(m) + " ";
			}
			document += "\n\n";
		}
		if (i == 1){
			document += "Thank you for considering me for this position.\n\nSincerely,";
		}
		else if (i == 2){
			document += "Restant disponible pour un entretien où je pourrais vous exposer de vive voix mes motivations, je vous prie d'agréer l'expression de ma considération distinguée.";
		}
		
		return document;
	}
	
	private static void displaySelection(ArrayList<ArrayList<String>> array, int select){
		for (int i = 0; i < (array.get(select)).size(); ++i){
			System.out.println(i + ": " + (array.get(select)).get(i));
		}
	}
	
	private static void commitToDatabase(ArrayList<ArrayList<String>> array, String path){
	PrintWriter write = null;
		
		try{
			write = new PrintWriter(new FileOutputStream(new File(path), false));
			for (int i = 0; i < array.size(); ++i){
				write.println("<p" + i + ">");
				for (int j = 0; i < (array.get(i)).size(); ++j){
					write.println("<s" + j + ">" + (array.get(i)).get(j));
				}
			}
			write.close();
			
		}
		catch(Exception e){
			write.close();
			
		}
	}
	
	private static void commitToDatabase2(ArrayList<String> array, String path){
		PrintWriter write = null;
			
			try{
				write = new PrintWriter(new FileOutputStream(new File(path), false));
				for (int i = 0; i < array.size(); ++i){
					write.println("<s" + i + ">" + array.get(i));
					
				}
				write.close();
				
			}
			catch(Exception e){
				write.close();
				
			}
		}
	
	
	private static void displayArray(ArrayList<ArrayList<String>> array){
		for (int i = 0; i < array.size(); ++i){
			System.out.println("Paragraph #" + i);
			for (int j = 0; j < (array.get(i)).size(); ++j){
				System.out.println(j + ": " + (array.get(i)).get(j));
			}
		}
	}
	
	private static ArrayList<ArrayList<String>> arrayGenerator(String text){
		Scanner reader = new Scanner(text);
		ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();
		String temp = "";
		boolean firstPar = true;
		int lineCount = 0;
		while(reader.hasNextLine()){
			temp = reader.nextLine();
			if (temp.matches("<p[0-9]+>")){
				array.add(new ArrayList<String>());
				if (firstPar){
					firstPar = false;
				}
				else{
					++lineCount;
				}
			}
			else if (temp.matches("<s[0-9]+>.*")){
				if (temp.matches("<s[0-9]>.*")){
					temp = returnPattern("(?<=<s[0-9]>)(.*)", temp);
					(array.get(lineCount)).add(temp);
				}
				else if (temp.matches("<s[0-9][0-9]>.*")){
					temp = returnPattern("(?<=<s[0-9][0-9]>)(.*)", temp);
					(array.get(lineCount)).add(temp);
				}
			}
		}
		reader.close();
		return array;
	}
	
	private static String returnPattern(String regex, String text){
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()){
			return matcher.group(1);
		}
		else{
			return null;
		}
	}
	
	private static void translate(ArrayList<ArrayList<String>> output, int i, int j){
		
		String path = path(i, j) + "replacementSentence.txt";
		String replacementSentence = read(path);
		Scanner reader = new Scanner(replacementSentence);
		ArrayList<String> array = new ArrayList<String>();
		String temporary = "";
		while(reader.hasNextLine()){
			temporary = reader.nextLine();
			if (temporary.matches("<s[0-9]+>.*")){
				if (temporary.matches("<s[0-9]>.*")){
					temporary = returnPattern("(?<=<s[0-9]>)(.*)", temporary);
					array.add(temporary);
				}
				else if (temporary.matches("<s[0-9][0-9]>.*")){
					array.add(temporary);
				}
			}
		}
		reader.close();
		
		for (int n = 0; n < output.size(); ++n){
			for (int m = 0; m < (output.get(n)).size(); ++m){
				
				(output.get(n)).set(m, ((output.get(n)).get(m)).replace("<NAME>", studentName));
				
				if (i == 1){
					(output.get(n)).set(m, ((output.get(n)).get(m)).replace("<PROGRAM>", program));
					(output.get(n)).set(m, ((output.get(n)).get(m)).replace("<UNIVERSITY>", university));
					(output.get(n)).set(m, ((output.get(n)).get(m)).replace("<JOBTITLE>", info.getJobTitle()));
					(output.get(n)).set(m, ((output.get(n)).get(m)).replace("<COMPANY>", info.getCompany()));
					(output.get(n)).set(m, ((output.get(n)).get(m)).replace("<TERM>", term));
				}
				if (i == 2){
					(output.get(n)).set(m, ((output.get(n)).get(m)).replace("<PROGRAM>", programFR));
					(output.get(n)).set(m, ((output.get(n)).get(m)).replace("<UNIVERSITY>", université));
					(output.get(n)).set(m, ((output.get(n)).get(m)).replace("<JOBTITLE>", info.getJobTitle()));
					(output.get(n)).set(m, ((output.get(n)).get(m)).replace("<COMPANY>", info.getCompany()));
					(output.get(n)).set(m, ((output.get(n)).get(m)).replace("<TERM>", termFR));
				}
				while (((output.get(n)).get(m)).contains("<INPUT>")){
					boolean flag = true;
					while (flag){
						System.out.println("Vous devez remplacer le premier tag <INPUT> du fichier par un texte. En attente du texte de remplacement...");
						String temp = scan.nextLine();
						System.out.println("Vous avez entré: " + temp + "\nEst-ce que cette information est exacte?\n1. Oui\n2. Non\n");
						switch(menuInputCheck(scan.nextLine(),2, 1)){
						case 1:
							flag = false;
							(output.get(n)).set(m, ((output.get(n)).get(m)).replaceFirst("<INPUT>", temp));
							break;
						case 2:
							break;
						}
					}
				}

				if (((output.get(n)).get(m)).contains("<INPUT2>")){
					System.out.println((output.get(n)).get(m));
					System.out.println("\nVous devez remplacer le tag <INPUT2> du fichier par une phrase. Voici les phrases en banque:\n");
					for (int count = 0; count < array.size(); ++count){
						System.out.println(count + ": " + array.get(count));
					}
					System.out.println("\nVoulez-vous: \n1. Utiliser une phrase en banque\n2. Écrire une nouvelle phrase");
					switch (menuInputCheck(scan.nextLine(), 2, 1)){
					case 1:
						System.out.println("Quelle phrase voulez-vous utiliser?");
						int choice = menuInputCheck(scan.nextLine(), array.size() - 1, 0);
						(output.get(n)).set(m, ((output.get(n)).get(m)).replaceFirst("<INPUT2>", array.get(choice)));
						break;
					case 2:
						boolean flag = true;
						while (flag){
							System.out.println("Veuillez entrer la phrase que vous voulez utiliser.");
							String sentence = scan.nextLine();
							System.out.println("Vous avez entré: " + sentence + "\nEst-ce que l'information est correcte?\n1. Oui\n2. Non\n");
							switch(menuInputCheck(scan.nextLine(), 2, 1)){
							case 1:
								flag = false;
								(output.get(n)).set(m, ((output.get(n)).get(m)).replaceFirst("<INPUT2>", sentence));
								array.add(sentence);
								commitToDatabase2(array, path);
								break;
							case 2:
								break;
							}	
						}		
						break;
					}
				}
				
				(output.get(n)).set(m, ((output.get(n)).get(m)).replaceAll(" {2,}", " "));
			}
		}
	}
	
	
	
	public static String read(String path){
		Scanner scan = null;
		String file = "";
		try{
			scan = new Scanner(new FileReader(path));
		}
		catch(FileNotFoundException e){
			System.out.println("Couldn't open the file to be read.\n" + path);
		}
		
		while(scan.hasNextLine()){
			file += scan.nextLine() + "\n";
		}
		return file;
	}
	
	
	private static void infoCheck(int i){
		boolean flag = true;
		while (flag){
			System.out.println("Quel est le titre de " + info.getName() + "?");
			String temp = scan.nextLine();
			System.out.println("Vous avez entré: \"" + temp + "\"\nEst-ce que l'information est correcte?\n1. Oui\n2. Non");
			switch (menuInputCheck(scan.nextLine(), 2, 1)){
			case 1:
				flag = false;
				info.setTitle(temp);
				break;
			case 2:
				flag = true;
				break;
			}
		}
		flag = true;
		while (flag){
			System.out.println("\nLe nom du travail entré est: " + info.getJobTitle() + "\nEst-ce correct?\n1. Oui\n2. Non\n");
			switch (menuInputCheck(scan.nextLine(), 2, 1)){
			case 1:
				boolean loop = true;
				while (loop){
					System.out.println("Change-t-on les majuscules du titre?\n1. Oui\n2. Non\n");
					switch (menuInputCheck(scan.nextLine(),2, 1)){
					case 1:
						info.setJobTitle(cap(info.getJobTitle()));
						loop = false;
						flag = false;
						break;
					case 2:
						loop = false;
						flag = false;
						break;
					}	
				}
				break;
				
			case 2:
				loop = true;
				while (loop){
					System.out.println("Quel est le nouveau titre du travail?");
					String temp = scan.nextLine();
					System.out.println("Vous avez entré: \"" + temp + "\"\nEst-ce que l'information est correcte?\n1. Oui\n2. Non\n");
					switch (menuInputCheck(scan.nextLine(), 2, 1)){
					case 1:
						loop = false;
						info.setJobTitle(temp);
						break;
					case 2:
						loop = true;
						break;
					}	
				}
				break;
			}	
		}
		boolean check = true;
		while (check){
			System.out.println("L'info entrée est: " +
		"\n1. Date:                                 " + ((i == 1) ? info.getDate() : info.getDateFr()) +
		"\n2. Compagnie:                            " + info.getCompany() +
		"\n3. Nom du travail:                       " + info.getJobTitle() +
		"\n4. Numéro d'emploi:                      " + info.getJobNumber() +
		"\n5. Nom de la personne contact:           " + info.getName() +
		"\n6. Titre de la personne contact:         " + info.getTitle() +
		"\n7. Honorificatif de la personne contact: " + ((i == 1) ? info.getFormOfAddress() : info.getTitleFR()) +
		"\n8. Addresse ligne 1:                     " + info.getAddress1() +
		"\n9. Addresse ligne 2:                     " + info.getAddress2());
			System.out.println("Est-ce que l'information est correcte?\n1. Oui\n2. Non\n");
			switch (menuInputCheck(scan.nextLine(), 2, 1)){
			case 1:
				check = false;
				break;
			case 2:
				check = true;
				System.out.println("Quelle information voulez-vous changer?");
				int temp = menuInputCheck(scan.nextLine(),9, 1);
				flag = true;
				String value = "";
				while (flag){
					System.out.println("Quelle est la nouvelle valeur?");
					value = scan.nextLine();
					System.out.println("Vous avez entré: " + value + "\nEst-ce correct?\n1. Oui\n2. Non\n");
					switch(menuInputCheck(scan.nextLine(), 2, 1)){
					case 1:
						flag = false;
						break;
					case 2:
						flag = true;
						break;
					}
				}
				switch (temp){
				case 1:
					if (i == 1){
						info.setDate(value);
					}
					else if (i == 2){
						info.setDateFr(value);
					}
					break;
				case 2:
					info.setCompany(value);
					break;
				case 3:
					info.setJobTitle(value);
					break;
				case 4:
					if (i == 1){
						info.setJobNumber(value);
					}
					else if (i == 2){
						info.setJobNumberFR(value);
					}	
					break;
				case 5:
					info.setName(value);
					break;
				case 6:
					info.setTitle(value);
					break;
				case 7:
					if (i == 1){
						info.setFormOfAddress(value);
					}
					else if (i == 2){
						info.setTitleFR(value);
					}
					break;
				case 8:
					info.setAddress1(value);
					break;
				case 9:
					info.setAddress2(value);
					break;
				}
				break;
			}
		
		
		
			
		}
		return;
	}
	
	private static void fermeture(){
		System.out.println("Fermeture du programme.");
		scan.close();
		System.exit(0);
	}
	
	private static String cap(String jobTitle){
		System.out.println(jobTitle);
		String ret = "";
		jobTitle = jobTitle.toLowerCase();
		String[] array = jobTitle.split("\\s+");
		String answer = "";
		for(int i = 0; i < array.length; ++i){
			System.out.println("Ajoute-t-on une majuscule à: " + array[i] + " ?\n1. Yes\n2. No\n");
			answer = (scan.nextLine());
			if (menuInputCheck(answer, 2, 1) == 1){
				array[i] = WordUtils.capitalize(array[i]);
			}
			ret += array[i] +" ";
		}
		return ret;
	}
	
	private static int menuInputCheck(String input, int higherBoundary, int lowerBoundary) {
		// We store the input argument as a temporary string since we may have to modify this string in the while loop.
		String temp = input;
		// We always enter the loop.
		while (true) {
			/*
			 * Condition check: Has the string 1 char? If no, the if statement resolves. If yes, we keep evaluating.
			 * When the string has only 1 char, is that number 0 (which wouldn't make sense in a menu choice). If it is,
			 * the if statement resolves. If it isn't, we keep evaluating. Is that number greater then the number of
			 * choices in the menu? If it is, the if statement resolves. If it isn't we go to the else statement.
			 */
			if (!temp.matches("^[0-9]+$") || Integer.parseInt(temp) < lowerBoundary || Integer.parseInt(temp) > higherBoundary) {
				System.out.print("\nInvalid option. Please enter a valid choice (from 1 to " + higherBoundary + ") > ");
				temp = scan.nextLine();
			} else {
				// If everything is ok, we break the loop.
				break;
			}
		}
		// Here we return an int.
		
		return Integer.parseInt(temp);

	}
	
	private static void export(String document, int langue){
		String head = "";
		String object = "";
		String date = "";
		String path = "";
		String salutation = "";
		if (langue == 1){
			date = info.getDate();
			head = info.getName() + "\n" + info.getTitle() +
					"\n" + info.getCompany() + "\n" + info.getAddress1() + "\n" + info.getAddress2() + "\n";
			object = "Re: " + info.getJobTitle() + " " + info.getJobNumber() + "\n" ;
			salutation = "Dear " + info.getFormOfAddress() + " " + info.getLastName() + "," + "\n";

		}
		else{
			date = info.getDateFr();
			head = info.getName() + "\n" + info.getTitle() +
					"\n" + info.getCompany() + "\n" + info.getAddress1() + "\n" + info.getAddress2() + "\n";
			object = "Sujet: " + info.getJobTitle() + " " + info.getJobNumber() + "\n";
			salutation = info.getTitleFR() + " " + info.getLastName() + "," + "\n";
		}
						
		
		try {
			String dirName = info.getDateExport();
			new File(export + dirName).mkdirs();
			FileOutputStream out = new FileOutputStream(new File(export + dirName + "\\" + info.getNumber() + ".docx"));
			path = export + dirName + "\\" + info.getNumber();
			XWPFDocument doc = new XWPFDocument(OPCPackage.open((pathDocx + nameDocx)));
			String[] array = {"<DATE>", "<HEAD>", "<SUBJECT>", "<SALUTATION>", "<BODY>"};
			String[] toReplace = {date, head, object, salutation, document};
			for (int i = 0; i < array.length; ++i){
				TextReplacer replacer = new TextReplacer(array[i], toReplace[i]);
				replacer.replace(doc);
			}
			doc.write(out);
			out.flush();
			out.close();
			
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 createPDF (path);
		
	}
	
	private static void createPDF(String path) {
        try {
        	
        	
    		
        	
        
        	Docx4jProperties.getProperties().setProperty("docx4j.Log4j.Configurator.disabled", "true");
        	Log4jConfigurator.configure();            
        	org.docx4j.convert.out.pdf.viaXSLFO.Conversion.log.setLevel(Level.OFF);
 
            // 1) Load DOCX into WordprocessingMLPackage
            FileInputStream is = new FileInputStream(new File(
                    path + ".docx"));
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(is);
 
            // 2) Prepare Pdf settings
            
            PdfSettings pdfSettings = new PdfSettings();
 
            // 3) Convert WordprocessingMLPackage to Pdf
            FileOutputStream newPdf = new FileOutputStream(new File(
                    path + ".pdf"));
            PdfConversion converter = new org.docx4j.convert.out.pdf.viaXSLFO.Conversion(wordMLPackage);
            converter.output(newPdf, pdfSettings);
 		
 
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
	

}

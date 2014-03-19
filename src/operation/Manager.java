package operation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Manager {

	static String sibPath;
	static PrintWriter pw;
	static ArrayList<Operation> currentOperations = new ArrayList<Operation>();

	public static void dispatch(String line) throws IOException {
		if (line.startsWith("Sib")) {
			String tokens[] = line.split(" ");
			if (tokens[1].equals("create") == true) {
				sibPath = tokens[2];
				pw = new PrintWriter(new File(sibPath + ".sib"));
				File dir = new File(sibPath);
				dir.mkdir();
			} else if (tokens[1].equals("append") == true) {
				sibPath = tokens[2];
				pw = new PrintWriter(new FileWriter(sibPath + ".sib", true));
			} else if (tokens[1].equals("close") == true) {
				pw.close();
			}
			currentOperations.clear();
		} else if (line.startsWith("GetText")) {
			GetText op = new GetText(sibPath, line);
			currentOperations.add(op);
			op.decompress();
		} else if (line.startsWith("GetImage")) {
			GetImage op = new GetImage(sibPath, line);
			currentOperations.add(op);
			op.decompress();
		} else if (line.startsWith("clear")) {
			currentOperations.clear();
		}
		else if (line.startsWith("compress")){
			for (Operation op : currentOperations) {
				pw.println(op.compress());
			}
		}
		else if (line.startsWith("decompress")){
			for (Operation op : currentOperations) {
				op.decompress();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		Scanner reader = new Scanner(System.in);
		System.out.print(">> ");
		String line = reader.nextLine();
		while (line.equals("exit") == false) {
			dispatch(line);
			System.out.print(">> ");
			line = reader.nextLine();
		}
		reader.close();
	}

}

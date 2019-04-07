package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import lexicalAnalyzer.HandlingTokens;
import lexicalAnalyzer.LexicalAnalyzer;
import lexicalAnalyzer.finalTokens;

public class Assignment {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method
		String inputFile = "input/Test05.txt";
		String outputFile = "output/output.txt";
		ArrayList<finalTokens> res = new ArrayList<finalTokens>();
		try {
			LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(inputFile);
			HandlingTokens handling= new HandlingTokens(lexicalAnalyzer.getTokens());
			handling.handerTokens();
			res=handling.getResult();
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
			for(int i = 0;i < res.size();i ++) {
				writer.write(res.get(i).toString());
				writer.newLine();
			}
			writer.close();
			
		} catch (IOException e) {
			System.out.println("File not found or do not exist");
			e.printStackTrace();
		}
	}
}

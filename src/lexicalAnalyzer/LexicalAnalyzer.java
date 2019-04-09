package lexicalAnalyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.ArrayList;

public class LexicalAnalyzer {
	private BufferedReader reader;
	private ArrayList<getTokens> tokens = new ArrayList<getTokens>();
	
	public LexicalAnalyzer(String input) throws IOException {
		this.reader = new BufferedReader(new FileReader(input));
		StreamTokenizer st = new StreamTokenizer(reader);
		//TODO set some special character is token
		st.ordinaryChar('\'');
		st.ordinaryChar(' ');
		st.ordinaryChar('.');
		st.ordinaryChar('*');
		st.ordinaryChar('\t');
		//Get token from file
		while(st.nextToken() != StreamTokenizer.TT_EOF) {
			int type =st.ttype;
			switch (type) {
				case StreamTokenizer.TT_WORD:
					tokens.add(new getTokens(st.sval,st.lineno()));
					break;
				case StreamTokenizer.TT_NUMBER:
					tokens.add(new getTokens(String.valueOf((int) st.nval),st.lineno()));
					break;
				default:{
					if(Character.toString((char) type).contains("\t")) {
						tokens.add(new getTokens("    ",st.lineno()));
						break;
					}
					tokens.add(new getTokens(Character.toString((char) type),st.lineno()));
					break;
				}
			}
		}
		this.reader.close();
	}
	//Getter
	public ArrayList<getTokens> getTokens() {
		return tokens;
	}
}

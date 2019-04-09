package lexicalAnalyzer;

import java.util.ArrayList;
import java.util.Hashtable;

public class HandlingTokens {
	private static Hashtable <String, String> key= new Hashtable<String,String>();//tokens use to identifier
	private static ArrayList<getTokens> tokens = new ArrayList<getTokens>();//tokens get from input file
	private ArrayList<finalTokens> result = new ArrayList<finalTokens>();//result of handling all of tokens
	static String doubleToken="";
	public HandlingTokens(ArrayList<getTokens> tokens) {
		HandlingTokens.tokens=tokens;
	}
	public void handerTokens() {
		hashTable();
		exitFOR: 
			for(int i = 0; i < tokens.size() ; i ++) {
			if(i > 0 && i<tokens.size()-1) {
				doubleToken = tokens.get(i).getTokenName()+tokens.get(i+1).getTokenName();				
				if(tokens.get(i-1).getTokenName().equals("'")) {
					StringBuilder getString= new StringBuilder();
					do {
						getString.append(tokens.get(i).getTokenName());
						i++;
					}while (!tokens.get(i).getTokenName().equals("'") && i<tokens.size()-1);
					if(getString.length()>1) {
						result.add(new finalTokens(getString.toString(),"SCONSTnumber", tokens.get(i).getLine(), getColumns(i, tokens.get(i).getLine(),false)));
					}
					else {
						result.add(new finalTokens(getString.toString(),"CCONSTnumber", tokens.get(i).getLine(), getColumns(i, tokens.get(i).getLine(),false)));
					}
					i++;
					continue;  
				}
			}
			//analyzis error comment
			if(doubleToken.equals("\\\\")) {
				i++;
				int stateLine=tokens.get(i-1).getLine();
				do {
					i++;
				}while(tokens.get(i).getLine()==stateLine);
				continue;
			}
			
			if(doubleToken.equalsIgnoreCase("(*") && i<tokens.size()) {
				StringBuilder getCmt = new StringBuilder();
				i+=2;
				boolean stop = false;
				int startCol=tokens.get(i).getLine();
				getcmt: do {
					doubleToken=tokens.get(i).getTokenName()+tokens.get(i+1).getTokenName();
					getCmt.append(tokens.get(i).getTokenName());
					i++;
					
					if(tokens.get(i).getTokenName().equals("EOF")) {
						System.out.println("Missing block comment");
						stop=true;
						break getcmt;
					}
				}while(!doubleToken.equals("*)") && i<tokens.size()-1);
				if(stop==false) {
					result.add(new finalTokens(getCmt.deleteCharAt(getCmt.length()-1).toString(),"COMMENTnumber", tokens.get(i).getLine(), getColumns(startCol, tokens.get(i).getLine(),false)));
				}
				continue;
			}
			if(tokens.get(i).getTokenName().equals("'")) {
				continue;
			}
			if(tokens.get(i).getTokenName().equals(" ")) {
				continue;
			}
			if(tokens.get(i).getTokenName().equals("    ")) {
				continue;
			}
			if(doubleToken.equals(":=")) {
				result.add(new finalTokens(":=","COLEQnumber", tokens.get(i).getLine(), getColumns(i+1, tokens.get(i).getLine(),true)));
				i++;
				continue;
			}
			if (doubleToken.equals("<=")){
				result.add(new finalTokens("<=","LEQnumber", tokens.get(i).getLine(), getColumns(i+1, tokens.get(i).getLine(),true)));
				i++;
				continue;
			}
			if (doubleToken.equals(">=")) {
				result.add(new finalTokens(">=","GEQnumber", tokens.get(i).getLine(), getColumns(i+1, tokens.get(i).getLine(),true)));
				i++;
				continue;
			}
			if (doubleToken.equals("<>")) {
				result.add(new finalTokens("<>","NEQnumber", tokens.get(i).getLine(), getColumns(i+1, tokens.get(i).getLine(),true)));
				i++;
				continue;
			}
			if (doubleToken.equals("..")) {
				result.add(new finalTokens("..","DOTDOTnumber", tokens.get(i).getLine(), getColumns(i+1, tokens.get(i).getLine(),true)));
				i++;
				continue;
			}
			if (doubleToken.equals("==")) {
				result.add(new finalTokens("==", "EQnumber", tokens.get(i).getLine(), getColumns(i+1, tokens.get(i).getLine(),true)));
				i++;
				continue;
			}
			if(isNumberic(tokens.get(i).getTokenName())){
				result.add(new finalTokens(tokens.get(i).getTokenName(),"ICONSTnumber", tokens.get(i).getLine(), getColumns(i, tokens.get(i).getLine(),false)));
				i++;
				continue;
			}
			else {
				boolean check=false;
					if(key.get(tokens.get(i).getTokenName()) != null) {
						result.add(new finalTokens( tokens.get(i).getTokenName(), key.get(tokens.get(i).getTokenName()), tokens.get(i).getLine(), getColumns(i, tokens.get(i).getLine(),false)));
						check=true;
					}				
				if(!check) {
					if(tokens.get(i).getTokenName().equals("=")||tokens.get(i).getTokenName().equals(":")) {
						result.add(new finalTokens(tokens.get(i).getTokenName(),"ERROR",tokens.get(i).getLine(),getColumns(i, tokens.get(i).getLine(),false)));
						break exitFOR;
					}
					result.add(new finalTokens(tokens.get(i).getTokenName(), "IDnumber",tokens.get(i).getLine(),getColumns(i, tokens.get(i).getLine(),false)));
				}
			}
		}
	}
	//Check token is numberic	
		public static boolean isNumberic(String token) {
			try {
				Double.parseDouble(token);
			}
			catch(NumberFormatException e){
				return false;
			}
			return true;
		}
	//Get column of line
		public static getColumn getColumns(int size, int line, boolean check) {
			int start=0;
			for(int i=0; i<size;i++) {
				if(tokens.get(i).getLine()==line) {
					start+=tokens.get(i).getTokenName().length();
				}
			}
			if(start==0) {
				return new getColumn(1, start+tokens.get(size).getTokenName().length());
			}else if(check) {
				return new getColumn(start, start+tokens.get(size).getTokenName().length());
			}
			return new getColumn(start+1, start+tokens.get(size).getTokenName().length());
		}
		
	//Getter
	public ArrayList<finalTokens> getResult() {
		return result;
	}
	public static void hashTable() {
		key.put(";","SEMInumber");
		key.put(":","COLONnumber");
		key.put(",","COMMAnumber");
		key.put(".","DOTnumber");
		key.put("(","LPARENnumber");
		key.put(")","RPARENnumber");
		key.put("<","LTnumber");
		key.put(">","GTnumber");
		key.put("-","MINUSnumber");
		key.put("+","PLUSnumber");
		key.put("*","TIMESnumber");
		key.put("and","ANDnumber");
		key.put("array","ARRAYnumber");
		key.put("begin","BEGINnumber");
		key.put("constant","CONSTnumber");
		key.put("div","DIVnumber");
		key.put("downto","DOWNTOnumber");
		key.put("else","ELSEnumber");
		key.put("elsif","ELSIFnumber");
		key.put("end","ENDnumber");
		key.put("endif","ENDIFnumber");
		key.put("endloop","ENDLOOPnumber");
		key.put("endrec","ENDRECnumber");
		key.put("exit","EXITnumber");
		key.put("for","FORnumber");
		key.put("forward","FORWARDnumber");
		key.put("function","FUNCTIONnumber");
		key.put("if","IFnumber");
		key.put("is","ISnumber");
		key.put("loop","LOOPnumber");
		key.put("not","NOTnumber");
		key.put("of","OFnumber");
		key.put("or","ORnumber");
		key.put("procedure","PROCEDUREnumber");
		key.put("program","PROGRAMnumber");
		key.put("record","RECORDnumber");
		key.put("repeat","REPEATnumber");
		key.put("return","RETURNnumber");
		key.put("then","THENnumber");
		key.put("to","TOnumber");
		key.put("type","TYPEnumber");
		key.put("until","UNTILnumber");
		key.put("var","VARnumber");
		key.put("while","WHILEnumber");
	}
}

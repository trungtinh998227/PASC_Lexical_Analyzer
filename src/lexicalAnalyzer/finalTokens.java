package lexicalAnalyzer;

public class finalTokens {
	private String tokenName;
	private String symbolicName;
	private int line;
	private getColumn column;
	
	public finalTokens(String tokenName, String symbolicName, int line, getColumn column) {
		this.tokenName=tokenName;
		this.symbolicName=symbolicName;
		this.line=line;
		this.column=column;
	}
	public String toString() {
		return tokenName+" "+symbolicName+" "+line+" "+line+" "+column.getStart()+" "+column.getEnd()+" ";
	}
}

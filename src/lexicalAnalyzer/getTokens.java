package lexicalAnalyzer;

public class getTokens {
	private String tokenName;
	private int line;
	
	public getTokens(String tokenName, int line) {
		this.tokenName=tokenName;
		this.line=line;
	}
	
	public String getTokenName() {
		return tokenName;
	}
	public int getLine() {
		return line;
	}
}

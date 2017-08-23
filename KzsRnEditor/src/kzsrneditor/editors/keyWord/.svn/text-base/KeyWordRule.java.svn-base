package kzsrneditor.editors.keyWord;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.IWordDetector;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WordRule;

//import com.test.SQLPartitionScanner;  

public class KeyWordRule extends WordRule implements IPredicateRule {

	private StringBuffer fBuffer = new StringBuffer();
	private boolean fIgnoreCase = false;
	/** 关键字 **/
	public String keywords = "insert,update,delete,select";

	public KeyWordRule(IWordDetector detector, IToken defaultToken) {
		// super(detector, new Token(SQLPartitionScanner.textAttr));
		super(detector, new Token(0));
		// 增加关键字
		String[] keywordArray = keywords.split(",");
		for (int i = 0; i < keywordArray.length; i++) {
			String keywrod = keywordArray[i];
			addWord(keywrod, defaultToken);
		}
	}

	public IToken evaluate(ICharacterScanner scanner) {
		int c = scanner.read();
		if (c != ICharacterScanner.EOF && fDetector.isWordStart((char) c)) {
			if (fColumn == UNDEFINED || (fColumn == scanner.getColumn() - 1)) {

				fBuffer.setLength(0);
				do {
					fBuffer.append((char) c);
					c = scanner.read();
				} while (c != ICharacterScanner.EOF && fDetector.isWordPart((char) c));
				scanner.unread();

				String buffer = fBuffer.toString();

				if (fIgnoreCase) {
					buffer = buffer.toLowerCase();
				}
				IToken token = (IToken) fWords.get(buffer);

				if (token != null) {
					return token;
				}

				if (fDefaultToken.isUndefined()) {
					unreadBuffer(scanner);
				}
				return fDefaultToken;
			}
		}
		scanner.unread();
		return Token.UNDEFINED;
	}

	@Override
	public IToken getSuccessToken() {
		return Token.UNDEFINED;
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner, boolean resume) {
		return this.fDefaultToken;
	}

}
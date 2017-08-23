package kzsrneditor.editors.keyWord;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import kzsrneditor.editors.keyWord.Contents;;

//import com.zdk.platform.studio.dbassistant.codeassistent.Contents;

/**
 * 类说明: 关键字提示数据类.
 * 
 * @author Administrator
 * 
 */
public class KeywordAssistentData implements IAssistentContent {

	private int start = 0;

	@Override
	public List<CompletionProposal> getAssistentData(IDocument doc, int offset) {

		String str = getFrontText(doc, offset);
		List<CompletionProposal> list = new ArrayList<CompletionProposal>();
		// 用正则匹配
		Pattern p = null;
		Matcher m = null;
		/*for (int i = 0; i < Contents.KEY_WORD_ARRAY.length; i++) {
			String keyWord = Contents.KEY_WORD_ARRAY[i];
			p = Pattern.compile("(^" + str + ")", Pattern.CASE_INSENSITIVE);
			m = p.matcher(keyWord);
			if (m.find()) {
				String insert = Contents.KEY_WORD_ARRAY[i];
				// 创建替换类容. insert:替换文本, offset:替换其实位置.
				// 0:替换结束位置.偏移量, insert.length:替换完成后.光标位置偏移量.
				CompletionProposal proposal = new CompletionProposal(insert, start, offset - start, insert.length());
				list.add(proposal);
			}
		}*/
		return list;
	}

	/**
	 * 方法说明: 获取提示前面的字符串.
	 * 
	 * @param doc
	 * @param offest
	 * @return
	 */
	public String getFrontText(IDocument doc, int offest) {
		StringBuffer buf = new StringBuffer();
		while (true) { // 循环添加关键字.
			try {
				char c = doc.getChar(--offest);
				start = offest;
				if (Character.isWhitespace(c)) {
					start++;
					break;
				}
				if (c == ';' || c == '(' || c == ')' || c == '{' || c == '}' || c == ',') { // 结束符号.
					start++;
					break;
				}
				buf.append(c);
			} catch (BadLocationException e) {
				break;
			}
		}
		return buf.reverse().toString();
	}
}
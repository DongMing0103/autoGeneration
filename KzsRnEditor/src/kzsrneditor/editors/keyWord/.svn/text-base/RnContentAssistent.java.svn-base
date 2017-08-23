package kzsrneditor.editors.keyWord;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

//import com.zdk.platform.studio.pojo.DBConfig;  

public class RnContentAssistent implements IContentAssistProcessor {

	/**
	 * 提示集合
	 */
	public List<IAssistentContent> assistentContentList = new ArrayList<IAssistentContent>();

	/**
	 * 开始位置
	 */
	public int startIndex = 0;
	/**
	 * 当前文档
	 */
	public IDocument doc;
	/**
	 * 数据连接对象
	 */
	// private DBConfig dbConfig;

	/**
	 * 构造方法: 添加提示种类.
	 */
	public RnContentAssistent() {
		super();
		assistentContentList.add(new KeywordAssistentData());
	}

	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		this.doc = viewer.getDocument();
		List list = new KeywordAssistentData().getAssistentData(this.doc, offset);
		return (CompletionProposal[]) list.toArray(new CompletionProposal[list.size()]);
	}

	@Override
	public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {
		// TODO 自动生成的方法存根
		return null;
	}

	/**
	 * 设置何时激活
	 */
	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		return new char[] { '.' };
	}

	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		return null;
	}

	@Override
	public String getErrorMessage() {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public IContextInformationValidator getContextInformationValidator() {
		// TODO 自动生成的方法存根
		return null;
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
				startIndex = offest;
				if (Character.isWhitespace(c)) {
					startIndex++;
					break;
				}
				if (c == ';' || c == '(' || c == ')' || c == '{' || c == '}' || c == ',') { // 结束符号.
					startIndex++;
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

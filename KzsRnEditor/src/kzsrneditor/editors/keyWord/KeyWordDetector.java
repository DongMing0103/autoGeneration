package kzsrneditor.editors.keyWord;

/******************************************************************************* 
 * Copyright (c) 2000 2005 IBM Corporation and others. 
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0 
 * which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 *     IBM Corporation - initial API and implementation 
 *     QNX Software System 
 *******************************************************************************/

import org.eclipse.jface.text.rules.IWordDetector;

/**
 * A C aware word detector.
 */
public class KeyWordDetector implements IWordDetector {

	/**
	 * @see IWordDetector#isWordIdentifierStart
	 */
	public boolean isWordStart(char c) {
		if (64 < c && c < 123 || c == '=') { // 大写字母A-Z, 小写字母a-z;
			return true;
		}
		return false;
	}

	/**
	 * @see IWordDetector#isWordIdentifierPart
	 */
	public boolean isWordPart(char c) {
		if (64 < c && c < 123 || c == '=') { // 大写字母A-Z, 小写字母a-z;
			return true;
		}
		return false;
	}
}

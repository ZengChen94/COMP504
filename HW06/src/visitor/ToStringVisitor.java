package visitor;

import provided.music.APhraseVisitor;
import provided.music.IPhrase;
import provided.music.IPhraseVisitorCmd;
import provided.music.NESeqList;

/**
 * The ToString visitor extends the APhraseVisitor
 * It implements the algorithm to transfer music into text for different hosts.
 *
 * @author Yuchang Shen
 * @author Chen Zeng
 * @version 1.0
 *
 */
public class ToStringVisitor extends APhraseVisitor {
	// reference: https://www.clear.rice.edu/comp504/f17/lectures/lec19/
	/**
	 * Constructor
	 */
	public ToStringVisitor() {
		/*
		 * The cmd for Non-empty list.
		 */
		addCmd("NESeqList", new IPhraseVisitorCmd() {
			@Override
			public Object apply(String id, IPhrase host, Object... params) {
				return ((NESeqList) host).getRest().execute(ToStringVisitor.this,
						params[0] + ", " + ((NESeqList) host).getFirst());

			}
		});

		/*
		 * The cmd for empty list.
		 */
		addCmd("MTSeqList", new IPhraseVisitorCmd() {
			@Override
			public Object apply(String id, IPhrase host, Object... params) {
				return params[0] + "}";
			}
		});

	}
}

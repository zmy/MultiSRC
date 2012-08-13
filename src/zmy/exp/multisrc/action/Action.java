package zmy.exp.multisrc.action;

public class Action {
	// How many sentences in each paragraph?
	final int SENTENCES_PER_PARAGRAPH = 20;

	// How many paragraphs in each article?
	final int PARAGRAPHS_PER_ARTICLE = 5;

	// Headline and body
	String mHeadline, mBody;

	/**
	 * Create a news article with randomly generated text.
	 * @param ngen the nonsense generator to use.
	 */
	public Action(NonsenseGenerator ngen) {
		mHeadline = ngen.makeHeadline();

		StringBuilder sb = new StringBuilder();
		sb.append("<html><body>");
		sb.append("<h1>" + mHeadline + "</h1>");
		int i;
		for (i = 0; i < PARAGRAPHS_PER_ARTICLE; i++) {
			sb.append("<p>").append(ngen.makeText(SENTENCES_PER_PARAGRAPH)).append("</p>");
		}

		sb.append("</body></html>");
		mBody = sb.toString();
	}

	/** Returns the headline. */
	public String getHeadline() {
		return mHeadline;
	}

	/** Returns the article body (HTML)*/
	public String getBody() {
		return mBody;
	}
}

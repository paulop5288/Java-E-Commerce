package search;

public class SearchResult {
	private String title = "", authorName = "", articleAbstract = "", articlePath = "", 
	OtherAuthors = "", authorEmail = "", keywords = "";
	
	public SearchResult(String title, String authorName, String articleAbstract, String articlePath, 
			String OtherAuthors, String authorEmail, String keywords) {
		this.title = title;
		this.authorName = authorName;
		this.articleAbstract = articleAbstract;
		this.articlePath = articlePath;
		this.OtherAuthors = OtherAuthors;
		this.authorEmail = authorEmail;
		this.keywords = keywords;
	}
	
	public String getArticleAbstract() {
		return articleAbstract;
	}
	public String getArticlePath() {
		return articlePath;
	}
	public String getAuthorEmail() {
		return authorEmail;
	}
	public String getAuthorName() {
		return authorName;
	}
	public String getKeywords() {
		return keywords;
	}
	public String getOtherAuthors() {
		return OtherAuthors;
	}
	public String getTitle() {
		return title;
	}

}

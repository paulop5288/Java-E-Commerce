package search;

public class SearchResult {
	private String title = "", authorName = "";
	private int articleID = 0;
	
	public SearchResult(int articleID, String title, String authorName) {
		this.title = title;
		this.authorName = authorName;
		this.articleID = articleID;
	}
	

	public String getAuthorName() {
		return authorName;
	}
	public String getTitle() {
		return title;
	}
	public int getArticleID() {
		return articleID;
	}

}

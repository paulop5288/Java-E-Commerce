package review;

public class Article {
	
	private int articleID = 0;
	private int authorID = 0;
	private String title = "";
	private String articleAbstract = "";

	public Article(int articleID, int authorID, String title, String articleAbstract) {
		this.articleID = articleID;
		this.title = title;
		this.authorID = authorID;
		this.articleAbstract = articleAbstract;
		
	}
	
	public String getArticleAbstract() {
		return articleAbstract;
	}
	
	public String getArticleIDString() {
		return String.valueOf(articleID);
	}
	
	public String getAuthorIDString() {
		return String.valueOf(authorID);
	}
	
	
	public String getTitle() {
		return title;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Article " + articleID + " is written by author" + authorID + ".Named as " + title + "\n";
	}

}

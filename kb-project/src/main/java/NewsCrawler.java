
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NewsCrawler {
	public static void main(String[] args) {
		try {
			String url = "https://m.yna.co.kr/economy/finance"; // 크롤링할 뉴스 웹 페이지 URL
			Document doc = Jsoup.connect(url).get();

			// 뉴스 기사 요소 선택
			Elements newsElements = doc.select(".news-article");

			for (Element element : newsElements) {
				// 뉴스 기사 제목 추출
				String title = element.select(".news-title").text();

				// 뉴스 기사 내용 추출
				String content = element.select(".news-content").text();

				// 뉴스 작성일 추출
				String date = element.select(".news-date").text();

				// 추출한 데이터 출력 또는 다른 작업 수행
				System.out.println("Title: " + title);
				System.out.println("Content: " + content);
				System.out.println("Date: " + date);
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

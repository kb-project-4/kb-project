package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.Login;
import com.example.demo.dto.NewsDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.News;
import com.example.demo.entity.User;
import com.example.demo.repository.NewsRepository;
import com.example.demo.repository.UserRepository;

@Service
public class NewsService {

	private final NewsRepository newsRepository;

	@Autowired
	public NewsService(NewsRepository newsRepository) {
		this.newsRepository = newsRepository;
	}

	public List<News> getallnews() {

		System.out.println("newsservice getallnews");
		return newsRepository.findAll();
	}

	public void savenews() {
 
		try {
			// 크롤링할 사이트 URL 설정
			String url = "https://www.yna.co.kr/economy/finance?site=navi_economy_depth02";

			// Jsoup을 사용하여 웹 페이지 파싱
			Document doc = Jsoup.connect(url).get();

			// 원하는 데이터 추출
			Elements titWraps = doc.select(".item-box01 .news-con a.tit-wrap"); // ".tit-wrap" 클래스 선택

			// 추출한 데이터 출력 (최대 10개까지)
			int count = 0;
			for (Element titWrap : titWraps) {
				if (count >= 10) {
					break; // 10개까지만 가져오기
				}

				String href = titWrap.attr("href"); // href 속성 값 가져오기
				String newsLink = "https:" + href; // 완전한 URL 생성

				// 뉴스 링크를 다시 크롤링하여 제목, 기사 내용, 날짜 추출
				Document newsDoc = Jsoup.connect(newsLink).get();

				// 제목 추출
				String title = newsDoc.selectFirst(".title-article01 .tit").text();

				// 기사 내용 추출
				StringBuilder contentBuilder = new StringBuilder();
				Elements paragraphs = newsDoc.select(".story-news.article p"); // 모든 p 태그 선택
				for (int i = 0; i < paragraphs.size() - 2; i++) { // 마지막 2개 줄 제외
					Element paragraph = paragraphs.get(i);
					contentBuilder.append(paragraph.text()).append("\n");
				}
				
				String content = contentBuilder.toString();

				// 날짜 추출
				String date = newsDoc.selectFirst(".update-time").text();

				// 결과 출력
				System.out.println("제목: " + title);
				System.out.println("기사 내용: " + content);
				System.out.println("날짜: " + date);
				System.out.println("------------------------------------");
				News news = new News(title, content, date);

				newsRepository.save(news);
				count++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

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
			String url = "https://m.yna.co.kr/economy/finance"; // 크롤링할 뉴스 웹 페이지 URL
			Document doc = Jsoup.connect(url).get();
//			System.out.println("doc" + doc);
			System.out.println("doc" + doc.title());

			// 뉴스 기사 요소 선택
			Elements newsElements = doc.select(".item-box01");
			System.out.println("savenews");
			System.out.println("newsElements " + newsElements);

			for (Element element : newsElements) {
				// 뉴스 기사 제목 추출
				String title = element.select(".tit-news").text();

				// 뉴스 기사 내용 추출
				String content = element.select(".news-content").text();

				// 뉴스 작성일 추출
				String date = element.select(".txt-time").text();
				System.out.println("title" + title);
				System.out.println("content" + content);

				// Create a new News object
				News news = new News(title, content, date);

				// Save the news object to the repository
				newsRepository.save(news);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

package com.example.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.NewsDto;
import com.example.demo.entity.User;
import com.example.demo.service.NewsService;

@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/news")
    public String getNews(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        List<Map<String, String>> newsUrlCrawler = newsService.newsUrlCrawler();

        model.addAttribute("newsList", newsUrlCrawler);

        if (user.isDisabled()) { // 장애인
            return "news/list";
        } else { // 비장애인
            return "news/list";
        }
    }

    @GetMapping("/news/detail/{title}")
    public String getNewsDetail(Model model, HttpServletRequest request, @PathVariable("title") String title)
            throws IOException {
        List<Map<String, String>> newsUrlCrawler = newsService.newsUrlCrawler();

        String newsUrl = null;
        for (Map<String, String> news : newsUrlCrawler) {
            if (title.equals(news.get("title"))) {
                newsUrl = news.get("url");
                break;
            }
        }

        if (newsUrl != null) {
            NewsDto newsDto = newsService.newsDetailsCrawler(newsUrl);
            model.addAttribute("news", newsDto);
        } else {
            model.addAttribute("msg", "error");
        }

        return "/news/newsDetail";
    }

}

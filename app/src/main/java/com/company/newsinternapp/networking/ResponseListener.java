package com.company.newsinternapp.networking;

import com.company.newsinternapp.modals.NewsHeadlines;

import java.util.List;

public interface ResponseListener<NewsApiResponse> {
    void getData(List<NewsHeadlines> list , String message);
    void onError(String message);
}

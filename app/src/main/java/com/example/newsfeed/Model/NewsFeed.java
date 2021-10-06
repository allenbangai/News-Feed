package com.example.newsfeed.Model;

public class NewsFeed {
    private final String sectionName;
    private final String webPublicationDate;
    private final String webTitle;
    private final String webUrl;
    private final String firstName;
    private final String lastName;

    public NewsFeed(String sectionName, String webPublicationDate, String webTitle, String webUrl, String firstName, String lastName) {
        this.sectionName = sectionName;
        this.webPublicationDate = webPublicationDate;
        this.webTitle = webTitle;
        this.webUrl = webUrl;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}

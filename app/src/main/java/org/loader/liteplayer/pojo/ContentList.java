package org.loader.liteplayer.pojo;

import java.util.List;

/**
 * @author husyin
 * @date 2018/1/15
 */

public class ContentList {
    private List<ContentItem> contentlist;

    public List<ContentItem> getContentlist() {
        return contentlist;
    }

    public void setContentlist(List<ContentItem> contentlist) {
        this.contentlist = contentlist;
    }

    @Override
    public String toString() {
        return "ContentList{" +
                "contentlist=" + contentlist +
                '}';
    }

    public static class ContentItem{
        private String album_link;
        private String title;
        private String word_url;
        private String link;
        private String singer_name;
        private String album_name;
        private String singer_link;

        public String getAlbum_link() {
            return album_link;
        }

        public void setAlbum_link(String album_link) {
            this.album_link = album_link;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getWord_url() {
            return word_url;
        }

        public void setWord_url(String word_url) {
            this.word_url = word_url;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getSinger_name() {
            return singer_name;
        }

        public void setSinger_name(String singer_name) {
            this.singer_name = singer_name;
        }

        public String getAlbum_name() {
            return album_name;
        }

        public void setAlbum_name(String album_name) {
            this.album_name = album_name;
        }

        public String getSinger_link() {
            return singer_link;
        }

        public void setSinger_link(String singer_link) {
            this.singer_link = singer_link;
        }

        @Override
        public String toString() {
            return "ContentItem{" +
                    "album_link='" + album_link + '\'' +
                    ", title='" + title + '\'' +
                    ", word_url='" + word_url + '\'' +
                    ", link='" + link + '\'' +
                    ", singer_name='" + singer_name + '\'' +
                    ", album_name='" + album_name + '\'' +
                    ", singer_link='" + singer_link + '\'' +
                    '}';
        }
    }
}

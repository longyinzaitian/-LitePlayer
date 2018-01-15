package org.loader.liteplayer.pojo;

import java.util.List;

/**
 * @author husyin
 * @date 2018/1/15
 */

public class MusicList {
    private List<MusicItem> musicList;

    public List<MusicItem> getMusicList() {
        return musicList;
    }

    public void setMusicList(List<MusicItem> musicList) {
        this.musicList = musicList;
    }

    @Override
    public String toString() {
        return "MusicList{" +
                "musicList=" + musicList +
                '}';
    }

    public static class MusicItem{
        private String link;
        private String title;
        private String word_url;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
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

        @Override
        public String toString() {
            return "MusicItem{" +
                    "link='" + link + '\'' +
                    ", title='" + title + '\'' +
                    ", word_url='" + word_url + '\'' +
                    '}';
        }
    }
}

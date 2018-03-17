package org.loader.liteplayer.pojo;

import java.util.List;

/**
 * @author longyinzaitian
 * @date 2018/3/17.
 */
public class Wiki {
    private long wiki_id;
    private String wiki_title;
    private String wiki_title_encode;
    private String wiki_name;
    private String wiki_type;
    private int wiki_parent;
    private long wiki_date;
    private long wiki_modified;
    private long wiki_modified_user;
    private List<WikiMeta> wiki_meta;
    private String wiki_url;
    private String wiki_fm_url;
    private WikiCover wiki_cover;

    public long getWiki_id() {
        return wiki_id;
    }

    public void setWiki_id(long wiki_id) {
        this.wiki_id = wiki_id;
    }

    public String getWiki_title() {
        return wiki_title;
    }

    public void setWiki_title(String wiki_title) {
        this.wiki_title = wiki_title;
    }

    public String getWiki_title_encode() {
        return wiki_title_encode;
    }

    public void setWiki_title_encode(String wiki_title_encode) {
        this.wiki_title_encode = wiki_title_encode;
    }

    public String getWiki_name() {
        return wiki_name;
    }

    public void setWiki_name(String wiki_name) {
        this.wiki_name = wiki_name;
    }

    public String getWiki_type() {
        return wiki_type;
    }

    public void setWiki_type(String wiki_type) {
        this.wiki_type = wiki_type;
    }

    public int getWiki_parent() {
        return wiki_parent;
    }

    public void setWiki_parent(int wiki_parent) {
        this.wiki_parent = wiki_parent;
    }

    public long getWiki_date() {
        return wiki_date;
    }

    public void setWiki_date(long wiki_date) {
        this.wiki_date = wiki_date;
    }

    public long getWiki_modified() {
        return wiki_modified;
    }

    public void setWiki_modified(long wiki_modified) {
        this.wiki_modified = wiki_modified;
    }

    public long getWiki_modified_user() {
        return wiki_modified_user;
    }

    public void setWiki_modified_user(long wiki_modified_user) {
        this.wiki_modified_user = wiki_modified_user;
    }

    public List<WikiMeta> getWiki_meta() {
        return wiki_meta;
    }

    public void setWiki_meta(List<WikiMeta> wiki_meta) {
        this.wiki_meta = wiki_meta;
    }

    public String getWiki_url() {
        return wiki_url;
    }

    public void setWiki_url(String wiki_url) {
        this.wiki_url = wiki_url;
    }

    public String getWiki_fm_url() {
        return wiki_fm_url;
    }

    public void setWiki_fm_url(String wiki_fm_url) {
        this.wiki_fm_url = wiki_fm_url;
    }

    public WikiCover getWiki_cover() {
        return wiki_cover;
    }

    public void setWiki_cover(WikiCover wiki_cover) {
        this.wiki_cover = wiki_cover;
    }

    public class WikiCover {
        private String small;
        private String medium;
        private String square;
        private String large;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getSquare() {
            return square;
        }

        public void setSquare(String square) {
            this.square = square;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        @Override
        public String toString() {
            return "WikiCover{" +
                    "small='" + small + '\'' +
                    ", medium='" + medium + '\'' +
                    ", square='" + square + '\'' +
                    ", large='" + large + '\'' +
                    '}';
        }
    }

    public class WikiMeta {
        private String meta_key;
        private String meta_value;
        private int meta_type;

        public String getMeta_key() {
            return meta_key;
        }

        public void setMeta_key(String meta_key) {
            this.meta_key = meta_key;
        }

        public String getMeta_value() {
            return meta_value;
        }

        public void setMeta_value(String meta_value) {
            this.meta_value = meta_value;
        }

        public int getMeta_type() {
            return meta_type;
        }

        public void setMeta_type(int meta_type) {
            this.meta_type = meta_type;
        }

        @Override
        public String toString() {
            return "WikiMeta{" +
                    "meta_key='" + meta_key + '\'' +
                    ", meta_value='" + meta_value + '\'' +
                    ", meta_type=" + meta_type +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Wiki{" +
                "wiki_id=" + wiki_id +
                ", wiki_title='" + wiki_title + '\'' +
                ", wiki_title_encode='" + wiki_title_encode + '\'' +
                ", wiki_name='" + wiki_name + '\'' +
                ", wiki_type='" + wiki_type + '\'' +
                ", wiki_parent=" + wiki_parent +
                ", wiki_date=" + wiki_date +
                ", wiki_modified=" + wiki_modified +
                ", wiki_modified_user=" + wiki_modified_user +
                ", wiki_meta=" + wiki_meta +
                ", wiki_url='" + wiki_url + '\'' +
                ", wiki_fm_url='" + wiki_fm_url + '\'' +
                ", wiki_cover=" + wiki_cover +
                '}';
    }
}

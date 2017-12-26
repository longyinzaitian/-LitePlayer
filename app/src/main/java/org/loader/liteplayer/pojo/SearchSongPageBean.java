package org.loader.liteplayer.pojo;

import java.util.List;

/**
 * @author longyinzaitian
 * @date 2017/12/26.
 *
 * 关键词搜索
 */
public class SearchSongPageBean {
    private String w;
    private int allPages;
    private int ret_code;
    private List<SearchSong> contentlist;
    private int currentPage;
    private String notice;
    private int allNum;
    private int maxResult;

    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }

    public int getAllPages() {
        return allPages;
    }

    public void setAllPages(int allPages) {
        this.allPages = allPages;
    }

    public List<SearchSong> getContentlist() {
        return contentlist;
    }

    public void setContentlist(List<SearchSong> contentlist) {
        this.contentlist = contentlist;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    @Override
    public String toString() {
        return "SearchSongPageBean{" +
                "allNum=" + allNum +
                ", allPages=" + allPages +
                ", contentlist=" + contentlist +
                ", currentPage=" + currentPage +
                ", keyword='" + keyword + '\'' +
                ", maxResult=" + maxResult +
                ", ret_code=" + ret_code +
                '}';
    }
}

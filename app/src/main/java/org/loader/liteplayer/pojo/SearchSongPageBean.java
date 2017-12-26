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

    @Override
    public String toString() {
        return "SearchSongPageBean{" +
                "w='" + w + '\'' +
                ", allPages=" + allPages +
                ", ret_code=" + ret_code +
                ", contentlist=" + contentlist +
                ", currentPage=" + currentPage +
                ", notice='" + notice + '\'' +
                ", allNum=" + allNum +
                ", maxResult=" + maxResult +
                '}';
    }

    public String getW() {
        return w;
    }

    public void setW(String w) {
        this.w = w;
    }

    public int getAllPages() {
        return allPages;
    }

    public void setAllPages(int allPages) {
        this.allPages = allPages;
    }

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
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

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }
}

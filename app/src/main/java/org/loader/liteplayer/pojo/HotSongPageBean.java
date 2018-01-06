package org.loader.liteplayer.pojo;

import java.util.List;

/**
 * @author longyinzaitian
 * @date 2017/12/26.
 *
 * 热门榜单
 */
public class HotSongPageBean {
    private List<HotSong> songlist;
    private int total_song_num;
    private String update_time;
    private long color;
    private int cur_song_num;
    private int comment_num;
    private int currentPage;
    private int code;
    private int song_begin;
    private String day_of_year;
    private int totalpage;
    private int ret_code;

    public List<HotSong> getSonglist() {
        return songlist;
    }

    public void setSonglist(List<HotSong> songlist) {
        this.songlist = songlist;
    }

    public int getTotal_song_num() {
        return total_song_num;
    }

    public void setTotal_song_num(int total_song_num) {
        this.total_song_num = total_song_num;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public long getColor() {
        return color;
    }

    public void setColor(long color) {
        this.color = color;
    }

    public int getCur_song_num() {
        return cur_song_num;
    }

    public void setCur_song_num(int cur_song_num) {
        this.cur_song_num = cur_song_num;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getSong_begin() {
        return song_begin;
    }

    public void setSong_begin(int song_begin) {
        this.song_begin = song_begin;
    }

    public String getDay_of_year() {
        return day_of_year;
    }

    public void setDay_of_year(String day_of_year) {
        this.day_of_year = day_of_year;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public int getRet_code() {
        return ret_code;
    }

    public void setRet_code(int ret_code) {
        this.ret_code = ret_code;
    }

    @Override
    public String toString() {
        return "HotSongPageBean{" +
                "songlist=" + songlist +
                ", total_song_num=" + total_song_num +
                ", update_time='" + update_time + '\'' +
                ", color=" + color +
                ", cur_song_num=" + cur_song_num +
                ", comment_num=" + comment_num +
                ", currentPage=" + currentPage +
                ", code=" + code +
                ", song_begin=" + song_begin +
                ", day_of_year='" + day_of_year + '\'' +
                ", totalpage=" + totalpage +
                ", ret_code=" + ret_code +
                '}';
    }
}

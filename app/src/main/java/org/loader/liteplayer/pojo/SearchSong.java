package org.loader.liteplayer.pojo;

/**
 * @author longyinzaitian
 * @date 2017/12/26.
 */
public class SearchSong {
    private String m4a;
    private String media_mid;
    private long songid;
    private int singerid;
    private String albumname;
    private String downUrl;
    private String singername;
    private String songname;
    private String strMediaMid;
    private String albummid;
    private String songmid;
    private String albumpic_big;
    private String albumpic_small;
    private int albumid;

    public String getM4a() {
        return m4a;
    }

    public void setM4a(String m4a) {
        this.m4a = m4a;
    }

    public String getMedia_mid() {
        return media_mid;
    }

    public void setMedia_mid(String media_mid) {
        this.media_mid = media_mid;
    }

    public long getSongid() {
        return songid;
    }

    public void setSongid(long songid) {
        this.songid = songid;
    }

    public int getSingerid() {
        return singerid;
    }

    public void setSingerid(int singerid) {
        this.singerid = singerid;
    }

    public String getAlbumname() {
        return albumname;
    }

    public void setAlbumname(String albumname) {
        this.albumname = albumname;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getSingername() {
        return singername;
    }

    public void setSingername(String singername) {
        this.singername = singername;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public String getStrMediaMid() {
        return strMediaMid;
    }

    public void setStrMediaMid(String strMediaMid) {
        this.strMediaMid = strMediaMid;
    }

    public String getAlbummid() {
        return albummid;
    }

    public void setAlbummid(String albummid) {
        this.albummid = albummid;
    }

    public String getSongmid() {
        return songmid;
    }

    public void setSongmid(String songmid) {
        this.songmid = songmid;
    }

    public String getAlbumpic_big() {
        return albumpic_big;
    }

    public void setAlbumpic_big(String albumpic_big) {
        this.albumpic_big = albumpic_big;
    }

    public String getAlbumpic_small() {
        return albumpic_small;
    }

    public void setAlbumpic_small(String albumpic_small) {
        this.albumpic_small = albumpic_small;
    }

    public int getAlbumid() {
        return albumid;
    }

    public void setAlbumid(int albumid) {
        this.albumid = albumid;
    }

    @Override
    public String toString() {
        return "SearchSong{" +
                "m4a='" + m4a + '\'' +
                ", media_mid='" + media_mid + '\'' +
                ", songid=" + songid +
                ", singerid=" + singerid +
                ", albumname='" + albumname + '\'' +
                ", downUrl='" + downUrl + '\'' +
                ", singername='" + singername + '\'' +
                ", songname='" + songname + '\'' +
                ", strMediaMid='" + strMediaMid + '\'' +
                ", albummid='" + albummid + '\'' +
                ", songmid='" + songmid + '\'' +
                ", albumpic_big='" + albumpic_big + '\'' +
                ", albumpic_small='" + albumpic_small + '\'' +
                ", albumid=" + albumid +
                '}';
    }
}
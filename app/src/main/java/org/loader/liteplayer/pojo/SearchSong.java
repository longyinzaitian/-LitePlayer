package org.loader.liteplayer.pojo;

/**
 * @author longyinzaitian
 * @date 2017/12/26.
 */
public class SearchSong {
    private String albumid;
    private String albumname;
    private String albumpic_big;
    private String albumpic_small;
    private String downUrl;
    private String m4a;
    private String singerid;
    private String singername;
    private long songid;
    private String songname;

    public String getAlbumid() {
        return albumid;
    }

    public void setAlbumid(String albumid) {
        this.albumid = albumid;
    }

    public String getAlbumname() {
        return albumname;
    }

    public void setAlbumname(String albumname) {
        this.albumname = albumname;
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

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getM4a() {
        return m4a;
    }

    public void setM4a(String m4a) {
        this.m4a = m4a;
    }

    public String getSingerid() {
        return singerid;
    }

    public void setSingerid(String singerid) {
        this.singerid = singerid;
    }

    public String getSingername() {
        return singername;
    }

    public void setSingername(String singername) {
        this.singername = singername;
    }

    public long getSongid() {
        return songid;
    }

    public void setSongid(long songid) {
        this.songid = songid;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    @Override
    public String toString() {
        return "SearchSong{" +
                "albumid='" + albumid + '\'' +
                ", albumname='" + albumname + '\'' +
                ", albumpic_big='" + albumpic_big + '\'' +
                ", albumpic_small='" + albumpic_small + '\'' +
                ", downUrl='" + downUrl + '\'' +
                ", m4a='" + m4a + '\'' +
                ", singerid='" + singerid + '\'' +
                ", singername='" + singername + '\'' +
                ", songid=" + songid +
                ", songname='" + songname + '\'' +
                '}';
    }
}

package org.loader.liteplayer.pojo;

/**
 * @author longyinzaitian
 * @date 2017/12/26.
 */
public class HotSong {
    private String albummid;
    private long albumid;
    private String downUrl;
    private long seconds;
    private long singerid;
    private String singername;
    private long songid;
    private String songname;
    private String url;
    private String albumpic_big;
    private String albumpic_small;

    public String getAlbummid() {
        return albummid;
    }

    public void setAlbummid(String albummid) {
        this.albummid = albummid;
    }

    public long getAlbumid() {
        return albumid;
    }

    public void setAlbumid(long albumid) {
        this.albumid = albumid;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public long getSingerid() {
        return singerid;
    }

    public void setSingerid(long singerid) {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    @Override
    public String toString() {
        return "HotSong{" +
                "albummid='" + albummid + '\'' +
                ", albumid=" + albumid +
                ", downUrl='" + downUrl + '\'' +
                ", seconds=" + seconds +
                ", singerid=" + singerid +
                ", singername='" + singername + '\'' +
                ", songid=" + songid +
                ", songname='" + songname + '\'' +
                ", url='" + url + '\'' +
                ", albumpic_big='" + albumpic_big + '\'' +
                ", albumpic_small='" + albumpic_small + '\'' +
                '}';
    }
}

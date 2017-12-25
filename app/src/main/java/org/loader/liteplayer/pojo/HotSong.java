package org.loader.liteplayer.pojo;

/**
 * @author longyinzaitian
 * @date 2017/12/26.
 */
public class HotSong {
    private long albumid;
    private String downUrl;
    private long seconds;
    private long singerid;
    private String singername;
    private long songid;
    private String url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "HotSong{" +
                "albumid=" + albumid +
                ", downUrl='" + downUrl + '\'' +
                ", seconds=" + seconds +
                ", singerid=" + singerid +
                ", singername='" + singername + '\'' +
                ", songid=" + songid +
                ", url='" + url + '\'' +
                '}';
    }
}

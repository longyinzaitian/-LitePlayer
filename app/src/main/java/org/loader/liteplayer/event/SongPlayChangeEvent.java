package org.loader.liteplayer.event;

/**
 * @author longyinzaitian
 * @date 2018/1/8
 */

public class SongPlayChangeEvent implements IEvent {
    private int pos;

    public SongPlayChangeEvent(int pos) {
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}

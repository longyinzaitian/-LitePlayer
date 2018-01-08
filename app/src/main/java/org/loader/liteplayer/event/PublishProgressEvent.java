package org.loader.liteplayer.event;

/**
 * @author longyinzaitian
 * @date 2018/1/8
 */

public class PublishProgressEvent implements IEvent {
    private int progress;

    public PublishProgressEvent(int progress) {
        this.progress = progress;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}

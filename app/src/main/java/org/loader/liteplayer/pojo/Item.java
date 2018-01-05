package org.loader.liteplayer.pojo;

/**
 * @author husyin
 * @date 2018/1/5
 */

public class Item {
    public Item(String title, int id) {
        this.title = title;
        this.id = id;
    }

    private String title;
    private int id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", id=" + id +
                '}';
    }
}

package org.loader.liteplayer.pojo;

import java.util.List;

/**
 * @author longyinzaitian
 * @date 2018/1/15
 */

public class RankList {
    private List<Item> rankList;

    public List<Item> getRankList() {
        return rankList;
    }

    public void setRankList(List<Item> rankList) {
        this.rankList = rankList;
    }

    @Override
    public String toString() {
        return "RankList{" +
                "rankList=" + rankList +
                '}';
    }

    public static class Item{
        private String id;
        private String name;

        public Item(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Item{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}

package com.jiebian.adwlf.bean.returned;

import java.util.List;

/**
 * Created by Administrator on 2015/11/23.
 */
public class Rating {


    /**
     * rank : 暂未入榜
     * earn_money : 1890
     */
    private MyinfoEntity myinfo;

    /**
     * uid : 72
     * username : 累
     * earn_money : 7788
     * relaynum : 28
     */
    private List<ListEntity> list;

    public void setMyinfo(MyinfoEntity myinfo) {
        this.myinfo = myinfo;
    }

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public MyinfoEntity getMyinfo() {
        return myinfo;
    }

    public List<ListEntity> getList() {
        return list;
    }

    public static class MyinfoEntity {
        private String rank;
        private String earn_money;
        private String rank_range;
        private String lastday_rank;
        public String getLastDayRank() {
            return lastday_rank;
        }
        public String getLastRank() {
            return rank_range;
        }
        public void setLastdayRank(String lastrank) {
            this.rank_range = lastrank;
        }
        public void setRank(String rank) {
            this.rank = rank;
        }

        public void setEarn_money(String earn_money) {
            this.earn_money = earn_money;
        }

        public String getRank() {
            return rank;
        }

        public double getEarn_money() {
            if(null==earn_money) {
                return 0;
            }else{
               return Double.valueOf(earn_money) / 100;
            }
        }

        @Override
        public String toString() {
            return "MyinfoEntity{" +
                    "rank='" + rank + '\'' +
                    ", earn_money='" + earn_money + '\'' +
                    '}';
        }
    }

    public static class ListEntity {
        private String uid;
        private String username;
        private String earn_money;
        private String relaynum;

        public void setUid(String uid) {
            this.uid = uid;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setEarn_money(String earn_money) {
            this.earn_money = earn_money;
        }

        public void setRelaynum(String relaynum) {
            this.relaynum = relaynum;
        }

        public String getUid() {
            return uid;
        }

        public String getUsername() {
            return username;
        }

        public double getEarn_money() {
            if(null==earn_money) {
                return 0;
            }else{
              return  Double.valueOf(earn_money) / 100;
            }

        }

        public String getRelaynum() {
            return relaynum;
        }

        @Override
        public String toString() {
            return "ListEntity{" +
                    "uid='" + uid + '\'' +
                    ", username='" + username + '\'' +
                    ", earn_money='" + earn_money + '\'' +
                    ", relaynum='" + relaynum + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Rating{" +
                "myinfo=" + myinfo +
                ", list=" + list +
                '}';
    }
}

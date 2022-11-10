package network.responses;

import model.database.UserDAO;

import java.util.LinkedList;

public class RankingClientResponse extends Response{
    private LinkedList<String[]> rankList;

    public RankingClientResponse(boolean successful, LinkedList<String[]> rankList) {
        super(successful);
        this.rankList = rankList;
    }

    public LinkedList<String[]> getRankList(){return rankList;}

}

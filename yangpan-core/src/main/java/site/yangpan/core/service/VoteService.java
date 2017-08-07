package site.yangpan.core.service;

import site.yangpan.core.domain.Vote;

/**
 * 点赞业务层接口
 * Created by yangpn on 2017-08-06 23:03
 */
public interface VoteService {
    /**
     * 根据id获取 Vote
     *
     * @param id
     * @return
     */
    Vote getVoteById(Long id);

    /**
     * 删除Vote
     *
     * @param id
     * @return
     */
    void removeVote(Long id);
}

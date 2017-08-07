package site.yangpan.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.yangpan.core.domain.Vote;
import site.yangpan.core.repository.VoteRepository;
import site.yangpan.core.service.VoteService;

/**
 * 点赞业务层实现类
 * Created by yangpn on 2017-08-06 23:10
 */
@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository voteRepository;
    
    @Override
    @Transactional
    public void removeVote(Long id) {
        voteRepository.delete(id);
    }

    @Override
    public Vote getVoteById(Long id) {
        return voteRepository.findOne(id);
    }

}


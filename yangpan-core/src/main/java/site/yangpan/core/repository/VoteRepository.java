package site.yangpan.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.yangpan.core.domain.Vote;

/**
 * 点赞持久层接口
 * Created by yangpn on 2017-08-06 22:38
 */
public interface VoteRepository extends JpaRepository<Vote, Long> {

}

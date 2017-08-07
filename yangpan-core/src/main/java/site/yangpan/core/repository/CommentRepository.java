package site.yangpan.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.yangpan.core.domain.Comment;

/**
 * 评论持久层接口
 * Created by yangpn on 2017-08-06 22:40
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

}

package site.yangpan.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.yangpan.core.domain.Comment;
import site.yangpan.core.repository.CommentRepository;
import site.yangpan.core.service.CommentService;

/**
 * 评论业务层实现类
 * Created by yangpn on 2017-08-06 23:08
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    
    @Override
    @Transactional
    public void removeComment(Long id) {
        commentRepository.delete(id);
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findOne(id);
    }

}

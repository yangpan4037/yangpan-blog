package site.yangpan.file.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import site.yangpan.file.domain.File;

/**
 * File 存储库
 * Created by yangpn on 2017-08-06 20:52
 */
public interface FileRepository extends MongoRepository<File, String> {
}

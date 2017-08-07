package site.yangpan.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.yangpan.core.domain.Catalog;
import site.yangpan.core.domain.User;
import site.yangpan.core.repository.CatalogRepository;
import site.yangpan.core.service.CatalogService;

import java.util.List;

/**
 * 分类业务层实现类
 * Created by yangpn on 2017-08-06 23:06
 */
@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CatalogRepository catalogRepository;

    @Override
    public Catalog saveCatalog(Catalog catalog) {
        // 判断重复
        List<Catalog> list = catalogRepository.findByUserAndName(catalog.getUser(), catalog.getName());
        if (list != null && list.size() > 0) {
            throw new IllegalArgumentException("该分类已经存在了");
        }
        return catalogRepository.save(catalog);
    }

    @Override
    public void removeCatalog(Long id) {
        catalogRepository.delete(id);
    }

    @Override
    public Catalog getCatalogById(Long id) {
        return catalogRepository.findOne(id);
    }

    @Override
    public List<Catalog> listCatalogs(User user) {
        return catalogRepository.findByUser(user);
    }

}

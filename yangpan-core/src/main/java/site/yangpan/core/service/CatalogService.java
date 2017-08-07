package site.yangpan.core.service;

import site.yangpan.core.domain.Catalog;
import site.yangpan.core.domain.User;

import java.util.List;

/**
 * 分类业务层接口
 * Created by yangpn on 2017-08-06 23:00
 */
public interface CatalogService {
    /**
     * 保存Catalog
     *
     * @param catalog
     * @return
     */
    Catalog saveCatalog(Catalog catalog);

    /**
     * 删除Catalog
     *
     * @param id
     * @return
     */
    void removeCatalog(Long id);

    /**
     * 根据id获取Catalog
     *
     * @param id
     * @return
     */
    Catalog getCatalogById(Long id);

    /**
     * 获取Catalog列表
     *
     * @return
     */
    List<Catalog> listCatalogs(User user);
}

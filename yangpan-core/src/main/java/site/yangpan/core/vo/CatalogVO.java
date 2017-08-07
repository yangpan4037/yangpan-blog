package site.yangpan.core.vo;


import site.yangpan.core.domain.Catalog;

import java.io.Serializable;

/**
 * Created by yangpn on 2017-08-06 22:03
 */
public class CatalogVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private Catalog catalog;

    public CatalogVO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

}

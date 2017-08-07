package site.yangpan.core.vo;

import java.io.Serializable;

/**
 * Tag 值对象
 * Created by yangpn on 2017-08-06 22:01
 */
public class TagVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private Long count;

    public TagVO(String name, Long count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

}

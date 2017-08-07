package site.yangpan.file.service;

import site.yangpan.file.domain.File;

import java.util.List;

/**
 * File 服务接口
 * Created by yangpn on 2017-08-06 20:53
 */
public interface FileService {
    /**
     * 保存文件
     *
     * @param File
     * @return
     */
    File saveFile(File file);

    /**
     * 删除文件
     *
     * @param File
     * @return
     */
    void removeFile(String id);

    /**
     * 根据id获取文件
     *
     * @param File
     * @return
     */
    File getFileById(String id);

    /**
     * 分页查询，按上传时间降序
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<File> listFilesByPage(int pageIndex, int pageSize);
}

package site.yangpan.file.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import site.yangpan.file.domain.File;
import site.yangpan.file.repository.FileRepository;
import site.yangpan.file.service.FileService;

import java.util.List;

/**
 * File 服务实现类
 * Created by yangpn on 2017-08-06 20:54
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    public FileRepository fileRepository;

    @Override
    public File saveFile(File file) {
        return fileRepository.save(file);
    }

    @Override
    public void removeFile(String id) {
        fileRepository.delete(id);
    }

    @Override
    public File getFileById(String id) {
        return fileRepository.findOne(id);
    }

    @Override
    public List<File> listFilesByPage(int pageIndex, int pageSize) {
        Page<File> page = null;
        List<File> list = null;

        Sort sort = new Sort(Sort.Direction.DESC, "uploadDate");
        Pageable pageable = new PageRequest(pageIndex, pageSize, sort);

        page = fileRepository.findAll(pageable);
        list = page.getContent();
        return list;
    }
}
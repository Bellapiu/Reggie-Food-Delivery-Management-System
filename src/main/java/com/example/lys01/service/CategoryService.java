package com.example.lys01.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.lys01.Result.R;
import com.example.lys01.entry.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService extends IService<Category> {
    R<Map<String, Object>> categoryFind(int page, int pageSize);

    R<String> categoryAdd(Category category);

    R<String> categoryUpdate(Category category);

    R<String> categoryDelete(Long ids);

    R<List<Category>> getList(int type);
}

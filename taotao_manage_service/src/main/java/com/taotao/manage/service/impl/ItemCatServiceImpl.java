package com.taotao.manage.service.impl;

import com.github.abel533.mapper.Mapper;
import com.taotao.manage.mapper.ItemCatMapper;
import com.taotao.manage.pojo.ItemCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemCatServiceImpl extends BaseService<ItemCat> {
    @Autowired
    public ItemCatMapper itemCatMapper;


}

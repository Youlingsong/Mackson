package com.taotao.manage.service.impl;

import com.taotao.manage.mapper.ItemDescMapper;
import com.taotao.manage.pojo.ItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemDescServiceimpl extends BaseService<ItemDesc> {
    @Autowired
    public ItemDescMapper itemDescMapper;
}

package com.taotao.manage.service.impl;

import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl extends BaseService<Item> {
    @Autowired
    public ItemMapper itemMapper;
    @Autowired
    ItemDescServiceimpl itemDescService;

    /*保证两个service在同一个事务中*/
    public void insertItemAndItemDesc(Item item,String desc){

        /*初始化商品状态1*/
        item.setStatus(1);
        // 强制设置id为null，所有不能被用户提交的数据置为null
        item.setId(null);
        this.save(item);

        // 保存商品的描述信息
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDescService.save(itemDesc);



    }
}

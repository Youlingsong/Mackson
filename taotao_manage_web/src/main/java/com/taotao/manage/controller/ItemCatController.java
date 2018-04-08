package com.taotao.manage.controller;

import com.taotao.manage.pojo.ItemCat;
import com.taotao.manage.service.impl.ItemCatServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
    @Autowired
    public ItemCatServiceImpl itemCatService;


    @GetMapping
    public ResponseEntity<List<ItemCat>> queryItemCatList(@RequestParam(value = "id",defaultValue ="0") Long parent_id){
        ItemCat itemCat = new ItemCat();
        itemCat.setParentId(parent_id);
        List<ItemCat> itemCatList=  itemCatService.queryListByWhere(itemCat);
        try {
            if(CollectionUtils.isEmpty(itemCatList)){
                //如果为空，返回状态码
                return ResponseEntity.notFound().build();
            }else{
                return ResponseEntity.ok().body(itemCatList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}

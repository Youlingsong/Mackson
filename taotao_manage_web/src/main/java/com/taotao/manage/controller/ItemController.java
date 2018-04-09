package com.taotao.manage.controller;

import com.taotao.manage.pojo.Item;
import com.taotao.manage.service.impl.ItemDescServiceimpl;
import com.taotao.manage.service.impl.ItemServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("item")
public class ItemController {

    @Autowired
    ItemServiceImpl itemService;

    @Autowired
    ItemDescServiceimpl itemDescService;

    @PostMapping
    public ResponseEntity<Void> saveItem(Item item, @RequestParam("desc") String desc){
        //数据为空，请求坏的
        try {
            if(item==null&&StringUtils.isEmpty(item.getTitle())){
                return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }else{
                /*调用service方法保证同一事务*/
              itemService.insertItemAndItemDesc(item,desc);
              return ResponseEntity.ok().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

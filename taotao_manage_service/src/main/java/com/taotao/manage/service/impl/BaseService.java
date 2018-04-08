package com.taotao.manage.service.impl;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.pojo.BasePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import javax.swing.*;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

public  abstract class BaseService<T extends BasePojo> {
    @Autowired
    private Mapper<T> mapper;

    public Class<T> clazz;

    {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.clazz = (Class<T>) type.getActualTypeArguments()[0];
    }



/*
6、save
7、update
8、deleteById
9、deleteByIds
10、deleteByWhere*/

    /*根据主键id查询*/
   public T queryById(Long id){
       T t = mapper.selectByPrimaryKey(id);
       return t;
   }

   /*查询所有*/
   public List<T> queryAll(){
       List<T> select = mapper.select(null);
       return select;
   }

   /*查询一条记录*/
   public T queryOne(T record){
       T t = mapper.selectOne(record);
       return t;
   }

   /*根据条件查询结果是集合*/
   public List<T> queryListByWhere(T record){
       List<T> select = mapper.select(record);
       return select;
   }

   /*分页查询*/
   public PageInfo<T> queryPageListByWhere(T record,int pageNumber,int pageSize){
       PageHelper.startPage(pageNumber,pageSize);
       List<T> select = mapper.select(record);
       return new PageInfo<T>(select);
   }

   /*保存*/
   public Integer save(T record){
       record.setCreated(new Date());
       record.setUpdated(record.getCreated());
       int insert = mapper.insert(record);
       return insert;
   }

    /**
     * 新增数据，新增不为null的字段
     *
     * @param record
     * @return
     */
    public Integer saveSelective(T record) {
        record.setCreated(new Date());
        record.setUpdated(record.getCreated());
        return this.mapper.insertSelective(record);
    }

    /**
     * 更新
     *
     * @param record
     * @return
     */
    public Integer update(T record) {
        record.setUpdated(new Date());
        return this.mapper.updateByPrimaryKey(record);
    }

    /**
     * 更新，不为null的字段
     *
     * @param record
     * @return
     */
    public Integer updateSelective(T record) {
        record.setUpdated(new Date());
        record.setCreated(null);
        return this.mapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 根据id删除
     *
     * @param id
     * @return
     */
    public Integer deleteById(Long id) {
        return this.mapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据条件删除记录
     *
     * @return
     */
    public Integer deleteByWhere(T record) {
        return this.mapper.delete(record);
    }


    /**
     * 根据多个id删除用户信息
     *
     * @param ids
     * @param clazz
     * @param property
     * @return
     */
    public Integer deleteByIds(List<Object> ids, String property) {
        Example example = new Example(clazz);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn(property, ids);
        int i = this.mapper.deleteByExample(criteria);
        return i;

    }


}

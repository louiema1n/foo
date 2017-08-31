package com.lm.service;

import com.lm.domain.Store;
import com.lm.mapper.StoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Louie on 2017-08-12.
 */
@Service
public class StoreService {
    @Autowired
    private StoreMapper storeMapper;

    public List<Store> getAll(String search) {
        return this.storeMapper.selectAll(search);
    }

    public List<Store> getAble(String search) {
        return this.storeMapper.selectAble(search);
    }

    public Store getById(long id) {
        return this.storeMapper.selectById(id);
    }

    public void updById(Store store) {
        this.storeMapper.updateById(store);
    }

    public void addStore(Store store) {
        this.storeMapper.insert(store);
    }

    public Integer delById(long id) {
        return this.storeMapper.delById(id);
    }
}

package com.taotao.manage.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PropertiesService {
    //在Service层属于Spring父容器,controller能拿到
    @Value("${REPOSITORY_PATH}")
    public String REPOSITORY_PATH;
    @Value("${IMAGE_BASE_URL}")
    public String IMAGE_BASE_URL;
}

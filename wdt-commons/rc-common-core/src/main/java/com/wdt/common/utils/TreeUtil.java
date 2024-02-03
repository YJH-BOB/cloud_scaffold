package com.wdt.common.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeUtil {
    private static final Logger log = LoggerFactory.getLogger(TreeUtil.class);

    private static final String KEY_NAME = "id";
    private static final String PARENT_NAME = "parentId";
    private static final String CHILDREN_NAME = "children";

    public static <T> List<T> getTree(List<T> originalList) {
        if (originalList == null || originalList.isEmpty()) {
            return new ArrayList<>();
        }

        Map<Long, T> idNodeMap = new HashMap<>();
        Map<Long, List<T>> parentIdChildrenMap = new HashMap<>();

        originalList.forEach(node -> {
            try {
                Long id = Long.valueOf(BeanUtils.getProperty(node, KEY_NAME));
                Long parentId = Long.valueOf(BeanUtils.getProperty(node, PARENT_NAME));
                idNodeMap.put(id, node);
                parentIdChildrenMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(node);
            } catch (Exception e) {
                log.error("处理节点属性时出错", e);
            }
        });

        List<T> rootNodes = new ArrayList<>();
        // 构建树形结构
        parentIdChildrenMap.forEach((parentId, children) -> {
            if (parentId == 0) {
                rootNodes.addAll(children);
            } else {
                T parentNode = idNodeMap.get(parentId);
                if (parentNode != null) {
                    try {
                        BeanUtils.setProperty(parentNode, CHILDREN_NAME, children);
                    } catch (Exception e) {
                        log.error("设置子节点属性时出错", e);
                    }
                }
            }
        });
        return rootNodes;
    }
}

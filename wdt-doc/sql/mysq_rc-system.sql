-- 创建服务为rc-system的数据库
CREATE DATABASE IF NOT EXISTS rc_system DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
-- 创建用户表
CREATE TABLE IF NOT EXISTS  `sys_user` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                            `user_name` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '用户名',
                            `nick_name` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '昵称',
                            `password` varchar(64) NOT NULL DEFAULT 'NULL' COMMENT '密码',
                            `status` char(1) DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
                            `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
                            `phone_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机号',
                            `sex` char(1) DEFAULT NULL COMMENT '用户性别（0男，1女，2未知）',
                            `avatar` varchar(128) DEFAULT NULL COMMENT '头像',
                            `user_type` char(1) NOT NULL DEFAULT '1' COMMENT '用户类型（0管理员，1普通用户）',
                            `create_user` bigint(20) DEFAULT NULL COMMENT '创建人的用户id',
                            `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                            `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
                            `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                            `is_delete` int(11) DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- 创建权限表
CREATE TABLE IF NOT EXISTS `sys_permission` (
                                  `id` bigint(20) NOT NULL COMMENT '编号',
                                  `pid` bigint(20) DEFAULT NULL COMMENT '所属上级',
                                  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '名称',
                                  `path` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '访问路径',
                                  `type` tinyint(1) DEFAULT NULL COMMENT '类型(1:菜单,2:按钮)',
                                  `component` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '组件路径',
                                  `icon` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '图标',
                                  `permission_value` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限值',
                                  `status` tinyint(1) DEFAULT NULL COMMENT '状态(0:禁止,1:正常)',
                                  `level` int(11) DEFAULT NULL COMMENT '层级',
                                  `is_select` tinyint(1) DEFAULT NULL COMMENT '是否选中',
                                  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
                                  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
                                  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                  `is_delete` tinyint(1) DEFAULT NULL COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户权限表';
-- 创建角色表
CREATE TABLE `sys_role` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
                            `role_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '角色编号',
                            `role_name` varchar(32) DEFAULT NULL COMMENT '角色名',
                            `role_description` varchar(64) DEFAULT NULL COMMENT '角色说明',
                            `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
                            `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                            `update_user` bigint(20) DEFAULT NULL COMMENT '修改人',
                            `update_time` datetime DEFAULT NULL COMMENT '修改时间',
                            `is_delete` tinyint(1) DEFAULT NULL COMMENT '是否删除',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户角色表';

-- 创建用户-角色关联表
CREATE TABLE `sys_user_role_rel` (
                                     `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                     `user_id` int(11) DEFAULT NULL COMMENT '用户id',
                                     `role_id` int(11) DEFAULT NULL COMMENT '角色id',
                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户-角色关联表';

-- 角色-权限关联表
CREATE TABLE `sys_role_permission_rel` (
                                           `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
                                           `role_id` int(11) DEFAULT NULL COMMENT '角色id',
                                           `permission_id` int(11) DEFAULT NULL COMMENT '权限id',
                                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色-权限关联表';
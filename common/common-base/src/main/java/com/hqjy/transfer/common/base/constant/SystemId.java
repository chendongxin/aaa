package com.hqjy.transfer.common.base.constant;

/**
 * 系统特殊资源固定ID
 * 用户表，角色表，部门表的ID从1001开始，0-1000规划为系统ID，用于特殊业务需求
 *
 * @author : heshuangshuang
 * @date : 2018/6/14 16:15
 */
public interface SystemId {
    /**
     * 超级管理员ID
     */
    Long SUPER_ADMIN = 1L;

    /**
     * 树根节点ID
     */
    Long TREE_ROOT = 1L;

    /**
     * 校区树根节点ID
     */
    String SCHOOL_TREE_ROOT = "0";

    /**
     * 部门ID
     */
    enum User {
        /**
         * 创建人没有，默认为0
         */
        NO_CREATE_ID(0L);

        private Long value;

        User(Long value) {
            this.value = value;
        }

        public Long getValue() {
            return value;
        }
    }


    /**
     * 部门ID
     */
    enum Dept {
        /**
         * 没有指定区域，或处理部门不存在的商机，默认分配到此部门，如果配置了系统参数，以系统参数为主
         */
        ALLOT_NO_AREA(100L);

        private Long value;

        Dept(Long value) {
            this.value = value;
        }

        public Long getValue() {
            return value;
        }

        public boolean equals(Long value) {
            return this.value.equals(value);
        }
    }
}

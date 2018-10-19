package com.hqjy.mustang.transfer.crm.nc;

import com.hqjy.mustang.transfer.crm.feign.SysUserDeptServiceFeign;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = deptTest.class)
public class deptTest {

    @Autowired
    private SysUserDeptServiceFeign sysUserDeptServiceFeign;

    @Test
    public void sysUserDeptServiceFeign() {
        System.out.println(sysUserDeptServiceFeign.getUserDeptIdList(215L));
    }
}

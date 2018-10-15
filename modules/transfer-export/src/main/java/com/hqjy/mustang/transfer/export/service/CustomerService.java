package com.hqjy.mustang.transfer.export.service;

import com.hqjy.mustang.common.base.utils.R;
import com.hqjy.mustang.transfer.export.model.dto.CustomerUpDTO;
import com.hqjy.mustang.transfer.export.model.query.CustomerExportQueryParams;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author gmm
 * @date create on 2018/10/10
 * @apiNote
 */
public interface CustomerService {

    /**
     * 导入客户
     *
     * @param file 导入的文件
     * @param dto  请求输入参数
     * @return 返回导入结果
     */
    R importCustomer(MultipartFile file, CustomerUpDTO dto);

    /**
     * 导出客户
     *
     * @param query 导出筛选条件
     * @return 返回导出结果
     */
    String exportCustomer(CustomerExportQueryParams query);
}

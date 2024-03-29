package com.hqjy.mustang.transfer.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.hqjy.mustang.common.base.base.BaseServiceImpl;
import com.hqjy.mustang.common.base.constant.StatusCode;
import com.hqjy.mustang.common.base.constant.SystemId;
import com.hqjy.mustang.common.base.exception.RRException;
import com.hqjy.mustang.common.base.utils.KeyWordUtil;
import com.hqjy.mustang.common.base.utils.PageQuery;
import com.hqjy.mustang.common.base.utils.RecursionUtil;
import com.hqjy.mustang.transfer.crm.dao.TransferKeywordDao;
import com.hqjy.mustang.transfer.crm.model.entity.TransferKeywordEntity;
import com.hqjy.mustang.transfer.crm.service.TransferKeywordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserId;
import static com.hqjy.mustang.common.web.utils.ShiroUtils.getUserName;

@Service
@Slf4j
public class TransferKeywordServiceImpl extends BaseServiceImpl<TransferKeywordDao, TransferKeywordEntity, Integer> implements TransferKeywordService {

    /**
     * 关键字字典缓存
     */
    private static List<TransferKeywordEntity> dictionaryList = new ArrayList<>();

    /**
     * 获取所有关键词
     */
    @Override
    public List<TransferKeywordEntity> getAllKeywordList() {
        return baseDao.getAllKeywordList();
    }

    /**
     * 关键词管理树数据
     */
    @Override
    public HashMap<String, List<TransferKeywordEntity>> getRecursionTree(boolean showRoot) {
        return RecursionUtil.listTree(showRoot, TransferKeywordEntity.class, "getId", getAllKeywordList(), Collections.singletonList(SystemId.TREE_ROOT_INTE));
    }

    @Override
    public List<TransferKeywordEntity> findKeyPage(Object o, PageQuery pageQuery) {
        PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize(), pageQuery.getPageOrder());
        List<TransferKeywordEntity> list = baseDao.findPage(pageQuery);
        for (TransferKeywordEntity entity : list) {
            if (entity.getId().equals(Integer.valueOf(o.toString()))) {
                list.remove(entity);
                break;
            }
        }
        return list;
    }

    /**
     * 增加一条关键词
     */
    @Override
    public int save(TransferKeywordEntity transferKeywordEntity) {
        if (null != baseDao.findOneByName(transferKeywordEntity.getName())) {
            throw new RRException(StatusCode.DATABASE_DUPLICATEKEY);
        }
        transferKeywordEntity.setSign(0);
        transferKeywordEntity.setCreateUserId(getUserId());
        transferKeywordEntity.setCreateUserName(getUserName());
        cleanList();
        return baseDao.save(transferKeywordEntity);
    }

    /**
     * 修改一条关键词
     */
    @Override
    public int update(TransferKeywordEntity transferKeywordEntity) {
        transferKeywordEntity.setUpdateUserId(getUserId());
        transferKeywordEntity.setUpdateUserName(getUserName());
        cleanList();
        return baseDao.update(transferKeywordEntity);
    }

    /**
     * 删除关键词
     *
     * @param ids 删除关键词数组
     * @return int>0 为删除成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBatch(Integer[] ids) {
        for (Integer id : ids) {
            if (baseDao.findOne(id).getSign() == 1) {
                throw new RRException(StatusCode.DATABASE_SELECT_USE);
            }
            List<TransferKeywordEntity> keywordList = baseDao.findByParentId(id);
            if (keywordList.size() > 0) {
                throw new RRException(StatusCode.DATABASE_DELETE_CHILD);
            }
        }
        cleanList();
        return super.deleteBatch(ids);
    }

    /**
     * 根据职位信息，遍历出关键字
     */
    @Override
    public String getKeyWork(String info) {
        log.info("开始遍历关键字：{}", info);
        dictionaryList = Optional.ofNullable(dictionaryList).orElseGet(() -> baseDao.findDictionaryList());
        dictionaryList.forEach(transferKeywordEntity -> System.out.println(transferKeywordEntity.getName()));
        if (dictionaryList != null && dictionaryList.size() > 0) {
            Set<String> wordSet = dictionaryList.stream().map(TransferKeywordEntity::getName).collect(Collectors.toSet());
            KeyWordUtil keyWordUtil = new KeyWordUtil(wordSet);
            Set<String> words = keyWordUtil.getWords(info);
            if (words.size() > 0) {
                String result = words.iterator().next();
                log.info("匹配到关键字：{}", result);
                return result;
            }
        }
        log.info("没有遍历到任何关键字：{}", info);
        return null;
    }

    /**
     * 清除缓存
     */
    private synchronized void cleanList() {
        dictionaryList.clear();
    }
    /**
     * 获取关键词类别(第三级)
     */
    @Override
    public List<TransferKeywordEntity> getAllKeyList() {
        List<TransferKeywordEntity> parentIdList = new ArrayList<>();
        List<TransferKeywordEntity> keywordList = new ArrayList<>();
        parentIdList.add(baseDao.findIdByParentId(0).get(0));
        for (int count = 0; count < 2; count++) {
            keywordList = this.getAllKeywordByParentId(parentIdList);
            parentIdList = keywordList;
        }
        return keywordList;
    }

    private List<TransferKeywordEntity> getAllKeywordByParentId(List<TransferKeywordEntity> parentIdList) {
        List<TransferKeywordEntity> keyWordList = new ArrayList<>();
        for (TransferKeywordEntity id : parentIdList) {
            keyWordList.addAll(baseDao.findIdByParentId(id.getId()));
        }
        return keyWordList;
    }

    /**
     * 获取某一类别下的关键词
     */
    @Override
    public List<TransferKeywordEntity> getAllKeyword(Integer parentId) {
        return baseDao.findIdByParentId(parentId);
    }

}

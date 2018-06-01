package com.infrastructure.portal.mapper.document;

import java.util.List;
import com.infrastructure.portal.entity.po.document.DocumentInfo;
import com.infrastructure.portal.entity.vo.QueryDocumentInfoVo;

public interface DocumentInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(DocumentInfo record);

    int insertSelective(DocumentInfo record);

    List<QueryDocumentInfoVo> selectByPage(QueryDocumentInfoVo queryDocumentInfoVo);

    DocumentInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DocumentInfo record);

    int updateByPrimaryKey(Integer id);
    
    int addDownloadTimes(Integer id);
    
    int getDownloadCounts();
}
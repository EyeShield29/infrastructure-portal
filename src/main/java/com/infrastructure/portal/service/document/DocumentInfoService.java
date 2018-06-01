package com.infrastructure.portal.service.document;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.infrastructure.portal.entity.po.document.DocumentInfo;
import com.infrastructure.portal.entity.vo.QueryDocumentInfoVo;
import com.infrastructure.portal.mapper.document.DocumentInfoMapper;

@Service
public class DocumentInfoService {
    @Autowired
    private DocumentInfoMapper documentInfoMapper;
    /**
     * 分页查询文件列表
     */    
    public PageInfo<QueryDocumentInfoVo> queryDocumentInfoListByPage(int currentPage, int pageSize, QueryDocumentInfoVo queryDocumentInfoVo){
    	PageHelper.startPage(currentPage, pageSize);
    	List<QueryDocumentInfoVo> documentInfoList = documentInfoMapper.selectByPage(queryDocumentInfoVo);
    	PageInfo<QueryDocumentInfoVo> pageInfo = new PageInfo<QueryDocumentInfoVo>(documentInfoList);
    	return pageInfo;
    }
    /**
     * 添加附件信息
     */
    public void addDocumentInfo(DocumentInfo documentInfo)
            throws Exception {
        
        documentInfo.setCreateTime(new Date());
        documentInfo.setUploadTime(new Date());
        documentInfo.setDownloadTimes(0);
        documentInfoMapper.insertSelective(documentInfo);
    }

    /**
     * 删除用户
     */
    public void deleteDocumentInfo(int id) throws Exception {
        //documentInfoDao.deleteDocumentInfoById(id);
    	documentInfoMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更改附件基本信息
     * @throws Exception 
     */
    public void modifyDocumentInfo(DocumentInfo documentInfo) throws Exception {
        documentInfoMapper.updateByPrimaryKeySelective(documentInfo);
    }

    /**
     * <pre>
     * 增加下载次数
     * </pre>
     */
    public void addDownloadTimes(int id) throws Exception {
        // TODO Auto-generated method stub
        documentInfoMapper.addDownloadTimes(id);
    }

    /**
     * <pre>
     *获取总下载次数
     * </pre>
     */
    public int downloadCount() throws Exception {
        // TODO Auto-generated method stub
        return documentInfoMapper.getDownloadCounts();
    }
    
}

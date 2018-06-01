package com.infrastructure.portal.service.config;


import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.infrastructure.portal.entity.po.config.ProcessConfig;
import com.infrastructure.portal.mapper.config.ProcessConfigMapper;

@Service
public class ProcessConfigService {

    @Autowired
    private ProcessConfigMapper processConfigMapper;
    /**
     * 根据period、process_name来分页查询用户
     */
    public PageInfo<ProcessConfig> queryProcessConfigListByPage(Integer currentPage, Integer pageSize,
    		ProcessConfig processConfig) throws Exception {
        PageHelper.startPage(currentPage, pageSize);
        List<ProcessConfig> processConfigList = processConfigMapper.selectByPage(processConfig);
        PageInfo<ProcessConfig> pageInfo = new PageInfo<ProcessConfig>(processConfigList);
        return pageInfo;
    }
    
    public List<ProcessConfig> queryProcessConfigList(int period) throws Exception {
        return processConfigMapper.queryProcessConfigList(period);
    }

    /**
     * 添加流程
     */
    public void addProcessConfig(ProcessConfig processConfig)
            throws Exception {
        
        processConfig.setCreateTime(new Date());
        processConfigMapper.addProcessConfig(processConfig);
    }

    /**
     * 删除流程配置
     */
    public void deleteProcessConfig(int id) throws Exception {
        processConfigMapper.deleteProcessConfigById(id);
    }

    /**
     * 更改流程配置信息
     * @throws Exception 
     */
    public void modifyProcessConfig(ProcessConfig processConfig) throws Exception {
        processConfigMapper.modifyProcessConfig(processConfig);
    }

    /**
     * <pre>
     * TODO
     * </pre>
     */
    public boolean checkProcessSerialNum(int period, int serialNum) throws Exception {
        int length = processConfigMapper.getProcessList(period,serialNum).size();
        if(length>0)
            return true;
        else
            return false;
    }
    
}

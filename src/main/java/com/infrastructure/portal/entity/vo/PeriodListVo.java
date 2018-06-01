/*
 * Copyright (c) 2007-2015 by CSCW All rights reserved
 */
package com.infrastructure.portal.entity.vo;

import java.util.List;

import com.infrastructure.portal.entity.po.project.ProjectPeriod;


/**
 * TODO <br/>
 * <br>=
 */
public class PeriodListVo {
    private List<ProjectPeriod> periods;

    public List<ProjectPeriod> getPeriods() {
        return periods;
    }

    public void setPeriods(List<ProjectPeriod> periods) {
        this.periods = periods;
    }
    
    public PeriodListVo(){
        super();
    }
    
    public PeriodListVo(List<ProjectPeriod> periods){
        super();
        this.periods = periods;
    }
}

/*
 * Copyright (c) 2007-2015 by CSCW All rights reserved
 */
package com.infrastructure.portal.utils;

import com.infrastructure.portal.entity.po.process.PeriodEnum;

/**
 * TODO <br/>
 * <br>=
 */
public class PeriodMap {
    
    public static String getPeriodName(int period){
        if(period==1){
            return PeriodEnum.FIRST.getValue();
        }else if(period==2){
            return PeriodEnum.SECOND.getValue();
        }else if(period==3){
            return PeriodEnum.THIRD.getValue();
        }else if(period==4){
            return PeriodEnum.FOURTH.getValue();
        }else if(period==5){
            return PeriodEnum.FIFTH.getValue();
        }else if(period==6){
            return PeriodEnum.SIXTH.getValue();
        }else if(period==7){
            return PeriodEnum.SEVENTH.getValue();
        }
        return null;
    }
}

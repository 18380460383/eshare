package com.jiebian.adwlf;

import com.jiebian.adwlf.bean.entitys.SelectEntity;
import com.jiebian.adwlf.interfaces.EnIdentifyShow;

/**
 * describe:
 * notice:
 * Created by FuPei on 2016/5/31.
 */
public abstract class EnMoneySelectEntity extends SelectEntity implements EnIdentifyShow{
    public abstract float getMoney();
}

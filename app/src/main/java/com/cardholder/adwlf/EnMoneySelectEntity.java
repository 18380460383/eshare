package com.cardholder.adwlf;

import com.cardholder.adwlf.bean.entitys.SelectEntity;
import com.cardholder.adwlf.interfaces.EnIdentifyShow;

/**
 * describe:
 * notice:
 * Created by FuPei on 2016/5/31.
 */
public abstract class EnMoneySelectEntity extends SelectEntity implements EnIdentifyShow{
    public abstract float getMoney();
}

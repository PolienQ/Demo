package com.pnas.demo.entity.wage;

import java.io.Serializable;
import java.util.List;

/**
 * ********
 *
 * @author 彭浩楠
 * @date 2015-12-14 11:31
 * @describ 工资详情的数据Info
 */
public class WageMonthInfo implements Serializable {

    public SalaryTypeEntity SalaryType;

    public List<SubsidyListEntity> subsidyList;

    public static class SalaryTypeEntity implements Serializable {
        /**
         * deductTotal : 3500.0
         * actualAmount : 46500.0
         * payTotal : 3500.0
         * payCompany : 上海罗塞德信息科技有限公司
         * empno : A001
         * startDate : 2015-11-01
         * endDate : 2015-12-31
         */
        public String deductTotal;
        public String actualAmount;
        public String payTotal;
        public String payCompany;
        public String empno;
        public String startDate;
        public String endDate;
    }

    public static class SubsidyListEntity implements Serializable {
        /**
         * subsidyName : 税前其它加
         * subsidyValue : 50000.0
         */
        public String subsidyName;
        public String subsidyValue;
    }
}

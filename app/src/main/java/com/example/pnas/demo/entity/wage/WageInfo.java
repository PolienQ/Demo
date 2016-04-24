package com.example.pnas.demo.entity.wage;

import java.io.Serializable;
import java.util.List;

/**
 * ********
 *
 * @author 彭浩楠
 * @date 2015-12-14 11:31
 * @describ 工资列表中,顶部年份和时光轴(月和薪资)的共同Info
 */
public class WageInfo<T> implements Serializable {

    // ServiceMediator 类中修改请求的泛型 getWageList-请求年份 ; getWageListForYear-请求某年的详情
    public List<T> Salarylist;

    @Override
    public String toString() {
        return "WageInfo{" +
                "Salarylist=" + Salarylist +
                '}';
    }

    public class WageItem implements Serializable {
        /******
         * datetime=2014-08-10,
         payAmount=9000
         */

        public String datetime;
        public String payAmount;

        @Override
        public String toString() {
            return "WageItem{" +
                    "datetime='" + datetime + '\'' +
                    ", payAmount='" + payAmount + '\'' +
                    '}';
        }
    }

}

package com.ql.util.express.issueNullOperator;


import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;

import com.ql.util.express.Sku;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class IssueNullOperator {
    ExpressRunner runner;
    DefaultContext<String, Object> context;

    @Before
    public void setUp() {
        runner = new ExpressRunner();
        context = new DefaultContext();
        context.put("sku", new Sku(3, 100));
    }


    @Test
    public void assert_null_operator_failed_when_less_than_compare() {
        String express = "sku.price == 100 && sku.extProperties.notExistKey <= 100";
        try {
            runner.execute(express, context, null, true, false, null);
            fail("应该会有异常");
        } catch (Exception e) {
            String errorCauseMsg = e.getCause().getMessage();
            assertTrue(errorCauseMsg.equals("空操作数不能执行这个操作：<="));
        }
    }

    @Test(expected = Test.None.class)
    public void assert_null_operator_ok_when_compare_with_string() throws Exception {
        String express = "sku.price == 100 && sku.extProperties.notExistKey == '5'";

        runner.execute(express, context, null, true, false, null);
    }

}

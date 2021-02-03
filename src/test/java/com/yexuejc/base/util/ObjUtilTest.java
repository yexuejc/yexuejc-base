package com.yexuejc.base.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * json化输出，改变属性名称，输出类型 {@link ObjUtil} + {@link  ToUeProperty} 测试类
 *
 * @author: yexuejc
 * @date: 2021-02-03 10:02:55
 */
class ObjUtilTest {

    public static void main(String[] args) {
        start();
    }

    public static void start(){
        B a = new B();
        a.nameFirst = "张三";
        a.ageInt = "5165458";
        a.testAss = "asdasdsad";
        a.setaM1("method1");
        a.setbM1("b1Mthod1");
        a.protectedStr = "protectedStr";
        a.amount=new BigDecimal("3");
        a.time = LocalDateTime.now();
        a.dateTime=new Date();
        C c = new C();
        c.ageInt = "test";
        a.c = c;
        a.list = new ArrayList<>();
        a.list.add(c);
        Map<String, Object> underlineMap = ObjUtil.getUnderlineMap(a, false, false);
        System.out.println(JsonUtil.formatPrinter(underlineMap));
    }

    static class A implements Serializable {
        private static final long serialVersionUID = -8462118058721865488L;
        public String nameFirst;
        public String ageInt;
        private String aM1;
        @ToUeProperty("p_str")
        protected String protectedStr;

        public String getaM1() {
            return aM1;
        }

        public A setaM1(String aM1) {
            this.aM1 = aM1;
            return this;
        }
    }

    static class B extends A {
        private static final long serialVersionUID = -8462118058721865488L;
        public String testAss;
        private String bM1;

        @ToUeProperty(type = Integer.class)
        public BigDecimal amount;

        @ToUeProperty(type = String.class)
        public LocalDateTime time;

        public Date dateTime;

        private C c;
        List<C> list;

        public String getbM1() {
            return bM1;
        }

        public B setbM1(String bM1) {
            this.bM1 = bM1;
            return this;
        }
    }

    static class C extends A {
        private static final long serialVersionUID = -8462118058721865488L;
        public String testAss;
        private String bM1;

        public String getbM1() {
            return bM1;
        }

        public C setbM1(String bM1) {
            this.bM1 = bM1;
            return this;
        }
    }

//    public static void main(String[] args) {
////        test1();
////        test2();
//
//    }
//
//    private static void test2() {
//        B t = new B();
//        t.sex = "男";
//        t.age = 18;
//        A test = new A();
//        test.name = "张三";
//        t.test = test;
//        B b = depthClone(t);
//        System.out.println(JsonUtil.obj2Json(b));
//    }
//
//    static class A implements Serializable {
//        private static final long serialVersionUID = -8462118058721865488L;
//        public String name;
//    }
//
//    static class B implements Serializable {
//        private static final long serialVersionUID = 3297717505428005316L;
//        public int age;
//        public String sex;
//        public A test;
//    }
//
//    static class C implements Serializable {
//        private static final long serialVersionUID = 3297717505428005316L;
//        public int age;
//        public String sex;
//        public A test;
//    }
//
//    private static void test1() {
//        ApiVO apiVO = new ApiVO(ApiVO.STATUS.S);
//        apiVO.setMsg("asdsadsad");
//        apiVO.setObject1("sadsadsad");
//
//        Resps<String> obj = new Resps<>();
//        obj.setSucc("安达圣斗士", "ok");
//        System.out.println(obj);
//        apiVO.setObject2(obj);
//        ApiVO apiVO1 = depthClone(apiVO);
//        System.out.println(apiVO == apiVO1);
//        System.out.println(JsonUtil.obj2Json(apiVO1));
//        System.out.println(JsonUtil.obj2Json(apiVO1.getObject1(String.class)));
//        System.out.println(JsonUtil.obj2Json(apiVO1.getObject2(Resps.class)));
//        System.out.println(apiVO1.getObject2(Resps.class) == obj);
//    }
}
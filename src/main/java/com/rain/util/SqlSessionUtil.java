package com.rain.util;



import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionUtil {

    private SqlSessionUtil(){}
    private static SqlSessionFactory sqlSessionFactory = null;
    static { String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    private static ThreadLocal<SqlSession> t = new ThreadLocal<SqlSession>();


    // 获取Session对象

    private static SqlSession getSqlSession() {

        SqlSession session = t.get();
        if(session== null){
            session = sqlSessionFactory.openSession();
            t.remove();
        }
        return session;
    }

     // 关闭SqlSession对象

    private static void myClose(SqlSession sqlSession){
        if(sqlSession != null){
            sqlSession.close();
            t.remove();
        }
    }

}

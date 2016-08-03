package com.study.mingappk.model.database;

import android.content.Context;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.WhereBuilder;
import com.litesuits.orm.db.model.ConflictAlgorithm;
import com.orhanobut.hawk.Hawk;
import com.study.mingappk.app.APP;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ming on 2016/5/9.
 */
public class MyDB {

    static String DB_NAME;

    public static void setLiteOrm(LiteOrm liteOrm) {
        MyDB.liteOrm = liteOrm;
    }

    private static volatile LiteOrm liteOrm;

    public static LiteOrm createDb(Context _activity) {
        // 创建数据库
        if (liteOrm == null) {
            synchronized (MyDB.class) {
                if (liteOrm == null) {
                    // 创建数据库,传入当前上下文对象和数据库名称
                    File folder = new File(APP.FILE_PATH + "DataBase/");
                    if (!folder.exists()) {
                        boolean a = folder.mkdirs();
                    }
                    DB_NAME = APP.FILE_PATH + "DataBase/"
                            + "user_" + Hawk.get(APP.ME_UID, "") + ".db";
                    liteOrm = LiteOrm.newSingleInstance(_activity, DB_NAME);
                }
            }
        }
        return liteOrm;
    }

    /**
     * 插入一条记录
     *
     * @param t
     */
    public static <T> void insert(T t) {
        if (t == null) {
            return;
        }
        liteOrm.save(t);
    }

    /**
     * 插入所有记录
     *
     * @param list
     */
    public static <T> void insertAll(List<T> list) {
        liteOrm.save(list);
    }


    /**
     * 查询所有
     *
     * @param cla
     * @return
     */
    public static <T> List<T> getQueryAll(Class<T> cla) {
        return liteOrm.query(cla);
    }

    /**
     * 查询  某字段 等于 Value的值
     *
     * @param cla
     * @param field
     * @param value
     * @return
     */
    public static <T> List<T> getQueryByWhere(Class<T> cla, String field, Object[] value) {
        return liteOrm.<T>query(new QueryBuilder<T>(cla).where(field + "=?", value));
    }
    public static <T> List<T> getQueryByWhere(Class<T> cla, String field, Object value) {
        QueryBuilder<T> queryBuilder=new QueryBuilder<T>(cla).whereEquals(field, value);
        return liteOrm.<T>query(queryBuilder);
    }

    /**
     * 查询  某字段 等于 Value的值  可以指定从1-20，就是分页
     *
     * @param cla
     * @param field
     * @param value
     * @param start
     * @param length
     * @return
     */
    public static <T> List<T> getQueryByWhereLength(Class<T> cla, String field, Object[] value, int start, int length) {
        QueryBuilder<T> queryBuilder=new QueryBuilder<T>(cla).where(field + "=?", value).limit(start, length);
        return liteOrm.<T>query(queryBuilder);
    }

    /**
     * 删除所有 某字段等于 Vlaue的值
     *
     * @param cla
     * @param field
     * @param value
     */
    public static <T> void deleteWhere(Class<T> cla, String field, Object[] value) {
        liteOrm.delete(WhereBuilder.create(cla).where(field + "=?", value));
    }

    /**
     * 删除指定条目
     * @param t
     * @param <T>
     */
    public static <T> void delete(T t) {
        liteOrm.delete(t);
    }

    /**
     * 删除所有
     *
     * @param cla
     */
    public static <T> void deleteAll(Class<T> cla) {
        liteOrm.deleteAll(cla);
    }

    /**
     * 仅在以存在时更新
     *
     * @param t
     */
    public static <T> void update(T t) {
        liteOrm.update(t, ConflictAlgorithm.Replace);
    }
    public static <T> void update1(T t) {
        liteOrm.update(t);
    }


    public static <T> void updateALL(List<T> list) {
        liteOrm.update(list);
    }


}

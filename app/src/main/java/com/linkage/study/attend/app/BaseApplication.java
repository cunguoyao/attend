package com.linkage.study.attend.app;

import android.app.Activity;
import android.app.Application;

import com.linkage.study.attend.BuildConfig;

import org.xutils.DbManager;
import org.xutils.db.table.TableEntity;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/**
 * Created by cunguoyao on 2017/7/29.
 */

public class BaseApplication extends Application {

    private static String TAG = BaseApplication.class.getName();
    private static BaseApplication mInstance;
    private DbManager dbManager;
    /*Activity堆*/
    private Stack<Activity> activityStack = new Stack<Activity>();
    public DbManager getDbManager() {
        return dbManager;
    }

    private List<String> unSetStateBarColorPage;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initUnSetStatusBarColorPage();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
        initXdb();
    }

    public static synchronized BaseApplication getInstance() {
        return mInstance;
    }

    private void initUnSetStatusBarColorPage() {
        unSetStateBarColorPage = new ArrayList<>();
        unSetStateBarColorPage.add(getPackageName() + ".ui.SplashActivity");
        unSetStateBarColorPage.add(getPackageName() + ".ui.ImagePagerActivity");
    }

    public List<String> getUnSetStateBarColorPage() {
        return unSetStateBarColorPage;
    }

    private void initXdb() {
        /**
         * 初始化DaoConfig配置
         */
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                //设置数据库名，默认xutils.db
                .setDbName("auction.db")
                //设置数据库路径，默认存储在app的私有目录
                //.setDbDir(new File("/mnt/sdcard/"))
                //设置数据库的版本号
                .setDbVersion(1)
                //设置数据库打开的监听
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        //开启数据库支持多线程操作，提升性能，对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                //设置数据库更新的监听
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                    }
                })
                //设置表创建的监听
                .setTableCreateListener(new DbManager.TableCreateListener() {
                    @Override
                    public void onTableCreated(DbManager db, TableEntity<?> table){
                    }
                });
        //设置是否允许事务，默认true
        //.setAllowTransaction(true)
        dbManager = x.getDb(daoConfig);
    }

    public void addActivity(final Activity curAT) {
        if (null == activityStack) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(curAT);
    }

    public void removeActivity(final Activity curAT) {
        if (null == activityStack) {
            activityStack = new Stack<Activity>();
        }
        activityStack.remove(curAT);
    }

    //获取最后一个Activity
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    //关闭所有Activity
    public void clearActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

}

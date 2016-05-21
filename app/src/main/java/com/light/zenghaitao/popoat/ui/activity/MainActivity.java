package com.light.zenghaitao.popoat.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.j256.ormlite.dao.Dao;
import com.light.zenghaitao.popoat.R;
import com.light.zenghaitao.popoat.adapter.ItemAdapter;
import com.light.zenghaitao.popoat.helper.DatabaseHelper;
import com.light.zenghaitao.popoat.model.DatabaseBean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity {

    private DatabaseHelper databaseHelper = null;
    private Dao<DatabaseBean, Integer> databaseDao = null;
    private DatabaseBean databaseBean = new DatabaseBean();

//    private SimpleDraweeView mAvatorImg1;
//    private SimpleDraweeView mAvatorImg2;
    private ListView lv_main_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
//        mAvatorImg1 = (SimpleDraweeView) findViewById(R.id.user_avator1);
//        mAvatorImg2 = (SimpleDraweeView) findViewById(R.id.user_avator2);
        lv_main_test = (ListView) findViewById(R.id.lv_main_test);
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
        for (int i = 0;i<10;i++) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("item", "1");
            arrayList.add(hashMap);
        }
        ItemAdapter itemAdapter = new ItemAdapter(getApplicationContext(), arrayList);
        lv_main_test.setAdapter(itemAdapter);
        try {
            databaseHelper = DatabaseHelper.getHelper(getApplicationContext());
            databaseDao = databaseHelper.getDatabaseDao();
            initData();
            databaseDao.create(databaseBean);
            DatabaseBean databaseBean0 = new DatabaseBean();
            DatabaseBean databaseBean1 = new DatabaseBean();
            databaseBean0.setPeople("liwenbing");
            databaseBean0.setAge(20);
            databaseBean1.setPeople("limenking");
            databaseBean1.setAge(30);
            databaseDao.create(databaseBean0);
            databaseDao.create(databaseBean1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            List<DatabaseBean> databaseBeans = databaseDao.queryBuilder()
                    .where().eq(DatabaseBean.PEOPLE, "liwenbing")
                    .query();
            if (null!=databaseBeans) {
                for (int i = 0; i < databaseBeans.size(); i++) {
                    DatabaseBean databaseBeanOut = databaseBeans.get(i);
                    System.out.println(databaseBeanOut.getPeople() + "," + databaseBeanOut.getAge());
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
//        Uri uri = Uri.parse("http://bbs.szlanyou.com/uc_server/avatar.php?uid=2043&size=middle");
//        mAvatorImg1.setAspectRatio(1.0f);
//        mAvatorImg2.setAspectRatio(1.0f);
//
//        mAvatorImg1.setImageURI(uri);
//        mAvatorImg2.setImageURI(uri);

    }

    private void initData(){
        databaseBean.setPeople("ISO");
        databaseBean.setAge(20);
    }
}

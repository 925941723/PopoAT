package com.light.zenghaitao.popoat.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.offline.MKOLSearchRecord;
import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;
import com.baidu.mapapi.model.LatLng;
import com.light.zenghaitao.popoat.R;
import com.light.zenghaitao.popoat.util.ToastSingletonUtils;

import java.util.ArrayList;

/**
 * Created by ly-zenghaitao on 2016/5/21.
 */

public class MainActivity extends Activity implements View.OnClickListener, MKOfflineMapListener{

    private Button button;
    private MapView mMapView = null;
    private BaiduMap baiduMap = null;

    //定位相关声明
    private LocationClient locationClient = null;
    private LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    //自定义图标
    boolean isFirstLoc = true; //是否首次定位
    //离线主工具
    private MKOfflineMap mkOfflineMap = null;
    //已下载的离线地图信息列表
    private ArrayList<MKOLUpdateElement> localMapList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationClient.stop();
        baiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        mkOfflineMap.destroy();
    }

    private void init() {
        button = (Button) findViewById(R.id.button);
        mMapView = (MapView) findViewById(R.id.bmapview);
        baiduMap = mMapView.getMap();

        button.setOnClickListener(this);
        //开启定位图层
        baiduMap.setMyLocationEnabled(true);
//        mCurrentMode = LocationMode.NORMAL;
//        baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
//                mCurrentMode, true, mCurrentMarker));
        locationClient = new LocationClient(getApplicationContext());//实例化LocationClient类
        locationClient.registerLocationListener(bdLocationListener);//注册监听函数
        this.setLocationOption();//设置定位参数
        locationClient.start();//开始定位

        mkOfflineMap = new MKOfflineMap();
        mkOfflineMap.init(this);
        //获取已离线下载的地图
        localMapList = mkOfflineMap.getAllUpdateInfo();
        if (null==localMapList){
            ArrayList<MKOLSearchRecord> records = mkOfflineMap.getOfflineCityList();
            for (MKOLSearchRecord r:records){
                if (!TextUtils.isEmpty(r.cityName)&&(r.cityName.equals("广东省")||r.cityName.equals("全国基础包"))){
                    mkOfflineMap.start(r.cityID);
                }
            }
        }
    }

    public BDLocationListener bdLocationListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //mapView销毁后不再接受新的位置
            if (null == bdLocation || null == mMapView){
                return;
            }
            MyLocationData locationData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    //此处设置开发者获取到的方向信息
                    .direction(100).latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();
            baiduMap.setMyLocationData(locationData);
            ToastSingletonUtils.showShort(String.valueOf(bdLocation.getLatitude())+","+String.valueOf(bdLocation.getLongitude()));
            if (isFirstLoc){
                isFirstLoc = false;

                LatLng latLng = new LatLng(bdLocation.getLatitude(),
                        bdLocation.getLongitude());
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLngZoom(latLng, 16);
                baiduMap.animateMapStatus(mapStatusUpdate);
            }

        }
    };

    private void setLocationOption(){
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setPriority(LocationClientOption.GpsFirst);
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        option.setNeedDeviceDirect(true);

        locationClient.setLocOption(option);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onGetOfflineMapState(int type, int state) {
        switch (type) {
            case MKOfflineMap.TYPE_DOWNLOAD_UPDATE: {
                MKOLUpdateElement update = mkOfflineMap.getUpdateInfo(state);
                // 处理下载进度更新提示
                if (update != null) {
                    ToastSingletonUtils.showShort(String.format("%s : %d%%", update.cityName,
                            update.ratio));
                }
            }
            break;
            case MKOfflineMap.TYPE_NEW_OFFLINE:
                // 有新离线地图安装
                Log.d("OfflineDemo", String.format("add offlinemap num:%d", state));
                break;
            case MKOfflineMap.TYPE_VER_UPDATE:
                // 版本更新提示
                // MKOLUpdateElement e = mOffline.getUpdateInfo(state);

                break;
            default:
                break;
        }
    }
}

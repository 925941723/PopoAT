package com.light.zenghaitao.popoat.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

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
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.light.zenghaitao.popoat.R;
import com.light.zenghaitao.popoat.util.ToastSingletonUtils;
import com.litesuits.async.SimpleTask;

/**
 * Created by ly-zenghaitao on 2016/5/21.
 */

public class MainActivity extends Activity{

    private MapView mMapView = null;
    private BaiduMap baiduMap = null;

    //定位相关声明
    private LocationClient locationClient = null;
    //自定义图标
    private BitmapDescriptor mCurrentMarker = null;
    boolean isFirstLoc = true; //是否首次定位

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
    }

    private void init() {
        mMapView = (MapView) findViewById(R.id.bmapview);
        baiduMap = mMapView.getMap();
        //开启定位图层
        baiduMap.setMyLocationEnabled(true);

        locationClient = new LocationClient(getApplicationContext());//实例化LocationClient类
        locationClient.registerLocationListener(bdLocationListener);//注册监听函数
        this.setLocationOption();//设置定位参数
        locationClient.start();//开始定位

//        baiduMap = mMapView.getMap();
//
//        //设定中心点坐标 
//
//        LatLng cenpt = new LatLng(29.806651,121.606983);
//        //定义地图状态
//        MapStatus mMapStatus = new MapStatus.Builder()
//                .target(cenpt)
//                .zoom(18)
//                .build();
//        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
//
//
//        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//        //改变地图状态
//        baiduMap.setMapStatus(mMapStatusUpdate);
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
        option.setCoorType("bd0911");
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        option.setNeedDeviceDirect(true);

        locationClient.setLocOption(option);
    }

    private SimpleTask<String> task = new SimpleTask<String>() {
        @Override
        protected String doInBackground() {

            return "awdawdawdawdawdawdawd";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.v("cesawda", s);
            ToastSingletonUtils.showLong("dawdawd");
        }
    };
}

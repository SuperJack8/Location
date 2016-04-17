package com.example.baidu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class MainActivity extends AppCompatActivity {
    private ImageView image;
    private TextView mTv = null;
    public LocationClient mLocationClient = null; //初始化LocationClient类
    public MyLocationListener myListener = new MyLocationListener();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(myListener); //注册监听函数
        image=(ImageView)findViewById(R.id.image);  //初始化图片
        mTv = (TextView) findViewById(R.id.tv_loc_info); //初始化文本
        setLocationOption(); //定义setLocationOption()方法
        mLocationClient.start(); //执行定位
    }

    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //将获取的City赋值给txt
            /**
             *1.国家:location.getCountry()
             * 2.城市:location.getCity()
             * 3.区域(例：天河区)：location.getDistrict()
             * 4.地点(例：风信路)：location.getStreet()
             * 5.详细地址：location.getAddrStr()
             */
            mTv.setText(location.getCity()+location.getDistrict());
            Toast.makeText(MainActivity.this,"网络定位成功"+
                    location.getDirection(),Toast.LENGTH_LONG).show();
        }
        public void onReceivePoi(BDLocation arg0) {
        }
    }

    //执行onDestroy()方法，停止定位
    @Override
    public void onDestroy() {
        mLocationClient.stop();
        super.onDestroy();
    }

    //设置相关参数
    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); //打开gps
        option.setAddrType("all");//返回定位结果包含地址信息
        option.setPriority(LocationClientOption.NetWorkFirst); // 设置网络优先
        option.setPriority(LocationClientOption.GpsFirst);       //gps
        option.disableCache(true);//禁止启用缓存定位
        mLocationClient.setLocOption(option);
    }

}

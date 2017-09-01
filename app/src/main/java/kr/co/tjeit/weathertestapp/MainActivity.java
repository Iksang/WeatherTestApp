package kr.co.tjeit.weathertestapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import kr.co.tjeit.weathertestapp.util.ServerUtil;

public class MainActivity extends BaseActivity {


    private android.widget.TextView skyWeatherTxt;
    private android.widget.TextView temperatureCurrentTxt;
    private android.widget.TextView temperatureMaxTxt;
    private android.widget.TextView temperatureMinTxt;
    private android.widget.TextView winDirTxt;
    private android.widget.TextView winSpdTxt;
    private TextView stationTxt;
    private android.widget.EditText latEdt;
    private android.widget.EditText lonEdt;
    private android.widget.Button confirmBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        bindViews();
        setupEvent();
        setValues();



    }

    @Override
    public void setupEvent() {
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerUtil.getCurrentWeatherFromServer(mContext,latEdt.getText().toString(),lonEdt.getText().toString(), new ServerUtil.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {
//                서버에서 응답이 오면 자동으로 실행되는 부분.
//                json이 재료로 날라오는걸 분석해서 화면에 뿌려주기.
                        JSONObject weather = null;
                        try {
                            weather = json.getJSONObject("weather");
                            JSONArray minutely = weather.getJSONArray("minutely");
                            JSONObject firstObject = minutely.getJSONObject(0);
                            JSONObject station = firstObject.getJSONObject("station");
                            stationTxt.setText(station.getString("name"));
                            JSONObject sky = firstObject.getJSONObject("sky");
                            skyWeatherTxt.setText(sky.getString("name"));
                            JSONObject temperature = firstObject.getJSONObject("temperature");
                            temperatureCurrentTxt.setText(String.format(Locale.KOREA," 현재 %.1f ℃",Float.parseFloat(temperature.getString("tc"))));
                            temperatureMaxTxt.setText(String.format(Locale.KOREA," 최고 %.1f ℃",Float.parseFloat(temperature.getString("tmax"))));
                            temperatureMinTxt.setText(String.format(Locale.KOREA," 최저 %.1f ℃",Float.parseFloat(temperature.getString("tmin"))));
                            JSONObject wind = firstObject.getJSONObject("wind");
                            winDirTxt.setText(wind.getString("wdir"));
                            winSpdTxt.setText(wind.getString("wspd") + "m");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

    }

    @Override
    public void setValues() {

//        try {
//            JSONObject json = new JSONObject(jsonString);
//////            1. 날씨 보여주는 동네 이름 추출 : String 변수 저장.
////            JSONObject weather = json.getJSONObject("weather");
////            JSONArray minutely = weather.getJSONArray("minutely");
//////            배열의 첫번째 0번칸 JSONObject 코드
////            JSONObject firstObject = minutely.getJSONObject(0);
////            JSONObject station = firstObject.getJSONObject("station");
////            String name = station.getString("name");
////            Toast.makeText(mContext, "사는 동네 : "+name, Toast.LENGTH_SHORT).show();
//
////            result -> message 내용을 토스트로 출력.
//            JSONObject result = json.getJSONObject("result");
//            String message = result.getString("message");
//
//            Toast.makeText(mContext, "message : " + message, Toast.LENGTH_SHORT).show();
//
//            JSONObject weather = json.getJSONObject("weather");
//            JSONArray minutely = weather.getJSONArray("minutely");
//            JSONObject firstObject = minutely.getJSONObject(0);
//
//            JSONObject station = firstObject.getJSONObject("station");
//            stationTxt.setText(station.getString("name"));
//
//            JSONObject sky = firstObject.getJSONObject("sky");
//            skyWeatherTxt.setText(sky.getString("name"));
//
//            JSONObject temperature = firstObject.getJSONObject("temperature");
//            temperatureCurrentTxt.setText(String.format(Locale.KOREA," 현재 %.1f ℃",Float.parseFloat(temperature.getString("tc"))));
//            temperatureMaxTxt.setText(String.format(Locale.KOREA," 최고 %.1f ℃",Float.parseFloat(temperature.getString("tmax"))));
//            temperatureMinTxt.setText(String.format(Locale.KOREA," 최저 %.1f ℃",Float.parseFloat(temperature.getString("tmin"))));
//
//            JSONObject wind = firstObject.getJSONObject("wind");
//            winDirTxt.setText(wind.getString("wdir"));
//            winSpdTxt.setText(wind.getString("wspd") + "m");
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public void bindViews() {
        this.winSpdTxt = (TextView) findViewById(R.id.winSpdTxt);
        this.winDirTxt = (TextView) findViewById(R.id.winDirTxt);
        this.temperatureMinTxt = (TextView) findViewById(R.id.temperatureMinTxt);
        this.temperatureMaxTxt = (TextView) findViewById(R.id.temperatureMaxTxt);
        this.temperatureCurrentTxt = (TextView) findViewById(R.id.temperatureCurrentTxt);
        this.skyWeatherTxt = (TextView) findViewById(R.id.skyWeatherTxt);
        this.stationTxt = (TextView) findViewById(R.id.stationTxt);
        this.confirmBtn = (Button) findViewById(R.id.confirmBtn);
        this.lonEdt = (EditText) findViewById(R.id.lonEdt);
        this.latEdt = (EditText) findViewById(R.id.latEdt);

    }
}

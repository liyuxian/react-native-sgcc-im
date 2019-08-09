
package com.sgcc.im;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

public class RNSgccImModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNSgccImModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNSgccIm";
  }
  /**
   js调用原生页面跳转
   */
  @ReactMethod
  public void startActivityFromJS(String activity,String params) {
   
     try{
            Activity currentActivity = getCurrentActivity();
            if(null!=currentActivity){
                Class toActivity = Class.forName(activity);
                Intent intent = new Intent(currentActivity,toActivity);
                intent.putExtra("params", params);
                currentActivity.startActivity(intent);
            }
        }catch(Exception e){
           Toast.makeText( this.reactContext, "message ==  eee "+e.toString(), 1).show();
        }
  
  }
  
}
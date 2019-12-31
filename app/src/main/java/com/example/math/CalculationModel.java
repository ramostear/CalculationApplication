package com.example.math;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import java.util.Random;

public class CalculationModel extends AndroidViewModel {

    private SavedStateHandle handle;

    private static String KEY_HIGH_SCORE = "key_high_score";            //最高记录
    private static String KEY_LEFT_NUMBER = "key_left_number";          //第一个运算数
    private static String KEY_RIGHT_NUMBER = "key_right_number";        //第二个运算数
    private static String KEY_OPERATOR = "key_operator";                //运算操作符
    private static String KEY_ANSWER = "key_answer";                    //运算答案
    private static String KEY_SAVE_SP_DATA_NAME = "save_sp_data_name";  //保存的数据
    private static String KEY_CURRENT_SCORE = "key_current_score";      //当前得分

    boolean win_flag = false;

    public CalculationModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        if(!handle.contains(KEY_HIGH_SCORE)){
            SharedPreferences preferences = getApplication().getSharedPreferences(KEY_SAVE_SP_DATA_NAME, Context.MODE_PRIVATE);
            handle.set(KEY_HIGH_SCORE,preferences.getInt(KEY_HIGH_SCORE,0));
            handle.set(KEY_LEFT_NUMBER,0);
            handle.set(KEY_RIGHT_NUMBER,0);
            handle.set(KEY_OPERATOR,"+");
            handle.set(KEY_ANSWER,0);
            handle.set(KEY_CURRENT_SCORE,0);
        }
        this.handle = handle;
    }

    /**
     * 获取第一个运算数
     * @return
     */
    public MutableLiveData<Integer> getLeftNumber(){
        return handle.getLiveData(KEY_LEFT_NUMBER);
    }

    /**
     * 获取第二个运算数
     * @return
     */
    public MutableLiveData<Integer> getRightNumber(){
        return handle.getLiveData(KEY_RIGHT_NUMBER);
    }

    /**
     * 获取运算符
     * @return
     */
    public MutableLiveData<String> getOperation(){
        return handle.getLiveData(KEY_OPERATOR);
    }

    /**
     * 获取最高记录
     * @return
     */
    public MutableLiveData<Integer> getHighSocre(){
        return handle.getLiveData(KEY_HIGH_SCORE);
    }

    /**
     * 获取当前得分
     * @return
     */
    public MutableLiveData<Integer> getCurrentScore(){
        return handle.getLiveData(KEY_CURRENT_SCORE);
    }

    /**
     * 获取答案
     * @return
     */
    public MutableLiveData<Integer> getAnswer(){
        return handle.getLiveData(KEY_ANSWER);
    }

    /**
     * 生成题目
     */
    public void generator(){
        int level = 20;
        Random random = new Random();
        int x,y;
        x = random.nextInt(level)+1;
        y = random.nextInt(level)+1;
        if(x%2==0){
            getOperation().setValue("+");
            if(x > y){
                getAnswer().setValue(x);
                getLeftNumber().setValue(y);
                getRightNumber().setValue(x-y);
            }else{
                getAnswer().setValue(y);
                getLeftNumber().setValue(x);
                getRightNumber().setValue(y-x);
            }
        }else{
            getOperation().setValue("-");
            if(x>y){
                getAnswer().setValue(x-y);
                getLeftNumber().setValue(x);
                getRightNumber().setValue(y);
            }else{
                getAnswer().setValue(y-x);
                getLeftNumber().setValue(y);
                getRightNumber().setValue(x);
            }
        }
    }

    /**
     * 保存
     */
    @SuppressWarnings("ConstantConditions")
    public void save(){
        SharedPreferences preferences = getApplication().getSharedPreferences(KEY_SAVE_SP_DATA_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_HIGH_SCORE,getHighSocre().getValue());
        editor.apply();
    }

    /**
     * 判断是否要更新最高记录
     */
    @SuppressWarnings("ConstantConditions")
    public void answerCorrect(){
        getCurrentScore().setValue(getCurrentScore().getValue()+1);
        if(getCurrentScore().getValue() > getHighSocre().getValue()){
            getHighSocre().setValue(getCurrentScore().getValue());
            win_flag = true;
        }
        generator();
    }
}

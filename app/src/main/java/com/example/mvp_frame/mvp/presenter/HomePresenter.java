package com.example.mvp_frame.mvp.presenter;

import android.util.Log;

import com.example.mvp_frame.app.App;
import com.example.mvp_frame.base.BasePresenter;

import com.example.mvp_frame.callback.IDataCallBack;
//import com.example.mvp_frame.di.componend.DaggerHomeComponent;
import com.example.mvp_frame.mvp.model.RxOperateImpl;
import com.example.mvp_frame.mvp.ui.fragment.HomeFragment;

import java.io.IOException;
import java.util.IdentityHashMap;

import javax.inject.Inject;
import javax.security.auth.login.LoginException;

import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;

//他是P层的实现类继承了BasePresenter，重写顶层（IBasePresenter）的两个方法
//可以在BasePresenter类中重写这两个方法，在这里可以想用哪个就调用那个
public class HomePresenter<T> extends BasePresenter {

//    //获取AppComponent类提供的okhttpclient对象
//    @Inject
//    OkHttpClient okHttpClient;

    private RxOperateImpl mImp;
    private HomeFragment mHomeFragment;

    //获取AppComponent类提供的inject对象，通过构造把AppComponent类的inject方法初始化
    public HomePresenter(HomeFragment homeFragment){
        this.mHomeFragment=homeFragment;
        mImp=new RxOperateImpl();
    }

    //要把索引值传到Fragment里边，要根据不同的索引值加载不同的数据，所以需要调用start把索引值传进去
    //通过这个方法向M层请求数据
    @Override
    public void start(Object obj) {
        super.start(obj);
        if (obj instanceof Integer) {
            //将obj强转成integer类型
            Integer type = (Integer) obj;

            //通过switch判断加载不同的数据
            switch (type) {
                case 0:
                    mImp.requestData("https://wanandroid.com/wxarticle/chapters/json ", new IDataCallBack<T>() {
                        @Override
                        public void onStateSucess(T t) {
                             if (t instanceof ResponseBody){
                                 ResponseBody body=(ResponseBody) t;
                                 String jsonStr=null;
                                 try {
                                     jsonStr= body.string();
                                     Log.e("TAG",jsonStr+"BasePresenter解析");
                                     //交给V层更新UI   Gson解析
                                     mHomeFragment.stateScouess(t);
                                 } catch (IOException e) {
                                     e.printStackTrace();
                                     //异常交给V层更新UI
                                     mHomeFragment.stateError(e.getMessage());
                                 }
                                     }
                        }

                        @Override
                        public void onStateError(String msg) {
                            //异常交给V层更新UI
                            mHomeFragment.stateError(msg);
                            Log.e("TAG",msg+"走的异常方法");
                        }

                        @Override
                        public void onResponseDisposable(Disposable disposable) {
                            //获取网路开关
                            addDisposable(disposable);
                        }
                    });
                    Log.e("TAG","HomePresenter类的第一个Fragment开始加载数据了");
                    break;
                case 1:
                    Log.e("TAG","HomePresenter类的第二个Fragment开始加载数据了");
                    break;
                case 2:
                    Log.e("TAG","HomePresenter类的第三个Fragment开始加载数据了");
                    break;
                case 3:
                    mImp.requestData("https://wanandroid.com/wxarticle/chapters/json ", new IDataCallBack<T>() {
                        @Override
                        public void onStateSucess(T t) {
                            if (t instanceof ResponseBody){
                                ResponseBody body=(ResponseBody) t;
                                String jsonStr=null;
                                try {
                                    jsonStr= body.string();
                                    Log.e("TAG",jsonStr+"BasePresenter解析");
                                    //交给V层更新UI   Gson解析
                                    mHomeFragment.stateScouess(t);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    //异常交给V层更新UI
                                    mHomeFragment.stateError(e.getMessage());
                                }
                            }
                        }

                        @Override
                        public void onStateError(String msg) {
                            //异常交给V层更新UI
                            mHomeFragment.stateError(msg);
                        }

                        @Override
                        public void onResponseDisposable(Disposable disposable) {
                            //获取网路开关
                            addDisposable(disposable);
                        }
                    });
                    Log.e("TAG","HomePresenter类的第四个Fragment开始加载数据了");
                    break;
            }
        }
    }
}

package com.dhw.fresco;

import android.app.Application;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * 在加载图片之前，你必须初始化Fresco类。
 * 你只需要调用Fresco.initialize一次即可完成初始化，在 Application 里面做这件事再适合不过了（如下面的代码）.
 * 注意多次的调用初始化是无意义的。
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}

package com.dhw.fresco;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
Fresco
 优点：
 1,使用简便,学习成本低
 2,十分强大，使用起来非常流畅，内存管理不用愁，不用担心OOM。
 3,自带加载时淡入效果,开发起来不费劲.
 4,图片加载时可在布局中直接设置加载动画等等,代码量大大减少
 缺点：
 1,必须使用fresco自定义的控件,如果需求更换,想要更换其他图片加载框架会有一定的麻烦,比如必须要改布局
 2,方法数太多,多达近4k方法,对于比较大的项目来说简直是压死骆驼的最后一个稻草,整项目方法数超过65k,不 得不分包.而且打包之后整个项目整整多了3M.确实大得很.
 3,必须全套使用fresco的图片加载,否则连获取简简单单的一个缓存中的bitmap都异常费劲
 总结:
 如果自己的项目不是社交软件,涉及到特别多图片加载的话,还是慎用吧.
 当然,在我的印象中,fresco就是你项目中图片加载处理的专业管家,几乎一切繁杂的事情它都帮你处理妥当,用起来简直贴心.
 */
public class MainActivity extends AppCompatActivity {
    private Animatable animation;
    private SimpleDraweeView myFrescoGif;
    private SimpleDraweeView fresco_rotate_view;
    private Button btFrescoStartAnim;
    private Button btFrescoAskImg;
    private Button btFrescoStopAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myFrescoGif = (SimpleDraweeView) findViewById(R.id.my_fresco_gif);
        btFrescoAskImg = (Button) findViewById(R.id.bt_fresco_askImg);
        btFrescoStartAnim = (Button) findViewById(R.id.bt_fresco_startAnim);
        btFrescoStopAnim = (Button) findViewById(R.id.bt_fresco_stopAnim);
        fresco_rotate_view = (SimpleDraweeView) findViewById(R.id.fresco_rotate_view);
        initView();

        //点击事件，请求加载Gif动画图片
        btFrescoAskImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 图片地址
                Uri uri = Uri.parse("http://qq.yh31.com/tp/zjbq/201711031402272777.gif");
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setAutoPlayAnimations(false)//设置为true将循环播放Gif动画
                        .setOldController(myFrescoGif.getController())
                        .build();

                // 设置控制器
                myFrescoGif.setController(controller);
            }
        });

        //开始Gif图片动画
        btFrescoStartAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animation = myFrescoGif.getController().getAnimatable();

                if (animation != null && !animation.isRunning()) {
                    animation.start();
                }
            }
        });

        //停止Gif图片动画
        btFrescoStopAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (animation != null && animation.isRunning()) {
                    animation.stop();
                }
            }
        });
    }

    private void initView() {
        /**
         * 1. 简单使用 SimpleDraweeView 加载网络图片
         * 仅仅是想简单下载一张网络图片，在下载完成之前，显示一张占位图，那么简单使用 SimpleDraweeView 即可。
         * 错误链接：http://img2.imgtn.bdimg.com/it/u=11890266177,681537567&fm=27&gp=0.jpg
         */
        SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.my_Imageview);
        Uri uri = Uri.parse("http://img2.imgtn.bdimg.com/it/u=1890266177,681537567&fm=27&gp=0.jpg");
        draweeView.setImageURI(uri);

        /**
         * //2. Drawees的使用
         * Drawees 负责图片的呈现。它由三个元素组成，有点像MVC模式。
         * 2.1 DraweeView 继承于 View, 负责图片的显示。可使用使用 XML属性来达到各式各样的效果
         * 2.2 DraweeHierarchy 用于组织和维护最终绘制和呈现的 Drawable 对象，相当于MVC中的M。
         * 2.3 DraweeController 负责和 image loader 交互（ Fresco 中默认为 image pipeline, 当然你也可以指定别的），可以创建一个这个类的实例，来实现对所要显示的图片做更多的控制。
         * 2.4 DraweeControllers 由 DraweeControllerBuilder 采用 Builder 模式创建，创建之后，不可修改。
         */

        //创建DraweeController
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                //加载的图片地址
                .setUri(uri)
                //设置点击重试是否开启
                .setTapToRetryEnabled(true)
                //设置旧的Controller
                .setOldController(draweeView.getController())
                //构建
                .build();

        //设置DraweeController（重复加载4次还是没有加载出来的时候才会显示 failureImage(失败图) 的图片）
        draweeView.setController(controller);
    }

    //修改图片
    public void resizeImage(View view) {
        int width = 50;
        int height = 50;
        Uri uri = Uri.parse("http://c.hiphotos.baidu.com/image/pic/item/962bd40735fae6cd21a519680db30f2442a70fa1.jpg");
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(width, height)).build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setOldController(fresco_rotate_view.getController())
                .setImageRequest(request)
                .build();
        fresco_rotate_view.setController(controller);
    }

    //旋转图片
    public void rotateImage(View view) {
        Uri uri2 = Uri.parse("http://c.hiphotos.baidu.com/image/pic/item/962bd40735fae6cd21a519680db30f2442a70fa1.jpg");
        //设置旋转角度
        RotationOptions rotationOptions = RotationOptions.forceRotation(RotationOptions.ROTATE_90);
        ImageRequest request1 = ImageRequestBuilder.newBuilderWithSource(uri2)
                .setRotationOptions(RotationOptions.autoRotate())
                .setRotationOptions(rotationOptions)            //旋转的时候要使用RotationOptions类
                .build();                                       //但貌似Fresco在旋转的功能上不是很好
        DraweeController controller1 = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request1)
                .setOldController(fresco_rotate_view.getController()).build();
        fresco_rotate_view.setController(controller1);
    }
}

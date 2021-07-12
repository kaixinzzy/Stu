package com.zzy.glide;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AppCompatActivity;

// 郭森 Glide系列 https://blog.csdn.net/sinyu890807/column/info/15318
//使用 https://www.jianshu.com/p/791ee473a89b
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private String imgUrl = "http://cn.bing.com/az/hprichbg/rb/TOAD_ZH-CN7336795473_1920x1080.jpg";
    private ImageView mIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIv = findViewById(R.id.iv);


    }

    private void loadNetImage() {
        Glide.with(this)
                .load(imgUrl)
//                .asGif()                                        // 只加载git图片，如果加载图片非gif图片则直接加载错误占位图
                .asBitmap()                                     // 只加载静态图片，如果是gif图片则只加载第一帧

                .placeholder(R.drawable.ic_launcher_background) // 占位图(图片未加载完成前显示)
                .error(R.drawable.ic_launcher_background)       // 占位图（图片加载失败时显示）
                .diskCacheStrategy(DiskCacheStrategy.NONE)      // 关闭Glide的硬盘缓存机制
                .skipMemoryCache(true)                          // 关闭Glide的内存缓存机制

                .override(100,100)               // 指定图片大小
                .into(mIv);

        //DiskCacheStrategy.NONE： 表示不缓存任何内容。
        //DiskCacheStrategy.SOURCE： 表示只缓存原始图片。
        //DiskCacheStrategy.RESULT： 表示只缓存转换过后的图片（默认选项）。
        //DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。

        // 预加载图片
        Glide.with(this)
                .load(imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .preload(100,100);
        // 使用预加载图片
        Glide.with(this)
                .load(imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mIv);
    }

    // 图片下载（子线程）
    private void asyncDownloadImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Context context = getApplicationContext();
                // 在此线程中下载图片
                FutureTarget<File> target = Glide.with(context)
                        .load(imgUrl)
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                try {
                    // 得到图片文件
                    final File imageFile = target.get();
                    imageFile.getPath();// 获取下载图片的本地路径
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 加载本地图片
                            Glide.with(context).load(imageFile).into(mIv);
                        }
                    });
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // 图片下载（主线程）
    private void syncDownloadImage() {
//        Glide.with(this)
//                .load(imgUrl)
//                .downloadOnly();
    }

    // 加载本地图片
    private void loadLocalImage() {
        File file = new File(getExternalCacheDir() + "/image.jpg");
        Glide.with(this).load(file).into(mIv);
    }

    // 加载资源图片
    private void loadResourceImage() {
        int resourceImage = R.drawable.ic_launcher_background;
        Glide.with(this).load(resourceImage).into(mIv);
    }

    // 加载字节流图片
    private void loadBytesImage(){
        byte[] image = getImageBytes();
        Glide.with(this).load(image).into(mIv);
    }

    // 加载Uri图片
    private void loadUriImage() {
        Uri imageUri = getImageUri();
        Glide.with(this).load(imageUri).into(mIv);
    }


    private Uri getImageUri() {
        return null;
    }

    private byte[] getImageBytes() {
        return new byte[]{};
    }



}
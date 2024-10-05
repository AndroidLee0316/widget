package com.pasc.lib.demo.widget.banner;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.pasc.lib.demo.R;
import com.pasc.lib.widget.banner.animations.DescriptionAnimation;
import com.pasc.lib.widget.banner.SliderLayout;
import com.pasc.lib.widget.banner.indicators.PagerIndicator;
import com.pasc.lib.widget.banner.slidertypes.BaseSliderView;
import com.pasc.lib.widget.banner.slidertypes.TextSliderView;
import com.pasc.lib.widget.banner.tricks.ViewPagerEx;
import com.pasc.lib.widget.banner.imageloader.ImageLoader;
import com.pasc.lib.widget.banner.imageloader.impl.GlideImpl;

import java.util.HashMap;

@Route(path = "/Demo/Containers/SliderLayout")
public class MainActivity extends Activity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    private SliderLayout sliderLayout;
    private PagerIndicator pagerIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_banner);
        sliderLayout = (SliderLayout) findViewById(R.id.slider);
        sliderLayout.setImgRadius(20);

        pagerIndicator = (PagerIndicator)findViewById(R.id.custom_indicator);

        initSliderLayout2();

        ListView l = (ListView) findViewById(R.id.transformers);
        l.setAdapter(new TransformerAdapter(this));
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sliderLayout.setPresetTransformer(((TextView) view).getText().toString());
                Toast.makeText(MainActivity.this, ((TextView) view).getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 测试单张图片时不更新的问题
    private void initSliderLayout3() {
        sliderLayout.setEnableHandSlide(false);
        sliderLayout.setAutoCycle(false);
        sliderLayout.setIndicatorVisible(false);
        sliderLayout.setImages(new int[]{R.drawable.ic_main_page_logo});

        setImageUrlsTiming();
    }

    private void setImageUrlsTiming() {
        System.out.println("设置图片的url");
        sliderLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                System.out.println("正在设置图片的url");
                sliderLayout.setAutoCycle(true);
                sliderLayout.setIndicatorVisible(true);
                sliderLayout.setEnableHandSlide(true);
                String[] urls = new String[]{
                        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537418291048&di=3ccea8ac89f45b0e8eec7394b9f08250&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201411%2F18%2F20141118142710_SPnCu.jpeg",
                        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537419880263&di=64a561f5e40969670a85699e4848ba10&imgtype=0&src=http%3A%2F%2Fpic13.photophoto.cn%2F20091121%2F0005018307819081_b.jpg",
                        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537419963735&di=6bf2ce13161d6a5ac561c1f898331f66&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D3586634344%2C3895758914%26fm%3D214%26gp%3D0.jpg",
                        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537419980811&di=76a7ab128a9d518e4b434f2667afc3a6&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201506%2F27%2F20150627205613_PjhFu.jpeg"
                };
                sliderLayout.setImageUrls(urls);
                sliderLayout.setOnItemClickListener(new SliderLayout.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Toast.makeText(MainActivity.this, "点击了图片", Toast.LENGTH_LONG).show();
                    }
                });
                //setImageUrlsTiming();
            }
        }, 3000);
    }


    private void initSliderLayout2() {
        String[] urls = new String[]{
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537418291048&di=3ccea8ac89f45b0e8eec7394b9f08250&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201411%2F18%2F20141118142710_SPnCu.jpeg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537419880263&di=64a561f5e40969670a85699e4848ba10&imgtype=0&src=http%3A%2F%2Fpic13.photophoto.cn%2F20091121%2F0005018307819081_b.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537419963735&di=6bf2ce13161d6a5ac561c1f898331f66&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D3586634344%2C3895758914%26fm%3D214%26gp%3D0.jpg",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537419980811&di=76a7ab128a9d518e4b434f2667afc3a6&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201506%2F27%2F20150627205613_PjhFu.jpeg"
        };
        sliderLayout.setAutoCycle(true);
        sliderLayout.setIndicatorVisible(true);
        sliderLayout.setCustomIndicator(pagerIndicator);
        sliderLayout.setEnableHandSlide(true);
        sliderLayout.setImageUrls(urls);

        sliderLayout.setEnableHandSlide(true);
        sliderLayout.setOnItemClickListener(new SliderLayout.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, "点击了" + position, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initSliderLayout() {
        HashMap<String, String> url_maps = new HashMap<String, String>();
        url_maps.put("Hannibal", "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg");
        url_maps.put("Big Bang Theory", "http://tvfiles.alphacoders.com/100/hdclearart-10.png");
        url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
        url_maps.put("Game of Thrones", "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");

        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Hannibal", R.drawable.hannibal);
        file_maps.put("Big Bang Theory", R.drawable.bigbang);
        file_maps.put("House of Cards", R.drawable.house);
        file_maps.put("Game of Thrones", R.drawable.game_of_thrones);

        ImageLoader imageLoader = new GlideImpl(this);
        for (String name : file_maps.keySet()) {
            // 有文字显示的
            TextSliderView textSliderView = new TextSliderView(this);

            // 默认的，没有文字显示的
            // DefaultSliderView defaultSliderView = new DefaultSliderView(this);

            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType("Fit")
                    .setOnSliderClickListener(this);

            textSliderView.setImageLoader(imageLoader);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.PagerAnimation.Accordion);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setPagerAnimationDuration(4000);
        sliderLayout.addOnPageChangeListener(this);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}

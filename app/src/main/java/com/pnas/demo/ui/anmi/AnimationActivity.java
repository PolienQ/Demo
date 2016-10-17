package com.pnas.demo.ui.anmi;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import com.pnas.demo.R;
import com.pnas.demo.base.BaseActivity;
import com.pnas.demo.utils.BitmapUtils;
import com.pnas.demo.view.dialog.RemindDialog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/***********
 * @author pans
 * @date 2016/2/26
 * @describ
 */
public class AnimationActivity extends BaseActivity implements View.OnClickListener {

    private static final String mHtmlString = "<p> \t<span style=\"font-size:12px;\"><span style=\"font-family:arial,helvetica,sans-serif;\">【产品内容】： </span></span></p> <p style=\"margin-left: 40px;\"> \t<span style=\"font-size:12px;\"><span style=\"font-family:arial,helvetica,sans-serif;\">梅香咸蛋55g*4</span></span></p> <p style=\"margin-left: 40px;\">   <span style=\"font-size:12px;\"><span style=\"font-family:arial,helvetica,sans-serif;\">五芳斋粽子（美味鲜肉粽）100g*2</span></span></p> <p style=\"margin-left: 40px;\">   <span style=\"font-size:12px;\"><span style=\"font-family:arial,helvetica,sans-serif;\">五芳斋粽子（新疆红枣粽）100g*2</span></span></p> <p style=\"margin-left: 40px;\">   <span style=\"font-size:12px;\"><span style=\"font-family:arial,helvetica,sans-serif;\">东北小米400g</span></span></p> <p style=\"margin-left: 40px;\">   <span style=\"font-size:12px;\"><span style=\"font-family:arial,helvetica,sans-serif;\">八宝米400g</span></span></p> <p style=\"margin-left: 40px;\">   <span style=\"font-size:12px;\"><span style=\"font-family:arial,helvetica,sans-serif;\">丑耳（银耳）60g</span></span></p> <p>   <span style=\"font-size:12px;\"><span style=\"font-family:arial,helvetica,sans-serif;\">【配送范围】：全国</span></span></p>";

    private static final String body = "<div class=\"content-inner\"> <div color:=\"\" font-family:=\"\" font-size:=\"\" line-height:=\"\" microsoft=\"\" padding:=\"\" style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: 'Microsoft YaHei', 微软雅黑, SimHei, 黑体, Verdana, Arial, Helvetica, sans-serif; font-size: 13.3333px; line-height: 20px; white-space: nowrap;\">   【端】杯递盏庆佳节，【午】月艾粽有奇香</div> <div color:=\"\" font-family:=\"\" font-size:=\"\" line-height:=\"\" microsoft=\"\" padding:=\"\" style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: 'Microsoft YaHei', 微软雅黑, SimHei, 黑体, Verdana, Arial, Helvetica, sans-serif; font-size: 13.3333px; line-height: 20px; white-space: nowrap;\">   【快】将闲愁都抛却，【乐】而谈笑享天伦</div> <div color:=\"\" font-family:=\"\" font-size:=\"\" line-height:=\"\" microsoft=\"\" padding:=\"\" style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: 'Microsoft YaHei', 微软雅黑, SimHei, 黑体, Verdana, Arial, Helvetica, sans-serif; font-size: 13.3333px; line-height: 20px; white-space: nowrap;\">   在中国传统佳节 - 端午节来临之际给您送上最真挚的节日祝福，祝您节日快乐！</div> <div color:=\"\" font-family:=\"\" font-size:=\"\" line-height:=\"\" microsoft=\"\" padding:=\"\" style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: 'Microsoft YaHei', 微软雅黑, SimHei, 黑体, Verdana, Arial, Helvetica, sans-serif; font-size: 13.3333px; line-height: 20px; white-space: nowrap;\">   我们精心准备了几款精美好礼，供您根据喜好任意选择一款。</div> <div color:=\"\" font-family:=\"\" font-size:=\"\" line-height:=\"\" microsoft=\"\" padding:=\"\" style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: 'Microsoft YaHei', 微软雅黑, SimHei, 黑体, Verdana, Arial, Helvetica, sans-serif; font-size: 13.3333px; line-height: 20px; white-space: nowrap;\">   所有礼物将通过快递直接寄送给您，您可以点击下列图片，了解各礼物的详细内容及可以配送的城市。</div> <div color:=\"\" font-family:=\"\" font-size:=\"\" line-height:=\"\" microsoft=\"\" padding:=\"\" style=\"margin: 0px; padding: 0px; color: rgb(0, 0, 0); font-family: 'Microsoft YaHei', 微软雅黑, SimHei, 黑体, Verdana, Arial, Helvetica, sans-serif; font-size: 13.3333px; line-height: 20px; white-space: nowrap;\"> \t请在选择节日礼物的时候，留下收货人的姓名、地址、电话等准确信息，以便您的礼物可以妥善投递。</div></div>";

    private static final String zhiHuBody = "<div class=\"main-wrap content-wrap\"> <div class=\"headline\">  <div class=\"img-place-holder\"></div>    </div>  <div class=\"content-inner\">     <div class=\"question\"> <h2 class=\"question-title\">如何以「小店打烊了，客官请回吧」开头写一篇小故事？</h2>  <div class=\"answer\">  <div class=\"meta\"> <img class=\"avatar\" src=\"http://pic3.zhimg.com/9c173502d4da48949052405d95efa3d2_is.jpg\"> <span class=\"author\">芒果，</span><span class=\"bio\">赐我一场春秋大梦。</span> </div>  <div class=\"content\"> <p>&ldquo;小店打烊了，客官请回吧。&rdquo;姑娘挡着门，就是不让小道师进去。</p>\n" +
            " <p>小道师此时非常无奈，第一次下山，好不容易找到一间客栈想要落脚，却还被拦在了门外。</p>\n" +
            " <p>小道师从五岁起跟着师父苦学阴阳术法十年，终于在这天，师父让他下山，想考验一下他这十年来所学的深浅。</p>\n" +
            " <p>&ldquo;灵符，有。木剑，有。罗盘，有&hellip;&hellip;&rdquo;</p>\n" +
            " <p>小道师一边赶路，一边数着自己行囊里的物品。他脑海里还回荡着师父的要求：下山时间为期三个月，只有抓到一只妖怪，才算完成考验。</p>\n" +
            " <p>可是到哪儿去找妖怪呢，小道师很苦恼。毕竟可没有一只妖怪会傻到把尾巴露在外边。</p>\n" +
            " <p>赶路的时间总是过得很快，太阳已经渐渐往西边沉去。小道师的肚子也开始不给面子的叫唤了起来，于是他决定先到山下找间客栈投宿一晚。</p>\n" +
            " <p>荒郊野岭的，人迹罕至，等他终于找到客栈的时候，月亮早已高悬空中。他举手敲门，屋里人未到，声先至：&ldquo;来啦来啦！&rdquo;</p>\n" +
            " <p>门嘎吱一声打开了。看清对方的瞬间，门里门外的两人都愣住了，空气中弥漫着一股迷之沉默。</p>\n" +
            " <p>开门的是个小姑娘，穿着朴素的居家服，头上盘着一条红色的头巾。是个很漂亮的姑娘，小道师心想。</p>\n" +
            " <p>反观姑娘反而瞳孔收缩，表情僵硬，如同一副见了鬼的模样。</p>\n" +
            " <p>然后，就发生了开头的对话。</p>\n" +
            " <p>&ldquo;请问店家，还有吃的吗？我就吃点儿东西，不留宿也没关系的。&rdquo;小道师干咳了一声，指了指肚子示意姑娘自己已经快要撑不住了。</p>\n" +
            " <p>&ldquo;啊，有，等等，没有&hellip;&hellip;我们打烊了！你不能进来！&rdquo;姑娘明显很慌，她张开双臂，一副万夫莫开的模样。</p>\n" +
            " <p>&ldquo;是&hellip;&hellip;谁呀&hellip;&hellip;姐姐？&rdquo;俩人正僵持着呢，姑娘的背后突然探了一个脑袋，是个长相讨喜的少年。</p>\n" +
            " <p>嗯，如果不是他头顶那长长的兔耳朵实在太过明显的话，应该没人会怀疑他不是人类。</p>\n" +
            " <p>&ldquo;妖怪！&rdquo;小道师惊叫了一声，手马上往他的行囊掏去。</p>\n" +
            " <p>&ldquo;等等，这是个误会！&rdquo;姑娘大急。</p>\n" +
            " <p>&ldquo;什么误会，他刚刚叫你姐姐，难道你也是！&rdquo;</p>\n" +
            " <p>&ldquo;这真的是误会！！&rdquo;</p>\n" +
            " <p>&hellip;&hellip;</p>\n" +
            " <p>&ldquo;所以，这就是你们姐弟开客栈的原因，体验人类的生活？&rdquo;</p>\n" +
            " <p>小道师扒了一口饭，一边无奈地问道。兔子姑娘和兔耳少年都在他对面正襟危坐，一副委屈的样子。</p>\n" +
            " <p>&ldquo;是的。但是请你放心，我们没有恶意，而且只吃素！&rdquo;兔子姑娘摸了摸少年的头小声解释着，一边小心观察小道师的反应，生怕他吃完翻脸不认人。</p>\n" +
            " <p>小道师抹了抹嘴，看了看眼眶里挂着泪珠的兔耳少年，忽然觉得有点儿惆怅。</p>\n" +
            " <p>现在是不愁着找不到妖怪了，但他发觉自己好像有点儿下不去手。</p>\n" +
            " <p>小道师就那么坐着，也不说话，只是皱着眉头，心里天人交战。兔子姑娘和兔耳少年看着他这严肃的模样，大气也不敢出一口。</p>\n" +
            " <p>&ldquo;算了，反正还有时间&hellip;&hellip;&rdquo;终于，小道师好像下定了什么决心，他喃喃自语着，一边站了起来。</p>\n" +
            " <p>兔子姑娘立刻把手护在了兔耳少年的胸前，俩兔都一副如临大敌的模样。</p>\n" +
            " <p>&ldquo;你该不会以后真要让他出来接待客人吧？&rdquo;</p>\n" +
            " <p>&ldquo;是啊，有什么问题吗？他也要明白什么是人类嘛！&rdquo;兔子姑娘一副理所当然的模样。</p>\n" +
            " <p>看着她一脸天真的表情，小道师已经不怀疑他们有能力危害平民了。</p>\n" +
            " <p>&ldquo;我们人类的头上，可没有兔耳朵！！&rdquo;</p>\n" +
            " <p>空荡冷清的客栈中，回荡着小道师无可奈何的大叫。</p>\n" +
            " <p>翌日，小道师整理好行李站在客栈门口和姐弟俩告别。兔耳少年此时头上已经没有了长长的耳朵，取而代之的是正常人类的双耳。在他头发里，还能看到若隐若现的一张小符。</p>\n" +
            " <p>&ldquo;在他可以完全化成人形之前，千万记得不要把符弄坏。&rdquo;小道师嘱咐着。</p>\n" +
            " <p>兔子姑娘高兴地应了声&ldquo;哎&rdquo;，还在笑着呢，她头顶的耳朵嗖地就窜了出来！</p>\n" +
            " <p>&ldquo;还有不要一高兴就散功！你耳朵又出来了！&rdquo;小道师无奈的大声说道，一脸的恨铁不成钢。他想起昨晚刚帮兔耳少年隐藏起耳朵的时候，兔子姑娘高兴得收不拢耳朵的一幕。</p>\n" +
            " <p>兔子姑娘愣了一下，脸瞬间红得像个苹果，她羞涩地对着小道师笑了笑，头顶耳朵又嗖地缩了回去。</p>\n" +
            " <p>&ldquo;这不是玩笑，在普通人面前露馅儿，会出大事儿的。&rdquo;小道师发觉自己唠唠叨叨像个大妈，他现在已经开始担心这俩傻兔子到底能不能在险恶的人类社会中生活下去了。</p>\n" +
            " <p>&ldquo;这有三张符，遇到事情撕碎我就会有所感应。只要不碰到顶厉害的道师，我应该都是能帮忙的。&rdquo;一边说着，小道师又掏出三张黄色的纸符递给兔子姑娘。</p>\n" +
            " <p>&ldquo;放心吧，我们会小心为上的。&rdquo;兔子姑娘点了点头，一脸郑重。</p>\n" +
            " <p>看着她脸上认真的表情，小道师噗嗤一声笑了出来。</p>\n" +
            " <p>&ldquo;那么，我走啦。别送，记住头顶的符，还有不要一激动就散功。&rdquo;</p>\n" +
            " <p>又嘱咐了最后一次，小道师这才转身离开。刚走不到几步，被发觉有人拉着他的行囊。他一转头，几根萝卜已经塞到了他的眼前。是兔耳少年。</p>\n" +
            " <p>&ldquo;谢&hellip;&hellip;谢你哥&hellip;&hellip;哥，这个&hellip;&hellip;路上吃。&rdquo;兔耳少年人话还说不利索，只是一双眸子却透澈无比。</p>\n" +
            " <p>小道师揉了揉他的头发，不远处巧笑嫣然的兔子姑娘正在向他挥着手。</p>\n" +
            " <p>小道师生平第一次觉得，师父有一个说法也许是错的。</p>\n" +
            " <p>妖怪，也不全都是坏的啊。小道师一边赶路一边想着，他怀里的萝卜还有着一点儿余温。</p>\n" +
            " <p>他突然发现，今天的阳光好暖啊，迎面吹来的风儿温柔的没边儿。</p>\n" +
            " <hr />\n" +
            " <p>小故事一则，谢谢你看完，也希望你喜欢。</p> </div> </div>   <div class=\"view-more\"><a href=\"http://www.zhihu.com/question/47552239\">查看知乎讨论<span class=\"js-question-holder\"></span></a></div>  </div>   </div> </div>";

    private static final String zhiHuBody2 = "<div class=\"main-wrap content-wrap\"> <div class=\"headline\">  <div class=\"img-place-holder\"></div>    </div>  <div class=\"content-inner\">     <div class=\"question\"> <h2 class=\"question-title\">如何以「小店打烊了，客官请回吧」开头写一篇小故事？</h2>  <div class=\"answer\">  <div class=\"meta\"> <img class=\"avatar\" src=\"http://pic3.zhimg.com/9c173502d4da48949052405d95efa3d2_is.jpg\"> <span class=\"author\">芒果，</span><span class=\"bio\">赐我一场春秋大梦。</span> </div>  <div class=\"content\"> <p>&ldquo;小店打烊了，客官请回吧。&rdquo;姑娘挡着门，就是不让小道师进去。</p>\n" +
            " <p>又嘱咐了最后一次，小道师这才转身离开。刚走不到几步，被发觉有人拉着他的行囊。他一转头，几根萝卜已经塞到了他的眼前。是兔耳少年。</p>\n" +
            " <p>小故事一则，谢谢你看完，也希望你喜欢。</p> </div> </div>   <div class=\"view-more\"><a href=\"http://www.zhihu.com/question/47552239\">查看知乎讨论<span class=\"js-question-holder\"></span></a></div>  </div>   </div> </div>";

    private Button mButton;
    private Button mBtnFile;
    private ImageView mImageView;
    private Button mBtnDialog;
    private Button mBtnHtml;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        initView();
    }

    private void initView() {

        mWebView = ((WebView) findViewById(R.id.animation_webView));
        mBtnHtml = ((Button) findViewById(R.id.animation_btn_html));
        mBtnDialog = ((Button) findViewById(R.id.animation_btn_dialog));
        mButton = ((Button) findViewById(R.id.animation_btn));
        mBtnFile = ((Button) findViewById(R.id.animation_btn_file));
        mImageView = ((ImageView) findViewById(R.id.animation_iv));

        mBtnHtml.setOnClickListener(this);
        mBtnDialog.setOnClickListener(this);
        mButton.setOnClickListener(this);
        mBtnFile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.animation_btn_html:

                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("<html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"css/detail.css\" ></head>");
                stringBuffer.append("<body>");
                stringBuffer.append(body);
                stringBuffer.append("</body></html>");


                Document doc = Jsoup.parse(body);
                // 获取class="article_item"的所有元素  获取该标签里面的所有元素
//                Elements blogList = doc.getElementsByClass("article_item");
//                Elements allElements = doc.getElementsByTag("div");
                Elements allElements = doc.select("div");
                for (Element element : allElements) {
//                    log(element.text());
                    element.after("<p>" + element.text() + "</p>");
                    element.remove();
                }
                mWebView.setDrawingCacheEnabled(true);
                mWebView.loadDataWithBaseURL(null, body, "text/html", "utf-8", null);

                break;
            case R.id.animation_btn_dialog:

                RemindDialog remindDialog = new RemindDialog(this);

                remindDialog.show();

                break;

            case R.id.animation_btn:

                mWebView.setDrawingCacheEnabled(true);
                mWebView.loadDataWithBaseURL(null, zhiHuBody2, "text/html", "utf-8", null);

                ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1f, 0, 1f,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                        ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
//                scaleAnimation.setAnimationListener();
                scaleAnimation.setDuration(1000);
                scaleAnimation.setFillAfter(true);
                scaleAnimation.setInterpolator(new BounceInterpolator());

                mButton.startAnimation(scaleAnimation);

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                String s = "2015-05-01";
                try {
                    long time = df.parse(s + " 00:00").getTime();
                    long currentTimeMillis = System.currentTimeMillis();
                    if (time - currentTimeMillis > 0) {
                        showToast(time + "  未过期");
                    } else {
                        showToast("过期");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                break;

            case R.id.animation_btn_file:
                String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                showToast("根目录路径 = " + absolutePath);

//                /storage/emulated/0/wmi/HRO_IMG/pic/WMI_IMG_20160615_161136235.jpg
//                Bitmap bitmap = readBitmap(absolutePath + "/wmi/HRO_IMG/pic/clip/1734884-1462426290517.jpg");
                String picPath = absolutePath + "/wmi/HRO_IMG/pic/WMI_IMG_20160615_172118714.jpg";
//                Bitmap bitmap = BitmapFactory.decodeFile(absolutePath + "/wmi/HRO_IMG/pic/clip/1734882-1461722516148.jpg");

                if (new File(picPath).exists()) {
                    Bitmap bitmap = BitmapUtils.imageZoom(500, BitmapUtils.readBitmap(picPath));
//                Bitmap bitmap = BitmapUtils.getScaledBitmap(picPath, UIUtils.getScreenWidth(), UIUtils.getScreenHeight());

                    mImageView.setImageBitmap(bitmap);
                } else {

                    showToast("图片不存在");

                }

                break;
        }

    }


}

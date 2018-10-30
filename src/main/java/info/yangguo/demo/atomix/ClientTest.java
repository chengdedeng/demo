package info.yangguo.demo.atomix;

import com.google.common.collect.Lists;
import io.atomix.cluster.Member;
import io.atomix.cluster.Node;
import io.atomix.cluster.discovery.BootstrapDiscoveryProvider;
import io.atomix.core.Atomix;
import io.atomix.core.map.AtomicMap;
import io.atomix.core.profile.Profile;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ClientTest {
    public static void main(String[] args) {
        List<Node> members = Lists.newArrayList();
        members.add(Member.builder().withId("gateway1").withAddress("127.0.0.1", 6001).build());
        members.add(Member.builder().withId("gateway2").withAddress("127.0.0.1", 6002).build());
        members.add(Member.builder().withId("gateway3").withAddress("127.0.0.1", 6003).build());

        Member member = Member.builder().withId("store").withAddress("localhost", 7001).build();
        Atomix atomix = Atomix.builder()
                .withMemberId(member.id())
                .withAddress(member.address())
                .withMembershipProvider(BootstrapDiscoveryProvider.builder()
                        .withNodes((Collection) members)
                        .build())
                .withProfiles(Profile.client())
                .build();

        atomix.start().join();


        AtomicMap<String, String> map = atomix.getAtomicMap("store-info");

        long begin = new Date().getTime();
//        for (int i = 27350; i < 1000000; i++) {
//            try {
//                String key = "/group1/ac/de/store-file-name-" + i;
//                map.put(key, "cnBeta.COM_中文业界资讯站\n" +
//                        "首页影视音乐游戏动漫趣闻科学软件主题排行\n" +
//                        " 搜索\n" +
//                        "iPhone XS Max、三星Note9终极对决:谁是年度最强机皇？\n" +
//                        "2018年10月30日 13:36 24 次阅读 稿源：搜狐IT 0 条评论\n" +
//                        "手机\n" +
//                        "三星公司在八月份推出了旗舰Galaxy Note 9手机，苹果公司也在九月推出了iPhone XS MAX手机，这两部手机可以说是这两大国际品牌的最强大屏手机。鉴于iPhone XS Max和三星Galaxy Note 9的屏幕尺寸、机身重量相仿，我们把它们来做一个硬碰硬的对比，来看下谁才是时下的最强机皇。\n" +
//                        "\n" +
//                        "\n" +
//                        "\n" +
//                        "外观：难分胜负\n" +
//                        "\n" +
//                        "不同于当今手机市场的“刘海屏”、“水滴屏”设计， Galaxy Note9延续了三星在屏幕设计方面的优秀基因，采用6.4英寸Super AMOLED全视曲面屏，并对动态范围、对比度、清晰度和色彩饱和度都做了大幅提升，同时对屏幕上端的虹膜扫描和传感器采取了隐蔽式设计、对屏幕下端进行缩减设计，最大程度减少视觉干扰，保证了屏幕最大化，更好的将游戏与影视画面精彩展现，为用户带来更出色的视觉体验以及更多的操作空间。\n" +
//                        "\n" +
//                        "Galaxy Note 9胜在屏幕左右两边曲面，MAX赢在上下两边非常狭窄观感好。\n" +
//                        "\n" +
//                        "从背面看，这一代iPhone XS系列依然几乎没有任何变化：一如既往的红绿灯式双摄，不过除了双摄之外整体外观还算简洁好看。Galaxy Note 9对比前代则有了比较大的变化，加上全视曲面屏和 3D 玻璃后盖，Galaxy Note 9整体给人的感觉都是赏心悦目。值得一提的是， Note 9调整过的指纹解锁位置要比之前便捷了不少，握持感也更为舒适。\n" +
//                        "\n" +
//                        "续航：Note 9户外更省电\n" +
//                        "\n" +
//                        "\n" +
//                        "\n" +
//                        "Phone XS MAX使用的是3174 mAh电池，Galaxy Note 9使用的是4000mAh电池，大电池将给三星Note 9的续航提供充足的保障。\n" +
//                        "\n" +
//                        "在宣称的续航上，三星Galaxy Note 9可达到2天1冲的续航，而iPhone Xs Max承诺比iPhone 8 Plus提升一个半小时，但即便如此，iPhone Xs Max的续航时间也在一天左右，远远无法跟三星Galaxy Note9相比。而且三星Galaxy Note9支持高通QC3.0快充，且附赠快充插头，iPhone Xs Max附赠的转接头已是使用6年的5v1A插头。\n" +
//                        "\n" +
//                        "iPhone XS Max与Galaxy Note 9屏幕都是顶级屏幕\n" +
//                        "\n" +
//                        "\n" +
//                        "\n" +
//                        "苹果去年在十周年之际推出的iPhone X，采用的是AMOLED屏幕，这次的iPhone XS MAX显示屏依然是由三星制造的。iPhone X Max是苹果有史以来屏幕最大的智能手机，屏幕大小达到6.5英寸，相比Galaxy Note 9的6.4英寸还更大一些。同时，两者的屏幕比例有所不同，Note 9是18.5:9的长宽比，iPhone XS Max则为19.5:9，所以后者屏幕显示区域必然显得更长一些。\n" +
//                        "\n" +
//                        "\n" +
//                        "\n" +
//                        "本次，三星为自家手机Galaxy Note 9的屏幕提供了3K Quad HD+级别的分辨率，具体为2960×1440像素，总像素430万像素，像素密度达到 516ppi。而iPhone XS则为FULL HD +级别的分辨率，具体为2688×1242像素，总像素为更低的330万像素，同时像素密度也是更低的458ppi。这使得三星Note 9在高分辨率和高像素密度下为用户带来更好的视觉体验。\n" +
//                        "\n" +
//                        "内存：三星Galaxy Note 9完胜iPhone Xs Max\n" +
//                        "\n" +
//                        "\n" +
//                        "\n" +
//                        "这方面三星Note9相比iPhone XSMax有着绝对的优势。三星Galaxy Note9拥有最高512GB的内存加上可扩展1TB的扩展存储卡设计，远远超过最高512GB的iPhone Xs Max，并且三星Galaxy Note9最顶配的价格市价为8999元低于iPhone Xs Max 12870元近一半。内存最大可扩展容量，这一点iPhone Xs Max完败。\n" +
//                        "\n" +
//                        "生物识别：三星Note9解锁多达3种\n" +
//                        "\n" +
//                        "\n" +
//                        "\n" +
//                        "Note 9和iPhone XS Max都提供面部识别作为解锁手机的方法，但Apple已经更加关注这一领域。现在看来，苹果已经铁了心要用Face ID取代Touch ID。三星在这方面做得比较保守，S系列和Note系列均保留了指纹识别，只不过都放在了手机背后的摄像头旁，这仍然是大多数用户首选的进入设备的路径。相比iPhone XSMax的3D人脸识别，三星Note9的生物识别解锁多达3种。\n" +
//                        "\n" +
//                        "相比iPhone XSMax的3D人脸识别，三星Note9的生物识别解锁多达3种，虹膜扫描仪、指纹扫描仪以及面部解锁，iPhone Xs Max 的3D人脸识别相比之下相对单一。\n" +
//                        "\n" +
//                        "拍照：风格各有不同\n" +
//                        "\n" +
//                        "\n" +
//                        "\n" +
//                        "iPhone XS MAX后摄像头是1200万像素双摄，主摄F1.8，辅摄F2.4，前摄像头为700万像素F2.2支持1080P60FPS。Note9后摄像头同样是1200万像素双摄，主摄F1.5-2.4，辅摄F1.7，前摄像头为800万像素F1.7支持2K30FPS。双机都支持光学变焦， Note9最大变化是加入了全新的AI场景识别模式。此模式可以识别你是在拍摄植物、食物、宠物还是人像，并自动调整颜色和对比度等，产生让人愉悦的照片。\n" +
//                        "\n" +
//                        "值得一提的是，三星Note 9这次亮出了自己的杀手锏，Note系列标志性的S Pen。这支手写笔有了进一步的升级，有了蓝牙遥控功能，可以通过按钮控制各种应用，包括自拍功能。\n" +
//                        "\n" +
//                        "性能：水平相当\n" +
//                        "\n" +
//                        "此次三款新iPhone XS的亮点不是很多，A12处理器算是其中一个。CPU架构方面，A12依然采用 2个大核＋4个小核的6核心设计，大核性能提升15％、小核性能提升50％。处理器方面三星Note 9基本没有太多的选择。骁龙845处理器和A12相差了一代，采用的是10nm制程，各方面都会稍微落后一些。\n" +
//                        "\n" +
//                        "不过，同时也要看到，在安卓阵营中，目前在售的旗舰产品中，骁龙845依然性能最强劲的处理器。三星Note 9已经可以满足绝大部分下的使用需求，即使是目前要求最高的手机游戏，作为唯一拿到“泰尔五星级游戏高性能证书”的机型，不卡顿的流畅感，成为游戏发烧友的全能神器。\n" +
//                        "\n" +
//                        "价格：比iPhone XS Max更有吸引力\n" +
//                        "\n" +
//                        "最后，再来看下两款产品的价格对比。Note9大概9000元就能买到8+512，MAX要12799元能买到4+512，价格相差近3799元。在系统实用性方面安卓确实不如IOS用的舒服苹果胜在系统，三星胜在价格。三星Note 9顶配价格比iPhone XS Max还要低，在顶级旗舰市场上，还是有价格优势的。可以看到，iPhone XSMax的起步价就比三星Note9贵了2600元，512G版更是3800元。而且三星Note 9还配备快充，这就要比iPhone XSMax更有吸引力。\n" +
//                        "\n" +
//                        "\n" +
//                        "\n" +
//                        "总体来说，毫无疑问，iPhone XS Max是一款极为强大的超级旗舰产品，性能强悍的处理器、Face ID、iOS生态系统等都是它的优势。但三星Note 9也有自己的杀手锏，例如功能强大、独一无二的S Pen，全视曲面屏带来的高颜值外观，大电池带来的续航保障，并且价格更有竞争力，相信消费者会做出适合自己的选择。\n" +
//                        "\n" +
//                        "搜狐科技/林国振\n" +
//                        "\n" +
//                        "\n" +
//                        "责任编辑：ugmbbc\n" +
//                        "对文章打分\n" +
//                        "iPhone XS Max、三星Note9终极对决:谁是年度最强机皇？\n" +
//                        "1 (33%)2 (67%)\n" +
//                        "已有 0 条意见\n" +
//                        "登录\n" +
//                        "\n" +
//                        "\n" +
//                        "\n" +
//                        "\n" +
//                        "\n" +
//                        "\n" +
//                        "\n" +
//                        "\n" +
//                        "\n" +
//                        "\n" +
//                        "\n" +
//                        "\n" +
//                        "\n" +
//                        "\n" +
//                        "还可以输入320字\n" +
//                        "\n" +
//                        "请输入评论内容\n" +
//                        " \n" +
//                        "请输入验证码\n" +
//                        " 刷新验证码 登录 / 注册\n" +
//                        "相关文章\n" +
//                        "QM发秋季高端机现状：国产四强争霸 OPPO超华为居第一\n" +
//                        "2 小时前\n" +
//                        "[视频]Hydrogen One手机未能给早期用户留下深刻的印象\n" +
//                        "2 小时前\n" +
//                        "调查称美国人平均三年更换一次智能手机\n" +
//                        "5 小时前\n" +
//                        "一加6T上手体验：美国市场首款屏下指纹手机怎么样？\n" +
//                        "6 小时前\n" +
//                        "一加6T图赏：搭载屏幕指纹 约合人民币3800元起\n" +
//                        "7 小时前\n" +
//                        "Top 10\n" +
//                        "本周 本月\n" +
//                        "1\n" +
//                        "不止一座 NASA发现又一座矩形冰山\n" +
//                        "阅读 (21798) 评论 (6)\n" +
//                        "2\n" +
//                        "网曝华为手机改名小米手机后 安兔兔跑分暴涨\n" +
//                        "阅读 (21281) 评论 (5)\n" +
//                        "3\n" +
//                        "3299元起 小米MIX 3正式发布：拍照世界前三 最高10G+256G\n" +
//                        "阅读 (19602) 评论 (15)\n" +
//                        "4小男孩邀32名同学庆生被\"放鸽子\" 一脸无奈\n" +
//                        "5你的U盘、硬盘降价50%！为啥？一个570亿美元的大机会“曝光”了\n" +
//                        "6iPhone XR最好别套官网黑色保护壳：画面让人“窒息”\n" +
//                        "7女大学生没带饭卡转账后举报食堂阿姨？校方回应\n" +
//                        "8《复仇者联盟4》古一法师将现身 多元宇宙大神吊打灭霸\n" +
//                        "9小米发布两款新型插线板 49元起\n" +
//                        "10南极洲惊现矩形冰山 但它看起来很不自然\n" +
//                        "\n" +
//                        "报道中出现的商标及图像版权属于其合法持有人，只供传递信息之用，非商务用途。互动交流时请遵守理性，宽容，换位思考的原则。\n" +
//                        "合作媒体与供稿人(部分) 驱动之家生物360威锋网虎嗅网中文摄影杂志极客公园MacXIMCN科技美学雷锋网安卓中国蜂鸟网充电头科客N软网开源中国科普中国网\n" +
//                        "鸣谢通信合作伙伴提供部分节点: VeryCloud高防服务器华夏名网海波网络万达网络香港服务器易迈云\n" +
//                        "关于我们 广告招租 报告不适当内容 \n" +
//                        "©2003-2018 cnBeta 浙ICP备11027646号  浙公网安备 33100302000002号\n" +
//                        "帮助cnBeta.COM更好地生产内容，请将我们加入广告屏蔽插件的白名单，非常感谢。具体方法X");
//                System.out.println(key);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        for (int i = 0; i < 200000; i++) {
            try {
                String key = "/group1/ac/de/store-file-name-" + i;
                if (map.containsKey(key))
                    System.out.println(key);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        long end = new Date().getTime();
        System.out.println("耗时" + (end - begin) / 1000 + "秒");
    }
}

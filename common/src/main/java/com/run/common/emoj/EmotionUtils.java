
package com.run.common.emoj;

import android.support.v4.util.ArrayMap;

import com.run.common.R;

public class EmotionUtils {

    /**
     * 表情类型标志符
     */
    public static final int EMOTION_CLASSIC_TYPE = 0x0001;//经典表情

    /**
     * key-表情文字;
     * value-表情图片资源
     */
    public static ArrayMap<String, Integer> EMPTY_MAP;
    public static ArrayMap<String, Integer> EMOTION_CLASSIC_MAP;


    static {
        EMPTY_MAP = new ArrayMap<>();
        EMOTION_CLASSIC_MAP = new ArrayMap<>();

        EMOTION_CLASSIC_MAP.put("[允悲]", R.drawable.yb);
        EMOTION_CLASSIC_MAP.put("[吃瓜]", R.drawable.cg);
        EMOTION_CLASSIC_MAP.put("[坏笑]", R.drawable.hx);
        EMOTION_CLASSIC_MAP.put("[笑而不语]", R.drawable.xeby);
        EMOTION_CLASSIC_MAP.put("[笑cry]", R.drawable.cc);
        EMOTION_CLASSIC_MAP.put("[偷笑]", R.drawable.tx);
        EMOTION_CLASSIC_MAP.put("[心]", R.drawable.x);
        EMOTION_CLASSIC_MAP.put("[赞]", R.drawable.edn);
        EMOTION_CLASSIC_MAP.put("[good]", R.drawable.edq);
        EMOTION_CLASSIC_MAP.put("[舔屏]", R.drawable.tp);
        EMOTION_CLASSIC_MAP.put("[色]", R.drawable.se);

    }

    /**
     * 根据名称获取当前表情图标R值
     *
     * @param EmotionType 表情类型标志符
     * @param imgName     名称
     * @return
     */
    public static int getImgByName(int EmotionType, String imgName) {
        Integer integer = null;
        switch (EmotionType) {
            case EMOTION_CLASSIC_TYPE:
                integer = EMOTION_CLASSIC_MAP.get(imgName);
                break;
            default:
                break;
        }
        return integer == null ? -1 : integer;
    }

    /**
     * 根据类型获取表情数据
     *
     * @param EmotionType
     * @return
     */
    public static ArrayMap<String, Integer> getEmojiMap(int EmotionType) {
        ArrayMap EmojiMap = null;
        switch (EmotionType) {
            case EMOTION_CLASSIC_TYPE:
                EmojiMap = EMOTION_CLASSIC_MAP;
                break;
            default:
                EmojiMap = EMPTY_MAP;
                break;
        }
        return EmojiMap;
    }
}

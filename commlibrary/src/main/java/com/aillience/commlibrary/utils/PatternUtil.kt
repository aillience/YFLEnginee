package com.aillience.commlibrary.utils

import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException

/**
 * create by yinfanglang 20181113
 * 对简单数据进行判断，格式化类
 */
object PatternUtil {

    /**
     * 只允许汉字
     *
     * @param str 目标字符串
     * @return 返回
     * @throws PatternSyntaxException 异常
     */
    @Throws(PatternSyntaxException::class)
    fun stringFilter1(str: String): String {
        //只允许汉字
        val regEx = "[^\u4E00-\u9FA5]"
        val p = Pattern.compile(regEx)
        val m = p.matcher(str)
        return m.replaceAll("").trim { it <= ' ' }
    }

    @Throws(PatternSyntaxException::class)
    fun isFilter(str: String): Boolean {
        //只允许汉字
        var n:Int
        for (i in 0 until str.length) {
            n = str[i].toInt()
            if (n !in 19968..40868) {
                return false
            }
        }
        return true
    }

    /**
     * 身份证合理判断
     */
    fun idCardValidate(no: String?): Boolean {
        // 对身份证号进行长度等简单判断
        if (no == null || no.length != 18 || !no.matches("\\d{17}[0-9X]".toRegex())) {
            return false
        }
        // 1-17位相乘因子数组
        val factor = intArrayOf(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2)
        // 18位随机码数组
        val random = "10X98765432".toCharArray()
        // 计算1-17位与相应因子乘积之和
        var total = 0
        for (i in 0..16) {
            total += Character.getNumericValue(no[i]) * factor[i]
        }
        // 判断随机码是否相等
        return random[total % 11] == no[17]
    }

    /**
     * 判断是否是号码
     * @param mobiles 号码字符串
     * @return 返回结果
     */
    fun isMobileNo(mobiles: String): Boolean {
        /*
          * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、184、187、188、147
          * 联通号码段:130、131、132、185、186、145、171/176/175
          * 电信号码段:133、153、180、181、189、173、177
        */
        //        String telRegex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(16[0-9])|(17([0-9]))|(18[0-9])|(19[0-9]))\\d{8}$";
        //中国电信号段 133、149、153、173、177、180、181、189、199
        //中国联通号段 130、131、132、145、155、156、166、175、176、185、186
        //中国移动号段 134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188、198
        //其他号段
        //14号段以前为上网卡专属号段，如中国联通的是145，中国移动的是147等等。
        //虚拟运营商
        //电信：1700、1701、1702
        //移动：1703、1705、1706
        //联通：1704、1707、1708、1709、171
        //卫星通信：1349
        //        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        //        String regex = "^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$";
        /*
         * (13[0-9])代表13号段 130-139
         * (14[5|7])代表14号段 145、147
         * (15([0-3]|[5-9]))代表15号段 150-153 155-159
         * (17([1-3][5-8]))代表17号段 171-173 175-179 虚拟运营商170屏蔽
         * (18[0-9]))代表18号段 180-189
         * d{8}代表后面可以是0-9的数字，有8位
         * 直接给1开头11位吧，虚拟化也投入使用了
         */
        val regex = "^(1)\\d{10}$"
        return mobiles.matches(regex.toRegex())
    }

    /**
     * 判断字符串是否为URL
     * @param urls 用户头像key
     * @return true:是URL、false:不是URL
     */
    fun isHttpUrl(urls: String): Boolean {
        val regex =
            "(((https|http|ftp)?://)?([a-z0-9]+[.])|(www.))" + "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)"//设置正则表达式
        //比对
        val pat = Pattern.compile(regex.trim { it <= ' ' })
        val mat = pat.matcher(urls.trim { it <= ' ' })
        return mat.matches()
    }


    /**
     * 推荐，速度最快
     * 判断是否为整数
     * @param str 传入的字符串
     * @return 是整数返回true, 否则返回false
     */
    fun isInteger(str: String): Boolean {
        val rex = "[0-9]*"
        val pattern = Pattern.compile(rex)
        return pattern.matcher(str).matches()
    }

    /**
     * 检测是否有emoji表情
     * @param source
     * @return
     */
    fun containsEmoji(source: String?): Boolean {
        if (source == null) {
            return false
        }
        val len = source.length
        for (i in 0 until len) {
            val codePoint = source[i]
            if (!isEmojiCharacter(codePoint)) {
                //如果不能匹配,则该字符是Emoji表情
                return true
            }
        }
        return false
    }

    /**
     * 判断是否是Emoji
     * @param codePoint 比较的单个字符
     * @return
     */
    private fun isEmojiCharacter(codePoint: Char): Boolean {
        return codePoint.toInt() == 0x0 ||
                codePoint.toInt() == 0x9 ||
                codePoint.toInt() == 0xA ||
                codePoint.toInt() == 0xD ||
                codePoint.toInt() in 0x20..0xD7FF ||
                codePoint.toInt() in 0xE000..0xFFFD
    }

}

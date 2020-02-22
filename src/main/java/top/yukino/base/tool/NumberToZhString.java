package top.yukino.base.tool;

public class NumberToZhString {
    /**
     * 数字转中文字符串
     * @param number
     * @return
     */
    public static String ntzs(int number){
        int temp    = number;
        int val     = 0;
        StringBuilder  stringBuilder    = new StringBuilder();

        for(int i=0;i<3;i++){
            val = temp%1000;
            if(val/1000>0){


            }

            if(i==1)
                stringBuilder.append("万");
            if(i==2)
                stringBuilder.append("亿");
            temp/=1000;
            if(temp==0)
                break;
        }
        return getZh(0);
    }
    private static String getZh(int number){
        switch (number){
            case 0:
                return "零";
            case 1:
                return "一";
            case 2:
                return "二";
            case 3:
                return "三";
            case 4:
                return "四";
            case 5:
                return "五";
            case 6:
                return "六";
            case 7:
                return "七";
            case 8:
                return "八";
            case 9:
                return "九";
                default:
                    return "";
        }
    }

}

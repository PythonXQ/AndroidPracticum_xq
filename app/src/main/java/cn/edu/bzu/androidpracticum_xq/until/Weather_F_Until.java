package cn.edu.bzu.androidpracticum_xq.until;

public class Weather_F_Until {

    public String f(String a){
        String weather = "";
        switch (a){

            case "00":
                weather="晴";
                break;

            case "01":
                weather="多云";
                break;

            case "02":
                weather="阴";
                break;

            case "03":
                weather="阵雨";
                break;

            case "04":
                weather="雷阵雨";
                break;

            case "05":
                weather="雷阵雨伴有冰雹";
                break;

            case "06":
                weather="雨夹雪";
                break;

            case "07":
                weather="小雨";
                break;

            case "08":
                weather="中雨";
                break;

            case "09":
                weather="大雨";
                break;

            case "10":
                weather="暴雨";
                break;

            case "11":
                weather="大暴雨";
                break;

            case "12":
                weather="特大暴雨";
                break;

            case "13":
                weather="阵雪";
                break;

            case "14":
                weather="小雪";
                break;

            case "15":
                weather="中雪";
                break;

            case "16":
                weather="大雪";
                break;

            case "17":
                weather="暴雪";
                break;

            case "18":
                weather="雾";
                break;

            case "19":
                weather="冻雨";
                break;

            case "20":
                weather="沙尘暴";
                break;

            case "21":
                weather="小雨-中雨";
                break;


            case "22":
                weather="中雨-大雨";
                break;

            case "23":
                weather="大雨-暴雨";
                break;

            case "24":
                weather="暴雨-大暴雨";
                break;

            case "25":
                weather="大暴雨-特大暴雨";
                break;

            case "26":
                weather="小雪-中雪";
                break;

            case "27":
                weather="中雪-大雪";
                break;

            case "28":
                weather="大雪-暴雪";
                break;

            case "29":
                weather="浮尘";
                break;

            case "30":
                weather="扬沙";
                break;

            case "31":
                weather="强沙尘暴";
                break;

            case "53":
                weather="霾";
                break;


        }
        return weather;
    }

}

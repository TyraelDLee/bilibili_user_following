package defaultPackage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BiliUser {
    private String uid;
    private String uname;
    private String followTime;
    private String face;
    private String sign;

    public BiliUser(){
        this.uid = "-1";
        this.uname = "";
        this.face = "";
        this.followTime = "-1";
        this.sign = "";
    }

    public void setUid(String uid){
        this.uid = uid;
    }

    public void setUname(String uname){
        this.uname = uname;
    }

    public void setFollowTime(String followTime){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy 年 MM 月 dd 日 HH时mm分ss秒");
        this.followTime = sf.format(new Date(Long.parseLong(followTime)*1000));
    }

    public void setFace(String face){
        this.face = face;
    }

    public void setSign(String sign){
        this.sign = sign;
    }

    public String getUid(){
        return this.uid;
    }

    public String getUname() {
        return uname;
    }

    public String getFollowTime() {
        return followTime;
    }

    public String getFace() {
        return face;
    }

    public String getSign() {
        return sign;
    }

    @Override
    public String toString() {
        return "BiliUser{" +
                "uid='" + uid + '\'' +
                ", uname='" + uname + '\'' +
                ", followTime='" + followTime + '\'' +
                ", face='" + face + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }

    public String display(){
        return "用户名："+this.uname+", uid:"+this.uid+", \t关注日期："+this.followTime;
    }
}

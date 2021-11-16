package defaultPackage;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.json.JSONObject;

public class ServiceWorker {
    private final String link = "https://api.bilibili.com/x/relation/followings?vmid=";
    private String uid;
    private String SESSDATA;
    private ArrayList<Obs> obs = new ArrayList<>();

    public ServiceWorker() {
        this.uid = "-1";
        this.SESSDATA = "-1";
    }

    public ServiceWorker(String uid) {
        this.uid = uid;
        this.SESSDATA = "-1";
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setSESSDATA(String sd) {
        this.SESSDATA = sd;
    }

    private String sentRequest(int pn) throws Exception {
        URL server = new URL(link + this.uid + "&pn=" + pn);
        HttpURLConnection connection = (HttpURLConnection) server.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-type", "application/json");
        if (!this.SESSDATA.equals("-1"))
            connection.setRequestProperty("Cookie", "SESSDATA=" + this.SESSDATA);
        connection.connect();
        StringBuffer replyPayload = new StringBuffer();
        InputStream is = connection.getInputStream();
        InputStreamReader ir = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(ir);
        String str = null;
        while ((str = br.readLine()) != null) {
            replyPayload.append(str);
        }
        return replyPayload.toString();
    }

    public String sentRequest() {
        String reply = "";
        ArrayList<BiliUser> biliUser = new ArrayList<>();
        String dispay = "";
        int code = -1;
        try {
            int pn = 1;
            reply = this.sentRequest(pn);
            code = new JSONObject(reply).getInt("code");
            if(code == 0){
                int total = new JSONObject(reply).getJSONObject("data").getInt("total");
                while (!this.SESSDATA.equals("-1") ? pn <= (int) (total / 50) + 1 : pn <= 5) {
                    reply = this.sentRequest(pn);
                    JSONObject jo = new JSONObject(reply);
                    for (int i = 0; i < jo.getJSONObject("data").getJSONArray("list").length(); i++) {
                        JSONObject following = (JSONObject) jo.getJSONObject("data").getJSONArray("list").get(i);
                        BiliUser bu = new BiliUser();
                        bu.setUid(following.getBigInteger("mid").toString());
                        bu.setUname(following.getString("uname"));
                        bu.setFollowTime(following.getBigInteger("mtime").toString());
                        bu.setFace(following.getString("face"));
                        bu.setSign(following.getString("sign"));
                        biliUser.add(bu);
                    }
                    pn++;
                }
            }
        } catch (Exception e) {
            System.out.println("Unexpected Happened");
        }
        if(code == 0){
            for (BiliUser b : biliUser) {
                dispay += b.display() + "\r\n";
            }
        }
        if(code == 22115)
            dispay = "用户已设置隐私，无法查看";

        return dispay;
    }

    public void reg(Obs o) {
        obs.add(o);
    }

    public void upd(String showText) {
        for (Obs o : obs) o.update(showText);
    }

    public static void main(String[] args) {
        ServiceWorker sw = new ServiceWorker("51030552");

        System.out.println(sw.sentRequest());

    }
}

package me.xuender.ethics.app.sex;

/**
 * Created by ender on 14-5-2.
 */
public class Day {
    private boolean no = false;
    private int day;
    private String title;
    private String gala;
    private String name;

    private boolean now = false;

    public Day(int day, String title, String chineseGala, String gala) {
        this.day = day;
        this.title = title;
        StringBuilder sb = new StringBuilder();
        if (chineseGala != null) {
            sb.append(chineseGala);
        }
        if (gala != null) {
            if (sb.length() > 0) {
                sb.append('\n');
            }
            sb.append(gala);
        }
        this.gala = sb.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void no() {
        this.no = true;
    }

    public void now() {
        this.now = true;
    }

    public boolean isNow() {
        return now;
    }

    public boolean isNo() {
        return no;
    }

    public int getDay() {
        return day;
    }

    public String getTitle() {
        return title;
    }

    public String getGala() {
        return gala;
    }
}
package me.xuender.ethics.app.chart;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import me.xuender.ethics.app.R;

/**
 * Created by ender on 14-4-27.
 */
public class ChartFragment extends Fragment {
    private GraphicalView rootView;
    private SharedPreferences sp;
    private Context context;
    private static final String[] BS = new String[]{"怒", "恨", "怨", "恼", "烦"};
    private XYMultipleSeriesDataset dataSet;
    private XYMultipleSeriesRenderer renderer;
    private List<String> dates = new ArrayList<String>();
    private SharedPreferences prefs;
    private static final int WEEK = 7;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            context = container.getContext();
            sp = getActivity().getSharedPreferences("count", Context.MODE_PRIVATE);
            rootView = createChart();
            rootView.setBackgroundColor(Color.BLACK);
        }
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        dataSet.clear();
        initData();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void initData() {
        if (dates == null || sp == null) {
            return;
        }
        dates.clear();
        dates.addAll(sp.getStringSet("date", new HashSet<String>()));
        Collections.sort(dates);
        //设置显示条数
        String last = prefs.getString("last", "7");
        if (last == null || last.length() == 0) {
            last = "7";
        }
        while (dates.size() > Integer.valueOf(last)) {
            dates.remove(0);
        }
        int max = 5;
        for (String b : BS) {
            XYSeries series = new XYSeries(b);
            int i = 0;
            for (String s : dates) {
                i++;
                int num = sp.getInt(s + b, 0);
                series.add(i, num);
                if (max < num) {
                    max = num;
                }
            }
            dataSet.addSeries(series);
        }
        renderer.setYAxisMax(max);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private GraphicalView createChart() {
        //同样是需要数据dataset和视图渲染器renderer
        dataSet = new XYMultipleSeriesDataset();
        renderer = new XYMultipleSeriesRenderer();
        initData();
        //设置图表的X轴的当前方向
        renderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);
        //renderer.setXTitle(context.getString(R.string.date));//设置为X轴的标题
        renderer.setYTitle(context.getString(R.string.count));//设置y轴的标题
        renderer.setAxisTitleTextSize(20);//设置轴标题文本大小
//        renderer.setChartTitle(context.getString(R.string.repent));//设置图表标题
//        renderer.setChartTitleTextSize(30);//设置图表标题文字的大小
        renderer.setLabelsTextSize(18);//设置标签的文字大小
        renderer.setLegendTextSize(20);//设置图例文本大小
        renderer.setPointSize(10f);//设置点的大小
        renderer.setYAxisMin(0);//设置y轴最小值是0
        renderer.setYAxisMax(5);
        renderer.setYLabels(10);//设置Y轴刻度个数（貌似不太准确）
        renderer.setXAxisMax(WEEK);
        renderer.setShowGrid(true);//显示网格
//        //将x标签栏目显示如：1,2,3,4替换为显示1月，2月，3月，4月
        int i = 0;
        for (String s : dates) {
            i++;
            renderer.addXTextLabel(i, s.substring(4));
        }
        renderer.setXLabels(0);//设置只显示如1月，2月等替换后的东西，不显示1,2,3等
//        renderer.setMargins(new int[]{20, 30, 15, 20});//设置视图位置
        renderer.addSeriesRenderer(createRenderer(R.color.木));
        renderer.addSeriesRenderer(createRenderer(R.color.火));
        renderer.addSeriesRenderer(createRenderer(R.color.土));
        renderer.addSeriesRenderer(createRenderer(R.color.金));
        renderer.addSeriesRenderer(createRenderer(R.color.水));
        return ChartFactory.getLineChartView(context, dataSet, renderer);
    }

    private XYSeriesRenderer createRenderer(int color) {
        XYSeriesRenderer renderer = new XYSeriesRenderer();//(类似于一条线对象)
        renderer.setColor(context.getResources().getColor(color));//设置颜色
        renderer.setPointStyle(PointStyle.CIRCLE);//设置点的样式
        renderer.setFillPoints(true);//填充点（显示的点是空心还是实心）
        renderer.setDisplayChartValues(true);//将点的值显示出来
        renderer.setChartValuesSpacing(10);//显示的点的值与图的距离
        renderer.setChartValuesTextSize(25);//点的值的文字大小
        //  r.setFillBelowLine(true);//是否填充折线图的下方
        //  r.setFillBelowLineColor(Color.GREEN);//填充的颜色，如果不设置就默认与线的颜色一致
        renderer.setLineWidth(3);//设置线宽
        return renderer;
    }
}
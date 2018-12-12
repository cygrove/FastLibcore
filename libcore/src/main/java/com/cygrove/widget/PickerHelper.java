package com.cygrove.widget;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.contrarywind.interfaces.IPickerViewData;
import com.google.gson.Gson;
import com.cygrove.libcore.R;
import com.cygrove.libcore.utils.DateUtil;
import com.cygrove.libcore.utils.GsonUtils;
import com.cygrove.libcore.utils.ResourcesUtil;
import com.cygrove.libcore.utils.WeekUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author cygrove
 * @time 2018-11-27 11:11
 */
public class PickerHelper {
    /**
     * 弹出时间选择器
     *
     * @param context
     * @param startDate
     * @param endDate
     * @param title
     * @param booleans
     * @param listener
     */
    public TimePickerView pvCustomTime;

    public void showTimePicker(Context context, Calendar startDate, Calendar endDate, String title, boolean[] booleans, OnTimeSelectListener listener) {
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerBuilder(context, listener)
                .setRangDate(startDate, endDate)
                .setDate(endDate)
                .setLayoutRes(R.layout.pickerview_custom_time, v -> {
                    final TextView tvSubmit = v.findViewById(R.id.tv_finish);
                    TextView tvCancel = v.findViewById(R.id.tv_cancel);
                    TextView tvTitle = v.findViewById(R.id.tv_title);
                    tvTitle.setText(title);
                    tvSubmit.setOnClickListener(v12 -> {
                        pvCustomTime.returnData();
                        pvCustomTime.dismiss();
                    });
                    tvCancel.setOnClickListener(v1 -> pvCustomTime.dismiss());
                })
                .setContentTextSize(18)
                .setType(booleans)
                .setLineSpacingMultiplier(1.2f)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .build();
        pvCustomTime.show();
    }

    /**
     * 弹出某年某周的选择
     *
     * @param context
     * @param title
     * @param listener
     */
    public void showWeekPicker(Context context, String[] years, String title, boolean needFuture, OnSelectListener listener) {
        List<WeekEntity> firstPickerList = new ArrayList<>();
        List<List<String>> secondPickerList = new ArrayList<>();
        firstPickerList.clear();
        secondPickerList.clear();
        for (int i = 0; i < years.length; i++) {
            WeekEntity entity = new WeekEntity();
            entity.setYear(years[i]);
            secondPickerList.add(getWeekList((years[i]), needFuture));
            firstPickerList.add(entity);
        }
        OptionsPickerView pvOptions = new OptionsPickerBuilder(context, (options1, options2, options3, v) -> {
            listener.onSelect(firstPickerList.get(options1).getYear(),
                    secondPickerList.get(options1).get(options2), "");
        })
                .setCancelColor(ResourcesUtil.getColor(R.color.text_gray))
                .setSubmitColor(ResourcesUtil.getColor(R.color.text_gray))
                .setTitleText(title)
                .build();
        pvOptions.setPicker(firstPickerList, secondPickerList);//二级选择器*/
        pvOptions.show();
    }

    /**
     * 获取某年的周数
     *
     * @param year
     * @return
     */
    private List<String> getWeekList(String year, boolean needFuture) {
        int weeks = 0;
        String currentYear = DateUtil.getCurrentDate("YYYY");
        if (year.equals(currentYear)) {
            if (needFuture) {
                weeks = WeekUtil.getMaxWeekNumOfYear(Integer.parseInt(year));
            } else {
                weeks = WeekUtil.getWeekOfYear(new Date());
            }
        } else {
            weeks = WeekUtil.getMaxWeekNumOfYear(Integer.parseInt(year));
        }
        List<String> week = new ArrayList<>();
        Date today = new Date();
        Calendar c = new GregorianCalendar();
        c.setTime(today);
        for (int i = 0; i < weeks; i++) {
            week.add((i + 1) + "周");
        }
        return week;
    }

    /**
     * 弹出省市区三级联动
     *
     * @param cotnext
     * @param title
     * @param showStage
     * @param listener
     */
    public void showProvinceCity(Context cotnext, String title, int showStage, OnSelectListener listener) {
        ArrayList<ProvinceCity> options1Items = new ArrayList<>();
        ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
        String JsonData = GsonUtils.getJson(cotnext, "province.json");//获取assets目录下的json文件数据
        ArrayList<ProvinceCity> jsonBean = parseData(JsonData);//用Gson 转成实体
        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }
            /**
             * 添加城市数据
             */
            options2Items.add(CityList);
            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

        ArrayList<ProvinceCity> finalOptions1Items = options1Items;
        OptionsPickerView pvOptions = new OptionsPickerBuilder(cotnext, (options1, options2, options3, v) -> {
            listener.onSelect(finalOptions1Items.get(options1).getPickerViewText(),
                    options2Items.get(options1).get(options2), options3Items.get(options1).get(options2).get(options3));
        })
                .setTitleText(title)
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        switch (showStage) {
            case 1:
                pvOptions.setPicker(options1Items);//一级选择器
                break;
            case 2:
                pvOptions.setPicker(options1Items, options2Items);//二级选择器
                break;
            case 3:
                pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
                break;
            default:
                pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器

        }
        pvOptions.show();
    }

    //Gson 解析
    public ArrayList<ProvinceCity> parseData(String result) {
        ArrayList<ProvinceCity> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                ProvinceCity entity = gson.fromJson(data.optJSONObject(i).toString(), ProvinceCity.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    public interface OnSelectListener {
        void onSelect(String options1, String options2, String options3);
    }

    public class WeekEntity implements IPickerViewData {
        private String year;
        private List<Week> weekList;

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public List<Week> getWeekList() {
            return weekList;
        }

        public void setWeekList(List<Week> weekList) {
            this.weekList = weekList;
        }

        @Override
        public String getPickerViewText() {
            return this.year;
        }

        public class Week {
            private List<String> week;

            public List<String> getWeek() {
                return week;
            }

            public void setWeek(List<String> week) {
                this.week = week;
            }
        }
    }

    public class ProvinceCity implements IPickerViewData {
        /**
         * name : 省份
         * city : [{"name":"北京市","area":["东城区","西城区","崇文区","宣武区","朝阳区"]}]
         */
        private String name;
        private List<CityBean> city;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<CityBean> getCityList() {
            return city;
        }

        public void setCityList(List<CityBean> city) {
            this.city = city;
        }

        // 实现 IPickerViewData 接口，
        // 这个用来显示在PickerView上面的字符串，
        // PickerView会通过IPickerViewData获取getPickerViewText方法显示出来。
        @Override
        public String getPickerViewText() {
            return this.name;
        }

        public class CityBean {
            /**
             * name : 城市
             * area : ["东城区","西城区","崇文区","昌平区"]
             */
            private String name;
            private List<String> area;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<String> getArea() {
                return area;
            }

            public void setArea(List<String> area) {
                this.area = area;
            }
        }
    }
}
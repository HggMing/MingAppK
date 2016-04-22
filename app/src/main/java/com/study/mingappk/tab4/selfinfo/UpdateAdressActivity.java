package com.study.mingappk.tab4.selfinfo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.study.mingappk.R;
import com.study.mingappk.app.APP;
import com.study.mingappk.model.service.MyServiceClient;
import com.study.mingappk.model.bean.A1Provice;
import com.study.mingappk.model.bean.A2City;
import com.study.mingappk.model.bean.A3County;
import com.study.mingappk.model.bean.A4Town;
import com.study.mingappk.model.bean.A5Village;
import com.study.mingappk.model.bean.Result;
import com.study.mingappk.tmain.BackActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateAdressActivity extends BackActivity {
    A1Provice a1Provice;
    A2City a2City;
    A3County a3County;
    A4Town a4Town;
    A5Village a5Village;
    @Bind(R.id.spprovince)
    Spinner spprovince;
    @Bind(R.id.spcity)
    Spinner spcity;
    @Bind(R.id.spcounty)
    Spinner spcounty;
    @Bind(R.id.sptown)
    Spinner sptown;
    @Bind(R.id.spvillage)
    Spinner spvillage;
    @Bind(R.id.line1)
    View line1;
    @Bind(R.id.line2)
    View line2;
    @Bind(R.id.line3)
    View line3;
    @Bind(R.id.line4)
    View line4;

    private String selected_province_name = null;
    private String selected_city_name = null;
    private String selected_country_name = null;
    private String selected_town_name = null;
    private String selected_village_name = null;
    private String vid=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address);
        ButterKnife.bind(this);

        line1.setVisibility(View.GONE);
        spcity.setVisibility(View.GONE);
        line2.setVisibility(View.GONE);
        spcounty.setVisibility(View.GONE);
        line3.setVisibility(View.GONE);
        sptown.setVisibility(View.GONE);
        line4.setVisibility(View.GONE);
        spvillage.setVisibility(View.GONE);
        //加载省份基本数据
        GetProvinceList();
    }

    /**
     * 获取省列表
     */
    private void GetProvinceList() {
        String auth = APP.getInstance().getAuth();
        MyServiceClient.getService().getCall_Add1(auth)
                .enqueue(new Callback<A1Provice>() {
                    @Override
                    public void onResponse(Call<A1Provice> call, Response<A1Provice> response) {
                        if (response.isSuccessful()) {
                            a1Provice = response.body();
                            if (a1Provice != null) {
                                if (a1Provice.getErr() == 0) {
                                    loadProvinceSpinner();//更新省的spinner adapter
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<A1Provice> call, Throwable t) {

                    }
                });

    }

    /**
     * 省列表的加载与选取
     */
    private void loadProvinceSpinner() {
        //绑定适配器和值
        A1Provice.DataEntity defaultProvince = new A1Provice.DataEntity();
        defaultProvince.setProvice_id("0");
        defaultProvince.setProvice_name("请点击此处选择所在省份");
        ArrayList<A1Provice.DataEntity> provinceList = new ArrayList<>();
        provinceList.add(defaultProvince);
        provinceList.addAll(a1Provice.getData());

        ProvinceListAdapter provinceAdapter = new ProvinceListAdapter(this, provinceList);
        spprovince.setAdapter(provinceAdapter);
        spprovince.setSelection(0, true);
        spprovince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                {
                    if (position != 0) {
                        //加载下一级（城市）
                        String provinceid = ((A1Provice.DataEntity) spprovince.getSelectedItem()).getProvice_id();
                        GetCityList(provinceid);
                        parent.setVisibility(View.VISIBLE);
                        line1.setVisibility(View.VISIBLE);
                        spcity.setVisibility(View.VISIBLE);

                        line2.setVisibility(View.GONE);
                        spcounty.setVisibility(View.GONE);

                        line3.setVisibility(View.GONE);
                        sptown.setVisibility(View.GONE);

                        line4.setVisibility(View.GONE);
                        spvillage.setVisibility(View.GONE);

                        selected_province_name = ((A1Provice.DataEntity) spprovince.getSelectedItem()).getProvice_name();
                    } else {
                        line1.setVisibility(View.GONE);
                        spcity.setVisibility(View.GONE);

                        line2.setVisibility(View.GONE);
                        spcounty.setVisibility(View.GONE);

                        line3.setVisibility(View.GONE);
                        sptown.setVisibility(View.GONE);

                        line4.setVisibility(View.GONE);
                        spvillage.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    /**
     * 获取市级列表
     *
     * @param provinceid 省份id
     */
    private void GetCityList(String provinceid) {
        String auth = APP.getInstance().getAuth();
        MyServiceClient.getService().getCall_Add2(auth, provinceid)
                .enqueue(new Callback<A2City>() {
                    @Override
                    public void onResponse(Call<A2City> call, Response<A2City> response) {
                        if (response.isSuccessful()) {
                            a2City = response.body();
                            if (a2City != null) {
                                if (a2City.getErr() == 0) {
                                    loadCitySpinner();//更新市的spinner adapter
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<A2City> call, Throwable t) {

                    }
                });
    }

    /**
     * 市列表的加载与选取
     */
    private void loadCitySpinner() {
        //绑定适配器和值
        A2City.DataEntity defaultCity = new A2City.DataEntity();
        defaultCity.setCity_id("0");
        defaultCity.setCity_name("请点击此处选择所在城市");
        ArrayList<A2City.DataEntity> cityList = new ArrayList<>();
        cityList.add(defaultCity);
        cityList.addAll(a2City.getData());

        CityListAdapter cityAdapter = new CityListAdapter(this, cityList);
        spcity.setAdapter(cityAdapter);
        spcity.setSelection(0, true);
        spcity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    //加载下一级（城市）
                    String cityid = ((A2City.DataEntity) spcity.getSelectedItem()).getCity_id();
                    GetCountryList(cityid);
                    parent.setVisibility(View.VISIBLE);
                    line1.setVisibility(View.VISIBLE);
                    spcity.setVisibility(View.VISIBLE);

                    line2.setVisibility(View.VISIBLE);
                    spcounty.setVisibility(View.VISIBLE);

                    line3.setVisibility(View.GONE);
                    sptown.setVisibility(View.GONE);

                    line4.setVisibility(View.GONE);
                    spvillage.setVisibility(View.GONE);

                    selected_city_name = ((A2City.DataEntity) spcity.getSelectedItem()).getCity_name();

                } else {
                    line1.setVisibility(View.VISIBLE);
                    spcity.setVisibility(View.VISIBLE);

                    line2.setVisibility(View.GONE);
                    spcounty.setVisibility(View.GONE);

                    line3.setVisibility(View.GONE);
                    sptown.setVisibility(View.GONE);

                    line4.setVisibility(View.GONE);
                    spvillage.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * 获取县级列表
     *
     * @param cityid 市id
     */
    private void GetCountryList(String cityid) {
        String auth = APP.getInstance().getAuth();
        MyServiceClient.getService().getCall_Add3(auth, cityid)
                .enqueue(new Callback<A3County>() {
                    @Override
                    public void onResponse(Call<A3County> call, Response<A3County> response) {
                        if (response.isSuccessful()) {
                            a3County = response.body();
                            if (a3County != null) {
                                if (a3County.getErr() == 0) {
                                    loadCountrySpinner();//更新县的spinner adapter
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<A3County> call, Throwable t) {

                    }
                });
    }

    /**
     * 县列表的加载与选取
     */
    private void loadCountrySpinner() {
        //绑定适配器和值
        A3County.DataEntity defaultCountry = new A3County.DataEntity();
        defaultCountry.setCounty_id("0");
        defaultCountry.setCounty_name("请点击此处选择所在县区");
        ArrayList<A3County.DataEntity> countryList = new ArrayList<>();
        countryList.add(defaultCountry);
        countryList.addAll(a3County.getData());

        CountryListAdapter countryAdapter = new CountryListAdapter(this, countryList);
        spcounty.setAdapter(countryAdapter);
        spcounty.setSelection(0, true);
        spcounty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    //加载下一级（城市）
                    String countryid = ((A3County.DataEntity) spcounty.getSelectedItem()).getCounty_id();
                    GetTownList(countryid);
                    parent.setVisibility(View.VISIBLE);
                    line1.setVisibility(View.VISIBLE);
                    spcity.setVisibility(View.VISIBLE);

                    line2.setVisibility(View.VISIBLE);
                    spcounty.setVisibility(View.VISIBLE);

                    line3.setVisibility(View.VISIBLE);
                    sptown.setVisibility(View.VISIBLE);

                    line4.setVisibility(View.GONE);
                    spvillage.setVisibility(View.GONE);

                    selected_country_name = ((A3County.DataEntity) spcounty.getSelectedItem()).getCounty_name();
                } else {
                    line1.setVisibility(View.VISIBLE);
                    spcity.setVisibility(View.VISIBLE);

                    line2.setVisibility(View.VISIBLE);
                    spcounty.setVisibility(View.VISIBLE);

                    line3.setVisibility(View.GONE);
                    sptown.setVisibility(View.GONE);

                    line4.setVisibility(View.GONE);
                    spvillage.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * 获取镇级列表
     *
     * @param countryid 县级id
     */
    private void GetTownList(String countryid) {
        String auth = APP.getInstance().getAuth();
        MyServiceClient.getService().getCall_Add4(auth, countryid)
                .enqueue(new Callback<A4Town>() {
                    @Override
                    public void onResponse(Call<A4Town> call, Response<A4Town> response) {
                        if (response.isSuccessful()) {
                            a4Town = response.body();
                            if (a4Town != null) {
                                if (a4Town.getErr() == 0) {
                                    loadTownSpinner();//更新镇的spinner adapter
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<A4Town> call, Throwable t) {

                    }
                });
    }

    /**
     * 镇列表的加载与选取
     */
    private void loadTownSpinner() {
        //绑定适配器和值
        A4Town.DataEntity defaultTown = new A4Town.DataEntity();
        defaultTown.setTown_id("0");
        defaultTown.setTown_name("请点击此处选择所在乡镇");
        ArrayList<A4Town.DataEntity> townList = new ArrayList<>();
        townList.add(defaultTown);
        townList.addAll(a4Town.getData());

        TownListAdapter townAdapter = new TownListAdapter(this, townList);
        sptown.setAdapter(townAdapter);
        sptown.setSelection(0, true);
        sptown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    //加载下一级（城镇）
                    String townid = ((A4Town.DataEntity) sptown.getSelectedItem()).getTown_id();
                    GetVillageList(townid);
                    parent.setVisibility(View.VISIBLE);
                    line1.setVisibility(View.VISIBLE);
                    sptown.setVisibility(View.VISIBLE);

                    line2.setVisibility(View.VISIBLE);
                    spcounty.setVisibility(View.VISIBLE);

                    line3.setVisibility(View.VISIBLE);
                    sptown.setVisibility(View.VISIBLE);

                    line4.setVisibility(View.VISIBLE);
                    spvillage.setVisibility(View.VISIBLE);

                    selected_town_name = ((A4Town.DataEntity) sptown.getSelectedItem()).getTown_name();
                } else {
                    line1.setVisibility(View.VISIBLE);
                    sptown.setVisibility(View.VISIBLE);

                    line2.setVisibility(View.VISIBLE);
                    spcounty.setVisibility(View.VISIBLE);

                    line3.setVisibility(View.VISIBLE);
                    sptown.setVisibility(View.VISIBLE);

                    line4.setVisibility(View.GONE);
                    spvillage.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * 获取村级列表
     *
     * @param townid 省份id
     */
    private void GetVillageList(String townid) {
        String auth = APP.getInstance().getAuth();
        MyServiceClient.getService().getCall_Add5(auth, townid)
                .enqueue(new Callback<A5Village>() {
                    @Override
                    public void onResponse(Call<A5Village> call, Response<A5Village> response) {
                        if (response.isSuccessful()) {
                            a5Village = response.body();
                            if (a5Village != null) {
                                if (a5Village.getErr() == 0) {
                                    loadVillageSpinner();//更新村的spinner adapter
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<A5Village> call, Throwable t) {

                    }
                });
    }

    /**
     * 村列表的加载与选取
     */
    private void loadVillageSpinner() {
        //绑定适配器和值
        A5Village.DataEntity defaultVillage = new A5Village.DataEntity();
        defaultVillage.setVillage_id("0");
        defaultVillage.setVillage_name("请点击此处选择所在村庄");
        ArrayList<A5Village.DataEntity> villageList = new ArrayList<>();
        villageList.add(defaultVillage);
        villageList.addAll(a5Village.getData());

        VillageListAdapter villageAdapter = new VillageListAdapter(this, villageList);
        spvillage.setAdapter(villageAdapter);
        spvillage.setSelection(0, true);
        spvillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parent.setVisibility(View.VISIBLE);
                line1.setVisibility(View.VISIBLE);
                spvillage.setVisibility(View.VISIBLE);

                line2.setVisibility(View.VISIBLE);
                spcounty.setVisibility(View.VISIBLE);

                line3.setVisibility(View.VISIBLE);
                sptown.setVisibility(View.VISIBLE);

                line4.setVisibility(View.VISIBLE);
                spvillage.setVisibility(View.VISIBLE);

                vid = ((A5Village.DataEntity) spvillage.getSelectedItem()).getVillage_id();
                selected_village_name = ((A5Village.DataEntity) spvillage.getSelectedItem()).getVillage_name();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick(R.id.select_vallige_btn)
    public void onClick() {
        if (selected_province_name != null &&
                selected_city_name != null &&
                selected_country_name != null &&
                selected_town_name != null &&
                selected_village_name != null) {
            final String newAddress = selected_province_name + selected_city_name + selected_country_name + selected_town_name + selected_village_name;
            Log.d("mm",newAddress);
            String auth = APP.getInstance().getAuth();
            MyServiceClient.getService().postCall_UpdateInfo(auth, null, null, null, vid)
                    .enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            if (response.isSuccessful()) {
                                Result result = response.body();
                                if (result != null) {
                                    Toast.makeText(UpdateAdressActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                                    if (result.getErr() == 0) {
                                        Bundle bundle = new Bundle();
                                        bundle.putString(UserDetailActivity.NEW_ADDRESS, newAddress);//给 bundle 写入数
                                        Intent mIntent = new Intent();
                                        mIntent.putExtras(bundle);
                                        setResult(RESULT_OK, mIntent);
                                        finish();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Result> call, Throwable t) {

                        }
                    });
        } else {
            Toast.makeText(this, "请选择详细的地址！", Toast.LENGTH_SHORT).show();
        }
    }


    public class ProvinceListAdapter extends BaseAdapter {
        private List<A1Provice.DataEntity> mList;
        private Context mContext;

        public ProvinceListAdapter(Context pContext, List<A1Provice.DataEntity> pList) {
            this.mContext = pContext;
            this.mList = pList;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_update_address, null);
            if (convertView != null) {
                TextView tvProvincename = (TextView) convertView.findViewById(R.id.tv_pos_name);
                tvProvincename.setText(mList.get(position).getProvice_name());
                tvProvincename.setTextSize(18.0f);
            }
            return convertView;
        }
    }

    public class CityListAdapter extends BaseAdapter {
        private List<A2City.DataEntity> mList;
        private Context mContext;

        public CityListAdapter(Context pContext, List<A2City.DataEntity> pList) {
            this.mContext = pContext;
            this.mList = pList;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_update_address, null);
            if (convertView != null) {
                TextView tvCityname = (TextView) convertView.findViewById(R.id.tv_pos_name);
                tvCityname.setText(mList.get(position).getCity_name());
                tvCityname.setTextSize(18.0f);
            }
            return convertView;
        }
    }

    public class CountryListAdapter extends BaseAdapter {
        private List<A3County.DataEntity> mList;
        private Context mContext;

        public CountryListAdapter(Context pContext, List<A3County.DataEntity> pList) {
            this.mContext = pContext;
            this.mList = pList;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_update_address, null);
            if (convertView != null) {
                TextView tvCountryname = (TextView) convertView.findViewById(R.id.tv_pos_name);
                tvCountryname.setText(mList.get(position).getCounty_name());
                tvCountryname.setTextSize(18.0f);
            }
            return convertView;
        }
    }

    public class TownListAdapter extends BaseAdapter {
        private List<A4Town.DataEntity> mList;
        private Context mContext;

        public TownListAdapter(Context pContext, List<A4Town.DataEntity> pList) {
            this.mContext = pContext;
            this.mList = pList;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_update_address, null);
            if (convertView != null) {
                TextView tvTownname = (TextView) convertView.findViewById(R.id.tv_pos_name);
                tvTownname.setText(mList.get(position).getTown_name());
                tvTownname.setTextSize(18.0f);
            }
            return convertView;
        }
    }

    public class VillageListAdapter extends BaseAdapter {
        private List<A5Village.DataEntity> mList;
        private Context mContext;

        public VillageListAdapter(Context pContext, List<A5Village.DataEntity> pList) {
            this.mContext = pContext;
            this.mList = pList;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_update_address, null);
            if (convertView != null) {
                TextView tvVillagename = (TextView) convertView.findViewById(R.id.tv_pos_name);
                tvVillagename.setText(mList.get(position).getVillage_name());
                tvVillagename.setTextSize(18.0f);
            }
            return convertView;
        }
    }
}

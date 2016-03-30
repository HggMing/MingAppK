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
import com.study.mingappk.api.MyNetApi;
import com.study.mingappk.api.result.Address1Result;
import com.study.mingappk.api.result.Address2Result;
import com.study.mingappk.api.result.Address3Result;
import com.study.mingappk.api.result.Address4Result;
import com.study.mingappk.api.result.Address5Result;
import com.study.mingappk.api.result.Result;
import com.study.mingappk.common.app.MyApplication;
import com.study.mingappk.main.BackActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateAdressActivity extends BackActivity {
    Address1Result address1Result;
    Address2Result address2Result;
    Address3Result address3Result;
    Address4Result address4Result;
    Address5Result address5Result;
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
        String auth = MyApplication.getInstance().getAuth();
        new MyNetApi().getService().getCall_Add1(auth)
                .enqueue(new Callback<Address1Result>() {
                    @Override
                    public void onResponse(Call<Address1Result> call, Response<Address1Result> response) {
                        if (response.isSuccess()) {
                            address1Result = response.body();
                            if (address1Result != null) {
                                if (address1Result.getErr() == 0) {
                                    loadProvinceSpinner();//更新省的spinner adapter
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Address1Result> call, Throwable t) {

                    }
                });

    }

    /**
     * 省列表的加载与选取
     */
    private void loadProvinceSpinner() {
        //绑定适配器和值
        Address1Result.DataEntity defaultProvince = new Address1Result.DataEntity();
        defaultProvince.setProvice_id("0");
        defaultProvince.setProvice_name("请点击此处选择所在省份");
        ArrayList<Address1Result.DataEntity> provinceList = new ArrayList<>();
        provinceList.add(defaultProvince);
        provinceList.addAll(address1Result.getData());

        ProvinceListAdapter provinceAdapter = new ProvinceListAdapter(this, provinceList);
        spprovince.setAdapter(provinceAdapter);
        spprovince.setSelection(0, true);
        spprovince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                {
                    if (position != 0) {
                        //加载下一级（城市）
                        String provinceid = ((Address1Result.DataEntity) spprovince.getSelectedItem()).getProvice_id();
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

                        selected_province_name = ((Address1Result.DataEntity) spprovince.getSelectedItem()).getProvice_name();
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
        String auth = MyApplication.getInstance().getAuth();
        new MyNetApi().getService().getCall_Add2(auth, provinceid)
                .enqueue(new Callback<Address2Result>() {
                    @Override
                    public void onResponse(Call<Address2Result> call, Response<Address2Result> response) {
                        if (response.isSuccess()) {
                            address2Result = response.body();
                            if (address2Result != null) {
                                if (address2Result.getErr() == 0) {
                                    loadCitySpinner();//更新市的spinner adapter
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Address2Result> call, Throwable t) {

                    }
                });
    }

    /**
     * 市列表的加载与选取
     */
    private void loadCitySpinner() {
        //绑定适配器和值
        Address2Result.DataEntity defaultCity = new Address2Result.DataEntity();
        defaultCity.setCity_id("0");
        defaultCity.setCity_name("请点击此处选择所在城市");
        ArrayList<Address2Result.DataEntity> cityList = new ArrayList<>();
        cityList.add(defaultCity);
        cityList.addAll(address2Result.getData());

        CityListAdapter cityAdapter = new CityListAdapter(this, cityList);
        spcity.setAdapter(cityAdapter);
        spcity.setSelection(0, true);
        spcity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    //加载下一级（城市）
                    String cityid = ((Address2Result.DataEntity) spcity.getSelectedItem()).getCity_id();
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

                    selected_city_name = ((Address2Result.DataEntity) spcity.getSelectedItem()).getCity_name();

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
        String auth = MyApplication.getInstance().getAuth();
        new MyNetApi().getService().getCall_Add3(auth, cityid)
                .enqueue(new Callback<Address3Result>() {
                    @Override
                    public void onResponse(Call<Address3Result> call, Response<Address3Result> response) {
                        if (response.isSuccess()) {
                            address3Result = response.body();
                            if (address3Result != null) {
                                if (address3Result.getErr() == 0) {
                                    loadCountrySpinner();//更新县的spinner adapter
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Address3Result> call, Throwable t) {

                    }
                });
    }

    /**
     * 县列表的加载与选取
     */
    private void loadCountrySpinner() {
        //绑定适配器和值
        Address3Result.DataEntity defaultCountry = new Address3Result.DataEntity();
        defaultCountry.setCounty_id("0");
        defaultCountry.setCounty_name("请点击此处选择所在县区");
        ArrayList<Address3Result.DataEntity> countryList = new ArrayList<>();
        countryList.add(defaultCountry);
        countryList.addAll(address3Result.getData());

        CountryListAdapter countryAdapter = new CountryListAdapter(this, countryList);
        spcounty.setAdapter(countryAdapter);
        spcounty.setSelection(0, true);
        spcounty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    //加载下一级（城市）
                    String countryid = ((Address3Result.DataEntity) spcounty.getSelectedItem()).getCounty_id();
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

                    selected_country_name = ((Address3Result.DataEntity) spcounty.getSelectedItem()).getCounty_name();
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
        String auth = MyApplication.getInstance().getAuth();
        new MyNetApi().getService().getCall_Add4(auth, countryid)
                .enqueue(new Callback<Address4Result>() {
                    @Override
                    public void onResponse(Call<Address4Result> call, Response<Address4Result> response) {
                        if (response.isSuccess()) {
                            address4Result = response.body();
                            if (address4Result != null) {
                                if (address4Result.getErr() == 0) {
                                    loadTownSpinner();//更新镇的spinner adapter
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Address4Result> call, Throwable t) {

                    }
                });
    }

    /**
     * 镇列表的加载与选取
     */
    private void loadTownSpinner() {
        //绑定适配器和值
        Address4Result.DataEntity defaultTown = new Address4Result.DataEntity();
        defaultTown.setTown_id("0");
        defaultTown.setTown_name("请点击此处选择所在乡镇");
        ArrayList<Address4Result.DataEntity> townList = new ArrayList<>();
        townList.add(defaultTown);
        townList.addAll(address4Result.getData());

        TownListAdapter townAdapter = new TownListAdapter(this, townList);
        sptown.setAdapter(townAdapter);
        sptown.setSelection(0, true);
        sptown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    //加载下一级（城镇）
                    String townid = ((Address4Result.DataEntity) sptown.getSelectedItem()).getTown_id();
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

                    selected_town_name = ((Address4Result.DataEntity) sptown.getSelectedItem()).getTown_name();
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
        String auth = MyApplication.getInstance().getAuth();
        new MyNetApi().getService().getCall_Add5(auth, townid)
                .enqueue(new Callback<Address5Result>() {
                    @Override
                    public void onResponse(Call<Address5Result> call, Response<Address5Result> response) {
                        if (response.isSuccess()) {
                            address5Result = response.body();
                            if (address5Result != null) {
                                if (address5Result.getErr() == 0) {
                                    loadVillageSpinner();//更新村的spinner adapter
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Address5Result> call, Throwable t) {

                    }
                });
    }

    /**
     * 村列表的加载与选取
     */
    private void loadVillageSpinner() {
        //绑定适配器和值
        Address5Result.DataEntity defaultVillage = new Address5Result.DataEntity();
        defaultVillage.setVillage_id("0");
        defaultVillage.setVillage_name("请点击此处选择所在村庄");
        ArrayList<Address5Result.DataEntity> villageList = new ArrayList<>();
        villageList.add(defaultVillage);
        villageList.addAll(address5Result.getData());

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

                vid = ((Address5Result.DataEntity) spvillage.getSelectedItem()).getVillage_id();
                selected_village_name = ((Address5Result.DataEntity) spvillage.getSelectedItem()).getVillage_name();

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
            String auth = MyApplication.getInstance().getAuth();
            new MyNetApi().getService().getCall_UpdateInfo(auth, null, null, null, vid)
                    .enqueue(new Callback<Result>() {
                        @Override
                        public void onResponse(Call<Result> call, Response<Result> response) {
                            if (response.isSuccess()) {
                                Result result = response.body();
                                if (result != null) {
                                    Toast.makeText(UpdateAdressActivity.this, result.getMsg(), Toast.LENGTH_SHORT).show();
                                    if (result.getErr() == 0) {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("newAddress", newAddress);//给 bundle 写入数
                                        Intent mIntent = new Intent();
                                        mIntent.putExtras(bundle);
                                        setResult(33, mIntent);
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
        private List<Address1Result.DataEntity> mList;
        private Context mContext;

        public ProvinceListAdapter(Context pContext, List<Address1Result.DataEntity> pList) {
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
        private List<Address2Result.DataEntity> mList;
        private Context mContext;

        public CityListAdapter(Context pContext, List<Address2Result.DataEntity> pList) {
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
        private List<Address3Result.DataEntity> mList;
        private Context mContext;

        public CountryListAdapter(Context pContext, List<Address3Result.DataEntity> pList) {
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
        private List<Address4Result.DataEntity> mList;
        private Context mContext;

        public TownListAdapter(Context pContext, List<Address4Result.DataEntity> pList) {
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
        private List<Address5Result.DataEntity> mList;
        private Context mContext;

        public VillageListAdapter(Context pContext, List<Address5Result.DataEntity> pList) {
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

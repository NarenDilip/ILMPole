package com.example.ilmpole.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ilmpole.R;
import com.example.ilmpole.adapter.RegionAdapter;
import com.example.ilmpole.basepojo.Region;

import java.util.ArrayList;

public class AreaCodeDialog extends Dialog {

    CallBack mCallBack;
    Context mContext;
    ListView pType;
    String ManufacturerData;
    String mData;

    String[] LiveData1 = {"VAA", "VAB", "VAC", "VAD", "VAE", "VAF", "VAG", "VAH", "VAI", "VAJ", "VAK", "VAL", "VAM", "VAN","VAO", "VAP", "VAQ", "VAR", "VAS", "VAT", "VAU", "VAV", "VAW",
            "VAX", "VAY", "VAZ"};

    RegionAdapter poleTypeSelectAdapter;

    public AreaCodeDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public void setCallBack(CallBack mCallBackDialog) {
        this.mCallBack = mCallBackDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.region_type_dialog);

//        regionDevicesDAO = Room.databaseBuilder(mContext, AppDatabase.class, "db-devices").fallbackToDestructiveMigration().allowMainThreadQueries().build().getDeviceDAO();
        pType = (ListView) findViewById(R.id.manufaturerList);
        filldata();

        pType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ManufacturerData = LiveData1.get(position).toString();
//                String Data = LiveData1.get(position).toString();
//                mCallBack.RegionDetails(Data);

                ManufacturerData = LiveData1[position];
                String Data = LiveData1[position].toString();
                mCallBack.RegionDetails(Data);
            }
        });
    }

    private void filldata() {

        ArrayList<Region> Manufacturer = new ArrayList<Region>();

        for (int u = 0; u < LiveData1.length; u++) {
            String data = LiveData1[u];
            Region vehcileManufacturer = new Region(data);
            Manufacturer.add(vehcileManufacturer);
        }

//        ArrayList<Region> Manufacturer = new ArrayList<Region>();
//        List<RegionDevices> regiolist = regionDevicesDAO.getDevices();
//        for (int u = 0; u < regiolist.size(); u++) {
//            String data = regiolist.get(u).getDevicename();
//            LiveData1.add(data);
//            Region vehcileManufacturer = new Region(data);
//            Manufacturer.add(vehcileManufacturer);
//        }

        poleTypeSelectAdapter = new RegionAdapter(mContext, Manufacturer);
        pType.setAdapter(poleTypeSelectAdapter);
    }

    public interface CallBack {
        public void RegionDetails(String ManufacturerData);

    }
}

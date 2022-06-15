package com.example.ilmpole.view

import android.os.Bundle
import android.os.StrictMode
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.ilmpole.R
import com.example.ilmpole.database.AppDatabase
import com.example.ilmpole.database.PrintedListDAO
import com.example.ilmpole.dialog.AreaCodeDialog
import com.example.ilmpole.model.AssetGroup
import com.example.ilmpole.webservice.AppPreference
import com.example.schnell_ccms.model.Asset
import com.example.schnell_ccms.model.LoginResponse
import com.example.schnell_ccms.model.ThingsBoardResponse
import com.example.schnell_ccms.webservice.ThingsManager
import com.example.schnell_ccms.widget.AppDialogs
import com.example.tscdll.TscWifiActivity
import com.schnell.http.Response
import com.schnell.http.ResponseListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), ResponseListener, AreaCodeDialog.CallBack {

    private var Maintext: EditText? = null
    private var Globaltest: TextView? = null
    private var Firsttext: TextView? = null
    private var incrementer: Button? = null
    private var decrement: Button? = null
    private var PrintQR: Button? = null
    private var bookList: ArrayList<String>? = null
    private var Seter = "T"
    private var DeviceGroupId = ""
    private var TscEthernetDll = TscWifiActivity()
    private var printDao: PrintedListDAO? = null
    private var regionDialog: AreaCodeDialog? = null
    //declare arraylist

    //       {0:'AA',1:'AB',2:'AC',3:'AD',4:'AE',5:'AF',6:'AG',7:'AH',8:'AI',9:'AJ',
    //            10:'AK',11:'AL',12:'AM',13:'AN',14:'AO',15:'AP',16:'AQ',17:'AR',18:'AS',
    //            19:'AT',20:'AU',21:'AV',22:'AW',23:'AX',24:'AY',25:'AZ',26:'BA',27:'BB',
    //            28:'BC',29:'BD',30:'BE',31:'BF',32:'BG',33:'BH',34:'BI',35:'BJ',36:'BK',
    //            37:'BL',38:'BM',39:'BN',40:'BO',41:'BP',42:'BQ',43:'BR',44:'BS',45:'BT',
    //            46:'BU',47:'BV',48:'BW',49:'BX',50:'BY',51:'BZ',53:'CA',54:'CB',55:'CC'}

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (android.os.Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        Maintext = findViewById<View>(R.id.maintext) as EditText
        Globaltest = findViewById<View>(R.id.global) as EditText
        Firsttext = findViewById<View>(R.id.first) as TextView
        incrementer = findViewById<View>(R.id.increment) as Button
        decrement = findViewById<View>(R.id.subtract) as Button
        PrintQR = findViewById<View>(R.id.printqr) as Button

        TscEthernetDll.openport("192.168.3.62", 9100, 50)

//        var details = AppPreference.get(this, "MainNumber", "")
//        if (!details!!.isEmpty()) {
//            Globaltest!!.setText(details)
//
//            val result = Integer.parseInt(Globaltest!!.text.toString()) / 1000
//
//            if (result == 0) {
//                Firsttext!!.text = "A" + "AA"
//            } else {
//                Firsttext!!.text = "A" + bookList!![result]
//                if (!bookList!![result].isEmpty()) {
//                    Seter = bookList!![result]
//                }
//            }
//
//            val specialData = Globaltest!!.text.toString()
//            val lastFourDigits = specialData.substring(specialData.length - 3)
//            Maintext!!.text = lastFourDigits
//        }

        printDao = Room.databaseBuilder(applicationContext!!, AppDatabase::class.java, "db-devices")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries() //Allows room to do operation on main thread
                .build()
                .print

//        callloginmethod()

        bookList = ArrayList()
        bookList!!.add(Integer.parseInt("0"), "AA")
        bookList!!.add(Integer.parseInt("1"), "AB")
        bookList!!.add(Integer.parseInt("2"), "AC")
        bookList!!.add(Integer.parseInt("3"), "AD")
        bookList!!.add(Integer.parseInt("4"), "AE")
        bookList!!.add(Integer.parseInt("5"), "AF")
        bookList!!.add(Integer.parseInt("6"), "AG")
        bookList!!.add(Integer.parseInt("7"), "AH")
        bookList!!.add(Integer.parseInt("8"), "AI")
        bookList!!.add(Integer.parseInt("9"), "AJ")
        bookList!!.add(Integer.parseInt("10"), "AK")
        bookList!!.add(Integer.parseInt("11"), "AL")
        bookList!!.add(Integer.parseInt("12"), "AM")
        bookList!!.add(Integer.parseInt("13"), "AN")
        bookList!!.add(Integer.parseInt("14"), "AO")
        bookList!!.add(Integer.parseInt("15"), "AP")
        bookList!!.add(Integer.parseInt("16"), "AQ")
        bookList!!.add(Integer.parseInt("17"), "AR")
        bookList!!.add(Integer.parseInt("18"), "AS")
        bookList!!.add(Integer.parseInt("19"), "AT")
        bookList!!.add(Integer.parseInt("20"), "AU")
        bookList!!.add(Integer.parseInt("21"), "AV")
        bookList!!.add(Integer.parseInt("22"), "AW")
        bookList!!.add(Integer.parseInt("23"), "AX")
        bookList!!.add(Integer.parseInt("24"), "AY")
        bookList!!.add(Integer.parseInt("25"), "AZ")

        first.setOnClickListener {
            regionDialog = AreaCodeDialog(this@MainActivity)
            regionDialog!!.setCallBack(this@MainActivity)
            regionDialog!!.show()
        }

        Globaltest!!.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {

            }

            override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
            }

            override fun afterTextChanged(arg0: Editable) {
                if(Globaltest!!.length()>=3) {
                    val specialData = Globaltest!!.text.toString()
                    val lastFourDigits = specialData.substring(specialData.length - 3)
                    Maintext!!.setText(lastFourDigits)
                }
            }
        })

        incrementer!!.setOnClickListener {
            try {
                val presentValStr = Globaltest!!.text.toString()
                var presentIntVal = Integer.parseInt(presentValStr)
                presentIntVal++

                val length = presentIntVal.toString().length

                if (length == 1) {
                    Globaltest!!.text = "00$presentIntVal"
                } else if (length == 2) {
                    Globaltest!!.text = "0$presentIntVal"
                } else if (length == 3) {
                    Globaltest!!.text = presentIntVal.toString()
                } else {
                    val ss = presentIntVal.toString()
                    Globaltest!!.text = ss
                }

                val result = Integer.parseInt(Globaltest!!.text.toString()) / 1000
                if (result == 0) {
                    Firsttext!!.text = "T" + bookList!![result]
                } else {
                    Firsttext!!.text = "T" + bookList!![result]
                    if (!bookList!![result].isEmpty()) {
                        Seter = bookList!![result]
                        Firsttext!!.text = "T$Seter"
                    }
                }

                val specialData = Globaltest!!.text.toString()
                val lastFourDigits = specialData.substring(specialData.length - 3)
                Maintext!!.setText(lastFourDigits)

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "Some error :(", Toast.LENGTH_SHORT).show()
            }
        }

        PrintQR!!.setOnClickListener {

            AppDialogs.showProgressDialog(this, "Please wait..")

            for (i in 0 until 20) {
//                ThingsManager.getDeviceAssets(this, this)
                val presentValStr = Globaltest!!.text.toString()
                var presentIntVal = Integer.parseInt(presentValStr)
                presentIntVal++

                val length = presentIntVal.toString().length

                if (length == 1) {
                    Globaltest!!.text = "00$presentIntVal"
                } else if (length == 2) {
                    Globaltest!!.text = "0$presentIntVal"
                } else if (length == 3) {
                    Globaltest!!.text = presentIntVal.toString()
                } else {
                    val ss = presentIntVal.toString()
                    Globaltest!!.text = ss
                }

                val result = Integer.parseInt(Globaltest!!.text.toString()) / 1000
                if (result == 0) {
                    Firsttext!!.text = first.text.toString()
                } else {
                    Firsttext!!.text = first.text.toString()
                    if (!bookList!![result].isEmpty()) {
                        Seter = bookList!![result]
                        Firsttext!!.text = "T$Seter"
                    }
                }

                val specialData = Globaltest!!.text.toString()
//                AppPreference.put(this, "MainNumber", Globaltest!!.text.toString())
                val lastFourDigits = specialData.substring(specialData.length - 3)
                Maintext!!.setText(lastFourDigits)
//                Toast.makeText(this, Maintext!!.text.toString(), Toast.LENGTH_SHORT).show()
                printQR()
            }
        }

        decrement!!.setOnClickListener {
            try {
                val presentValStr = Globaltest!!.text.toString()
                var presentIntVal = Integer.parseInt(presentValStr)
                presentIntVal--

                val length = presentIntVal.toString().length


                if (length == 1) {
                    Globaltest!!.text = "00$presentIntVal"
                } else if (length == 2) {
                    Globaltest!!.text = "0$presentIntVal"
                } else if (length == 3) {
                    Globaltest!!.text = presentIntVal.toString()
                } else {
                    val ss = presentIntVal.toString()
                    Globaltest!!.text = ss
                }

                val result = Integer.parseInt(Globaltest!!.text.toString()) / 1000
                if (result == 0) {
                    Firsttext!!.text = "T" + bookList!![result]
                } else {
                    Firsttext!!.text = "T" + bookList!![result]
                    if (!bookList!![result].isEmpty()) {
                        Seter = bookList!![result]
                        Firsttext!!.text = "T$Seter"
                    }
                }

                val specialData = Globaltest!!.text.toString()
                val lastFourDigits = specialData.substring(specialData.length - 3)
                Maintext!!.setText(lastFourDigits)

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "Some error :(", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun callloginmethod() {
        AppDialogs.showProgressDialog(this, "Please wait..")
        ThingsManager.login(
                c = this!!,
                username = "smartCity@schnellenergy.com",
                password = "smart@City135"
        )
    }

    private fun printQR() {
        TscEthernetDll.sendcommand("SIZE 14 mm, 11 mm\r\n")
        TscEthernetDll.clearbuffer()
        TscEthernetDll.sendcommand("SPEED 4\r\n")
        TscEthernetDll.sendcommand("DENSITY 12\r\n")
        TscEthernetDll.sendcommand("CODEPAGE UTF-8\r\n")
        TscEthernetDll.sendcommand("SET TEAR ON\r\n")
        TscEthernetDll.sendcommand("SET COUNTER @1 1\r\n")
        TscEthernetDll.sendcommand("@1 = \"0001\"\r\n")
        TscEthernetDll.sendcommand("TEXT 100,300,\"ROMAN.TTF\",0,12,12,@1\r\n")
        TscEthernetDll.sendcommand("TEXT 100,400,\"ROMAN.TTF\",0,12,12,\"TEST FONT\"\r\n")
        TscEthernetDll.qrcode(30, 20, "M", "3", "A", "0", "M2", "S7", Firsttext!!.text.toString() + Maintext!!.text.toString())
        TscEthernetDll.printerfont(24, 20, "1", 90, 1, 1, Firsttext!!.text.toString() + Maintext!!.text.toString())
        TscEthernetDll.printlabel(1, 1)
        AppDialogs.hideProgressDialog()
    }

    override fun onBackPressed() {
        val l = object : AppDialogs.ConfirmListener {
            override fun yes() {
                TscEthernetDll.closeport(5000)
                finish()
            }
        }
        AppDialogs.confirmAction(c = this!!, text = "Sure!! you want to Exit Application?", l = l)
    }

    override fun onResponse(r: Response?) {
        try {
            AppDialogs.hideProgressDialog()
            if (r == null) {
                return
            }

            if (r.message == "Token has expired" || r.errorCode == 11 && r.status == 401) {
                callloginmethod()
            }

            when (r.requestType) {

                ThingsManager.API.login.hashCode() -> {
                    if (r is LoginResponse) {
                        AppPreference.put(
                                applicationContext!!,
                                AppPreference.Key.accessToken,
                                r.token.toString()
                        )
                        AppPreference.put(
                                applicationContext!!,
                                AppPreference.Key.refreshToken,
                                r.refreshToken.toString()
                        )
                    } else {
//                        Snackbar.make(choosepole, "Could not load device information", Snackbar.LENGTH_LONG)
//                                .show()
                    }
                }

                ThingsManager.API.asset.hashCode() -> {
                    if (r is Asset) {
                        ThingsManager.addserverAttribute(this, this, r.id!!.id!!, "label", Firsttext!!.text.toString() + Maintext!!.text.toString())
                        printQR()
                    }
                }

                ThingsManager.API.getDeviceAssets.hashCode() -> {
                    if (r is AssetGroup) {
                        if (!r.deviceList!!.isEmpty()) {
                            for (i in 0 until r.deviceList!!.size) {
                                if (r.deviceList!!.get(i).name.equals("Poles")) {
                                    DeviceGroupId = r.deviceList!!.get(i).id!!.id!!

                                    val asset = Asset()
                                    asset.name = Firsttext!!.text.toString() + Maintext!!.text.toString()
                                    AppPreference.put(this, "MainNumber", Globaltest!!.text.toString())
                                    asset.type = "Pole"
                                    AppDialogs.showProgressDialog(this, "Please wait..")
                                    ThingsManager.saveAsset(this, this, DeviceGroupId, asset)
                                }
                            }
                        }
                    }
                }

                ThingsManager.API.addAttribute.hashCode() -> {
                    if (r is ThingsBoardResponse) {
//                        printQR()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun RegionDetails(ManufacturerData: String?) {
        first.setText(ManufacturerData)
        regionDialog!!.dismiss()
        maintext!!.setText("000")
        if(ManufacturerData!!.equals("TAA")){
            Globaltest!!.text = "000"
        }else if(ManufacturerData!!.equals("TAB")){
            Globaltest!!.text = "1000"
        }else if(ManufacturerData!!.equals("TAC")){
            Globaltest!!.text = "2000"
        }else if(ManufacturerData!!.equals("TAD")){
            Globaltest!!.text = "3000"
        }else if(ManufacturerData!!.equals("TAE")){
            Globaltest!!.text = "4000"
        }else if(ManufacturerData!!.equals("TAF")){
            Globaltest!!.text = "5000"
        }else if(ManufacturerData!!.equals("TAG")){
            Globaltest!!.text = "6000"
        }else if(ManufacturerData!!.equals("TAH")){
            Globaltest!!.text = "7000"
        }else if(ManufacturerData!!.equals("TAI")){
            Globaltest!!.text = "8000"
        }else if(ManufacturerData!!.equals("TAJ")){
            Globaltest!!.text = "9000"
        }else if(ManufacturerData!!.equals("TAK")){
            Globaltest!!.text = "10000"
        }else if(ManufacturerData!!.equals("TAL")){
            Globaltest!!.text = "11000"
        }else if(ManufacturerData!!.equals("TAM")){
            Globaltest!!.text = "12000"
        }else if(ManufacturerData!!.equals("TAN")){
            Globaltest!!.text = "13000"
        }else if(ManufacturerData!!.equals("TAO")){
            Globaltest!!.text = "14000"
        }else if(ManufacturerData!!.equals("TAP")){
            Globaltest!!.text = "15000"
        }else if(ManufacturerData!!.equals("TAQ")){
            Globaltest!!.text = "16000"
        }else if(ManufacturerData!!.equals("TAR")){
            Globaltest!!.text = "17000"
        }else if(ManufacturerData!!.equals("TAS")){
            Globaltest!!.text = "18000"
        }else if(ManufacturerData!!.equals("TAT")){
            Globaltest!!.text = "19000"
        }else if(ManufacturerData!!.equals("TAU")){
            Globaltest!!.text = "20000"
        }else if(ManufacturerData!!.equals("TAV")){
            Globaltest!!.text = "21000"
        }else if(ManufacturerData!!.equals("TAW")){
            Globaltest!!.text = "22000"
        }else if(ManufacturerData!!.equals("TAX")){
            Globaltest!!.text = "23000"
        }else if(ManufacturerData!!.equals("TAY")){
            Globaltest!!.text = "24000"
        }else if(ManufacturerData!!.equals("TAZ")){
            Globaltest!!.text = "25000"
        }
    }
}

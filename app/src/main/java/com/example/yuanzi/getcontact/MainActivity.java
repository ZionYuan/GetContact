package com.example.yuanzi.getcontact;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Cursor personCur;
    private Cursor numberCur;
    private Cursor emailCur;
    private List<ContactInfo> contactList = new ArrayList();
    private ListView conListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conListview = (ListView) findViewById(R.id.lstNames);
        //为listview添加点击事件
        conListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContactInfo contactInfo = contactList.get(position);
                Toast.makeText(MainActivity.this,"姓名："+contactInfo.getName()+"\n"
                                                +"电话："+contactInfo.getPhonenumber()+"\n"
                                                +"邮箱："+contactInfo.getEmail(),Toast.LENGTH_SHORT).show();
            }
        });

        // 获取手机通讯录信息
        ContentResolver resolver = this.getContentResolver();
        // 获取联系人信息
        personCur = resolver.query(ContactsContract.Contacts.CONTENT_URI, null,
                null, null, null);
        // 循环遍历，获取每个联系人的姓名和电话号码
        while (personCur.moveToNext()) {
            // 新建联系人对象
            ContactInfo cInfor = new ContactInfo();
            String cname = "";
            String cnum = "";
            String cemail = null;
            String ID;

            ID = personCur.getString(personCur
                    .getColumnIndex(ContactsContract.Contacts._ID));
            cname = personCur.getString(personCur
                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

            // id的整型数据
            int id = Integer.parseInt(ID);

            if (id > 0) {
                // 获取指定id号码的电话号码
                Cursor c = resolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                + "=" + ID, null, null);
                Cursor cc = resolver.query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                        null, ContactsContract.CommonDataKinds.Email.CONTACT_ID
                                + "=" + ID, null, null);
                while (c.moveToNext()) {
                    cnum = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                if(cc.moveToFirst()&&cc!=null) {
                    do {
                        cemail = cc.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                    }while(cc.moveToNext());
                }else{
                    cemail = "无";
                }

                cInfor.setName(cname);
                cInfor.setPhonenumber(cnum);
                cInfor.setEmail(cemail);

                contactList.add(cInfor);
            }
        }

        ContactAdapter conadapter = new ContactAdapter(this, contactList);
        conListview.setAdapter(conadapter);
    }

}

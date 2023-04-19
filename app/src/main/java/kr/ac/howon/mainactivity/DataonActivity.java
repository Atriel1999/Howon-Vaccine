package kr.ac.howon.mainactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DataonActivity extends Activity {




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dataon);

        Intent getIntent = getIntent();
        String value = ""+getIntent.getStringExtra("nfcData");
        if (value.contains("ko")) {
            value = value.split("ko")[1];
        }
        Log.e("res",value);

        String[] info = value.split("/");
        //0:이름, 1:성별 2:생년월일 3:백신이름 4:백신접종일 5:백신접종차수

        String name = info[0];
        String gender = info[1];
        String birth = info[2];
        String Vname = info[3];
        String Vdate = info[4];
        String Vcount = info[5];

        TextView vaccinealarm;
        vaccinealarm=(TextView)findViewById(R.id.vaccinealarm);
        TextView name1 = findViewById(R.id.inputname);
        TextView gender1 = findViewById(R.id.inputgender);
        TextView birth1 = findViewById(R.id.inputbirth);
        TextView Vname1 = findViewById(R.id.inputVname);
        TextView Vdate1 = findViewById(R.id.inputVdate);
        TextView Vcount1 = findViewById(R.id.inputVcount);

        Button edit1 = findViewById(R.id.edit);
        Button quit1 = findViewById(R.id.quit);

        //넘어온값 보여주기 (입력)
        name1.setText(name);
        gender1.setText(gender);
        birth1.setText(birth);
        Vname1.setText(Vname);
        Vdate1.setText(Vdate);
        Vcount1.setText(Vcount);

        String finalValue = value;
        edit1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //입력창으로 넘어가기
                Intent intent = new Intent(DataonActivity.this, InputActivity.class);
                intent.putExtra("nfcData", finalValue);
                startActivity(intent);
                finish();
            }
        });

        quit1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //종료
                finishAffinity();
                System.runFinalization();
                System.exit(0);
            }
        });

        Vdate1.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에 조치
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력되는 텍스트에 변화가 있을 때 호출된다.

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // 입력이 끝났을 때 호출된다.
                if(Vdate1.getText().length()!=8){
                    vaccinealarm.setText("백신 접종일을 입력해주세요.");
                } else if(Vdate1.getText().length()==8) {

                    Date date = null;
                    Date today = Calendar.getInstance().getTime();

                    SimpleDateFormat Vdateformat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

                    try {
                        date = Vdateformat.parse(Vdate1.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    long diffSec = (today.getTime() - date.getTime()) / 1000; //초 차이
                    long diffDays = diffSec / (24*60*60);

                    vaccinealarm.setText("백신 접종일로부터\n"+diffDays+"일 지났습니다.");
                }
            }
        });



    }
}

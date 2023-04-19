package kr.ac.howon.mainactivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class InputActivity extends Activity {

    final Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.input);


        TextView vaccinealarm;
        EditText inputname,inputbirth,inputVname,inputVdate;
        RadioButton manbtn,womanbtn,firstbtn,secondbtn;
        RadioGroup gender,Vcount;
        Button edit;

        vaccinealarm = (TextView)findViewById(R.id.vaccinealarm);
        inputname = (EditText) findViewById(R.id.inputname);
        inputbirth = (EditText)findViewById(R.id.inputbirth);
        inputVname = (EditText)findViewById(R.id.inputVname);
        inputVdate = (EditText)findViewById(R.id.inputVdate);

        manbtn = (RadioButton)findViewById(R.id.manbtn);
        womanbtn = (RadioButton)findViewById(R.id.womanbtn);
        firstbtn = (RadioButton)findViewById(R.id.firstbtn);
        secondbtn = (RadioButton)findViewById(R.id.secondbtn);

        gender = (RadioGroup)findViewById(R.id.gender);
        Vcount = (RadioGroup)findViewById(R.id.Vcount);

        edit = (Button)findViewById(R.id.edit);



        //Dataon 데이터 넘겨받아서 빈칸에 넣기
        Intent getIntent = getIntent();
        String value = getIntent.getStringExtra("nfcData");

        String[] info = value.split("/");
        //0:이름, 1:성별 2:생년월일 3:백신이름 4:백신접종일 5:백신접종차수

        String name1 = info[0];
        String gender1 = info[1];
        String birth1 = info[2];
        String Vname1 = info[3];
        String Vdate1 = info[4];
        String Vcount1 = info[5];

        if(name1!=null){
            inputname.setText(name1);
        }

        if(gender1!=null) {
            if (gender1 == "남") {
                manbtn.setChecked(true);
                womanbtn.setChecked(false);
            } else if (gender1 == "여") {
                manbtn.setChecked(false);
                womanbtn.setChecked(true);
            }
        }

        if(birth1!=null){
            inputbirth.setText(birth1);
        }

        if(Vname1!=null){
            inputVname.setText(Vname1);
        }
        if(Vdate1!=null){
            inputVdate.setText(Vdate1);
        }

        if(Vcount1!=null) {
            if (Vcount1 == "1차") {
                firstbtn.setChecked(true);
                secondbtn.setChecked(false);
            } else if (Vcount1 == "2차") {
                firstbtn.setChecked(false);
                secondbtn.setChecked(true);
            }
        }


        inputVdate.addTextChangedListener(new TextWatcher() {
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
                if(inputVdate.getText().length()!=8){
                    vaccinealarm.setText("백신 접종일을 입력해주세요.");
                } else if(inputVdate.getText().length()==8) {

                    Date date = null;
                    Date today = Calendar.getInstance().getTime();

                    SimpleDateFormat Vdateformat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

                    try {
                        date = Vdateformat.parse(inputVdate.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    long diffSec = (today.getTime() - date.getTime()) / 1000; //초 차이
                    long diffMin = (today.getTime() - date.getTime()) / 60000; //분 차이
                    long diffHor = (today.getTime() - date.getTime()) / 3600000; //시 차이
                    long diffDays = diffSec / (24*60*60);

                    vaccinealarm.setText("백신 접종일로부터\n"+diffDays+"일 지났습니다.");


                }
            }

        });


        //확인버튼누를시에
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InputActivity.this);
                //빈칸있는지 확인
                builder.setNegativeButton("다시입력", null);
                
                if (inputname.equals("")) {
                    builder.setTitle("오류");
                    builder.setMessage("이름을 입력해주세요");
                    builder.create().show();
                } else if (manbtn.isChecked() == false && womanbtn.isChecked() == false) {
                    builder.setTitle("오류");
                    builder.setMessage("성별을 체크해주세요");
                    builder.create().show();
                } else if (inputbirth.equals("")) {
                    builder.setTitle("오류");
                    builder.setMessage("생년월일을 입력해주세요");
                    builder.create().show();
                } else if (inputVname.equals("")) {
                    builder.setTitle("오류");
                    builder.setMessage("접종받은 백신명을 입력해주세요");
                    builder.create().show();
                } else if (inputVdate.equals("")) {
                    builder.setTitle("오류");
                    builder.setMessage("백신 접종일자를 입력해주세요");
                    builder.create().show();
                } else if (firstbtn.isChecked() == false && secondbtn.isChecked() == false) {
                    builder.setTitle("오류");
                    builder.setMessage("접종 차수를 체크해주세요");
                    builder.create().show();
                } else{
                    builder.setTitle("알림");
                    builder.setMessage("이대로 입력하시겠습니까?");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //data ->다음 액티비티에 보낼 데이터
                            String data = "";
                            data += inputname.getText().toString();
                            data += "/";
                            if(manbtn.isChecked()==true)
                                data +="남";
                            else if(womanbtn.isChecked()==true)
                                data +="여";
                            data +="/";

                            data +=inputbirth.getText().toString();
                            data +="/";
                            data +=inputVname.getText().toString();
                            data +="/";
                            data +=inputVdate.getText().toString();
                            data +="/";

                            if(firstbtn.isChecked()==true)
                                data +="1차";
                            else if(secondbtn.isChecked()==true)
                                data +="2차";

                            //보내기
                            Intent intent1 = new Intent(InputActivity.this, Datatransfer.class);
                            intent1.putExtra("Inputdata", data);
                            startActivity(intent1);
                            finish();
                        }
                    });

                    builder.create().show();
                }
            }
        });



        //확인
        switch (gender.getCheckedRadioButtonId()){
            case R.id.manbtn:
                break;
            case R.id.womanbtn:
                break;
        }





    }
}

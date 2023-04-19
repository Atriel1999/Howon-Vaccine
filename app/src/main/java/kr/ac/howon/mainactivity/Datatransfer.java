package kr.ac.howon.mainactivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

public class Datatransfer extends Activity {

    NfcProcess process;
    PendingIntent pendingIntent;
    NfcAdapter nfcAdapter;

    String nfcData = "";
    String inputData;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.datatransfer);

        Intent getIntent = getIntent();
        inputData = getIntent.getStringExtra("Inputdata");

        ImageView load = (ImageView)findViewById(R.id.loading_view);
        Glide.with(this).load(R.raw.loading).into(load);

        process = new NfcProcess();

        nfcAdapter =  NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC를 지원하지 않는 단말기입니다.", Toast.LENGTH_SHORT).show();
        }
        Intent targetIntent = new Intent(this, this.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0, targetIntent, 0);


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Tag myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        // WRITE
        NdefMessage message = process.createTagMessage(inputData, process.TYPE_TEXT);
        boolean result = process.writeTag(message, myTag);
        if (result) {
            // 성공
            Intent intent1 = new Intent(Datatransfer.this, DataonActivity.class);
            Toast.makeText(this, "수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
            intent1.putExtra("nfcData",inputData);
            startActivity(intent1);
            finish();

        } else {
            // 실패
            Toast.makeText(this, "쓰기 실패"+inputData, Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (nfcAdapter != null){
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfcAdapter != null){
            if (nfcAdapter.isEnabled()){
                nfcAdapter.disableForegroundDispatch(this);
            }else{
                nfcAdapter = null;
                nfcAdapter =  NfcAdapter.getDefaultAdapter(this);
                Toast.makeText(this, "'NFC'기능에 문제가 발생했습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


}

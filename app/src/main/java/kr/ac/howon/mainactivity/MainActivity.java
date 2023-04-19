package kr.ac.howon.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;
import java.time.LocalDateTime;

public class MainActivity extends Activity {

    NfcProcess process;
    PendingIntent pendingIntent;
    NfcAdapter nfcAdapter;

    //String nfcId = "";
    String nfcData = "";

   // TextView nfcTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        process = new NfcProcess();

        TextView animationtext = (TextView)findViewById(R.id.animationtext);
        Animation alpha_anim;
        alpha_anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.alpha_anim);

        animationtext.startAnimation(alpha_anim);

        nfcAdapter =  NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC를 지원하지 않는 단말기입니다.", Toast.LENGTH_SHORT).show();
        }
        Intent targetIntent = new Intent(this, this.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0, targetIntent, 0);

        //nfcTv = (TextView) findViewById(R.id.nfc_tv);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Tag myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        //nfcId = process.byteArrayToHexString(myTag.getId());

        // READ
        Parcelable[] messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (messages != null){
            // 성공
            nfcData = process.getReadTagData((NdefMessage)messages[0]);
            intent = new Intent(this, DataonActivity.class);
            intent.putExtra("nfcData",nfcData);
            startActivity(intent);
        } else {
            // 실패
            Toast.makeText(this, "정보가 없습니다.", Toast.LENGTH_SHORT).show();
            intent = new Intent(MainActivity.this, DataoffActivity.class);
            startActivity(intent);
            finish();
        }

        /* WRITE
        String inputData = "NFC WRITE DATA/name";
        NdefMessage message = process.createTagMessage(inputData, process.TYPE_TEXT);
        boolean result = process.writeTag(message, myTag);
        if (result) {
            // 성공
            Toast.makeText(this, process.getReadTagData(message), Toast.LENGTH_SHORT).show();
        } else {
            // 실패
            Toast.makeText(this, "쓰기 실패", Toast.LENGTH_SHORT).show();
        }*/

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
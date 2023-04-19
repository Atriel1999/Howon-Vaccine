package kr.ac.howon.mainactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

public class DataoffActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dataoff);

        Button btn;

        btn = (Button)findViewById(R.id.dataoffbutton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataoffActivity.this, InputActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

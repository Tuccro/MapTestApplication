package com.tuccro.maptestapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.addressFrom)
    EditText addressFrom;
    @Bind(R.id.addressTo)
    EditText addressTo;

    @Bind(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        addressTo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                        || (keyCode == EditorInfo.IME_ACTION_DONE)) {
                    button.performClick();
                }
                return false;
            }
        });
    }

    @OnClick(R.id.button)
    void onClick() {

        if (StringUtils.isNotBlank(addressFrom.getText())
                && StringUtils.isNotBlank(addressTo.getText())) {

            String uri = getString(R.string.google_maps_url)
                    + getString(R.string.address1) + addressFrom.getText().toString()
                    + getString(R.string.address2) + addressTo.getText().toString();

            Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
            mapIntent.setClassName(getString(R.string.google_maps_package), getString(R.string.google_maps_class_name));
            startActivity(mapIntent);

            try {
                startActivity(mapIntent);
            } catch (ActivityNotFoundException e) {

                Toast.makeText(this, R.string.setup_google_maps, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(getString(R.string.google_maps_play_market)));
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e1) {
                    Toast.makeText(this, R.string.setup_google_play, Toast.LENGTH_SHORT).show();
                }
                e.printStackTrace();
            }

        } else {
            Toast.makeText(MainActivity.this, R.string.fill_fields_first, Toast.LENGTH_SHORT).show();
        }
    }
}

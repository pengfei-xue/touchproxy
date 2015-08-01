package com.example.pengfei.touchproxy;

import android.content.ContentResolver;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.provider.Settings.Secure;
import android.widget.Toast;

import com.example.pengfei.touchproxy.utils.ProxySetter;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "TOUCH_PROXY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton b = (ImageButton)findViewById(R.id.proxy_switcher);
        b.setOnClickListener(new View.OnClickListener() {
            Boolean validateProxyPort(String port) {
                return port.matches("\\d+");
            }

            Boolean validateProxyServer(String server) {
                return server.matches("^(\\d{1,3}\\.){3}\\d{1,3}$");
            }

            @Override
            public void onClick(View v) {
                ImageButton _b = (ImageButton) v;

                String proxy_settings = Secure.HTTP_PROXY;
                Log.v(TAG, proxy_settings);

                final ProxySetter ps = new ProxySetter();

                ContentResolver cr = getContentResolver();

                if (proxy_settings.indexOf(":") >= 0) {
                    // when click this button, proxy is set, so we should unset proxy
                    // and set the background image as proxy off
                    Boolean success = ps.turnOffProxy(cr);
                    Log.v(TAG, success.toString());
                    if (success) {
                        _b.setImageResource(R.drawable.proxy_off);
                    }
                } else {
                    EditText server = (EditText)findViewById(R.id.proxy_server);
                    String server_str = server.getText().toString();
                    if (!validateProxyServer(server_str)) {
                        Toast.makeText(getApplicationContext(), "proxy server address error!", Toast.LENGTH_SHORT);
                        return;
                    }

                    EditText port = (EditText)findViewById(R.id.proxy_port);
                    String port_str = port.getText().toString();
                    if (!validateProxyPort(port_str)) {
                        Toast.makeText(getApplicationContext(), "proxy server port error!", Toast.LENGTH_SHORT);
                        return;
                    }

                    String proxy_addr = server_str + ":" + port_str;
                    Boolean success = ps.turnOnProxy(cr, proxy_addr);
                    Log.v(TAG, success.toString());
                    if (success) {
                        _b.setImageResource(R.drawable.proxy_on);
                    }
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

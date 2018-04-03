package practicaltest01var01.eim.systems.cs.pub.ro.practicaltest01var01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import practicaltest01var01.eim.systems.cs.pub.ro.practicaltest01var01.general.Constants;
import practicaltest01var01.eim.systems.cs.pub.ro.practicaltest01var01.service.PracticalTest01Var01Service;

public class PracticalTest01Var01MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button northButton, eastButton;
    private Button westButton, southButton;
    private Button navigateToSecondaryActivityButton;
    private int numberOfClicks;

    private IntentFilter intentFilter = new IntentFilter();


    private ButtonClickListener buttonClickListener = new ButtonClickListener();

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String northButtonText = "N, ";
            String eastButtonText = "E, ";
            String westButtonText = "W, ";
            String southButtonText = "S, ";

            //phoneNumberEditText.setText(phoneNumberEditText.getText().toString() + ((Button)view).getText().toString());

            switch (view.getId()) {
                case R.id.north_button:
                    numberOfClicks++;
                    editText.setText(editText.getText().toString() + northButtonText);
                    break;
                case R.id.east_button:
                    numberOfClicks++;
                    editText.setText(editText.getText().toString() + eastButtonText);
                    break;
                case R.id.south_button:
                    numberOfClicks++;
                    editText.setText(editText.getText().toString() + southButtonText);
                    break;
                case R.id.west_button:
                    numberOfClicks++;
                    editText.setText(editText.getText().toString() + westButtonText);
                    break;
                case R.id.second_activity:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
                    String text = editText.getText().toString();
                    intent.putExtra(Constants.RECEIVED_TEXT, text);
                    startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
                    break;
            }

            if (numberOfClicks > 4) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var01Service.class);
                intent.putExtra(Constants.COUNTER, numberOfClicks);
                intent.putExtra(Constants.RECEIVED_TEXT, editText.getText().toString());
                getApplicationContext().startService(intent);
                //serviceStatus = Constants.SERVICE_STARTED;
            }


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var01_main);

        editText = (EditText) findViewById(R.id.right_edit_text);

        westButton = (Button) findViewById(R.id.west_button);
        westButton.setOnClickListener(buttonClickListener);

        northButton = (Button) findViewById(R.id.north_button);
        northButton.setOnClickListener(buttonClickListener);

        eastButton = (Button) findViewById(R.id.east_button);
        eastButton.setOnClickListener(buttonClickListener);

        southButton = (Button) findViewById(R.id.south_button);
        southButton.setOnClickListener(buttonClickListener);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Constants.COUNTER)) {
                numberOfClicks = savedInstanceState.getInt(Constants.COUNTER);
            }
            if (savedInstanceState.containsKey(Constants.DIRECTIONS)) {
                editText.setText(savedInstanceState.getString(Constants.DIRECTIONS));
            }

        }


        Toast.makeText(this, "Number of clicks : " + numberOfClicks, Toast.LENGTH_LONG).show();


        navigateToSecondaryActivityButton = (Button) findViewById(R.id.second_activity);
        navigateToSecondaryActivityButton.setOnClickListener(buttonClickListener);

    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(Constants.COUNTER, numberOfClicks);
        savedInstanceState.putString(Constants.DIRECTIONS, editText.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(Constants.COUNTER)) {
            numberOfClicks = savedInstanceState.getInt(Constants.COUNTER);
        }
        if (savedInstanceState.containsKey(Constants.DIRECTIONS)) {
            editText.setText(savedInstanceState.getString(Constants.DIRECTIONS));
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_TAG, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var01Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
            if (resultCode == -1) {
                Toast.makeText(this, "The activity returned with result " + "Register", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "The activity returned with result " + "Cancel", Toast.LENGTH_LONG).show();
            }

        }
    }


}

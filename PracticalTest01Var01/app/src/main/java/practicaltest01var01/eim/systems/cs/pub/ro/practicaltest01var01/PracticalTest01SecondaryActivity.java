package practicaltest01var01.eim.systems.cs.pub.ro.practicaltest01var01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import practicaltest01var01.eim.systems.cs.pub.ro.practicaltest01var01.general.Constants;


public class PracticalTest01SecondaryActivity extends AppCompatActivity {

    private TextView receivedText;
    private Button okButton, cancelButton;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.register_button:
                    setResult(RESULT_OK, null);
                    break;
                case R.id.cancel_button:
                    setResult(RESULT_CANCELED, null);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);

        receivedText = (TextView)findViewById(R.id.received_text);
        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey(Constants.RECEIVED_TEXT)) {
            String text = intent.getStringExtra(Constants.RECEIVED_TEXT);
            receivedText.setText(String.valueOf(text));
        }

        okButton = (Button)findViewById(R.id.register_button);
        okButton.setOnClickListener(buttonClickListener);
        cancelButton = (Button)findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(buttonClickListener);
    }
}

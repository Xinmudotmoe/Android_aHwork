package moe.xinmu.android.ahwork;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class LoginActivity extends AppCompatActivity {
    LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ll=findViewById(R.id.login_ill);
        createEditer();
    }
    void createEditer(){
        View lll=ll.inflate(this,R.layout.editviewd,null);
        lll.setOnClickListener(this::EditerClick);
        ll.addView(lll);
    }
    void EditerClick(View v){
        EditText et=v.findViewById(R.id.editText);
        et.setFocusable(true);
        et.setFocusableInTouchMode(true);
        et.requestFocus();
    }
}

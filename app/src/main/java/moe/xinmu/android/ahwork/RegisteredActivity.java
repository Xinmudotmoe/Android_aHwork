package moe.xinmu.android.ahwork;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.Random;

public class RegisteredActivity extends AppCompatActivity {
    ImageView exit;
    EditText name,pickname,phone,password,vc;
    Button vcb,reg;
    String phone_data;
    int vcs=0;
    MasterDatabaseUtils mdu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        find();
        exit.setOnClickListener(v -> finish());
        vcb.setOnClickListener(l->{
            phone_data=phone.getText().toString();
            vcs=get4Int();
            Toast.makeText(getApplicationContext(),"验证码为  "+vcs+"  ",Toast.LENGTH_SHORT).show();
        });
        reg.setOnClickListener(l->{
            reg();
            vcs=0;
            vc.setText("");//清除验证码框
        });
        mdu=new MasterDatabaseUtils(getApplicationContext());
    }
    private void reg(){
        if(vcs==0){
            Toast.makeText(getApplicationContext(),"请重新获取验证码",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!vc.getText().toString().equals(Integer.toString(vcs)))
        {
            Toast.makeText(getApplicationContext(),"验证码错误",Toast.LENGTH_SHORT).show();
            return;
        }
        String check=mdu.checkcancreateuser(
                pickname.getText().toString(),
                name.getText().toString(),
                password.getText().toString(),
                phone.getText().toString()
        );
        if(check.isEmpty()){
            if(mdu.createUser(pickname.getText().toString(), name.getText().toString(), password.getText().toString(), phone.getText().toString()))
            {
                Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_SHORT).show();
            }


        }
        else
            Toast.makeText(getApplicationContext(),check,Toast.LENGTH_SHORT).show();
    }
    private int get4Int(){
        int d=(int)Math.abs(new Random().nextLong()%10000);
        return d>1000?d:get4Int();//自己调自己 直到获取符合的数字
    }
    private void find() {
        exit=findViewById(R.id.ar_ex);
        name=findViewById(R.id.ar_n);
        pickname=findViewById(R.id.ar_nn);
        phone=findViewById(R.id.ar_pn);
        password=findViewById(R.id.ar_pw);
        vc=findViewById(R.id.ar_vc);
        vcb=findViewById(R.id.ar_vcb);
        reg=findViewById(R.id.ar_reg);
    }
}

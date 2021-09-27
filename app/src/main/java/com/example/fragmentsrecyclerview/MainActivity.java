package com.example.fragmentsrecyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements PersonAdapter.ItemClicked {

    AutoCompleteTextView etName;
    TextView tvName, tvTel;
    EditText etTel;
    Button btnAdd;
    ListFrag listFrag;
    FragmentManager fragmentManager;
    ImageView ivCall;
    String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvName = findViewById(R.id.tvName);
        tvTel = findViewById(R.id.tvTel);

        etName = (AutoCompleteTextView) findViewById(R.id.etName);
        etTel = findViewById(R.id.etTel);

        btnAdd = findViewById(R.id.btnAdd);
        ivCall = findViewById(R.id.ivCall);
        fragmentManager = this.getSupportFragmentManager();
        listFrag = (ListFrag) fragmentManager.findFragmentById(R.id.listFrag);

        String [] names = {"Anshuman Chaudhary", "Abhishek Bilung" , "kajal Rojra", "Kanchan Bora" , "Anamika sharma" , "Khiuphilliu Nring"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.custom_design_autocomplete, names);

        etName.setThreshold(1);
        etName.setAdapter(adapter);


        ivCall.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+number));
                startActivity(intent);
            }
        });

        if(findViewById(R.id.layout_portrait) != null)
        {
            FragmentManager fragmanager = this.getSupportFragmentManager();
            fragmanager.beginTransaction()
                    .hide(fragmanager.findFragmentById(R.id.detailFrag))
                    .hide(fragmanager.findFragmentById(R.id.addPersonFrag))
                    .show(fragmanager.findFragmentById(R.id.listFrag))
                    .commit();
        }
        if(findViewById(R.id.layout_land) != null)
        {
            onItemClicked(0);
            FragmentManager fragmanager = this.getSupportFragmentManager();
            fragmanager.beginTransaction()
                    .show(fragmanager.findFragmentById(R.id.detailFrag))
                    .hide(fragmanager.findFragmentById(R.id.addPersonFrag))
                    .show(fragmanager.findFragmentById(R.id.listFrag))
                    .commit();

        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(etName.getText().toString().isEmpty() || etTel.getText().toString().isEmpty())
                {
                    showToast("Please Enter All Fields","cancel");
                }
                else
                {
                    ApplicationClass.people.add(new Person(etName.getText().toString().trim(),etTel.getText().toString().trim()));
                    showToast("Person added successfully","done");
                    etName.setText("");
                    etTel.setText("");
                    listFrag.notifyDataChange();
                    if(findViewById(R.id.layout_portrait) != null)
                    {
                        fragmentManager.beginTransaction()
                                .hide(fragmentManager.findFragmentById(R.id.detailFrag))
                                .hide(fragmentManager.findFragmentById(R.id.addPersonFrag))
                                .show(fragmentManager.findFragmentById(R.id.listFrag))
                                .commit();
                    }
                    if(findViewById(R.id.layout_land) != null)
                    {
                        fragmentManager.beginTransaction()
                                .show(fragmentManager.findFragmentById(R.id.detailFrag))
                                .hide(fragmentManager.findFragmentById(R.id.addPersonFrag))
                                .show(fragmentManager.findFragmentById(R.id.listFrag))
                                .commit();

                    }
                }

            }
        });


    }

    public  void showToast(String msg,String img)
    {
        View toastView = getLayoutInflater().inflate(R.layout.toast,(ViewGroup) findViewById(R.id.linlay));

        TextView tvToast = (TextView) toastView.findViewById(R.id.tvToast);
        ImageView ivToast = (ImageView) toastView.findViewById(R.id.ivToast);

        tvToast.setText(msg);

        if(img.equals("cancel"))
        {
            ivToast.setImageResource(R.drawable.cancel);
        }
        else
        {
            ivToast.setImageResource(R.drawable.done);
        }

        Toast toast = new Toast(MainActivity.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastView);
        toast.setGravity(Gravity.BOTTOM|Gravity.FILL_HORIZONTAL,0,0);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.addperson:
                if(findViewById(R.id.layout_portrait) != null)
                {
                    fragmentManager.beginTransaction()
                            .hide(fragmentManager.findFragmentById(R.id.detailFrag))
                            .show(fragmentManager.findFragmentById(R.id.addPersonFrag))
                            .hide(fragmentManager.findFragmentById(R.id.listFrag))
                            .addToBackStack(null)
                            .commit();
                }
                if(findViewById(R.id.layout_land) != null)
                {
                    fragmentManager.beginTransaction()
                            .hide(fragmentManager.findFragmentById(R.id.detailFrag))
                            .show(fragmentManager.findFragmentById(R.id.addPersonFrag))
                            .show(fragmentManager.findFragmentById(R.id.listFrag))
                            .addToBackStack(null)
                            .commit();
                }
                break;

            case R.id.edit:
                showToast("Edit Cilcked! Feature not Available","cancel");
                break;

            case R.id.Share:
                showToast("Share Clicked! Feature not Available","cancel");
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(int index) {

        if(findViewById(R.id.layout_portrait) != null)
        {
            FragmentManager fragmanager = this.getSupportFragmentManager();
            fragmanager.beginTransaction()
                    .show(fragmanager.findFragmentById(R.id.detailFrag))
                    .hide(fragmanager.findFragmentById(R.id.listFrag))
                    .addToBackStack(null)
                    .commit();
        }

        tvName.setText(ApplicationClass.people.get(index).getName());
        tvTel.setText(ApplicationClass.people.get(index).getTelNr());

        number = ApplicationClass.people.get(index).getTelNr();
    }
}

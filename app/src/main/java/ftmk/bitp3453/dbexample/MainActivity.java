package ftmk.bitp3453.dbexample;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // creating variables for our edittext, button and dbhandler
    private EditText studname, studnum, studemail, studstate, studGender, studDOB;
    private Button addButton;
    private DBHandler dbHandler;
    String url = "http://192.168.0.157/rest_api.php";
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing all our variables.
        studname = findViewById(R.id.stud_name);
        studemail = findViewById(R.id.stud_email);
        studnum = findViewById(R.id.stud_no);
        studstate = findViewById(R.id.stud_state);
        studDOB = findViewById(R.id.stud_DOB);
        studGender = findViewById(R.id.stud_gender);
        addButton = findViewById(R.id.AddBttn);
        builder = new AlertDialog.Builder(MainActivity.this);

        // creating a new dbhandler class
        // and passing our context to it.
        dbHandler = new DBHandler(MainActivity.this);



        // below line is to add on click listener for our add course button.
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // below line is to get data from all edit text fields.
                String name = studname.getText().toString();
                String num = studnum.getText().toString();
                String email = studemail.getText().toString();
                String state = studstate.getText().toString();
                String dob = studDOB.getText().toString();
                String gender = studGender.getText().toString();

                // validating if the text fields are empty or not.
                if (name.isEmpty() && num.isEmpty() && email.isEmpty() && state.isEmpty() && gender.isEmpty() && dob.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                    return;
                }

                // on below line we are calling a method to add new
                // course to sqlite data and pass all our values to it.
                dbHandler.addNewCourse(num, name, email, state, gender, dob);

                // after adding the data we are displaying a toast message.
                Toast.makeText(MainActivity.this, "Student Details has been added.", Toast.LENGTH_SHORT).show();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                builder.setTitle("Server Response");
                                builder.setMessage("Response :"+response);
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        studnum.setText("");
                                        studname.setText("");
                                        studemail.setText("");
                                        studstate.setText("");
                                        studGender.setText("");
                                        studDOB.setText("");
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }
                        }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,"Error!!", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("colID", num);
                        params.put("colName", name);
                        params.put("colEmail", email);
                        params.put("colDOB", dob);
                        params.put("colGender", gender);
                        params.put("colState", state);
                        return params;
                    }
                };
                MySingleton.getInstance(MainActivity.this).addTorequestqueue(stringRequest);

            }
        });
    }


}
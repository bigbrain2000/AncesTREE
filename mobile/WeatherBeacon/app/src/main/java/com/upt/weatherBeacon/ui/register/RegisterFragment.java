package com.upt.weatherBeacon.ui.register;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.upt.weatherBeacon.databinding.FragmentRegisterBinding;
import com.upt.weatherBeacon.ui.base.BaseFragment;
import com.upt.weatherBeacon.ui.base.Navigation;
import com.upt.weatherBeacon.ui.base.navigation.NavAttribs;
import com.upt.weatherBeacon.ui.base.navigation.Screen;

import java.util.Calendar;
import java.util.Date;

public class RegisterFragment extends BaseFragment<RegisterViewModel> {

    private FragmentRegisterBinding binding;
    private TextView birthDate;
    private Date _birthDate;
    private EditText username;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;

    private EditText confirmPassword;
    private EditText phoneNumber;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        this.birthDate = binding.birthDate;
        this.username = binding.username;
        this.firstName = binding.firstName;
        this.lastName = binding.lastName;
        this.password = binding.password;
        this.confirmPassword = binding.password2;
        this.email = binding.email;
        this.phoneNumber = binding.phoneNumber;
        return binding.getRoot();
    }

    @Override
    protected void setObservers() {

    }

    @Override
    protected void initUi() {
        Button back = binding.backButton;
        Button register = binding.registerButton;

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateFields()){
                    boolean res = viewModel.registerUser(username.getText().toString(),
                            firstName.getText().toString(),
                            lastName.getText().toString(),
                            email.getText().toString(),
                            password.getText().toString(),
                            _birthDate,
                            phoneNumber.getText().toString());

                    System.out.println("USER ::: register result : "+ res);
                   if(res) {
                       Toast.makeText(getContext(), "User created!", Toast.LENGTH_SHORT).show();
                       viewModel.uiEventStream.setValue(new Navigation(new NavAttribs(Screen.LoginScreen, null, false)));
                   }
                }
            }
        });

        birthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.uiEventStream.setValue(new Navigation(new NavAttribs(Screen.LoginScreen, null, false)));
            }
        });
    }

    @Override
    protected int getContentView() {
        return 0;
    }

    private boolean validateFields(){
        int ok = 0;
        if(username.getText().toString().isEmpty()){
            username.setError("Required!");
            binding.errorUsername.setVisibility(View.VISIBLE);
            binding.errorUsername.setText("Required!");
            ok++;
        }
        if(password.getText().toString().isEmpty()){
            password.setError("Required!");
            binding.errorPassword.setVisibility(View.VISIBLE);
            binding.errorPassword.setText("Required!");
            ok++;
        }
        if(confirmPassword.getText().toString().isEmpty()){
            confirmPassword.setError("Required!");
            binding.errorConfirmPassword.setVisibility(View.VISIBLE);
            binding.errorConfirmPassword.setText("Passwords doesn't match!");
            ok++;
        }
        if(firstName.getText().toString().isEmpty()){
            firstName.setError("Required!");
            binding.errorFirstName.setVisibility(View.VISIBLE);
            binding.errorFirstName.setText("Required!");
            ok++;
        }
        if(lastName.getText().toString().isEmpty()){
            lastName.setError("Required!");
            binding.errorLastName.setVisibility(View.VISIBLE);
            binding.errorLastName.setText("Required!");
            ok++;
        }
        System.out.println("Password : "+password.getText()+"\n Confirm : " +confirmPassword.getText());
        if(!password.getText().toString().equals( confirmPassword.getText().toString())){
            password.setError("Required!");
            binding.errorConfirmPassword.setVisibility(View.VISIBLE);
            binding.errorConfirmPassword.setText("Passwords doesn't match!");
            ok++;
        }
        if(!viewModel.validatePassword(password.getText().toString())){
            password.setError("Required!");
            binding.errorPassword.setVisibility(View.VISIBLE);
            binding.errorPassword.setText("At least 8 characters");
            ok++;
        }

//        if(birthDate.getText().equals("Birth date")){
//            birthDate.setError("ERROR");
//            binding.errorBirthDate.setVisibility(View.VISIBLE);
//            binding.errorBirthDate.setText("Required!");
//            ok++;
//        }
        if(!viewModel.validateEmail(email.getText().toString())){
            email.setError("ERROR");
            binding.errorEmail.setVisibility(View.VISIBLE);
            binding.errorEmail.setText("Wrong email");
            ok++;
        }
        if(email.getText().toString().isEmpty()){
            email.setError("ERROR");
            binding.errorEmail.setVisibility(View.VISIBLE);
            binding.errorEmail.setText("Required!");
            ok++;
        }

        return ok==0;
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Do something with the selected date
                        String label = "Birth date: " + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
//                        Toast.makeText(getContext(), label, Toast.LENGTH_SHORT).show();
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(Calendar.YEAR, year);
                        selectedDate.set(Calendar.MONTH, monthOfYear);
                        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        Date selectedDateObject = selectedDate.getTime();
                        _birthDate = selectedDateObject;
                        birthDate.setText(label);
                    }
                }, year, month, dayOfMonth);

        // Show the date picker dialog
        datePickerDialog.show();
    }
}

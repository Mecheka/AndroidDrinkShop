package com.example.suriya.androiddrinkshop.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.suriya.androiddrinkshop.activity.HomeActivity;
import com.example.suriya.androiddrinkshop.R;
import com.example.suriya.androiddrinkshop.manager.Common;
import com.example.suriya.androiddrinkshop.manager.IDrinkShopAPI;
import com.example.suriya.androiddrinkshop.model.CheckUserResponse;
import com.example.suriya.androiddrinkshop.model.UserModel;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.szagurskii.patternedtextwatcher.PatternedTextWatcher;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment implements View.OnClickListener {

    private static final int REQUEST_CODE = 1000;

    private Button btnContinue;

    private IDrinkShopAPI mService;

    public MainFragment() {
        // Required empty public constructor
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView) {

        //Service
        mService = Common.getAPI();

        //Inin View
        btnContinue = rootView.findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(this);

        //Check session
        if (AccountKit.getCurrentAccessToken() != null){
            final AlertDialog alertDialog = new SpotsDialog.Builder().setContext(getActivity()).build();
            alertDialog.show();
            alertDialog.setMessage("Please waiting...");

            //Auto login
            AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                @Override
                public void onSuccess(final Account account) {

                    mService.checkUserExists(account.getPhoneNumber().toString())
                            .enqueue(new Callback<CheckUserResponse>() {
                                @Override
                                public void onResponse(Call<CheckUserResponse> call, Response<CheckUserResponse> response) {
                                    CheckUserResponse checkUserResponse = response.body();
                                    if (checkUserResponse.isExists()){
                                        //Fetch information
                                        mService.getUserInformation(account.getPhoneNumber().toString())
                                                .enqueue(new Callback<UserModel>() {
                                                    @Override
                                                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {

                                                        Common.cerrentUserModel = response.body();

                                                        //if UserModel already exist, new Activity
                                                        alertDialog.dismiss();
                                                        Toast.makeText(getActivity(), "UserModel already exist", Toast.LENGTH_SHORT).show();
                                                        Intent intentHome = new Intent(getActivity(), HomeActivity.class);
                                                        startActivity(intentHome);
                                                        getActivity().finish();

                                                    }

                                                    @Override
                                                    public void onFailure(Call<UserModel> call, Throwable t) {
                                                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

                                                    }
                                                });

                                    }else {
                                        //No UserModel , go register
                                        alertDialog.dismiss();
                                        showRegisterDialog(account.getPhoneNumber().toString());
                                    }
                                }

                                @Override
                                public void onFailure(Call<CheckUserResponse> call, Throwable t) {
                                    Log.d("Response ERROR", t.getMessage());
                                }
                            });

                }

                @Override
                public void onError(AccountKitError accountKitError) {
                    Log.d("Accouct ERROR", accountKitError.getErrorType().getMessage());
                }
            });

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            AccountKitLoginResult result = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if (result.getError() != null) {
                Toast.makeText(getActivity(), result.getError().getErrorType().getMessage(), Toast.LENGTH_SHORT).show();
            } else if (result.wasCancelled()) {
                Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT).show();
            } else {
                if (result.getAccessToken() != null) {
                    final AlertDialog alertDialog = new SpotsDialog.Builder().setContext(getActivity()).build();
                    alertDialog.show();
                    alertDialog.setMessage("Please waiting...");

                    AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                        @Override
                        public void onSuccess(final Account account) {

                            mService.checkUserExists(account.getPhoneNumber().toString())
                                    .enqueue(new Callback<CheckUserResponse>() {
                                        @Override
                                        public void onResponse(Call<CheckUserResponse> call, Response<CheckUserResponse> response) {
                                            CheckUserResponse checkUserResponse = response.body();
                                            if (checkUserResponse.isExists()){
                                                //Fetch information
                                                mService.getUserInformation(account.getPhoneNumber().toString())
                                                        .enqueue(new Callback<UserModel>() {
                                                            @Override
                                                            public void onResponse(Call<UserModel> call, Response<UserModel> response) {

                                                                //if UserModel already exist, new Activity
                                                                alertDialog.dismiss();
                                                                Toast.makeText(getActivity(), "UserModel already exist", Toast.LENGTH_SHORT).show();
                                                                Intent intentHome = new Intent(getActivity(), HomeActivity.class);
                                                                startActivity(intentHome);
                                                                getActivity().finish();

                                                            }

                                                            @Override
                                                            public void onFailure(Call<UserModel> call, Throwable t) {
                                                                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

                                                            }
                                                        });

                                            }else {
                                                //No UserModel , go register
                                                alertDialog.dismiss();
                                                showRegisterDialog(account.getPhoneNumber().toString());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<CheckUserResponse> call, Throwable t) {
                                            Log.d("Response ERROR", t.getMessage());
                                        }
                                    });

                        }

                        @Override
                        public void onError(AccountKitError accountKitError) {
                            Log.d("Accouct ERROR", accountKitError.getErrorType().getMessage());
                        }
                    });
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnContinue:
                startLoginPage(LoginType.PHONE);
                break;
        }
    }

    private void startLoginPage(LoginType phone) {

        Intent intent = new Intent(getActivity(), AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder builder = new AccountKitConfiguration.AccountKitConfigurationBuilder(
                phone, AccountKitActivity.ResponseType.TOKEN);
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, builder.build());
        startActivityForResult(intent, REQUEST_CODE);

    }

    private void showRegisterDialog(final String phone) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("REGISTER");

        LayoutInflater inflater = this.getLayoutInflater();
        View registerLayout = inflater.inflate(R.layout.dialog_register, null);

        final MaterialEditText edtName = registerLayout.findViewById(R.id.edtName);
        final MaterialEditText edtAddress = registerLayout.findViewById(R.id.edtAddress);
        final MaterialEditText edtBirthdate = registerLayout.findViewById(R.id.edtBirthdate);
        Button btnContinue = registerLayout.findViewById(R.id.btnContinue);

        edtBirthdate.addTextChangedListener(new PatternedTextWatcher("####-##-##"));

        builder.setView(registerLayout);
        final AlertDialog dialog = builder.create();

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               dialog.dismiss();

                if (TextUtils.isEmpty(edtName.getText().toString())){
                    Toast.makeText(getActivity(), "Please enter your name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(edtAddress.getText().toString())){
                    Toast.makeText(getActivity(), "Please enter your address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(edtBirthdate.getText().toString())){
                    Toast.makeText(getActivity(), "Please enter your birthdate", Toast.LENGTH_SHORT).show();
                    return;
                }

                final AlertDialog waitDialog = new SpotsDialog.Builder().setContext(getActivity()).build();
                waitDialog.show();
                waitDialog.setMessage("Please waiting...");

                mService.registerNewUser(phone, edtName.getText().toString(), edtAddress.getText().toString(),
                        edtBirthdate.getText().toString()).enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        waitDialog.dismiss();
                        UserModel userModel = response.body();
                        if (TextUtils.isEmpty(userModel.getError_msg())){
                            Toast.makeText(getActivity(), "UserModel register successfully", Toast.LENGTH_SHORT).show();
                            Intent intentHome = new Intent(getActivity(), HomeActivity.class);
                            startActivity(intentHome);
                            getActivity().finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        waitDialog.dismiss();
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        dialog.show();

    }
}

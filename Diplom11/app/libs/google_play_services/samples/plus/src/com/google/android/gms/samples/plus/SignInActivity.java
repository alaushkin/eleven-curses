/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.gms.samples.plus;

import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class SignInActivity extends Activity implements OnClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ServerAuthCodeCallbacks
        {

    private static final String TAG  = "SignInActivity";

    private static final int DIALOG_GET_GOOGLE_PLAY_SERVICES = 1;

    private static final int REQUEST_CODE_SIGN_IN = 1;
    private static final int REQUEST_CODE_GET_GOOGLE_PLAY_SERVICES = 2;

    private static final String KEY_NEW_CODE_REQUIRED = "codeRequired";

    private TextView mSignInStatus;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton mSignInButton;
    private View mSignOutButton;
    private View mServerAuthCodeResetButton;
    private TextView mServerAuthCodeDisabledLabel;
    private View mRevokeAccessButton;
    private ToggleButton mScopeSelector;

    /*
     * Stores the connection result from onConnectionFailed callbacks so that we can resolve them
     * when the user clicks sign-in.
     */
    private ConnectionResult mConnectionResult;

    /*
     * Tracks whether the sign-in button has been clicked so that we know to resolve all issues
     * preventing sign-in without waiting.
     */
    private boolean mSignInClicked;

    /*
     * Tracks whether a resolution Intent is in progress.
     */
    private boolean mIntentInProgress;

    /**
     * Tracks the emulated state of whether a new server auth code is required.
     */
    private final AtomicBoolean mServerAuthCodeRequired = new AtomicBoolean(false);

    /**
     * Whether Verbose is loggable.
     */
    private boolean mIsLogVerbose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // If you want to understand the life cycle more, you can use below command to turn on
        // verbose logging for this Activity on your testing device:
        // adb shell setprop log.tag.SignInActivity VERBOSE
        mIsLogVerbose = Log.isLoggable(TAG, Log.VERBOSE);

        setContentView(R.layout.sign_in_activity);
        restoreState(savedInstanceState);

        logVerbose("Activity onCreate, creating new GoogleApiClient");

        mGoogleApiClient = buildGoogleApiClient(false);

        mSignInStatus = (TextView) findViewById(R.id.sign_in_status);
        mSignInButton = (SignInButton) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(this);

        mServerAuthCodeDisabledLabel = (TextView) findViewById(R.id.server_auth_code_disabled);
        mServerAuthCodeResetButton = findViewById(R.id.server_auth_code_reset_button);
        mServerAuthCodeResetButton.setOnClickListener(this);
        if (!isUsingOfflineAccess()) {
            mServerAuthCodeDisabledLabel.setVisibility(View.VISIBLE);
            mServerAuthCodeResetButton.setVisibility(View.GONE);
        } else {
            mServerAuthCodeDisabledLabel.setVisibility(View.GONE);
            mServerAuthCodeResetButton.setVisibility(View.VISIBLE);
        }

        mSignOutButton = findViewById(R.id.sign_out_button);
        mSignOutButton.setOnClickListener(this);
        mRevokeAccessButton = findViewById(R.id.revoke_access_button);
        mRevokeAccessButton.setOnClickListener(this);
        mScopeSelector = (ToggleButton) findViewById(R.id.scope_selection_toggle);
        mScopeSelector.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                mGoogleApiClient.disconnect();
                // Since we changed the configuration, the cached connection result is no longer
                // valid.
                mConnectionResult = null;
                mGoogleApiClient = buildGoogleApiClient(checked);
                mGoogleApiClient.connect();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mServerAuthCodeRequired.set(isUsingOfflineAccess());
        } else {
            mServerAuthCodeRequired.set(
                    savedInstanceState.getBoolean(KEY_NEW_CODE_REQUIRED, false));
        }
    }

    private GoogleApiClient buildGoogleApiClient(boolean useProfileScope) {
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this);

        String serverClientId = getString(R.string.server_client_id);

        if (!TextUtils.isEmpty(serverClientId)) {
            builder.requestServerAuthCode(serverClientId, this);
        }

        if (useProfileScope) {
            builder.addApi(Plus.API)
                    .addScope(Plus.SCOPE_PLUS_PROFILE);
        } else {
            builder.addApi(Plus.API, Plus.PlusOptions.builder()
                            .addActivityTypes(MomentUtil.ACTIONS).build())
                    .addScope(Plus.SCOPE_PLUS_LOGIN);
        }

        return builder.build();
    }

    private boolean isUsingOfflineAccess() {
        // the emulation of offline access negotiation is enabled/disabled by
        // specifying the server client ID of the app in strings.xml - if no
        // value is present, we do not request offline access.
        return !TextUtils.isEmpty(getString(R.string.server_client_id));
    }

    @Override
    public void onStart() {
        super.onStart();
        logVerbose("Activity onStart, starting connecting GoogleApiClient");
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        logVerbose("Activity onStop, disconnecting GoogleApiClient");
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(KEY_NEW_CODE_REQUIRED, mServerAuthCodeRequired.get());
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.sign_in_button:
                if (!mGoogleApiClient.isConnecting()) {
                    int available = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
                    if (available != ConnectionResult.SUCCESS) {
                        showDialog(DIALOG_GET_GOOGLE_PLAY_SERVICES);
                        return;
                    }

                    mSignInClicked = true;
                    mSignInStatus.setText(getString(R.string.signing_in_status));
                    resolveSignInError();
                }
                break;
            case R.id.server_auth_code_reset_button:
                mServerAuthCodeRequired.set(true);
                break;
            case R.id.sign_out_button:
                if (mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.clearDefaultAccountAndReconnect();
                }
                break;
            case R.id.revoke_access_button:
                mServerAuthCodeRequired.set(true);
                if (mGoogleApiClient.isConnected()) {
                    Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient).setResultCallback(
                            new ResultCallback<Status>() {
                                @Override
                                public void onResult(Status status) {
                                    if (status.isSuccess()) {
                                        mSignInStatus.setText(R.string.revoke_access_status);
                                    } else {
                                        mSignInStatus.setText(R.string.revoke_access_error_status);
                                    }
                                    mGoogleApiClient.reconnect();
                                }
                            }
                    );
                    updateButtons(false /* isSignedIn */);
                }
                break;
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id != DIALOG_GET_GOOGLE_PLAY_SERVICES) {
            return super.onCreateDialog(id);
        }

        int available = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (available == ConnectionResult.SUCCESS) {
            return null;
        }
        if (GooglePlayServicesUtil.isUserRecoverableError(available)) {
            return GooglePlayServicesUtil.getErrorDialog(
                    available, this, REQUEST_CODE_GET_GOOGLE_PLAY_SERVICES);
        }
        return new AlertDialog.Builder(this)
                .setMessage(R.string.plus_generic_error)
                .setCancelable(true)
                .create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        logVerbose(String.format("onActivityResult - requestCode:%d resultCode:%d", requestCode,
                resultCode));

        if (requestCode == REQUEST_CODE_SIGN_IN
                || requestCode == REQUEST_CODE_GET_GOOGLE_PLAY_SERVICES) {
            mIntentInProgress = false; //Previous resolution intent no longer in progress.

            if (resultCode == RESULT_OK) {
                // After resolving a recoverable error, now retry connect(). Note that it's possible
                // mGoogleApiClient is already connected or connecting due to rotation / Activity
                // restart while user is walking through the (possibly full screen) resolution
                // Activities. We should always reconnect() and ignore earlier connection attempts
                // started before completion of the resolution. (With only one exception, a
                // connect() attempt started late enough in the resolution flow and it actually
                // succeeded)
                if (!mGoogleApiClient.isConnected()) {
                    logVerbose("Previous resolution completed successfully, try connecting again");
                    mGoogleApiClient.reconnect();
                }
            } else {
                mSignInClicked = false; // No longer in the middle of resolving sign-in errors.

                if (resultCode == RESULT_CANCELED) {
                    mSignInStatus.setText(getString(R.string.signed_out_status));
                } else {
                    mSignInStatus.setText(getString(R.string.sign_in_error_status));
                    Log.w(TAG, "Error during resolving recoverable error.");
                }
            }
        }
    }

    @Override
    public CheckResult onCheckServerAuthorization(String idToken, Set<Scope> scopeSet) {
        if (mServerAuthCodeRequired.get()) {
            Set<Scope> scopes = new HashSet<Scope>();
            if (mScopeSelector.isChecked()) {
                scopes.add(Plus.SCOPE_PLUS_PROFILE);
            } else {
                scopes.add(Plus.SCOPE_PLUS_LOGIN);
            }

            // also emulate the server asking for an additional Drive scope.
            scopes.add(new Scope(Scopes.DRIVE_APPFOLDER));
            return CheckResult.newAuthRequiredResult(scopes);
        } else {
            return CheckResult.newAuthNotRequiredResult();
        }
    }

    @Override
    public boolean onUploadServerAuthCode(String idToken, String serverAuthCode) {
        Log.d(TAG, "upload server auth code " + serverAuthCode + " requested, faking success");
        mServerAuthCodeRequired.set(false);
        return true;
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        logVerbose("GoogleApiClient onConnected");
        Person person = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        String currentPersonName = person != null
                ? person.getDisplayName()
                : getString(R.string.unknown_person);
        mSignInStatus.setText(getString(R.string.signed_in_status, currentPersonName));
        updateButtons(true /* isSignedIn */);
        mSignInClicked = false;
    }

    @Override
    public void onConnectionSuspended(int cause) {
        logVerbose("GoogleApiClient onConnectionSuspended");
        mSignInStatus.setText(R.string.loading_status);
        mGoogleApiClient.connect();
        updateButtons(false /* isSignedIn */);
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        logVerbose(String.format("GoogleApiClient onConnectionFailed, error code: %d, with " +
                "resolution: %b", result.getErrorCode(), result.hasResolution()));
        if (!mIntentInProgress) {
            logVerbose("Caching the failure result");
            mConnectionResult = result;

            if (mSignInClicked) {
                resolveSignInError();
            }
            updateButtons(false /* isSignedIn */);
        } else {
            logVerbose("Intent already in progress, ignore the new failure");
        }
    }

    private void updateButtons(boolean isSignedIn) {
        if (isSignedIn) {
            mSignInButton.setVisibility(View.INVISIBLE);
            mSignOutButton.setEnabled(true);
            mRevokeAccessButton.setEnabled(true);
        } else {
            if (mConnectionResult == null) {
                // Disable the sign-in button until onConnectionFailed is called with result.
                mSignInButton.setVisibility(View.INVISIBLE);
                mSignInStatus.setText(getString(R.string.loading_status));
            } else {
                // Enable the sign-in button since a connection result is available.
                mSignInButton.setVisibility(View.VISIBLE);
                mSignInStatus.setText(getString(R.string.signed_out_status));
            }

            mSignOutButton.setEnabled(false);
            mRevokeAccessButton.setEnabled(false);
        }
    }

    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                logVerbose("Start the resolution intent, flipping the intent-in-progress bit.");
                mConnectionResult.startResolutionForResult(this, REQUEST_CODE_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default state and
                // attempt to connect to get an updated ConnectionResult.
                mIntentInProgress = false;
                mGoogleApiClient.connect();
                Log.w(TAG, "Error sending the resolution Intent, connect() again.");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, PlusSampleActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logVerbose(String message) {
        if (mIsLogVerbose) {
            Log.v(TAG, message);
        }
    }
}

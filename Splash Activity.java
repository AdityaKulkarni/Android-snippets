/*Splash activity thread(3 seconds)*/
public class SplashActivity extends AppCompatActivity {


    LoginSessionManager sessionManager;
    //private BroadcastReceiver broadcastReceiver;
    String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));


        sessionManager = new LoginSessionManager(this);
        if(!isNetworkAvailable()){
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SplashActivity.this);
            builder.setTitle(getString(R.string.oops));
            builder.setMessage(getString(R.string.no_connection_detected));
            if (!isNetworkAvailable()) {
                builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                android.app.AlertDialog dialog = builder.create();
                dialog.show();
            }
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(sessionManager.isLoggedIn()){
                        startActivity(new Intent(getApplicationContext(),SelectCityActivity.class));
                    }else{
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    }
                }
            },3000);
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

/*Shared preferences for keeping a user logged in*/
class LoginSessionManager {
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    Context context;
    private String PREFERENCE_KEY = "IsLoggedIn";
    String LOGIN_NAME = "name";
    private String LOGIN_EMAIL = "email";
    String LOGIN_CONTACT = "contact";

    LoginSessionManager(Context ctx){
        this.context = ctx;
        String PREFERENCE_NAME = "Login Preference";
        preferences = context.getSharedPreferences(PREFERENCE_NAME,0);
        editor = preferences.edit();
    }

    void createLoginSession(String name, String email, String contact){
        editor.putBoolean(PREFERENCE_KEY,true);
        editor.putString(LOGIN_NAME,name);
        editor.putString(LOGIN_EMAIL,email);
        editor.putString(LOGIN_CONTACT,contact);
        editor.commit();
    }

    public void checkIsLoggedIn(){
        if(!this.isLoggedIn()){
            Intent intent = new Intent(context,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();
        user.put(LOGIN_NAME, preferences.getString(LOGIN_NAME, null));
        user.put(LOGIN_EMAIL, preferences.getString(LOGIN_EMAIL, null));
        user.put(LOGIN_CONTACT, preferences.getString(LOGIN_CONTACT, null));
        return user;
    }

    boolean isLoggedIn(){
        return preferences.getBoolean(PREFERENCE_KEY, false);
    }

    void logOutUser(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

}

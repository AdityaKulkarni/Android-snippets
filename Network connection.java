/*checks if device is connected to network*/
if (!isNetworkAvailable()) {
    Snackbar snackbar = Snackbar.make(linearLayout, getString(R.string.no_connection_detected), Snackbar.LENGTH_LONG);
    snackbar.show();
}
private boolean isNetworkAvailable() {
    ConnectivityManager connectivityManager
        = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
}
/*Inserting data into table with PHP backend using POST method*/
class AppointWorker extends AsyncTask<String, String, String> implements IpInterface{
    private String TAG = getClass().getSimpleName();

    private String result;
    Context context;
    private ProgressDialog progressDialog;
    View view;

    AppointWorker(Context ctx, View view) {
        this.context = ctx;
        this.view = view;
    }
    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.confirmingAppointment));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String pname = params[0];
        String pcontact = params[1];
        String pemail = params[2];
        String docname = params[3];
        String appointURl = ip + "requestappoint.php";

        try {
            URL url = new URL(appointURl);
            Log.i(TAG, String.valueOf(url));
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            String appoint = URLEncoder.encode("pname", "UTF-8") + "=" + URLEncoder.encode(pname, "UTF-8") + "&" +
                    URLEncoder.encode("pcontact", "UTF-8") + "=" + URLEncoder.encode(pcontact, "UTF-8") + "&" +
                    URLEncoder.encode("pemail", "UTF-8") + "=" + URLEncoder.encode(pemail, "UTF-8") + "&" +
                    URLEncoder.encode("dname", "UTF-8") + "=" + URLEncoder.encode(docname, "UTF-8");

            writer.write(appoint);
            writer.close();

            Log.i(TAG, appoint);

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            StringBuffer buffer = new StringBuffer();

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            result = buffer.toString();
            reader.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Handler handler =  new Handler(context.getMainLooper());
            handler.post( new Runnable(){
                public void run(){
                    Snackbar snackbar = Snackbar.make(view, context.getString(R.string.invalidUrl), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            Handler handler =  new Handler(context.getMainLooper());
            handler.post( new Runnable(){
                public void run(){
                    Snackbar snackbar = Snackbar.make(view, context.getString(R.string.conFailed), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            });
        }

        return result;
    }

    @Override
    protected void onPostExecute(String aVoid) {
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
            Snackbar snackbar = Snackbar.make(view, aVoid, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }
}

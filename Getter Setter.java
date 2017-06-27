/*Gteer setter model*/
class DoctorListModel implements Serializable{
    private String docname,docemail,doccontact,docqual,doccat,doclat,doclong;
    private String url;
    private String city;
    private String category;
    private String doctorProfileUrl;
    private String date;
    private String time;

     String getCity() {
        return city;
     }

     void setCity(String city) {
        this.city = city;
     }

    String getDoctorProfileUrl() {
        return doctorProfileUrl;
    }

    void setDoctorProfileUrl(String doctorProfileUrl) {
        this.doctorProfileUrl = doctorProfileUrl;
    }

    String getDate() {
        return date;
    }

    void setDate(String date) {
        this.date = date;
    }



     String getUrl() {
        return url;
     }

     void setUrl(String url) {
        this.url = url;
     }

     String getCategory() {
        return category;
     }

     void setCategory(String category) {
        this.category = category;
     }

    String getDocname(){
        return docname;
    }

    void setDocname(String docname){
        this.docname = docname;
    }

    String getDocemail(){
        return docemail;
    }

    void setDocemail(String docemail){
        this.docemail = docemail;
    }

    String getDoccontact(){
        return doccontact;
    }

    void setDoccontact(String doccontact){
        this.doccontact = doccontact;
    }

     String getDocqual(){
        return  docqual;
    }

     void setDocqual(String docqual){
        this.docqual = docqual;
    }

     String getDoccat(){
        return doccat;
    }

     void setDoccat(String doccat){
        this.doccat = doccat;
    }

     String getDoclat(){
        return doclat;
    }

     void setDoclat(String doclat){
        this.doclat = doclat;
    }

     String getDoclong(){
        return doclong;
    }

    void setDoclong(String doclong){
        this.doclong = doclong;
    }

    String getTime() {
        return time;
    }

    void setTime(String time) {
        this.time = time;
    }
}

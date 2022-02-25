package vidyawell.infotech.bsn.admin.Database;



public class Note {
    public static final String TABLE_NAME = "markentry";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CODE = "studentcode";
    public static final String COLUMN_NAME = "studentname";
    public static final String COLUMN_THEORY = "theorymarks";
    public static final String COLUMN_PRACTICAL = "practicalmarks";
    public static final String COLUMN_NOTEBOOK = "notebookmarks";
    public static final String COLUMN_ENRICHMENT = "enrichmentmarks";
    public static final String COLUMN_LOCK = "marklock";
    public static final String COLUMN_MAXTHEORY = "maxtheory";
    public static final String COLUMN_MAXENRICHMENT = "maxenrichment";
    public static final String COLUMN_MAXNOTE = "maxnote";
    public static final String COLUMN_MAXPRACTICAL = "maxpractical";
    public static final String COLUMN_ROWCOUNT = "rcount";
    public static final String COLUMN_GRANDTOTAL = "total";
    public static final String COLUMN_TEST = "test";

    private int id;
    private String code;
    private String name;
    private String theory;
    private String practical;
    private String notebook;
    private String enrichment;
    private String lock;
    private String maxtheory;
    private String maxenrichment;
    private String maxnote;
    private String maxpractical;
    private String rowcount;
    private String total;
    private String test;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_CODE + " TEXT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_THEORY + " TEXT,"
                    + COLUMN_PRACTICAL + " TEXT,"
                    + COLUMN_NOTEBOOK + " TEXT,"
                    + COLUMN_ENRICHMENT + " TEXT,"
                    + COLUMN_LOCK + " TEXT,"
                    + COLUMN_MAXTHEORY + " TEXT,"
                    + COLUMN_MAXENRICHMENT + " TEXT,"
                    + COLUMN_MAXNOTE + " TEXT,"
                    + COLUMN_MAXPRACTICAL + " TEXT,"
                    + COLUMN_ROWCOUNT + " TEXT,"
                    + COLUMN_GRANDTOTAL + " TEXT,"
                    + COLUMN_TEST + " TEXT"
                    + ")";

    public Note() {
    }

    public Note(int id,String code, String name,String theory,String practical,String notebook, String enrichment, String lock,String maxtheory,String maxenrichment,String maxnote,String maxpractical,String rowcount,String total,String test) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.theory = theory;
        this.practical = practical;
        this.notebook = notebook;
        this.enrichment = enrichment;
        this.lock = lock;
        this.maxtheory = maxtheory;
        this.maxenrichment = maxenrichment;
        this.maxnote = maxnote;
        this.maxpractical = maxpractical;
        this.rowcount = rowcount;
        this.total = total;
        this.test = test;
    }

    public String gettotal() {
        return total;
    }
    public void settotal(String total) {
        this.total = total;
    }

    public String gettest() {
        return test;
    }
    public void settest(String test) {
        this.test = test;
    }


    public String getrowcount() {
        return rowcount;
    }
    public void setrowcount(String rowcount) {
        this.rowcount = rowcount;
    }


    public String getlock() {
        return lock;
    }
    public void setlock(String lock) {
        this.lock = lock;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String gettheory() {
        return theory;
    }

    public void settheory(String theory) {
        this.theory = theory;
    }

    public String getpractical() {
        return practical;
    }

    public void setpractical(String practical) {
        this.practical = practical;
    }

    public String getnotebook() {
        return notebook;
    }

    public void setnotebook(String notebook) {
        this.notebook = notebook;
    }

    public String getenrichment() {
        return enrichment;
    }

    public void setenrichment(String enrichment) {
        this.enrichment = enrichment;
    }


    public String getmaxtheory() {
        return maxtheory;
    }

    public void setmaxtheory(String maxtheory) {
        this.maxtheory = maxtheory;
    }

    public String getmaxenrichment() {
        return maxenrichment;
    }

    public void setmaxenrichment(String maxenrichment) {
        this.maxenrichment = maxenrichment;
    }


    public String getmaxnote() {
        return maxnote;
    }

    public void setmaxnote(String maxnote) {
        this.maxnote = maxnote;
    }

    public String getmaxpractical() {
        return maxpractical;
    }

    public void setmaxpractical(String maxpractical) {
        this.maxpractical = maxpractical;
    }
}

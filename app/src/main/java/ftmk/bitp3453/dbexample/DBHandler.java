package ftmk.bitp3453.dbexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    //(num, name, email, state, gender, dob);
    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "DBSTUDENT";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "STUDENT";

    // below variable is for our id column.
    private static final String ID_COL = "StudentNumber";

    // below variable is for our course name column
    private static final String NAME_COL = "StudentName";

    // below variable id for our course duration column.
    private static final String EMAIL_COL = "StudentEmail";

    // below variable for our course description column.
    private static final String STATE_COL = "StudentState";

    // below variable is for our course tracks column.
    private static final String GENDER_COL = "StudentGender";

    // below variable is for our course tracks column.
    private static final String DOB_COL = "StudentDOB";

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //(num, name, email, state, gender, dob);
    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " TEXT PRIMARY KEY, "
                + NAME_COL + " TEXT,"
                + EMAIL_COL + " TEXT,"
                + STATE_COL + " TEXT,"
                + GENDER_COL + " VARCHAR,"
                + DOB_COL + " DATE)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new course to our sqlite database.
    public void addNewCourse(String snum, String sname, String semail, String sstate, String sgender, String sdob) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        //(num, name, email, state, gender, dob);
        // on below line we are passing all values
        // along with its key and value pair.
        values.put(ID_COL, snum);
        values.put(NAME_COL, sname);
        values.put(EMAIL_COL, semail);
        values.put(STATE_COL, sstate);
        values.put(GENDER_COL, sgender);
        values.put(DOB_COL, sdob);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}